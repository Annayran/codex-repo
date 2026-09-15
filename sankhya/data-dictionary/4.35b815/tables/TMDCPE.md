# TMDCPE — Tabela de ligação Consumidor - Perfil

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
| MSDDestinatarioPerfil | Destinatários por Perfil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCON | Código do destinatário | I |  |  | N |
| CODPER | Código do perfil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDDestinatario / [TMDCON](TMDCON.md) | MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | CODCON->CODCON | I |
| MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | MSDDestinatario / [TMDCON](TMDCON.md) | CODCON->CODCON | I |
| MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | MSDPerfil / [TMDPER](TMDPER.md) | CODPER->CODPER | I |
| MSDPerfil / [TMDPER](TMDPER.md) | MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | CODPER->CODPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
