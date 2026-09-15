# TIMIVI — TABLE TIMIVI

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptuImovel | Imóveis vinculados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IVIGEROUPARCELAS | Gerou Parcelas | S |  |  | N |
| IVIIMOVEL | Imóvel | I |  |  | N |
| IVIIPTU | Cod. IPTU | I |  |  | N |
| IVIISENTACOMISSAO | Isenta Comissão | S |  |  | N |
| IVIISENTAIPTU | Isenta IPTU | S |  |  | N |
| IVIPERCENTUAL | Percentual | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIptu / [TIMIPT](TIMIPT.md) | TimIptuImovel / [TIMIVI](TIMIVI.md) | IPTCODIGO->IVIIPTU | I |
| TimIptuImovel / [TIMIVI](TIMIVI.md) | TimImovel / [TIMIMV](TIMIMV.md) | IVIIMOVEL->IMVCODIGO | I |
| TimIptuImovel / [TIMIVI](TIMIVI.md) | TimIptu / [TIMIPT](TIMIPT.md) | IVIIPTU->IPTCODIGO | I |
| TimIptuImovel / [TIMIVI](TIMIVI.md) | TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | IVIIMOVEL->PIPIMOVEL; IVIIPTU->PIPIPTU | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimIptuImovel / [TIMIVI](TIMIVI.md) | PIPIMOVEL->IVIIMOVEL; PIPIPTU->IVIIPTU | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimIptuImovel / [TIMIVI](TIMIVI.md) | RICIMOVEL->IVIIMOVEL; RICIPTU->IVIIPTU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
