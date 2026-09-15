# TGFRAT — Rateio por Natureza de Receita e Despesa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RateioRecDesp | Rateio Receita/Despesa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCTACTB | Conta Contábil | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODSITE | Site | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| DTALTER | DTALTER | H |  |  | N |
| NUFIN | Num. Financeiro | I |  |  | N |
| NUMCONTRATO | Num. Contrato | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| PERCRATEIO | % Rateio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CriterioRateio / [TGFCRI](TGFCRI.md) | RateioRecDesp / [TGFRAT](TGFRAT.md) | CODCRITERIO->NUFIN; ORIGEMCRITERIO->ORIGEM | I |
| Financeiro / [TGFFIN](TGFFIN.md) | RateioRecDesp / [TGFRAT](TGFRAT.md) | NUFIN->NUFIN | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | RateioRecDesp / [TGFRAT](TGFRAT.md) | NUFIN->NUFIN | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | RateioRecDesp / [TGFRAT](TGFRAT.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
