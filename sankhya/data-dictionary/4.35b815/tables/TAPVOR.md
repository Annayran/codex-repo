# TAPVOR — Valor Referência por Órgão

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ValorReferencia | Valor Referência | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCQM | CODCQM | I |  |  | N |
| CODORG | CODORG | I |  |  | N |
| VALOR | VALOR | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrgaoRegulamentador / [TAPORG](TAPORG.md) | ValorReferencia / [TAPVOR](TAPVOR.md) | CODORG->CODORG | I |
| ValorReferencia / [TAPVOR](TAPVOR.md) | CompostoQuimico / [TAPCQM](TAPCQM.md) | CODCQM->CODCQM | I |
| ValorReferencia / [TAPVOR](TAPVOR.md) | OrgaoRegulamentador / [TAPORG](TAPORG.md) | CODORG->CODORG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
