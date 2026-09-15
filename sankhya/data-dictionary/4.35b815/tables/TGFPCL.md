# TGFPCL — Pedidos Complementares Planejamento Compra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PedidosComplementares | Pedidos Complementares Planejamento Compra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODVTP | Meio de Transporte | I |  |  | N |
| NUPCA | Núm. Planejamento | I |  |  | N |
| QTD | Quantidade | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | PedidosComplementares / [TGFPCL](TGFPCL.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |
| PedidosComplementares / [TGFPCL](TGFPCL.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
