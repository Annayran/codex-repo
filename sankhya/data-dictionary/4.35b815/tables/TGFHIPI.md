# TGFHIPI — Histórico Alíquotas de IPI

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoAliquotaIPI | Historico Alíquota de IPI | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | Alíquota ICMS | F |  |  | N |
| ALIQII | Aliq. II | F |  |  | N |
| CODENQIPIENT | Cód. Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Cód. Enq. Legal IPI Saída | I |  |  | N |
| CODEXII | Cód. Exceção II | I |  |  | N |
| CODEXNCM | Cód. Exceção NCM | I |  |  | N |
| CODFISIPI | Código Fiscal (NPC, NBM) | S |  |  | N |
| CODIPI | Código do IPI | I |  |  | N |
| CSTIPIENT | Cód.Sit.Trib.IPI Entrada | I |  |  | N |
| CSTIPISAI | Cód.Sit.Trib.IPI Saída | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Data e hora alteração | H |  |  | N |
| ID | Código do IPI | I |  |  | N |
| PERCCOFINS | % COFINS | F |  |  | N |
| PERCCSSL | % CSLL | F |  |  | N |
| PERCENTUAL | Percentual | F |  |  | N |
| PERCPIS | % PIS | F |  |  | N |
| PERCREDVLRIPI | % Redução de Vlr. IPI | F |  |  | N |
| VLRPAUTA | Valor de Pauta | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | IpiNCM / [TGFIPN](TGFIPN.md) | CODIPI->CODIPI | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | CODIPI->CODIPI | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | IpiSuspenso / [TGFISU](TGFISU.md) | CODIPI->CODIPI | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODIPI->CODIPI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
