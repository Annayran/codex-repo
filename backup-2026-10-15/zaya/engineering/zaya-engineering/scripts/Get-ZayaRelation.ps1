[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [string]$SourceTable,
    [Parameter(Position = 1)]
    [string]$TargetTable,
    [string]$Version = '4.35b815',
    [ValidateRange(1, 200)]
    [int]$MaxResults = 30,
    [switch]$AsJson
)

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'Zaya.Dictionary.ps1')
$root = Get-ZayaDictionaryRoot -Version $Version
$rows = Import-Csv -LiteralPath (Join-Path $root 'catalog\ENTITY_RELATIONSHIPS.csv') -Encoding UTF8
$matches = @($rows | Where-Object {
    $touchesSource = $_.SourceTable -ieq $SourceTable -or $_.TargetTable -ieq $SourceTable
    $touchesTarget = -not $TargetTable -or
        $_.SourceTable -ieq $TargetTable -or $_.TargetTable -ieq $TargetTable
    $touchesSource -and $touchesTarget
} | Select-Object -First $MaxResults)
$output = @($matches | Select-Object SourceTable, SourceInstance, TargetTable,
    TargetInstance, FieldMappings, RelationshipType, Required, InsertAllowed,
    UpdateAllowed, DeleteAllowed, Additional)
Write-ZayaResult -InputObject $output -AsJson:$AsJson
