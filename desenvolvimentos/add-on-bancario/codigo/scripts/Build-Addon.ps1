[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)][string]$JavaHome,
    [string]$SnkLib,
    [switch]$SdkOficial,
    [string]$NfeJar,
    [string]$PythonExe = 'python3',
    [string]$NodeExe = 'node',
    [string]$IdentityFile,
    [switch]$GerarPacote,
    [switch]$ValidarSemIdentidade,
    [switch]$Offline,
    [switch]$SemProxy,
    [switch]$IpcTcp,
    [switch]$Smoke,
    [string]$BrowserExe,
    [string]$PlaywrightModule
)
$ErrorActionPreference = 'Stop'
$raizBuild = Split-Path -Parent $PSScriptRoot
$JavaHome = [IO.Path]::GetFullPath($JavaHome)
if ($SnkLib) { $SnkLib = [IO.Path]::GetFullPath($SnkLib) }
if ($NfeJar) { $NfeJar = [IO.Path]::GetFullPath($NfeJar) }
$javaExe = Join-Path $JavaHome 'bin/java.exe'
if (-not (Test-Path -LiteralPath $javaExe -PathType Leaf)) { throw 'JDK informado não localizado' }
$versaoJava = (& $javaExe -version 2>&1 | Out-String)
if ($versaoJava -notmatch 'version "17[\."]') { throw 'Execute o build com JDK 17; o produto gera bytecode Java 8' }
if ($SdkOficial -and $SnkLib) { throw 'Escolha -SnkLib ou -SdkOficial' }
if (-not $SdkOficial -and -not $SnkLib -and -not $env:SNK_LIB) {
    throw 'Informe -SnkLib com 4_36/api_outros ou selecione explicitamente -SdkOficial'
}
if ($IdentityFile -and -not (Test-Path -LiteralPath $IdentityFile -PathType Leaf)) {
    throw 'Arquivo de identidade informado não localizado'
}
if ($IdentityFile) { $IdentityFile = (Resolve-Path -LiteralPath $IdentityFile).ProviderPath }
$identidade = if ($IdentityFile) { $IdentityFile } elseif ($env:ZYCB_IDENTITY_FILE) {
    $env:ZYCB_IDENTITY_FILE
} else { Join-Path $raizBuild '.local/addon-identity.gradle' }
if (-not [IO.Path]::IsPathRooted($identidade)) {
    $identidade = [IO.Path]::GetFullPath((Join-Path $raizBuild $identidade))
}
if ($GerarPacote -and -not (Test-Path -LiteralPath $identidade -PathType Leaf) -and -not $env:ZYCB_APP_KEY) {
    throw 'Configure a identidade autorizada ou ZYCB_APP_KEY antes de gerar o pacote; não grave a chave no código'
}
if ($GerarPacote -and $ValidarSemIdentidade) { throw 'O perfil sem identidade não pode gerar pacote' }
$somenteCodigo = $ValidarSemIdentidade -or (-not (Test-Path -LiteralPath $identidade -PathType Leaf) -and -not $env:ZYCB_APP_KEY)
if ($Smoke -and (-not $BrowserExe -or -not $PlaywrightModule)) {
    throw 'O smoke visual exige -BrowserExe e -PlaywrightModule disponíveis localmente'
}

$nomesAmbiente = @('JAVA_HOME','GRADLE_USER_HOME','SNK_LIB','ZYCB_NFE_JAR','ZYCB_PYTHON','ZYCB_IDENTITY_FILE',
    'AWS_EC2_METADATA_DISABLED','HTTP_PROXY','HTTPS_PROXY','ALL_PROXY','JAVA_TOOL_OPTIONS',
    'CHROME_BIN','PLAYWRIGHT_MODULE','ZYCB_UI_OUTPUT')
