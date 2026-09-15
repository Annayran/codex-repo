# TCSCNV — Comissão de Negociação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComissaoNegociacao | Comissão de Vendedores por Negociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Natureza | I |  |  | N |
| CODUSUALTER | Usuário Alteração | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| COMISSAO | Comissão (%) | F |  |  | N |
| DHALTER | Data Alteração | H |  |  | N |
| GRIDMULT | Multiplicador | I |  |  | S |
| NUCOMISSAO | Núm. Comissão | I |  |  | N |
| NUMOS | Núm. OS | I |  |  | N |
| VLRBASE | Vlr Base | F |  |  | S |
| VLRTOTCOMISSAO | Total Comissão | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | L |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
