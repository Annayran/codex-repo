# TPQQUE — Questionário

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Questionario | Questionário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODGRUPOQUEST | Cód. Grupo | I |  |  | N |
| CODQUEST | Código | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRQUEST | Descrição | S |  |  | N |
| DHALTER | Data e hora da alteração | H |  |  | N |
| EXIBERESPCORRETA | Exibir resposta correta | S |  |  | N |
| NROPERGPORPAG | Núm. de perguntas por pág. | I |  |  | N |
| ORDEMALEATORIA | Ordem Aleatória | S |  |  | N |
| PERGUNTASPORGRUPO | Organizar por grupos de pergunta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pesquisa / [TPQPES](TPQPES.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |
| Planejamento / [TPQPLA](TPQPLA.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |
| ProcessoProcedimento / [TCSPEP](TCSPEP.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |
| Questionario / [TPQQUE](TPQQUE.md) | GrupoQuestionario / [TPQGRQ](TPQGRQ.md) | CODGRUPOQUEST->CODGRUPOQUEST | I |
| Questionario / [TPQQUE](TPQQUE.md) | InformacaoQuestionario / [TPQINF](TPQINF.md) | CODQUEST->CODQUEST | I |
| Questionario / [TPQQUE](TPQQUE.md) | PerfilQuestionario / [TPQPFI](TPQPFI.md) | CODQUEST->CODQUEST | I |
| Questionario / [TPQQUE](TPQQUE.md) | PerguntaQuestionario / [TPQPEQ](TPQPEQ.md) | CODQUEST->CODQUEST | I |
| QuestionarioFluxo / [TCSQXF](TCSQXF.md) | Questionario / [TPQQUE](TPQQUE.md) | CODQUEST->CODQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
