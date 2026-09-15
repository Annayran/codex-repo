# TIMLDT — TimDetalhamentoLoteamento

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimDetalhamentoLoteamento | Detalhamentos p/ Loteamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LDTCODIGO | Código | I |  |  | N |
| LDTCOMPLEMENTO | Complemento | S |  |  | N |
| LDTDATAALTERACAO | Dh. Alteração | H |  |  | N |
| LDTDATAINCLUSAO | Dh. Inclusão | H |  |  | N |
| LDTDATAMOV | Dt. Atual | D |  |  | N |
| LDTDTFIM | Dt. Fim Período | D |  |  | N |
| LDTDTINIC | Dt. Início Período | D |  |  | N |
| LDTHISTORICO | Tipo do Detalhamento | I |  |  | N |
| LDTJUROSMULTA | Incide em juros e multa | S |  |  | N |
| LDTPARCELA | ID Parcela | I |  |  | N |
| LDTREAJUSTE | Nro. Reajuste | I |  |  | N |
| LDTRECEBEDE | Recebe de | S |  |  | N |
| LDTREPASSAPARA | Repassa para | S |  |  | N |
| LDTUSUARIO | Usuário Inclusão | I |  |  | N |
| LDTUSUARIOALT | Usuário Alteração | I |  |  | N |
| LDTVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | NUFIN->LDTPARCELA | I |
| TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | LDTHISTORICO->HCBCODIGO | I |
| TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | Usuario / [TSIUSU](TSIUSU.md) | LDTUSUARIO->CODUSU | I |
| TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | Usuario2 / [TSIUSU](TSIUSU.md) | LDTUSUARIOALT->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
