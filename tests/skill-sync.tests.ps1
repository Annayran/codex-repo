$global:ZayaSyncTestRepoRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)

Describe 'Zaya skill synchronization' {
    It 'reports canonical and installed copies as synchronized' {
        $result = & (Join-Path $global:ZayaSyncTestRepoRoot 'scripts\Test-ZayaSkillSync.ps1')
        $result.Status | Should Be 'Synchronized'
        $result.SourceHash | Should Be $result.InstalledHash
    }
}
