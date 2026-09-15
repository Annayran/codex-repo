# TMDPER — Tabela de perfis

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDPerfil | Tabela de perfis | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPER | Código do Perfil | I |  |  | N |
| DESCPER | Descrição do Perfil | S |  |  | N |
| ENVULT | Enviar Somente Última | S |  |  | N |
| INTMIN | Intervalo Mínimo | I |  |  | N |
| NOMEPER | Nome do Perfil | S |  |  | N |
| QUERYLISTADEST | Consulta | C |  |  | N |
| USAQUERYLISTA | Lista Destinatários Variável | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | MSDPerfil / [TMDPER](TMDPER.md) | CODPER->CODPER | I |
| MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | MSDPerfil / [TMDPER](TMDPER.md) | CODPER->CODPER | I |
| MSDPerfil / [TMDPER](TMDPER.md) | MSDDestinatarioPerfil / [TMDCPE](TMDCPE.md) | CODPER->CODPER | I |
| MSDPerfil / [TMDPER](TMDPER.md) | MSDMensagemPerfil / [TMDMPE](TMDMPE.md) | CODPER->CODPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
