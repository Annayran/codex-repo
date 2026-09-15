# TIMTHI — Tipo de SAC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTipoSac | Tipo de SAC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| THICODHISTORICO | Motivo | I |  |  | N |
| THICODIGO | Código | I |  |  | N |
| THIDESCRICAO | Descrição Motivo | S |  |  | S |
| THIORIGEM | Produto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimMotivoSac / [TIMHIS](TIMHIS.md) | TimTipoSac / [TIMTHI](TIMTHI.md) | HISCODIGO->THICODHISTORICO | I |
| TimTipoSac / [TIMTHI](TIMTHI.md) | TimMotivoSac / [TIMHIS](TIMHIS.md) | THICODHISTORICO->HISCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
