# TGFHBC — Históricos Lançamentos Bancários

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 4
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoBancario | Históricos Lançamentos Bancários | S | S |
| HistoricoBancarioDestino | Histórico de lançamentos bancários (Destino) | S | N |
| HistoricoLancBancarioDespesa | Históricos de Lançamentos Bancários Despesas | S | N |
| HistoricoLancBancarioReceita | Históricos de Lançamentos Bancários Receitas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITADEV | Aceita Devolução Individual? | S |  |  | N |
| CODLANC | Código | I |  |  | N |
| DESCRLANCBCO | Descrição | S |  |  | N |
| TIPLANC | Tipo do Lançamento | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANC->CODLANC | I |
| ContaBancaria / [TSICTA](TSICTA.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANCBAIXABOLRAP->CODLANC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANCBCOREC->CODLANC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | HistoricoBancarioDestino / [TGFHBC](TGFHBC.md) | CODLANCBCOPAG->CODLANC | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | HistoricoLancBancarioDespesa / [TGFHBC](TGFHBC.md) | CODLANCDESP->CODLANC | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | HistoricoLancBancarioReceita / [TGFHBC](TGFHBC.md) | CODLANCREC->CODLANC | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANC->CODLANC | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | HistoricoBancarioDestino / [TGFHBC](TGFHBC.md) | CODLANCDEST->CODLANC | I |
| MovimentoBancarioDevCheque / [TGFMBC](TGFMBC.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANC->CODLANC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
