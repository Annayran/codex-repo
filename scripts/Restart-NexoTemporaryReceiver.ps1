param([Parameter(Mandatory)][string]$SessionPath)
$ErrorActionPreference='Stop'
$root=Split-Path $PSScriptRoot -Parent
$allowed=[IO.Path]::GetFullPath((Join-Path $root 'config/private'))+[IO.Path]::DirectorySeparatorChar
$sessionFile=[IO.Path]::GetFullPath($SessionPath)
if (!$sessionFile.StartsWith($allowed,[StringComparison]::OrdinalIgnoreCase)) { throw 'Session must be inside config/private.' }
$session=Get-Content -LiteralPath $sessionFile -Raw | ConvertFrom-Json
if ([DateTimeOffset]$session.expiresAt -le [DateTimeOffset]::UtcNow) { throw 'Sessão expirada; reinício recusado.' }
$dir=Split-Path $sessionFile
$settings=@{}
Get-Content (Join-Path $dir 'resources/nexo-temporary-session.properties') | ForEach-Object {
    if ($_ -match '^([^=]+)=(.*)$') { $settings[$matches[1]]=$matches[2] }
}
if (!$settings.password -or !$settings.principal) { throw 'Credencial da sessão ausente.' }
$centralFile=Join-Path $dir 'central.properties'
$portLine=Get-Content $centralFile | Where-Object {$_ -match '^quarkus.http.port='}
$port=[int](($portLine -split '=',2)[1])
$existing=@()
foreach ($entry in $session.processes) {
    $process=Get-Process -Id $entry.id -ErrorAction SilentlyContinue
    if ($process -and ([DateTimeOffset]$process.StartTime).UtcTicks -eq ([DateTimeOffset]$entry.startedAt).UtcTicks) {
        if ($process.ProcessName -eq 'java') { throw 'Receptor atual ainda está ativo; não iniciar outro.' }
        $existing+=$entry
    }
}
$oldPassword=$env:NEXO_TEMPORARY_PASSWORD
$receiver=$null; $success=$false
try {
    $env:NEXO_TEMPORARY_PASSWORD=$settings.password
    $receiver=Start-Process java -WorkingDirectory $root -WindowStyle Hidden -PassThru `
        -ArgumentList @('-Djdk.net.unixdomain.tmpdir=target',('-Dquarkus.config.locations='+([Uri]$centralFile).AbsoluteUri),'-jar','target/quarkus-app/quarkus-run.jar') `
        -RedirectStandardOutput (Join-Path $dir 'receiver-update.stdout.log') -RedirectStandardError (Join-Path $dir 'receiver-update.stderr.log')
    $env:NEXO_TEMPORARY_PASSWORD=$oldPassword
    $headers=@{Authorization='Basic '+[Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes(($settings.principal+':'+$settings.password)))}
    $ready=$false
    for ($attempt=0;$attempt -lt 40;$attempt++) {
        if ($receiver.HasExited) { throw 'Receptor atualizado encerrou; consulte logs privados.' }
        try { Invoke-RestMethod "http://127.0.0.1:$port/investigations/00000000-0000-0000-0000-000000000000" -Headers $headers -TimeoutSec 2 | Out-Null }
        catch { if ($_.Exception.Response -and [int]$_.Exception.Response.StatusCode -eq 404) {$ready=$true;break} }
        Start-Sleep -Milliseconds 500
    }
    if (!$ready) { throw 'Receptor atualizado não ficou pronto.' }
    $session.processes=@($existing)+@(@{id=$receiver.Id;startedAt=$receiver.StartTime.ToUniversalTime().ToString('o')})
    $session | ConvertTo-Json -Depth 6 | Set-Content -LiteralPath $sessionFile -Encoding utf8
    $stopFile=Join-Path $PSScriptRoot 'Stop-NexoTemporarySession.ps1'
    $watcher=Start-Process (Get-Process -Id $PID).Path -WindowStyle Hidden -PassThru `
        -ArgumentList @('-NoProfile','-File',('"'+$stopFile+'"'),'-SessionPath',('"'+$sessionFile+'"'),'-AtExpiry') `
        -RedirectStandardOutput (Join-Path $dir 'updated-expiry.stdout.log') -RedirectStandardError (Join-Path $dir 'updated-expiry.stderr.log')
    Start-Sleep -Milliseconds 500
    if ($watcher.HasExited) { throw 'Supervisor de expiração não iniciou.' }
    $success=$true
    [PSCustomObject]@{ReceiverReady=$true;OriginalExpiryPreserved=$true;ExpirySupervisorRunning=$true}
} finally {
    $env:NEXO_TEMPORARY_PASSWORD=$oldPassword
    if (!$success -and $receiver -and !$receiver.HasExited) { Stop-Process -Id $receiver.Id }
}
