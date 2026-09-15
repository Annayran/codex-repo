# TGFMBC — Movimento Bancário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 34
- Instâncias DWF: 8
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentacaoBancariaDespesa | Movimentação Bancária Despesas | S | N |
| MovimentacaoBancariaDestino | Movimentação Bancária Destino | S | N |
| MovimentacaoBancariaReceita | Movimentação Bancária Receita | S | N |
| MovimentoBancario | Movimento Bancário | S | N |
| MovimentoBancarioConPart | Movimento Bancário Contrapartida | S | N |
| MovimentoBancarioDevCheque | Movimento Bancário Devolução de Cheque | S | S |
| MovimentoBancarioFox | MovimentoBancarioFox | S | N |
| MovimentoBancarioPDV | MovimentoBancarioPDV | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOCONTRA | Cód. conta bancária | I |  |  | N |
| CODCTABCOINT | Conta Origem | I |  |  | N |
| CODCTABCOINTDEST | Conta Destino | I |  |  | S |
| CODLANC | Lanç. Origem | I |  |  | N |
| CODLANCDEST | Lanç. Destino | I |  |  | S |
| CODPDV | Cod. PDV | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONCILIADO | Conciliado | S |  |  | N |
| CONCILIADODEST | Conciliado Destino | S |  |  | S |
| CONTABILIZADO | Contabilizado? | S |  |  | S |
| DHCONCILIACAO | Dh. Conciliação | H |  |  | N |
| DHTIPOPER | Dh. Operação | H |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |
| DTCONTAB | Dt. Contabilização | H |  |  | N |
| DTINCLUSAO | Dt. Inclusão | H |  |  | N |
| DTLANC | Dt. Lançamento | D |  |  | N |
| HISTORICO | Histórico | S |  |  | N |
| NROIMPORT | Nro da importação | I |  |  | N |
| NUBCO | Núm. Único Bancário | I |  |  | N |
| NUBCOCP | Núm. Banco | I |  |  | N |
| NUCAIXA | Núm. Caixa | I |  |  | N |
| NUMDOC | Núm. Documento | I |  |  | N |
| NUMTRANSF | Número | I |  |  | N |
| ORIGMOV | Tipo de Movimento | S |  |  | N |
| PREDATA | Pré-Data | H |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |
| SALDO | Saldo | F |  |  | N |
| TALAO | Talão | I |  |  | N |
| VLRCHEQUE | Vlr. Cheque | F |  |  | S |
| VLRLANC | Vlr. Lançamento | F |  |  | N |
| VLRLANC_DESTINO | Vlr. Lanç. Destino | F |  |  | S |
| VLRMOEDA | Vlr. Moeda | F |  |  | N |
| VLRTROCO | Vlr. Troco | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | MovimentoBancario / [TGFMBC](TGFMBC.md) | NUBCO->NUBCO | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOCONTRA->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | ContaDestino / [TSICTA](TSICTA.md) | CODCTABCOINTDEST->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANC->CODLANC | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | HistoricoBancarioDestino / [TGFHBC](TGFHBC.md) | CODLANCDEST->CODLANC | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | ImplantacaoSaldoConta / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | MovimentoBancarioConPart / [TGFMBC](TGFMBC.md) | NUBCO->NUBCO | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | SaldoBancario / [TGFSBC](TGFSBC.md) | CODCTABCOINT->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MovimentoBancarioDevCheque / [TGFMBC](TGFMBC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| MovimentoBancarioDevCheque / [TGFMBC](TGFMBC.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANC->CODLANC | I |
| MovimentoBancarioDevCheque / [TGFMBC](TGFMBC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| SaldoMoeda / [TGFMBS](TGFMBS.md) | MovimentacaoBancariaDespesa / [TGFMBC](TGFMBC.md) | NUBCODESP->NUBCO | I |
| SaldoMoeda / [TGFMBS](TGFMBS.md) | MovimentacaoBancariaReceita / [TGFMBC](TGFMBC.md) | NUBCOREC->NUBCO | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | MovimentacaoBancariaDestino / [TGFMBC](TGFMBC.md) | NUBCODEST->NUBCO | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | MovimentoBancario / [TGFMBC](TGFMBC.md) | NUBCO->NUBCO | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | MovimentoBancario / [TGFMBC](TGFMBC.md) | NUBCO->NUBCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
