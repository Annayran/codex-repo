# TPQPES — Pesquisa

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Pesquisa | Pesquisa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTATOPESQ | CODCONTATOPESQ | I |  |  | N |
| CODEMPFUNPESQ | CODEMPFUNPESQ | I |  |  | N |
| CODFUNCPESQ | CODFUNCPESQ | I |  |  | N |
| CODPAPPESQ | CODPAPPESQ | I |  |  | N |
| CODPARCPESQ | Cód. Parceiro Pesq | I |  |  | N |
| CODPRODPESQ | CODPRODPESQ | I |  |  | N |
| CODQUEST | CODQUEST | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUPESQ | CODUSUPESQ | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DTAPLICACAO | DTAPLICACAO | H |  |  | N |
| HORAAPLICACAO | HORAAPLICACAO | I |  |  | N |
| NOMEPESQ | NOMEPESQ | S |  |  | N |
| NOTAFINAL | NOTAFINAL | F |  |  | N |
| NUCURRICULOPESQ | NUCURRICULOPESQ | I |  |  | N |
| NUPESQ | NUPESQ | I |  |  | N |
| NUPLA | NUPLA | I |  |  | N |
| OBSERVACAO | OBSERVACAO | S |  |  | N |
| TIPOPESQ | TIPOPESQ | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Consenso / [TPQCON](TPQCON.md) | Pesquisa / [TPQPES](TPQPES.md) | NUPESQCONSENSO->NUPESQ | I |
| Pesquisa / [TPQPES](TPQPES.md) | PesquisaOrdemServico / [TCSPOS](TCSPOS.md) | NUPESQ->NUPESQ | I |
| Pesquisa / [TPQPES](TPQPES.md) | Planejamento / [TPQPLA](TPQPLA.md) | NUPLA->NUPLA | I |
| Pesquisa / [TPQPES](TPQPES.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |
| Pesquisa / [TPQPES](TPQPES.md) | RespostaPerguntaPesquisa / [TPQRPE](TPQRPE.md) | NUPESQ->NUPESQ | I |
| PesquisaOrdemServico / [TCSPOS](TCSPOS.md) | Pesquisa / [TPQPES](TPQPES.md) | NUPESQ->NUPESQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
