param(
    [Parameter(Mandatory = $true)]
    [string]$ProblemFile
)

$ErrorActionPreference = "Stop"
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$PromptPath = Join-Path $ProjectRoot ".ai\prompts\diagnose-problem.md"

if (-not (Test-Path $PromptPath)) {
    throw "Prompt nao encontrado: $PromptPath"
}

if (-not (Test-Path $ProblemFile)) {
    throw "Arquivo de problema nao encontrado: $ProblemFile"
}

$ProblemPath = Resolve-Path $ProblemFile

Push-Location $ProjectRoot
try {
    $prompt = Get-Content -Raw $PromptPath
    $problem = Get-Content -Raw $ProblemPath
    codex exec "$prompt`n`nProblema recebido:`n`n$problem"
}
finally {
    Pop-Location
}
