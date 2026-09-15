# TCAPRE — Mensalidade por Período

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MensalidadePeriodo | Mensalidade por Período | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODCUR | Cód.Curso | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODSER | Cód.Série | S |  |  | N |
| VLRMATRICULA | Valor da Matrícula | F |  |  | N |
| VLRTOTMENSALIDADE | Valor total da mensalidade | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | MensalidadePeriodo / [TCAPRE](TCAPRE.md) | CODANO->CODANO | I |
| MensalidadePeriodo / [TCAPRE](TCAPRE.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| MensalidadePeriodo / [TCAPRE](TCAPRE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MensalidadePeriodo / [TCAPRE](TCAPRE.md) | Serie / [TCASER](TCASER.md) | CODCUR->CODCUR; CODSER->CODSER | I |
| Serie / [TCASER](TCASER.md) | MensalidadePeriodo / [TCAPRE](TCAPRE.md) | CODCUR->CODCUR; CODSER->CODSER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
