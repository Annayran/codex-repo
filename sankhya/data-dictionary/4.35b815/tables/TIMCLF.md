# TIMCLF — Classificados

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimClassificado | Classificados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLFCODIGO | Código | I |  |  | N |
| CLFDATAFIM | Dt. Fim Publicação | D |  |  | N |
| CLFDATAINI | Dt. Início Publicação | D |  |  | N |
| CLFDESCRICAO | Descrição | S |  |  | N |
| CLFESTAGIO | Estágio | S |  |  | N |
| CLFMOSTRAPRECO | Apresenta Valor | S |  |  | N |
| CLFOBSERVACAO | Observações | S |  |  | N |
| CLFPARAQUE | Utilizado para | S |  |  | N |
| CLFVEICULO | Veículo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimClassificado / [TIMCLF](TIMCLF.md) | TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | CLFCODIGO->CFICLASSIFICADO | I |
| TimClassificado / [TIMCLF](TIMCLF.md) | TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | CLFVEICULO->VCOCODIGO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimClassificado / [TIMCLF](TIMCLF.md) | CFICLASSIFICADO->CLFCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
