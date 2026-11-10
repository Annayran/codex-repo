$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$project = Split-Path $PSScriptRoot -Parent
$base = Join-Path $project 'build/homologacao-20261022-153434/MAHospitalar.jar'
$fix = Join-Path $project 'build/homologacao-20261023-110752/MAHospitalar.jar'
$directory = Join-Path $project 'output/hotfix-modelo-20261023'
$output = Join-Path $directory 'MAHospitalar-Modelo-Hotfix.jar'
$report = Join-Path $directory 'README.md'
$baseExpected = '4E744181F269052C8F531899B99B34C111C90E3DBA4E257478F3F46C74A077A9'
$fixExpected = '9DE476B2FC647225B9C795B92214523DE882025558026CAD09CB3A6CF1427D93'
$target = 'br/com/zaya/mahospitalar/genesis/repository/MigracaoModeloGenesisRepository.class'
$guards = @(
    'br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.class',
    'br/com/zaya/mahospitalar/genesis/service/GenesisSyncService.class'
)

function Get-EntryHashes([string]$path) {
    $hashes = [System.Collections.Generic.Dictionary[string,string]]::new([StringComparer]::Ordinal)
    $zip = [System.IO.Compression.ZipFile]::OpenRead($path)
    try {
        foreach ($entry in $zip.Entries) {
            if ($entry.FullName -match '(?i)^META-INF/([^/]+\.(SF|RSA|DSA|EC)|SIG-[^/]+)$') {
                throw "Signed JAR rejected: $($entry.FullName)"
            }
            if ($hashes.ContainsKey($entry.FullName)) { throw 'Duplicate ZIP entry rejected.' }
            $stream = $entry.Open()
            $sha = [System.Security.Cryptography.SHA256]::Create()
            try {
                $digest = [BitConverter]::ToString($sha.ComputeHash($stream)).Replace('-', '')
                $hashes.Add($entry.FullName, $digest)
            } finally { $sha.Dispose(); $stream.Dispose() }
        }
    } finally { $zip.Dispose() }
    return ,$hashes
}

$baseHash = (Get-FileHash -LiteralPath $base -Algorithm SHA256).Hash
$fixHash = (Get-FileHash -LiteralPath $fix -Algorithm SHA256).Hash
if ($baseHash -cne $baseExpected) { throw 'Base JAR SHA256 mismatch.' }
if ($fixHash -cne $fixExpected) { throw 'Corrected JAR SHA256 mismatch.' }
$baseEntries = Get-EntryHashes $base
$fixEntries = Get-EntryHashes $fix
foreach ($name in @($target) + $guards) {
    if (-not $baseEntries.ContainsKey($name) -or -not $fixEntries.ContainsKey($name)) {
        throw "Required class missing: $name"
    }
}
if ($baseEntries[$target] -ceq $fixEntries[$target]) { throw 'Target class has no delta.' }
if ((Test-Path -LiteralPath $output) -or (Test-Path -LiteralPath $report)) {
    throw 'Output/report already exists; refusing to overwrite.'
}

$sourceZip = [System.IO.Compression.ZipFile]::OpenRead($fix)
$bytes = [System.IO.MemoryStream]::new()
try {
    $sourceStream = $sourceZip.GetEntry($target).Open()
    try { $sourceStream.CopyTo($bytes) } finally { $sourceStream.Dispose() }
} finally { $sourceZip.Dispose() }
try {
    New-Item -ItemType Directory -Path $directory -Force | Out-Null
    Copy-Item -LiteralPath $base -Destination $output
    # Recreate the container: Update can corrupt existing ZIP local extra headers.
    $baseZip = [System.IO.Compression.ZipFile]::OpenRead($base)
    try {
        $outputStream = [System.IO.File]::Open($output, [System.IO.FileMode]::Create)
        $outputZip = [System.IO.Compression.ZipArchive]::new($outputStream, [System.IO.Compression.ZipArchiveMode]::Create)
        try {
            foreach ($entry in $baseZip.Entries) {
                $replacement = $outputZip.CreateEntry($entry.FullName)
                $replacement.LastWriteTime = $entry.LastWriteTime
                $replacement.ExternalAttributes = $entry.ExternalAttributes
                $destinationStream = $replacement.Open()
                try {
                    if ($entry.FullName -ceq $target) {
                        $bytes.Position = 0
                        $bytes.CopyTo($destinationStream)
                    } else {
                        $originalStream = $entry.Open()
                        try { $originalStream.CopyTo($destinationStream) } finally { $originalStream.Dispose() }
                    }
                } finally { $destinationStream.Dispose() }
            }
        } finally { $outputZip.Dispose() }
    } finally { $baseZip.Dispose() }
} finally { $bytes.Dispose() }

