# TIMIFE — Caracterizações

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimInfraestrutura | Infraestruturas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IFECARACTERISTICA | Característica no Imóvel | S |  |  | N |
| IFECODIGO | Código | I |  |  | N |
| IFEINFRAESTRUTURA | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | TimInfraestrutura / [TIMIFE](TIMIFE.md) | IEOINFRAESTRUTURA->IFECODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
