# TIMAPO — Contratos Cancelados

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContratoCancelado | Contratos Cancelados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APOAPROVEITAR | Vlr. Aproveitar | F |  |  | N |
| APOAPROVSALDO | Aproveitamento Saldo | I |  |  | N |
| APOCONTRATO | Contrato | I |  |  | N |
| APOLOTE | Lote | S |  |  | N |
| APOQUADRA | Quadra | S |  |  | N |
| APOVLRTOTAL | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContratoCancelado / [TIMAPO](TIMAPO.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | APOCONTRATO->LTVCODIGO | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | TimContratoCancelado / [TIMAPO](TIMAPO.md) | APSCODIGO->APOAPROVSALDO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
