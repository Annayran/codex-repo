# TCSPXT — Produto por tipo de negociação pré-venda

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoPorNegociacaoPreVenda | Produto por Tipo de Negociação Pré-Venda | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| CODTPN | Cód. Tipo de Negociação Pré-Venda | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoPorNegociacaoPreVenda / [TCSPXT](TCSPXT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | ProdutoPorNegociacaoPreVenda / [TCSPXT](TCSPXT.md) | CODTPN->CODTPN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
