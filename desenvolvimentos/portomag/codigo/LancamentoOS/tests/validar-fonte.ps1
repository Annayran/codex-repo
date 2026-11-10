$ErrorActionPreference = 'Stop'
$projectDir = Split-Path $PSScriptRoot -Parent
$action = Get-Content (Join-Path $projectDir 'src/main/java/view/GerarPedidoCompra.java') -Raw
$helper = Get-Content (Join-Path $projectDir 'src/main/java/helper/SankhyaHelper.java') -Raw
# Contratos estaticos: nao substituem testes de integracao Jape/Oracle.
$checks = [ordered]@{
    'Empresa usa CODEMP' = $action.Contains('apontamento.asBigDecimal("CODEMP")') -and !$action.Contains('asBigDecimal("EMPRESA")')
    'Tecnico usa chave propria' = $action.Contains('findByPK(codTecnico)') -and !$action.Contains('findByPK(apontamento.asBigDecimal("CODPARC"))')
    'Cidade usa CIDADE' = $helper.Contains('siteVO.asString("CIDADE")') -and !$helper.Contains('asString("NOMECID")')
    'Relatorio usa conteudo' = $action.Contains('apontamento.asString("AD_RELATORIOFOTOGRAFICO2")')
    'Lock por chave composta' = $action.Contains('NUNOTA = :NUNOTA AND SEQUENCIA = :SEQUENCIA FOR UPDATE NOWAIT')
    'Bloqueia NUOS existente' = $action.Contains('osExistente.signum() != 0')
    'Atualiza somente linha selecionada' = $action.Contains('prepareToUpdate(apontamento)') -and !$action.Contains('find("this.NUNOTA = ?"')
    'Lock antes de persistir' = $action.IndexOf('FOR UPDATE NOWAIT') -lt $action.IndexOf('dwFacade.createEntity')
    'Validacao item antes de persistir' = $action.IndexOf('helper.validarItem(apontamento)') -lt $action.IndexOf('dwFacade.createEntity')
    'Item antes de vinculo' = $action.IndexOf('helper.inseriItem(') -lt $action.IndexOf('fluidUpdAnxVO.update()')
    'Totalizacao antes de vinculo' = $action.Contains('helper.totalizarEValidar(nuNota, apontamento)') -and $action.IndexOf('helper.totalizarEValidar(') -lt $action.IndexOf('fluidUpdAnxVO.update()')
    'Observacao vem da atividade' = $action.Contains('apontamento.asString("OBSSERVICO")') -and !$action.Contains('apontamento.asString("OBSINSTALACAO")')
    'Preco vem de VLRSERVICO' = $helper.Contains('item.asBigDecimal("VLRSERVICO")') -and $helper.Contains('ValorAtividade.unitario(valorAtividade, quantidade)') -and !$helper.Contains('item.asBigDecimal("VLRUNIT")') -and !$helper.Contains('item.asBigDecimal("VLRTOT")')
    'Totalizacao pelo helper padrao com trava' = $helper.Contains('totalizador.totalizarNota(nuNota)') -and $helper.Contains('totalNota.compareTo(esperado) != 0')
    'Cabecalho inicializado antes da persistencia' = $action.Contains('SankhyaHelper.inicializarValoresCabecalho(notaCompraOS, apontamento)') -and $action.IndexOf('SankhyaHelper.inicializarValoresCabecalho(') -lt $action.IndexOf('dwFacade.createEntity')
    'Zera modelo antes de preencher atividade' = $helper.Contains('for (String campo : campos) cabecalho.setProperty(campo, BigDecimal.ZERO)') -and $helper.Contains('cabecalho.setProperty("VLRNOTA", ValorAtividade.total(')
    'Item sem nulos nos componentes da soma' = $helper.Contains('itemVO.setProperty("VLRDESC", BigDecimal.ZERO)') -and $helper.Contains('itemVO.setProperty("VLRIPI", BigDecimal.ZERO)') -and $helper.Contains('itemVO.setProperty("VLRREPRED", BigDecimal.ZERO)')
    'Total conferido em consulta parametrizada' = $helper.Contains('FROM TGFCAB C WHERE C.NUNOTA = :NUNOTA') -and $helper.Contains('rs.getBigDecimal("VLRNOTA")') -and $helper.Contains('totalItens.compareTo(esperado) != 0')
    'TOP exata no item' = $helper.Contains('JapeFactory.dao("TipoOperacao").findByPK(codtop, dhTipoOper)') -and $helper.Contains('Timestamp dhTipoOper = cabecalhoVO.asTimestamp("DHTIPOPER")') -and !$helper.Contains('ComercialUtils.getTipoOperacao(')
    'TOP exige chave completa' = $helper.Contains('if (codtop == null || dhTipoOper == null)')
    'TOP ausente interrompe item' = $helper.Contains('if (topVO == null)') -and $helper.IndexOf('if (topVO == null)') -lt $helper.IndexOf('centralItemNota.inicializaProduto(params)')
    'Servico fixo 202160' = $helper.Contains('new BigDecimal("202160")') -and $helper.Contains('params.codProd = PRODUTO_SERVICO_INSTALACAO') -and $helper.Contains('itemVO.setProperty("CODPROD", PRODUTO_SERVICO_INSTALACAO)')
    'Referencia de engenharia em PRODUTONFE' = $helper.Contains('item.asString("REFERENCIA")') -and $helper.Contains('itemVO.setProperty("PRODUTONFE", referenciaEngenharia.trim())') -and !$helper.Contains('item.asString("REFFORN")')
    'Descricao original em AD_PRODINST' = $helper.Contains('produtoOriginalVO.asString("DESCRPROD").trim()') -and $helper.Contains('itemVO.setProperty("AD_PRODINST"') -and !$helper.Contains('itemVO.setProperty("AD_DESCRPRODNF"')
    'Produtos e dados validados antes do cabecalho' = $helper.Contains('Produto de servico 202160 nao encontrado.') -and $action.IndexOf('helper.validarItem(apontamento)') -lt $action.IndexOf('dwFacade.createEntity')
    'Nota inicia pendente' = $action.Contains('setProperty("STATUSNOTA", "P")')
    'NUNOTA vem do retorno persistido' = $action.Contains('notaPersistida.getValueObject()')
    'Retorno informa notas' = $action.Contains('Notas processadas:')
    'Excecao propagada' = $action.Contains('throw new RuntimeException(e)')
}
foreach ($check in $checks.GetEnumerator()) {
    if (!$check.Value) { throw "FALHOU: $($check.Key)" }
    Write-Output "OK: $($check.Key)"
}
Write-Output "$($checks.Count) contratos estaticos verificados. Sem execucao em banco."
