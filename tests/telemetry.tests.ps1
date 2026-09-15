$global:ZayaTelemetryTestRepoRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)

Describe 'Zaya local telemetry' {
    It 'writes only approved technical fields in an ignored directory' {
        $fixture = Join-Path $global:ZayaTelemetryTestRepoRoot ('.zaya\runtime\tests\telemetry-' + [Guid]::NewGuid().ToString('N'))
        try {
            New-Item -ItemType Directory -Path $fixture -Force | Out-Null
            $parameters = @{
                TaskType = 'JAVA'
                FilesSearched = 3
                FilesOpened = 2
                LibraryDocsLoaded = 1
                DictionaryQueries = 0
                ValidationStatus = 'passed'
                ProjectPath = $fixture
            }
            & (Join-Path $global:ZayaTelemetryTestRepoRoot 'scripts\Write-ZayaTelemetry.ps1') @parameters | Out-Null
            $eventPath = Join-Path $fixture '.zaya\telemetry\events.jsonl'
            $event = Get-Content -Raw -Encoding UTF8 -LiteralPath $eventPath | ConvertFrom-Json
            @($event.PSObject.Properties.Name).Count | Should Be 8
            $event.task_type | Should Be 'JAVA'
            ($event.PSObject.Properties.Name -contains 'prompt') | Should Be $false
            ($event.PSObject.Properties.Name -contains 'code') | Should Be $false
        } finally {
            if (Test-Path -LiteralPath $fixture) { Remove-Item -LiteralPath $fixture -Recurse -Force }
        }
    }
}
