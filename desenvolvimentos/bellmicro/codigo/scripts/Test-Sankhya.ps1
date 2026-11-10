param(
    [Parameter(Mandatory=$true)][string]$DependenciesDir,
    [string]$VersionDir = ''
)
$ErrorActionPreference = 'Stop'
if (-not (Test-Path -LiteralPath $DependenciesDir -PathType Container)) { throw 'SDK ausente.' }
Push-Location (Split-Path $PSScriptRoot -Parent)
try {
    New-Item -ItemType Directory -Force 'build/sdk' | Out-Null
    $sdkClasspath = (Join-Path $DependenciesDir '*') + [IO.Path]::PathSeparator + (Join-Path $DependenciesDir 'Jar/*')
    if ($VersionDir) {
        $versionPath = Join-Path $DependenciesDir $VersionDir
        if (-not (Test-Path -LiteralPath $versionPath -PathType Container)) { throw 'Versao do SDK ausente.' }
        $sdkClasspath = (Join-Path $versionPath '*') + [IO.Path]::PathSeparator + $sdkClasspath
    }
    $sdkSources = @(Get-ChildItem -LiteralPath 'src','sankhya/src' -Recurse -Filter '*.java' | ForEach-Object FullName)
    & javac --release 8 -proc:none -encoding UTF-8 -cp $sdkClasspath -d build/sdk $sdkSources
    if ($LASTEXITCODE -ne 0) { throw 'Falha ao compilar integracao Sankhya.' }
    Copy-Item -Path 'src/main/resources/*' -Destination 'build/sdk' -Recurse -Force
    Write-Output 'Compilacao SDK aprovada; nao comprova instalacao ou homologacao.'
} finally { Pop-Location }
