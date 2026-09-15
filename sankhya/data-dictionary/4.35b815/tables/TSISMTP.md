# TSISMTP — tabela servidor smtp

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ServidorSMTP | Conta SMTP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACCESSTOKEN | Refresh Token | S |  |  | N |
| ACCESSTOKENV2 | Refresh Token V2 | C |  |  | N |
| CODATH | Configurações OAuth | I |  |  | N |
| CODSMTP | Cód. Conta | I |  |  | N |
| EXPIRESIN | Expira em | S |  |  | N |
| IGNORACERTIFICADO | Ignorar validação do certificado do servidor ? | S |  |  | N |
| PADRAO | Conta padrão | S |  |  | N |
| PORTA | Porta | I |  |  | N |
| PORTAPOP | Porta | I |  |  | N |
| REFRESHTOKEN | Refresh Token | S |  |  | N |
| REFRESHTOKENV2 | Refresh Token V2 | C |  |  | N |
| REMETENTE | Remetente | S |  |  | N |
| SENHA | Senha | S |  |  | N |
| SERVIDOR | Servidor SMTP | S |  |  | N |
| SERVIDORPOP | Servidor | S |  |  | N |
| TIPO | Tipo de Conexão | S |  |  | N |
| USEOAUTH | Autenticar com OAuth | S |  |  | N |
| USUARIO | Usuário | S |  |  | N |
| UTILDOWNXML | Utilizar para download de XML de DF-e? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlertaSLA / [TWFASLA](TWFASLA.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| ModeloEmail / [TSIMEM](TSIMEM.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| MSDMensagem / [TMDMSG](TMDMSG.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| Planejamento / [TPQPLA](TPQPLA.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| ServidorSMTP / [TSISMTP](TSISMTP.md) | ConfigsOAuth / [TSIATH](TSIATH.md) | CODATH->CODATH | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
