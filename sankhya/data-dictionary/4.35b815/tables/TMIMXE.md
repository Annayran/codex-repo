# TMIMXE — Metas por Exercícios

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MetaExercicio | Meta por Exercício | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXE | Exercício | I |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | MetaExercicio / [TMIMXE](TMIMXE.md) | CODEXE->CODEXE | I |
| MetaExercicio / [TMIMXE](TMIMXE.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| MetaExercicio / [TMIMXE](TMIMXE.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | MetaExercicio / [TMIMXE](TMIMXE.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
