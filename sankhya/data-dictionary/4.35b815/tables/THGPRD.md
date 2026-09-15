# THGPRD — Hidrogeologia Relatório de Produção

- Módulo técnico: [THG](../modules/THG.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelatorioProducao | Hidrogeologia Relatório de Produção | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPRD | CODPRD | I |  |  | N |
| CODPROJ | CODPROJ | I |  |  | N |
| CODUSUCOORCAMPO | CODUSUCOORCAMPO | I |  |  | N |
| CODUSUCOORPROJETO | CODUSUCOORPROJETO | I |  |  | N |
| DATAINICIO | DATAINICIO | H |  |  | N |
| OBSERVACAO | OBSERVACAO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RelatorioProducao / [THGPRD](THGPRD.md) | ItemRelatorioProducao / [THGITE](THGITE.md) | CODPRD->CODPRD | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCOORCAMPO->CODUSU | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUCOORPROJETO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
