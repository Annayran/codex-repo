# TMIIND — Indicadores

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Indicador | Indicador | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIND | Cód. Indicador | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Última alteração | H |  |  | N |
| ORIENTACAO | Orientação | S |  |  | N |
| QTDDEC | Qtd. casas decimais | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| UNMEDIDA | Und. Medida | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Indicador / [TMIIND](TMIIND.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | Indicador / [TMIIND](TMIIND.md) | CODIND->CODIND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
