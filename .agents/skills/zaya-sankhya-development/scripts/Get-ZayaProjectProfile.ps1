[CmdletBinding()]
param(
    [Parameter(Position = 0)]
    [string]$ProjectPath = (Get-Location).Path,

    [switch]$WriteProfile,
    [switch]$AsJson,
    [switch]$FailOnWarning
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$resolvedProject = (Resolve-Path -LiteralPath $ProjectPath).Path
$gitRootOutput = @(& git -C $resolvedProject rev-parse --show-toplevel 2>$null)
if ($LASTEXITCODE -ne 0 -or $gitRootOutput.Count -eq 0) {
    throw "O diretorio nao pertence a um repositorio Git: $resolvedProject"
}

$gitRoot = [System.IO.Path]::GetFullPath([string]$gitRootOutput[0])
$scanRoot = [System.IO.Path]::GetFullPath($resolvedProject)
$branch = [string](& git -C $gitRoot branch --show-current)
$statusCount = @(& git -C $gitRoot status --short).Count
$gitHeadOutput = @(& git -C $gitRoot rev-parse --verify HEAD 2>$null)
$gitHead = if ($LASTEXITCODE -eq 0 -and $gitHeadOutput.Count -gt 0) {
    [string]$gitHeadOutput[0]
} else {
    ''
}

$allFiles = @(Get-ChildItem -LiteralPath $scanRoot -File -Recurse -ErrorAction SilentlyContinue |
    Where-Object {
        $relative = $_.FullName.Substring($scanRoot.Length).TrimStart('\')
        $relative -notmatch '^(templates|examples|tests\\fixtures)(\\|$)' -and
        $relative -notmatch '(^|\\)(build|out|bin|target|node_modules|\.gradle|\.git)(\\|$)'
    })

$buildFiles = @('build.gradle', 'build.gradle.kts' | ForEach-Object {
    $candidate = Join-Path $scanRoot $_
    if (Test-Path -LiteralPath $candidate -PathType Leaf) {
        Get-Item -LiteralPath $candidate
    }
})
$buildFileNames = @($buildFiles | ForEach-Object { $_.Name })
$settingsFiles = @('settings.gradle', 'settings.gradle.kts' | ForEach-Object {
    $candidate = Join-Path $scanRoot $_
    if (Test-Path -LiteralPath $candidate -PathType Leaf) {
        Get-Item -LiteralPath $candidate
    }
})
$wrapperProperties = @(
    $wrapperCandidate = Join-Path $scanRoot 'gradle\wrapper\gradle-wrapper.properties'
    if (Test-Path -LiteralPath $wrapperCandidate -PathType Leaf) {
        Get-Item -LiteralPath $wrapperCandidate
    }
)
$pomFile = Join-Path $scanRoot 'pom.xml'
$hasMaven = Test-Path -LiteralPath $pomFile -PathType Leaf
$javaFiles = @($allFiles | Where-Object {
    $_.Extension -eq '.java' -and $_.FullName -match '\\src\\main\\java\\'
})
$testFiles = @($allFiles | Where-Object {
    $_.FullName -match '\\src\\test\\' -and $_.Extension -in @('.java', '.kt', '.groovy')
})
$metadataFiles = @($allFiles | Where-Object {
    $_.Extension -eq '.xml' -and $_.FullName -match '\\datadictionary\\'
})
$databaseFiles = @($allFiles | Where-Object {
    $_.FullName -match '\\(database|dbscripts)\\'
})
$reportFiles = @($allFiles | Where-Object {
    $_.Extension -in @('.jrxml', '.jasper') -or
    $_.FullName -match '\\(report|reports|relatorio|relatorios)\\'
})

$buildContent = ($buildFiles | ForEach-Object {
    Get-Content -Raw -LiteralPath $_.FullName
}) -join "`n"
$settingsContent = ($settingsFiles | ForEach-Object {
    Get-Content -Raw -LiteralPath $_.FullName
}) -join "`n"

$hasModelModule = Test-Path -LiteralPath (Join-Path $scanRoot 'model')
$hasVcModule = Test-Path -LiteralPath (Join-Path $scanRoot 'vc')
$isAddonStudio = ($hasModelModule -and $settingsContent -match 'include\s+[''"]model[''"]') -or
    ($hasVcModule -and $settingsContent -match 'include\s+[''"]vc[''"]') -or
    ($buildContent -match 'snkmodule|br\.com\.sankhya\.studio')
$hasLegacyJava = $javaFiles.Count -gt 0 -and $buildFiles.Count -gt 0
$projectType = if ($isAddonStudio) {
    'Add-on Studio'
} elseif ($hasLegacyJava) {
    'Java/Sankhya legado ou modular'
} elseif ($buildFiles.Count -gt 0) {
    'Gradle sem codigo Java detectado'
} elseif ($hasMaven) {
    'Maven'
} else {
    'Documentacao ou modelo nao classificado'
}
$buildSystem = if ($buildFiles.Count -gt 0) {
    'Gradle'
} elseif ($hasMaven) {
    'Maven'
} else {
    'Nenhum'
}

$mvcFiles = @($javaFiles | Where-Object {
    $_.FullName -match '\\(controller|controllers|model|models|view|views)\\'
}).Count
$layeredFiles = @($javaFiles | Where-Object {
    $_.FullName -match '\\(application|domain|infrastructure)\\'
}).Count

$javaTargets = @()
foreach ($match in [regex]::Matches($buildContent, 'VERSION_(?:1_)?([0-9]+)|JavaLanguageVersion\.of\(([0-9]+)\)')) {
    $value = if ($match.Groups[1].Success) { $match.Groups[1].Value } else { $match.Groups[2].Value }
    if ($value) { $javaTargets += $value }
}
$javaTargets = @($javaTargets | Sort-Object -Unique)

$gradleVersions = @()
foreach ($wrapperFile in $wrapperProperties) {
    $wrapperContent = Get-Content -Raw -LiteralPath $wrapperFile.FullName
    $versionMatch = [regex]::Match($wrapperContent, 'gradle-([0-9]+(?:\.[0-9]+)+)-')
    if ($versionMatch.Success) { $gradleVersions += $versionMatch.Groups[1].Value }
}
$gradleVersions = @($gradleVersions | Sort-Object -Unique)

$javaSource = ($javaFiles | ForEach-Object {
    try { Get-Content -Raw -LiteralPath $_.FullName } catch { '' }
}) -join [Environment]::NewLine
$packageRoots = @([regex]::Matches(
    $javaSource,
    '(?m)^\s*package\s+([A-Za-z_][\w]*(?:\.[A-Za-z_][\w]*)+)\s*;'
) | ForEach-Object { $_.Groups[1].Value } |
    Sort-Object -Unique |
    Select-Object -First 20)
$actions = @($javaFiles | Where-Object {
    $_.FullName -match '\\(action|actions|button|buttons)\\'
} | Select-Object -First 20)
$schedulers = @($javaFiles | Where-Object {
    $_.FullName -match '\\(scheduler|schedulers|schedule|job|jobs)\\'
} | Select-Object -First 20)
$listeners = @($javaFiles | Where-Object {
    $_.FullName -match '\\(listener|listeners|event|events)\\'
} | Select-Object -First 20)
$services = @($javaFiles | Where-Object {
    $_.FullName -match '\\(service|services)\\'
} | Select-Object -First 20)
$repositories = @($javaFiles | Where-Object {
    $_.FullName -match '\\(repository|repositories|dao|daos)\\'
} | Select-Object -First 20)
$entryPoints = @($actions + $schedulers + $listeners |
    Select-Object -Unique -First 30)
$tableNames = @([regex]::Matches(
    $javaSource,
    '\b(?:AD_|TGF|TSI|TDD|TWF|TGW|TFC)[A-Z0-9_]{2,}\b'
) | ForEach-Object { $_.Value } |
    Sort-Object -Unique |
    Select-Object -First 30)
$usesJape = $javaSource -match '\b(JapeSession|JapeWrapper|EntityFacade|DynamicVO)\b'
$usesNativeSql = $javaSource -match '\bNativeSql\b'
$criticalDependencies = @($buildContent -split '\r?\n' | Where-Object {
    $_ -match '(?i)(implementation|compileOnly|compile|runtimeOnly).*(sankhya|jape|mge|tomcat|servlet)'
} | ForEach-Object { $_.Trim() } |
    Select-Object -Unique |
    Select-Object -First 20)

function Get-TextSha256 {
    param([string]$Text)
    $sha = [System.Security.Cryptography.SHA256]::Create()
    try {
        $bytes = [System.Text.Encoding]::UTF8.GetBytes($Text)
        return ([System.BitConverter]::ToString($sha.ComputeHash($bytes))).Replace('-', '')
    } finally {
        $sha.Dispose()
    }
}

$structuralFiles = @($buildFiles + $settingsFiles + $wrapperProperties)
if ($hasMaven) {
    $structuralFiles += Get-Item -LiteralPath $pomFile
}
$gradleProperties = Join-Path $scanRoot 'gradle.properties'
if (Test-Path -LiteralPath $gradleProperties -PathType Leaf) {
    $structuralFiles += Get-Item -LiteralPath $gradleProperties
}
$structuralManifest = @($structuralFiles | Sort-Object FullName | ForEach-Object {
    [System.IO.Path]::GetRelativePath($scanRoot, $_.FullName) + '=' +
        (Get-FileHash -Algorithm SHA256 -LiteralPath $_.FullName).Hash
})
$sourceManifest = @($allFiles | Where-Object {
    $_.Extension -in @('.java', '.kt', '.groovy', '.xml', '.sql', '.js', '.html', '.jsp', '.jrxml')
} | Sort-Object FullName | ForEach-Object {
    [System.IO.Path]::GetRelativePath($scanRoot, $_.FullName) + ':' +
        $_.Length + ':' + $_.LastWriteTimeUtc.Ticks
})
$buildFileHash = Get-TextSha256 ($structuralManifest -join [Environment]::NewLine)
$sourceTreeHash = Get-TextSha256 ($sourceManifest -join [Environment]::NewLine)
$fingerprint = Get-TextSha256 (($gitHead, $buildFileHash, $sourceTreeHash) -join '|')
$profilePath = Join-Path $scanRoot 'docs\zaya\project-profile.md'
$existingFingerprint = ''
if (Test-Path -LiteralPath $profilePath) {
    $existingText = Get-Content -Raw -LiteralPath $profilePath
    $match = [regex]::Match(
        $existingText,
        '<!-- zaya-profile:fingerprint=([A-Fa-f0-9]{64}) -->'
    )
    if ($match.Success) {
        $existingFingerprint = $match.Groups[1].Value.ToUpperInvariant()
    }
}
$profileFresh = $existingFingerprint -eq $fingerprint

$warnings = [System.Collections.Generic.List[string]]::new()
if ($buildFiles.Count -eq 0 -and -not $hasMaven -and $javaFiles.Count -gt 0) {
    $warnings.Add('Build Gradle ou Maven nao detectado.')
}
if ($wrapperProperties.Count -eq 0 -and $buildFiles.Count -gt 0) { $warnings.Add('Gradle Wrapper nao detectado.') }
if ($settingsFiles.Count -eq 0 -and $buildFiles.Count -gt 0) { $warnings.Add('settings.gradle nao detectado.') }
if (($buildFileNames -contains 'build.gradle') -and ($buildFileNames -contains 'build.gradle.kts')) {
    $warnings.Add('Groovy DSL e Kotlin DSL coexistem; exige justificativa.')
}
if ($buildContent -match 'SNAPSHOT') { $warnings.Add('Versao SNAPSHOT detectada.') }
if ($buildFiles.Count -gt 0 -and $buildContent -notmatch 'companyCode.*projectCode') {
    $warnings.Add('Convencao corporativa de nome do JAR nao detectada.')
}
if ($javaFiles.Count -gt 0 -and $mvcFiles -eq 0) { $warnings.Add('Estrutura MVC nao detectada no codigo Java.') }
if ($statusCount -gt 0) { $warnings.Add("Working tree possui $statusCount item(ns) alterado(s).") }
if (-not $profileFresh) { $warnings.Add('Project profile ausente ou desatualizado.') }

$profile = [ordered]@{
    schemaVersion = 1
    generatedAt = (Get-Date).ToString('yyyy-MM-ddTHH:mm:ssK')
    fingerprint = $fingerprint
    profileFresh = $profileFresh
    project = Split-Path -Leaf $scanRoot
    git = [ordered]@{
        branch = $branch
        head = $gitHead
        changedItems = $statusCount
    }
    classification = $projectType
    build = [ordered]@{
        system = $buildSystem
        files = @($buildFiles | ForEach-Object { [System.IO.Path]::GetRelativePath($scanRoot, $_.FullName) })
        settings = @($settingsFiles | ForEach-Object { [System.IO.Path]::GetRelativePath($scanRoot, $_.FullName) })
        wrapperVersions = $gradleVersions
        javaTargets = $javaTargets
        buildFileHash = $buildFileHash
    }
    structure = [ordered]@{
        javaFiles = $javaFiles.Count
        testFiles = $testFiles.Count
        mvcFiles = $mvcFiles
        layeredFiles = $layeredFiles
        metadataXmlFiles = $metadataFiles.Count
        databaseFiles = $databaseFiles.Count
        reportFiles = $reportFiles.Count
        sourceTreeHash = $sourceTreeHash
    }
    code = [ordered]@{
        packageRoots = $packageRoots
        entryPoints = @($entryPoints | ForEach-Object {
            [System.IO.Path]::GetRelativePath($scanRoot, $_.FullName)
        })
        actions = $actions.Count
        schedulers = $schedulers.Count
        listeners = $listeners.Count
        services = $services.Count
        repositories = $repositories.Count
        tables = $tableNames
        usesJape = $usesJape
        usesNativeSql = $usesNativeSql
        criticalDependencies = $criticalDependencies
    }
    warnings = @($warnings)
}

if ($WriteProfile) {
    $profileDirectory = Join-Path $scanRoot 'docs\zaya'
    $marker = '<!-- generated-by: zaya-sankhya-development -->'

    if (Test-Path -LiteralPath $profilePath) {
        $existing = Get-Content -Raw -LiteralPath $profilePath
        if ($existing -notmatch [regex]::Escape($marker)) {
            throw "Perfil existente nao foi gerado pela skill e nao sera sobrescrito: $profilePath"
        }
    }

    New-Item -ItemType Directory -Path $profileDirectory -Force | Out-Null
    $persistentWarnings = @($warnings | Where-Object {
        $_ -notmatch '^Working tree possui |^Project profile ausente ou desatualizado\.'
    })
    $warningLines = if ($persistentWarnings.Count -eq 0) {
        '- Nenhum alerta estrutural detectado.'
    } else {
        ($persistentWarnings | ForEach-Object { "- $_" }) -join "`n"
    }
    $buildLines = if ($buildFiles.Count -eq 0) {
        '- Nenhum arquivo Gradle detectado.'
    } else {
        ($buildFiles | ForEach-Object {
            '- `' + [System.IO.Path]::GetRelativePath($scanRoot, $_.FullName) + '`'
        }) -join "`n"
    }
    $javaSummary = if ($javaTargets.Count -gt 0) { $javaTargets -join ', ' } else { 'nao identificado' }
    $wrapperSummary = if ($gradleVersions.Count -gt 0) { $gradleVersions -join ', ' } else { 'nao identificado' }
    $packageSummary = if ($packageRoots.Count -gt 0) { $packageRoots -join ', ' } else { 'nenhum' }
    $tableSummary = if ($tableNames.Count -gt 0) { $tableNames -join ', ' } else { 'nenhuma' }
    $dependencySummary = if ($criticalDependencies.Count -gt 0) { $criticalDependencies -join '; ' } else { 'nenhuma' }
$markdown = @"
$marker
<!-- zaya-profile:fingerprint=$fingerprint -->
# Perfil tecnico do projeto

> Arquivo gerado automaticamente. Fatos funcionais e compatibilidade Sankhya
> ainda exigem confirmacao no ambiente e na documentacao oficial.

## Identificacao

- Projeto: $($profile.project)
- Classificacao: $projectType
- Build: $buildSystem
- Java detectado: $javaSummary
- Gradle Wrapper detectado: $wrapperSummary
- Profile gerado em: $($profile.generatedAt)
- Git HEAD: $(if ($gitHead) { $gitHead } else { 'sem commit' })
- Build hash: $buildFileHash
- Source tree hash: $sourceTreeHash

## Build

$buildLines

## Estrutura

- Arquivos Java: $($javaFiles.Count)
- Testes: $($testFiles.Count)
- Arquivos em pacotes MVC: $mvcFiles
- Arquivos em pacotes de arquitetura em camadas: $layeredFiles
- XMLs em datadictionary: $($metadataFiles.Count)
- Arquivos em database/dbscripts: $($databaseFiles.Count)
- Relatorios: $($reportFiles.Count)

## Codigo

- Package roots: $packageSummary
- Entry points: $(@($entryPoints).Count)
- Actions: $($actions.Count)
- Schedules: $($schedulers.Count)
- Listeners: $($listeners.Count)
- Services: $($services.Count)
- Repositories: $($repositories.Count)
- Uso de Jape: $usesJape
- Uso de NativeSql: $usesNativeSql
- Principais tabelas: $tableSummary
- Dependencias criticas: $dependencySummary

## Alertas

$warningLines

## Conhecimento aplicavel

- Leia o `AGENTS.md` mais proximo antes de qualquer alteracao.
- Use os padroes MVC, Gradle e Java da biblioteca Zaya.
- Para Add-on Studio ou metadados, use o template oficial e valide o XSD.
- Para transacoes e documentos, aplique os checklists Sankhya correspondentes.
"@
    $temporaryPath = "$profilePath.tmp"
    try {
        [System.IO.File]::WriteAllText(
            $temporaryPath,
            $markdown,
            [System.Text.UTF8Encoding]::new($false)
        )
        Move-Item -LiteralPath $temporaryPath -Destination $profilePath -Force
    } finally {
        if (Test-Path -LiteralPath $temporaryPath) {
            Remove-Item -LiteralPath $temporaryPath -Force
        }
    }
    Write-Output "Perfil atualizado: $profilePath"
}

if ($AsJson) {
    $profile | ConvertTo-Json -Depth 6
} elseif (-not $WriteProfile) {
    $profile
}

if ($FailOnWarning -and $warnings.Count -gt 0) {
    exit 1
}
