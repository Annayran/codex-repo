# TSISUPL — Usuário Suplementar

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsuarioSuplementar | Usuário Suplementar | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUSUPL | Cód. Usuário | I |  |  | N |
| DTFIM | Data Final | D |  |  | N |
| DTINICIO | Data de Início | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Usuario / [TSIUSU](TSIUSU.md) | UsuarioSuplementar / [TSISUPL](TSISUPL.md) | CODUSU->CODUSU | I |
| UsuarioSuplementar / [TSISUPL](TSISUPL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioSuplementar / [TSISUPL](TSISUPL.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUSUPL->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
