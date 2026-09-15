# TIMREP — Parcelamento Reneg

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRenegNovaParcela | Novas Parcelas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| REPCODIGO | Código | I |  |  | N |
| REPDTVENC | Dt. Vencimento | D |  |  | N |
| REPNUFIN | Nro. Único Parcela | I |  |  | N |
| REPRENEG | Renegociação | I |  |  | N |
| REPTIMORIGEM | Tipo Parcela | S |  |  | N |
| REPVLRAMORT | Vlr. Amort | F |  |  | N |
| REPVLRBALAO | Vlr. Balão | F |  |  | N |
| REPVLRDESDOB | Vlr. Parcela | F |  |  | N |
| REPVLRJUROS | Vlr. Juros | F |  |  | N |
| REPVLRSALDODEV | Vlr. Saldo. Dev. | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRenegNovaParcela / [TIMREP](TIMREP.md) | Financeiro / [TGFFIN](TGFFIN.md) | REPNUFIN->NUFIN | I |
| TimRenegNovaParcela / [TIMREP](TIMREP.md) | TimRenegParcelas / [TIMREN](TIMREN.md) | REPRENEG->RENCODIGO | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | TimRenegNovaParcela / [TIMREP](TIMREP.md) | RENCODIGO->REPRENEG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
