# TAPCQM — Composto Químico

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CompostoQuimico | Composto Químico | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCQM | Composto Químico | I |  |  | N |
| DESCRCIAO | Descrição | S |  |  | N |
| SIGLA | Sigla | S |  |  | N |
| VALORPADRAO | Valor Padrão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ValorReferencia / [TAPVOR](TAPVOR.md) | CompostoQuimico / [TAPCQM](TAPCQM.md) | CODCQM->CODCQM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
