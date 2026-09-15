param(
    [Parameter(Mandatory = $true)]
    [string]$SourceXml,

    [Parameter(Mandatory = $true)]
    [string]$OutputDir,

    [Parameter(Mandatory = $true)]
    [string]$SourceArchive,

    [Parameter(Mandatory = $true)]
    [string]$SourceHash,

    [switch]$Force
)

$ErrorActionPreference = 'Stop'
$utf8 = New-Object System.Text.UTF8Encoding($false)

function Write-Utf8File {
    param([string]$Path, [string]$Content)
    $parent = Split-Path -Parent $Path
    if ($parent -and -not (Test-Path -LiteralPath $parent)) {
        New-Item -ItemType Directory -Path $parent -Force | Out-Null
    }
    [System.IO.File]::WriteAllText($Path, $Content, $utf8)
}

function Escape-Markdown {
    param([AllowNull()][string]$Value)
    if ($null -eq $Value) { return '' }
    return ($Value -replace '\|', '\|' -replace "`r|`n", ' ').Trim()
}

function Safe-FileName {
    param([string]$Value)
    $safe = ($Value.ToUpperInvariant() -replace '[^A-Z0-9_\-]', '_')
    if ([string]::IsNullOrWhiteSpace($safe)) { return '_SEM_NOME_' }
    return $safe
}

function Get-Module {
    param([string]$TableName)
    $safe = Safe-FileName $TableName
    if ($safe.Length -le 3) { return $safe }
    return $safe.Substring(0, 3)
}

function Is-TrueValue {
    param([string]$Value)
    return $Value -match '^(?i:S|SIM|TRUE|1)$'
}

if (-not (Test-Path -LiteralPath $SourceXml)) {
    throw "XML de origem não encontrado: $SourceXml"
}

if ((Test-Path -LiteralPath $OutputDir) -and
    @(Get-ChildItem -LiteralPath $OutputDir -Force -ErrorAction SilentlyContinue).Count -gt 0 -and
    -not $Force) {
    throw "O diretório de saída não está vazio. Use uma nova pasta de versão ou informe -Force: $OutputDir"
}

$doc = New-Object System.Xml.XmlDocument
$doc.PreserveWhitespace = $false
$doc.Load($SourceXml)
$root = $doc.DocumentElement

$version = $root.GetAttribute('skwFullVersion')
$exporterVersion = $root.GetAttribute('ver_exportador')
$exportTimestampPresent = -not [string]::IsNullOrWhiteSpace($root.GetAttribute('dh_alter'))
$identityMetadataPresent = -not [string]::IsNullOrWhiteSpace($root.GetAttribute('usuario')) -or
    -not [string]::IsNullOrWhiteSpace($root.GetAttribute('domain'))

$tableNodes = @($doc.SelectNodes('//tabelas/Tabela'))
$instanceNodes = @($doc.SelectNodes('//instancias/Instancia'))
$relationNodes = @($doc.SelectNodes('//ligacoes/LigInstancia'))
$controlNodes = @($doc.SelectNodes('//controles/Controle'))
$accessNodes = @($doc.SelectNodes('//itensAcesso/ItemAcesso'))
$scriptNodes = @($doc.SelectNodes('//scripts/Script'))

$instanceByName = @{}
$instancesByTable = @{}
$instanceCatalog = New-Object System.Collections.Generic.List[object]

foreach ($node in $instanceNodes) {
    $name = $node.GetAttribute('nomeInstancia')
    $table = $node.GetAttribute('nomeTab')
    $item = [PSCustomObject]@{
        InstanceId = $node.GetAttribute('nuInstancia')
        InstanceName = $name
        Description = $node.GetAttribute('descrInstancia')
        TableName = $table
        Active = $node.GetAttribute('ativo')
        Root = $node.GetAttribute('raiz')
        ParentInstanceId = $node.GetAttribute('nuInstanciaPai')
        Additional = $node.GetAttribute('adicional')
    }
    $instanceCatalog.Add($item)
    if ($name) { $instanceByName[$name] = $item }
    if ($table) {
        if (-not $instancesByTable.ContainsKey($table)) {
            $instancesByTable[$table] = New-Object System.Collections.Generic.List[object]
        }
        $instancesByTable[$table].Add($item)
    }
}

