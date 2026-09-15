# TGFPRD — Produto Data

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoData | Produto Data | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| COMGER | % Comissão do Gerente | F |  |  | N |
| COMVEND | % Comissão de Vendedor | F |  |  | N |
| DTIMPORT | Data de Importação | H |  |  | N |
| MARGLUCRO | % Margem de Lucro | F |  |  | N |
| PERCCUSFIXO | % Custo Fixo | F |  |  | N |
| ROYALT | Royalt | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | ProdutoData / [TGFPRD](TGFPRD.md) | CODPROD->CODPROD | I |
| ProdutoData / [TGFPRD](TGFPRD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoData / [TGFPRD](TGFPRD.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoData / [TGFPRD](TGFPRD.md) | TarifasCIP /  | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ProdutoData / [TGFPRD](TGFPRD.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
