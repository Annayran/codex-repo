# TGFFDM — Fórmulas para Cálculo de Desconto Máximo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaCalcDescMax | Fórmula para Cálculo de Desconto Máximo | S | N |
| FormulaCalcDescMaxItens | Fórmula para Cálculo de Desconto Máximo Itens | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORM | Código | I |  |  | N |
| DESCRFORM | Descrição da Fórmula | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoNegociacao / [TGFTPV](TGFTPV.md) | FormulaCalcDescMax / [TGFFDM](TGFFDM.md) | CODFORMDESCMAX->CODFORM | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | FormulaCalcDescMaxItens / [TGFFDM](TGFFDM.md) | CODFORMDESCMAXITENS->CODFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
