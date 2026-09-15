# TGFFNF — Frete para Nota Financeiro

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
| FreteNotaFinanceiro | Frete para Nota Financeiro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUFIN | Número no Financeiro | I |  |  | N |
| NUNOTA | Número da Nota | I |  |  | N |
| TIPFRETE | Tipo do frete | S |  |  | N |
| VLRFRETE | Valor do frete | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FreteNotaFinanceiro / [TGFFNF](TGFFNF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| FreteNotaFinanceiro / [TGFFNF](TGFFNF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
