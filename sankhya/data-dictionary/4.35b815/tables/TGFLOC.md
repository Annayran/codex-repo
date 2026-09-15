# TGFLOC — Locais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 23
- Instâncias DWF: 17
- Relações DWF: 56
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LocalBaixaMPProducao | Local de Baixa de MP | S | N |
| LocalDestinoPerda | LocalDestinoPerda | S | N |
| LocalDestinoProducao | Local de Destino  do Produção | S | N |
| LocalDestinoProducao2 | Local de Destino Encadeada | S | N |
| LocalEstoque | Local de Estoque | S | N |
| LocalEstoqueTerceiro | Local Específico Estoque Terceiros | S | N |
| LocalEstoqueWms | Local para ajuste de estoque | S | N |
| LocalFinanceiro | Local | S | N |
| LocalFinanceiroEntDest | Local | S | N |
| LocalFinanceiroEntOrig | Local Entrada Origem da TGFETA | S | N |
| LocalFinanceiroSaiDest | Local | S | N |
| LocalFinanceiroSaiOrig | Local Saída Origem da TGFETA | S | N |
| LocalMateriaPrima | Locais Matéria Prima | S | S |
| LocalOrigemProducao | Local | S | N |
| LocalPadraoEconet | Local Padrão Econet | S | N |
| LocalPadraoImportarXML | Local padrão para importar XML | S | N |
| LocalTabPreco | Locais Tabela de Preço | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITANOVAPROD | Aceita Novas Produções | S |  |  | N |
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CAPACIDADEPRODUCAO | Capacidade de Produção | F |  |  | N |
| CODLOCAL | Cód. Local | I |  |  | N |
| CODLOCALPAI | Local Pai | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTAB | Tabela de preço | I |  |  | N |
| DESCRLOCAL | Descrição | S |  |  | N |
| DIASPRODUCAO | Prazo de entrega (em dias) | I |  |  | N |
| DOMINGO | Domingo | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| INCSOBREIRF | Incide sobre IRF | S |  |  | N |
| QUARTA | Quarta | S |  |  | N |
| QUINTA | Quinta | S |  |  | N |
| SABADO | Sábado | S |  |  | N |
| SEGUNDA | Segunda | S |  |  | N |
| SEXTA | Sexta | S |  |  | N |
| TERCA | Terça | S |  |  | N |
| UTILIZAWMS | Controlado pelo WMS | S |  |  | N |
| VALESTINDEP | Val.Est.Independente | S |  |  | N |
| VLRCUS | Valor de custo | F |  |  | N |
| VLRVENDA | Valor de venda | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ArmazenaDisponibilidadeDiaMaq / [TGFDID](TGFDID.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ContagemEstoqueSerie / [TGFCTS](TGFCTS.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ControleFCI / [TGFMFCI](TGFMFCI.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Custo / [TGFCUS](TGFCUS.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Desconto / [TGFDES](TGFDES.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| DisponibilidadeDiaria / [TGFDID](TGFDID.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalEstoqueTerceiro / [TGFLOC](TGFLOC.md) | CODLOCALTERC->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalEstoqueWms / [TGFLOC](TGFLOC.md) | WMSLOCALAJEST->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | LOCALPAD->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalPadraoEconet / [TGFLOC](TGFLOC.md) | LOCALPADECONECT->CODLOCAL | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALPAD->CODLOCAL | L |
| Estoque / [TGFEST](TGFEST.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroEntDest / [TGFLOC](TGFLOC.md) | CODLOCALENTDEST->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroEntOrig / [TGFLOC](TGFLOC.md) | CODLOCALENTORIG->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroSaiDest / [TGFLOC](TGFLOC.md) | CODLOCALSAIDEST->CODLOCAL | I |
| EtapaProducao / [TGFETA](TGFETA.md) | LocalFinanceiroSaiOrig / [TGFLOC](TGFLOC.md) | CODLOCALSAIORIG->CODLOCAL | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Excecao / [TGFEXC](TGFEXC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| GiroProduto / [TGFGIR](TGFGIR.md) | LocalFinanceiroSaiDest / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | LocalMateriaPrima / [TGFLOC](TGFLOC.md) | CODLOCALMP->CODLOCAL | I |
| ItemCotacao / [TGFITC](TGFITC.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemNota / [TGFITE](TGFITE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ItemNota / [TGFITE](TGFITE.md) | LocalFinanceiroEntDest / [TGFLOC](TGFLOC.md) | CODLOCALDEST->CODLOCAL | I |
| ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ItemRegras / [TGFITR](TGFITR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODINSTPRINC->CODLOCAL | I |
| ItemRegras / [TGFITR](TGFITR.md) | LocalFinanceiroEntDest / [TGFLOC](TGFLOC.md) | CODINSTSECFIN->CODLOCAL | I |
| ItemRegras / [TGFITR](TGFITR.md) | LocalFinanceiroEntOrig / [TGFLOC](TGFLOC.md) | CODINSTSECINI->CODLOCAL | I |
| LocalFinanceiro / [TGFLOC](TGFLOC.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| LocalFinanceiro / [TGFLOC](TGFLOC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LocalTabPreco / [TGFLOC](TGFLOC.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| MetaAtual / [TGMMET](TGMMET.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| OrdemCarga / [TGFORD](TGFORD.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| OrdemSugestaoLocal / [TGFOSL](TGFOSL.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Parceiro / [TGFPAR](TGFPAR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALPADRAO->CODLOCAL | I |
| Produto / [TGFPRO](TGFPRO.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALPADRAO->CODLOCAL | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| Recibo / [TGFREC](TGFREC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LocalPadraoImportarXML / [TGFLOC](TGFLOC.md) | CODLOCALIMPXML->CODLOCAL | I |
| Transferencia / [TGMTRA](TGMTRA.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| VariacaoCustoEstoque / [TGFVCE](TGFVCE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
