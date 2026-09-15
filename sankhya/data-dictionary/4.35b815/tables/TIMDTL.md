# TIMDTL — Detalhamentos

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 48
- Instâncias DWF: 2
- Relações DWF: 17
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimDetalhamento | Detalhamentos Imobiliários | S | N |
| TimDetalhamentoReneg | Detalhamento Reneg. | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DLTRECALCIRRF | Recalcular IRRF | S |  |  | N |
| DTLCODIGO | Código | I |  |  | N |
| DTLCODIPTU | Cod. IPTU | I |  |  | N |
| DTLCODORIGEM | Dtl. Origem | I |  |  | N |
| DTLCOMISSAOIPTU | Vlr. Comissão Iptu | F |  |  | N |
| DTLCOMPADM | Compensação Adm | S |  |  | N |
| DTLCOMPLEMENTO | Complemento | S |  |  | N |
| DTLCONTATO | Contato (Beneficiário) | I |  |  | N |
| DTLCONTRATOLOC | Contrato de Locação | I |  |  | N |
| DTLDATAALTERACAO | Dh. Alteração | H |  |  | N |
| DTLDATAINCLUSAO | Dh. Inclusão | H |  |  | N |
| DTLDATAMOV | Dt. Atual | D |  |  | N |
| DTLDESVINCULADO | Fin. Desvinculado | I |  |  | N |
| DTLDETALHAMENTO | Detalhamento de origem | I |  |  | N |
| DTLDHGERREPASSE | Dh. Ger. Repasse | H |  |  | N |
| DTLDTFIM | Dt. Fim Período | D |  |  | N |
| DTLDTINIC | Dt. Início Período | D |  |  | N |
| DTLDTVENCDESP | Dt. Vencimento Despesa | D |  |  | N |
| DTLESTAGIO | DTLESTAGIO | I |  |  | N |
| DTLFINDESP | Despesa Gerada | I |  |  | N |
| DTLGARANTIDO | Garantido | S |  |  | N |
| DTLHISTORICO | Tipo do Detalhamento | I |  |  | N |
| DTLIDENTIFICADOR | DTLIDENTIFICADOR | I |  |  | N |
| DTLIMOVEL | Imóvel | I |  |  | N |
| DTLINCTXMINIMA | Incide tx. mínima adm. | S |  |  | S |
| DTLIRRF | Incide no IRRF | S |  |  | N |
| DTLJUROSMULTA | Incide em juros e multa | S |  |  | N |
| DTLNEGOCIACAO | Negociação | I |  |  | N |
| DTLNUACERTO | Nro. Acerto Compensação | I |  |  | N |
| DTLNUFINRENEG | Financeiro Origem Reneg. | I |  |  | N |
| DTLORIGCOMISSAO | Dtl Origem Comissão | I |  |  | N |
| DTLORIGEM | Tipo Parcela | S |  |  | N |
| DTLORIGRENEG | Repactuação | S |  |  | N |
| DTLPARCDESP | Parceiro para despesa | I |  |  | N |
| DTLPARCEIRO | Parceiro (Proprietário) | I |  |  | N |
| DTLPARCELA | ID Parcela | I |  |  | N |
| DTLPARCELAIPTU | Cod. Parcela IPTU | I |  |  | N |
| DTLPROPORCIONAL | Proporcional | S |  |  | N |
| DTLRATEADO | DTLRATEADO | I |  |  | N |
| DTLREAJUSTE | Nro. Reajuste | I |  |  | N |
| DTLRECEBEDE | Recebe de | S |  |  | N |
| DTLRENEG | Detalhamento Origem Reneg. | I |  |  | N |
| DTLREPASSAPARA | Repassa para | S |  |  | N |
| DTLSERVICO | Código da OS | I |  |  | N |
| DTLTAXAADM | Incide na tx. adm. | S |  |  | N |
| DTLUSUARIO | Usuário Inclusão | I |  |  | N |
| DTLUSUARIOALT | Usuário Alteração | I |  |  | N |
| DTLVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | NUFIN->DTLPARCELA | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Contato / [TGFCTT](TGFCTT.md) | DTLPARCEIRO->CODPARC; DTLCONTATO->CODCONTATO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Financeiro / [TGFFIN](TGFFIN.md) | DTLPARCELA->NUFIN | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Parceiro / [TGFPAR](TGFPAR.md) | DTLPARCEIRO->CODPARC | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | DTLPARCDESP->CODPARC | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | DTLORIGCOMISSAO->DTLCODIGO; DTLPARCELA->DTLPARCELA | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimDetalhamentoReneg / [TIMDTL](TIMDTL.md) | DTLNUFINRENEG->DTLPARCELA; DTLRENEG->DTLCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimFinanceiro / [TGFFIN](TGFFIN.md) | DTLFINDESP->NUFIN | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimImovel / [TIMIMV](TIMIMV.md) | DTLIMOVEL->IMVCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | DTLCODIPTU->PIPIPTU; DTLIMOVEL->PIPIMOVEL; DTLPARCELAIPTU->PIPCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimLocacao / [TIMLOC](TIMLOC.md) | DTLCONTRATOLOC->LOCCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | DTLCONTRATOLOC->LCRCONTRATO; DTLNEGOCIACAO->LCRCONTADOR | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimTipoDetalhamento / [TIMHCB](TIMHCB.md) | DTLHISTORICO->HCBCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | DTLUSUARIOALT->CODUSU | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | DTLUSUARIO->CODUSU | I |
| TimFinanceiroAluguel / [TGFFIN](TGFFIN.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | NUFIN->DTLPARCELA | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | PIPCODIGO->DTLPARCELAIPTU; PIPIMOVEL->DTLIMOVEL; PIPIPTU->DTLCODIPTU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
