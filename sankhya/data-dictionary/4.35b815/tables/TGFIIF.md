# TGFIIF — Impostos Item de Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpostosItemFinanceiro | Impostos Item de Financeiro | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| BASE | Base | F |  |  | N |
| BASERED | Base Cálc.Reduzida | F |  |  | N |
| CODIMP | Imposto | I |  |  | N |
| CST | CST | I |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| NUFIN | Nro Único Fin. | I |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPBASE | Incidência | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImpostosItemFinanceiro / [TGFIIF](TGFIIF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
