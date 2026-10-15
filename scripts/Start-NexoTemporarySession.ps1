param([ValidateRange(5,120)][int]$Minutes=60, [ValidateRange(1024,65535)][int]$Port=18081,
    [switch]$AllowUnverifiedHttps, [switch]$LocalAi)
$ErrorActionPreference = 'Stop'
$root = Split-Path $PSScriptRoot -Parent
$connector = Join-Path $root 'target/tools/cloudflared.exe'
if (!(Test-Path $connector)) { throw 'Conector oficial cloudflared não disponível em target/tools/cloudflared.exe.' }
if (!(Test-Path (Join-Path $root 'target/quarkus-app/quarkus-run.jar'))) { throw 'Compile primeiro o receptor com mvn verify.' }
$listener = [Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback,$Port)
try { $listener.Start() } finally { $listener.Stop() }
$sessionDir = Join-Path $root ('config/private/session-' + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $sessionDir -Force | Out-Null
# Restrict generated credentials/artifacts to the current Windows account and SYSTEM.
$currentSid = [Security.Principal.WindowsIdentity]::GetCurrent().User.Value
& icacls $sessionDir /inheritance:r /grant:r "*${currentSid}:(OI)(CI)F" '*S-1-5-18:(OI)(CI)F' | Out-Null
if ($LASTEXITCODE -ne 0) { throw 'Não foi possível restringir acesso ao diretório privado.' }
$principal = 'trial-' + [Guid]::NewGuid().ToString('N')
$randomBytes = New-Object byte[] 32
$rng = [Security.Cryptography.RandomNumberGenerator]::Create()
try { $rng.GetBytes($randomBytes) } finally { $rng.Dispose() }
$password = [Convert]::ToBase64String($randomBytes)
$expiry = [DateTimeOffset]::UtcNow.AddMinutes($Minutes).ToString('o')
$identityFile = Join-Path $sessionDir 'identities.json'
@(@{principal=$principal;tenant=$principal;environment='fixture';approvedPolicies=@()}) | ConvertTo-Json -AsArray -Depth 5 | Set-Content $identityFile -Encoding utf8
$centralFile = Join-Path $sessionDir 'central.properties'
@"
quarkus.http.host=127.0.0.1
quarkus.http.port=$Port
quarkus.security.users.embedded.users.$principal=`${NEXO_TEMPORARY_PASSWORD}
quarkus.security.users.embedded.roles.$principal=investigator
nexo.identity-manifest=$($identityFile.Replace('\','/'))
nexo.storage=$($sessionDir.Replace('\','/'))/data
nexo.gateway=disabled
nexo.demo-identities=false
nexo.ai.enabled=false
"@ | Set-Content $centralFile -Encoding ascii
if ($LocalAi) {
    $tags=Invoke-RestMethod 'http://127.0.0.1:11434/api/tags' -TimeoutSec 3
    if (!($tags.models | Where-Object name -eq 'qwen2.5:3b')) {throw 'Modelo local ausente.'}
    @'
nexo.ai.enabled=true
quarkus.langchain4j.openai.base-url=http://127.0.0.1:11434/v1
quarkus.langchain4j.openai.api-key=ollama
quarkus.langchain4j.openai.chat-model.model-name=qwen2.5:3b
quarkus.langchain4j.openai.chat-model.temperature=0
quarkus.langchain4j.openai.timeout=180s
quarkus.langchain4j.openai.max-retries=1
'@ | Add-Content $centralFile -Encoding ascii
}
$service=$null; $tunnel=$null; $completed=$false
$previousPassword = $env:NEXO_TEMPORARY_PASSWORD
try {
    $env:NEXO_TEMPORARY_PASSWORD = $password
    $centralUri = ([Uri]$centralFile).AbsoluteUri
    $service = Start-Process java -WorkingDirectory $root -WindowStyle Hidden -PassThru `
        -ArgumentList @('-Djdk.net.unixdomain.tmpdir=target', ('-Dquarkus.config.locations=' + $centralUri), '-jar','target/quarkus-app/quarkus-run.jar') `
        -RedirectStandardOutput (Join-Path $sessionDir 'service.stdout.log') -RedirectStandardError (Join-Path $sessionDir 'service.stderr.log')
    $env:NEXO_TEMPORARY_PASSWORD=$previousPassword
    $headers=@{Authorization='Basic '+[Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes("${principal}:$password"))}
    $ready=$false
    for ($attempt=0;$attempt -lt 60;$attempt++) {
        if ($service.HasExited) { throw 'Receptor encerrou na inicialização. Consulte os logs privados da sessão.' }
        try { Invoke-RestMethod "http://127.0.0.1:$Port/investigations/00000000-0000-0000-0000-000000000000" -Headers $headers -TimeoutSec 2 | Out-Null }
        catch { if ($_.Exception.Response -and [int]$_.Exception.Response.StatusCode -eq 404) { $ready=$true;break } }
        Start-Sleep -Milliseconds 500
    }
    if (!$ready) { throw 'Receptor local indisponível.' }
    $tunnelLog=Join-Path $sessionDir 'tunnel.stderr.log'
    $tunnel=Start-Process $connector -WorkingDirectory $sessionDir -WindowStyle Hidden -PassThru `
        -ArgumentList @('tunnel','--no-autoupdate','--protocol','http2','--url',"http://127.0.0.1:$Port") `
        -RedirectStandardOutput (Join-Path $sessionDir 'tunnel.stdout.log') -RedirectStandardError $tunnelLog
    $publicUrl=$null
    for ($attempt=0;$attempt -lt 90;$attempt++) {
        if ($tunnel.HasExited) { throw 'Túnel encerrou; consulte os logs privados da sessão.' }
        if (Test-Path $tunnelLog) {
            $logStream=[IO.File]::Open($tunnelLog,[IO.FileMode]::Open,[IO.FileAccess]::Read,[IO.FileShare]::ReadWrite)
            $logReader=[IO.StreamReader]::new($logStream)
            try { $tunnelText=$logReader.ReadToEnd() } finally { $logReader.Dispose() }
            $match=[regex]::Match($tunnelText,'https://[a-z0-9-]+\.trycloudflare\.com')
            if ($match.Success) { $publicUrl=$match.Value;break }
        }
        Start-Sleep -Milliseconds 500
    }
    if (!$publicUrl) { throw 'Túnel não disponibilizou URL no prazo.' }
    $endpoint="$publicUrl/investigations/packages"
    $packet=Get-Content (Join-Path $root 'docs/examples/package-simulated.json') -Raw | ConvertFrom-Json
    $packet.collectedAt=[DateTime]::UtcNow.ToString('o')
    $publicReady=$false
    $lastPublicStatus='no-response'
    $publicAttempts = if ($AllowUnverifiedHttps) { 5 } else { 45 }
    for ($attempt=0;$attempt -lt $publicAttempts;$attempt++) {
        try {
            Invoke-RestMethod "$publicUrl/investigations/00000000-0000-0000-0000-000000000000" -Headers $headers -TimeoutSec 5 | Out-Null
        } catch {
            if ($_.Exception.Response) { $lastPublicStatus=[string][int]$_.Exception.Response.StatusCode }
            else { $lastPublicStatus=$_.Exception.GetType().Name }
            if ($lastPublicStatus -eq '404') { $publicReady=$true;break }
            Start-Sleep -Milliseconds 1000
        }
    }
    if (!$publicReady -and !$AllowUnverifiedHttps) { throw "HTTPS público não ficou pronto. Último resultado: $lastPublicStatus" }
    if (!$publicReady) { Write-Warning 'HTTPS público não validado nesta rede. Pacote somente para testar conectividade pela hospedagem; não considerar envio comprovado.' }
    $testEndpoint = if ($publicReady) { $endpoint } else { "http://127.0.0.1:$Port/investigations/packages" }
    # POST only once: a timeout may happen after persistence, so never retry blindly.
    $receipt=Invoke-RestMethod ($testEndpoint+'?receipt=true') -Method Post -Headers $headers -ContentType 'application/json; charset=utf-8' `
        -Body ([Text.Encoding]::UTF8.GetBytes(($packet | ConvertTo-Json -Depth 20))) -TimeoutSec 10
    if (!$receipt -or !$receipt.simulated) { throw 'Envio sintético não foi validado.' }
    $resourceDir=Join-Path $sessionDir 'resources'
    $serviceDir=Join-Path $resourceDir 'META-INF/services'
    New-Item -ItemType Directory -Path $serviceDir -Force | Out-Null
    'br.com.zaya.engineering.nexobutton.TemporarySessionProvider' | Set-Content (Join-Path $serviceDir 'br.com.zaya.engineering.nexobutton.ButtonConfigurationProvider') -Encoding ascii
    @"
endpoint=$endpoint
principal=$principal
password=$password
expiresAt=$expiry
erpVersion=4.36b159/core-5.21.4
"@ | Set-Content (Join-Path $resourceDir 'nexo-temporary-session.properties') -Encoding ascii
    & (Join-Path $root 'integration/sankhya-button/build.ps1') -ProvisioningDir $resourceDir | Out-Null
    $trialJar=Join-Path $sessionDir 'ZAYA-NEXO-HOMOLOGACAO.jar'
    $buttonVersion = ((Get-Content (Join-Path $root 'integration/sankhya-button/gradle.properties') | Where-Object {$_ -match '^projectVersion='}) -split '=',2)[1]
    Copy-Item -LiteralPath (Join-Path $sessionDir "build/libs/ZAYA-NEXO-V$buttonVersion.jar") -Destination $trialJar
    # Restore the ordinary artifact without session credentials.
    & (Join-Path $root 'integration/sankhya-button/build.ps1') | Out-Null
    $sessionFile=Join-Path $sessionDir 'session.json'
    $processEntries=@(@($service,$tunnel) | ForEach-Object {@{id=$_.Id;startedAt=$_.StartTime.ToUniversalTime().ToString('o')}})
    @{expiresAt=$expiry;endpoint=$endpoint;jar=$trialJar;httpsValidated=$publicReady;processes=$processEntries} | ConvertTo-Json -Depth 5 | Set-Content $sessionFile -Encoding utf8
    $stopScript=Join-Path $PSScriptRoot 'Stop-NexoTemporarySession.ps1'
    $watcher=Start-Process (Get-Process -Id $PID).Path -WindowStyle Hidden -PassThru -ArgumentList @('-NoProfile','-File',('"'+$stopScript+'"'),'-SessionPath',('"'+$sessionFile+'"'),'-AtExpiry') `
        -RedirectStandardOutput (Join-Path $sessionDir 'expiry.stdout.log') -RedirectStandardError (Join-Path $sessionDir 'expiry.stderr.log')
    Start-Sleep -Milliseconds 500
    if ($watcher.HasExited) { throw 'Supervisor de expiração não iniciou; encerrando sessão.' }
    $completed=$true
    [PSCustomObject]@{Endpoint=$endpoint;ExpiresAt=$expiry;Jar=$trialJar;Session=$sessionFile;HTTPSValidated=$publicReady;Simulated=$true;Investigation=$receipt.id}
} finally {
    $env:NEXO_TEMPORARY_PASSWORD=$previousPassword
    if (!$completed) { foreach ($process in @($tunnel,$service)) { if ($process -and !$process.HasExited) { Stop-Process -Id $process.Id } } }
}

