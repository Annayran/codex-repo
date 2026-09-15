# TPQDPD — Relação entre perguntas

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 5
- Instâncias DWF: 2
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerguntaFilha | Pergunta filha | N | N |
| RelacaoPergunta | Relação entre Perguntas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPERGDEP | Cód. pergunta dependente | I |  |  | N |
| CODPERGMESTRE | Cód. pergunta mestre | I |  |  | N |
| CODRESPMESTRE | Cód. resposta mestre | I |  |  | N |
| DHALTER | Data de alteração | H |  |  | N |
| ORDEM | Ordem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pergunta / [TPQPER](TPQPER.md) | PerguntaFilha / [TPQDPD](TPQDPD.md) | CODPERG->CODPERGMESTRE | I |
| Pergunta / [TPQPER](TPQPER.md) | RelacaoPergunta / [TPQDPD](TPQDPD.md) | CODPERG->CODPERGDEP | I |
| PerguntaFilha / [TPQDPD](TPQDPD.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERGDEP->CODPERG | I |
| PerguntaFilha / [TPQDPD](TPQDPD.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERGMESTRE->CODPERG; CODRESPMESTRE->CODRESP | I |
| RelacaoPergunta / [TPQDPD](TPQDPD.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERGMESTRE->CODPERG | I |
| RelacaoPergunta / [TPQDPD](TPQDPD.md) | Pergunta2 / [TPQPER](TPQPER.md) | CODPERGDEP->CODPERG | I |
| RelacaoPergunta / [TPQDPD](TPQDPD.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERGMESTRE->CODPERG; CODRESPMESTRE->CODRESP | I |
| RespostaPergunta / [TPQRES](TPQRES.md) | RelacaoPergunta / [TPQDPD](TPQDPD.md) | CODPERG->CODPERGMESTRE; CODRESP->CODRESPMESTRE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
