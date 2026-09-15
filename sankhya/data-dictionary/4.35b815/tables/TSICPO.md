# TSICPO — Configuração de Pedido Offline

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoPedidoOffline | Configuração de Pedido Offline | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAPTURAGEO | Capturar geolocalização | S |  |  | N |
| CODCONFIG | Cód. Config | I |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| DESCRCONFIG | Descrição | S |  |  | N |
| FOTOPRODUTO | Sincronizar fotos dos produtos | S |  |  | N |
| PADRAO | Padrão | S |  |  | N |
| PARCEIROEXP | Parceiros | S |  |  | N |
| PRODUTOEXP | Produtos | S |  |  | N |
| TIPONEGOCIACAOEXP | Tipos de Negociação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoPedidoOffline / [TSICPO](TSICPO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoPedidoOffline / [TSICPO](TSICPO.md) | PedidoOfflineModelo / [TSIPOM](TSIPOM.md) | CODCONFIG->CODCONFIG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
