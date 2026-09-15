# TMDFMG — Tabela da fila de mensagens

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 25
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDFilaMensagem | Fila de Mensagens | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ASSUNTO | ASSUNTO | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CODCON | Código do Consumidor | I |  |  | N |
| CODCONTASMS | Conta SMTP | I |  |  | N |
| CODFILA | Código na Fila | I |  |  | N |
| CODMSG | Código da Mensagem | I |  |  | N |
| CODSMTP | Conta SMTP | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUREMET | Usuário Remetente | I |  |  | N |
| DBHASHCODE | DBHASHCODE | S |  |  | N |
| DHULTTENTA | DHULTTENTA | H |  |  | N |
| DTENTRADA | Data de Entrada | H |  |  | N |
| EMAIL | EMAIL | S |  |  | N |
| EMAILSEMCOPIA | Em cópia para | S |  |  | S |
| MAXTENTENVIO | MAXTENTENVIO | I |  |  | N |
| MENSAGEM | Mensagem SLA | C |  |  | N |
| MIMETYPE | MIMETYPE | S |  |  | N |
| MSGERRO | Mensagem de Erro | S |  |  | N |
| NUANEXO | NUANEXO | I |  |  | N |
| NUCHAVE | Pedido do Evento | I |  |  | N |
| REENVIAR | Reenviar | S |  |  | N |
| STATUS | Status | S |  |  | N |
| TENTENVIO | Tentativas de Envio | I |  |  | N |
| TIPODOC | Tipo Documento | S |  |  | N |
| TIPOENVIO | TIPOENVIO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmailBoletos / [TGFEBOL](TGFEBOL.md) | MSDFilaMensagem / [TMDFMG](TMDFMG.md) | CODFILA->CODFILA | I |
| MSDDestFilaMensagem / [TMDFMD](TMDFMD.md) | MSDFilaMensagem / [TMDFMG](TMDFMG.md) | CODFILA->CODFILA | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | AnexoPorMensagem / [TMDAXM](TMDAXM.md) | CODFILA->CODFILA | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | ContaSMS / [TSISMS](TSISMS.md) | CODCONTASMS->CODCONTASMS | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | EmailBoletos / [TGFEBOL](TGFEBOL.md) | CODFILA->CODFILA | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | MSDMensagem / [TMDMSG](TMDMSG.md) | CODMSG->CODMSG | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | MSDMensagemResolvida / [TMDMSR](TMDMSR.md) | CODMSG->CODMSG | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
