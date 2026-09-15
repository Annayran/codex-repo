# TCSITE — Ítens da Ordem de Serviço

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 70
- Instâncias DWF: 1
- Relações DWF: 19
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemOrdemServico | Sub-OS | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSIFICACAO | Classificação | S | 25 |  | S |
| COBRAR | Cobrar | S |  |  | N |
| CODCENCUSPAD | Cód.Centro Resultado Padrão | I |  |  | S |
| CODEPV | Expectativa Fechamento | I |  |  | N |
| CODOCOROS | Motivo | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| CODPROJ | Cód. Projeto | I |  |  | N |
| CODREQ | Requisito | I |  |  | N |
| CODSERV | Cód. Serviço | I |  |  | N |
| CODSERVRAIZ | Cód. Serv. Raiz | I |  |  | N |
| CODSIT | Cód. Status Sub-OS | I |  |  | N |
| CODUNNEXEC | Cód. un. negócio executante | I |  |  | S |
| CODUNNOS | Cód. un. negócio OS | I |  |  | S |
| CODUSU | Cód. Usuário Executante | I |  |  | N |
| CODUSUALTER | Cód. Usuário | I |  |  | N |
| CODUSUREM | Cód. Usuário Remetente | I |  |  | N |
| CODVEND | Cód.Vendedor | I |  |  | S |
| COMPLETUDE | Completude | I |  |  | N |
| CONTADORFLUXO | CONTADORFLUXO | I |  |  | N |
| CORSLA | Cor de SLA | I |  |  | N |
| DESCRCENCUS | Centro de Resultado | S |  |  | S |
| DESCSERV | Problema | S |  |  | N |
| DHEMAILVAL | Dh email validação | H |  |  | N |
| DHENTRADA | Dt Entrada Sub-OS | H |  |  | N |
| DHLIMITESLA | Data Limite do SLA | H |  |  | N |
| DHPAUSEAPP | Data Hora pausa aplicativo | H |  |  | N |
| DHPREVISTA | Agendamento | H |  |  | N |
| DHVALIDACAO | Dh validação | H |  |  | N |
| DTALTER | Dt. alteração do item | H |  |  | N |
| DTPREVFECHAMENTO | Data Limite Sub OS | H |  |  | N |
| EXECUTANTE | Executante (Painel de filtros) | S |  |  | S |
| GRAUDIFIC | Grau de dificuldade | I |  |  | N |
| HRFINAL | Hora Final | T |  |  | N |
| HRINICIAL | Hora Inicial | T |  |  | N |
| INICEXEC | Dt execução | D |  |  | N |
| INTERVALO | Intervalo | I |  |  | N |
| LIBERADO | Liberado | S |  |  | N |
| NUMETAPA | Num. Etapa | I |  |  | N |
| NUMFLUXO | Num. Fluxo | I |  |  | N |
| NUMITEM | Sub-OS | I |  |  | N |
| NUMOS | Núm. OS | I |  |  | N |
| NUNEGOCIACAO | Negociação | I |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | S |
| PRIORIDADE | Prioridade | I |  |  | N |
| PRODUTIVIDADE | Produtividade | F |  |  | S |
| QTDHORA | Qtd. Horas | T |  |  | N |
| QTDHORABONIF | Qtd. Horas Bonif. | F |  |  | N |
| QTDHORACOMBONIF | Qtd.Horas Com.Bonif. | F |  |  | N |
| QTDHORAEXT | Qtd. Horas Extras | F |  |  | N |
| QTDHORAEXTBONIF | Qtd. Horas Extras Bonif. | F |  |  | N |
| QTDHORAEXTCOMBONIF | Qtd.Horas Extras Com.Bonif. | F |  |  | N |
| QTDSERV | Quantidade serviço | F |  |  | N |
| RETRABALHO | Retrabalho | S |  |  | N |
| SEQFLUXO | Sequência do Fluxo | I |  |  | N |
| SERIE | Série | S |  |  | N |
| SOLUCAO | Solução | S |  |  | N |
| TEMPGASTO | Tempo gasto | I |  |  | N |
| TEMPOGASTOSLAPROC | TEMPOGASTOSLAPROC | I |  |  | N |
| TEMPOGASTOSLASUB | TEMPOGASTOSLASUB | I |  |  | N |
| TEMPPREVISTO | Tempo previsto para executar a OS | H |  |  | N |
| TERMEXEC | Término da execução | H |  |  | N |
| TIPO | Tipo | S |  |  | S |
| TIPOOS | Tipo OS | S | 15 |  | S |
| TURNO | Turno | I |  |  | N |
| VLRCOBRADO | Valor a ser cobrado | F |  |  | N |
| VLRCOMISSAO | Vlr. Comissão | F |  |  | S |
| VLRHORACOM | Vlr.Hora Comissão | F |  |  | N |
| VLRHORAEXTCOM | Vlr.Hora Extra Comissão | F |  |  | N |
| VLRHORAEXTFAT | Vlr. Hora Extra | F |  |  | N |
| VLRHORAFAT | Vlr. Hora | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMOS->NUMOS; SUBOS->NUMITEM | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | ControleVeiculo / [TGFCVE](TGFCVE.md) | NUMOS->NUMOS | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | ExecutanteRemetente / [TSIUSU](TSIUSU.md) | CODUSUREM->CODUSU | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | ExpectativaEsforcoPreVenda / [TCSEPV](TCSEPV.md) | CODEPV->CODEPV | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | ItemOSDocAssinados / [TCSITE_DAS](TCSITE_DAS.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | NUNEGOCIACAO->NUNEGOCIACAO | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | OcorrenciaOS / [TCSOOS](TCSOOS.md) | CODOCOROS->CODOCOROS | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | SituacaoSubOs / [TCSITS](TCSITS.md) | CODSIT->CODSIT | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | TempoSLA / [TCSSLT](TCSSLT.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| NotificacaoSonora / [TCSSNS](TCSSNS.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMOS->NUMOS | I |
| RegraSLASubOs / [TCSRSI](TCSRSI.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
