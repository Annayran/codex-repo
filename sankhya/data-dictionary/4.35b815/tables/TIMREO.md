# TIMREO — Parcelas Origem Reneg

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRenegParcelaOrigem | Parcelas Origem Reneg. | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| REODTVENC | Dt. Vencimento | D |  |  | N |
| REONUFIN | Nro. Único Parcela | I |  |  | N |
| REORENEG | Renegociação | I |  |  | N |
| REOVLRCORR | Vlr. Corr. | F |  |  | N |
| REOVLRDESCAP | Vlr. Descap | F |  |  | N |
| REOVLRDESDOB | Vlr. Principal | F |  |  | N |
| REOVLRJUROS | Vlr. Juros | F |  |  | N |
| REOVLRMULTA | Vlr. Multa | F |  |  | N |
| REOVLRTOTAL | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRenegParcelaOrigem / [TIMREO](TIMREO.md) | Financeiro / [TGFFIN](TGFFIN.md) | REONUFIN->NUFIN | I |
| TimRenegParcelaOrigem / [TIMREO](TIMREO.md) | TimRenegParcelas / [TIMREN](TIMREN.md) | REORENEG->RENCODIGO | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | TimRenegParcelaOrigem / [TIMREO](TIMREO.md) | RENCODIGO->REORENEG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
