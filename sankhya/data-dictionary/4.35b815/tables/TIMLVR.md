# TIMLVR — Renegociações de Saldo

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 42
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRenegociacao | Renegociações de Saldo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LVRALTERATABELA | Altera para Price | S |  |  | N |
| LVRAPROVEITASALDO | Aproveit. Saldo | S |  |  | N |
| LVRCODIGO | Código | I |  |  | N |
| LVRCONTRATO | Contrato | I |  |  | N |
| LVRCORRECAO | Vlr. Correção | F |  |  | N |
| LVRDATA | Dt. Reneg. | D |  |  | N |
| LVRDESCAP | Vlr. Descaptalização | F |  |  | N |
| LVRDESCONTO | Vlr. Desconto | F |  |  | N |
| LVRDTAMORTSALDO | Dt. Amort. Saldo Dev. | D |  |  | N |
| LVRDTPRIPARCELA | Dt. Primeira Parc. | D |  |  | N |
| LVRESTAGIO | Estágio | S |  |  | N |
| LVRFECHAMENTOBANCO | Fechamento Banco | S |  |  | N |
| LVRFINBANCODEVOLVER | Nro. Fin. Devolver Banco | I |  |  | N |
| LVRFINCOMPENSAMORT | Nro. Fin. Compensar | I |  |  | N |
| LVRJUROS | Tx. Reneg. % | F |  |  | N |
| LVRMAISJUROS | Tx. Juros % | F |  |  | N |
| LVRMAXDESC | Vlr. Limite Desconto | F |  |  | N |
| LVRNUACERTO | Nro. Acerto | I |  |  | N |
| LVRNURENEG | Nro. Reneg. Skw | I |  |  | N |
| LVROLDJUROS | Tx. Juros Anterior % | F |  |  | N |
| LVROLDPROXREAJUSTE | Dt. Old Prox. Reajuste | H |  |  | N |
| LVRPARCELASEMATRASO | Qtd. Parc's. Atraso | I |  |  | N |
| LVRPQDESCONTO | Justificativa Desconto | S |  |  | N |
| LVRPRIPARCELA | Nro. Primeira Parc. | I |  |  | N |
| LVRQTDPARCABERTO | Qtd. Parc's. Aberto | I |  |  | N |
| LVRQTDPARCELAS | Qtd. Parcelas | I |  |  | N |
| LVRQTDPARPAGAS | Qtd. Parc's. Pagas | I |  |  | N |
| LVRRENEGOCIACAO | Renegociação | S |  |  | N |
| LVRSMJURMUL | Vlr. Soma Juros Multa | F |  |  | N |
| LVRTXRENEG | Vlr. Tx. Juros Reneg. | F |  |  | N |
| LVRVALOREMABERTO | Vlr. Em Aberto | F |  |  | N |
| LVRVALOREMATRASO | Vlr. Em Atraso | F |  |  | N |
| LVRVALORPAGO | Vlr. Pago | F |  |  | N |
| LVRVALORPARCELA | Vlr. Parcela | F |  |  | N |
| LVRVLAPARCELAR | Vlr. Refinanciar | F |  |  | N |
| LVRVLPAGORENEGOC | Vlr. Amort. Saldo Dev. | F |  |  | N |
| LVRVLRBANCOATUAL | Vlr. Atual Banco | F |  |  | N |
| LVRVLRBANCODEVOLVER | Vlr. Devolver Banco | F |  |  | N |
| LVRVLRBANCODIFER | Vlr. Diferença Banco | F |  |  | N |
| LVRVLRBANCOFECHADO | Vlr. Fechado Banco | F |  |  | N |
| LVRVLRTOTALBALAO | Vlr. Total Balões | F |  |  | N |
| LVRVLRTOTALINTERMED | Vlr. Total Intermediárias | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | TIMCONTRATOLTV->LVRCONTRATO; TIMRENEGLOTE->LVRCODIGO | I |
| TimBalaoReneg / [TIMBAR](TIMBAR.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | BARCONTRATO->LVRCONTRATO; BARRENEG->LVRCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | LTVCODIGO->LVRCONTRATO | I |
| TimIntermedReneg / [TIMINR](TIMINR.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | INRCONTRATO->LVRCODIGO; INRRENEG->LVRCONTRATO | I |
| TimRenegociacao / [TIMLVR](TIMLVR.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | LVRCONTRATO->LTVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
