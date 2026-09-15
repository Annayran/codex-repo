# AULAS — Aula Universidade

- Módulo técnico: [AUL](../modules/AUL.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AulaUniversidade | Aulas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DURACAO_VIDEO | Duração do vídeo | I |  |  | N |
| FORMATO | Formato | S |  |  | N |
| ID | Código | I |  |  | N |
| ID_CURSO | ID_CURSO | I |  |  | N |
| NM_AULA | Nome da aula | S |  |  | N |
| NM_VIDEO | Nome do vídeo | S |  |  | N |
| SORTORDER | SORTORDER | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CursoUniversidade / [COURSE](COURSE.md) | AulaUniversidade / [AULAS](AULAS.md) | ID->ID_CURSO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
