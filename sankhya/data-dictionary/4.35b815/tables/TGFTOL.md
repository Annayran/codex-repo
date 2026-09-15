# TGFTOL — Liberacao Por Tipo Operacao

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LiberacaoPorTipoOperacao | Liberação por Tipo de Operação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COPIARLIBER | Copiar liberação quando faturando individualmente | S |  |  | N |
| DTALTER | Dh. alteração | H |  |  | N |
| EVENTO | Evento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LiberacaoPorTipoOperacao / [TGFTOL](TGFTOL.md) | LiberacaoCascata / [TGFLIC](TGFLIC.md) | CODTIPOPER->CODTIPOPER; EVENTO->EVENTO | I |
| LiberacaoPorTipoOperacao / [TGFTOL](TGFTOL.md) | ViewEventoLiberacao / [VGFLIBEVE](VGFLIBEVE.md) | EVENTO->EVENTO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LiberacaoPorTipoOperacao / [TGFTOL](TGFTOL.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
