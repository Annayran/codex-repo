# TAPSAM — Status Amostra

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
| StatusAmostra | Status Amostra | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | CODPROJ | I |  |  | N |
| DHINC | DHINC | H |  |  | N |
| IDAMOSTRA | IDAMOSTRA | S |  |  | N |
| IDSESSAO | IDSESSAO | S |  |  | N |
| OBSREC | OBSREC | S |  |  | N |
| STATUS | STATUS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| StatusAmostra / [TAPSAM](TAPSAM.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
