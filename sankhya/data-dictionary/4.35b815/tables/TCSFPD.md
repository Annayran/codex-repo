# TCSFPD — Pedidos da FAP

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PedidoProjeto | Pedidos da PAP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | ATIVO | S |  |  | N |
| NUFAP | NUFAP | I |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| QTDHORAS | QTDHORAS | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PedidoProjeto / [TCSFPD](TCSFPD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | PedidoProjeto / [TCSFPD](TCSFPD.md) | NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
