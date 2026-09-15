# TCSPNP — Proposta negociação pré-venda

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PropostaNegociacaoPreVenda | Proposta negociação pré-venda | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRPROPOSTA | Descrição do cenário | S |  |  | N |
| DHALTER | Data da Alteração | H |  |  | N |
| IDPROPOSTA | Identificador Proposta | S |  |  | N |
| IMPRIMIU | Imprimiu | S |  |  | N |
| NUMOS | Número da OS | I |  |  | N |
| SEQPROPOSTA | Seq. Proposta | I |  |  | N |
| TEMFINANC | Financiamento? | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | DetalheNaturezaProposta / [TCSFIP](TCSFIP.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | HistoricoProdutosNegociacao / [TCSPRNSEQ](TCSPRNSEQ.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | HistoricoResumoPrecoNegociacao / [TCSRPNSEQ](TCSRPNSEQ.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | HistoricoSimulacaoPrecoNeg / [TCSSPNSEQ](TCSSPNSEQ.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
