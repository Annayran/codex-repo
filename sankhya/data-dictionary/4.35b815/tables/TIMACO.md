# TIMACO — Parcelas Origem

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimParcelasOrigem | Parcelas Origem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACOACUMULO | Nro. Acúmulo | I |  |  | N |
| ACOCONTRATO | Nro. Contrato | I |  |  | N |
| ACODTVENC | Dt. Vencimento | D |  |  | N |
| ACONUFIN | Nro. Único | I |  |  | N |
| ACOTIMPARCELA | Nro. Parcela | I |  |  | N |
| ACOVLRATUAL | Vlr. Atual | F |  |  | N |
| ACOVLRCORRMONET | Vlr. Corr. Monetária | F |  |  | N |
| ACOVLRDESDOB | Vlr. Original | F |  |  | N |
| ACOVLRJUROS | Vlr. Juros | F |  |  | N |
| ACOVLRMULTA | Vlr. Multa | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | TimParcelasOrigem / [TIMACO](TIMACO.md) | ACMCONTRATO->ACOCONTRATO; ACMSEQUENCIA->ACOACUMULO | I |
| TimParcelasOrigem / [TIMACO](TIMACO.md) | Financeiro / [TGFFIN](TGFFIN.md) | ACONUFIN->NUFIN | I |
| TimParcelasOrigem / [TIMACO](TIMACO.md) | TimAcumuloParcelas / [TIMACM](TIMACM.md) | ACOACUMULO->ACMSEQUENCIA; ACOCONTRATO->ACMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
