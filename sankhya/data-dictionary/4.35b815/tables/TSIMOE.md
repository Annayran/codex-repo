# TSIMOE — Moedas

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 7
- Instâncias DWF: 3
- Relações DWF: 38
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Moeda | Moeda | S | S |
| Moeda2 | Moeda | S | N |
| TimMoeda | TimMoeda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODINTMOEDA | Código tabela internacional | I |  |  | N |
| CODMOEDA | Código | I |  |  | N |
| CODTABBCB | Cód. tabela BCB | I |  |  | N |
| NOMEMOEDA | Nome | S |  |  | N |
| TIPCALC | Tipo do Acúmulo | S |  |  | N |
| TIPMOEDA | Tipo | S |  |  | N |
| TIPOTAXA | Tipo de taxa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDARENDA->CODMOEDA | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Contrato / [TCSCON](TCSCON.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Contrato / [TCSCON](TCSCON.md) | Moeda2 / [TSIMOE](TSIMOE.md) | CODMOEALTREAJ->CODMOEDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Moeda2 / [TSIMOE](TSIMOE.md) | PPAUTASC->CODMOEDA | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| CotacaoMoeda / [TSICOT](TSICOT.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| HistoricoFormula / [TCCHTF](TCCHTF.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| JuroMensal / [TGFJUM](TGFJUM.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Moeda / [TSIMOE](TSIMOE.md) | CotacaoMoeda / [TSICOT](TSICOT.md) | CODMOEDA->CODMOEDA | I |
| MoedaPortalCotacao / [TGFMOPC](TGFMOPC.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Produto / [TGFPRO](TGFPRO.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Servico / [TGFPRO](TGFPRO.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | Moeda / [TSIMOE](TSIMOE.md) | ADMINDICETXADM->CODMOEDA | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Moeda / [TSIMOE](TSIMOE.md) | LTVINDICECORRECAO->CODMOEDA | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Moeda / [TSIMOE](TSIMOE.md) | EPRINDICEAPOSCHAVES->CODMOEDA | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Moeda2 / [TSIMOE](TSIMOE.md) | EPRINDICEATECHAVES->CODMOEDA | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | Moeda / [TSIMOE](TSIMOE.md) | LOCINDEXADOR->CODMOEDA | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | Moeda2 / [TSIMOE](TSIMOE.md) | LOCCODMOEDACDU->CODMOEDA | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimMoeda / [TSIMOE](TSIMOE.md) | LOCINDICETXADM->CODMOEDA | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Moeda / [TSIMOE](TSIMOE.md) | LOTINDEXADOR->CODMOEDA | I |
| TimParcelamento / [TIMQPV](TIMQPV.md) | Moeda / [TSIMOE](TSIMOE.md) | QPVINDICE->CODMOEDA | I |
| TimParcelamento / [TIMQPV](TIMQPV.md) | Moeda2 / [TSIMOE](TSIMOE.md) | QPVINDICEAPCHV->CODMOEDA | I |
| TimReajusteContrato / [TIMRCV](TIMRCV.md) | Moeda / [TSIMOE](TSIMOE.md) | RCVINDICE->CODMOEDA | I |
| TimReajusteLocContrato / [TIMRLC](TIMRLC.md) | Moeda / [TSIMOE](TSIMOE.md) | RLCINDICE->CODMOEDA | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | Moeda / [TSIMOE](TSIMOE.md) | CTRINDICE->CODMOEDA | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDAVP->CODMOEDA | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
