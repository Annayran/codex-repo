# TGIFIN — TGIFIN

- Módulo técnico: [TGI](../modules/TGI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FinanceiroPropostaComercial | Financeiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Natureza | I |  |  | N |
| NUPROJETO | NUPROJETO | I |  |  | N |
| RATEADO | RATEADO | S |  |  | S |
| TIPFIN | TIPFIN | S |  |  | N |
| VERSAOPROJETO | VERSAOPROJETO | S |  |  | N |
| VLRDESP | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | FinanceiroPropostaComercial / [TGIFIN](TGIFIN.md) | NUPROJETO->NUPROJETO; VERSAOPROJETO->VERSAOPROJETO | I |
| FinanceiroPropostaComercial / [TGIFIN](TGIFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
