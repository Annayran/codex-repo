param(
    [string]$NotesFile = ""
)

$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$prompt = Join-Path $root ".ai\prompts\update-context.md"

if (!(Test-Path $prompt)) { throw "Prompt nao encontrado: $prompt" }
if ($NotesFile -and !(Test-Path $NotesFile)) { throw "Arquivo de notas nao encontrado: $NotesFile" }

$text = Get-Content -Path $prompt -Raw -Encoding UTF8
if ($NotesFile) { $text += "`n`nNotas adicionais:`n" + (Get-Content -Path $NotesFile -Raw -Encoding UTF8) }

Push-Location $root
try {
    $text | codex exec -
} finally {
    Pop-Location
}