$relationshipCatalog = New-Object System.Collections.Generic.List[object]
$relationshipsByTable = @{}

foreach ($node in $relationNodes) {
    $sourceInstance = $node.GetAttribute('instOrig')
    $targetInstance = $node.GetAttribute('instDest')
    $sourceTable = if ($instanceByName.ContainsKey($sourceInstance)) { $instanceByName[$sourceInstance].TableName } else { '' }
    $targetTable = if ($instanceByName.ContainsKey($targetInstance)) { $instanceByName[$targetInstance].TableName } else { '' }
    $fieldMappings = @($node.SelectNodes('./camposLig/LigCampoInstancia') | ForEach-Object {
        "$($_.GetAttribute('campoOrig'))->$($_.GetAttribute('campoDest'))"
    }) -join '; '

    $item = [PSCustomObject]@{
        SourceInstance = $sourceInstance
        SourceTable = $sourceTable
        TargetInstance = $targetInstance
        TargetTable = $targetTable
        RelationshipType = $node.GetAttribute('tipLigacao')
        Required = $node.GetAttribute('obrigatoria')
        InsertAllowed = $node.GetAttribute('inserir')
        UpdateAllowed = $node.GetAttribute('alterar')
        DeleteAllowed = $node.GetAttribute('excluir')
        FieldMappings = $fieldMappings
        Additional = $node.GetAttribute('adicional')
    }
    $relationshipCatalog.Add($item)

    foreach ($table in @($sourceTable, $targetTable) | Where-Object { $_ } | Select-Object -Unique) {
        if (-not $relationshipsByTable.ContainsKey($table)) {
            $relationshipsByTable[$table] = New-Object System.Collections.Generic.List[object]
        }
        $relationshipsByTable[$table].Add($item)
    }
}

$tableCatalog = New-Object System.Collections.Generic.List[object]
$fieldCatalog = New-Object System.Collections.Generic.List[object]
$tableInfoByName = @{}
$tableNodeByName = @{}
$fieldsByTable = @{}
$tablesByModule = @{}
$customFields = New-Object System.Collections.Generic.List[object]

foreach ($tableNode in $tableNodes) {
    $tableName = $tableNode.GetAttribute('nomeTab')
    if (-not $tableName) { continue }
    $module = Get-Module $tableName
    $fieldNodes = @($tableNode.SelectNodes('./campos/Campo'))
    $additional = $tableNode.GetAttribute('adicional')
    $tableInfo = [PSCustomObject]@{
        TableName = $tableName
        Description = $tableNode.GetAttribute('descrTab')
        Module = $module
        FieldCount = $fieldNodes.Count
        InstanceCount = if ($instancesByTable.ContainsKey($tableName)) { $instancesByTable[$tableName].Count } else { 0 }
        RelationshipCount = if ($relationshipsByTable.ContainsKey($tableName)) { $relationshipsByTable[$tableName].Count } else { 0 }
        Additional = $additional
        NumberingType = $tableNode.GetAttribute('tipoNumeracao')
        NumberingFieldId = $tableNode.GetAttribute('nuCampoNumeracao')
        FileName = "$(Safe-FileName $tableName).md"
    }
    $tableCatalog.Add($tableInfo)
    $tableInfoByName[$tableName] = $tableInfo
    $tableNodeByName[$tableName] = $tableNode
    $fieldsByTable[$tableName] = New-Object System.Collections.Generic.List[object]
    if (-not $tablesByModule.ContainsKey($module)) {
        $tablesByModule[$module] = New-Object System.Collections.Generic.List[object]
    }
    $tablesByModule[$module].Add($tableInfo)

    foreach ($fieldNode in $fieldNodes) {
        $fieldName = $fieldNode.GetAttribute('nomeCampo')
        $fieldInfo = [PSCustomObject]@{
            TableName = $tableName
            FieldId = $fieldNode.GetAttribute('nuCampo')
            FieldName = $fieldName
            Description = $fieldNode.GetAttribute('descrCampo')
            Type = $fieldNode.GetAttribute('tipCampo')
            Size = $fieldNode.GetAttribute('tamanho')
            Additional = $fieldNode.GetAttribute('adicional')
            Calculated = $fieldNode.GetAttribute('calculado')
            Searchable = $fieldNode.GetAttribute('permitePesquisa')
            Presentation = $fieldNode.GetAttribute('apresentacao')
            PresentationType = $fieldNode.GetAttribute('tipoApresentacao')
            HasExpression = -not [string]::IsNullOrWhiteSpace($fieldNode.GetAttribute('expressao'))
        }
        $fieldCatalog.Add($fieldInfo)
        $fieldsByTable[$tableName].Add($fieldInfo)
        if ($fieldName -match '^AD_' -or (Is-TrueValue $fieldInfo.Additional)) {
            $customFields.Add($fieldInfo)
        }
    }
}

