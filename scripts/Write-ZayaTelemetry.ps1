[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('JAVA','SQL','DATABASE','JAPE','DOCUMENT','DASHBOARD','REPORT',
        'HTML5','JAVASCRIPT','ADDON','BUILD','DEPLOY','DEBUG','REVIEW')]
    [string]$TaskType,
    [ValidateRange(0, 100000)]
    [int]$FilesSearched = 0,
    [ValidateRange(0, 100000)]
    [int]$FilesOpened = 0,
    [ValidateRange(0, 1000)]
    [int]$LibraryDocsLoaded = 0,
    [ValidateRange(0, 10000)]
    [int]$DictionaryQueries = 0,
    [ValidateSet('not-run','passed','failed')]
    [string]$BuildStatus = 'not-run',
    [ValidateSet('not-run','passed','failed')]
    [string]$ValidationStatus = 'not-run',
    [string]$ProjectPath = (Get-Location).Path
)

$ErrorActionPreference = 'Stop'
$root = (Resolve-Path -LiteralPath $ProjectPath).Path
$directory = Join-Path $root '.zaya\telemetry'
$path = Join-Path $directory 'events.jsonl'
if (-not (Test-Path -LiteralPath $directory)) {
    New-Item -ItemType Directory -Path $directory -Force | Out-Null
}
$event = [ordered]@{
    timestamp = (Get-Date).ToUniversalTime().ToString('o')
    task_type = $TaskType
    files_searched = $FilesSearched
    files_opened = $FilesOpened
    library_docs_loaded = $LibraryDocsLoaded
    dictionary_queries = $DictionaryQueries
    build_status = $BuildStatus
    validation_status = $ValidationStatus
}
Add-Content -LiteralPath $path -Encoding UTF8 -Value ($event | ConvertTo-Json -Compress)
Get-Item -LiteralPath $path
