# TGFIPC — Itens Planejamento de Compra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItensPlanejamentoCompra | Itens do Planejamento de Compra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| COMPRA | Pedido | F |  |  | N |
| COMPRAPREVISTA | Pedido Revisto | F |  |  | N |
| DEMANDA | Demanda | F |  |  | N |
| DESVIO | Desvio | F |  |  | N |
| ESTFISICO | Estoque Fisico | F |  |  | N |
| ESTFUTURO | Estoque Futuro | F |  |  | N |
| ESTFUTUROAJU | Estoque Futuro Ajustado | F |  |  | N |
| ESTPREVISTO | Estoque Previsto | F |  |  | N |
| ESTSEG | Estoque Segurança | F |  |  | N |
| GIROMED | Giro Médio | F |  |  | N |
| GIROMEDREC | Giro Médio Recente | F |  |  | N |
| LEADTIME | Lead Time | F |  |  | N |
| LOTECOMPRAS | Lote Compras | F |  |  | N |
| NUPCA | Núm. Planejamento | I |  |  | N |
| PEDCOMPEN | Pedidos Compra Pendente | F |  |  | N |
| PEDEFETIVO | Pedido Efetivo | F |  |  | N |
| PEDVENPEN | Pedidos Venda Pendente | F |  |  | N |
| RUPACEITAVEL | Ruptura Aceitável | I |  |  | N |
| RUPCRITICA | Ruptura Critica | I |  |  | N |
| SUGCOMPRA | Sugestão Compra | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | InformacoesPeriodosAnteriores / [TGFIPA](TGFIPA.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | PedidosComplementares / [TGFPCL](TGFPCL.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | PedidosPendentes / [TGFPPD](TGFPPD.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | ProjecaoDeCompra / [TGFPCP](TGFPCP.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |
| PlanejamentoCompra / [TGFPCA](TGFPCA.md) | ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | NUPCA->NUPCA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
