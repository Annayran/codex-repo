# TGFVAR — Compras e Vendas com vários Pedidos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CompraVendavariosPedido | Compras e Vendas com vários Pedidos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CUSATEND | Custo atendimento | F |  |  | N |
| FIXACAO | Fixação | S |  |  | N |
| NROATOCONCDRAW | Número do ato concessório de Drawback | S |  |  | N |
| NROMEMORANDO | Nro Memorando Exportação | I |  |  | N |
| NROREGEXPORT | Número do Registro de Exportação | S |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| NUNOTAORIG | Nro. Único de Origem | I |  |  | N |
| ORDEMPROD | Ordem do produto | I |  |  | N |
| QTDATENDIDA | Quantidade atendida | F |  |  | N |
| SEQUENCIA | Seq. Item | I |  |  | N |
| SEQUENCIAORIG | Seq. Item de Origem | I |  |  | N |
| STATUSNOTA | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CompraVendavariosPedido / [TGFVAR](TGFVAR.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| CompraVendavariosPedido / [TGFVAR](TGFVAR.md) | ItemNotaOrigem / [TGFITE](TGFITE.md) | NUNOTAORIG->NUNOTA; SEQUENCIAORIG->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
