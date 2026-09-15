# TGFSTMVT — TOPs para calcular o valor médio de venda

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
| BaseStMediaVendaTop | BaseStMediaVendaTop | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| DHTIPOPER | Data e hora alteração | H |  |  | N |
| NROUNICO | Nro. Único | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | BaseStMediaVendaTop / [TGFSTMVT](TGFSTMVT.md) | NROUNICO->NROUNICO | I |
| BaseStMediaVendaTop / [TGFSTMVT](TGFSTMVT.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
