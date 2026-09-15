# TGFCRI — Critérios de Rateio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CriterioRateio | Critério de Rateio | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AUTOMATICO | Automático | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCRITERIO | Critério | I |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPROJ | Cód. Projeto | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| ORIGEMCRITERIO | Origem Critério | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contrato / [TCSCON](TCSCON.md) | CriterioRateio / [TGFCRI](TGFCRI.md) | CODCRITERIO->CODCRITERIO | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CriterioRateio / [TGFCRI](TGFCRI.md) | RateioRecDesp / [TGFRAT](TGFRAT.md) | CODCRITERIO->NUFIN; ORIGEMCRITERIO->ORIGEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
