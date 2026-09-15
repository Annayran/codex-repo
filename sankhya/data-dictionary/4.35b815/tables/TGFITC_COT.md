# TGFITC_COT — Itens de Cotação do Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 37
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemCotacaoParceiro | Item de Cotação Portal Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | % ICMS | F |  |  | N |
| ALIQIPI | % IPI | F |  |  | N |
| CABECALHO | CABECALHO | S |  |  | N |
| CODCONTATO | CODCONTATO | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODPARC | CODPARC | I |  |  | N |
| CODPROD | Ref. p/ o comprador | I |  |  | N |
| CODPROPARC | Referência do produto | S |  |  | N |
| CODTIPVENDA | Forma de Pagamento | I |  |  | N |
| CONDPAGT | Condição de pagamento | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DESCRPROPARC | Descrição do produto | S |  |  | N |
| DHENTREGA | Dt. entrega | H |  |  | N |
| DIFERENCIADOR | DIFERENCIADOR | I |  |  | N |
| FRETE | Frete Unitário | F |  |  | N |
| GARANTIA | Garantia | I |  |  | N |
| ICMS | ICMS unitário | F |  |  | N |
| IPI | IPI unitário | F |  |  | N |
| MODFRETE | Modalidade de Frete | S |  |  | N |
| NUMCOTACAO | Nro cotação | I |  |  | N |
| OBS | Observação | S |  |  | N |
| OUTROS | Outros | F |  |  | N |
| PERCACRESC | % Acréscimo | F |  |  | N |
| PERCDESC | % Desconto | F |  |  | N |
| PRAZOENTREGA | Prazo Entrega | I |  |  | N |
| PRAZOMEDIO | Prazo Médio | I |  |  | N |
| PRAZOPARC | Intervalo pagamento | I |  |  | N |
| PRECO | Preço unit. | F |  |  | N |
| QTDCOTADA | Qtd. cotada | F |  |  | N |
| QTDPARCPAGT | Parcelas | I |  |  | N |
| REJEITADO | Cotação Rejeitada? | I |  |  | N |
| STATUSPROD | STATUSPROD | S |  |  | N |
| TAXAJURO | Taxa Juro | F |  |  | N |
| VLRACRESC | Valor do Acréscimo | F |  |  | N |
| VLRDESC | Vlr. desconto | F |  |  | N |
| VLRSUBST | Valor S.T. unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | CabecalhoCotacao / [TGFCOT](TGFCOT.md) | NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | DescontoCotacao / [TGFITD](TGFITD.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
