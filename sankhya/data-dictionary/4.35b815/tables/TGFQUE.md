# TGFQUE — Questionário da Pesquisa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| QuestionarioPesquisa | Questionário da Pesquisa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição do Questionário | S |  |  | N |
| NUQUE | Questionário | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Projeto / [TCSPRJ](TCSPRJ.md) | QuestionarioPesquisa / [TGFQUE](TGFQUE.md) | NUQUE->NUQUE | I |
| QuestionarioPesquisa / [TGFQUE](TGFQUE.md) | PerguntasQuestionarioPesquisa / [TGFPQE](TGFPQE.md) | NUQUE->NUQUE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
