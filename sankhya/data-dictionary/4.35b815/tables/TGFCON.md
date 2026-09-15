# TGFCON — Conferência de Pedidos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConferenciaPedido | Conferência de Pedidos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Código de Barras | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHCONF | Dt. Conferência | D |  |  | N |
| NUCONF | Número único de conferência de Pedido | I |  |  | N |
| QTD | Quantidade do pedido | F |  |  | N |
| SEQUENCIA | Sequência única de conferência de Pedido | I |  |  | N |
| TIPOCONF | Tipo | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
