param(
    [string]$ExtraArgs = ""
)

$ErrorActionPreference = "Stop"
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$PromptPath = Join-Path $ProjectRoot ".ai\prompts\init-context.md"

if (-not (Test-Path $PromptPath)) {
    throw "Prompt nao encontrado: $PromptPath"
}

Push-Location $ProjectRoot
try {
    $prompt = Get-Content -Raw $PromptPath
    if ([string]::IsNullOrWhiteSpace($ExtraArgs)) {
        codex exec $prompt
    } else {
        codex exec "$prompt`n`n$ExtraArgs"
    }
}
finally {
    Pop-Location
}
