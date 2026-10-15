[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [string]$Name,
    [string]$Version = '4.35b815',
    [ValidateRange(1, 100)]
    [int]$MaxResults = 10,
    [switch]$AsJson
)

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'Zaya.Dictionary.ps1')
$root = Get-ZayaDictionaryRoot -Version $Version
$rows = @(Import-Csv -LiteralPath (Join-Path $root 'catalog\TABLE_CATALOG.csv') -Encoding UTF8)
$exact = @($rows | Where-Object { $_.TableName -ieq $Name })
$result = if ($exact.Count) {
    $exact
} else {
    @($rows | Where-Object {
        $_.TableName -like "*$Name*" -or $_.Description -like "*$Name*"
    } | Select-Object -First $MaxResults)
}
$output = @($result | Select-Object TableName, Description, Module, FieldCount,
    InstanceCount, RelationshipCount, Additional,
    @{Name = 'Page'; Expression = { Join-Path $root ('tables\' + $_.FileName) }})
Write-ZayaResult -InputObject $output -AsJson:$AsJson
