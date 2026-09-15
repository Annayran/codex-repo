# TCCFIN — Financeiro Civil

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FinanceiroCivil | Financeiro Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| NUFIN | NUFIN | I |  |  | N |
| NUFINORIG | NUFINORIG | I |  |  | N |
| NUMNEG | NUMNEG | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FinanceiroCivil / [TCCFIN](TCCFIN.md) | NegociacaoCivil / [TCCNEG](TCCNEG.md) | CODCC->CODCC; NUMNEG->NUMNEG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
