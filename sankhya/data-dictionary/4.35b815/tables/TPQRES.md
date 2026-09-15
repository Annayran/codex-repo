# TPQRES — Resposta por Pergunta

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RespostaPergunta | Resposta por Pergunta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITAOBSERVACAO | Aceita Observação | S |  |  | N |
| ANEXO | Anexo | B |  |  | N |
| ATIVO | Resposta Ativa | S |  |  | N |
| CODPERG | Cód. Pergunta | I |  |  | N |
| CODRESP | Cód. resposta | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRRESP | Descrição da Resposta | S |  |  | N |
| DHALTER | Data alteração | H |  |  | N |
| LIMPAAGRUPA | Limpa agrupamentos | S |  |  | N |
| NOMEARQUIVO | Nome do anexo | S |  |  | N |
| NOTA | Nota | F |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PADRAO | Padrão | S |  |  | N |
| PENALIDADE | Penalidade | F |  |  | N |
| SEQAGRUPA | Agrupamento | I |  |  | N |
| TAMANHOMAX | Tamanho máximo | I |  |  | N |
| VALORMAX | Valor máximo | F |  |  | N |
| VALORMIN | Valor mínimo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pergunta / [TPQPER](TPQPER.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERG->CODPERG | I |
| PerguntaFilha / [TPQDPD](TPQDPD.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERGMESTRE->CODPERG; CODRESPMESTRE->CODRESP | I |
| QuestionarioFluxo / [TCSQXF](TCSQXF.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERG->CODPERG; CODRESP->CODRESP | I |
| RelacaoPergunta / [TPQDPD](TPQDPD.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERGMESTRE->CODPERG; CODRESPMESTRE->CODRESP | I |
| RespostaPergunta / [TPQRES](TPQRES.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| RespostaPergunta / [TPQRES](TPQRES.md) | RelacaoPergunta / [TPQDPD](TPQDPD.md) | CODPERG->CODPERGMESTRE; CODRESP->CODRESPMESTRE | I |
| RespostaPergunta / [TPQRES](TPQRES.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RespostaPerguntaPesquisa / [TPQRPE](TPQRPE.md) | RespostaPergunta / [TPQRES](TPQRES.md) | CODPERG->CODPERG; CODRESP->CODRESP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
