# TCCNEG — Negociação CC

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NegociacaoCivil | Negociação de Contrato Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| CODCTABCOINT | Banco | I |  |  | N |
| CODFORM | Fórmula | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODTIPTIT | Tipo Titulo | I |  |  | N |
| CODTIPVENDA | Negociação | I |  |  | N |
| DATAINI | Dt. Inicio | D |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHTIPVENDA | DHTIPVENDA | H |  |  | N |
| DIAVENC | Dia Vencimento | I |  |  | N |
| FORPAG | Forma de Pagamento | S |  |  | N |
| NUMNEG | NUMNEG | I |  |  | N |
| PERIODO | Periodicidade | I |  |  | N |
| PROVIVAR | Provisiona Variação? | S |  |  | N |
| QTDPAR | Qtd. Parcelas | I |  |  | N |
| TIPVENC | Tipo Vencimento | S |  |  | N |
| VLRPAR | Vlr. Parcela | F |  |  | N |
| VLRTOT | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | NegociacaoCivil / [TCCNEG](TCCNEG.md) | CODCC->CODCC | I |
| FinanceiroCivil / [TCCFIN](TCCFIN.md) | NegociacaoCivil / [TCCNEG](TCCNEG.md) | CODCC->CODCC; NUMNEG->NUMNEG | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | ContratoCivil / [TCCCON](TCCCON.md) | CODCC->CODCC | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | FormulaCivil / [TCCFOR](TCCFOR.md) | CODFORM->CODFORM | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
