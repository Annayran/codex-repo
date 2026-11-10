$ErrorActionPreference='Stop'
$testDir=Join-Path (Split-Path $PSScriptRoot -Parent) ('config/private/stop-check-'+[Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $testDir | Out-Null
$child=Start-Process (Get-Process -Id $PID).Path -WindowStyle Hidden -PassThru -ArgumentList @('-NoProfile','-Command','Start-Sleep -Seconds 30')
try {
    $testSession=Join-Path $testDir 'session.json'
    @{expiresAt=[DateTimeOffset]::UtcNow.AddSeconds(-1).ToString('o');processes=@(@{id=$child.Id;startedAt=$child.StartTime.ToUniversalTime().ToString('o')})} |
        ConvertTo-Json -Depth 5 | Set-Content $testSession -Encoding utf8
    & (Join-Path $PSScriptRoot 'Stop-NexoTemporarySession.ps1') -SessionPath $testSession -AtExpiry | Out-Null
    if (!$child.WaitForExit(2000)) { throw 'Supervisor não encerrou o processo de teste expirado.' }
    'Session stop: ISO timestamp handling and matching process termination passed.'
} finally { if (!$child.HasExited) { Stop-Process -Id $child.Id } }
