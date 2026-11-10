$ErrorActionPreference='Stop'
$path=Join-Path (Split-Path $PSScriptRoot -Parent) 'config/private/desk-skala/session.json'
if(!(Test-Path $path)){return}
$session=Get-Content $path -Raw|ConvertFrom-Json
$owned=Get-Process -Id $session.pid -ErrorAction SilentlyContinue
if($owned -and $owned.StartTime.ToUniversalTime().Ticks -eq ([DateTimeOffset]$session.started).UtcTicks){Stop-Process -Id $owned.Id}
'Central local encerrada; dados preservados.'
