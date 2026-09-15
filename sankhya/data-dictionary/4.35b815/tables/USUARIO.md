# USUARIO — Aluno Universidade

- Módulo técnico: [USU](../modules/USU.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AlunoUniversidade | Aluno da Universidade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARGO | Cargo | S |  |  | N |
| CHANGEPASS | Alterar senha no próximo logon | I |  |  | N |
| CONCOMITANTE | Concomitante/Individual | I |  |  | N |
| DELETED | Ativo | I |  |  | N |
| DTFIM | Dt. Fim | H |  |  | N |
| DTINICIO | Dt. Início | H |  |  | N |
| EMAIL | E-Mail | S |  |  | N |
| FIRSTACCESS | FIRSTACCESS | H |  |  | N |
| FULLNAME | Nome Completo | S |  |  | N |
| ID | Código | I |  |  | N |
| IMPLANTADOR | Implantador | I |  |  | N |
| INSTITUTIONID | Parceiro da universidade | I |  |  | N |
| LASTACCESS | LASTACCESS | H |  |  | N |
| MATRMODELO | Matrícula modelo | S |  |  | N |
| NIVEL | Nível do usuário | I |  |  | N |
| NOVATO | Novato | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PASSWORD | Senha | S |  |  | N |
| SKJV | SKJV | S |  |  | N |
| TIMEMODIFIED | TIMEMODIFIED | H |  |  | N |
| TODASEMPRESAS | Acesso ao Relat. Todas Empresas | I |  |  | N |
| USERNAME | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlunoUniversidade / [USUARIO](USUARIO.md) | CursoAlunoUniversidade / [COURSE_USER](COURSE_USER.md) | ID->USERID | I |
| AlunoUniversidade / [USUARIO](USUARIO.md) | ParceiroUniversidade / [INSTITUTION](INSTITUTION.md) | INSTITUTIONID->ID | I |
| CursoAlunoUniversidade / [COURSE_USER](COURSE_USER.md) | AlunoUniversidade / [USUARIO](USUARIO.md) | USERID->ID | I |
| GestorReport / [GESTOR_REPORT](GESTOR_REPORT.md) | AlunoUniversidade / [USUARIO](USUARIO.md) | USERID->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
