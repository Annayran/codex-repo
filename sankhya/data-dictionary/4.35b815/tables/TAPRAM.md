# TAPRAM — Resultado Análise

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ResultadoAnalise | ResultadoAnalise | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | Projeto | I |  |  | N |
| DHINC | Data de inclusão | H |  |  | N |
| IDAMOSTRA | Id Amostra | S |  |  | N |
| IDSESSAO | IDSESSAO | S |  |  | N |
| SIGLA | Sigla | S |  |  | N |
| VALOR | Valor | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ResultadoAnalise / [TAPRAM](TAPRAM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
