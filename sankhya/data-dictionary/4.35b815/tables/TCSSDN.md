# TCSSDN — Destinatários de Notificações SLA

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DestinatarioSLA | Destinatários de Notificações SLA | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCON | Consumidor MSD | I |  |  | N |
| NUMREG | Nro. Regra | I |  |  | N |
| NUSLA | Nro. Único SLA | I |  |  | N |
| SEQDEST | Sequência Destinatário | I |  |  | N |
| SEQNOTIFICA | Sequência Notificação | I |  |  | N |
| TIPODEST | Tipo de Destinatário | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinatarioSLA / [TCSSDN](TCSSDN.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| DestinatarioSLA / [TCSSDN](TCSSDN.md) | NotificacaoSLA / [TCSSLN](TCSSLN.md) | SEQNOTIFICA->SEQNOTIFICA | I |
| NotificacaoSLA / [TCSSLN](TCSSLN.md) | DestinatarioSLA / [TCSSDN](TCSSDN.md) | NUMREG->NUMREG; NUSLA->NUSLA; SEQNOTIFICA->SEQNOTIFICA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