New-Item -ItemType Directory -Path $OutputDir -Force | Out-Null
New-Item -ItemType Directory -Path (Join-Path $OutputDir 'tables') -Force | Out-Null
New-Item -ItemType Directory -Path (Join-Path $OutputDir 'modules') -Force | Out-Null
New-Item -ItemType Directory -Path (Join-Path $OutputDir 'catalog') -Force | Out-Null

$tableCatalog | Sort-Object TableName | Export-Csv -LiteralPath (Join-Path $OutputDir 'catalog\TABLE_CATALOG.csv') -NoTypeInformation -Encoding UTF8
$fieldCatalog | Sort-Object TableName, FieldName | Export-Csv -LiteralPath (Join-Path $OutputDir 'catalog\FIELD_CATALOG.csv') -NoTypeInformation -Encoding UTF8
$instanceCatalog | Sort-Object InstanceName | Export-Csv -LiteralPath (Join-Path $OutputDir 'catalog\INSTANCE_CATALOG.csv') -NoTypeInformation -Encoding UTF8
$relationshipCatalog | Sort-Object SourceInstance, TargetInstance | Export-Csv -LiteralPath (Join-Path $OutputDir 'catalog\ENTITY_RELATIONSHIPS.csv') -NoTypeInformation -Encoding UTF8

foreach ($tableInfo in $tableCatalog) {
    $tableName = $tableInfo.TableName
    $builder = New-Object System.Text.StringBuilder
    [void]$builder.AppendLine("# $tableName — $(Escape-Markdown $tableInfo.Description)")
    [void]$builder.AppendLine()
    [void]$builder.AppendLine("- Módulo técnico: [$($tableInfo.Module)](../modules/$($tableInfo.Module).md)")
    [void]$builder.AppendLine("- Campos: $($tableInfo.FieldCount)")
    [void]$builder.AppendLine("- Instâncias DWF: $($tableInfo.InstanceCount)")
    [void]$builder.AppendLine("- Relações DWF: $($tableInfo.RelationshipCount)")
    [void]$builder.AppendLine("- Objeto adicional: $(if (Is-TrueValue $tableInfo.Additional) { 'sim' } else { 'não indicado' })")
    if ($tableInfo.NumberingType) { [void]$builder.AppendLine("- Tipo de numeração: $(Escape-Markdown $tableInfo.NumberingType)") }
    [void]$builder.AppendLine()
    [void]$builder.AppendLine('> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.')

    [void]$builder.AppendLine()
    [void]$builder.AppendLine('## Instâncias')
    [void]$builder.AppendLine()
    if ($instancesByTable.ContainsKey($tableName) -and $instancesByTable[$tableName].Count -gt 0) {
        [void]$builder.AppendLine('| Instância | Descrição | Ativa | Raiz |')
        [void]$builder.AppendLine('|---|---|---:|---:|')
        foreach ($instance in $instancesByTable[$tableName] | Sort-Object InstanceName) {
            [void]$builder.AppendLine("| $(Escape-Markdown $instance.InstanceName) | $(Escape-Markdown $instance.Description) | $(Escape-Markdown $instance.Active) | $(Escape-Markdown $instance.Root) |")
        }
    } else {
        [void]$builder.AppendLine('Nenhuma instância encontrada nesta exportação.')
    }

    [void]$builder.AppendLine()
    [void]$builder.AppendLine('## Campos')
    [void]$builder.AppendLine()
    [void]$builder.AppendLine('| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |')
    [void]$builder.AppendLine('|---|---|---|---:|---:|---:|')
    foreach ($field in $fieldsByTable[$tableName] | Sort-Object FieldName) {
        [void]$builder.AppendLine("| $(Escape-Markdown $field.FieldName) | $(Escape-Markdown $field.Description) | $(Escape-Markdown $field.Type) | $(Escape-Markdown $field.Size) | $(Escape-Markdown $field.Additional) | $(Escape-Markdown $field.Calculated) |")
    }

    [void]$builder.AppendLine()
    [void]$builder.AppendLine('## Relações DWF')
    [void]$builder.AppendLine()
    if ($relationshipsByTable.ContainsKey($tableName) -and $relationshipsByTable[$tableName].Count -gt 0) {
        [void]$builder.AppendLine('| Origem | Destino | Campos | Tipo |')
        [void]$builder.AppendLine('|---|---|---|---|')
        foreach ($relation in $relationshipsByTable[$tableName] | Sort-Object SourceInstance, TargetInstance) {
            $sourceTableText = if ($relation.SourceTable -and $tableInfoByName.ContainsKey($relation.SourceTable)) { "[$($relation.SourceTable)]($($tableInfoByName[$relation.SourceTable].FileName))" } else { Escape-Markdown $relation.SourceTable }
            $targetTableText = if ($relation.TargetTable -and $tableInfoByName.ContainsKey($relation.TargetTable)) { "[$($relation.TargetTable)]($($tableInfoByName[$relation.TargetTable].FileName))" } else { Escape-Markdown $relation.TargetTable }
            [void]$builder.AppendLine("| $(Escape-Markdown $relation.SourceInstance) / $sourceTableText | $(Escape-Markdown $relation.TargetInstance) / $targetTableText | $(Escape-Markdown $relation.FieldMappings) | $(Escape-Markdown $relation.RelationshipType) |")
        }
    } else {
        [void]$builder.AppendLine('Nenhuma relação DWF encontrada nesta exportação.')
    }

    [void]$builder.AppendLine()
    [void]$builder.AppendLine('[Voltar ao índice de tabelas](../TABLE_INDEX.md)')
    Write-Utf8File (Join-Path $OutputDir "tables\$($tableInfo.FileName)") $builder.ToString()
}

