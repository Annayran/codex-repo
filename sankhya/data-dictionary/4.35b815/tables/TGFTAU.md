# TGFTAU — Tipos de Autores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoAutor | Tipos de Autores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODTIPAUTOR | Tipo de autor | I |  |  | N |
| CODTIPAUTORPAI | Tipo Autor | I |  |  | N |
| DESCRTIPAUTOR | Descrição do tipo | S |  |  | N |
| GRAU | Grau | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorProduto / [TGFPRA](TGFPRA.md) | TipoAutor / [TGFTAU](TGFTAU.md) | CODTIPAUTOR->CODTIPAUTOR | I |
| TipoAutor / [TGFTAU](TGFTAU.md) | AutorProduto / [TGFPRA](TGFPRA.md) | CODTIPAUTOR->CODTIPAUTOR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
