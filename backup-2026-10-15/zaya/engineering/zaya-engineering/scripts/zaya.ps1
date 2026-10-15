[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [ValidateSet('project', 'profile', 'table', 'field', 'relation', 'pattern', 'error')]
    [string]$Command,
    [Parameter(Position = 1, ValueFromRemainingArguments = $true)]
    [string[]]$Arguments
)

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$profileScript = Join-Path $root '.agents\skills\zaya-sankhya-development\scripts\Get-ZayaProjectProfile.ps1'

switch ($Command) {
    'project' {
        $projectPath = if ($Arguments.Count) { $Arguments[0] } else { (Get-Location).Path }
        & $profileScript -ProjectPath $projectPath -AsJson
    }
    'profile' {
        $projectPath = if ($Arguments.Count -and $Arguments[0] -ne 'write') {
            $Arguments[0]
        } else {
            (Get-Location).Path
        }
        if ($Arguments -contains 'write') {
            & $profileScript -ProjectPath $projectPath -WriteProfile
        } else {
            & $profileScript -ProjectPath $projectPath -AsJson
        }
    }
    'table' {
        if (-not $Arguments.Count) { throw 'Informe a tabela.' }
        & (Join-Path $PSScriptRoot 'Get-ZayaTable.ps1') -Name $Arguments[0]
    }
    'field' {
        if (-not $Arguments.Count) { throw 'Informe o campo.' }
        $table = if ($Arguments.Count -gt 1) { $Arguments[1] } else { '' }
        & (Join-Path $PSScriptRoot 'Get-ZayaField.ps1') -Name $Arguments[0] -Table $table
    }
    'relation' {
        if (-not $Arguments.Count) { throw 'Informe ao menos uma tabela.' }
        $target = if ($Arguments.Count -gt 1) { $Arguments[1] } else { '' }
        & (Join-Path $PSScriptRoot 'Get-ZayaRelation.ps1') -SourceTable $Arguments[0] -TargetTable $target
    }
    'pattern' {
        if (-not $Arguments.Count) { throw 'Informe o padrao.' }
        $map = @{
            java = 'standards\java.md'
            gradle = 'standards\gradle.md'
            jape = 'sankhya\transactions.md'
            sql = 'standards\sql.md'
            javascript = 'standards\html5-javascript.md'
            addon = 'sankhya\addon-studio-metadata.md'
            document = 'sankhya\document-generation.md'
        }
        $key = $Arguments[0].ToLowerInvariant()
        if (-not $map.ContainsKey($key)) { throw "Padrao desconhecido: $key" }
        Get-Item -LiteralPath (Join-Path $root $map[$key])
    }
    'error' {
        if (-not $Arguments.Count) { throw 'Informe o erro.' }
        Select-String -LiteralPath (Join-Path $root 'sankhya\common-errors.md') -Pattern $Arguments[0] -SimpleMatch
    }
}
