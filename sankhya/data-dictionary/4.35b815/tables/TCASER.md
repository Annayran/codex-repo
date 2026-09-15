# TCASER — Séries

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Serie | Séries | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCUR | Cód.Curso | S |  |  | N |
| CODPROXCUR | Cód.Próximo Curso | S |  |  | N |
| CODPROXSER | Cód.Próxima Série | S |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| NOME | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DisciplinaSemanal / [TCADSE](TCADSE.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Materiais / [TCAMAT](TCAMAT.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| MensalidadePeriodo / [TCAPRE](TCAPRE.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Serie / [TCASER](TCASER.md) | Curso / [TCACUR](TCACUR.md) | CODCUR->CODCUR | I |
| Serie / [TCASER](TCASER.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Serie / [TCASER](TCASER.md) | Materiais / [TCAMAT](TCAMAT.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Serie / [TCASER](TCASER.md) | MensalidadePeriodo / [TCAPRE](TCAPRE.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Serie / [TCASER](TCASER.md) | ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Serie / [TCASER](TCASER.md) | Turma / [TCATUR](TCATUR.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Turma / [TCATUR](TCATUR.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
