# TIMRIC — TABLE TIMRIC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptuReajusteImovel | Imóveis para reajuste | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RICIMOVEL | Imóvel | I |  |  | N |
| RICIPTU | Cod. IPTU | I |  |  | N |
| RICNOVOIPTU | Novo IPTU | I |  |  | N |
| RICPARCELA | Vlr. Parcela | F |  |  | N |
| RICPERCENTUAL | Percentual de IPTU | F |  |  | N |
| RICQTDEPARC | Qtde. Parcelas | I |  |  | N |
| RICREAJUSTE | Cod. Reajuste | I |  |  | N |
| RICVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIptuReajuste / [TIMRIP](TIMRIP.md) | TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | RIPCODIGO->RICREAJUSTE | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimImovel / [TIMIMV](TIMIMV.md) | RICIMOVEL->IMVCODIGO | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimIptu / [TIMIPT](TIMIPT.md) | RICNOVOIPTU->IPTCODIGO | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimIptuImovel / [TIMIVI](TIMIVI.md) | RICIMOVEL->IVIIMOVEL; RICIPTU->IVIIPTU | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimIptuReajuste / [TIMRIP](TIMRIP.md) | RICREAJUSTE->RIPCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
