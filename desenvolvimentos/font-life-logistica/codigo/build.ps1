param(
    [string]$SankhyaLib = 'C:\dev\zaya\platform\modulos-sankhya\Dependencias\4_35'
)
$ErrorActionPreference = 'Stop'
if (-not (Test-Path -LiteralPath $SankhyaLib -PathType Container)) {
    throw 'Informe -SankhyaLib com o diretorio das bibliotecas Sankhya.'
}
$classes = Join-Path $PSScriptRoot 'build/classes'
$dist = Join-Path $PSScriptRoot 'dist'
New-Item -ItemType Directory -Force -Path $classes, $dist | Out-Null
$source = Join-Path $PSScriptRoot 'src/main/java/br/com/zaya/actions/CapturandoJar.java'
& javac --release 8 -proc:none -encoding UTF-8 -cp (Join-Path $SankhyaLib '*') -d $classes $source
if ($LASTEXITCODE -ne 0) { throw 'Falha na compilacao.' }
& jar cf (Join-Path $dist 'capturando-jar.jar') -C $classes br/com/zaya/actions/CapturandoJar.class
if ($LASTEXITCODE -ne 0) { throw 'Falha no empacotamento.' }
Write-Output (Join-Path $dist 'capturando-jar.jar')
