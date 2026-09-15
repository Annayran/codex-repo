# TGFIFE — Aliquotas (PIS, Confins e CSLL)

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 22
- Instâncias DWF: 4
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaPisCofinsCssl | Alíquotas (Pis, Cofins, CSLL) | S | N |
| AliquotaPisCofinsCssl1 | AliquotaPisCofinsCssl1 | S | N |
| AliquotaPisCofinsCssl2 | AliquotaPisCofinsCssl2 | S | N |
| AliquotaPisCofinsCssl3 | AliquotaPisCofinsCssl3 | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQ | Alíquota | F |  |  | N |
| ALIQCRED | Alíq. p/ crédito | F |  |  | N |
| ALIQSUFRAMA | Alíquota Suframa | F |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODTAB | Tabela c/base p/ ST | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CST | Cód. sit. tributária | I |  |  | N |
| ENTSAI | Tipo | S |  |  | N |
| GERASIMULTPISCOFST | Habilitar geração simultânea dos grupos PIS/COFINS e PISST/COFINSST | S |  |  | N |
| GRUPOIMP | Grupo | S |  |  | N |
| IDALIQ | Código Alíquota | I |  |  | N |
| IPIINCBASE | IPI incide na base de cálculo | S |  |  | N |
| IVA | IVA | F |  |  | N |
| NOMEIMP | Nome do imposto | S |  |  | N |
| PERCVLR | Tipo alíquota | S |  |  | N |
| PRODSEMTRIB | Produto sem tributação | S |  |  | N |
| REDBASE | % Red. base | F |  |  | N |
| RETEMFIN | Retém no financeiro | S |  |  | N |
| SOMARPISCOFINSST | Indica se o valor do PIS/COFINS ST compõe o valor total da NF-e | S |  |  | N |
| VLRPAUTA | Valor pauta | F |  |  | N |
| VLRPAUTACRED | Valor pauta cred. | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaPisCofinsCssl1 / [TGFIFE](TGFIFE.md) | GRUPOPIS->GRUPOIMP | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaPisCofinsCssl2 / [TGFIFE](TGFIFE.md) | GRUPOCOFINS->GRUPOIMP | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaPisCofinsCssl3 / [TGFIFE](TGFIFE.md) | GRUPOCSSL->GRUPOIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
