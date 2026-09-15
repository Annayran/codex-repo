# TSITCMTITEM — Detalhes de Telemetria de Transações de Customizações

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TelemetriaTelaItem | Telemetria Tela Item | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| DETALHES | Detalhes | S |  |  | N |
| DHEXECUCAO | Data/Hora de Execução | D |  |  | N |
| ENTIDADE | Entidade | S |  |  | N |
| ERRO | Erro | S |  |  | N |
| GLOBALID | ID Global | S |  |  | N |
| IDTIPO | ID do Tipo | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SESSIONID | ID de Sessão | S |  |  | N |
| TEMPDBCUTOMIZACAO | Tempo DB Customização | I |  |  | N |
| TEMPO | Tempo | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TelemetriaTela / [TSITCMT](TSITCMT.md) | TelemetriaTelaItem / [TSITCMTITEM](TSITCMTITEM.md) | GLOBALID->GLOBALID; SESSIONID->SESSIONID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
