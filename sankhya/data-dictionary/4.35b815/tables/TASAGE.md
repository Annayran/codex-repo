# TASAGE — Agentes

- Módulo técnico: [TAS](../modules/TAS.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Agente | Agente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CONTEUDO | CONTEUDO | B |  |  | N |
| DESCRAGENTE | Descrição | S |  |  | N |
| DESCRITOR | DESCRITOR | C |  |  | N |
| DHINC | Incluído em? | H |  |  | N |
| HASH | HASH | S |  |  | N |
| ID | ID | S |  |  | N |
| NOTIFICADO | Hub Notificado? | S |  |  | N |
| QTDEXEC | Qtd. de Execuções | I |  |  | N |
| SCHEDULE | SCHEDULE | S |  |  | N |
| STATUS | Status | S |  |  | N |
| ULTLOG | Ultimo Log de execução | C |  |  | N |
| VERSAO | VERSAO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Agente / [TASAGE](TASAGE.md) | PermissaoAgente / [TASPAG](TASPAG.md) | ID->ID | L |
| PermissaoAgente / [TASPAG](TASPAG.md) | Agente / [TASAGE](TASAGE.md) | ID->ID | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
