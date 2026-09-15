# TIMRCM — TABLE TIMRCM

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRescisaoLocDetalhamentos | Detalhamentos de Rescisão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RCMCODIGO | Código | I |  |  | N |
| RCMCODIPTU | Cod. Iptu | I |  |  | N |
| RCMCOMPLEMENTO | Complemento | S |  |  | N |
| RCMDTFIM | Dt. Fim | D |  |  | N |
| RCMDTINICIO | Dt. Início | D |  |  | N |
| RCMDTLORIGEM | Detalhamento Origem | I |  |  | N |
| RCMDTVENCDESP | Dt. Vencimento | D |  |  | N |
| RCMFINDESP | Nro. Único Despesa | I |  |  | N |
| RCMGERAALUG | Gera em parcela avulsa | S |  |  | N |
| RCMGERADESP | Gera contas a pagar | S |  |  | N |
| RCMHISTORICO | Tipo de detalhamento | I |  |  | N |
| RCMIMOVEL | Imóvel Iptu | I |  |  | N |
| RCMNUFINORIGEM | Nro. Único Origem | I |  |  | N |
| RCMORIGEMDESPESA | Origem Despesa | S |  |  | N |
| RCMPARCDESP | Parceiro | I |  |  | N |
| RCMPARCELAIPTU | Parcela Iptu | I |  |  | N |
| RCMRECEBEDE | Recebe de | S |  |  | N |
| RCMREPASSAPARA | Repassa para | S |  |  | N |
| RCMRESCISAO | Cod. Rescisão | I |  |  | N |
| RCMVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRescisaoLoc / [TIMREC](TIMREC.md) | TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | RECCODIGO->RCMRESCISAO | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | Parceiro / [TGFPAR](TGFPAR.md) | RCMPARCDESP->CODPARC | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | RCMCODIPTU->PIPIPTU; RCMIMOVEL->PIPIMOVEL; RCMPARCELAIPTU->PIPCODIGO | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | TimRescisaoLoc / [TIMREC](TIMREC.md) | RCMRESCISAO->RECCODIGO | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | RCMHISTORICO->HCBCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
