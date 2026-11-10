param(
    [Parameter(Mandatory = $true)]
    [string]$DemandFile
)

$ErrorActionPreference = "Stop"
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$PromptPath = Join-Path $ProjectRoot ".ai\prompts\implement-demand.md"

if (-not (Test-Path $PromptPath)) {
    throw "Prompt nao encontrado: $PromptPath"
}

if (-not (Test-Path $DemandFile)) {
    throw "Arquivo de demanda nao encontrado: $DemandFile"
}

$DemandPath = Resolve-Path $DemandFile

Push-Location $ProjectRoot
try {
    $prompt = Get-Content -Raw $PromptPath
    $demand = Get-Content -Raw $DemandPath
    codex exec "$prompt`n`nDemanda recebida:`n`n$demand"
}
finally {
    Pop-Location
}
