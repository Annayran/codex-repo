param(
    [Parameter(Mandatory=$true)][string]$ReferenceZip,
    [Parameter(Mandatory=$true)][string]$ClobReferenceZip
)
$ErrorActionPreference='Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem
$project=Split-Path $PSScriptRoot -Parent
$output=Join-Path $project 'metadata/genesis-rastreabilidade'
New-Item -ItemType Directory -Path $output -Force | Out-Null
function Read-Metadata([string]$path) {
    $z=[IO.Compression.ZipFile]::OpenRead($path)
    try {
        $e=$z.GetEntry('metadata.xml')
        if(!$e){throw 'Referencia sem metadata.xml na raiz.'}
        $s=$e.Open()
        try{$d=[xml]::new();$d.XmlResolver=$null;$d.Load($s);return ,$d}finally{$s.Dispose()}
    } finally {$z.Dispose()}
}
$reference=Read-Metadata $ReferenceZip
$clobReference=Read-Metadata $ClobReferenceZip
$clob=$clobReference.SelectSingleNode('//field[@dataType="C"]')
if(!$clob){throw 'Referencia sem campo CLOB comprovado.'}
if($reference.metadata.instances.instance.Count -gt 1){throw 'Esperada uma instancia de referencia.'}
$definitions=@(
    @{Name='AD_GENAPICTRL';Description='Controle de Cadastros Genesis';Key='IDINTGENESIS';Fields=@(
        'IDINTGENESIS|Identificador do controle|I|19|S',
        'CHAVE_MOVIMENTO|Chave unica do controle|S|200|S',
        'TIPO_MOVIMENTO|Tipo do controle|S|100|S',
        'STATUS_INTEGRACAO|Estado atual da integracao|S|30|S',
        'TENTATIVAS|Quantidade de tentativas|I|10|S',
        'HTTP_STATUS|Codigo HTTP retornado|I|5|N',
        'MENSAGEM_ERRO|Mensagem da integracao|C||N',
        'PAYLOAD_HASH|Hash do payload confirmado|S|64|N',
        'PROTOCOLO_GENESIS|ID confirmado na Genesis|S|100|N',
        'ORIGEM_ITEM|Origem do item|S|30|N',
        'DH_INCLUSAO|Data e hora de inclusao|H||S',
        'DH_ATUALIZACAO|Data e hora de atualizacao|H||S',
        'DH_ENVIO|Data e hora de envio|H||N',
        'NUNOTA|Nota de origem sem relacionamento|I|10|N'
    )},
    @{Name='AD_GENREQLOG';Description='Historico HTTP Genesis';Key='IDREQ';Fields=@(
        'IDREQ|Identificador da requisicao|S|36|S',
        'EXECUCAO|Identificador da execucao|S|36|S',
        'FLUXO|Fluxo da integracao|S|50|S',
        'CONTEXTO|Contexto da operacao|S|1000|N',
        'CHAVE_CONTROLE|Chave do controle tecnico|S|200|N',
        'CHAVE_MOVIMENTO|Chave do movimento de nota|S|200|N',
        'ENTIDADE|Entidade consultada|S|50|N',
        'IDCONSULTADO|ID utilizado na consulta|S|100|N',
        'METODO|Metodo HTTP|S|10|S',
        'ENDPOINT|Caminho do endpoint sem autenticacao|S|1000|S',
        'DH_INICIO|Inicio da requisicao|H||S',
        'DH_FIM|Fim da requisicao|H||N',
        'DURACAO_MS|Duracao em milissegundos|I|19|N',
        'HTTP_STATUS|Codigo HTTP retornado|I|5|N',
        'CLASSIFICACAO|Classificacao do retorno|S|30|N',
        'IDRETORNO|ID retornado pela Genesis|S|100|N',
        'REQUISICAO|Corpo enviado sanitizado|C||N',
        'RESPOSTA|Corpo recebido sanitizado|C||N',
        'IDENTIDADE|Estado da validacao de identidade|S|20|S',
        'PERSISTENCIA|Estado da persistencia local|S|20|S',
        'DH_PERSISTENCIA|Data e hora de persistencia|H||N',
        'DETALHE|Detalhe da aplicacao local|C||N'
    )}
)
$encoding=[Text.Encoding]::GetEncoding('ISO-8859-1')
$inventory=@()
foreach($definition in $definitions) {
    $d=[xml]$reference.CloneNode($true)
    # Never copy internal connection details, events or relationships.
    $export=$d.metadata.exportInfo
    foreach($node in @($export.ChildNodes)) {
        if($node.Name -notin @('exportTime','systemVersion','systemCharSet','dbMetadata')){[void]$export.RemoveChild($node)}
    }
    $db=$export.SelectSingleNode('dbMetadata')
    if(!$db){throw 'Referencia sem dbMetadata obrigatorio.'}
    foreach($node in @($db.ChildNodes)) {
        if($node.LocalName -notin @('dbUser','urlConnection','jdbcDriver','DBMS')){[void]$db.RemoveChild($node)}
    }
    foreach($name in @('dbUser','urlConnection')) {
        $node=$db.SelectSingleNode($name)
        if(!$node){$node=$d.CreateElement($name);[void]$db.AppendChild($node)}
        $node.InnerText=''
    }
    $instance=$d.metadata.instances.instance
    $instance.SetAttribute('name',$definition.Name);$instance.SetAttribute('isUpdate','false')
    foreach($node in @($instance.ChildNodes)) {
        if($node.LocalName -notin @('instanceDescription','tableInfo','fields')){[void]$instance.RemoveChild($node)}
    }
    $instance.SelectSingleNode('instanceDescription').InnerText=[string]$definition.Description
    $table=$instance.tableInfo
    $table.SetAttribute('name',$definition.Name);$table.SetAttribute('sequenceType','M')
    foreach($node in @($table.ChildNodes)) {
        if($node.LocalName -notin @('category','tableDescription','primaryKey')){[void]$table.RemoveChild($node)}
    }
    $table.SelectSingleNode('tableDescription').InnerText=[string]$definition.Description
    $primary=$table.SelectSingleNode('primaryKey')
    $primary.RemoveAll();[void]$primary.AppendChild($d.CreateElement($definition.Key))
    $fields=$instance.fields;$fields.RemoveAll()
    foreach($spec in $definition.Fields) {
        $p=$spec.Split('|');$type=$p[2];$required=$p[4] -eq 'S';$pk=$p[0] -eq $definition.Key
        $template=if($type -eq 'C'){$clob}else{$reference.SelectSingleNode("//field[@dataType='$type']")}
        if(!$template){throw "Tipo sem referencia: $type"}
        $f=$d.ImportNode($template,$true)
        foreach($node in @($f.ChildNodes)){if($node.Name -ne 'description'){[void]$f.RemoveChild($node)}}
        $f.SetAttribute('name',$p[0]);$f.SetAttribute('systemField','N')
        $f.SetAttribute('calculated','N');$f.SetAttribute('allowDefault','S')
        $f.SetAttribute('allowSearch',$(if($pk){'S'}else{'N'}))
        $f.SetAttribute('visibleOnSearch',$(if($type -eq 'C'){'N'}else{'S'}))
        $f.SetAttribute('allowNull',$(if($required){'N'}else{'S'}))
        $f.RemoveAttribute('size');if($p[3]){$f.SetAttribute('size',$p[3])}
        $f.SelectSingleNode('description').InnerText=[string]$p[1]
        [void]$fields.AppendChild($f)
    }
    $settings=[Xml.XmlWriterSettings]::new();$settings.Encoding=$encoding;$settings.Indent=$true
    $xmlPath=Join-Path $output ($definition.Name+'.xml')
    $writer=[Xml.XmlWriter]::Create($xmlPath,$settings)
    try{$d.Save($writer)}finally{$writer.Dispose()}
    $zipPath=Join-Path $output ('Metadados_'+$definition.Name+'_corrigido.zip')
    # Rebuild the generated artifact only; source exports are never modified.
    $stream=[IO.File]::Open($zipPath,[IO.FileMode]::Create)
    $zip=[IO.Compression.ZipArchive]::new($stream,[IO.Compression.ZipArchiveMode]::Create)
    try{
        $entry=$zip.CreateEntry('metadata.xml');$dest=$entry.Open()
        try{$bytes=[IO.File]::ReadAllBytes($xmlPath);$dest.Write($bytes,0,$bytes.Length)}finally{$dest.Dispose()}
    }finally{$zip.Dispose();$stream.Dispose()}
    $check=Read-Metadata $zipPath
    if($check.SelectNodes('//field').Count -ne $definition.Fields.Count){throw 'Quantidade de campos divergente.'}
    if($check.SelectNodes('//relationships|//events|//scripts').Count){throw 'Conteudo nao permitido no pacote.'}
    if(!$check.SelectSingleNode('/metadata/exportInfo/dbMetadata')){throw 'dbMetadata obrigatorio ausente.'}
    foreach($name in @('dbUser','urlConnection','jdbcDriver','DBMS')) {
        $node=$check.SelectSingleNode('/metadata/exportInfo/dbMetadata/'+$name)
        if(!$node){throw "Elemento obrigatorio ausente: $name"}
        if($name -in @('dbUser','urlConnection') -and $node.InnerText){throw 'Conexao interna nao foi sanitizada.'}
    }
    if($check.metadata.instances.instance.tableInfo.primaryKey.FirstChild.Name -ne $definition.Key){throw 'Chave primaria divergente.'}
    $inventory += [pscustomobject]@{Table=$definition.Name;Fields=$definition.Fields.Count;Sha256=(Get-FileHash $zipPath -Algorithm SHA256).Hash}
}
$inventory|ConvertTo-Json|Set-Content (Join-Path $output 'manifest.json') -Encoding utf8
$inventory|Format-Table -AutoSize
