# VWFSOLI — View Lista de Tarefas

- Módulo técnico: [VWF](../modules/VWF.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewSolicitacao | View Credencial  Solicitação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| COD_PROCESSO | Cód. Processo | I |  |  | N |
| CODUSUCANCEL | Responsável cancelamento | I |  |  | N |
| DHCONCLUSAO | Dh. conclusão da solicitação | H |  |  | N |
| DHINCLUSAO | Dh. da solicitação | H |  |  | N |
| EFIC_EXECUCAO | Eficiência de execução | I |  |  | N |
| EFIC_FILA | Eficiência de fila | I |  |  | N |
| IDINSTPRN | Nro Solicitação | I |  |  | N |
| IDPRNMESTRE | Nro Solicitação mestre | I |  |  | N |
| OBSCANCEL | Observação do cancelamento | S |  |  | N |
| PRAZO_SLA | Prazo do SLA | H |  |  | N |
| RESPONSAVEL_TAREFA_ATUAL | Responsável da tarefa atual | I |  |  | N |
| SITUACAOEXEC | Situação da solicitação | S |  |  | N |
| SOLICITANTE | Solicitante | I |  |  | N |
| STATUS_SLA | Status do SLA | S |  |  | N |
| TAREFA_ATUAL | Tarefa atual | S |  |  | N |
| TEMPO_APONTAMENTO | Tempo de apontamento | I |  |  | N |
| TEMPO_ATENDIMENTO | Tempo de atendimento | I |  |  | N |
| TEMPO_EXECUCAO | Tempo de execução | I |  |  | N |
| TEMPO_FILA | Tempo de fila | I |  |  | N |
| VERSAO | Versão | I |  |  | N |
| VERSAO_ATUAL | Versão atual do processo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | COD_PROCESSO->CODPRN; VERSAO->VERSAO | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | Usuario / [TSIUSU](TSIUSU.md) | SOLICITANTE->CODUSU | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RESPONSAVEL_TAREFA_ATUAL->CODUSU | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUCANCEL->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
