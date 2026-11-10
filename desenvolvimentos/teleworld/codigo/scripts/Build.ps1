param(
    [Parameter(Mandatory = $true)]
    [string]$FlowApiJar
)
$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path $PSScriptRoot -Parent
$socketDir = Join-Path $projectRoot '.gradle\sockets'
New-Item -ItemType Directory -Force -Path $socketDir | Out-Null
$originalOptions = $env:JAVA_TOOL_OPTIONS
Push-Location $projectRoot
try {
    $env:JAVA_TOOL_OPTIONS = ($originalOptions + ' -Djdk.net.unixdomain.tmpdir="' + $socketDir + '"').Trim()
    & .\gradlew.bat clean build "-PflowApiJar=$FlowApiJar"
    if ($LASTEXITCODE -ne 0) { throw "Gradle falhou com codigo $LASTEXITCODE" }
} finally {
    $env:JAVA_TOOL_OPTIONS = $originalOptions
    Pop-Location
}
