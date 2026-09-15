# TGFCOI — Ítens de Conferência de Pedido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemConferenciaPedido | Ítens de Conferência de Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUCONF | Número único da conferência de pedidos | I |  |  | N |
| NUNOTAORIG | Número único de origem | I |  |  | N |
| QTD | Quantidade de origem | F |  |  | N |
| SEQUENCIA | Sequência do ítem da conferência do pedido | I |  |  | N |
| SEQUENCIAORIG | Sequência de origem da conferência de pedidos | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemConferenciaPedido / [TGFCOI](TGFCOI.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTAORIG->NUNOTA; SEQUENCIAORIG->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
