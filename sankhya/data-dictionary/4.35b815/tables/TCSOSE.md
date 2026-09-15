# TCSOSE — Ordem de Serviço

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 56
- Instâncias DWF: 1
- Relações DWF: 30
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OrdemServico | Ordem de Serviço | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BAIRRO | Bairro | S |  |  | N |
| CIDADE | Cidade | S |  |  | N |
| CODATEND | Atendente | I |  |  | N |
| CODBEM | Bem | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCONTATO | Cód. Contato | I |  |  | N |
| CODCONTATOPAP | Cód. Contato Prospect | I |  |  | N |
| CODCOS | Cód. Status OS | I |  |  | N |
| CODCOSANT | CODCOSANT | I |  |  | N |
| CODOAT | Cód. Origem de Atendimento | I |  |  | N |
| CODPAP | Cód. Prospect | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCATEND | Cód. Parceiro Atendido | I |  |  | N |
| CODPLA | Plano de Ação | I |  |  | N |
| CODPROCMAHA | Processo/Procedimento Maha | I |  |  | N |
| CODSERVFLUXO | CODSERVFLUXO | I |  |  | N |
| CODTPN | CODTPN | I |  |  | N |
| CODUSUALTER | Cód. Usuário Alteração | I |  |  | N |
| CODUSUFECH | Cód. Usuário Fechamento | I |  |  | N |
| CODUSURESP | Cód. Usuário Responsável | I |  |  | N |
| CODUSUSOLICITANTE | Cód. Usuário Solicitante | I |  |  | N |
| CODVEND | Gerente de Contas | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| CONTATO | Contato | S |  |  | N |
| DESCRICAO | Problema | S |  |  | N |
| DHCHAMADA | Dt/Hr Chamada | H |  |  | N |
| DHFECHAMENTOSLA | Fechamento SLA | H |  |  | N |
| DTALTER | Dt alteração | H |  |  | N |
| DTFECHAMENTO | Data de fechamento | H |  |  | N |
| DTPREVISTA | Dt/Hr limite OS | H |  |  | N |
| ENDERECO | Endereço | S |  |  | N |
| ETAPANEG | Etapa | S |  |  | S |
| IDENTIFICADOR | Identificador | S |  |  | N |
| IMPACTO | Impacto | S |  |  | N |
| MODELOVISIVELAPPOS | Modelo visível App OS | S |  |  | N |
| NOMECONTATO | Nome Contato | S |  |  | N |
| NOMEMODELO | Nome do Modelo | S |  |  | N |
| NUFAP | Número Unico FAP | I |  |  | N |
| NUMCONTRATO | Núm. Contrato | I |  |  | N |
| NUMETAPA | Número Etapa | I |  |  | N |
| NUMOS | Núm. da OS | I |  |  | N |
| NUMOSCLIENTE | Núm. OS Cliente | S |  |  | N |
| NUMOSRELACIONADA | OS Relacionada | I |  |  | N |
| NUNOTA | Nro.único do pedido | I |  |  | N |
| POSSUISLA | Possui SLA | S |  |  | N |
| SERIE | Série | S |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| STATUSNEG | Status | S |  |  | S |
| TELCONTATO | Tel. Contato | S |  |  | N |
| TEMPGASTO | Tempo gasto na OS | I |  |  | N |
| TEMPOGASTOSLA | Tempo Gasto SLA | I |  |  | N |
| TEMPOSLA | Tempo SLA | I |  |  | N |
| TEMPPREVISTO | Tempo previsto | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| URGENCIA | Urgência | S |  |  | N |
| VARIACAOFLUXO | VARIACAOFLUXO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Atendente / [TSIUSU](TSIUSU.md) | CODATEND->CODUSU | L |
| OrdemServico / [TCSOSE](TCSOSE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ClassificacaoOs / [TCSCOS](TCSCOS.md) | CODCOS->CODCOS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | L |
| OrdemServico / [TCSOSE](TCSOSE.md) | ContatoProspect / [TCSCTT](TCSCTT.md) | CODCONTATOPAP->CODCONTATO; CODPAP->CODPAP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | FluxoDiagnosticoOS / [TCSFXO](TCSFXO.md) | NUMOS->NUMOS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMOS->NUMOS | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | OrigemAtendimento / [TCSOAT](TCSOAT.md) | CODOAT->CODOAT | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ParceiroAtendido / [TGFPAR](TGFPAR.md) | CODPARCATEND->CODPARC | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ProcessoProcedimento / [TCSPEP](TCSPEP.md) | CODPROCMAHA->CODPROC | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | L |
| OrdemServico / [TCSOSE](TCSOSE.md) | Solicitante / [TSIUSU](TSIUSU.md) | CODUSUSOLICITANTE->CODUSU | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | CODTPN->CODTPN | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ProdutoNegociacao / [TCSPRN](TCSPRN.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ResumoPrecoNegociacao / [TCSRPN](TCSRPN.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| SimulacaoPrecoNegociacao / [TCSSPN](TCSSPN.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ViewContatosOS / [VGFCOS](VGFCOS.md) | OrdemServico / [TCSOSE](TCSOSE.md) | CODCONTATO->CODCONTATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
