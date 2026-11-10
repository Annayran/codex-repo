param(
    [Parameter(Mandatory=$true)][string]$SourceFile,
    [Parameter(Mandatory=$true)][string]$Version,
    [Parameter(Mandatory=$true)][ValidateSet('demo-a','demo-b')][string]$Tenant,
    [Parameter(Mandatory=$true)][string]$Process,
    [Parameter(Mandatory=$true)][string]$Environment,
    [Parameter(Mandatory=$true)][switch]$Reviewed,
    [switch]$Simulated
)
$ErrorActionPreference = 'Stop'
if (-not $Reviewed) { throw 'Explicit reviewed import required' }
$sourcePath = (Resolve-Path -LiteralPath $SourceFile).Path
if ((Get-Item -LiteralPath $sourcePath).Length -gt 80000) { throw 'Select a smaller source excerpt' }
$content = Get-Content -LiteralPath $sourcePath -Raw -Encoding UTF8
if ($content.Length -gt 20000) { throw 'Maximum 20000 characters per document' }
$repoPath = Split-Path -Parent $PSScriptRoot
$manifestDir = Join-Path $repoPath 'knowledge'
$manifestPath = Join-Path $manifestDir 'reviewed.json'
$existing = @()
if (Test-Path -LiteralPath $manifestPath) { $existing = @(Get-Content -LiteralPath $manifestPath -Raw | ConvertFrom-Json) }
if ($existing.Count -ge 50) { throw 'Maximum 50 documents' }
$document = @{metadata=@{id=[guid]::NewGuid().ToString();source=$sourcePath;version=$Version;process=$Process;tenant=$Tenant;simulated=[bool]$Simulated;observation='Explicitly reviewed import; applicability must be verified per incident'};environment=$Environment;content=$content}
New-Item -ItemType Directory -Path $manifestDir -Force | Out-Null
ConvertTo-Json -InputObject @($existing + $document) -Depth 10 | Set-Content -LiteralPath $manifestPath -Encoding UTF8
Write-Output 'Reviewed manifest created. Configure NEXO_KNOWLEDGE_MANIFEST with its absolute path.'
