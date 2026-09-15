# COURSE — Curso Universidade

- Módulo técnico: [COU](../modules/COU.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CursoUniversidade | Curso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CATEGORY | CATEGORY | I |  |  | N |
| COST | Tipo avaliação | I |  |  | N |
| FULLNAME | Nome completo | S |  |  | N |
| ID | Código | I |  |  | N |
| SKJV | SKJV | S |  |  | N |
| SORTORDER | SORTORDER | I |  |  | N |
| TIMECREATED | TIMECREATED | I |  |  | N |
| TIMEMODIFIED | TIMEMODIFIED | I |  |  | N |
| VISIBLE | Visível | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CategoriaCursoUniversidade / [COURSE_CATEGORIES](COURSE_CATEGORIES.md) | CursoUniversidade / [COURSE](COURSE.md) | ID->CATEGORY | I |
| CursoAlunoUniversidade / [COURSE_USER](COURSE_USER.md) | CursoUniversidade / [COURSE](COURSE.md) | COURSEID->ID | I |
| CursoUniversidade / [COURSE](COURSE.md) | AulaUniversidade / [AULAS](AULAS.md) | ID->ID_CURSO | I |
| CursoUniversidade / [COURSE](COURSE.md) | CategoriaCursoUniversidade / [COURSE_CATEGORIES](COURSE_CATEGORIES.md) | CATEGORY->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
