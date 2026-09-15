# COURSE_CATEGORIES — Categoria de Cursos

- Módulo técnico: [COU](../modules/COU.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CategoriaCursoUniversidade | Categoria de Cursos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| COURSECOUNT | COURSECOUNT | I |  |  | N |
| DEPTH | DEPTH | I |  |  | N |
| ID | ID | I |  |  | N |
| NAME | Categoria | S |  |  | N |
| PARENT | PARENT | I |  |  | N |
| SKJV | Sankhya/Jiva | S |  |  | N |
| SORTORDER | SORTORDER | I |  |  | N |
| TIMEMODIFIED | TIMEMODIFIED | I |  |  | N |
| VISIBLE | VISIBLE | I |  |  | N |
| VISIVELB2B | Visível B2B | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CategoriaCursoUniversidade / [COURSE_CATEGORIES](COURSE_CATEGORIES.md) | CursoUniversidade / [COURSE](COURSE.md) | ID->CATEGORY | I |
| CursoUniversidade / [COURSE](COURSE.md) | CategoriaCursoUniversidade / [COURSE_CATEGORIES](COURSE_CATEGORIES.md) | CATEGORY->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
