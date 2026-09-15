# TGFDIN — Impostos Item de nota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 104
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ImpostoItemNota | Impostos do item da nota | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQADREDRETANT | Alíquota ad rem do IBS/CBS retido anteriormente | F |  |  | N |
| ALIQADREM | Alíquota ad rem do IBS/CBS | F |  |  | N |
| ALIQADREMSR | Alíquota ad rem do IBS/CBS sujeito a retenção | F |  |  | N |
| ALIQDESPACESS | Alíquota Despesas Acessórias | F |  |  | N |
| ALIQDIFERENCIAL | Alíquota de Diferencial | F |  |  | N |
| ALIQINTDEST | Alíq. Interna Destino | F |  |  | N |
| ALIQPARADIFAL | Alíquota para cálculo do DIFAL | F |  |  | N |
| ALIQUOTA | Alíquota | F |  |  | N |
| ALIQUOTADESON | Alíquota PIS/COFINS Desonerados | F |  |  | N |
| ALIQUOTAEFET | Alíquota Efetivo | F |  |  | N |
| ALIQUOTANORMAL | Alíquota Normal | F |  |  | N |
| BASE | Base de Cálculo | F |  |  | N |
| BASEDIFAL | Base Difal | F |  |  | N |
| BASEFCP | Base para Fundo Comb. Pobreza | F |  |  | N |
| BASEFCPINT | Base FCP Interno | F |  |  | N |
| BASENORMDIFICMS | Base Normal Diferimento ICMS | F |  |  | N |
| BASERED | Base Cálc.Reduzida | F |  |  | N |
| BASEREDEFET | Base Cálc.Reduzida Efetivo | F |  |  | N |
| CCLASSTRIB | Código de Classificação Tributária | S |  |  | N |
| CCLASSTRIBREG | Código de Classificação Tributária Regular | S |  |  | N |
| CCREDPRES | Código de Class. Créd. Presumido | S |  |  | N |
| CODBEN | Código de Benefício Fiscal de Crédito Presumido na UF aplicado ao item | S |  |  | N |
| CODCST | Código de Situação Tributária | S |  |  | N |
| CODCSTREG | Código de Situação Tributária Regular | S |  |  | N |
| CODIMP | Imposto | I |  |  | N |
| CODINC | Incidência | I |  |  | N |
| CODLST | Tipo de Serviço | I |  |  | N |
| CODNATREND | Código Natureza de Rendimento | I |  |  | N |
| CODTRIBMUNISS | Cod. Trib. Município | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMIVA | Tem IVA | S |  |  | N |
| CREDPRESCOND | Valor do Crédito Pres. Cond. Susp. | F |  |  | N |
| CST | Cst/Csosn | I |  |  | N |
| DHALTER | Dt.Alteração | H |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| GERASIMULTPISCOFST | Habilitar geração simultânea dos grupos PIS/COFINS e PISST/COFINSST | S |  |  | N |
| IDALIQ | Código Alíquota | I |  |  | N |
| INDGIBSCBS | IBS/CBS? | S |  |  | N |
| IVA | IVA | F |  |  | N |
| JUSTIFICATIVASCORE | Justificativa do Score da Aliquota | S |  |  | N |
| MOTREDADREM | Motivo Redução do ad rem | I |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| PALIQCBSREG | Alíquota efetiva regular da CBS | F |  |  | N |
| PALIQEFETREG | Alíquota Efetiva de Referência do Regime Geral CBS | F |  |  | N |
| PALIQIBSMUNREG | Alíquota efetiva regular do IBS do Município | F |  |  | N |
| PALIQIBSUFREG | Alíquota efetiva regular do IBS da UF | F |  |  | N |
| PAUTA | Pauta | F |  |  | N |
| PCREDPRES | % do Crédito Presumido | F |  |  | N |
| PERCALIQADREMICMS | % Redução Alíquota ad rem ICMS | F |  |  | N |
| PERCALIQEFET | Alíquota Efetiva que será aplicada a Base de Cálculo | F |  |  | N |
| PERCDEVTRIB | Percentual do tributo devolvido | F |  |  | N |
| PERCDIF | Percentual do diferimento | F |  |  | N |
| PERCFCP | Perc. para Fundo Comb. Pobreza | F |  |  | N |
| PERCFCPINT | % FCP Interno | F |  |  | N |
| PERCINSSESPECIAL | % INSS Especial | F |  |  | N |
| PERCISESPEC | Alíquota específica por unidade de medida apropriada | F |  |  | N |
| PERCPARTDIFAL | Percentual de Partilha DIFAL | F |  |  | N |
| PERCREDALIQ | Percentual da redução de alíquota | F |  |  | N |
| PERCREDBASE | Perc.Red.base | F |  |  | N |
| PERCREDBASEEFET | Perc. Red. base Efetivo | F |  |  | N |
| PERCREDGOVALIQ | Percentual da redução de alíquota Governamental | F |  |  | N |
| PERCREDPR | Percentual do Crédito Presumido | F |  |  | N |
| PERCREDVLRIPI | % Redução de Vlr. IPI | F |  |  | N |
| PERCVLR | Percentual / Valor | S |  |  | N |
| RETEMFIN | Retém Financeiro | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SOMARPISCOFINSST | Indica se o valor do PIS/COFINS ST compõe o valor total da NF-e | S |  |  | N |
| TIPCALCDIFAL | Tipo de Cálculo do DIFAL | I |  |  | N |
| TIPCALCFCPESPEC | Tipo de Cálculo de FCP Específico | I |  |  | N |
| TIPO | Tipo Retenção | I |  |  | N |
| TIPODEDISS | Tipo Ded. ISS | S |  |  | N |
| TIPOINSSESPECIAL | Tipo de INSS Especial | S |  |  | N |
| TOTIBSMONO | Total de IBS Monofásico | F |  |  | N |
| TPALCZFM | Tipo de Aplicação da Alíquota Zero — ZFM/ALC | I |  |  | N |
| TPIRRFEXT | Tributação IRRF - Exterior REINF | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VALORDESON | Valor PIS/COFINS Desonerados | F |  |  | N |
| VALORDIFERENCIAL | Valor de Diferencial | F |  |  | N |
| VALOREFET | Valor Efetivo | F |  |  | N |
| VCREDPRES | Valor do Crédito Presumido | F |  |  | N |
| VLRANTREDGOV | Valor do IBS/CBS sem a redução governamental | F |  |  | N |
| VLRCBSREG | Valor da CBS Regular | F |  |  | N |
| VLRCRED | Vlr.Crédito | F |  |  | N |
| VLRCREDPR | Valor do Crédito Presumido | F |  |  | N |
| VLRDEDUZCREDPRES | Valor com dedução do Crédito Presumido | F |  |  | N |
| VLRDEVTRIB | Valor do tributo devolvido | F |  |  | N |
| VLRDIF | Valor do Diferimento | F |  |  | N |
| VLRDIFALDEST | Valor DIFAL UF Destino | F |  |  | N |
| VLRDIFALREM | Valor DIFAL UF Remet. | F |  |  | N |
| VLRFCP | Vlr. para Fundo Comb. Pobreza | F |  |  | N |
| VLRFCPINT | Vlr. FCP Interno | F |  |  | N |
| VLRIBSMUNREG | Valor do IBS Regular do Município | F |  |  | N |
| VLRIBSUFREG | Valor do IBS Regular da UF | F |  |  | N |
| VLRICMSMONODEV | Valor do ICMS Monofásico Devido | F |  |  | N |
| VLRICMSMONODIF | Valor ICMS Monofásico Diferido | F |  |  | N |
| VLRICMSPARADIFAL | Vlr. Icms Para Difal | F |  |  | N |
| VLRINSSESPECIAL | Vlr. INSS Especial | F |  |  | N |
| VLRMONO | Valor do IBS/CBS monofásico | F |  |  | N |
| VLRMONOSR | Valor do IBS/CBS monofásico sujeito a retenção | F |  |  | N |
| VLRREPDIFALFCP | Vlr. Repasse Difal e Fcp | F |  |  | N |
| VLRREPRED | Vlr.Repasse de Redução | F |  |  | N |
| VLRREPREDSEMDESC | Vlr. redução sem desconto | F |  |  | N |
| VLRRETANT | Valor do IBS/CBS retido anteriormente | F |  |  | N |
| VTRIBREG | Valor da CBS Calculado sem a Redução — ZFM/ALC | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImpostoItemNota / [TGFDIN](TGFDIN.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| ImpostoItemNota / [TGFDIN](TGFDIN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemNota / [TGFITE](TGFITE.md) | ImpostoItemNota / [TGFDIN](TGFDIN.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
