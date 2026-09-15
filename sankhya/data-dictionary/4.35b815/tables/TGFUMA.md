# TGFUMA — Tabela de Unidades de Movimentação e Arnazenagem

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeMovimentacaoArmazenagem | Unidade Movimentação e Armazenagem | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUMA | Código da Unidade | I |  |  | N |
| DESCRUMA | Descrição da Unidade | S |  |  | N |
| PESO | Peso | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UnidadeMovArmazenagemProduto / [TGFPUMA](TGFPUMA.md) | UnidadeMovimentacaoArmazenagem / [TGFUMA](TGFUMA.md) | CODUMA->CODUMA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
