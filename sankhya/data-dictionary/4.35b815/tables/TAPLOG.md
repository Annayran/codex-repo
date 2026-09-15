# TAPLOG — Tabela de log

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogApontamento | Log de apontamentos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BYTESENVIADOS | Qtd. Bytes Enviados(KB) | F |  |  | N |
| BYTESRECEBIDOS | Qtd. Bytes Recebidos(KB) | F |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHMOV | Data do movimento | H |  |  | N |
| TIPOEVENTO | Tipo de Evento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogApontamento / [TAPLOG](TAPLOG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
