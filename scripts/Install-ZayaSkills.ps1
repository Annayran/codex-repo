[CmdletBinding(SupportsShouldProcess = $true)]
param(
    [string]$Source = (Join-Path (Split-Path -Parent $PSScriptRoot) '.agents\skills\zaya-sankhya-development'),
    [string]$Installed = (Join-Path ([Environment]::GetFolderPath('UserProfile')) '.agents\skills\zaya-sankhya-development')
)

$ErrorActionPreference = 'Stop'
if (-not (Test-Path -LiteralPath $Source -PathType Container)) {
    throw "Skill fonte nao encontrada: $Source"
}

$changed = 0
$unchanged = 0
foreach ($file in Get-ChildItem -LiteralPath $Source -Recurse -File) {
    $relative = $file.FullName.Substring($Source.Length).TrimStart('\')
    $target = Join-Path $Installed $relative
    $same = (Test-Path -LiteralPath $target -PathType Leaf) -and
        ((Get-FileHash -Algorithm SHA256 -LiteralPath $file.FullName).Hash -eq
         (Get-FileHash -Algorithm SHA256 -LiteralPath $target).Hash)
    if ($same) {
        $unchanged++
        continue
    }
    if ($PSCmdlet.ShouldProcess($target, 'Instalar ou atualizar skill Zaya')) {
        $parent = Split-Path -Parent $target
        if (-not (Test-Path -LiteralPath $parent)) {
            New-Item -ItemType Directory -Path $parent -Force | Out-Null
        }
        Copy-Item -LiteralPath $file.FullName -Destination $target -Force
        $changed++
    }
}

[PSCustomObject]@{
    Source = $Source
    Installed = $Installed
    Changed = $changed
    Unchanged = $unchanged
}
