$global:ZayaRoutingTestRepoRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
$global:ZayaRoutingTestSkill = Join-Path $global:ZayaRoutingTestRepoRoot '.agents\skills\zaya-sankhya-development\SKILL.md'
$global:ZayaRoutingTestMap = Join-Path $global:ZayaRoutingTestRepoRoot '.agents\skills\zaya-sankhya-development\references\library-routing.md'

Describe 'Zaya context routing' {
    It 'routes every supported task class' {
        $routingContent = Get-Content -Raw -Encoding UTF8 -LiteralPath $global:ZayaRoutingTestMap
        $classes = @('JAVA','SQL','DATABASE','JAPE','DOCUMENT','DASHBOARD','REPORT',
            'HTML5','JAVASCRIPT','ADDON','BUILD','DEPLOY','DEBUG','REVIEW')
        foreach ($class in $classes) {
            $routingContent | Should Match ([regex]::Escape($class))
        }
    }

    It 'documents the initial exploration budget' {
        $skillContent = Get-Content -Raw -Encoding UTF8 -LiteralPath $global:ZayaRoutingTestSkill
        $skillContent | Should Match 'three to five source files'
        $skillContent | Should Match 'at most two library references'
    }

    It 'limits reviewer scope to the diff' {
        $skillContent = Get-Content -Raw -Encoding UTF8 -LiteralPath $global:ZayaRoutingTestSkill
        $skillContent | Should Match 'git diff atual'
    }
}
