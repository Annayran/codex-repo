# TAPIRM — Item do Movimento de RM

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemMovimentoRM | Item do Movimento de RM | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERV | CODSERV | I |  |  | N |
| CODVOL | CODVOL | S |  |  | N |
| NURM | NURM | I |  |  | N |
| OBSERVACAO | OBSERVACAO | S |  |  | N |
| QTDSERV | QTDSERV | F |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TOTAL | Valor total do item | I |  |  | S |
| VLRUNI | VLRUNI | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturamentoItemApontamento / [TAPARM](TAPARM.md) | ItemMovimentoRM / [TAPIRM](TAPIRM.md) | NURM->NURM; SEQUENCIA->SEQUENCIA | I |
| ItemMovimentoRM / [TAPIRM](TAPIRM.md) | FaturamentoItemApontamento / [TAPARM](TAPARM.md) | NURM->NURM; SEQUENCIA->SEQUENCIA | I |
| ItemMovimentoRM / [TAPIRM](TAPIRM.md) | MovimentoRM / [TAPMRM](TAPMRM.md) | NURM->NURM | I |
| ItemMovimentoRM / [TAPIRM](TAPIRM.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | ItemMovimentoRM / [TAPIRM](TAPIRM.md) | NURM->NURM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
