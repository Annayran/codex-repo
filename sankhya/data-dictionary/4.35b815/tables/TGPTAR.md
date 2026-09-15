# TGPTAR — TABLE TGPTAR

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescricaoTarefas | Descrição Tarefas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODESCALA | Cód. Escala | I |  |  | N |
| CODTAREFA | Cód. Tarefa | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DESCRTAREFA | Descrição | S |  |  | N |
| DHALTER | DHALTER | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescricaoTarefas / [TGPTAR](TGPTAR.md) | Escala / [TGPESC](TGPESC.md) | CODESCALA->CODESCALA | I |
| DescricaoTarefas / [TGPTAR](TGPTAR.md) | ProcedimentosTarefas / [TGPTPR](TGPTPR.md) | CODTAREFA->CODTAREFA | I |
| Tarefas / [TGPFCT](TGPFCT.md) | DescricaoTarefas / [TGPTAR](TGPTAR.md) | CODTAREFA->CODTAREFA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
