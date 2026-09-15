# TMDMSR — Tabela de mensagens resolvidas

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDMensagemResolvida | MSDMensagemResolvida | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMSG | Código da mensagem | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| MENSAGEM | Mensagem | C |  |  | N |
| NOVA | Nova? | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | MSDMensagemResolvida / [TMDMSR](TMDMSR.md) | CODMSG->CODMSG | I |
| MSDMensagemResolvida / [TMDMSR](TMDMSR.md) | MSDMensagem / [TMDMSG](TMDMSG.md) | CODMSG->CODMSG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
