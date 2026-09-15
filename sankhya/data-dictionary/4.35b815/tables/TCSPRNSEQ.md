# TCSPRNSEQ — Histórico de produtos da negociação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoProdutosNegociacao | Histórico de produtos da negociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód. Produto | I |  |  | N |
| NUMOS | Núm. da OS | I |  |  | N |
| SEQPROPOSTA | Sequência | I |  |  | N |
| VLRTOT | Valor Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | HistoricoProdutosNegociacao / [TCSPRNSEQ](TCSPRNSEQ.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
