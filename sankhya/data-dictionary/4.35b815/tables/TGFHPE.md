# TGFHPE — Histórico de Pedido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoPedido | Histórico de Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| EVENTO | Evento | S |  |  | N |
| NUEVENTO | Nro Único Evento | I |  |  | N |
| NUEVENTOORIG | Nro. Evento Origem | I |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| PERCDESC | Perc. Desconto | F |  |  | N |
| QTDENTREGUE | Qtd. Entregue | F |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| QTDPENDENTE | Qtd. Pendente | F |  |  | S |
| SEQUENCIA | Sequência | I |  |  | N |
| VLRUNIT | Vlr. Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoPedido / [TGFHPE](TGFHPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| HistoricoPedido / [TGFHPE](TGFHPE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
