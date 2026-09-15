# TSIATH — Configurações OAuth

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfigsOAuth | Configurações OAuth | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLIENTID | Client ID | S |  |  | N |
| CLIENTSECRET | Client Secret | S |  |  | N |
| CODATH | Cód. Configuração | I |  |  | N |
| NOME | Nome da Configuração | S |  |  | N |
| NOMEDAAPI | Nome da API | S |  |  | N |
| PROVEDOR | Provedor | S |  |  | N |
| REDIRECTURI | Redirect URI | S |  |  | N |
| SCOPES | Scopes | S |  |  | N |
| TENANT | Tenant | S |  |  | N |
| URLAPIGOOGLE | URL da API do Google | S |  |  | N |
| URLGETACCESSTOKEN | URL para obter o Access Token | S |  |  | N |
| URLGOOGLEPEOPLE | URL da API do Google People | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ServidorSMTP / [TSISMTP](TSISMTP.md) | ConfigsOAuth / [TSIATH](TSIATH.md) | CODATH->CODATH | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
