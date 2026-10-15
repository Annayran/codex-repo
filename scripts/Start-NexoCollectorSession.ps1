param([ValidateRange(5,60)][int]$Minutes=60,[ValidateRange(1024,65535)][int]$Port=18086,[switch]$AllowUnverifiedHttps)
$ErrorActionPreference='Stop'
$root=Split-Path $PSScriptRoot -Parent
$desk=Join-Path $root 'config/private/desk-skala'
$access=Get-Content (Join-Path $desk 'access.json') -Raw|ConvertFrom-Json
$deskSession=Get-Content (Join-Path $desk 'session.json') -Raw|ConvertFrom-Json
$headers=@{Authorization='Basic '+[Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes($access.principal+':'+$access.password))}
Invoke-RestMethod "http://127.0.0.1:$($deskSession.port)/monitoring/issues" -Headers $headers -TimeoutSec 5|Out-Null
$connector=Join-Path $root 'target/tools/cloudflared.exe'
$jar=Join-Path $root 'integration/sankhya-button/build/libs/ZAYA-NEXO-V1.8.jar'
if(!(Test-Path $connector) -or !(Test-Path $jar)){throw 'Compile o JAR V1.8 e disponibilize o conector oficial antes.'}
$listener=[Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback,$Port);try{$listener.Start()}finally{$listener.Stop()}
$dir=Join-Path $root ('config/private/log-session-'+[Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory $dir|Out-Null
$sid=[Security.Principal.WindowsIdentity]::GetCurrent().User.Value
& icacls $dir /inheritance:r /grant:r "*${sid}:(OI)(CI)F" '*S-1-5-18:(OI)(CI)F'|Out-Null
if($LASTEXITCODE -ne 0){throw 'Falha ao proteger a identidade temporária.'}
$bytes=New-Object byte[] 32;$rng=[Security.Cryptography.RandomNumberGenerator]::Create();try{$rng.GetBytes($bytes)}finally{$rng.Dispose()}
$password=[Convert]::ToBase64String($bytes);$principal='log-'+[Guid]::NewGuid().ToString('N')
$frontAuth='Basic '+[Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes($principal+':'+$password))
$expiry=[DateTimeOffset]::UtcNow.AddMinutes($Minutes).ToString('o')
$ingress=$null;$tunnel=$null;$complete=$false
$oldFront=$env:NEXO_INGRESS_FRONT_AUTH;$oldBack=$env:NEXO_INGRESS_BACK_AUTH;$oldExpiry=$env:NEXO_INGRESS_EXPIRY
try {
    $env:NEXO_INGRESS_FRONT_AUTH=$frontAuth;$env:NEXO_INGRESS_BACK_AUTH=$headers.Authorization;$env:NEXO_INGRESS_EXPIRY=$expiry
    $ingress=Start-Process java -ArgumentList @('-Djdk.net.unixdomain.tmpdir=target','-cp','target/classes','br.com.zaya.engineering.nexo.monitoring.CollectorIngress',"$Port","$($deskSession.port)") -WorkingDirectory $root -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $dir 'ingress.stdout.log') -RedirectStandardError (Join-Path $dir 'ingress.stderr.log')
    $env:NEXO_INGRESS_FRONT_AUTH=$oldFront;$env:NEXO_INGRESS_BACK_AUTH=$oldBack;$env:NEXO_INGRESS_EXPIRY=$oldExpiry
    $ready=$false
    for($i=0;$i -lt 40;$i++){if($ingress.HasExited){throw 'Ingresso encerrou na inicialização.'};try{Invoke-RestMethod "http://127.0.0.1:$Port/health" -Headers @{Authorization=$frontAuth} -TimeoutSec 2|Out-Null;$ready=$true;break}catch{Start-Sleep -Milliseconds 250}}
    if(!$ready){throw 'Ingresso local indisponível.'}
    $tunnel=Start-Process $connector -ArgumentList @('tunnel','--no-autoupdate','--protocol','http2','--url',"http://127.0.0.1:$Port") -WorkingDirectory $root -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $dir 'tunnel.stdout.log') -RedirectStandardError (Join-Path $dir 'tunnel.stderr.log')
    $public=$null
    for($i=0;$i -lt 60;$i++){if($tunnel.HasExited){throw 'Túnel encerrou.'};foreach($file in @('tunnel.stdout.log','tunnel.stderr.log')){if(Test-Path (Join-Path $dir $file)){$match=[regex]::Match((''+(Get-Content (Join-Path $dir $file) -Raw)),'https://[a-z0-9-]+\.trycloudflare\.com');if($match.Success){$public=$match.Value;break}}};if($public){break};Start-Sleep -Milliseconds 500}
    if(!$public){throw 'Endereço do túnel não obtido.'}
    $verified=$false
    try{Invoke-RestMethod ($public+'/health') -Headers @{Authorization=$frontAuth} -TimeoutSec 10|Out-Null;$verified=$true}catch{}
    if(!$verified -and !$AllowUnverifiedHttps){throw 'HTTPS público não validado; não gerar instalação como comprovada.'}
    $resources=Join-Path $dir 'resources';New-Item -ItemType Directory $resources|Out-Null
    @"
endpoint=$public/monitoring/events
principal=$principal
password=$password
expiresAt=$expiry
installationId=skala-log-homologacao
erpVersion=unverified
"@|Set-Content (Join-Path $resources 'nexo-temporary-session.properties') -Encoding ascii
    $trialJar=Join-Path $dir 'ZAYA-NEXO-LOG-HOMOLOGACAO-V1.8.jar';Copy-Item -LiteralPath $jar -Destination $trialJar
    & jar uf $trialJar -C $resources nexo-temporary-session.properties
    if($LASTEXITCODE -ne 0){throw 'Falha ao provisionar JAR privado.'}
    $sessionFile=Join-Path $dir 'session.json'
    @{expiresAt=$expiry;endpoint=($public+'/monitoring/events');jar=$trialJar;httpsValidated=$verified;processes=@(@($ingress,$tunnel)|ForEach-Object{@{id=$_.Id;startedAt=$_.StartTime.ToUniversalTime().ToString('o')}})}|ConvertTo-Json -Depth 5|Set-Content $sessionFile
    $watcher=Start-Process (Get-Process -Id $PID).Path -ArgumentList @('-NoProfile','-File',('"'+(Join-Path $PSScriptRoot 'Stop-NexoCollectorSession.ps1')+'"'),'-SessionPath',('"'+$sessionFile+'"'),'-AtExpiry') -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $dir 'expiry.stdout.log') -RedirectStandardError (Join-Path $dir 'expiry.stderr.log')
    Start-Sleep -Milliseconds 250;if($watcher.HasExited){throw 'Supervisor de expiração indisponível.'}
    $complete=$true
    [pscustomobject]@{Jar=$trialJar;Session=$sessionFile;ExpiresAt=$expiry;HTTPSValidated=$verified;LocalDesk="http://127.0.0.1:$($deskSession.port)/issues.html"}
}finally{
    $env:NEXO_INGRESS_FRONT_AUTH=$oldFront;$env:NEXO_INGRESS_BACK_AUTH=$oldBack;$env:NEXO_INGRESS_EXPIRY=$oldExpiry
    if(!$complete){foreach($owned in @($tunnel,$ingress)){if($owned -and !$owned.HasExited){Stop-Process -Id $owned.Id}}}
}
