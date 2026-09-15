$repoRoot = Split-Path -Parent $PSScriptRoot
$scripts = Join-Path $repoRoot 'scripts'

Describe 'Zaya dictionary lookup' {
    It 'finds TGFCAB without opening the full dictionary' {
        $result = @(& (Join-Path $scripts 'Get-ZayaTable.ps1') TGFCAB)
        $result.Count | Should Be 1
        $result[0].TableName | Should Be 'TGFCAB'
    }

    It 'finds NUNOTA fields with a bounded result' {
        $result = @(& (Join-Path $scripts 'Get-ZayaField.ps1') NUNOTA -MaxResults 5)
        $result.Count | Should BeGreaterThan 0
        $result.Count | Should Not BeGreaterThan 5
    }

    It 'finds relations between TGFCAB and TGFITE' {
        $result = @(& (Join-Path $scripts 'Get-ZayaRelation.ps1') TGFCAB TGFITE)
        $result.Count | Should BeGreaterThan 0
    }

    It 'supports batch search' {
        $result = @(& (Join-Path $scripts 'Search-ZayaDictionary.ps1') -Query @('TGFCAB', 'NUNOTA') -MaxResults 10)
        $result.Count | Should BeGreaterThan 1
        $result.Count | Should Not BeGreaterThan 10
    }
}
