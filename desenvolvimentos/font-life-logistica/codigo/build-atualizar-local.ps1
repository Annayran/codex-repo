param(
    [string]$OriginalJar = "$env:USERPROFILE\Downloads\FileViewer.jar",
    [string]$SankhyaLib = 'C:\dev\zaya\platform\modulos-sankhya\Dependencias\4_35'
)
$ErrorActionPreference = 'Stop'
$expectedHash = 'AFA34A118F3369DD49E995E76250CCAC934A0E4C8DF9BDD5360CDAE757154CE6'
if ((Get-FileHash -LiteralPath $OriginalJar -Algorithm SHA256).Hash -ne $expectedHash) {
    throw 'O JAR original difere do modulo analisado. Revise antes de recompilar.'
}
$classes = Join-Path $PSScriptRoot 'build/atualizar-local'
$dist = Join-Path $PSScriptRoot 'dist'
New-Item -ItemType Directory -Force -Path $classes, $dist | Out-Null
$source = Join-Path $PSScriptRoot 'src/main/java/br/com/sankhya/bhz/central/AtualizarLocal.java'
& javac --release 8 -proc:none -encoding UTF-8 -cp "$OriginalJar;$SankhyaLib\*" -d $classes $source
if ($LASTEXITCODE -ne 0) { throw 'Falha na compilacao.' }
$outputJar = Join-Path $dist 'FileViewer-atualizado-v2.jar'
Copy-Item -LiteralPath $OriginalJar -Destination $outputJar -Force
& jar uf $outputJar -C $classes br/com/sankhya/bhz/central/AtualizarLocal.class
if ($LASTEXITCODE -ne 0) { throw 'Falha no empacotamento.' }
Write-Output $outputJar
