# TMIPME — Parâmetros de Metas

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParametroMeta | Parâmetro de Meta | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FONTE | Fonte | S |  |  | N |
| LISTA | Lista | S |  |  | N |
| NOMEPAR | Nome do Parâmetro | S |  |  | N |
| NUMET | Nro. Meta | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALOR | Valor | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MetaGestaoIndicadores / [TMIMET](TMIMET.md) | ParametroMeta / [TMIPME](TMIPME.md) | NUMET->NUMET | I |
| ParametroMeta / [TMIPME](TMIPME.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
