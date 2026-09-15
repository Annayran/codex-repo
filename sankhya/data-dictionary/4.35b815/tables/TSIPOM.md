# TSIPOM — Pedido Offline Modelo

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PedidoOfflineModelo | Pedido Offline Modelo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONFIG | Cód. Config | I |  |  | N |
| DESCRNOTA | Descrição do Modelo | S |  |  | N |
| NUNOTA | Nro. Único do Pedido | I |  |  | N |
| PADRAO | Padrão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoPedidoOffline / [TSICPO](TSICPO.md) | PedidoOfflineModelo / [TSIPOM](TSIPOM.md) | CODCONFIG->CODCONFIG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
