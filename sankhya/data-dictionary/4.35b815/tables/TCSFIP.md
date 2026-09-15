# TCSFIP — Detalhe natureza da proposta

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheNaturezaProposta | Detalhe natureza da proposta | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Cód. Natureza | I |  |  | N |
| DIAPGTO | Dia de Pagamento | I |  |  | N |
| DTENTRADA | Data de Entrada | H |  |  | N |
| NROCARTAO | Número do Cartão | S |  |  | N |
| NROPARCELAS | Número de Parcelas | I |  |  | N |
| NUMOS | Número da OS | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SEQNAT | Seq. Natureza | I |  |  | N |
| SEQPROPOSTA | Seq. Proposta | I |  |  | N |
| VALCARTAO | Validade do Cartão | H |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRENTRADA | Valor da Entrada | F |  |  | N |
| VLRFINANCIADO | Valor Financiado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheNaturezaProposta / [TCSFIP](TCSFIP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | DetalheNaturezaProposta / [TCSFIP](TCSFIP.md) | NUMOS->NUMOS; SEQPROPOSTA->SEQPROPOSTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
