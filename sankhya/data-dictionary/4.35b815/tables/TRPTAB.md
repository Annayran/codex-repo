# TRPTAB — Tabela Replay

- Módulo técnico: [TRP](../modules/TRP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TabelaReplay | Tabela Replay | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMAPA | Cód. Mapa | I |  |  | N |
| NULIGACAO | Num. Ligação | I |  |  | N |
| TABDESTINO | Tabela de Destino | S |  |  | N |
| TABORIGEM | Tabela de Origem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoReplay / [TRPCAM](TRPCAM.md) | TabelaReplay / [TRPTAB](TRPTAB.md) | NULIGACAO->NULIGACAO | I |
| TabelaReplay / [TRPTAB](TRPTAB.md) | MapaReplay / [TRPMAP](TRPMAP.md) | CODMAPA->CODMAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
