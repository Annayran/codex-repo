# TGFDPN — TABLE TGFDPN

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalhePedidosNaoPendentes | Detalhe de Pedidos Não Pendentes | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| NUAGENDAMENTO | NUAGENDAMENTO | I |  |  | N |
| NUDETALHE | NUDETALHE | I |  |  | N |
| NUHISTORICO | NUHISTORICO | I |  |  | N |
| NUNOTA | Nro. Único Pedido | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| STATUS | STATUS | S |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhePedidosNaoPendentes / [TGFDPN](TGFDPN.md) | HistoricoPedidosNaoPendentes / [TGFHNP](TGFHNP.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| HistoricoPedidosNaoPendentes / [TGFHNP](TGFHNP.md) | DetalhePedidosNaoPendentes / [TGFDPN](TGFDPN.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
