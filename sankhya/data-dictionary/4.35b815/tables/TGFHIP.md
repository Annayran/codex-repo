# TGFHIP — Histórido de alteração Item Pendente

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoItemPendente | Histórico Item Pendente Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DESCRICAO | Justificativa | S |  |  | N |
| DHALTER | Modificado em | H |  |  | N |
| NUNOTA | Número Nota | I |  |  | N |
| SEQHIP | Sequência Justificativa | I |  |  | N |
| SEQUENCIA | Sequência do Item | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoItemPendente / [TGFHIP](TGFHIP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemNota / [TGFITE](TGFITE.md) | HistoricoItemPendente / [TGFHIP](TGFHIP.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
