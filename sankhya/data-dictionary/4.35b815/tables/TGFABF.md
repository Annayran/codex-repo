# TGFABF — Adiantamento baixa financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AdiantamentoBaixaFinanceiro | Adiantamento de baixa do financeiro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHMOV | DHMOV | H |  |  | N |
| NUANTECIPA | NUANTECIPA | I |  |  | N |
| NUFIN | NUFIN | I |  |  | N |
| VLRTAXA | VLRTAXA | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AdiantamentoBaixaFinanceiro / [TGFABF](TGFABF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| AdiantamentoBaixaFinanceiro / [TGFABF](TGFABF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
