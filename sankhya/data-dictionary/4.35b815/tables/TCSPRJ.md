# TCSPRJ — Cadastro de Projetos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 30
- Instâncias DWF: 3
- Relações DWF: 63
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Projeto | Projeto | S | S |
| Projeto2 | Projeto2 | S | N |
| Projeto3 | Projeto3 | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREVIATURA | Abreviação Projeto | S |  |  | N |
| AMOSTRACUSMAX | Amostras por Custódia | I |  |  | N |
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CARGAHORMAX | Carga Horária Máxima | I |  |  | N |
| CODCLT | Padrão de Classificação | I |  |  | N |
| CODCTACTB | Conta Contábil 1 | I |  |  | N |
| CODCTACTB2 | Conta Contábil 2 | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODPROJPAI | Projeto pai | I |  |  | N |
| CODUSURESP | Cód. Usuário Responsável | I |  |  | N |
| DTINICIO | Data Início | H |  |  | N |
| DTTERMINO | Data de Término | H |  |  | N |
| GRAU | Grau | I |  |  | N |
| HRFINALMAX | Hora Final Máxima | I |  |  | N |
| HRINICIALMIN | Hora Inicial Mínima | I |  |  | N |
| IDENTIFICACAO | Identificação | S |  |  | N |
| INTERVALOMAX | Intervalo Máximo | I |  |  | N |
| INTERVALOMIN | Intervalo Mínimo | I |  |  | N |
| LIMBONIF | Limite de Bonificação | F |  |  | N |
| NUQUE | Questionário | I |  |  | N |
| PERCRETENCAO | Percentual de Retenção | F |  |  | N |
| RETENCAOATIVA | Retenção Ativa | S |  |  | N |
| TIMCONTACOMPART | Conta Compartilhada | S |  |  | N |
| TIMNMCTACOMP | Conta Bancária | I |  |  | N |
| TIMPARCPROJ | Parceiro | I |  |  | N |
| TIMTXPART | Participação % | F |  |  | N |
| ULTIMORDO | Último número RDO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Amostra / [TAPAMO](TAPAMO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CadastroProjetoSite / [TGFPSI](TGFPSI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Contrato / [TCSCON](TCSCON.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Contrato / [TCSCON](TCSCON.md) | Projeto2 / [TCSPRJ](TCSPRJ.md) | CODPROJSINT->CODPROJ | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| EquipamentoProjeto / [TAPEPJ](TAPEPJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| EquipePorProjeto / [TAPEQJ](TAPEQJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| FiltroCalculoCustoProjeto / [TSIFOP](TSIFOP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODCHAVE->CODPROJ | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ItemRegras / [TGFITR](TGFITR.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODINSTPRINC->CODPROJ | I |
| ItemRegras / [TGFITR](TGFITR.md) | Projeto2 / [TCSPRJ](TCSPRJ.md) | CODINSTSECINI->CODPROJ | I |
| ItemRegras / [TGFITR](TGFITR.md) | Projeto3 / [TCSPRJ](TCSPRJ.md) | CODINSTSECFIN->CODPROJ | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| MetaAtual / [TGMMET](TGMMET.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ParceiroInstalacao / [TCSCPA](TCSCPA.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJPAD->CODPROJ | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJETO->CODPROJ | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Produto / [TGFPRO](TGFPRO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | CadastroProjetoSite / [TGFPSI](TGFPSI.md) | CODPROJ->CODPROJ | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | ContaBancaria / [TSICTA](TSICTA.md) | TIMNMCTACOMP->CODCTABCOINT | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | PlanoConta2 /  | CODCTACTB2->CODCTACTB | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | QuestionarioPesquisa / [TGFQUE](TGFQUE.md) | NUQUE->NUQUE | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | TimEmpresa / [TSIEMP](TSIEMP.md) | TIMPARCPROJ->CODEMP | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ResultadoAnalise / [TAPRAM](TAPRAM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Servico / [TGFPRO](TGFPRO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ServicoObjeto / [TAPSEO](TAPSEO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| StatusAmostra / [TAPSAM](TAPSAM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Projeto / [TCSPRJ](TCSPRJ.md) | EPRPROJCORBANCARIO->CODPROJ | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Projeto2 / [TCSPRJ](TCSPRJ.md) | EPRPROJETO->CODPROJ | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | LOTPROJETO->CODPROJ | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Projeto / [TCSPRJ](TCSPRJ.md) | TXCCODPROJ->CODPROJ | I |
| Topografia / [TAPTOP](TAPTOP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| VeiculoPorProjeto / [TAPVPJ](TAPVPJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Verbas / [TGFGEV](TGFGEV.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJETO->CODPROJ | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
