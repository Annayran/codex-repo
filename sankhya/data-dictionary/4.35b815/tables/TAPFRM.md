# TAPFRM — Faturamento de RM

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
| FaturamentoRM | Faturamento de RM | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUFAT | NUFAT | I |  |  | N |
| NURM | NURM | I |  |  | N |
| PERCFAT | PERCFAT | F |  |  | N |
| TIPO | TIPO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturamentoRM / [TAPFRM](TAPFRM.md) | MovimentoRM / [TAPMRM](TAPMRM.md) | NURM->NURM | I |
| FaturamentoRM / [TAPFRM](TAPFRM.md) | ResumoFaturamento / [TAPFAT](TAPFAT.md) | NUFAT->NUFAT | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | FaturamentoRM / [TAPFRM](TAPFRM.md) | NUFAT->NUFAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
