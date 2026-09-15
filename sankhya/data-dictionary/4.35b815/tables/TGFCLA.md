# TGFCLA — Classificação do Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClassificacaoProduto | Classificação do Produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLASSIF | Classificação | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LigacaoClassificacaoProduto / [TGFCLP](TGFCLP.md) | ClassificacaoProduto / [TGFCLA](TGFCLA.md) | CODCLASSIF->CODCLASSIF | I |
| ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | ClassificacaoProduto / [TGFCLA](TGFCLA.md) | CODCLASSIF->CODCLASSIF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
