# TCSRPNSEQ — Histórico resumo preço negociação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoResumoPrecoNegociacao | Histórico resumo preço negociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Cód. Natureza | I |  |  | N |
| GRIDMULT | GRIDMULT | I |  |  | N |
| NUMOS | Núm. da OS | I |  |  | N |
| SEQPROPOSTA | Sequência | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRIMP | Vlr. Impostos | F |  |  | N |
| VLRLIQ | Vlr. Líquido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoResumoPrecoNegociacao / [TCSRPNSEQ](TCSRPNSEQ.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | HistoricoResumoPrecoNegociacao / [TCSRPNSEQ](TCSRPNSEQ.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
