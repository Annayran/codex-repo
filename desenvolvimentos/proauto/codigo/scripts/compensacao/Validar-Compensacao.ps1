[CmdletBinding()]
param(
    [string]$DependenciesPath = 'C:\dev\zaya\platform\modulos-sankhya\Dependencias\Jar'
)
$ErrorActionPreference = 'Stop'
$taskRoot = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '..\..'))
$taskBuild = Join-Path $taskRoot ('build\compensacao-evento-hom\' + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Force -Path "$taskBuild\main", "$taskBuild\test" | Out-Null
# Lista restrita: evita carregar dependencias estranhas ao modulo e nao baixa/atualiza SDK.
$taskPatterns = @('jape-*.jar','mge-modelcore-*.jar','mgefin-model-*.jar','cuckoo.jar',
    'dwf-*.jar','mge-param-*.jar','sanutil-*.jar','sanws-*.jar','jdom.jar')
$taskJars = foreach ($taskPattern in $taskPatterns) {
    $taskMatches = @(Get-ChildItem -LiteralPath $DependenciesPath -Filter $taskPattern)
    if ($taskMatches.Count -ne 1) { throw "Esperado exatamente um JAR para $taskPattern em DependenciesPath." }
    $taskMatches[0].FullName
}
$taskCp = $taskJars -join ';'
$taskSources = @(Get-ChildItem (Join-Path $taskRoot 'src\main\java') -Filter '*.java' -Recurse | ForEach-Object FullName)
& javac --release 8 -encoding UTF-8 -cp $taskCp -d "$taskBuild\main" $taskSources
if ($LASTEXITCODE -ne 0) { throw 'Compilacao principal falhou.' }
$taskCache = Join-Path $taskRoot '.gradle-local\caches\modules-2\files-2.1'
$taskJunit = @(Get-ChildItem "$taskCache\junit\junit\4.13.2" -Filter '*.jar' -Recurse)
$taskHamcrest = @(Get-ChildItem "$taskCache\org.hamcrest\hamcrest-core\1.3" -Filter '*.jar' -Recurse)
if ($taskJunit.Count -ne 1 -or $taskHamcrest.Count -ne 1) { throw 'Cache local JUnit 4.13.2/Hamcrest 1.3 ausente ou ambiguo.' }
$taskTestCp = "$taskBuild\main;$($taskJunit[0].FullName);$($taskHamcrest[0].FullName);$DependenciesPath\jdom.jar"
$taskTests = @(Get-ChildItem (Join-Path $taskRoot 'src\test\java') -Filter '*.java' -Recurse | ForEach-Object FullName)
& javac --release 8 -encoding UTF-8 -cp $taskTestCp -d "$taskBuild\test" $taskTests
if ($LASTEXITCODE -ne 0) { throw 'Compilacao dos testes falhou.' }
& java -cp "$taskBuild\test;$taskTestCp" org.junit.runner.JUnitCore `
    br.com.zaya.proauto.compensacao.CompensacaoServiceTest `
    br.com.zaya.proauto.compensacao.ConfirmacaoNotaTest `
    br.com.zaya.proauto.compensacao.ControlePopupCompensacaoTest `
    br.com.zaya.proauto.filareserva.application.services.AtualizaProvisaoServiceTest
if ($LASTEXITCODE -ne 0) { throw 'Testes falharam.' }
# Somente as classes atuais da compensacao; nunca embutir SDK ou rotina de fila de reserva.
$taskPackage = 'br/com/zaya/proauto/compensacao'
$taskEntries = @(Get-ChildItem "$taskBuild\main\$taskPackage" -Filter '*.class' | ForEach-Object Name)
foreach ($taskEntry in $taskEntries) {
    $taskOwner = ($taskEntry -split '\$')[0] -replace '\.class$', ''
    if (-not (Test-Path (Join-Path $taskRoot "src/main/java/$taskPackage/$taskOwner.java"))) {
        throw 'Classes obsoletas no diretorio de build; usar um diretorio de build limpo antes de empacotar.'
    }
}
$taskArtifact = "$taskBuild\proauto-compensacao-credito-evento-hom.jar"
& jar --create --file $taskArtifact -C "$taskBuild\main" $taskPackage
if ($LASTEXITCODE -ne 0) { throw 'Empacotamento falhou.' }
Get-FileHash -LiteralPath $taskArtifact -Algorithm SHA256
Write-Output 'Artefato candidato. Nao comprova compatibilidade/integracao com Sankhya 4.36b139.'
