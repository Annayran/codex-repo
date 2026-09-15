# TPQPEQ — Pergunta X Questionário

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerguntaQuestionario | Pergunta X Questionário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDO | Apelido | S |  |  | N |
| CODPERG | Cód. da pergunta | I |  |  | N |
| CODQUEST | Cód. do questionário | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PESO | Peso | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PerguntaQuestionario / [TPQPEQ](TPQPEQ.md) | Pergunta / [TPQPER](TPQPER.md) | CODPERG->CODPERG | I |
| Questionario / [TPQQUE](TPQQUE.md) | PerguntaQuestionario / [TPQPEQ](TPQPEQ.md) | CODQUEST->CODQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
