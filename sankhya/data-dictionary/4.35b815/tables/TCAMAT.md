# TCAMAT — Materiais

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 25
- Instâncias DWF: 1
- Relações DWF: 14
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Materiais | Materiais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODCUR | Cód.Curso | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODMAT | Cód.Material | S |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| CODTIPVENDA | Cód.Tipo de Negociação | I |  |  | N |
| CODTURNO | Cód.Turno | I |  |  | N |
| DHTIPVENDA | Data e hora tipo de negociação | H |  |  | N |
| DTMAT | Data Material | H |  |  | N |
| DTSITATU | DTSITATU | H |  |  | N |
| FINANCEIRO | Financeiro | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PERCDESC | PERCDESC | F |  |  | N |
| RESULTADO | Resultado | S |  |  | N |
| SEGURADO | Segurado | S |  |  | N |
| SELECAO | Seleção | S |  |  | N |
| SITATU | SITATU | S |  |  | N |
| SITESPEC | SITESPEC | S |  |  | N |
| SITMAT | SITMAT | S |  |  | N |
| SITNOVREP | SITNOVREP | S |  |  | N |
| TURMA | Turma | S |  |  | N |
| VALORDESCONTO | Valor do Desconto | F |  |  | N |
| VALORTOTAL | Valor Total | F |  |  | N |
| VLRMAT | Valor do Material | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | Materiais / [TCAMAT](TCAMAT.md) | CODANO->CODANO | I |
| Diario / [TCADIA](TCADIA.md) | Materiais / [TCAMAT](TCAMAT.md) | CODMAT->CODMAT | I |
| Materiais / [TCAMAT](TCAMAT.md) | Alunos / [TCAALU](TCAALU.md) | CODPARC->CODPARC | I |
| Materiais / [TCAMAT](TCAMAT.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Materiais / [TCAMAT](TCAMAT.md) | Curso / [TCACUR](TCACUR.md) | CODCUR->CODCUR | I |
| Materiais / [TCAMAT](TCAMAT.md) | Diario / [TCADIA](TCADIA.md) | CODMAT->CODMAT | I |
| Materiais / [TCAMAT](TCAMAT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Materiais / [TCAMAT](TCAMAT.md) | RemanejamentoTurma / [TCAREM](TCAREM.md) | CODMAT->CODMAT | I |
| Materiais / [TCAMAT](TCAMAT.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Materiais / [TCAMAT](TCAMAT.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| Materiais / [TCAMAT](TCAMAT.md) | Turno / [TCATNO](TCATNO.md) | CODTURNO->CODTURNO | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Materiais / [TCAMAT](TCAMAT.md) | CODMAT->CODMAT | I |
| Serie / [TCASER](TCASER.md) | Materiais / [TCAMAT](TCAMAT.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Turno / [TCATNO](TCATNO.md) | Materiais / [TCAMAT](TCAMAT.md) | CODTURNO->CODTURNO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
