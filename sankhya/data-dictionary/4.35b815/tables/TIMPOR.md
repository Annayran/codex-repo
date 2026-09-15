# TIMPOR — TIMPOR

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimPortalImoveis | Portais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PORCODIGO | Nro. Único | I |  |  | N |
| PORDESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimPlanos / [TIMTPL](TIMTPL.md) | TimPortalImoveis / [TIMPOR](TIMPOR.md) | TPLPORTAL->PORCODIGO | I |
| TimProcessoIntegracao / [TIMPIN](TIMPIN.md) | TimPortalImoveis / [TIMPOR](TIMPOR.md) | NUPOR->PORCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
