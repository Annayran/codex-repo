# TCAREM — Remanejamento de Turmas

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RemanejamentoTurma | Ca_RemanejamentoTurma | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODMAT | Cód.Material | S |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODTURANT | Cód.Turma anterior | I |  |  | N |
| CODTURMA | Cód.Turma | I |  |  | N |
| DTREM | Data do remanejamento | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | RemanejamentoTurma / [TCAREM](TCAREM.md) | CODANO->CODANO | I |
| Materiais / [TCAMAT](TCAMAT.md) | RemanejamentoTurma / [TCAREM](TCAREM.md) | CODMAT->CODMAT | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Materiais / [TCAMAT](TCAMAT.md) | CODMAT->CODMAT | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Turma / [TCATUR](TCATUR.md) | CODTURMA->CODTURMA | I |
| Turma / [TCATUR](TCATUR.md) | RemanejamentoTurma / [TCAREM](TCAREM.md) | CODTURMA->CODTURANT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
