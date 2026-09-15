# TRSPFV — Perfil de Vaga

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerfilVaga | Perfil da Vaga | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANUNCIO | Cód. Anúncio | I |  |  | N |
| CODPERFIL | Cód. Perfil | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnuncioVagas / [TRSANU](TRSANU.md) | PerfilVaga / [TRSPFV](TRSPFV.md) | CODANUNCIO->CODANUNCIO | I |
| PerfilVaga / [TRSPFV](TRSPFV.md) | PerfilAnuncio /  | CODPERFIL->CODPERFIL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
