# TGFALB — Álbum

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Album | Álbum | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUALBUM | Núm. Álbum | I |  |  | N |
| NUNOTA | Núm. Pedido do Evento | I |  |  | N |
| SEQNOTA | Seq. Pedido | I |  |  | N |
| STATUSALBUM | Status do Álbum | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemAlbum / [TGFIAL](TGFIAL.md) | Album / [TGFALB](TGFALB.md) | NUALBUM->NUALBUM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
