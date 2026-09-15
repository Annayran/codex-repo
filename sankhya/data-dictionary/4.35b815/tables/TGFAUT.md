# TGFAUT — Autores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Autor | Autores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDO | Apelido | S |  |  | N |
| CODAUTOR | Autor | I |  |  | N |
| NOMEAUTOR | Nome do autor | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Autor / [TGFAUT](TGFAUT.md) | AutorProduto / [TGFPRA](TGFPRA.md) | CODAUTOR->CODAUTOR | I |
| AutorProduto / [TGFPRA](TGFPRA.md) | Autor / [TGFAUT](TGFAUT.md) | CODAUTOR->CODAUTOR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
