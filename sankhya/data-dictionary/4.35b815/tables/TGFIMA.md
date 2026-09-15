# TGFIMA — Aliquotas de impostos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 7
- Relações DWF: 27
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaImposto | Outros Impostos | S | N |
| AliquotaImpostoEmpresa | Alíquota do Imposto de Empresa | S | N |
| AliquotaImpostoGrupoProd | Alíquota de Imposto do Grupo de Produtos | S | N |
| AliquotaImpostoParceiro | Alíquota do Imposto de Parceiro | S | N |
| AliquotaImpostoProd | Alíquota do Imposto de Produto | S | N |
| AliquotaImpostoServico | Alíquota do Imposto de Serviço | S | N |
| AliquotaImpostoTOP | Alíquota de Imposto TOP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | Alíquota | F |  |  | N |
| CALCULAR | Calcular | S |  |  | N |
| CODIGO | Código | I |  |  | N |
| CODIMP | Imposto | I |  |  | N |
| CODRECEITA | Código da Receita | S |  |  | N |
| IMPFINORIGEST | No Financeiro Origem Estoque | I |  |  | N |
| IMPFINORIGFIN | No Financeiro Origem Financeiro | I |  |  | N |
| IMPNOTA | Na Nota | I |  |  | N |
| REDBASE | Redução da Base de Imposto | F |  |  | N |
| TIPIMP | Tipo do Imposto | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TPIRRFEXT | Tributação IRRF - Exterior REINF | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODIGO->CODEMP | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODIGO->CODGRUPOPROD | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODIGO->CODPARC | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Produto / [TGFPRO](TGFPRO.md) | CODIGO->CODPROD | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Servico / [TGFPRO](TGFPRO.md) | CODIGO->CODPROD | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODIGO->CODTIPOPER | I |
| AliquotaImpostoEmpresa / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoGrupoProd / [TGFIMA](TGFIMA.md) | Imposto / [TGFIMC](TGFIMC.md) | CODIMP->CODIMP | I |
| AliquotaImpostoGrupoProd / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoParceiro / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoProd / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoServico / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoTOP / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| Empresa / [TSIEMP](TSIEMP.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODEMP->CODIGO | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODGRUPOPROD->CODIGO; TIPOIMPOSTO->TIPO | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoEmpresa / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoGrupoProd / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoParceiro / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoProd / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoServico / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Imposto / [TGFIMC](TGFIMC.md) | AliquotaImpostoTOP / [TGFIMA](TGFIMA.md) | CODIMP->CODIMP | I |
| Parceiro / [TGFPAR](TGFPAR.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODPARC->CODIGO | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODPROD->CODIGO | I |
| Servico / [TGFPRO](TGFPRO.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODPROD->CODIGO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODTIPOPER->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
