param([Parameter(Mandatory)][string]$SessionPath)
$ErrorActionPreference='Stop'
$allowed=[IO.Path]::GetFullPath((Join-Path (Split-Path $PSScriptRoot -Parent) 'target'))+[IO.Path]::DirectorySeparatorChar
$resolved=[IO.Path]::GetFullPath($SessionPath)
if(!$resolved.StartsWith($allowed,[StringComparison]::OrdinalIgnoreCase)){throw 'Sessão fora de target.'}
$session=Get-Content -LiteralPath $resolved -Raw | ConvertFrom-Json
foreach($kind in @('receiver','ollama')) {
    $ownedId=$session."${kind}Pid"; $started=$session."${kind}Started"
    if(!$ownedId -or !$started){continue}
    $ownedProcess=Get-Process -Id $ownedId -ErrorAction SilentlyContinue
    if($ownedProcess -and $ownedProcess.StartTime.ToUniversalTime().Ticks -eq ([DateTimeOffset]$started).UtcTicks){Stop-Process -Id $ownedId}
}
'Sessão local encerrada; dados preservados.'
