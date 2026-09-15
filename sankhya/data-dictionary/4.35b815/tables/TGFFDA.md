# TGFFDA — Fórmulas diferencial de Alíquota / Base de ICMS AT

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 3
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaCalculoDifal | Fórmulas diferencial de Alíquota / Base de ICMS AT | S | N |
| FormulaCalculoFcpDifal | Fórmula para o Cálculo do FCP do DIFAL | S | N |
| FormulaDiferencialAliquota | Fórmulas para DIFAL, ICMS AT e MVA Ajustada | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORM | Código da fórmula | I |  |  | N |
| DESCRFORM | Descrição da fórmula | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaICMS / [TGFICM](TGFICM.md) | FormulaCalculoDifal / [TGFFDA](TGFFDA.md) | CODFORMCALCDIFAL->CODFORM | I |
| AliquotaICMS / [TGFICM](TGFICM.md) | FormulaCalculoFcpDifal / [TGFFDA](TGFFDA.md) | FORMCALFCPDIFAL->CODFORM | I |
| AliquotaICMS / [TGFICM](TGFICM.md) | FormulaDiferencialAliquota / [TGFFDA](TGFFDA.md) | CODFORMBASDIFAL->CODFORM | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FormulaDiferencialAliquota / [TGFFDA](TGFFDA.md) | CODFORDIFALIQ->CODFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
