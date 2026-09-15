# TMDMSG — Tabela de definição de  mensagens

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDMensagem | Tabela com as mensagens registradas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ASSUNTO | Assunto | S |  |  | N |
| ATIVA | Ativa | S |  |  | N |
| CFGDTRESOL | Configuração das datas de resolução | S |  |  | N |
| CODMSG | Código da Mensagem | I |  |  | N |
| CODSMTP | Conta SMTP | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONDICAO | Condição | C |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| INDICACAO | Indicação | S |  |  | N |
| INTMIN | INTMIN | I |  |  | N |
| MENSAGEM | Mensagem | C |  |  | N |
| PROXRESOL | Data para a próxima resolução da mensagem | H |  |  | N |
| TENTATIVAS | Número de Tentativas | I |  |  | N |
| TIPOCONTEUDO | Tipo Conteúdo | S |  |  | N |
| ULTRESOL | Data da última resolução da mensagem | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | MSDMensagem / [TMDMSG](TMDMSG.md) | CODMSG->CODMSG | I |
| MSDMensagem / [TMDMSG](TMDMSG.md) | MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | CODMSG->CODMSG | I |
| MSDMensagem / [TMDMSG](TMDMSG.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| MSDMensagem / [TMDMSG](TMDMSG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | MSDMensagem / [TMDMSG](TMDMSG.md) | CODMSG->CODMSG | I |
| MSDMensagemResolvida / [TMDMSR](TMDMSR.md) | MSDMensagem / [TMDMSG](TMDMSG.md) | CODMSG->CODMSG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
