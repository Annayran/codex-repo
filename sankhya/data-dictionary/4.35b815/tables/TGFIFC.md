# TGFIFC — Item de Fatura de Cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemFaturaCartao | Item da Fatura do Cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCLOJISTA | Parceiro Lojista | I |  |  | N |
| NUFIN | Nro Único Financeiro | I |  |  | N |
| NUFTC | Nro Fatura Cartão | I |  |  | N |
| PARCELA | Parcelas | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRTOTITEM | Vlr.Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FaturaCartao / [TGFFTC](TGFFTC.md) | ItemFaturaCartao / [TGFIFC](TGFIFC.md) | NUFTC->NUFTC | I |
| ItemFaturaCartao / [TGFIFC](TGFIFC.md) | FaturaCartao / [TGFFTC](TGFFTC.md) | NUFTC->NUFTC | I |
| ItemFaturaCartao / [TGFIFC](TGFIFC.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ItemFaturaCartao / [TGFIFC](TGFIFC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCLOJISTA->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
