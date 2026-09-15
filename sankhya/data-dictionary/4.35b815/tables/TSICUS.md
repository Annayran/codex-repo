# TSICUS — Centros de Resultado

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 21
- Instâncias DWF: 13
- Relações DWF: 79
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CentroResultado | Centro de Resultado | S | S |
| CentroResultadoArm | Centros de Resultado Armazenagem | S | N |
| CentroResultadoDespesa | Centro de Resultado Despesa | S | N |
| CentroResultadoEFDEmpresa | Centro Resultado | S | N |
| CentroResultadoExp | Centros de Resultado Armazenagem | S | N |
| CentroResultadoFinal | Centro de Resultado Final | S | N |
| CentroResultadoIpi | Centro de Resultado | S | S |
| TimCentroResultadoAdm | Centro Resultado | S | N |
| TimCentroResultadoCorresBanc | Centro Resultado | S | N |
| TimCentroResultadoIncorporador | Centro Resultado | S | N |
| TimCentroResultadoLoteamento | Centro Resultado | S | N |
| TimCentroResultadoRevenda | Centro Resultado | S | N |
| TimCentroResultadoVenda | Centro Resultado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| AREA | Área | F |  |  | N |
| AREACONT | Área Contábil | F |  |  | N |
| AREAPERM | Área Permutada | F |  |  | N |
| AREAREAL | Área Real | F |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CALCELALURPARTEA | CR para calculo e-LALUR (Parte A) | S |  |  | N |
| CODCENCUS | Código | I |  |  | N |
| CODCENCUSPAI | Cód.Centro Resultado Pai | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCRESP | Parceiro Responsável | I |  |  | N |
| CODTAB | Tabela de Preço | I |  |  | N |
| CODUNG | Unidade Gerencial | I |  |  | N |
| CODUNN | Unidade de negócio | I |  |  | N |
| CODUSURESP | Usuário Responsável | I |  |  | N |
| DESCRCENCUS | Descrição | S |  |  | N |
| DTINCLUSAO | Dt. Inclusão | H |  |  | N |
| FRACCONT | Fração Contábil | F |  |  | N |
| FRACGEREN | Fração Real | F |  |  | N |
| GRAU | Grau | I |  |  | N |
| VEICULO | Veículo Obrigatório | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Agendamento / [TCSAGE](TCSAGE.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CentroResultado / [TSICUS](TSICUS.md) | CentroResultadoNatureza / [TGFCRN](TGFCRN.md) | CODCENCUS->CODCENCUS | I |
| CentroResultado / [TSICUS](TSICUS.md) | LimiteOrcamentarioCR / [TSILOR](TSILOR.md) | CODCENCUS->CODCENCUS | I |
| CentroResultado / [TSICUS](TSICUS.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| CentroResultado / [TSICUS](TSICUS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CentroResultado / [TSICUS](TSICUS.md) | ParceiroResponsavel / [TGFPAR](TGFPAR.md) | CODPARCRESP->CODPARC | I |
| CentroResultado / [TSICUS](TSICUS.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| CentroResultado / [TSICUS](TSICUS.md) | UnidadeNegocio / [TGFUNN](TGFUNN.md) | CODUNN->CODUNN | I |
| CentroResultado / [TSICUS](TSICUS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | CentroResultado / [TSICUS](TSICUS.md) | CENTRESULT->CODCENCUS | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Contrato / [TCSCON](TCSCON.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CentroResultadoArm / [TSICUS](TSICUS.md) | CODCENCUSAR->CODCENCUS | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CentroResultadoExp / [TSICUS](TSICUS.md) | CODCENCUSEX->CODCENCUS | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODCENCUSDESP->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultadoEFDEmpresa / [TSICUS](TSICUS.md) | EFDCODCENCUS->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultadoIpi / [TSICUS](TSICUS.md) | CODCENCUSIPI->CODCENCUS | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSINICIAL->CODCENCUS | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | CentroResultadoFinal / [TSICUS](TSICUS.md) | CODCENCUSFINAL->CODCENCUS | I |
| EtapaProducao / [TGFETA](TGFETA.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| FiltroCalculoCusto / [TSIFOP](TSIFOP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCHAVE->CODCENCUS | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ItemCotacao / [TGFITC](TGFITC.md) | CentroResultado / [TSICUS](TSICUS.md) | CENTRESULT->CODCENCUS | I |
| ItemRegras / [TGFITR](TGFITR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODINSTPRINC->CODCENCUS | I |
| ItemRegras / [TGFITR](TGFITR.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODINSTSECINI->CODCENCUS | I |
| ItemRegras / [TGFITR](TGFITR.md) | CentroResultadoFinal / [TSICUS](TSICUS.md) | CODINSTSECFIN->CODCENCUS | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| LimiteOrcamentarioCR / [TSILOR](TSILOR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| MetaAtual / [TGMMET](TGMMET.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSPAD->CODCENCUS | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Produto / [TGFPRO](TGFPRO.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ResidencialEmpreendimento / [TCCRES](TCCRES.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Servico / [TGFPRO](TGFPRO.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| TabelaPrecoCR / [TGFCRT](TGFCRT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | CentroResultado / [TSICUS](TSICUS.md) | CORCENTROCUSTO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | CentroResultado / [TSICUS](TSICUS.md) | IMBCODCENCUSLOCACAO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoAdm / [TSICUS](TSICUS.md) | IMBCODCENCUSADM->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoCorresBanc / [TSICUS](TSICUS.md) | IMBCODCENCUSCORBANCARIO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoIncorporador / [TSICUS](TSICUS.md) | IMBCODCENCUSINCORPORA->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoLoteamento / [TSICUS](TSICUS.md) | IMBCODCENCUSLOTEAMENTO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoRevenda / [TSICUS](TSICUS.md) | IMBCODCENCUSREVENDA->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoVenda / [TSICUS](TSICUS.md) | IMBCODCENCUSVENDA->CODCENCUS | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | CentroResultado / [TSICUS](TSICUS.md) | TXCCODCENCUS->CODCENCUS | I |
| Transferencia / [TGMTRA](TGMTRA.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | CentroResultado / [TSICUS](TSICUS.md) | CODUNG->CODUNG | I |
| Usuario / [TSIUSU](TSIUSU.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSPAD->CODCENCUS | I |
| Veiculo / [TGFVEI](TGFVEI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Vendedor / [TGFVEN](TGFVEN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSPAD->CODCENCUS | I |
| Verbas / [TGFGEV](TGFGEV.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
