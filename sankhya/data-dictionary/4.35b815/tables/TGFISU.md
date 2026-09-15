# TGFISU — IPI Suspenso

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| IpiSuspenso | IPI Suspenso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODENQIPIENT | Cód. Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Cód. Enq. Legal IPI Saída | I |  |  | N |
| CODIPI | Código do IPI | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CSTIPIENT | Cód.Sit.Trib.IPI Entrada | I |  |  | S |
| CSTIPISAI | Cód.Sit.Trib.IPI Saída | I |  |  | S |
| DHTIPOPER | Dh. Operação | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaIPI / [TGFIPI](TGFIPI.md) | IpiSuspenso / [TGFISU](TGFISU.md) | CODIPI->CODIPI | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | IpiSuspenso / [TGFISU](TGFISU.md) | CODIPI->CODIPI | I |
| IpiSuspenso / [TGFISU](TGFISU.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| IpiSuspenso / [TGFISU](TGFISU.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| IpiSuspenso / [TGFISU](TGFISU.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
