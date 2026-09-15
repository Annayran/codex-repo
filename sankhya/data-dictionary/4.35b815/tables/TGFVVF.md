# TGFVVF — Vínculo do Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VinculoVerbaFinanceiro | Vínculo do financeiro da verba | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| GERADO | Receita/Despesa gerada pelo vinculo | I |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| NUVINCULO | Nro. Vínculo | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VinculoVerbaFinanceiro / [TGFVVF](TGFVVF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
