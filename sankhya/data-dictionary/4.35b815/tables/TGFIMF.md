# TGFIMF — Imposto Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpostoFinanceiro | Imposto Financeiro | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| BASE | Base | F |  |  | N |
| CODIMP | Imposto | I |  |  | N |
| CODNATREND | Código Natureza de Rendimento | I |  |  | N |
| CODRECEITA | Cód. Receita | S |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| NUFIN | Nro Único Fin. | I |  |  | N |
| PERCINSSESPECIAL | % INSS Especial | F |  |  | N |
| TIPIMP | Tipo Imposto | I |  |  | N |
| TIPOINSSESPECIAL | Tipo de INSS Especial | S |  |  | N |
| TPIRRFEXT | Tributação IRRF - Exterior REINF | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRINSSESPECIAL | Vlr. INSS Especial | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | NUFIN->NUFIN | I |
| ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |
| ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | NUFIN->NUFIN | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
