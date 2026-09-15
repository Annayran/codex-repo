# TMIEFE — Efeito/Problema

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EfeitoProblema | Efeito / Problema | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXE | Exercício | I |  |  | N |
| CODUNG | Unidade Gerencial | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTANALISE | Dt. Análise | H |  |  | N |
| NUEFEITO | Cód. Análise | I |  |  | N |
| NUMET | Meta | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PERINI | Período | H |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CausaEfeitoProblema / [TMICEP](TMICEP.md) | EfeitoProblema / [TMIEFE](TMIEFE.md) | NUEFEITO->NUEFEITO | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | CausaEfeito / [TMICAU](TMICAU.md) | NUEFEITO->NUEFEITO | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | RealizadoMeta / [TMIRME](TMIRME.md) | CODEXE->CODEXE; NUMET->NUMET; PERINI->PERINI | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
