# TMIMET — Metas

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 31
- Instâncias DWF: 2
- Relações DWF: 22
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MetaGestaoIndicadores | Meta | S | S |
| MetaGestaoIndicadoresPai | Meta Pai | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APRESDECGRAF | Apresentar casas decimais no gráfico | S |  |  | N |
| CODIND | Indicador | I |  |  | N |
| CODINSTQUEBRA | Código da quebra | I |  |  | N |
| CODUNG | Und. Gerencial | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHPROXATUAL | Data Próx. Atualização | H |  |  | N |
| DSPREVACUM | DSPREVACUM | S |  |  | N |
| DSPREVPER | DSPREVPER | S |  |  | N |
| DSREALACUM | DSREALACUM | S |  |  | N |
| DSREALPER | DSREALPER | S |  |  | N |
| EXPSQLPREVACUM | Expressão de SQL para Previsto Acumulado | C |  |  | N |
| EXPSQLPREVPER | Expressão SQL para Previsto no Período | C |  |  | N |
| EXPSQLREALACUM | Expressão de Valor Realizado Acumulado | C |  |  | N |
| EXPSQLREALPER | Expressão SQL para calculo do realizado no período | C |  |  | N |
| EXPVALPREVACUM | Expressão de Valor Previsto Acumulado | C |  |  | N |
| EXPVALPREVPER | Expressão de Valor Previsto no Período | C |  |  | N |
| EXPVALREALACUM | Expressão de Valor Realizado Acumulado | C |  |  | N |
| EXPVALREALPER | Expressão de Valor Realizado no Período | C |  |  | N |
| HORARIOATU | Horário Atualização | T |  |  | N |
| INFOPERIODO | Informações de período | S |  |  | S |
| NOMEINSTAQUEBRA | NOMEINSTAQUEBRA | S |  |  | S |
| NUDSB | Dashboard de detalhamento | I |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |
| NUMETPAI | Nro. Meta Pai | I |  |  | N |
| PERIODICIDADE | Periodicidade | I |  |  | N |
| PERIODICIDADEATU | Periodicidade Atualização | I |  |  | N |
| QTDPERLINEAR | Qtd. períodos para cálculo da tendência | I |  |  | N |
| TIPOACUMPREV | Tipo de Acumulado Previsto | S |  |  | N |
| TIPOREAL | Tipo de Valor Realizado | S |  |  | N |
| TIPOREALACUM | Tipo de Realizado Acumulado | S |  |  | N |
| TIPPREV | Tipo de Valor Previsto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EfeitoProblema / [TMIEFE](TMIEFE.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| FaixaFarol / [TMIFME](TMIFME.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| LogAtualizacaoMetas / [TMILOG](TMILOG.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| MetaExercicio / [TMIMXE](TMIMXE.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | Dashboard / [TSIDSB](TSIDSB.md) | NUDSB->NUDSB | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | FaixaFarol / [TMIFME](TMIFME.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | Indicador / [TMIIND](TMIIND.md) | CODIND->CODIND | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | Instancia / [TDDINS](TDDINS.md) | CODINSTQUEBRA->NUINSTANCIA | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | MetaExercicio / [TMIMXE](TMIMXE.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | MetaGestaoIndicadoresPai / [TMIMET](TMIMET.md) | NUMETPAI->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | ParametroMeta / [TMIPME](TMIPME.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | ParametrosDaQuebraDaMeta / [TMIQUEP](TMIQUEP.md) | NUMET->NUMETBASE | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | QuebraItensMeta / [TMIQUEI](TMIQUEI.md) | NUMET->NUMETBASE | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | QuebraMetaLigacoes / [TMIQUEL](TMIQUEL.md) | NUMET->NUMETBASE | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | RealizadoMeta / [TMIRME](TMIRME.md) | NUMET->NUMET | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| ParametroMeta / [TMIPME](TMIPME.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| PlanoAcaoMeta / [TMIPXM](TMIPXM.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| RealizadoMeta / [TMIRME](TMIRME.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | CODUNG->CODUNG | I |
| ViewLogAtualizacaoMetas / [VMILOG](VMILOG.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
