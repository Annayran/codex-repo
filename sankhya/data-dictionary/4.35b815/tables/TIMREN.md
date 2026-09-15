# TIMREN — Renegociação de Parcelas

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRenegParcelas | Reneg. de Parcelas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RENCODIGO | Código | I |  |  | N |
| RENCONTRATO | Contrato | I |  |  | N |
| RENDHALTERACAO | Dh. Alteração | H |  |  | N |
| RENDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| RENDTBASE | Dt. Base | D |  |  | N |
| RENDTENTRADA | Dt. Entrada/Sinal | D |  |  | N |
| RENDTPARCELA | Dt. Parcela | D |  |  | N |
| RENESTAGIO | Estágio | S |  |  | N |
| RENMESBALAO | Mês de Balão | I |  |  | N |
| RENNURENEG | Nro. Reneg. SKW | I |  |  | N |
| RENQTDENTRADA | Qtd. Entrada/Sinal | I |  |  | N |
| RENQTDPARCELA | Qtd. Parcela | I |  |  | N |
| RENUSUALTERACAO | Usuário Alterou | I |  |  | N |
| RENUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| RENVLRBALAO | Vlr. Balão | F |  |  | N |
| RENVLRCORR | Vlr. Corr. | F |  |  | N |
| RENVLRDESC | Vlr. Desconto | F |  |  | N |
| RENVLRDESCAP | Vlr. Descap | F |  |  | N |
| RENVLRENTRADA | Vlr. Entrada/Sinal | F |  |  | N |
| RENVLRJUROS | Vlr. Juros | F |  |  | N |
| RENVLRMULTA | Vlr. Multa | F |  |  | N |
| RENVLRPARCELAR | Vlr. Parcelar | F |  |  | N |
| RENVLRTOTAL | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRenegNovaParcela / [TIMREP](TIMREP.md) | TimRenegParcelas / [TIMREN](TIMREN.md) | REPRENEG->RENCODIGO | I |
| TimRenegParcelaOrigem / [TIMREO](TIMREO.md) | TimRenegParcelas / [TIMREN](TIMREN.md) | REORENEG->RENCODIGO | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | RENCONTRATO->LTVCODIGO | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | TimRenegNovaParcela / [TIMREP](TIMREP.md) | RENCODIGO->REPRENEG | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | TimRenegParcelaOrigem / [TIMREO](TIMREO.md) | RENCODIGO->REORENEG | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | Usuario / [TSIUSU](TSIUSU.md) | RENUSUINCLUSAO->CODUSU | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RENUSUALTERACAO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
