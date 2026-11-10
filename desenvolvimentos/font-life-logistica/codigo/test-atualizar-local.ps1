param([string]$SankhyaLib = 'C:\dev\zaya\platform\modulos-sankhya\Dependencias\4_35')
$ErrorActionPreference = 'Stop'
$testClasses = Join-Path $PSScriptRoot 'build/test-atualizar-local'
New-Item -ItemType Directory -Force -Path $testClasses | Out-Null
$sources = @(Get-ChildItem (Join-Path $PSScriptRoot 'src/test/java') -Recurse -Filter '*.java' | ForEach-Object FullName)
$production = Join-Path $PSScriptRoot 'dist/FileViewer-atualizado-v2.jar'
& javac --release 8 -proc:none -encoding UTF-8 -cp "$production;$SankhyaLib\*" -d $testClasses @sources
if ($LASTEXITCODE -ne 0) { throw 'Falha ao compilar testes.' }
& java -Xverify:all -cp "$testClasses;$production;$SankhyaLib\*" AtualizarLocalTest
if ($LASTEXITCODE -ne 0) { throw 'Falha nos testes.' }
