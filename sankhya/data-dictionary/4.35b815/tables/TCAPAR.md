# TCAPAR — Parcelamento do Vencimento

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParcelamentoVencimento | Parcelamento do Vencimento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCUR | Cód.Curso | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODNAT | Cód.Natureza | I |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| CODTIPTIT | Cód.Tipo de título | I |  |  | N |
| DTVENC | Data Vencimento | H |  |  | N |
| PARCELA | Parcela | S |  |  | N |
| TEMDESCONTO | Tem Desconto? | S |  |  | N |
| TSI_CODEMP | Cód.Empresa (TSIEMP) | I |  |  | N |
| VLRDESDOB | Valor do Desdobramento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | CODANO->CODANO | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| Serie / [TCASER](TCASER.md) | ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | CODCUR->CODCUR; CODSER->CODSER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
