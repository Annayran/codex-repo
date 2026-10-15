param([ValidateRange(1024,65535)][int]$Port=18085,[switch]$CopyPassword)
$ErrorActionPreference='Stop'
$root=Split-Path $PSScriptRoot -Parent
$desk=Join-Path $root 'config/private/desk-skala'
New-Item -ItemType Directory -Path $desk -Force | Out-Null
$sid=[Security.Principal.WindowsIdentity]::GetCurrent().User.Value
& icacls $desk /inheritance:r /grant:r "*${sid}:(OI)(CI)F" '*S-1-5-18:(OI)(CI)F' | Out-Null
if($LASTEXITCODE -ne 0){throw 'Não foi possível proteger o diretório da central.'}
$identityPath=Join-Path $desk 'access.json'
if(!(Test-Path $identityPath)) {
    $bytes=New-Object byte[] 32;$rng=[Security.Cryptography.RandomNumberGenerator]::Create()
    try{$rng.GetBytes($bytes)}finally{$rng.Dispose()}
    @{principal='desk-skala';password=[Convert]::ToBase64String($bytes)}|ConvertTo-Json|Set-Content $identityPath
}
$access=Get-Content $identityPath -Raw|ConvertFrom-Json
$identities=Join-Path $desk 'identities.json'
@(@{principal=$access.principal;tenant='skala';environment='homologacao-local';approvedPolicies=@()})|ConvertTo-Json -AsArray -Depth 5|Set-Content $identities
$central=Join-Path $desk 'central.properties'
@"
quarkus.http.host=127.0.0.1
quarkus.http.port=$Port
quarkus.security.users.embedded.users.$($access.principal)=`${NEXO_DESK_PASSWORD}
quarkus.security.users.embedded.roles.$($access.principal)=investigator
nexo.identity-manifest=$($identities.Replace('\','/'))
nexo.storage=$($desk.Replace('\','/'))/data
nexo.demo-identities=false
nexo.gateway=disabled
nexo.ai.enabled=false
"@|Set-Content $central
$sessionPath=Join-Path $desk 'session.json'
$running=$null
if(Test-Path $sessionPath){$old=Get-Content $sessionPath -Raw|ConvertFrom-Json;$running=Get-Process -Id $old.pid -ErrorAction SilentlyContinue
    if($running -and $running.StartTime.ToUniversalTime().Ticks -ne ([DateTimeOffset]$old.started).UtcTicks){$running=$null}}
if(!$running) {
    $listener=[Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback,$Port)
    try{$listener.Start()}finally{$listener.Stop()}
    $previous=$env:NEXO_DESK_PASSWORD
    try {
        $env:NEXO_DESK_PASSWORD=$access.password
        $argsList=@('-Djdk.net.unixdomain.tmpdir=target',('-Dquarkus.config.locations='+([Uri]$central).AbsoluteUri),'-jar','target/quarkus-app/quarkus-run.jar')
        $running=Start-Process java -ArgumentList $argsList -WorkingDirectory $root -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $desk 'stdout.log') -RedirectStandardError (Join-Path $desk 'stderr.log')
        @{pid=$running.Id;started=$running.StartTime.ToUniversalTime().ToString('o');port=$Port}|ConvertTo-Json|Set-Content $sessionPath
    }finally{$env:NEXO_DESK_PASSWORD=$previous}
    $ready=$false
    for($i=0;$i -lt 60;$i++) {
        if($running.HasExited){throw 'Receptor encerrou; consulte logs privados da central.'}
        try{Invoke-WebRequest "http://127.0.0.1:$Port/issues.html" -TimeoutSec 2|Out-Null;$ready=$true;break}catch{Start-Sleep -Milliseconds 500}
    }
    if(!$ready){Stop-Process -Id $running.Id;throw 'Receptor local indisponível.'}
} else {$Port=$old.port}
if($CopyPassword){Set-Clipboard $access.password;Write-Host 'Senha local copiada para a área de transferência.'}
[pscustomobject]@{URL="http://127.0.0.1:$Port/issues.html";User=$access.principal;PublicTunnel=$false}
