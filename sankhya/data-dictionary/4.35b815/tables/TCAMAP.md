# TCAMAP — Mapa do Diário

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MapaDiario | Mapa do Diário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODALU | Cód.Aluno | I |  |  | N |
| CODANO | Cód.Ano | I |  |  | N |
| CODDSE | Cód.Disciplina Semanal | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODPROF | Cód.Professor | I |  |  | N |
| CODTURMA | Cód.Turma | I |  |  | N |
| CONCEITO | Conceito | S |  |  | N |
| FALTAJUS | Faltas Justificadas | F |  |  | N |
| NUMAPA | Número do Mapa do Diário | I |  |  | N |
| NUMDIARIO | Número do Diário | I |  |  | N |
| NUPERIODO | Número do Período | I |  |  | N |
| TCA_CODTURMA | Cód.Turma | I |  |  | N |
| TOTFALTA | Total de Faltas | F |  |  | N |
| TOTNOTA | Nota total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Controle / [TCACON](TCACON.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODANO->CODANO; CODCON->CONCEITO | I |
| Diario / [TCADIA](TCADIA.md) | MapaDiario / [TCAMAP](TCAMAP.md) | NUMDIARIO->NUMDIARIO | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODDSE->CODDSE | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Controle / [TCACON](TCACON.md) | CODANO->CODANO | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Diario / [TCADIA](TCADIA.md) | NUMDIARIO->NUMDIARIO | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODDSE->CODDSE | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODALU->CODPARC; CODPROF->CODPARC | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Periodo / [TCAPER](TCAPER.md) | CODANO->CODANO; NUPERIODO->NUPERIODO | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Turma / [TCATUR](TCATUR.md) | CODTURMA->CODTURMA | I |
| Periodo / [TCAPER](TCAPER.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODANO->CODANO; NUPERIODO->NUPERIODO | I |
| Turma / [TCATUR](TCATUR.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODTURMA->TCA_CODTURMA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
