# TGFFDIC — Lote FDIC

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LoteFDIC | Lote FDIC | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro FDIC | I |  |  | N |
| CODUSURESP | Cód. Usuário Responsável | I |  |  | N |
| DHALTER | Data da Alteração | H |  |  | N |
| DTFECHAMENTO | Data Fechamento do Lote | H |  |  | N |
| IDLOTEFDIC | Identificador do Lote FDIC | I |  |  | N |
| NROCESSAOFDIC | Número da Cessão FDIC | S |  |  | N |
| TAXAEFETIVA | Taxa Evolução | F |  |  | N |
| TOTALAQUISICAO | Total da Aquisição | F |  |  | N |
| TOTALESPECIE | Total da Espécie | F |  |  | N |
| TOTALFACE | Total de Face | F |  |  | N |
| TOTALQUOTAS | Total de Quotas | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LoteFDIC / [TGFFDIC](TGFFDIC.md) | ItemLoteFDIC / [TGFIFDIC](TGFIFDIC.md) | IDLOTEFDIC->IDLOTEFDIC | I |
| LoteFDIC / [TGFFDIC](TGFFDIC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LoteFDIC / [TGFFDIC](TGFFDIC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
