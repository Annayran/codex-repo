# TIMCTR — TABLE TIMCTR

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRenovacaoContrato | Contratos Renovação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CTRAPROVADO | Aprovado | S |  |  | N |
| CTRCODUSUALTERACAO | Usuário Alteração | I |  |  | N |
| CTRCODUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| CTRCONTRATO | Contrato | I |  |  | N |
| CTRDHALTERACAO | Dh. Alteração | H |  |  | N |
| CTRDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| CTRDIAVENCIMENTO | Dia de Vencimento | I |  |  | N |
| CTRESTAGIO | Estágio | S |  |  | N |
| CTRHAPENDENCIAS | Pendências | S |  |  | S |
| CTRINDICE | Índice | I |  |  | N |
| CTRLOG | Detalhes | S |  |  | N |
| CTRNEGOCIACAO | Negociação | I |  |  | N |
| CTRQTDMESES | Qtd. Meses Acumulados | I |  |  | N |
| CTRQTDMESESRENOVAR | Qtd. Meses Renovar | I |  |  | N |
| CTRRENOVACAO | Renovação | I |  |  | N |
| CTRSACNEGOCIACAO | SAC Negociação | I |  |  | N |
| CTRTXCORRECAO | Tx. Correção | F |  |  | N |
| CTRVALORALUGUEL | Vlr. Aluguel Novo | F |  |  | N |
| CTRVLRALUGUELATUAL | Vlr. Aluguel Atual | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimRenovacao / [TIMRCT](TIMRCT.md) | TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | RCTCODIGO->CTRRENOVACAO | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | Moeda / [TSIMOE](TSIMOE.md) | CTRINDICE->CODMOEDA | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | CTRSACNEGOCIACAO->SACCODIGO | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | TimLocacao / [TIMLOC](TIMLOC.md) | CTRCONTRATO->LOCCODIGO | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | TimRenovacao / [TIMRCT](TIMRCT.md) | CTRRENOVACAO->RCTCODIGO | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | Usuario / [TSIUSU](TSIUSU.md) | CTRCODUSUINCLUSAO->CODUSU | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CTRCODUSUALTERACAO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
