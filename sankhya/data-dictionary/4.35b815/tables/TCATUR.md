# TCATUR — Turmas

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Turma | Turmas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAPMAX | Capacidade máxima | I |  |  | N |
| CAPMIN | Capacidade mínima | I |  |  | N |
| CODANO | Cód.Ano | I |  |  | N |
| CODCUR | Cód.Curso | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| CODTURMA | Cód.Turma | I |  |  | N |
| CODTURNO | Cód.Turno | I |  |  | N |
| NOME | Nome | S |  |  | N |
| TURMA | Turma | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | Turma / [TCATUR](TCATUR.md) | CODANO->CODANO | I |
| Diario / [TCADIA](TCADIA.md) | Turma / [TCATUR](TCATUR.md) | CODTURMA->CODTURMA | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Turma / [TCATUR](TCATUR.md) | CODTURMA->CODTURMA | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Turma / [TCATUR](TCATUR.md) | CODTURMA->CODTURMA | I |
| Serie / [TCASER](TCASER.md) | Turma / [TCATUR](TCATUR.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Turma / [TCATUR](TCATUR.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Turma / [TCATUR](TCATUR.md) | Diario / [TCADIA](TCADIA.md) | CODTURMA->CODTURMA | I |
| Turma / [TCATUR](TCATUR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Turma / [TCATUR](TCATUR.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODTURMA->TCA_CODTURMA | I |
| Turma / [TCATUR](TCATUR.md) | RemanejamentoTurma / [TCAREM](TCAREM.md) | CODTURMA->CODTURANT | I |
| Turma / [TCATUR](TCATUR.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Turma / [TCATUR](TCATUR.md) | Turno / [TCATNO](TCATNO.md) | CODTURNO->CODTURNO | I |
| Turno / [TCATNO](TCATNO.md) | Turma / [TCATUR](TCATUR.md) | CODTURNO->CODTURNO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
