[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [string[]]$Query,
    [string]$Version = '4.35b815',
    [ValidateRange(1, 200)]
    [int]$MaxResults = 30,
    [switch]$AsJson
)

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'Zaya.Dictionary.ps1')
$root = Get-ZayaDictionaryRoot -Version $Version
$tables = @(Import-Csv -LiteralPath (Join-Path $root 'catalog\TABLE_CATALOG.csv') -Encoding UTF8)
$fields = @(Import-Csv -LiteralPath (Join-Path $root 'catalog\FIELD_CATALOG.csv') -Encoding UTF8)
$instances = @(Import-Csv -LiteralPath (Join-Path $root 'catalog\INSTANCE_CATALOG.csv') -Encoding UTF8)
$results = [System.Collections.Generic.List[object]]::new()

foreach ($term in $Query) {
    if ([string]::IsNullOrWhiteSpace($term)) { continue }
    foreach ($row in $tables | Where-Object {
        $_.TableName -like "*$term*" -or $_.Description -like "*$term*"
    }) {
        $results.Add([PSCustomObject]@{
            Query = $term; Kind = 'Table'; Name = $row.TableName
            Context = $row.Description; Table = $row.TableName
        })
    }
    foreach ($row in $fields | Where-Object {
        $_.FieldName -like "*$term*" -or $_.Description -like "*$term*"
    }) {
        $results.Add([PSCustomObject]@{
            Query = $term; Kind = 'Field'; Name = $row.FieldName
            Context = $row.Description; Table = $row.TableName
        })
    }
    foreach ($row in $instances | Where-Object {
        $_.InstanceName -like "*$term*" -or $_.Description -like "*$term*"
    }) {
        $results.Add([PSCustomObject]@{
            Query = $term; Kind = 'Instance'; Name = $row.InstanceName
            Context = $row.Description; Table = $row.TableName
        })
    }
}

$output = @($results | Sort-Object Query, Kind, Name -Unique | Select-Object -First $MaxResults)
Write-ZayaResult -InputObject $output -AsJson:$AsJson
