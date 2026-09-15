# TSITAR — Tarefas Agendadas

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TarefaAgendada | Tarefas Agendadas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODREL | Cód.Relatório | I |  |  | N |
| CODROT | Cód.Rota | I |  |  | N |
| CODTAR | Cód.Tarefa | I |  |  | N |
| DESCRTAR | Descrição da Tarefa | S |  |  | N |
| DTEXEC | Data de Execução | H |  |  | N |
| DTULTIMA | Data da Última Execução | H |  |  | N |
| FILTROS | Filtro | S |  |  | N |
| FREQUENCIA | Frenquência de Execução | S |  |  | N |
| HREXEC | Hora de Execução | H |  |  | N |
| HRULTIMA | Hora da Última Execução | H |  |  | N |
| MINUTOS | Tempo de Execução | I |  |  | N |
| PARAMETRO | Parâmetro da Tarefa | S |  |  | N |
| PROGRAMACAO | Programação | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TabelaRotina / [TSIROT](TSIROT.md) | TarefaAgendada / [TSITAR](TSITAR.md) | CODROT->CODROT | I |
| TarefaAgendada / [TSITAR](TSITAR.md) | FormatadorRelatorio / [TSIIMP](TSIIMP.md) | CODREL->CODREL | I |
| TarefaAgendada / [TSITAR](TSITAR.md) | TabelaRotina / [TSIROT](TSIROT.md) | CODROT->CODROT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
