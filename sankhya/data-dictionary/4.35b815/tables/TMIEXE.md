# TMIEXE — Exercícios

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExercicioGestaoIndicadores | Exercício | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXE | Exercício | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Última alteração | H |  |  | N |
| DTFIN | Data de Finalização | D |  |  | N |
| DTINI | Data de Início | D |  |  | N |
| OBS | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EfeitoProblema / [TMIEFE](TMIEFE.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | MetaExercicio / [TMIMXE](TMIMXE.md) | CODEXE->CODEXE | I |
| ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | RealizadoMeta / [TMIRME](TMIRME.md) | CODEXE->CODEXE | I |
| ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LogAtualizacaoMetas / [TMILOG](TMILOG.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| MetaExercicio / [TMIMXE](TMIMXE.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| RealizadoMeta / [TMIRME](TMIRME.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
