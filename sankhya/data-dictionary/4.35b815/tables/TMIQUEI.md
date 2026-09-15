# TMIQUEI — TABLE TMIQUEI

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| QuebraItensMeta | Quebra de itens da meta | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AD_DESQUEBRA | Descrição | S |  |  | S |
| CODQUEBRA | Código | I |  |  | N |
| CODUNG | Código da unidade gerencial | I |  |  | N |
| NUMETBASE | Número do item da meta | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | QuebraItensMeta / [TMIQUEI](TMIQUEI.md) | NUMET->NUMETBASE | I |
| QuebraItensMeta / [TMIQUEI](TMIQUEI.md) | ParametrosItemQuebraMeta / [TMIQUEP](TMIQUEP.md) | CODQUEBRA->CODQUEBRA; NUMETBASE->NUMETBASE | I |
| QuebraItensMeta / [TMIQUEI](TMIQUEI.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
