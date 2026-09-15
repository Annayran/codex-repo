# TCATDI — Tipo de Disciplinas

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoDisciplina | Tipo de Disciplinas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTPDISC | Cód.Tipo de Disciplina | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DisciplinaSemanal / [TCADSE](TCADSE.md) | TipoDisciplina / [TCATDI](TCATDI.md) | CODTPDISC->CODTPDISC | I |
| TipoDisciplina / [TCATDI](TCATDI.md) | DisciplinaSemanal / [TCADSE](TCADSE.md) | CODTPDISC->CODTPDISC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
