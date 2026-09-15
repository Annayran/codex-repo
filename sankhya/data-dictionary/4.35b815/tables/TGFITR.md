# TGFITR — Itens Regra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 36
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemRegras | Item Regras | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODINSTPRINC | Cód. Instância Principal | I |  |  | N |
| CODINSTPRINCFIN | Cód. Instância Principal Final | I |  |  | N |
| CODINSTSECFIN | Cód. Instância Secundária Final | I |  |  | N |
| CODINSTSECINI | Cód. Instância Secundária Inicial | I |  |  | N |
| CODREGRA | Código Regra | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCDINSTPRINCFIN | Desc. Instância Principal Final | S |  |  | S |
| DESCINSTPRINC | Desc. Instância Principal | S |  |  | S |
| DESCINSTSECFIN | Desc. Instância Secundária Final | S |  |  | S |
| DESCINSTSECINI | Desc. Instância Secundária Inicial | S |  |  | S |
| DTALTER | Data Alteração | H |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemRegras / [TGFITR](TGFITR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODINSTPRINC->CODCENCUS | I |
| ItemRegras / [TGFITR](TGFITR.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODINSTSECINI->CODCENCUS | I |
| ItemRegras / [TGFITR](TGFITR.md) | CentroResultadoFinal / [TSICUS](TSICUS.md) | CODINSTSECFIN->CODCENCUS | I |
| ItemRegras / [TGFITR](TGFITR.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODINSTPRINC->CODCTABCOINT | I |
| ItemRegras / [TGFITR](TGFITR.md) | ContaBancariaDespesa / [TSICTA](TSICTA.md) | CODINSTSECFIN->CODCTABCOINT | I |
| ItemRegras / [TGFITR](TGFITR.md) | ContaBancariaReceita / [TSICTA](TSICTA.md) | CODINSTSECINI->CODCTABCOINT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODINSTPRINC->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | EmpresaBaixa / [TSIEMP](TSIEMP.md) | CODINSTSECINI->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | EmpresaMatriz / [TSIEMP](TSIEMP.md) | CODINSTSECFIN->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | EmpresaPessoal /  | CODINSTPRINC->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODINSTPRINC->CODGRUPOPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | GrupoProduto2 / [TGFGRU](TGFGRU.md) | CODINSTSECINI->CODGRUPOPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | GrupoProduto3 / [TGFGRU](TGFGRU.md) | CODINSTSECFIN->CODGRUPOPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODINSTPRINC->CODLOCAL | I |
| ItemRegras / [TGFITR](TGFITR.md) | LocalFinanceiroEntDest / [TGFLOC](TGFLOC.md) | CODINSTSECFIN->CODLOCAL | I |
| ItemRegras / [TGFITR](TGFITR.md) | LocalFinanceiroEntOrig / [TGFLOC](TGFLOC.md) | CODINSTSECINI->CODLOCAL | I |
| ItemRegras / [TGFITR](TGFITR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODINSTPRINC->CODNAT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Natureza2 / [TGFNAT](TGFNAT.md) | CODINSTSECINI->CODNAT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Natureza3 / [TGFNAT](TGFNAT.md) | CODINSTSECFIN->CODNAT | I |
| ItemRegras / [TGFITR](TGFITR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODINSTPRINC->CODPARC | I |
| ItemRegras / [TGFITR](TGFITR.md) | ParceiroF2 / [TGFPAR](TGFPAR.md) | CODINSTSECFIN->CODPARC | I |
| ItemRegras / [TGFITR](TGFITR.md) | ParceiroMatriz / [TGFPAR](TGFPAR.md) | CODINSTSECINI->CODPARC | I |
| ItemRegras / [TGFITR](TGFITR.md) | Produto / [TGFPRO](TGFPRO.md) | CODINSTPRINC->CODPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | Produto3 / [TGFPRO](TGFPRO.md) | CODINSTSECFIN->CODPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | ProdutoMateriaPrima /  | CODINSTSECINI->CODPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODINSTPRINC->CODPROJ | I |
| ItemRegras / [TGFITR](TGFITR.md) | Projeto2 / [TCSPRJ](TCSPRJ.md) | CODINSTSECINI->CODPROJ | I |
| ItemRegras / [TGFITR](TGFITR.md) | Projeto3 / [TCSPRJ](TCSPRJ.md) | CODINSTSECFIN->CODPROJ | I |
| ItemRegras / [TGFITR](TGFITR.md) | Regras / [TGFREG](TGFREG.md) | CODREGRA->CODREGRA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODINSTPRINC->CODTIPVENDA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoNegociacao3 / [TGFTPV](TGFTPV.md) | CODINSTSECFIN->CODTIPVENDA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoNegociacaoFilha / [TGFTPV](TGFTPV.md) | CODINSTSECINI->CODTIPVENDA | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODINSTPRINC->CODTIPOPER | I |
| ItemRegras / [TGFITR](TGFITR.md) | TOPDespesa / [TGFTOP](TGFTOP.md) | CODINSTSECFIN->CODTIPOPER | I |
| ItemRegras / [TGFITR](TGFITR.md) | TOPReceita / [TGFTOP](TGFTOP.md) | CODINSTSECINI->CODTIPOPER | I |
| Regras / [TGFREG](TGFREG.md) | ItemRegras / [TGFITR](TGFITR.md) | CODREGRA->CODREGRA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
