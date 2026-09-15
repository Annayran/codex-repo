# TAPAMO — Amostra

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Amostra | Amostra | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | Projeto | I |  |  | N |
| IDAMOSTRA | Identificador | S |  |  | N |
| IDOBJETO | ID objeto | S |  |  | N |
| NOMETAB | Tabela | S |  |  | N |
| NUAMOSTRA | Número amostra | I |  |  | N |
| NUAMOSTRAORIG | NUAMOSTRAORIG | I |  |  | N |
| NUCLL | Padrão de classificação | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PROFFINAL | Prof. final | F |  |  | N |
| PROFINICIAL | Prof. inicial | F |  |  | N |
| TIPOAMOSTRA | Tipo de amostra | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Amostra / [TAPAMO](TAPAMO.md) | ColetaAmostra / [TAPCAM](TAPCAM.md) | NUAMOSTRA->NUAMOSTRA | I |
| Amostra / [TAPAMO](TAPAMO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Amostra / [TAPAMO](TAPAMO.md) | TipoAnaliseAmostra / [TAPTAA](TAPTAA.md) | NUAMOSTRA->NUAMOSTRA | I |
| TipoAnaliseAmostra / [TAPTAA](TAPTAA.md) | Amostra / [TAPAMO](TAPAMO.md) | NUAMOSTRA->NUAMOSTRA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
