# TAPTAA — Tipo de análise por amostra

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoAnaliseAmostra | Tipo de análise por amostra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPANA | Tipo Análise | I |  |  | N |
| NUAMOSTRA | Amostra | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Amostra / [TAPAMO](TAPAMO.md) | TipoAnaliseAmostra / [TAPTAA](TAPTAA.md) | NUAMOSTRA->NUAMOSTRA | I |
| TipoAnaliseAmostra / [TAPTAA](TAPTAA.md) | Amostra / [TAPAMO](TAPAMO.md) | NUAMOSTRA->NUAMOSTRA | I |
| TipoAnaliseAmostra / [TAPTAA](TAPTAA.md) | TipoAnalise / [TAPTAN](TAPTAN.md) | CODTIPANA->CODTIPANA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
