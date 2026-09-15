# TIMHIS — Motivo de SAC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimMotivoSac | Motivo de SAC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| HISCODIGO | Código | I |  |  | N |
| HISDESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimMotivoSac / [TIMHIS](TIMHIS.md) | TimTipoSac / [TIMTHI](TIMTHI.md) | HISCODIGO->THICODHISTORICO | I |
| TimTipoSac / [TIMTHI](TIMTHI.md) | TimMotivoSac / [TIMHIS](TIMHIS.md) | THICODHISTORICO->HISCODIGO | I |
| TimViewTipoSac / [VIMTHI](VIMTHI.md) | TimMotivoSac / [TIMHIS](TIMHIS.md) | THICODHISTORICO->HISCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
