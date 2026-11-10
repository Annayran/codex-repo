param([Parameter(Mandatory)][string]$SessionPath,[switch]$AtExpiry)
$ErrorActionPreference='Stop'
$allowed=[IO.Path]::GetFullPath((Join-Path (Split-Path $PSScriptRoot -Parent) 'config/private'))+[IO.Path]::DirectorySeparatorChar
$resolved=[IO.Path]::GetFullPath($SessionPath)
if(!$resolved.StartsWith($allowed,[StringComparison]::OrdinalIgnoreCase)){throw 'Sessão fora do diretório privado.'}
$session=Get-Content -LiteralPath $resolved -Raw|ConvertFrom-Json
if($AtExpiry){while([DateTimeOffset]::UtcNow -lt [DateTimeOffset]$session.expiresAt){$remaining=([DateTimeOffset]$session.expiresAt)-[DateTimeOffset]::UtcNow;Start-Sleep -Milliseconds ([math]::Min(30000,[math]::Max(1,$remaining.TotalMilliseconds)))}}
foreach($entry in $session.processes){$owned=Get-Process -Id $entry.id -ErrorAction SilentlyContinue;if($owned -and $owned.StartTime.ToUniversalTime().Ticks -eq ([DateTimeOffset]$entry.startedAt).UtcTicks){Stop-Process -Id $owned.Id}}
'Ingresso e túnel do coletor encerrados; central local preservada.'
