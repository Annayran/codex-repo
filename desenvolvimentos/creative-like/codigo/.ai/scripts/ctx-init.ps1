param(
    [string]$ExtraPromptFile = ""
)

$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$prompt = Join-Path $root ".ai\prompts\init-context.md"

if (!(Test-Path $prompt)) { throw "Prompt nao encontrado: $prompt" }
if ($ExtraPromptFile -and !(Test-Path $ExtraPromptFile)) { throw "Arquivo adicional nao encontrado: $ExtraPromptFile" }

$text = Get-Content -Path $prompt -Raw -Encoding UTF8
if ($ExtraPromptFile) { $text += "`n`n" + (Get-Content -Path $ExtraPromptFile -Raw -Encoding UTF8) }

Push-Location $root
try {
    $text | codex exec -
} finally {
    Pop-Location
}

