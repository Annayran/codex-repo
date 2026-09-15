# TIMRCT — TABLE TIMRCT

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRenovacao | Renovação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RCTCODIGO | Código | I |  |  | N |
| RCTCODUSUALTERACAO | Usuário Alteração | I |  |  | N |
| RCTCODUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| RCTDHALTERACAO | Dh. Alteração | H |  |  | N |
| RCTDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| RCTDTBASE | Dt. Base | D |  |  | N |
| RCTESTAGIO | Estágio | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRenovacao / [TIMRCT](TIMRCT.md) | TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | RCTCODIGO->CTRRENOVACAO | I |
| TimRenovacao / [TIMRCT](TIMRCT.md) | Usuario / [TSIUSU](TSIUSU.md) | RCTCODUSUINCLUSAO->CODUSU | I |
| TimRenovacao / [TIMRCT](TIMRCT.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RCTCODUSUALTERACAO->CODUSU | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | TimRenovacao / [TIMRCT](TIMRCT.md) | CTRRENOVACAO->RCTCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
