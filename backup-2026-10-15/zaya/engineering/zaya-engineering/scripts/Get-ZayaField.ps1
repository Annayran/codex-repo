[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [string]$Name,
    [Parameter(Position = 1)]
    [string]$Table,
    [string]$Version = '4.35b815',
    [ValidateRange(1, 200)]
    [int]$MaxResults = 20,
    [switch]$AsJson
)

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'Zaya.Dictionary.ps1')
$root = Get-ZayaDictionaryRoot -Version $Version
$rows = Import-Csv -LiteralPath (Join-Path $root 'catalog\FIELD_CATALOG.csv') -Encoding UTF8
$matches = @($rows | Where-Object {
    ($_.FieldName -ieq $Name -or $_.FieldName -like "*$Name*" -or
        $_.Description -like "*$Name*") -and
    (-not $Table -or $_.TableName -ieq $Table)
} | Select-Object -First $MaxResults)
$output = @($matches | Select-Object TableName, FieldName, Description, Type, Size,
    Additional, Calculated, Searchable, HasExpression)
Write-ZayaResult -InputObject $output -AsJson:$AsJson
