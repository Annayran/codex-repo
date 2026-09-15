# COURSE_USER — Curso do Aluno

- Módulo técnico: [COU](../modules/COU.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CursoAlunoUniversidade | Cursos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CERTIFIED | CERTIFIED | I |  |  | N |
| CERTIFIED_PRATICA | Aprovado | I |  |  | N |
| COURSEID | Curso | I |  |  | N |
| DT_END | DT_END | I |  |  | N |
| ID | ID | I |  |  | N |
| NOTA_PRATICA | Nota | I |  |  | N |
| QTDACESSOS | QTDACESSOS | I |  |  | N |
| SORTORDER | SORTORDER | I |  |  | N |
| STATUS | Situação | I |  |  | N |
| TENTATIVA | Tentativa | I |  |  | N |
| TIMESTART | TIMESTART | I |  |  | N |
| USERID | USERID | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlunoUniversidade / [USUARIO](USUARIO.md) | CursoAlunoUniversidade / [COURSE_USER](COURSE_USER.md) | ID->USERID | I |
| CursoAlunoUniversidade / [COURSE_USER](COURSE_USER.md) | AlunoUniversidade / [USUARIO](USUARIO.md) | USERID->ID | I |
| CursoAlunoUniversidade / [COURSE_USER](COURSE_USER.md) | CursoUniversidade / [COURSE](COURSE.md) | COURSEID->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
