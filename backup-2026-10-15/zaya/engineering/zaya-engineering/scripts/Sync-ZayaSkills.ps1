[CmdletBinding(SupportsShouldProcess = $true)]
param(
    [string]$Source = (Join-Path (Split-Path -Parent $PSScriptRoot) '.agents\skills\zaya-sankhya-development'),
    [string]$Installed = (Join-Path ([Environment]::GetFolderPath('UserProfile')) '.agents\skills\zaya-sankhya-development')
)

$installer = Join-Path $PSScriptRoot 'Install-ZayaSkills.ps1'
& $installer -Source $Source -Installed $Installed -WhatIf:$WhatIfPreference
