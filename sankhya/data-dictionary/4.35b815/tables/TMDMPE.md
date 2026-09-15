# TMDMPE — Tabela de ligação Mensagem - Perfil

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDMensagemPerfil | Mensagens por Perfil | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMSG | Código da Mensagem | I |  |  | N |
| CODPER | Código do Perfil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDMensagem / [TMDMSG](TMDMSG.md) | MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | CODMSG->CODMSG | I |
| MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | MSDMensagem / [TMDMSG](TMDMSG.md) | CODMSG->CODMSG | I |
| MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | MSDPerfil / [TMDPER](TMDPER.md) | CODPER->CODPER | I |
| MSDPerfil / [TMDPER](TMDPER.md) | MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | CODPER->CODPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
