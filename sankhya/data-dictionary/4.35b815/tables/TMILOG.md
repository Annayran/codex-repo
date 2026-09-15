# TMILOG — LogAtualizacaoMetas

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogAtualizacaoMetas | Log de Atualização das Metas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEXE | Cód. Exercício | I |  |  | N |
| DHULTEXEC | Dh. Inclusão | H |  |  | N |
| LOG | Log Execução | C |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |
| SITULTATUAL | Evento | S |  |  | N |
| TEMPOPREV | Tempo Prev. | S |  |  | N |
| TEMPOPREVACUM | Tempo Prev. Acum. | S |  |  | N |
| TEMPOREAL | Tempo Real. | S |  |  | N |
| TEMPOREALACUM | Tempo Real. Acum. | S |  |  | N |
| TEMPOTOTAL | Tempo Total | S |  |  | N |
| TIPOATUAL | Tipo Atualização | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogAtualizacaoMetas / [TMILOG](TMILOG.md) | ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | CODEXE->CODEXE | I |
| LogAtualizacaoMetas / [TMILOG](TMILOG.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
