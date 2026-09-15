# TIMTLV — Tranferências

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTransferencia | Transferência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TLVCOMENTARIO | Comentário | S |  |  | N |
| TLVCONTRATO | Cod. Contrato | I |  |  | N |
| TLVDATA | Dt. Cadastro | D |  |  | N |
| TLVDATAEFETIVACAO | Dt. Efetivação | D |  |  | N |
| TLVESTAGIO | Estágio | S |  |  | N |
| TLVNURENEG | Nro. Reneg. SKW | I |  |  | N |
| TLVSEQUENCIA | Sequência | I |  |  | N |
| TLVTXTRANSF | Vlr. Tx. Transferência | I |  |  | N |
| TLVVLTRANSF | Vlr. Acertado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | TimTransferencia / [TIMTLV](TIMTLV.md) | TVPCONTRATO->TLVCONTRATO; TVPTRANSFERENCIA->TLVSEQUENCIA | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimTransferencia / [TIMTLV](TIMTLV.md) | LTVCODIGO->TLVCONTRATO | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | TimTransferencia / [TIMTLV](TIMTLV.md) | TNPCONTRATO->TLVCONTRATO; TNPTRANSFERENCIA->TLVSEQUENCIA | I |
| TimTransferencia / [TIMTLV](TIMTLV.md) | TimAntigoComprador / [TIMTVP](TIMTVP.md) | TLVCONTRATO->TVPCONTRATO; TLVSEQUENCIA->TVPTRANSFERENCIA | I |
| TimTransferencia / [TIMTLV](TIMTLV.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TLVCONTRATO->LTVCODIGO | I |
| TimTransferencia / [TIMTLV](TIMTLV.md) | TimNovoComprador / [TIMTNP](TIMTNP.md) | TLVCONTRATO->TNPCONTRATO; TLVSEQUENCIA->TNPTRANSFERENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
