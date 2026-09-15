$global:ZayaTestRepoRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
$global:ZayaTestProfileScript = Join-Path $global:ZayaTestRepoRoot '.agents\skills\zaya-sankhya-development\scripts\Get-ZayaProjectProfile.ps1'

Describe 'Zaya project discovery' {
    It 'does not classify zaya-engineering as Gradle because of templates' {
        $profile = (& $global:ZayaTestProfileScript -ProjectPath $global:ZayaTestRepoRoot -AsJson | Out-String) | ConvertFrom-Json
        $profile.classification | Should Be 'Documentacao ou modelo nao classificado'
        @($profile.build.files).Count | Should Be 0
    }

    Context 'real Gradle fixture' {
        It 'detects a real root Gradle project' {
            $fixture = Join-Path $global:ZayaTestRepoRoot ('.zaya\runtime\tests\profile-' + [Guid]::NewGuid().ToString('N'))
            try {
                New-Item -ItemType Directory -Path (Join-Path $fixture 'gradle\wrapper') -Force | Out-Null
                New-Item -ItemType Directory -Path (Join-Path $fixture 'src\main\java\br\com\zaya\test\actions') -Force | Out-Null
                Set-Content -LiteralPath (Join-Path $fixture 'build.gradle') -Encoding UTF8 -Value @(
                'plugins { id "java" }'
                'sourceCompatibility = JavaVersion.VERSION_1_8'
                'dependencies { compileOnly files("libs/sankhya.jar") }'
                )
                Set-Content -LiteralPath (Join-Path $fixture 'settings.gradle') -Encoding UTF8 -Value 'rootProject.name = "fixture"'
                Set-Content -LiteralPath (Join-Path $fixture 'gradle\wrapper\gradle-wrapper.properties') -Encoding UTF8 -Value 'distributionUrl=https://services.gradle.org/distributions/gradle-8.10-bin.zip'
                Set-Content -LiteralPath (Join-Path $fixture 'src\main\java\br\com\zaya\test\actions\ExampleAction.java') -Encoding UTF8 -Value @(
                'package br.com.zaya.test.actions;'
                'class ExampleAction { String table = "TGFCAB"; }'
                )
                $profile = (& $global:ZayaTestProfileScript -ProjectPath $fixture -AsJson | Out-String) | ConvertFrom-Json
                $profile.build.system | Should Be 'Gradle'
                $profile.classification | Should Be 'Java/Sankhya legado ou modular'
                (@($profile.build.files) -contains 'build.gradle') | Should Be $true
                $profile.code.actions | Should Be 1
                (@($profile.code.tables) -contains 'TGFCAB') | Should Be $true
            } finally {
                if (Test-Path -LiteralPath $fixture) { Remove-Item -LiteralPath $fixture -Recurse -Force }
            }
        }

        It 'writes and validates profile freshness' {
            $fixture = Join-Path $global:ZayaTestRepoRoot ('.zaya\runtime\tests\freshness-' + [Guid]::NewGuid().ToString('N'))
            try {
                New-Item -ItemType Directory -Path $fixture -Force | Out-Null
                Set-Content -LiteralPath (Join-Path $fixture 'build.gradle') -Encoding UTF8 -Value 'plugins { id "java" }'
                Set-Content -LiteralPath (Join-Path $fixture 'settings.gradle') -Encoding UTF8 -Value 'rootProject.name = "fixture"'
                & $global:ZayaTestProfileScript -ProjectPath $fixture -WriteProfile | Out-Null
                $fresh = (& $global:ZayaTestProfileScript -ProjectPath $fixture -AsJson | Out-String) | ConvertFrom-Json
                $fresh.profileFresh | Should Be $true
                Add-Content -LiteralPath (Join-Path $fixture 'build.gradle') -Value '// structural change'
                $stale = (& $global:ZayaTestProfileScript -ProjectPath $fixture -AsJson | Out-String) | ConvertFrom-Json
                $stale.profileFresh | Should Be $false
            } finally {
                if (Test-Path -LiteralPath $fixture) { Remove-Item -LiteralPath $fixture -Recurse -Force }
            }
        }
    }
}
