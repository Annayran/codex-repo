param([string]$ExtensionsJar, [string]$ProvisioningDir)
$ErrorActionPreference = 'Stop'
$repositoryRoot = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '../..')).Path
$socketDir = Join-Path $repositoryRoot 'target'
New-Item -ItemType Directory -Path $socketDir -Force | Out-Null
$previousGradleHome = $env:GRADLE_USER_HOME
$previousJavaOptions = $env:JDK_JAVA_OPTIONS
try {
    $env:GRADLE_USER_HOME = Join-Path $socketDir 'gradle-user-home'
    if ($IsWindows -or $env:OS -eq 'Windows_NT') {
        $env:JDK_JAVA_OPTIONS = (($previousJavaOptions + ' -Djdk.net.unixdomain.tmpdir="' + $socketDir + '"').Trim())
    }
    $buildArguments = @('--no-daemon', '-p', $PSScriptRoot, 'build')
    if ($ExtensionsJar) { $buildArguments += '-PsankhyaExtensionsJar=' + (Resolve-Path -LiteralPath $ExtensionsJar).Path }
    if ($ProvisioningDir) { $buildArguments += '-PnexoProvisioningDir=' + (Resolve-Path -LiteralPath $ProvisioningDir).Path }
    & (Join-Path $PSScriptRoot 'gradlew.bat') @buildArguments
    if ($LASTEXITCODE -ne 0) { throw 'Gradle button build failed' }
    $artifactDir = if ($ProvisioningDir) { Join-Path (Split-Path (Resolve-Path -LiteralPath $ProvisioningDir).Path -Parent) 'build/libs' } else { Join-Path $PSScriptRoot 'build/libs' }
    Write-Output ('Button artifacts: ' + $artifactDir)
} finally {
    $env:GRADLE_USER_HOME = $previousGradleHome
    $env:JDK_JAVA_OPTIONS = $previousJavaOptions
}
