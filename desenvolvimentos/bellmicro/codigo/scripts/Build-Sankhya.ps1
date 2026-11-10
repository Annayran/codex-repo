param(
    [Parameter(Mandatory=$true)][string]$DependenciesDir,
    [string]$VersionDir = '',
    [switch]$TcpOnlyJdk
)
$ErrorActionPreference = 'Stop'
if (-not (Test-Path -LiteralPath $DependenciesDir -PathType Container)) { throw 'SDK ausente.' }
if ($VersionDir -and -not (Test-Path -LiteralPath (Join-Path $DependenciesDir $VersionDir) -PathType Container)) { throw 'Versao do SDK ausente.' }
$previousJdkOptions = $env:JDK_JAVA_OPTIONS
Push-Location (Split-Path $PSScriptRoot -Parent)
try {
    if ($TcpOnlyJdk) {
        # PipeImpl.createListener falls back to loopback TCP when the Unix listener cannot bind.
        # Keep this path absent; do not create it or change the JVM/Gradle versions.
        $tcpOnlyMarker = Join-Path (Get-Location) '.gradle/jdk-tcp-only/absent'
        if (Test-Path -LiteralPath $tcpOnlyMarker) { throw 'Marcador TCP deve permanecer inexistente.' }
        $env:JDK_JAVA_OPTIONS = ($previousJdkOptions + ' -Djdk.net.unixdomain.tmpdir="' + $tcpOnlyMarker + '"').Trim()
    }
    $buildArguments = @('clean','build','--offline','--no-daemon','--max-workers=1',('-PsankhyaDependenciesDir=' + (Resolve-Path -LiteralPath $DependenciesDir).Path))
    if ($VersionDir) { $buildArguments += '-PsankhyaVersionDir=' + $VersionDir }
    & .\gradlew.bat @buildArguments
    if ($LASTEXITCODE -ne 0) { throw 'Build Gradle falhou.' }
} finally {
    $env:JDK_JAVA_OPTIONS = $previousJdkOptions
    Pop-Location
}
