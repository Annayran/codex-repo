# TCADSE — Disciplina Semanal

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DisciplinaSemanal | Disciplina Semanal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AULAS_SEMANAIS | Aulas Semanais | I |  |  | N |
| CARGA_HORARIA | Carga horária | I |  |  | N |
| CARHORECUPERA | Carga horária Recuperação | I |  |  | N |
| CODANO | Cód.Ano | I |  |  | N |
| CODCUR | Cód.Curso | S |  |  | N |
| CODDISC | Cód.Disciplina | S |  |  | N |
| CODDSE | Cód.Disciplina Semanal | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| CODTPDISC | Cód.Tipo Disciplina | S |  |  | N |
| CONTACOMORECUPERACAO | Conta como recuperação | S |  |  | N |
| OBRIGATORIA | Obrigatória | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| RECUPERACAO | Recuperação | S |  |  | N |
| REPROVAPORFALTA | Reprova por falta | S |  |  | N |
| REPROVAPORNOTA | Reprova por nota | S |  |  | N |
| TIPOAVALIACAO | Tipo de Avaliação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODANO->CODANO | I |
| Disciplina / [TCADIS](TCADIS.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODDISC->CODDISC | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | Disciplina / [TCADIS](TCADIS.md) | CODDISC->CODDISC | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODDSE->CODDSE | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | TipoDisciplina / [TCATDI](TCATDI.md) | CODTPDISC->CODTPDISC | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODDSE->CODDSE | I |
| Serie / [TCASER](TCASER.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| TipoDisciplina / [TCATDI](TCATDI.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODTPDISC->CODTPDISC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
