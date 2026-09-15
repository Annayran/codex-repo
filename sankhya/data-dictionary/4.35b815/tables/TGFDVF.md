# TGFDVF — Destinação Financeiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DestinacaoFinanceiros | Destinação Financeiros | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUDESTINACAO | Nro. Destinação | I |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DestinacaoFinanceiros / [TGFDVF](TGFDVF.md) | DestinacaoVerbaMestre / [TGFDVM](TGFDVM.md) | NUDESTINACAO->NUDESTINACAO | I |
| DestinacaoFinanceiros / [TGFDVF](TGFDVF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
