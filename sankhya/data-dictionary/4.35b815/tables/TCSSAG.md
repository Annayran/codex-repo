# TCSSAG — Conta Sincronização de Agenda

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContaSincAgenda | Conta Sincronização de Agenda | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AUTHCODE | Código autorização | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONTASINCAGENDA | Conta | S |  |  | N |
| DHTOKEN | Dh. Token | H |  |  | N |
| DHULTSINCAGENDA | Dh. Última sincronização | H |  |  | N |
| EMAIL | Email | S |  |  | N |
| EMAILENVIADO | Email Enviado | S |  |  | N |
| EXPIREIN | Expira em | I |  |  | N |
| IDSINCAGENDA | Identificador Agenda | S |  |  | N |
| REFRESHTOKEN | Refresh Token | S |  |  | N |
| SENHASINCAGENDA | Senha | S |  |  | N |
| SINCAGENDA | Sincronizar com | S |  |  | N |
| SINCSTATUS | Status da Sincronização | S |  |  | N |
| TOKEN | Token | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContaSincAgenda / [TCSSAG](TCSSAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | ContaSincAgenda / [TCSSAG](TCSSAG.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
