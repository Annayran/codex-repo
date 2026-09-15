# TGPTPR — TABLE TGPTPR

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProcedimentosTarefas | Procedimentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROCEDIMENTO | Cód. Procedimento | I |  |  | N |
| CODTAREFA | CODTAREFA | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DTFIM | Data Final | D |  |  | N |
| DTINICIO | Data Início | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescricaoTarefas / [TGPTAR](TGPTAR.md) | ProcedimentosTarefas / [TGPTPR](TGPTPR.md) | CODTAREFA->CODTAREFA | I |
| ProcedimentosTarefas / [TGPTPR](TGPTPR.md) | Procedimentos / [TGPPRC](TGPPRC.md) | CODPROCEDIMENTO->CODPROCEDIMENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
