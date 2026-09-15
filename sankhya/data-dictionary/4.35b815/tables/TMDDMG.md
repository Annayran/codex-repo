# TMDDMG — Tabela de ligação Destinatário - Mensagens

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDDestinatarioMensagem | Tabela que relaciona as mensagens ao seu destinatário | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCON | Código do consumidor | I |  |  | N |
| CODMSG | Código da mensagem | I |  |  | N |
| INTMIN | Intervalo mínimo | I |  |  | N |
| ULTENVIO | Data do último envio para o destinatário | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDDestinatarioMensagem / [TMDDMG](TMDDMG.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
