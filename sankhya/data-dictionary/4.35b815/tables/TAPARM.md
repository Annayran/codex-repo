# TAPARM — Faturamento de Item de Apontamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FaturamentoItemApontamento | Faturamento Item de Apontamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUAPONTA | NUAPONTA | I |  |  | N |
| NUMITEM | NUMITEM | I |  |  | N |
| NURM | NURM | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturamentoItemApontamento / [TAPARM](TAPARM.md) | ItemApontamento / [TAPIAP](TAPIAP.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |
| FaturamentoItemApontamento / [TAPARM](TAPARM.md) | ItemMovimentoRM / [TAPIRM](TAPIRM.md) | NURM->NURM; SEQUENCIA->SEQUENCIA | I |
| ItemMovimentoRM / [TAPIRM](TAPIRM.md) | FaturamentoItemApontamento / [TAPARM](TAPARM.md) | NURM->NURM; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
