# TGPFCT — TABLE TGPFCT

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Tarefas | Tarefas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFUNCAO | Cód. Função | I |  |  | N |
| CODTAREFA | Cód. Tarefa | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DTFIM | Data Final | D |  |  | N |
| DTINICIO | Data Início | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Tarefas / [TGPFCT](TGPFCT.md) | DescricaoTarefas / [TGPTAR](TGPTAR.md) | CODTAREFA->CODTAREFA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
