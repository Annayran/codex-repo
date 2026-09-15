# TGFCLP — Classificação por Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LigacaoClassificacaoProduto | Classificação por Produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLASSIF | Classificação | I |  |  | N |
| CODPROD | Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LigacaoClassificacaoProduto / [TGFCLP](TGFCLP.md) | ClassificacaoProduto / [TGFCLA](TGFCLA.md) | CODCLASSIF->CODCLASSIF | I |
| Produto / [TGFPRO](TGFPRO.md) | LigacaoClassificacaoProduto / [TGFCLP](TGFCLP.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
