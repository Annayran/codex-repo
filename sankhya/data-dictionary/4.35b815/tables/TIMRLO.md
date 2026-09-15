# TIMRLO — Reajuste Locação

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimReajusteLocacao | Reajuste Locação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RLOCODIGO | Nro. Reajuste | I |  |  | N |
| RLODHALTERACAO | Dh. Alteração | H |  |  | N |
| RLODHINCLUSAO | Dh. Inclusão | H |  |  | N |
| RLODHREAJUSTE | Dt. Reajuste | D |  |  | N |
| RLOESTAGIO | Estágio | S |  |  | N |
| RLOUSUALTERACAO | Usuário Alteração | I |  |  | N |
| RLOUSUINCLUSAO | Usuário Inclusão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimReajusteLocacao / [TIMRLO](TIMRLO.md) | TimReajusteLocContrato / [TIMRLC](TIMRLC.md) | RLOCODIGO->RLCREAJUSTE | I |
| TimReajusteLocacao / [TIMRLO](TIMRLO.md) | Usuario / [TSIUSU](TSIUSU.md) | RLOUSUINCLUSAO->CODUSU | I |
| TimReajusteLocacao / [TIMRLO](TIMRLO.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RLOUSUALTERACAO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
