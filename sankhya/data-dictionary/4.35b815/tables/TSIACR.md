# TSIACR — Acesso Remoto

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcessoRemoto | Acesso Remoto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODACR | Cód da Sessão | I |  |  | N |
| DATAAUTORIZACAO | Sessão aberta em | H |  |  | N |
| DATADEEXPIRACAO | Sessão encerrada em | H |  |  | N |
| DATAHORALIMITE | Limite da sessão | H |  |  | N |
| FINALIAUTORIZACAO | Descrição | S |  |  | N |
| HASH | Chave de Acesso | S |  |  | N |
| HTTPPORT | httpPort | S |  |  | N |
| IDSAS | idSas | S |  |  | N |
| NUMOSACR | Núm. OS/Ticket | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| REVOGADO | Revogar Consentimento | S |  |  | N |
| SENHA | Senha do Banco de Dados | S |  |  | N |
| SENHAOM | Senha do Sankhya OM | S |  |  | N |
| TERMODECIENCIA | Termo de Ciência | S |  |  | N |
| TKNHASHID | Tkn Hash Id | S |  |  | N |
| URLJDBC | urlJdbc | S |  |  | N |
| USERSAS | userSas | S |  |  | N |
| USUARIO | Usuário do Banco de Dados | S |  |  | N |
| USUARIOAUTORIZOU | Autorizado por | I |  |  | N |
| USUARIOOM | Usuário do Sankhya OM | I |  |  | N |
| USUARIOQUEAUTORIZOU | Usuário que autorizou | S |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcessoRemoto / [TSIACR](TSIACR.md) | Usuario / [TSIUSU](TSIUSU.md) | USUARIOOM->CODUSU | I |
| AcessoRemoto / [TSIACR](TSIACR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | USUARIOAUTORIZOU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
