# TAPTOP — Topografia

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Topografia | Topografia | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | Projeto | I |  |  | N |
| COORDX | Coordenada X | F |  |  | N |
| COORDY | Coordenada Y | F |  |  | N |
| COORDZ | Coordenada Z | F |  |  | N |
| IDOBJETO | Objeto | S |  |  | N |
| IDTOPOGRAFIA | Identificador | S |  |  | N |
| NOMETAB | Tabela | S |  |  | N |
| QUINTACOLUNA | Quinta coluna | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Topografia / [TAPTOP](TAPTOP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
