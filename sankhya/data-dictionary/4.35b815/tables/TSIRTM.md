# TSIRTM — Mestre de Retorno

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MestreRetorno | Mestre de Retorno | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Cód.Layout | I |  |  | N |
| POSINITIPREG | Pos Ini Tipo Registo | I |  |  | N |
| SP_CHAMADA | Stored Chamada | S |  |  | N |
| TAMREG | Tamanho Registro | I |  |  | N |
| TIPREG | Tipo de Registro | S |  |  | N |
| TITULO | Título do Layout | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheRetorno / [TSIRTD](TSIRTD.md) | MestreRetorno / [TSIRTM](TSIRTM.md) | CODIGO->CODIGO; TIPREG->TIPREG | I |
| MestreRetorno / [TSIRTM](TSIRTM.md) | DetalheRetorno / [TSIRTD](TSIRTD.md) | CODIGO->CODIGO; TIPREG->TIPREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
