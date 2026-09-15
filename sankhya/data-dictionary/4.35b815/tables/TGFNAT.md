# TGFNAT — Natureza de Receitas e Despesas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 30
- Instâncias DWF: 8
- Relações DWF: 78
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Natureza | Natureza | S | S |
| Natureza2 | Natureza2 | S | N |
| Natureza3 | Natureza3 | S | N |
| NaturezaArm | Natureza de armazenagem | S | N |
| NaturezaExp | Natureza de armazenagem | S | N |
| NaturezaIpi | Natureza | S | S |
| NaturezaReceitaDespesaICMS | Natureza Despesa Receitas ICMS | S | N |
| NaturezaReceitaDespesaICMSST | Natureza Despesa Receitas ICMS ST | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQCOFINS | Alíquota de COFINS | F |  |  | N |
| ALIQPIS | Alíquota de PIS | F |  |  | N |
| ANALITICA | Analítica | S |  |  | N |
| ATIVA | Ativa | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCTACTB | Conta contábil | I |  |  | N |
| CODCTACTB2 | Conta contábil 2 | I |  |  | N |
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODGRUPONAT | Grupo da Natureza | I |  |  | N |
| CODHISTCTB | Cód. Histórico 1 | I |  |  | N |
| CODHISTCTB2 | Cód. Histórico 2 | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODNATPAI | Cód. Natureza Pai | I |  |  | N |
| CODSERVUNICO | Serviço único faturamento | I |  |  | N |
| CSTCOFINS | Cód. Sit. Tribut. COFINS | I |  |  | N |
| CSTPIS | Cód. Sit. Tribut. PIS | I |  |  | N |
| DECVLR | Decimal para Valor | I |  |  | N |
| DESCRNAT | Descrição | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| GERALCDPR | Gera informações para o Livro Caixa Digital Produtor Rural | S |  |  | N |
| GERF100RECCAMB | Gera F100 - Receita Variação Cambial | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| GRUPOMKP | Grupo MKP | S |  |  | N |
| INCRESULT | Incide no resultado | S |  |  | N |
| NATBCCRED | Natureza da Base de Cálculo do Crédito de PIS/COFINS | S |  |  | N |
| NATEFDCONTM410M810 | Cod. Natureza (PIS/COFINS M410/M810) | I |  |  | N |
| RECADIANTRURAL | Receita de Adiantamentos para o Livro Caixa Digital Produtor Rural | S |  |  | N |
| REGIMEEFD | Regime (EFD PIS/COFINS) | S |  |  | N |
| SUBGRUPOMKP | Sub Grupo MKP | S |  |  | N |
| TIPNAT | Tipo de natureza | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Acordo / [TGFACO](TGFACO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CentroResultadoNatureza / [TGFCRN](TGFCRN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | L |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Contrato / [TCSCON](TCSCON.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NaturezaArm / [TGFNAT](TGFNAT.md) | CODNATAR->CODNAT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NaturezaExp / [TGFNAT](TGFNAT.md) | CODNATEX->CODNAT | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| DescricaoNaturezaParceiro / [TGFDNP](TGFDNP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| DetalheNaturezaProposta / [TCSFIP](TCSFIP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Natureza / [TGFNAT](TGFNAT.md) | NATCANPIX->CODNAT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NaturezaIpi / [TGFNAT](TGFNAT.md) | CODNATIPI->CODNAT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NaturezaReceitaDespesaICMS / [TGFNAT](TGFNAT.md) | EFDCODNATDESPRECICMS->CODNAT | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| FiltroCalculoCustoNat / [TSIFOP](TSIFOP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODCHAVE->CODNAT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| FinanceiroPropostaComercial / [TGIFIN](TGIFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| GrupoMetodologia / [TCSGMT](TCSGMT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| GrupoNaturezas / [TGFGNT](TGFGNT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODGRUPONAT->CODGRUPONAT | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| HistoricoResumoPrecoNegociacao / [TCSRPNSEQ](TCSRPNSEQ.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODINSTPRINC->CODNAT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Natureza2 / [TGFNAT](TGFNAT.md) | CODINSTSECINI->CODNAT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Natureza3 / [TGFNAT](TGFNAT.md) | CODINSTSECFIN->CODNAT | I |
| ItensPrecificacao / [TCACPI](TCACPI.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Mensagem / [TGMMSG](TGMMSG.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| MetaAtual / [TGMMET](TGMMET.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | DescricaoNaturezaParceiro / [TGFDNP](TGFDNP.md) | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | GrupoNaturezas / [TGFGNT](TGFGNT.md) | CODGRUPONAT->CODGRUPONAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | HistoricoPadrao /  | CODHISTCTB->CODHISTCTB | I |
| Natureza / [TGFNAT](TGFNAT.md) | HistoricoPadrao2 /  | CODHISTCTB2->CODHISTCTB | I |
| Natureza / [TGFNAT](TGFNAT.md) | NaturCentroResultCtaContabil /  | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | L |
| Natureza / [TGFNAT](TGFNAT.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| Natureza / [TGFNAT](TGFNAT.md) | PlanoContaEmpresa2 /  | CODCTACTB2->CODCTACTB | I |
| Natureza / [TGFNAT](TGFNAT.md) | ProdutoServico / [TCSSER](TCSSER.md) | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | RelacaoEmpNatCtaContabil /  | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | RelacaoNatPisCofins /  | CODNAT->CODNAT | I |
| Natureza / [TGFNAT](TGFNAT.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERVUNICO->CODPROD | I |
| NaturezaContaBancaria / [TGFNCB](TGFNCB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| NaturezaPorNegociacaoPreVenda / [TCSNXT](TCSNXT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| NaturezaServico / [TCSNTS](TCSNTS.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ParcelaAdicional / [TCSPAD](TCSPAD.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNATPAD->CODNAT | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT; CODNATRECEITA->CODNAT | I |
| Produto / [TGFPRO](TGFPRO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ProdutoServico / [TCSSER](TCSSER.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ResumoPrecoNegociacao / [TCSRPN](TCSRPN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Servico / [TGFPRO](TGFPRO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| TimFinanceiroRepasse / [TGFFIN](TGFFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Natureza / [TGFNAT](TGFNAT.md) | TXCCODNAT->CODNAT | I |
| TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | Natureza / [TGFNAT](TGFNAT.md) | HCBNATUREZA->CODNAT | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Verbas / [TGFGEV](TGFGEV.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
