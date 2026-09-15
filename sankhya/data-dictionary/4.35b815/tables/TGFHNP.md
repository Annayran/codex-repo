# TGFHNP — TABLE TGFHNP

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoPedidosNaoPendentes | Histórico de Pedidos Não Pendentes | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHFIMEXEC | Dh. Fim Execução | H |  |  | N |
| DHINIEXEC | Dh. Ini. Execução | H |  |  | N |
| NUAGENDAMENTO | Nro. Único Agend. | I |  |  | N |
| NUHISTORICO | Nro. Único Histórico | I |  |  | N |
| QTDPEDIDOS | Qtd. Pedidos | I |  |  | N |
| QTDPEDNAOPEND | Qtd. Pedidos Não Pendentes | I |  |  | N |
| QTDPEDPEND | Qtd. Pedidos Pendentes | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalhePedidosNaoPendentes / [TGFDPN](TGFDPN.md) | HistoricoPedidosNaoPendentes / [TGFHNP](TGFHNP.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| HistoricoPedidosNaoPendentes / [TGFHNP](TGFHNP.md) | DetalhePedidosNaoPendentes / [TGFDPN](TGFDPN.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
