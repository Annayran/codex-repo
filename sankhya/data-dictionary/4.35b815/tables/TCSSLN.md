# TCSSLN — Notificações SLA

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotificacaoSLA | Notificação SLA | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQSOM | Arquivo de Som | S |  |  | N |
| DECORESTA | Decorrido/Restante | S |  |  | N |
| NUMREG | Nro. Regra | I |  |  | N |
| NUSLA | Nro. Único | I |  |  | N |
| RECORRENCIA | Recorrência | I |  |  | N |
| SEQNOTIFICA | Sequência local | I |  |  | N |
| TIPOALERTA | Tipo de Alerta | S |  |  | N |
| TIPOTEMPO | Tipo de Tempo | S |  |  | N |
| VALORTEMPO | Tempo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinatarioSLA / [TCSSDN](TCSSDN.md) | NotificacaoSLA / [TCSSLN](TCSSLN.md) | SEQNOTIFICA->SEQNOTIFICA | I |
| NotificacaoSLA / [TCSSLN](TCSSLN.md) | DestinatarioSLA / [TCSSDN](TCSSDN.md) | NUMREG->NUMREG; NUSLA->NUSLA; SEQNOTIFICA->SEQNOTIFICA | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | NotificacaoSLA / [TCSSLN](TCSSLN.md) | NUMREG->NUMREG; NUSLA->NUSLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
