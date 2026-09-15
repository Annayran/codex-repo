# TCSLIHIST — TABLE TCSLIHIST

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TasksHistorico | Instancia da tabela de historico | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APP | App | I |  |  | N |
| CODFONE | Id | S |  |  | N |
| CODFONEDESTINO | Id Destino | S |  |  | N |
| CODHIST | Código Histórico | I |  |  | N |
| CODSOLI | Código da Solicitação | I |  |  | N |
| CODUSU | Código do Usuário | I |  |  | N |
| CODUSULIB | Código Usuário Liberador | I |  |  | N |
| DTLIB | Data de Liberação | H |  |  | N |
| DTSOLI | Data de Solicitação | H |  |  | N |
| NOME | Nome | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TasksHistorico / [TCSLIHIST](TCSLIHIST.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TasksHistorico / [TCSLIHIST](TCSLIHIST.md) | UsuarioLiberacao / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