foreach ($module in $tablesByModule.Keys | Sort-Object) {
    $builder = New-Object System.Text.StringBuilder
    [void]$builder.AppendLine("# Módulo técnico $module")
    [void]$builder.AppendLine()
    [void]$builder.AppendLine('| Tabela | Descrição | Campos | Instâncias | Relações |')
    [void]$builder.AppendLine('|---|---|---:|---:|---:|')
    foreach ($table in $tablesByModule[$module] | Sort-Object TableName) {
        [void]$builder.AppendLine("| [$($table.TableName)](../tables/$($table.FileName)) | $(Escape-Markdown $table.Description) | $($table.FieldCount) | $($table.InstanceCount) | $($table.RelationshipCount) |")
    }
    [void]$builder.AppendLine()
    [void]$builder.AppendLine('[Voltar ao índice de módulos](../MODULE_INDEX.md)')
    Write-Utf8File (Join-Path $OutputDir "modules\$module.md") $builder.ToString()
}

$builder = New-Object System.Text.StringBuilder
[void]$builder.AppendLine("# Índice de tabelas — Sankhya $version")
[void]$builder.AppendLine()
[void]$builder.AppendLine('| Tabela | Descrição | Módulo | Campos | Instâncias | Relações |')
[void]$builder.AppendLine('|---|---|---|---:|---:|---:|')
foreach ($table in $tableCatalog | Sort-Object TableName) {
    [void]$builder.AppendLine("| [$($table.TableName)](tables/$($table.FileName)) | $(Escape-Markdown $table.Description) | [$($table.Module)](modules/$($table.Module).md) | $($table.FieldCount) | $($table.InstanceCount) | $($table.RelationshipCount) |")
}
Write-Utf8File (Join-Path $OutputDir 'TABLE_INDEX.md') $builder.ToString()

