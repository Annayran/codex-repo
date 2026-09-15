# THGITE — Hidrogeologia Itens do Relatório de produção

- Módulo técnico: [THG](../modules/THG.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemRelatorioProducao | Hidrogeologia Itens do Relatório de produção | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOTIVOPARADA | CODMOTIVOPARADA | I |  |  | N |
| CODPRD | CODPRD | I |  |  | N |
| COORDX | COORDX | F |  |  | N |
| COORDY | COORDY | F |  |  | N |
| DTCOLETAAMOSTRA | DTCOLETAAMOSTRA | H |  |  | N |
| DTDESENVOLVIMENTO | DTDESENVOLVIMENTO | H |  |  | N |
| DTENVIOAMOSTRA | DTENVIOAMOSTRA | H |  |  | N |
| DTMEDICAOFQ | DTMEDICAOFQ | H |  |  | N |
| PM | PM | S |  |  | N |
| SLUG_CODMOTIVOPARADA | SLUG_CODMOTIVOPARADA | I |  |  | N |
| SLUG_DATA | SLUG_DATA | H |  |  | N |
| ZCOTA | ZCOTA | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemRelatorioProducao / [THGITE](THGITE.md) | MotivoParada / [THGMTV](THGMTV.md) | CODMOTIVOPARADA->CODMOTIVO | I |
| ItemRelatorioProducao / [THGITE](THGITE.md) | MotivoParada2 / [THGMTV](THGMTV.md) | SLUG_CODMOTIVOPARADA->CODMOTIVO | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | ItemRelatorioProducao / [THGITE](THGITE.md) | CODPRD->CODPRD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
