# TIMVCO — Veículos de comunicação

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimVeiculoComunicacao | Veículo de Comunicação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| VCOCODIGO | Código | I |  |  | N |
| VCOMIDIA | Mídia | I |  |  | N |
| VCONOMEDOVEICULO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimClassificado / [TIMCLF](TIMCLF.md) | TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | CLFVEICULO->VCOCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | FACVEICULO->VCOCODIGO | I |
| TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | TimMidia / [TIMMDA](TIMMDA.md) | VCOMIDIA->MDACODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | FACVEICULO->VCOCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
