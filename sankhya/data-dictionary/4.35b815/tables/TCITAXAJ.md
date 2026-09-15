# TCITAXAJ — Taxas de Ajustes de Depreciação

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TaxaDepreciacaoAjuste | Taxas de Ajustes de Depreciação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód.Bem | S |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| DTINICIO | Data Inicial | D |  |  | N |
| TAXA | Taxa | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Imobilizado / [TCIBEM](TCIBEM.md) | TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | CODPROD->CODPROD | I |
| TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
