# TCSNXT — Natureza por tipo de negociação pré-venda

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
| NaturezaPorNegociacaoPreVenda | Natureza por Tipo Negociação Pré-Venda | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Cód. Natureza | I |  |  | N |
| CODTPN | Cód. Tipo de Negociação Pré-Venda | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NaturezaPorNegociacaoPreVenda / [TCSNXT](TCSNXT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | NaturezaPorNegociacaoPreVenda / [TCSNXT](TCSNXT.md) | CODTPN->CODTPN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
