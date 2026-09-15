# TGFTOPNDC — Tipo de Operação por Nota de Débito/Crédito (Reforma Tributária)

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TCOMNotaDebCred | Tipo de Operação por Nota de Débito/Crédito (Reforma Tributária) | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPOPER | Tipo de Operação | I |  |  | N |
| DESCRTPNF | Tipo de Nota | S |  |  | N |
| FINNFE | Finalidade NF-e | S |  |  | N |
| TPNF | Tipo de Nota | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TCOMNotaDebCred / [TGFTOPNDC](TGFTOPNDC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
