# TCADIA — Diário

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Diario | Diário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód.Empresa | I |  |  | N |
| CODMAT | Cód.Matéria | S |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODTURMA | Cód.Turma | I |  |  | N |
| DATMOV | Data do Movimento | H |  |  | N |
| NUMDIARIO | Número do Diário | I |  |  | N |
| SEQUENCIAL | Sequência | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Diario / [TCADIA](TCADIA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Diario / [TCADIA](TCADIA.md) | MapaDiario / [TCAMAP](TCAMAP.md) | NUMDIARIO->NUMDIARIO | I |
| Diario / [TCADIA](TCADIA.md) | Materiais / [TCAMAT](TCAMAT.md) | CODMAT->CODMAT | I |
| Diario / [TCADIA](TCADIA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Diario / [TCADIA](TCADIA.md) | Turma / [TCATUR](TCATUR.md) | CODTURMA->CODTURMA | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Diario / [TCADIA](TCADIA.md) | NUMDIARIO->NUMDIARIO | I |
| Materiais / [TCAMAT](TCAMAT.md) | Diario / [TCADIA](TCADIA.md) | CODMAT->CODMAT | I |
| Turma / [TCATUR](TCATUR.md) | Diario / [TCADIA](TCADIA.md) | CODTURMA->CODTURMA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
