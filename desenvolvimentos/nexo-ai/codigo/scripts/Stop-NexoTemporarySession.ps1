param([Parameter(Mandatory)][string]$SessionPath, [switch]$AtExpiry)
$ErrorActionPreference = 'Stop'
$rootPath = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '../config/private')) + [IO.Path]::DirectorySeparatorChar
$sessionFile = [IO.Path]::GetFullPath($SessionPath)
if (!$sessionFile.StartsWith($rootPath, [StringComparison]::OrdinalIgnoreCase)) { throw 'Session must be inside config/private.' }
$session = Get-Content -LiteralPath $sessionFile -Raw | ConvertFrom-Json
if ($AtExpiry) {
    # ConvertFrom-Json can deserialize ISO timestamps as DateTime; never reparse a culture-formatted string.
    $remaining = ([DateTimeOffset]$session.expiresAt - [DateTimeOffset]::UtcNow).TotalSeconds
    if ($remaining -gt 0) { Start-Sleep -Seconds ([int][Math]::Ceiling($remaining)) }
    # A receiver can be replaced within the same session without extending its expiry.
    $session = Get-Content -LiteralPath $sessionFile -Raw | ConvertFrom-Json
}
foreach ($entry in $session.processes) {
    $process = Get-Process -Id $entry.id -ErrorAction SilentlyContinue
    if ($process -and ([DateTimeOffset]$process.StartTime).UtcTicks -eq ([DateTimeOffset]$entry.startedAt).UtcTicks) {
        Stop-Process -Id $process.Id
    }
}
Write-Output 'Sessão temporária encerrada; endpoint e credencial revogados com o receptor.'
