# TGFFTC — Fatura do cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FaturaCartao | Fatura do Cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| DTFECHAMENTO | Dt. Fechamento | D |  |  | N |
| DTREFERENCIA | Dt. Vencto | D |  |  | N |
| NROLOTE | Nro. Lote | I |  |  | N |
| NUFTC | Nro. Único | I |  |  | N |
| NURENEG | Nro. Fatura | I |  |  | N |
| VLRTOTAL | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturaCartao / [TGFFTC](TGFFTC.md) | ItemFaturaCartao / [TGFIFC](TGFIFC.md) | NUFTC->NUFTC | I |
| FaturaCartao / [TGFFTC](TGFFTC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | FaturaCartao / [TGFFTC](TGFFTC.md) | NUFTC->NUFTC | I |
| ItemFaturaCartao / [TGFIFC](TGFIFC.md) | FaturaCartao / [TGFFTC](TGFFTC.md) | NUFTC->NUFTC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
