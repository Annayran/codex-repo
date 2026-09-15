# VIMTHI — Tipo de SAC

- Módulo técnico: [VIM](../modules/VIM.md)
- Campos: 4
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimViewTipoSac | Tipo de SAC | S | N |
| TimViewTipoSacImovel | Tipos de SAC para Imóveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| THICODHISTORICO | Motivo | I |  |  | N |
| THICODIGO | Código | I |  |  | N |
| THIDESCRICAO | Descrição Motivo | S |  |  | N |
| THIORIGEM | Produto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimViewTipoSac / [VIMTHI](VIMTHI.md) | SACORIGEM->THIORIGEM; SACTIPOHIST->THICODIGO | I |
| TimViewTipoSac / [VIMTHI](VIMTHI.md) | TimMotivoSac / [TIMHIS](TIMHIS.md) | THICODHISTORICO->HISCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
