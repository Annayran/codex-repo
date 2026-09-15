# TPQPER — Pergunta

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 17
- Instâncias DWF: 2
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Pergunta | Pergunta | S | N |
| Pergunta2 | Pergunta2 | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITAOBSERVACAO | Aceita observação | S |  |  | N |
| CODGRUPOPERG | Cód. grupo pergunta | I |  |  | N |
| CODPERG | Cód. pergunta | I |  |  | N |
| CODPERGTAB | Cód. da tabela | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRPERG | Descrição | S |  |  | N |
| DHALTER | Data de alteração | H |  |  | N |
| INSTRUCAOAPLICADOR | Instrução para aplicação | S |  |  | N |
| NROTENTATIVAS | Núm. de tentativas | I |  |  | N |
| OBRIGATORIA | Obrigatória | S |  |  | N |
| ORDEMTAB | Ordem na tabela | I |  |  | N |
| PERMITEANEXO | Permite Anexo | S |  |  | N |
| PERMITEFILTRAR | Permite filtrar pergunta | S |  |  | N |
| QTDMAXESCOLHAS | Qtd. máxima de escolhas | I |  |  | N |
| QTDMAXLINHAS | Qtd. máxima de linhas | I |  |  | N |
| TEMPOPARARESPOSTA | Tempo para resposta | F |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pergunta / [TPQPER](TPQPER.md) | GrupoPergunta / [TPQGRP](TPQGRP.md) | CODGRUPOPERG->CODGRUPOPERG | I |
| Pergunta / [TPQPER](TPQPER.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERGTAB | I |
| Pergunta / [TPQPER](TPQPER.md) | PerguntaFilha / [TPQDPD](TPQDPD.md) | CODPERG->CODPERGMESTRE | I |
| Pergunta / [TPQPER](TPQPER.md) | RelacaoPergunta / [TPQDPD](TPQDPD.md) | CODPERG->CODPERGDEP | I |
| Pergunta / [TPQPER](TPQPER.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERG->CODPERG | I |
| PerguntaFilha / [TPQDPD](TPQDPD.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERGDEP->CODPERG | I |
| PerguntaQuestionario / [TPQPEQ](TPQPEQ.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| ProcessoProcedimento / [TCSPEP](TCSPEP.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| QuestionarioFluxo / [TCSQXF](TCSQXF.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| RelacaoPergunta / [TPQDPD](TPQDPD.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERGMESTRE->CODPERG | I |
| RelacaoPergunta / [TPQDPD](TPQDPD.md) | Pergunta2 / [TPQPER](TPQPER.md) | CODPERGDEP->CODPERG | I |
| RespostaPergunta / [TPQRES](TPQRES.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| RespostaPerguntaPesquisa / [TPQRPE](TPQRPE.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
