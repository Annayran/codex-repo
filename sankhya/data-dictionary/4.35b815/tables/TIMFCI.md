# TIMFCI — TABLE TIMFCI

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptuComissaoFaixa | Faixas de Comissão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FCICOMISSAO | Cod. Comissionamento | I |  |  | N |
| FCIMAXVALOR | Limite superior | F |  |  | N |
| FCIPERCENTUAL | Comissão (%) | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIptuComissao / [TIMCIT](TIMCIT.md) | TimIptuComissaoFaixa / [TIMFCI](TIMFCI.md) | CITCODIGO->FCICOMISSAO | I |
| TimIptuComissaoFaixa / [TIMFCI](TIMFCI.md) | TimIptuComissao / [TIMCIT](TIMCIT.md) | FCICOMISSAO->CITCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
