# TIMIPT — TABLE TIMIPT

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptu | IPTU | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IPTCARNENAIMOB | Carnê na Imobiliária | S |  |  | N |
| IPTCODIGO | Cód. IPTU | I |  |  | N |
| IPTEDIFICIO | Edifício/Galeria | I |  |  | N |
| IPTGERAAVULSO | Gera avulso | S |  |  | N |
| IPTIMOVEL | Imóvel | I |  |  | N |
| IPTINSCRICAO | Inscrição Municipal | S |  |  | N |
| IPTMULTIIMOVEIS | Múltiplos imóveis por conta | S |  |  | S |
| IPTOBSERVACAO | Observação | S |  |  | N |
| IPTPERCDESC | Desconto % | F |  |  | N |
| IPTQTDPARC | Qtd. Parcelas | I |  |  | N |
| IPTRECEBEDE | Recebe de | S |  |  | N |
| IPTREFERENCIA | Referência | D |  |  | N |
| IPTREFINICOBRANCA | Referência p/ cobrança | D |  |  | N |
| IPTREFINIPAGTO | Referência p/ pagamento | D |  |  | N |
| IPTREPASSAPARA | Repassa para | S |  |  | N |
| IPTTIPO | Tipo | S |  |  | N |
| IPTTXEXPEDIENTE | Tx. Expediente | F |  |  | N |
| IPTVLRAVISTA | Vlr. previsto à vista | F |  |  | S |
| IPTVLRPARCELADO | Vlr. Previsto parcelado | F |  |  | S |
| IPTVLRTOT | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimIptu / [TIMIPT](TIMIPT.md) | CODIPTU->IPTCODIGO | I |
| TimIptu / [TIMIPT](TIMIPT.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | IPTEDIFICIO->EDGCODIGO | I |
| TimIptu / [TIMIPT](TIMIPT.md) | TimImovel / [TIMIMV](TIMIMV.md) | IPTIMOVEL->IMVCODIGO | I |
| TimIptu / [TIMIPT](TIMIPT.md) | TimIptuImovel / [TIMIVI](TIMIVI.md) | IPTCODIGO->IVIIPTU | I |
| TimIptuImovel / [TIMIVI](TIMIVI.md) | TimIptu / [TIMIPT](TIMIPT.md) | IVIIPTU->IPTCODIGO | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimIptu / [TIMIPT](TIMIPT.md) | PIPIPTU->IPTCODIGO | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimIptu / [TIMIPT](TIMIPT.md) | RICNOVOIPTU->IPTCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
