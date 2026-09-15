# TGFPPD — Pedidos Pendentes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PedidosPendentes | Pedidos Pendentes | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| DTPREVCHEGADA | Previsão Chegada | D |  |  | N |
| NUMPEDIDO2 | Número pedido | S |  |  | N |
| NUNOTA | Núm. Único (Pedido) | I |  |  | N |
| NUPCA | Núm. Planejamento | I |  |  | N |
| QTD | Quantidade | F |  |  | N |
| SEQPERIODO | Período | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| TIPMOV | Tipo de Movimento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | PedidosPendentes / [TGFPPD](TGFPPD.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |
| PedidosPendentes / [TGFPPD](TGFPPD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