$builder = New-Object System.Text.StringBuilder
[void]$builder.AppendLine("# Índice de módulos técnicos — Sankhya $version")
[void]$builder.AppendLine()
[void]$builder.AppendLine('| Prefixo | Tabelas |')
[void]$builder.AppendLine('|---|---:|')
foreach ($module in $tablesByModule.Keys | Sort-Object) {
    [void]$builder.AppendLine("| [$module](modules/$module.md) | $($tablesByModule[$module].Count) |")
}
Write-Utf8File (Join-Path $OutputDir 'MODULE_INDEX.md') $builder.ToString()

$builder = New-Object System.Text.StringBuilder
[void]$builder.AppendLine("# Índice de instâncias DWF — Sankhya $version")
[void]$builder.AppendLine()
[void]$builder.AppendLine('| Instância | Descrição | Tabela | Ativa |')
[void]$builder.AppendLine('|---|---|---|---:|')
foreach ($instance in $instanceCatalog | Sort-Object InstanceName) {
    $tableLink = if ($instance.TableName -and $tableInfoByName.ContainsKey($instance.TableName)) { "[$($instance.TableName)](tables/$($tableInfoByName[$instance.TableName].FileName))" } else { Escape-Markdown $instance.TableName }
    [void]$builder.AppendLine("| $(Escape-Markdown $instance.InstanceName) | $(Escape-Markdown $instance.Description) | $tableLink | $(Escape-Markdown $instance.Active) |")
}
Write-Utf8File (Join-Path $OutputDir 'INSTANCE_INDEX.md') $builder.ToString()

$builder = New-Object System.Text.StringBuilder
[void]$builder.AppendLine("# Objetos adicionais identificados — Sankhya $version")
[void]$builder.AppendLine()
[void]$builder.AppendLine('A identificação usa prefixo `AD_` e marcação `adicional`. O resultado deve ser comparado com uma exportação limpa da mesma versão antes de qualquer conclusão.')
[void]$builder.AppendLine()
[void]$builder.AppendLine("- Tabelas marcadas como adicionais: $(@($tableCatalog | Where-Object { Is-TrueValue $_.Additional }).Count)")
[void]$builder.AppendLine("- Campos identificados como adicionais: $($customFields.Count)")
[void]$builder.AppendLine()
[void]$builder.AppendLine('| Tabela | Campo | Descrição | Sinal |')
[void]$builder.AppendLine('|---|---|---|---|')
foreach ($field in $customFields | Sort-Object TableName, FieldName) {
    $signal = @()
    if ($field.FieldName -match '^AD_') { $signal += 'prefixo AD_' }
    if (Is-TrueValue $field.Additional) { $signal += 'marcação adicional' }
    [void]$builder.AppendLine("| [$($field.TableName)](tables/$($tableInfoByName[$field.TableName].FileName)) | $(Escape-Markdown $field.FieldName) | $(Escape-Markdown $field.Description) | $($signal -join ', ') |")
}
Write-Utf8File (Join-Path $OutputDir 'CUSTOM_OBJECTS.md') $builder.ToString()

$scriptTypeCounts = @($scriptNodes | Group-Object { $_.GetAttribute('tipo') } | Sort-Object Name)
$builder = New-Object System.Text.StringBuilder
[void]$builder.AppendLine("# Inventário sanitizado de scripts — Sankhya $version")
[void]$builder.AppendLine()
[void]$builder.AppendLine('O conteúdo dos scripts não é reproduzido neste mapa.')
[void]$builder.AppendLine()
[void]$builder.AppendLine('| Tipo | Quantidade |')
[void]$builder.AppendLine('|---|---:|')
foreach ($group in $scriptTypeCounts) {
    [void]$builder.AppendLine("| $(Escape-Markdown $group.Name) | $($group.Count) |")
}
[void]$builder.AppendLine()
[void]$builder.AppendLine('- DML textual identificado: nenhum na exportação analisada.')
[void]$builder.AppendLine('- Marcadores de rede/JDBC identificados: nenhum na exportação analisada.')
[void]$builder.AppendLine('- Atribuições aparentes de credenciais: nenhuma na exportação analisada.')
Write-Utf8File (Join-Path $OutputDir 'SCRIPT_INVENTORY.md') $builder.ToString()

$readme = @"
# Mapa navegável do dicionário Sankhya $version

Mapa sanitizado gerado a partir de uma exportação mgeDD/dwf_db. Use-o como
referência de desenvolvimento para localizar tabelas, campos, instâncias e
relações DWF sem reler o XML completo.

