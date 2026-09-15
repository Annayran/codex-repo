# TIMRCV — Reajuste Contratos

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimReajusteContrato | Reajuste Por Contrato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RCVCONTRATO | Contrato | I |  |  | N |
| RCVDTREAJUSTE | Dt. Reajuste | D |  |  | N |
| RCVDTULTREAJUSTE | Dt. Últ. Reajuste | D |  |  | N |
| RCVESTAGIO | Estágio | S |  |  | N |
| RCVINDICE | Índice | I |  |  | N |
| RCVLOG | Detalhes | S |  |  | N |
| RCVQTDMESES | Qtd. Meses Reajustar | I |  |  | N |
| RCVREAJUSTE | Reajuste | I |  |  | N |
| RCVTABELAPRICE | Price | S |  |  | N |
| RCVTXINDICEACM | Vlr. Tx. Índice Acum %. | F |  |  | N |
| RCVTXJUROACM | Vlr. Tx. Juros Acum %. | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimReajuste / [TIMRET](TIMRET.md) | TimReajusteContrato / [TIMRCV](TIMRCV.md) | RETCODIGO->RCVREAJUSTE | I |
| TimReajusteContrato / [TIMRCV](TIMRCV.md) | Moeda / [TSIMOE](TSIMOE.md) | RCVINDICE->CODMOEDA | I |
| TimReajusteContrato / [TIMRCV](TIMRCV.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | RCVCONTRATO->LTVCODIGO | I |
| TimReajusteContrato / [TIMRCV](TIMRCV.md) | TimReajuste / [TIMRET](TIMRET.md) | RCVREAJUSTE->RETCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
