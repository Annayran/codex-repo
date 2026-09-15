# TIMFEA — Fechamento Aluguel

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFechamentoAluguel | Fechamento Aluguel | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FEACODIGO | Código | I |  |  | N |
| FEAEMPREENDIMENTO | Empreendimento | I |  |  | N |
| FEAGEROUPARC | Parcelas geradas | S |  |  | N |
| FEAREFERENCIA | Referência | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFechamentoAluguel / [TIMFEA](TIMFEA.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FEAEMPREENDIMENTO->EPRCODIGO | I |
| TimFechamentoAluguel / [TIMFEA](TIMFEA.md) | TimFechamentoAluItens / [TIMFAI](TIMFAI.md) | FEACODIGO->FAIFECHAMENTO | I |
| TimFechamentoAluItens / [TIMFAI](TIMFAI.md) | TimFechamentoAluguel / [TIMFEA](TIMFEA.md) | FAIFECHAMENTO->FEACODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
