param(
    [Parameter(Mandatory = $true)]
    [string]$DemandFile
)

$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$prompt = Join-Path $root ".ai\prompts\implement-demand.md"

if (!(Test-Path $prompt)) { throw "Prompt nao encontrado: $prompt" }
if (!(Test-Path $DemandFile)) { throw "Arquivo de demanda nao encontrado: $DemandFile" }

$text = Get-Content -Path $prompt -Raw -Encoding UTF8
$text += "`n`nDemanda:`n" + (Get-Content -Path $DemandFile -Raw -Encoding UTF8)

Push-Location $root
try {
    $text | codex exec -
} finally {
    Pop-Location
}

