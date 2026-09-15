# TRPMAP — Tabela de Mapa Replay

- Módulo técnico: [TRP](../modules/TRP.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MapaReplay | Mapa Replay | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMAPA | Cód. Mapa | I |  |  | N |
| DESCRMAPA | Descrição do Mapa | S |  |  | N |
| TABRAIZ | Tabela Raíz | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TabelaReplay / [TRPTAB](TRPTAB.md) | MapaReplay / [TRPMAP](TRPMAP.md) | CODMAPA->CODMAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
