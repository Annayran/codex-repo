# VWFTAR — View Lista de Tarefas

- Módulo técnico: [VWF](../modules/VWF.md)
- Campos: 35
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewListaTarefa | View Lista de Tarefas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APPOINTMENT_STARTED | Apontamento Iniciado | I |  |  | N |
| COD_PROCESSO | Processo | I |  |  | N |
| CODUSUCANCEL | Responsável cancelamento | I |  |  | N |
| CODUSUDONO | Responsável da tarefa | I |  |  | N |
| CODUSUINC | Usuário inclusão | I |  |  | N |
| DHACEITE | Início da tarefa | H |  |  | N |
| DHCONCLUSAO | Dh. conclusão da tarefa | H |  |  | N |
| DHCONCLUSAO_PROCESSO | Dh. conclusão da solicitação | H |  |  | N |
| DHCRIACAO | Dh. criação da tarefa | H |  |  | N |
| DHINCLUSAO | Dh. da solicitação | H |  |  | N |
| EFIC_EXECUCAO | Eficiência de execução | I |  |  | N |
| EFIC_FILA | Eficiência de fila | I |  |  | N |
| IDELEMENTO | Id Elemento | S |  |  | N |
| IDINSTPRN | Nro. Solicitação | I |  |  | N |
| IDINSTTAR | Id Instância da Tarefa | I |  |  | N |
| IS_APPOINTMENT | Tipo apontamento | S |  |  | N |
| IS_APPOINTMENTCRUD | Tipo manipulação apontamento | S |  |  | N |
| NOME_PROCESSO | Nome processo | S |  |  | N |
| NOME_TAREFA | Tarefa | S |  |  | N |
| NUGRUPO | Grupo processo | I |  |  | N |
| OBSCANCEL | Observação do cancelamento | S |  |  | N |
| PRAZO_SLA | Prazo do SLA | H |  |  | N |
| PRAZO_TAREFA | Prazo da Tarefa | H |  |  | N |
| SITUACAOEXEC | Situação da solicitação | S |  |  | N |
| SOLICITANTE | Solicitante | I |  |  | N |
| STATUS_SLA | Status do SLA | S |  |  | N |
| STATUS_TAREFA | Status da Tarefa | S |  |  | N |
| SUSPENSO_SLA | SLA Suspenso | S |  |  | N |
| TEMPO_APONTAMENTO | Tempo de apontamento | I |  |  | N |
| TEMPO_ATENDIMENTO | Tempo de atendimento | I |  |  | N |
| TEMPO_DECORRIDO | Tempo decorrido | I |  |  | N |
| TEMPO_EXECUCAO | Tempo de execução | I |  |  | N |
| TEMPO_FILA | Tempo de fila | I |  |  | N |
| VERSAO_ATUAL | Versão atual do processo | I |  |  | N |
| VERSAO_PROCESSO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | GrupoProcesso / [TWFGRU](TWFGRU.md) | NUGRUPO->NUGRUPO | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | COD_PROCESSO->CODPRN; VERSAO_PROCESSO->VERSAO | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | SOLICITANTE->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUDONO->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario4 / [TSIUSU](TSIUSU.md) | CODUSUCANCEL->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
