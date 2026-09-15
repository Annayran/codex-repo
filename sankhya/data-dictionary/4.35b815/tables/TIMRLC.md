# TIMRLC — Reajuste Contrato Locação

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimReajusteLocContrato | Contrato Locação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RLCBLOQUEADO | Canc. Bloq. | S |  |  | N |
| RLCCONTRATO | Nro Contrato | I |  |  | N |
| RLCESTAGIO | Estágio | S |  |  | N |
| RLCINDICE | Índice Correção | I |  |  | N |
| RLCLOG | Detalhes | S |  |  | N |
| RLCQTDMESES | Qtd. Meses | I |  |  | N |
| RLCREAJUSTE | Nro Reajuste | I |  |  | N |
| RLCTXCORR | Tx. Correção Acm. % | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimReajusteLocacao / [TIMRLO](TIMRLO.md) | TimReajusteLocContrato / [TIMRLC](TIMRLC.md) | RLOCODIGO->RLCREAJUSTE | I |
| TimReajusteLocContrato / [TIMRLC](TIMRLC.md) | Moeda / [TSIMOE](TSIMOE.md) | RLCINDICE->CODMOEDA | I |
| TimReajusteLocContrato / [TIMRLC](TIMRLC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | RLCCONTRATO->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
