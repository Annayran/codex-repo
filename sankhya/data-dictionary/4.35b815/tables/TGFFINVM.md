# TGFFINVM — Financeiro Venda Mais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FinanceiroVendaMais | Financeiro Venda Mais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NOSSONUM | Nosso Número | S |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FinanceiroVendaMais / [TGFFINVM](TGFFINVM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
