# TCACUR — Tabela de Cursos

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Curso | Curso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCUR | Cód.Curso | S |  |  | N |
| CODDURA | Cód.Duração | I |  |  | N |
| CODTPENS | Cod.Tipo Ensino | I |  |  | N |
| GRAU | Grau | I |  |  | N |
| MODULOAULA | Módulo | I |  |  | N |
| NOMEABREVIADO | Nome abreviado do Curso | S |  |  | N |
| NOMECUR | Nome do Curso | S |  |  | N |
| PORCINC | PORCINC | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Curso / [TCACUR](TCACUR.md) | FuncaoCurso /  | CODCUR->CODCUR | I |
| Duracao / [TCADUR](TCADUR.md) | Curso / [TCACUR](TCACUR.md) | CODDURA->CODDURA | I |
| Materiais / [TCAMAT](TCAMAT.md) | Curso / [TCACUR](TCACUR.md) | CODCUR->CODCUR | I |
| Serie / [TCASER](TCASER.md) | Curso / [TCACUR](TCACUR.md) | CODCUR->CODCUR | I |
| TipoEncerramento / [TCATEN](TCATEN.md) | Curso / [TCACUR](TCACUR.md) | CODTPENS->CODTPENS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
