param(
    [Parameter(Mandatory = $true)]
    [string]$ProblemFile
)

$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$prompt = Join-Path $root ".ai\prompts\diagnose-problem.md"

if (!(Test-Path $prompt)) { throw "Prompt nao encontrado: $prompt" }
if (!(Test-Path $ProblemFile)) { throw "Arquivo de problema nao encontrado: $ProblemFile" }

$text = Get-Content -Path $prompt -Raw -Encoding UTF8
$text += "`n`nProblema:`n" + (Get-Content -Path $ProblemFile -Raw -Encoding UTF8)

Push-Location $root
try {
    $text | codex exec -
} finally {
    Pop-Location
}

