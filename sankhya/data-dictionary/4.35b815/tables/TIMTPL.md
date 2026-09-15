# TIMTPL — TIMTPL

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimPlanos | Planos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TPLCODIGO | Código | I |  |  | N |
| TPLDESCRICAO | Descrição | S |  |  | N |
| TPLPORTAL | Portal | I |  |  | N |
| TPLQTDCONTRATADO | Qtd. Contratada | I |  |  | N |
| TPLQTDINSERIDA | Qtd. Incluída | I |  |  | S |
| TPLSIGLA | Cód. de Integração | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelPlano / [TIMTIP](TIMTIP.md) | TimPlanos / [TIMTPL](TIMTPL.md) | TIPPLANO->TPLCODIGO | I |
| TimPlanos / [TIMTPL](TIMTPL.md) | TimImovelPlano / [TIMTIP](TIMTIP.md) | TPLCODIGO->TIPPLANO | I |
| TimPlanos / [TIMTPL](TIMTPL.md) | TimPortalImoveis / [TIMPOR](TIMPOR.md) | TPLPORTAL->PORCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
