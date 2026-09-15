# TAPTAN — Tipo de Análise

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoAnalise | Tipo de Análise | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPANA | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoAnaliseAmostra / [TAPTAA](TAPTAA.md) | TipoAnalise / [TAPTAN](TAPTAN.md) | CODTIPANA->CODTIPANA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
