param([string]$Sdk = 'C:/dev/zy-tools/modulos-sankhya/Dependencias', [switch]$UnitOnly)
$ErrorActionPreference = 'Stop'
$project = Split-Path $PSScriptRoot -Parent
$output = Join-Path $project ('build/homologacao-' + (Get-Date -Format 'yyyyMMdd-HHmmss'))
$classes = Join-Path $output 'classes'
$tests = Join-Path $output 'tests'
New-Item -ItemType Directory -Path $classes,$tests -Force | Out-Null
$sdkJars = @('Jar','4_35','jrepository','platina') | ForEach-Object {
    Get-ChildItem (Join-Path $Sdk $_) -Filter '*.jar' -Recurse -File
}
$cache = Join-Path $env:USERPROFILE '.gradle/caches/modules-2/files-2.1'
$jackson = Get-ChildItem (Join-Path $cache 'com.fasterxml.jackson.core') -Filter '*2.17.0.jar' -Recurse -File
$junit = @('org.junit.jupiter','org.junit.platform','org.opentest4j','org.apiguardian') | ForEach-Object {
    Get-ChildItem (Join-Path $cache $_) -Filter '*.jar' -Recurse -File
}
$cp = (($sdkJars + $jackson | Select-Object -ExpandProperty FullName -Unique) -join ';')
function Compile-Sources($sources,$destination,$classpath,$name) {
    $argfile = Join-Path $output ($name + '.args')
    $lines = @('--release','8','-proc:none','-encoding','UTF-8','-cp',('"'+$classpath.Replace('\','/')+'"'),'-d',('"'+$destination.Replace('\','/')+'"'))
    $lines += $sources | ForEach-Object { '"'+$_.FullName.Replace('\','/')+'"' }
    $lines | Set-Content -LiteralPath $argfile -Encoding UTF8
    & javac ('@'+$argfile)
    if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $name" }
}
Compile-Sources (Get-ChildItem (Join-Path $project 'src') -Filter '*.java' -Recurse -File) $classes $cp 'main'
$testcp = $classes+';'+$cp+';'+(($junit | Select-Object -ExpandProperty FullName -Unique) -join ';')
$testSources = @(Get-ChildItem (Join-Path $project 'test/br/com/zaya/mahospitalar/genesis') -Filter '*.java' -Recurse -File)
$testSources += Get-Item (Join-Path $PSScriptRoot 'RunGenesisTests.java')
Compile-Sources $testSources $tests $testcp 'test'
Push-Location $project
try {
    $runArgs = Join-Path $output 'run-tests.args'
    $arguments = @('-cp',('"'+($tests+';'+$testcp).Replace('\','/')+'"'),'RunGenesisTests')
    if ($UnitOnly) { $arguments += 'unit-only' }
    $arguments | Set-Content -LiteralPath $runArgs -Encoding UTF8
    & java ('@'+$runArgs)
    if ($LASTEXITCODE -ne 0) { throw 'Tests failed; no homologation package generated.' }
} finally { Pop-Location }
$jar = Join-Path $output 'MAHospitalar.jar'
Get-ChildItem (Join-Path $project 'src') -Recurse -File | Where-Object { $_.Extension -ne '.java' } | ForEach-Object {
    $relative = $_.FullName.Substring((Join-Path $project 'src').Length).TrimStart('\','/')
    $target = Join-Path $classes $relative
    New-Item -ItemType Directory -Path (Split-Path $target -Parent) -Force | Out-Null
    Copy-Item -LiteralPath $_.FullName -Destination $target
}
& jar cf $jar -C $classes .
if ($LASTEXITCODE -ne 0) { throw 'JAR packaging failed' }
$stage = Join-Path $output 'package'
$dashboard = Join-Path $stage 'webapp/genesis'
New-Item -ItemType Directory -Path $dashboard -Force | Out-Null
$web = Join-Path $project 'webapp/genesis'
$jsp = Get-Content (Join-Path $web 'index.jsp') -Raw
$css = Get-Content (Join-Path $web 'assets/genesis.css') -Raw
$js = Get-Content (Join-Path $web 'assets/genesis.js') -Raw
$diagnosis = Get-Content (Join-Path $web 'assets/diagnosis.js') -Raw
$jsp = $jsp.Replace('<script src="assets/diagnosis.js"></script>',"<script type=`"text/javascript`">`n$diagnosis`n</script>")
$jsp = $jsp.Replace('<link rel="stylesheet" href="assets/genesis.css">',"<style type=`"text/css`">`n$css`n</style>")
$jsp = $jsp.Replace('<script src="assets/genesis.js"></script>',"<script type=`"text/javascript`">`n$js`n</script>")
$jsp | Set-Content (Join-Path $dashboard 'index.jsp') -Encoding UTF8
Compress-Archive -LiteralPath (Join-Path $dashboard 'index.jsp') -DestinationPath (Join-Path $output 'MAHospitalar-Genesis-Dashboard-JSP.zip')
New-Item -ItemType Directory -Path (Join-Path $stage 'java'),(Join-Path $stage 'docs/genesis'),(Join-Path $stage 'sql/genesis') -Force | Out-Null
Copy-Item $jar (Join-Path $stage 'java/MAHospitalar.jar')
@('homologacao-consolidada.md','arquitetura.md','configuracao.md','implantacao-consolidada.md',
  'cadastro-manual-cancelamento.md','cancelamento-notas.md','catalogo-estoques.md',
  'base-unica-post-get.md','arquitetura.html','rastreabilidade-controles.md','fabricante-referencia-id.md','estoque-disponivel-identidade-hsp.md','modelo-hsp-migracao.md','diagnostico-tratamento-20261021.md','melhoria-selecao-m1-m3.md') | ForEach-Object {
    Copy-Item (Join-Path $project "docs/genesis/$_") (Join-Path $stage 'docs/genesis')
}
@('CADASTRO_MANUAL.md','view','cancelamento','validar-base-unica-post-get.sql','rastreabilidade','estoque-disponivel','modelo-hsp','diagnostico-notas-120671-120872.sql') | ForEach-Object {
    Copy-Item (Join-Path $project "sql/genesis/$_") (Join-Path $stage 'sql/genesis') -Recurse
}
$packageName = if ($UnitOnly) { 'MAHospitalar-Genesis-Candidato-Homologacao.zip' } else { 'MAHospitalar-Genesis-Homologacao.zip' }
Compress-Archive -Path (Join-Path $stage '*') -DestinationPath (Join-Path $output $packageName)
Get-FileHash $jar -Algorithm SHA256
Write-Output "Package: $output"