$outputEntries = Get-EntryHashes $output
if ($outputEntries.Count -ne $baseEntries.Count) { throw 'ZIP entry count changed; artifact NOT validated.' }
$delta = @()
foreach ($name in $baseEntries.Keys) {
    if (-not $outputEntries.ContainsKey($name)) { throw "Entry missing: $name" }
    if ($outputEntries[$name] -cne $baseEntries[$name]) { $delta += $name }
}
if ($delta.Count -ne 1 -or $delta[0] -cne $target) { throw 'Unexpected entry delta; artifact NOT validated.' }
if ($outputEntries[$target] -cne $fixEntries[$target]) { throw 'Target differs from corrected JAR.' }
foreach ($name in $guards) {
    if ($outputEntries[$name] -cne $baseEntries[$name]) { throw "M1/M3 isolation failed: $name" }
}
if ((Get-FileHash -LiteralPath $base -Algorithm SHA256).Hash -cne $baseExpected -or
    (Get-FileHash -LiteralPath $fix -Algorithm SHA256).Hash -cne $fixExpected) {
    throw 'Input JAR changed during build; artifact NOT validated.'
}
$outputHash = (Get-FileHash -LiteralPath $output -Algorithm SHA256).Hash
$lines = @(
    '# Modelo Hotfix - 2026-10-23',
    '',
    'RISCO: base local testada em 22/10, NAO comprovadamente a versao em producao.',
    'Somente montagem/verificacao local. Sem deploy autorizado; sem homologacao Oracle.',
    'Comparacao de API publica/javap, proveniencia e testes contra este JAR pendentes com o coordenador.',
    '',
    '## Origem e SHA256',
    "Base: build/homologacao-20261022-153434/MAHospitalar.jar | $baseHash",
    "Corrigido (fonte de uma classe): build/homologacao-20261023-110752/MAHospitalar.jar | $fixHash",
    "Saida: MAHospitalar-Modelo-Hotfix.jar | $outputHash",
    '',
    '## Delta verificado',
    "Entradas base/saida: $($baseEntries.Count)/$($outputEntries.Count). Delta: exatamente 1.",
    "Classe: $target",
    "SHA256 antes: $($baseEntries[$target])",
    "SHA256 depois (identico ao corrigido): $($outputEntries[$target])",
    'TODAS as demais entradas preservadas por nome e SHA256 do conteudo descomprimido, inclusive manifest e classes internas.',
    'Nenhuma entrada extra. Entradas duplicadas e assinaturas SF/RSA/DSA/EC/SIG- rejeitadas.',
    'Originais verificados novamente e nao alterados. Nenhuma recompilacao nem importacao das demais classes do JAR corrigido.',
    '',
    '## Guards M1/M3 (saida identica a base)'
)
foreach ($name in $guards) {
    $lines += "$name | base/saida: $($baseEntries[$name]) | corrigido: $($fixEntries[$name])"
}
$lines += @('', 'Este artefato NAO incorpora as mudancas M1/M3 do JAR corrigido. Nao comprova equivalencia com producao.')
[System.IO.File]::WriteAllLines($report, $lines, [System.Text.UTF8Encoding]::new($false))
Write-Output "Verified hotfix: $output"
Write-Output "SHA256: $outputHash"
Write-Output "Report: $report"
