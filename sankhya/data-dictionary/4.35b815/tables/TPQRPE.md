# TPQRPE — Resposta de Pergunta em Pesquisa

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RespostaPerguntaPesquisa | Resposta de Pergunta em Pesquisa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANEXO | ANEXO | B |  |  | N |
| CODPERG | CODPERG | I |  |  | N |
| CODRESP | CODRESP | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUCORRETOR | CODUSUCORRETOR | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DHCORRECAO | DHCORRECAO | H |  |  | N |
| NOTA | NOTA | F |  |  | N |
| NUPESQ | NUPESQ | I |  |  | N |
| OBSERVACAO | OBSERVACAO | S |  |  | N |
| PROIBELIMPEZA | PROIBELIMPEZA | S |  |  | N |
| TEXTO | TEXTO | S |  |  | N |
| VALOR | VALOR | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pesquisa / [TPQPES](TPQPES.md) | RespostaPerguntaPesquisa / [TPQRPE](TPQRPE.md) | NUPESQ->NUPESQ | I |
| RespostaPerguntaPesquisa / [TPQRPE](TPQRPE.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| RespostaPerguntaPesquisa / [TPQRPE](TPQRPE.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERG->CODPERG; CODRESP->CODRESP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
