# TIMACM — Acúmulo de Parcelas

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAcumuloParcelas | Acúmulo de Parcelas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACMCONTRATO | Nro. Contrato | I |  |  | N |
| ACMDHALTER | Dh. Alteração | H |  |  | N |
| ACMDHINCUSAO | Dh. Inclusão | H |  |  | N |
| ACMDTACUMULO | Dt. Acúmulo | D |  |  | N |
| ACMDTCANCELADO | Dt. Cancelado | D |  |  | N |
| ACMDTVENC | Dt. Vencimento | D |  |  | N |
| ACMESTAGIO | Estágio | S |  |  | N |
| ACMNUFIN | Nro. Único Parcela | I |  |  | N |
| ACMNURENEG | Nro. Reneg. SKW | I |  |  | N |
| ACMSEQUENCIA | Código | I |  |  | N |
| ACMUSUALTER | Usuário Alteração | I |  |  | N |
| ACMUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| ACMVLRACUMULADO | Vlr. Acumulado | F |  |  | N |
| ACMVLRDESCONTO | Vlr. Desconto | F |  |  | N |
| ACMVLRTOTAL | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | Financeiro / [TGFFIN](TGFFIN.md) | ACMNUFIN->NUFIN | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | ACMCONTRATO->LTVCODIGO | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | TimParcelasOrigem / [TIMACO](TIMACO.md) | ACMCONTRATO->ACOCONTRATO; ACMSEQUENCIA->ACOACUMULO | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | Usuario / [TSIUSU](TSIUSU.md) | ACMUSUINCLUSAO->CODUSU | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | Usuario2 / [TSIUSU](TSIUSU.md) | ACMUSUALTER->CODUSU | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimAcumuloParcelas / [TIMACM](TIMACM.md) | LTVCODIGO->ACMCONTRATO | I |
| TimParcelasOrigem / [TIMACO](TIMACO.md) | TimAcumuloParcelas / [TIMACM](TIMACM.md) | ACOACUMULO->ACMSEQUENCIA; ACOCONTRATO->ACMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
