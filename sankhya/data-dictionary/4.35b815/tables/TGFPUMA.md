# TGFPUMA — Unidade de Mov./Armazenagem

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeMovArmazenagemProduto | Unidade de Mov./Armazenagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Código de Barras | S |  |  | N |
| CODPROD | Código do Produto | I |  |  | N |
| CODUMA | U.M.A | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| PADRAO | Padrão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | UnidadeMovArmazenagemProduto / [TGFPUMA](TGFPUMA.md) | CODPROD->CODPROD | I |
| UnidadeMovArmazenagemProduto / [TGFPUMA](TGFPUMA.md) | UnidadeMovimentacaoArmazenagem / [TGFUMA](TGFUMA.md) | CODUMA->CODUMA | I |
| UnidadeMovArmazenagemProduto / [TGFPUMA](TGFPUMA.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
