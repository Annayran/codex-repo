# VWFITAR — Instância de Tarefas

- Módulo técnico: [VWF](../modules/VWF.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewInstanciaTarefa | View Instância de Tarefas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PROCESSCREATETIME | Dh. criação do Processo | H |  |  | N |
| PROCESSDOCUMENTATION | Documentação do Processo | S |  |  | N |
| PROCESSENDTIME | Dh. conclusão do Processo | H |  |  | N |
| PROCESSID | Cód. Processo | I |  |  | N |
| PROCESSINSTANCEID | Id Instância do Processo | I |  |  | N |
| PROCESSNAME | Nome do Processo | S |  |  | N |
| PROCESSUSERCREATECOD | Cód. Usuário solicitante | I |  |  | N |
| PROCESSUSERCREATENAME | Usuário solicitante | S |  |  | N |
| PROCESSVERSION | Versão | I |  |  | N |
| PROCESSXML | XML do Processo | S |  |  | N |
| TASKCREATETIME | Dh. criação | H |  |  | N |
| TASKDOCUMENTATION | Documentação da Tarefa | S |  |  | N |
| TASKENDTIME | Dh. conclusão | H |  |  | N |
| TASKIDELEMENTO | Id do Elemento da Tarefa | S |  |  | N |
| TASKINSTANCEID | Id da Instância da Tarefa | I |  |  | N |
| TASKNAME | Nome da Tarefa | S |  |  | N |
| TASKSTARTTIME | Dh. aceite | H |  |  | N |
| TASKUSERASSIGNEECOD | Cód. Usuário responsável | I |  |  | N |
| TASKUSERASSIGNEENAME | Usuário responsável | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewInstanciaTarefa / [VWFITAR](VWFITAR.md) | ElementoProcesso / [TWFELE](TWFELE.md) | PROCESSID->CODPRN; PROCESSVERSION->VERSAO; TASKIDELEMENTO->IDELEMENTO | I |
| ViewInstanciaTarefa / [VWFITAR](VWFITAR.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | PROCESSID->CODPRN; PROCESSVERSION->VERSAO | I |
| ViewInstanciaTarefa / [VWFITAR](VWFITAR.md) | Usuario / [TSIUSU](TSIUSU.md) | PROCESSUSERCREATECOD->CODUSU; TASKUSERASSIGNEECOD->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
