# GESTOR_REPORT — Permissão Acesso Relatórios

- Módulo técnico: [GES](../modules/GES.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GestorReport | Permissão Acesso Relatórios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ID | ID | I |  |  | N |
| INSTITUTIONID | Instituição | I |  |  | N |
| USERID | Aluno | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GestorReport / [GESTOR_REPORT](GESTOR_REPORT.md) | AlunoUniversidade / [USUARIO](USUARIO.md) | USERID->ID | I |
| GestorReport / [GESTOR_REPORT](GESTOR_REPORT.md) | BPUniversidade / [BP](BP.md) | INSTITUTIONID->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
