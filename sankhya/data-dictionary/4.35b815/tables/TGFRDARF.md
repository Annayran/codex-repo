# TGFRDARF — Códigos de Receita - DARF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 2
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ReceitaDarf | Códigos de Receita - DARF | S | S |
| ReceitaDarfReinf | Códigos de Receita - DARF | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODRECEITA | Código Receita e Variação | S |  |  | N |
| DESCRRECEITA | Descrição | S |  |  | N |
| PERIODICIDADE | Periodicidade | S |  |  | N |
| TIPIMPOSTO | Tipo Imposto | S |  |  | N |
| TIPOPESSOA | Pessoa Física/Jurídica | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaImposto / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoEmpresa / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoGrupoProd / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoParceiro / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoProd / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoServico / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| AliquotaImpostoTOP / [TGFIMA](TGFIMA.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITAIPI->CODRECEITA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ReceitaDarfReinf / [TGFRDARF](TGFRDARF.md) | CODRECEITAREINF->CODRECEITA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
