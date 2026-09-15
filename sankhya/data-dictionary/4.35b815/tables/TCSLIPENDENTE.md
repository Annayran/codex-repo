# TCSLIPENDENTE — TABLE TCSLIPENDENTE

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LicencaPendente | Instancia das solicitações pendentes do sankhya tasks | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APP | App | I |  |  | N |
| CODFONE | Id | S |  |  | N |
| CODSOLI | Cod. Solicitação | I |  |  | N |
| CODUSU | Cod. Usuário Solicitante | I |  |  | N |
| CODUSULIB | Cod. Usuário Liberador | I |  |  | N |
| DTLIB | Data Liberação | H |  |  | N |
| DTSOLI | Data Solicitação | H |  |  | N |
| NOME | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LicencaPendente / [TCSLIPENDENTE](TCSLIPENDENTE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
