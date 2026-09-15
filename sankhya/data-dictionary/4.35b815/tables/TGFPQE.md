# TGFPQE — Perguntas do Questionário da Pesquisa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PerguntasQuestionarioPesquisa | Perguntas do Questionário da Pesquisa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUQUE | Núm. do Questionário | I |  |  | N |
| OBRIGATORIA | Pergunta obrigatória? | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TEXTO | Texto | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| QuestionarioPesquisa / [TGFQUE](TGFQUE.md) | PerguntasQuestionarioPesquisa / [TGFPQE](TGFPQE.md) | NUQUE->NUQUE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
