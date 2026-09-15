# TSICSB — Consolidar Eventos

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConsolidarEventosBD | Consolidar Eventos BD | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CALLID | Id da Chamada | S |  |  | N |
| COMANDO | Comando | B |  |  | N |
| DATA | Data | D |  |  | N |
| DBUSERNAME | Usuário do Banco de Dados | S |  |  | N |
| DTCOMMAND | Dt. Da ultima execução | D |  |  | N |
| MAIORTEMPO | Maior Tempo | F |  |  | N |
| MD5 | MD5 | S |  |  | N |
| MEDIA | Media | F |  |  | N |
| MEDIASEMMAIORMENORTEMPO | Media Sem Maior e Menor Tempo | F |  |  | N |
| MENORTEMPO | Menor Tempo | F |  |  | N |
| NMEXECUTOR | Nome do Executor | S |  |  | N |
| NMTHREAD | Nome da Thread | S |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| QTDVEZES | Quantidade de Vezes | I |  |  | N |
| SCORE | Score | F |  |  | N |
| SID | SID | I |  |  | N |
| STACKTRACE | Trace | B |  |  | N |
| TIPOCOMANDO | Tipo De Comando | S |  |  | N |
| TOKEN | Token da Tela | S |  |  | N |
| TOTALIZADORTEMPO | Media Sem Maior e Menor Tempo | F |  |  | N |
| UNIQUEID | UID | S |  |  | N |
| USERID | ID do Usuário | I |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