$ambienteAnterior = @{}
foreach ($nome in $nomesAmbiente) {
    $ambienteAnterior[$nome] = [Environment]::GetEnvironmentVariable($nome, 'Process')
}
Push-Location -LiteralPath $raizBuild
try {
    # KSP no Windows pode escapar incorretamente caminhos Unicode do JDK.
    $fsWindows = New-Object -ComObject Scripting.FileSystemObject
    $jdkCurto = $fsWindows.GetFolder($JavaHome).ShortPath
    $env:JAVA_HOME = if ($jdkCurto) { $jdkCurto } else { $JavaHome }
    $env:GRADLE_USER_HOME = Join-Path $raizBuild 'out/addon-gradle-home'
    $env:AWS_EC2_METADATA_DISABLED = 'true'
    $env:ZYCB_PYTHON = $PythonExe
    if ($IdentityFile -or $env:ZYCB_IDENTITY_FILE) { $env:ZYCB_IDENTITY_FILE = $identidade }
    if ($SdkOficial) { $env:SNK_LIB = $null } elseif ($SnkLib) { $env:SNK_LIB = $SnkLib }
    if ($NfeJar) { $env:ZYCB_NFE_JAR = $NfeJar }
    if ($SemProxy) {
        foreach ($nome in @('HTTP_PROXY','HTTPS_PROXY','ALL_PROXY')) {
            [Environment]::SetEnvironmentVariable($nome, $null, 'Process')
        }
    }
    if ($IpcTcp) {
        # O JDK usa TCP como fallback se o diretório AF_UNIX não existir.
        # Contorno apenas para hosts Windows que recusam AF_UNIX; não afeta TLS do banco.
        $raizCurta = $fsWindows.GetFolder($raizBuild).ShortPath
        $ipcIndisponivel = Join-Path $raizCurta 'out/sem-unix-sockets'
        if (Test-Path -LiteralPath $ipcIndisponivel) { throw 'O caminho reservado ao fallback IPC deve estar ausente' }
        $env:JAVA_TOOL_OPTIONS = ($env:JAVA_TOOL_OPTIONS + ' -Djdk.net.unixdomain.tmpdir="' + $ipcIndisponivel + '"').Trim()
    }
    & $PythonExe scripts/check_project.py
    if ($LASTEXITCODE -ne 0) { throw 'Verificação de estrutura/XML falhou' }
    & $NodeExe scripts/frontend.test.cjs
    if ($LASTEXITCODE -ne 0) { throw 'Verificação dos parâmetros frontend falhou' }
    & $NodeExe --check vc/src/main/webapp/html5/zycb/zycb.js
    if ($LASTEXITCODE -ne 0) { throw 'Verificação sintática JavaScript falhou' }

    $argumentos = @('--no-daemon', '--console=plain')
    if ($Offline) { $argumentos += '--offline' }
    if ($SdkOficial) { $argumentos += '-PzycbSdkOficial=true' }
    if ($somenteCodigo) {
        Write-Output 'Perfil sem identidade: validação de código e testes, sem gerar pacote ou descritores de distribuição.'
        $argumentos += '-PzycbValidacaoSemIdentidade=true'
    }
    $argumentos += ':model:test'
    if ($GerarPacote) { $argumentos += 'gerarAddon' } elseif (-not $somenteCodigo) { $argumentos += @(':model:buildJar', ':vc:buildWar') }
    & ./gradlew.bat @argumentos
    if ($LASTEXITCODE -ne 0) { throw 'Build/testes do add-on falharam' }
    if ($Smoke) {
        $env:CHROME_BIN = $BrowserExe
        $env:PLAYWRIGHT_MODULE = $PlaywrightModule
        $env:ZYCB_UI_OUTPUT = Join-Path $raizBuild 'out/validacao/ui'
        New-Item -ItemType Directory -Path $env:ZYCB_UI_OUTPUT -Force | Out-Null
        & $NodeExe scripts/ui_smoke.cjs
        if ($LASTEXITCODE -ne 0) { throw 'Smoke visual falhou' }
    }
} finally {
    Pop-Location
    foreach ($nome in $nomesAmbiente) {
        [Environment]::SetEnvironmentVariable($nome, $ambienteAnterior[$nome], 'Process')
    }
}
