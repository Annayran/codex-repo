Set-StrictMode -Version Latest

function Get-ZayaEngineeringRoot {
    if ($env:ZAYA_ENGINEERING_HOME) {
        return (Resolve-Path -LiteralPath $env:ZAYA_ENGINEERING_HOME).Path
    }
    return (Split-Path -Parent $PSScriptRoot)
}

function Get-ZayaDictionaryRoot {
    param([string]$Version = '4.35b815')
    $root = Get-ZayaEngineeringRoot
    $dictionary = Join-Path $root "sankhya\data-dictionary\$Version"
    if (-not (Test-Path -LiteralPath $dictionary -PathType Container)) {
        throw "Dicionario Sankhya nao encontrado: $dictionary"
    }
    return $dictionary
}

function Write-ZayaResult {
    param(
        [object[]]$InputObject,
        [switch]$AsJson
    )
    if ($AsJson) {
        $InputObject | ConvertTo-Json -Depth 6
    } else {
        $InputObject
    }
}
