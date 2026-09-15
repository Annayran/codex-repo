# TIMTPI — Tipos de Imóveis

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 2
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelTipo | Tipos de Imóveis | S | N |
| TimTipoImovelFiltro | Para usar em filtro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TPICODIGO | Código | I |  |  | N |
| TPICQMTIPO | Tipo | S |  |  | N |
| TPIEXIGESEGIND | Exige Seguro Incêndio | S |  |  | N |
| TPITIPO | Tipo do Imóvel | S |  |  | N |
| TPITIPODEIMOVEL | Descrição | S |  |  | N |
| TPIVIVAREAL | Tipo | S |  |  | N |
| TPIWIMOVEISSUBTIPO | Subtipo | S |  |  | N |
| TPIWIMOVEISTIPO | Tipo | S |  |  | N |
| TPIZAPCATEGRIAIMOVEL | Categoria | S |  |  | N |
| TPIZAPSUBTIPOIMOVEL | Subtipo | S |  |  | N |
| TPIZAPTIPOIMOVEL | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimImovelTipo / [TIMTPI](TIMTPI.md) | FACTIPOIMOVEL->TPICODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimImovelTipo / [TIMTPI](TIMTPI.md) | IMVTIPODOIMOVEL->TPICODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimImovelTipo / [TIMTPI](TIMTPI.md) | FACTIPOIMOVEL->TPICODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
