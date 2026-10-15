[CmdletBinding()]
param(
    [Parameter(Position = 0)]
    [string]$ProjectPath = (Get-Location).Path,

    [switch]$WriteProfile,

    [switch]$AsJson
)

$ErrorActionPreference = 'Stop'

$implementation = Join-Path (Split-Path -Parent $PSScriptRoot) `
    '.agents\skills\zaya-sankhya-development\scripts\Get-ZayaProjectProfile.ps1'

if (-not (Test-Path -LiteralPath $implementation -PathType Leaf)) {
    throw "Gerador canônico não encontrado: $implementation"
}

& $implementation -ProjectPath $ProjectPath -WriteProfile:$WriteProfile -AsJson:$AsJson
