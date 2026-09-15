# TSIFAT — Faturamento Cruzado

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FaturamentoCruzado | Faturamento Cruzado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCTABCOINTDESP | Conta Baixa Despesa | I |  |  | N |
| CODCTABCOINTREC | Conta Baixa Receita | I |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| CODLANCDESP | Cód. Hist. Lancto. Despesa | I |  |  | N |
| CODLANCREC | Cód. Hist. Lancto. Receita | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROJ | Código do Projeto | I |  |  | N |
| CODTIPOPERDESP | Cód.Tipo Operação Baixa Despesa | I |  |  | N |
| CODTIPOPERREC | Cód.Tipo Operação Baixa Receita | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FaturamentoCruzado / [TSIFAT](TSIFAT.md) | CODEMP->CODEMP | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | ContaBancariaDespesa / [TSICTA](TSICTA.md) | CODCTABCOINTDESP->CODCTABCOINT | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | ContaBancariaReceita / [TSICTA](TSICTA.md) | CODCTABCOINTREC->CODCTABCOINT | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | HistoricoLancBancarioDespesa / [TGFHBC](TGFHBC.md) | CODLANCDESP->CODLANC | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | HistoricoLancBancarioReceita / [TGFHBC](TGFHBC.md) | CODLANCREC->CODLANC | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPERREC->CODTIPOPER | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERDESP->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
