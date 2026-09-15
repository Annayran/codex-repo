# TWFCRD — Credenciais do processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CredenciaisProcesso | Credenciais do processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPRN | Cód. Processo | I |  |  | N |
| CODUSUALTER | Usuário resp. alteração | I |  |  | N |
| CODUSUPUBLICACAO | Usuário resp. publicação | I |  |  | N |
| CONFIG | Configuração | C |  |  | N |
| DHALTER | Dh. alteração | H |  |  | N |
| DHPUBLICACAO | Dh. publicação | H |  |  | N |
| NOMEVIEW | Nome view | S |  |  | N |
| PUBLICACAOAUTO | Publicação automática | S |  |  | N |
| STATUS | Status da credencial | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VERSAOCREDENCIAL | Versão de alteração da credencial | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CredenciaisProcesso / [TWFCRD](TWFCRD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| CredenciaisProcesso / [TWFCRD](TWFCRD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUPUBLICACAO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
