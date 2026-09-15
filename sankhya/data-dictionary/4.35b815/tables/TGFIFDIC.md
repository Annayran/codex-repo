# TGFIFDIC — Item de Lote FDIC

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemLoteFDIC | Item de Lote FDIC | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSAQUE | Código do Saque | I |  |  | N |
| IDLOTEFDIC | Identificador do Lote FDIC | I |  |  | N |
| TAXAEFETIVA | Taxa Evolução | F |  |  | N |
| VLRCESSAO | Valor Cessão | F |  |  | N |
| VLROPERACAO | Valor Operação | F |  |  | N |
| VLRPARCELA | Valor da Parcela | F |  |  | N |
| VLRSAQUE | Valor do Saque | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LoteFDIC / [TGFFDIC](TGFFDIC.md) | ItemLoteFDIC / [TGFIFDIC](TGFIFDIC.md) | IDLOTEFDIC->IDLOTEFDIC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
