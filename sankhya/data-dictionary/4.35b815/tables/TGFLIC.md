# TGFLIC — Liberação Cascata

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LiberacaoCascata | Liberação Cascata | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSULIB | Usuário Aprovação | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| ENVIAREMAIL | Enviar Notificação | S |  |  | N |
| EVENTO | Evento | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRMAX | Vlr.Máximo | F |  |  | N |
| VLRMIN | Vlr.Mínimo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LiberacaoCascata / [TGFLIC](TGFLIC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| LiberacaoPorTipoOperacao / [TGFTOL](TGFTOL.md) | LiberacaoCascata / [TGFLIC](TGFLIC.md) | CODTIPOPER->CODTIPOPER; EVENTO->EVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
