# TSIUFA — Usuário Filtro API

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UsuarioFiltroAPI | Usuário Filtro API | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFIL | Cód. Filtro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| EMAIL | Email | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | UsuarioFiltroAPI / [TSIUFA](TSIUFA.md) | CODFIL->CODFIL | I |
| UsuarioFiltroAPI / [TSIUFA](TSIUFA.md) | CadastroFiltrosAPI / [TSICFA](TSICFA.md) | CODFIL->CODFIL | I |
| UsuarioFiltroAPI / [TSIUFA](TSIUFA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
