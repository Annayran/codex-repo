# TIMLCR — Negociações

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 25
- Instâncias DWF: 2
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimNegociacao | Negociações | S | N |
| TimUltimaNegociacao | Última Negociação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LCRAPOLICE | Apólice | S |  |  | N |
| LCRCODIGO | Código | I |  |  | N |
| LCRCODUSUINC | Usuário Inclusão | I |  |  | N |
| LCRCONTADOR | Nro. Negociação | I |  |  | N |
| LCRCONTRATO | Contrato | I |  |  | N |
| LCRDATAINICIO | Dt. Início Vigência | D |  |  | N |
| LCRDATARENEGOCIACAO | Dh. Inclusão | H |  |  | N |
| LCRDATATERMINO | Dt. Término Vigência | D |  |  | N |
| LCRDHALTER | Dh. Alteração | H |  |  | N |
| LCRDIAVENC | Dia de Vcto. | I |  |  | N |
| LCRDTPRIVENC | Dt. Primeiro Venc. | D |  |  | N |
| LCRESTAGIO | Estágio | S |  |  | N |
| LCRGEROUPARCELAS | Gerou parcelas | S |  |  | N |
| LCRINICIOSEGURO | Dt. Início Seguro | D |  |  | N |
| LCRISENTATARIFABOL | Isenta tarifa do boleto | S |  |  | N |
| LCROBSERVACAO | Observação | S |  |  | N |
| LCRPRAZOLOCACAO | Qtd. meses de locação | I |  |  | N |
| LCRQTDMPRO | Qtd. Meses Prorrogados | I |  |  | N |
| LCRSEGADM | Feito com a administradora | S |  |  | N |
| LCRSEGURADORA | Seguradora | S |  |  | N |
| LCRTERMINOSEGURO | Dt. Término Seguro | D |  |  | N |
| LCRUSUARIO | Usuário Alteração | I |  |  | N |
| LCRVALORALUGUEL | Vlr. Aluguel Negociado | F |  |  | N |
| LCRVALORALUGUELATUAL | Vlr. Aluguel Atualizado | F |  |  | N |
| LCRVALORSEGURO | Vlr. Seguro | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | TIMCONTRATOLOC->LCRCONTRATO; TIMNEGOCIACAO->LCRCONTADOR | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | DTLCONTRATOLOC->LCRCONTRATO; DTLNEGOCIACAO->LCRCONTADOR | I |
| TimFinanceiroAluguel / [TGFFIN](TGFFIN.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | TIMNEGOCIACAO->LCRCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | LOCCODIGO->LCRCONTRATO | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | Financeiro / [TGFFIN](TGFFIN.md) | LCRCONTADOR->TIMNEGOCIACAO; LCRCONTRATO->TIMCONTRATOLOC | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LCRCONTRATO->LOCCODIGO | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | LCRUSUARIO->CODUSU | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | LCRCODUSUINC->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
