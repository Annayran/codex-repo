# TGFREC — Recibos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Recibo | Recibos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCALORIG | Local de Origem | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| NUFIN | Número único Financeiro | I |  |  | N |
| QTDPAG | Valor Pago | F |  |  | N |
| VLRPAG | Valor a pagar | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Recibo / [TGFREC](TGFREC.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| Recibo / [TGFREC](TGFREC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| Recibo / [TGFREC](TGFREC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
