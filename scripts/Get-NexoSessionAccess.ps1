param([Parameter(Mandatory)][string]$SessionPath,[switch]$CopyPassword)
$ErrorActionPreference='Stop'
$root=Split-Path $PSScriptRoot -Parent
$allowed=[IO.Path]::GetFullPath((Join-Path $root 'config/private'))+[IO.Path]::DirectorySeparatorChar
$resolved=[IO.Path]::GetFullPath($SessionPath)
if(!$resolved.StartsWith($allowed,[StringComparison]::OrdinalIgnoreCase)){throw 'Sessão fora do diretório privado.'}
$session=Get-Content -LiteralPath $resolved -Raw | ConvertFrom-Json
if([DateTimeOffset]::UtcNow -ge [DateTimeOffset]$session.expiresAt){throw 'Sessão expirada.'}
$properties=@{}
Get-Content -LiteralPath (Join-Path (Split-Path $resolved) 'resources/nexo-temporary-session.properties') | ForEach-Object {
    $parts=$_ -split '=',2; if($parts.Count -eq 2){$properties[$parts[0]]=$parts[1]}
}
if($CopyPassword){Set-Clipboard -Value $properties['password']; Write-Host 'Senha temporária copiada. Cole no campo da tela e limpe a área de transferência depois.'}
$central=Get-Content (Join-Path (Split-Path $resolved) 'central.properties')
$portLine=$central | Where-Object {$_ -match '^quarkus.http.port='} | Select-Object -First 1
$portValue=($portLine -split '=',2)[1]
[pscustomobject]@{LocalURL="http://127.0.0.1:$portValue/nexo.html";User=$properties['principal'];ExpiresAt=$session.expiresAt}
