# TSIGEL — Gadget por Evento de Liberação

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GadgetEventoLiberacao | Gadget X Evento de Liberação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| EVENTO | Evento Liberação | I |  |  | N |
| NUGDG | Componente BI | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GadgetEventoLiberacao / [TSIGEL](TSIGEL.md) | Gadget / [TSIGDG](TSIGDG.md) | NUGDG->NUGDG | I |
| GadgetEventoLiberacao / [TSIGEL](TSIGEL.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
