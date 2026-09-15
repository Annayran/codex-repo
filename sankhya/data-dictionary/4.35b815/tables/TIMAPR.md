# TIMAPR — Contratos Renegociados

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContratoRenegociado | Contratos Renegociados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APRAPROVEITADO | Vlr. Aproveitado | F |  |  | N |
| APRAPROVSALDO | Aproveitamento de Saldo | I |  |  | N |
| APRCONTRATO | Contrato | I |  |  | N |
| APRLOTE | Lote | S |  |  | N |
| APRPENDENTE | Vlr. Aberto | F |  |  | N |
| APRQUADRA | Quadra | S |  |  | N |
| APRRESTANTE | Vlr. Pendente | F |  |  | N |
| APRVLRPARCANT | Vlr. Atual Parcela | F |  |  | N |
| APRVLRTOTAL | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContratoRenegociado / [TIMAPR](TIMAPR.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | APRCONTRATO->LTVCODIGO | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | TimContratoRenegociado / [TIMAPR](TIMAPR.md) | APSCODIGO->APRAPROVSALDO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
