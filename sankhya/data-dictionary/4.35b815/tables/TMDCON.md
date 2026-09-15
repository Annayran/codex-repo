# TMDCON — Tabela de definição de destinatários

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDDestinatario | Destinatário | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CODCON | Código do Destinatário | I |  |  | N |
| CODCONT | Código do Contato | I |  |  | N |
| CODFUNC | Código do Funcionário | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| EMAIL | E-mail | S |  |  | N |
| NOMECON | Nome do Destinatário | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinatarioRelatorioFormatado / [TSIDRF](TSIDRF.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| DestinatarioSLA / [TCSSDN](TCSSDN.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONT->CODCONTATO; CODPARC->CODPARC | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | CODCON->CODCON | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDDestinatarioMensagem / [TMDDMG](TMDDMG.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
