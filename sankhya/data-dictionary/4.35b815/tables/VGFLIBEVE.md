# VGFLIBEVE — View Eventos Liberacao

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewEventoLiberacao | Eventos Liberação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| EVENTO | Evento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GadgetEventoLiberacao / [TSIGEL](TSIGEL.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| LiberacaoPorTipoOperacao / [TGFTOL](TGFTOL.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| LimiteLiberacao / [TSILIM](TSILIM.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| RegraNegocio / [TGFRNG](TGFRNG.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
