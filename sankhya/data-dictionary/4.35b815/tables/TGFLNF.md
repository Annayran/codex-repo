# TGFLNF — LoteNotaFiscalEletronica

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LoteNotaFiscalEletronica | Lote da Nota Fiscal Eletrônica | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHRECEB | Dt.Receb.Lote NF-e | H |  |  | N |
| GUID | Guid | S |  |  | N |
| NULOTE | Nro. Lote | I |  |  | N |
| NUMLOTEPROVEDOR | NUMLOTEPROVEDOR | I |  |  | N |
| NUMRECEB | Nro.Receb.Lote NF-e | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | LoteNotaFiscalEletronica / [TGFLNF](TGFLNF.md) | NULOTENFE->NULOTE | I |
| LoteNotaFiscalEletronica / [TGFLNF](TGFLNF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NULOTE->NULOTENFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
