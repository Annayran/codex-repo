# TGFMBS — Saldo de Moeda

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SaldoMoeda | Saldo de Moeda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUBCODESP | Número Banco Despesa | I |  |  | N |
| NUBCOREC | Número Banco Receita | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRVARCAMBIAL | Valor Variação Cambial | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SaldoMoeda / [TGFMBS](TGFMBS.md) | MovimentacaoBancariaDespesa / [TGFMBC](TGFMBC.md) | NUBCODESP->NUBCO | I |
| SaldoMoeda / [TGFMBS](TGFMBS.md) | MovimentacaoBancariaReceita / [TGFMBC](TGFMBC.md) | NUBCOREC->NUBCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