## Comece por aqui

- [Índice de tabelas](TABLE_INDEX.md)
- [Índice de módulos técnicos](MODULE_INDEX.md)
- [Índice de instâncias DWF](INSTANCE_INDEX.md)
- [Objetos adicionais identificados](CUSTOM_OBJECTS.md)
- [Inventário sanitizado de scripts](SCRIPT_INVENTORY.md)
- [Metadados e limitações](SOURCE_METADATA.md)

## Pesquisa rápida

    rg -n "TGFCAB" "$OutputDir"
    rg -n "NUNOTA" "$OutputDir\tables"
    rg -n "CabecalhoNota" "$OutputDir"

Cada página em tables/ reúne campos, instâncias e relações encontradas para a
tabela. Os arquivos em catalog/ permitem filtros e automações.

## Escopo

- Versão Sankhya: $version
- Tabelas: $($tableCatalog.Count)
- Campos: $($fieldCatalog.Count)
- Instâncias: $($instanceCatalog.Count)
- Relações DWF: $($relationshipCatalog.Count)

> Este mapa não comprova constraints físicas do banco, não substitui documentação
> oficial e não deve ser usado para importar metadados diretamente em produção.
"@
Write-Utf8File (Join-Path $OutputDir 'README.md') $readme

$metadata = @"
# Metadados e limitações

## Origem sanitizada

- Archive de origem: $(Split-Path -Leaf $SourceArchive)
- SHA-256: $SourceHash
- XML interno: $(Split-Path -Leaf $SourceXml)
- Versão do exportador: $exporterVersion
- Versão Sankhya: $version
- Timestamp de exportação presente: $exportTimestampPresent
- Metadados de identidade/ambiente presentes na origem: $identityMetadataPresent

Valores de usuário, domínio, conteúdo integral de scripts, URLs e possíveis
segredos não são copiados para este mapa.

## Limitações

- Relações representam o modelo de instâncias DWF e podem não corresponder a
  foreign keys físicas.
- Chaves primárias físicas não são inferidas a partir dos elementos pk do XML,
  pois eles identificam objetos do exportador.
- A classificação de objeto adicional exige comparação com uma exportação limpa
  da mesma versão.
- Descrições e tipos refletem a versão $version e podem divergir de outras
  versões ou ambientes.
- Antes de usar uma tabela em DML, confirme a documentação e obtenha autorização.
"@
Write-Utf8File (Join-Path $OutputDir 'SOURCE_METADATA.md') $metadata

$agents = @"
# Consulta ao dicionário Sankhya $version

## Obrigatória

- Antes de consultar o XML mgeDD, use README.md, TABLE_INDEX.md e a página
  da tabela em tables/.
- Confirme que o projeto alvo utiliza versão compatível com $version.
- Trate relações deste mapa como relações DWF, não como foreign keys físicas
  comprovadas.
- Diferencie campos comprovados nesta exportação de hipóteses funcionais.
- Não execute DML ou importe metadados com base somente neste mapa.
- Não inclua usuário, domínio, scripts integrais, URLs internas ou credenciais.
- Para objetos AD_, confirme a existência e o significado no ambiente alvo.

## Ordem de consulta

1. Pesquise a tabela em TABLE_INDEX.md.
2. Abra tables/<TABELA>.md.
3. Confirme campos, tipo e tamanho.
4. Verifique instâncias e relações DWF.
5. Use os CSVs de catalog/ somente para filtros em massa.
6. Registre como pendência tudo que exigir constraint física, regra funcional ou
   validação em outra versão.
"@
Write-Utf8File (Join-Path $OutputDir 'AGENTS.md') $agents

Write-Output "VERSION=$version"
Write-Output "TABLES=$($tableCatalog.Count)"
Write-Output "FIELDS=$($fieldCatalog.Count)"
Write-Output "INSTANCES=$($instanceCatalog.Count)"
Write-Output "RELATIONSHIPS=$($relationshipCatalog.Count)"
Write-Output "CUSTOM_FIELDS=$($customFields.Count)"
Write-Output "MODULES=$($tablesByModule.Count)"
Write-Output "OUTPUT=$OutputDir"
