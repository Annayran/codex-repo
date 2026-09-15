# TIMAPS — Registro de Aproveitamento de Saldo

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRegistroAprovSaldo | Registro de Aproveitamento de Saldo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APSCODIGO | Código | I |  |  | N |
| APSCOMPRADOR | Comprador | I |  |  | N |
| APSDATA | Dt. Aproveitamento | D |  |  | N |
| APSLOTEAMENTO | Loteamento | I |  |  | N |
| APSNURENEG | Nro. Reneg. Titulos | I |  |  | N |
| APSVLRTOTALABERTO | Vlr. Total Aberto | F |  |  | N |
| APSVLRTOTALAPROVEITADO | Vlr. Total Aproveitado | F |  |  | N |
| APSVLRTOTALAPROVEITAR | Vlr. Total Aproveitar | F |  |  | N |
| APSVLRTOTALCANCELADO | Vlr. Total Cancelado | F |  |  | N |
| APSVLRTOTALPENDENTE | Vlr. Total Pendente | F |  |  | N |
| APSVLRTOTALRESTANTE | Vlr. Total Restante | F |  |  | N |
| APSVLRTOTALRESTITUIR | Vlr. Total Restituir | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | Parceiro / [TGFPAR](TGFPAR.md) | APSCOMPRADOR->CODPARC | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | TimContratoCancelado / [TIMAPO](TIMAPO.md) | APSCODIGO->APOAPROVSALDO | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | TimContratoRenegociado / [TIMAPR](TIMAPR.md) | APSCODIGO->APRAPROVSALDO | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | APSLOTEAMENTO->LOTCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
