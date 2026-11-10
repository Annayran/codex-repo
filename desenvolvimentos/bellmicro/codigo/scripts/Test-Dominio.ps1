$ErrorActionPreference = 'Stop'
Push-Location (Split-Path $PSScriptRoot -Parent)
try {
    New-Item -ItemType Directory -Force 'build/manual/main', 'build/manual/test' | Out-Null
    $mainSources = @(Get-ChildItem -LiteralPath 'src' -Recurse -Filter '*.java' | ForEach-Object FullName)
    & javac --release 8 -encoding UTF-8 -d build/manual/main $mainSources
    if ($LASTEXITCODE -ne 0) { throw 'Falha ao compilar dominio.' }
    Copy-Item -Path 'src/main/resources/*' -Destination 'build/manual/main' -Recurse -Force
    $testSources = @(Get-ChildItem -LiteralPath 'test' -Recurse -Filter '*.java' | ForEach-Object FullName)
    & javac --release 8 -encoding UTF-8 -cp build/manual/main -d build/manual/test $testSources
    if ($LASTEXITCODE -ne 0) { throw 'Falha ao compilar testes.' }
    $classpath = (Join-Path $PWD 'build/manual/main') + [IO.Path]::PathSeparator + (Join-Path $PWD 'build/manual/test')
    & java -cp $classpath br.com.zaya.belmicro.rateio.TesteDominio
    if ($LASTEXITCODE -ne 0) { throw 'Falha nos testes de dominio.' }
    & java -cp $classpath br.com.zaya.belmicro.rateio.TesteSimulacao
    if ($LASTEXITCODE -ne 0) { throw 'Falha nos testes de aplicacao.' }
    & java -cp $classpath br.com.zaya.belmicro.rateio.TesteJdbc
    if ($LASTEXITCODE -ne 0) { throw 'Falha nos testes JDBC.' }
    & java -cp $classpath br.com.zaya.belmicro.rateio.TestePersistencia
    if ($LASTEXITCODE -ne 0) { throw 'Falha nos testes de persistencia.' }
} finally {
    Pop-Location
}
