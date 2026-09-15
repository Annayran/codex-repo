# TCCCOM — Compradores Civil

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CompradoresCivil | Compradores Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| PERCOMPRA | Percentual Compra | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CompradoresCivil / [TCCCOM](TCCCOM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | CompradoresCivil / [TCCCOM](TCCCOM.md) | CODCC->CODCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
