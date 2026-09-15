# TSITCMT — Telemetria de Transações de Customizações

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TelemetriaTela | Telemetria Tela | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Código de Usuário | I |  |  | N |
| DETALHES | Detalhes | S |  |  | N |
| DHEXECUCAO | Data/Hora de Execução | D |  |  | N |
| GLOBALID | ID Global | S |  |  | N |
| RESOURCEID | ID de Recurso | S |  |  | N |
| SERVICENAME | Nome do Serviço | S |  |  | N |
| SESSIONID | ID de Sessão | S |  |  | N |
| TEMPDBCUTOMIZACAO | Tempo DB Customização | I |  |  | N |
| TEMPDBNATIVO | Tempo DB Nativo | I |  |  | N |
| TEMPOTOTAL | Tempo Total | I |  |  | N |
| TEMPOTOTALCUSTOMIZACAO | Tempo Total Customização | I |  |  | N |
| THREADNAME | Nome da Thread | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TelemetriaTela / [TSITCMT](TSITCMT.md) | TelemetriaTelaItem / [TSITCMTITEM](TSITCMTITEM.md) | GLOBALID->GLOBALID; SESSIONID->SESSIONID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
