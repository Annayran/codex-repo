# TGFFPI — Fórmula p/ Parcelas Independentes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaParcelasIndependentes | Fórmula p/ Parcelas Independentes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGUF | Código GNRE | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPRODGNRE | Código do Produto (GNRE) | I |  |  | N |
| CODRECGNRE | Código da Receita (GNRE) | I |  |  | N |
| CODTIPTIT | Tipo de Título | I |  |  | N |
| CODUF | UF | I |  |  | N |
| FINPARAGNRE | Financeiro para GNRE | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| FORMULADOIS | Fórmula 2 | S |  |  | N |
| INDVALDOIS | Indicador de valor 2 | I |  |  | N |
| INDVALUM | Indicador de valor 1 | I |  |  | N |
| RECDESP | Receita ou Despesa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | GNREUnidadeFederativa / [TSIGUF](TSIGUF.md) | CODGUF->CODGUF | I |
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
