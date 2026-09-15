# TMIPLA — Plano de Ação

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 26
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanoAcao | Plano de Ação | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXE | Exercício | I |  |  | N |
| CODPLA | Código | I |  |  | N |
| CODUNG | Un. Gerencial | I |  |  | N |
| CODUSU | Cód. Usuário Inclusão | I |  |  | N |
| CODUSUALTER | Cód. Usuário Modificação | I |  |  | N |
| CODUSUEXEC | Quem | I |  |  | N |
| COMO | Como | S |  |  | N |
| COMPLETUDE | % Completude | F |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dt./Hora Modificação | H |  |  | N |
| DTFIMPREVISTO | Fim previsto | H |  |  | N |
| DTFIMREALIZADO | Fim realizado | H |  |  | N |
| DTINIPREVISTO | Início previsto | H |  |  | N |
| DTINIREALIZADO | Início realizado | H |  |  | N |
| DTPLA | Dt./Hora Inclusão | H |  |  | N |
| ESFORCOPREVISTO | Esforço Previsto | S |  |  | S |
| ESFORCOREALIZADO | Esforço Realizado | S |  |  | S |
| NUMET | Meta | I |  |  | N |
| NUMOS | Ordem de serviço | I |  |  | N |
| OBS | Observação | S |  |  | N |
| ONDE | Onde | S |  |  | N |
| OQUE | O que | S |  |  | N |
| PORQUE | Porque | S |  |  | N |
| QUANTO | Quanto custa | S |  |  | N |
| RETORNO | Retorno esperado | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanoAcao / [TMIPLA](TMIPLA.md) | CausaEfeitoProblema / [TMICEP](TMICEP.md) | CODPLA->CODPLA | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | PlanoAcaoMeta / [TMIPXM](TMIPXM.md) | CODPLA->CODPLA | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUEXEC->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
