# TCAANO — Ano letivo

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnoLetivo | Ano letivo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSIFDIARIO | CLASSIFDIARIO | S |  |  | N |
| CODANO | Cód.Ano | I |  |  | N |
| CODCUR | Cód.Curso | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTFIMAULAS | Data do fim das aulas | H |  |  | N |
| DTINICIOAULAS | Data de início das aulas | H |  |  | N |
| FREQDIARIA | Frequência diária | S |  |  | N |
| MEDIA | Média | I |  |  | N |
| MSGINCENTIVO | Mensagem de incentivo | S |  |  | N |
| NROMAXDEPEND | Número máximo de dependências | I |  |  | N |
| NROMAXRECUPERACAO | Número máximo de recuperações | I |  |  | N |
| PARCELA | Parcela | S |  |  | N |
| QTDDIALETIVO | Quantidade de dias letivos | I |  |  | N |
| QTDPERIODO | Quantidade Período | I |  |  | N |
| RENOVAMATRICULA | Data para renovar matrícula | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | Controle / [TCACON](TCACON.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | Materiais / [TCAMAT](TCAMAT.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | MensalidadePeriodo / [TCAPRE](TCAPRE.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | Periodo / [TCAPER](TCAPER.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | ReferenciaPeriodo / [TCAREF](TCAREF.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | RemanejamentoTurma / [TCAREM](TCAREM.md) | CODANO->CODANO | I |
| AnoLetivo / [TCAANO](TCAANO.md) | Turma / [TCATUR](TCATUR.md) | CODANO->CODANO | I |
| Controle / [TCACON](TCACON.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Materiais / [TCAMAT](TCAMAT.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| MensalidadePeriodo / [TCAPRE](TCAPRE.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Periodo / [TCAPER](TCAPER.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| ReferenciaPeriodo / [TCAREF](TCAREF.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Turma / [TCATUR](TCATUR.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
