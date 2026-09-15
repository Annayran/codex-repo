# TCSTPN — Tipo de Negociação Pré-venda

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoNegociacaoPreVenda | TipoNegociacaoPreVenda | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTPN | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemServico / [TCSOSE](TCSOSE.md) | TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | CODTPN->CODTPN | I |
| TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | CODTPN->CODTPN | I |
| TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | NaturezaPorNegociacaoPreVenda / [TCSNXT](TCSNXT.md) | CODTPN->CODTPN | I |
| TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | ProdutoPorNegociacaoPreVenda / [TCSPXT](TCSPXT.md) | CODTPN->CODTPN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
