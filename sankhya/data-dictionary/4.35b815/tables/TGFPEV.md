# TGFPEV — Pedido por Evento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PedidoPorEvento | Pedido por Evento | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMINHOPASTA | Pasta no FTP | S |  |  | N |
| CODACESSO | Cód. Acesso Link | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| FINVENCIDOS | Financeiros Vencidos | S |  |  | S |
| NUNOTA | Nota/Pedido | I |  |  | N |
| NUPEV | Pedido do Evento | I |  |  | N |
| OBSCLIENTE | Observação do Parceiro | S |  |  | N |
| QTDENVIO | Qtde. Envio | I |  |  | S |
| STATUSULTENVIO | Status Último Envio | S |  |  | S |
| TIMQTDFOTOS | Qtd. Fotos | I |  |  | N |
| ULTENVIO | Dt. Último Envio | H |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PedidoPorEvento / [TGFPEV](TGFPEV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| PedidoPorEvento / [TGFPEV](TGFPEV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TimImovel / [TIMIMV](TIMIMV.md) | PedidoPorEvento / [TGFPEV](TGFPEV.md) | NUPEV->NUPEV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
