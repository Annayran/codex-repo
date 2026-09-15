# TGFFINVMOCOR — Ocorrência Financeira

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
| FinanceiroOcorrenciaVendaMais | Financeiro Ocorrencia | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DTOCOR | Data Ocorrência | H |  |  | N |
| NUFIN | Financeiro | I |  |  | N |
| OCORRENCIA | Descrição da ocorrência | S |  |  | N |
| TICKETNUMBER | Número Ticket | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FinanceiroOcorrenciaVendaMais / [TGFFINVMOCOR](TGFFINVMOCOR.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
