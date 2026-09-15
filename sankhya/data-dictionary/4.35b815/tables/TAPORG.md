# TAPORG — Orgão Regulamentador

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OrgaoRegulamentador | Órgão Regulamentador | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODORG | Orgão Regulamentador | I |  |  | N |
| NOME | Nome | S |  |  | N |
| SIGLA | Sigla | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrgaoRegulamentador / [TAPORG](TAPORG.md) | ValorReferencia / [TAPVOR](TAPVOR.md) | CODORG->CODORG | I |
| ValorReferencia / [TAPVOR](TAPVOR.md) | OrgaoRegulamentador / [TAPORG](TAPORG.md) | CODORG->CODORG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
