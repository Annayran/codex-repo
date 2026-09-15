# TIMTZL — Zonas de Uso

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
| TimZonaUso | Zona de Uso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TZLABREVIACAO | Abreviação | S |  |  | N |
| TZLCODIGO | Código | I |  |  | N |
| TZLZONADEUSO | Zona de uso | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLote / [TIMLTE](TIMLTE.md) | TimZonaUso / [TIMTZL](TIMTZL.md) | LTETIPOZONA->TZLCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
