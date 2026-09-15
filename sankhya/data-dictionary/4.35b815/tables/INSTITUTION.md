# INSTITUTION — Instituição

- Módulo técnico: [INS](../modules/INS.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParceiroUniversidade | Parceiro da Universidade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BPID | Unidade/Franquia | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| DT_INICIO_CONCOMITANTE | Dt. Início Concomitante | H |  |  | N |
| ID | Código | I |  |  | N |
| IMPLANTADO | Implantado | I |  |  | N |
| NOME | Nome | S |  |  | N |
| QTDACESSOS | Qte. Matricula não concomitante | I |  |  | N |
| QTDACESSOS_CONT | Qte. Matricula concomitantes | I |  |  | N |
| SKJV | Sankhya/Jiva | S |  |  | N |
| VISIBLE | Ativo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlunoUniversidade / [USUARIO](USUARIO.md) | ParceiroUniversidade / [INSTITUTION](INSTITUTION.md) | INSTITUTIONID->ID | I |
| ParceiroUniversidade / [INSTITUTION](INSTITUTION.md) | BPUniversidade / [BP](BP.md) | BPID->ID | I |
| ParceiroUniversidade / [INSTITUTION](INSTITUTION.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
