[CmdletBinding()]
param(
    [Parameter(Mandatory=$true)][string]$DependenciesPath,
    [Parameter(Mandatory=$true)][string]$JunitJar,
    [Parameter(Mandatory=$true)][string]$HamcrestJar
)
$ErrorActionPreference='Stop'
$taskRoot=[IO.Path]::GetFullPath((Join-Path $PSScriptRoot '..'))
$taskBuild=Join-Path $taskRoot ('build/' + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Force -Path "$taskBuild/main","$taskBuild/test" | Out-Null
$taskPatterns=@('jape-*.jar','mge-modelcore-*.jar','mgefin-model-*.jar','dwf-*.jar','mge-param-*.jar','sanutil-*.jar','sanws-*.jar','jdom.jar')
$taskJars=foreach($taskPattern in $taskPatterns){
    $taskMatches=@(Get-ChildItem -LiteralPath $DependenciesPath -Filter $taskPattern)
    if($taskMatches.Count -ne 1){throw "Esperado um JAR para $taskPattern"}
    $taskMatches[0].FullName
}
$taskCp=$taskJars -join [IO.Path]::PathSeparator
$taskSources=@(Get-ChildItem "$taskRoot/src/main/java" -Filter '*.java' -Recurse | ForEach-Object FullName)
& javac --release 8 -encoding UTF-8 -cp $taskCp -d "$taskBuild/main" $taskSources
if($LASTEXITCODE -ne 0){throw 'Falha na compilacao'}
$taskTestCp=@("$taskBuild/main",$JunitJar,$HamcrestJar,(Join-Path $DependenciesPath 'jdom.jar')) -join [IO.Path]::PathSeparator
$taskTests=@(Get-ChildItem "$taskRoot/src/test/java" -Filter '*.java' -Recurse | ForEach-Object FullName)
& javac --release 8 -encoding UTF-8 -cp $taskTestCp -d "$taskBuild/test" $taskTests
if($LASTEXITCODE -ne 0){throw 'Falha na compilacao dos testes'}
& java -cp ("$taskBuild/test" + [IO.Path]::PathSeparator + $taskTestCp) org.junit.runner.JUnitCore br.com.zaya.proauto.compensacao.CompensacaoServiceTest br.com.zaya.proauto.compensacao.ConfirmacaoNotaTest br.com.zaya.proauto.compensacao.ControlePopupCompensacaoTest
if($LASTEXITCODE -ne 0){throw 'Falha nos testes'}
$taskArtifact="$taskBuild/proauto-compensacao-credito-evento-hom.jar"
& jar --create --file $taskArtifact -C "$taskBuild/main" br/com/zaya/proauto/compensacao
if($LASTEXITCODE -ne 0){throw 'Falha no empacotamento'}
Get-FileHash -LiteralPath $taskArtifact -Algorithm SHA256
