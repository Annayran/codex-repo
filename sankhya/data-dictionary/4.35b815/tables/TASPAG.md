# TASPAG — Permissões de Agente

- Módulo técnico: [TAS](../modules/TAS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PermissaoAgente | Permissões de Agente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AUTORIZADO | Permitir? | S |  |  | N |
| DHAUTORIZACAO | Autorizado em | H |  |  | N |
| ID | Plugin | S |  |  | N |
| PERMISSAO | Permissão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Agente / [TASAGE](TASAGE.md) | PermissaoAgente / [TASPAG](TASPAG.md) | ID->ID | L |
| PermissaoAgente / [TASPAG](TASPAG.md) | Agente / [TASAGE](TASAGE.md) | ID->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
