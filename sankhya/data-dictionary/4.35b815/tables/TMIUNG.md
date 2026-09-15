# TMIUNG — Unidades Gerenciais

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeGerencial | Unidade Gerencial | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativa | S |  |  | N |
| CODNGE | Nível Gerencial | I |  |  | N |
| CODUNG | Und. Gerencial | I |  |  | N |
| CODUNGPAI | Und. Gerencial Pai | I |  |  | N |
| CODUSURESP | Usuário Responsável | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| UNIDADE | Unidade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| CentroResultado / [TSICUS](TSICUS.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| EfeitoProblema / [TMIEFE](TMIEFE.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| QuebraItensMeta / [TMIQUEI](TMIQUEI.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | CODUNG->CODUNG | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | CentroResultado / [TSICUS](TSICUS.md) | CODUNG->CODUNG | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | CODUNG->CODUNG | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | NivelGerencial / [TMINGE](TMINGE.md) | CODNGE->CODNGE | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| UsuarioPorUnidadeGerencial / [TMIUXG](TMIUXG.md) | UnidadeGerencial / [TMIUNG](TMIUNG.md) | CODUNG->CODUNG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
