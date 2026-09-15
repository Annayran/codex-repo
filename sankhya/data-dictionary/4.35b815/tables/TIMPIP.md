# TIMPIP — TABLE TIMPIP

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptuImovelParcela | Parcelas de IPTU | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PIPCODIGO | Cod. Único | I |  |  | N |
| PIPCOMISSAO | Comissão | F |  |  | N |
| PIPDTFINPERIODO | Dt. Fim Período | D |  |  | N |
| PIPDTINIPERIODO | Dt. Início Período | D |  |  | N |
| PIPGEROUPARCELAS | Gerou Parcelas | S |  |  | N |
| PIPIMOVEL | Imóvel | I |  |  | N |
| PIPINCORPAOVALOR | Incorpora comissão ao valor | S |  |  | N |
| PIPIPTU | Cod. IPTU | I |  |  | N |
| PIPPAGO | Pago | S |  |  | N |
| PIPVLRPAGO | Vlr. Pago | F |  |  | N |
| PIPVLRPAGOINQ | Vlr. p/ inquilino | F |  |  | N |
| PIPVLRPAGOPROP | Vlr. p/ Proprietário | F |  |  | N |
| PIPVLRPARCELA | Vlr. Parcela | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | DTLCODIPTU->PIPIPTU; DTLIMOVEL->PIPIMOVEL; DTLPARCELAIPTU->PIPCODIGO | I |
| TimIptuImovel / [TIMIVI](TIMIVI.md) | TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | IVIIMOVEL->PIPIMOVEL; IVIIPTU->PIPIPTU | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | PIPCODIGO->DTLPARCELAIPTU; PIPIMOVEL->DTLIMOVEL; PIPIPTU->DTLCODIPTU | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimImovel / [TIMIMV](TIMIMV.md) | PIPIMOVEL->IMVCODIGO | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimIptu / [TIMIPT](TIMIPT.md) | PIPIPTU->IPTCODIGO | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimIptuImovel / [TIMIVI](TIMIVI.md) | PIPIMOVEL->IVIIMOVEL; PIPIPTU->IVIIPTU | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | RCMCODIPTU->PIPIPTU; RCMIMOVEL->PIPIMOVEL; RCMPARCELAIPTU->PIPCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
