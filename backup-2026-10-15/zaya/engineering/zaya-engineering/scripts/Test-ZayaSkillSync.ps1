[CmdletBinding()]
param(
    [string]$Source = (Join-Path (Split-Path -Parent $PSScriptRoot) '.agents\skills\zaya-sankhya-development'),
    [string]$Installed = (Join-Path ([Environment]::GetFolderPath('UserProfile')) '.agents\skills\zaya-sankhya-development'),
    [switch]$FailOnDifference
)

$ErrorActionPreference = 'Stop'

function Get-DirectoryFingerprint {
    param([string]$Root)
    if (-not (Test-Path -LiteralPath $Root -PathType Container)) { return '' }
    $manifest = Get-ChildItem -LiteralPath $Root -Recurse -File |
        Sort-Object FullName |
        ForEach-Object {
            $relative = $_.FullName.Substring($Root.Length).TrimStart('\')
            $relative + '=' + (Get-FileHash -Algorithm SHA256 -LiteralPath $_.FullName).Hash
        }
    $sha = [System.Security.Cryptography.SHA256]::Create()
    try {
        $bytes = [System.Text.Encoding]::UTF8.GetBytes(($manifest -join [Environment]::NewLine))
        return ([BitConverter]::ToString($sha.ComputeHash($bytes))).Replace('-', '')
    } finally {
        $sha.Dispose()
    }
}

$sourceHash = Get-DirectoryFingerprint -Root $Source
$installedHash = Get-DirectoryFingerprint -Root $Installed
$status = if ($sourceHash -and $sourceHash -eq $installedHash) { 'Synchronized' } else { 'Different' }

[PSCustomObject]@{
    Source = $Source
    SourceHash = $sourceHash
    Installed = $Installed
    InstalledHash = $installedHash
    Status = $status
}

if ($FailOnDifference -and $status -ne 'Synchronized') { exit 1 }
