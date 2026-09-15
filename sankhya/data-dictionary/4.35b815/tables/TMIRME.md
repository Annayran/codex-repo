# TMIRME — Realizados de Metas

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RealizadoMeta | Realizado por Meta | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXE | Exercício | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRPERIODO | Descrição do período | S |  |  | N |
| DHALTERPREV | Última alteração valor previsto | H |  |  | N |
| DHALTERREAL | Última alteração valor realizado | H |  |  | N |
| FAROL | Farol | I |  |  | N |
| FAROLACUM | Farol do Acumulado | I |  |  | N |
| FECHADO | Fechado | S |  |  | N |
| MANUAL | Atualização Manual | S |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |
| PERFIN | Final do Período | D |  |  | N |
| PERINI | Início do Período | D |  |  | N |
| VLRPREV | Valor Previsto | F |  |  | N |
| VLRPREVACUM | Valor Previsto Acumulado | F |  |  | N |
| VLRREAL | Valor Real | F |  |  | N |
| VLRREALACUM | Valor Real Acumulado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EfeitoProblema / [TMIEFE](TMIEFE.md) | RealizadoMeta / [TMIRME](TMIRME.md) | CODEXE->CODEXE; NUMET->NUMET; PERINI->PERINI | I |
| ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | RealizadoMeta / [TMIRME](TMIRME.md) | CODEXE->CODEXE | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | RealizadoMeta / [TMIRME](TMIRME.md) | NUMET->NUMET | I |
| RealizadoMeta / [TMIRME](TMIRME.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| RealizadoMeta / [TMIRME](TMIRME.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| RealizadoMeta / [TMIRME](TMIRME.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
