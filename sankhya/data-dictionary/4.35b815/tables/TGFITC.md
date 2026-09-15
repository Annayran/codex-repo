# TGFITC — Ítens da Cotação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 94
- Instâncias DWF: 2
- Relações DWF: 31
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ColetaItemCotacao | Fornecedores e Coleta de Preços | S | N |
| ItemCotacao | Ítens da Cotação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | % ICMS | F |  |  | N |
| ALIQIPI | % IPI | F |  |  | N |
| CABECALHO | Cabeçalho | S |  |  | N |
| CENTRESULT | Centro de Resultado | I |  |  | S |
| CODCONTATO | Contato | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODMOTCAN | Código Motivo de Cancelamento | I |  |  | N |
| CODNAT | Natureza | I |  |  | S |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPRODESP | Produto específico | I |  |  | N |
| CODPROJ | Projeto | I |  |  | S |
| CODTIPVENDA | Tipo Negociação | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| COMPLDESC | Complemento | S |  |  | S |
| CONDPAGT | Condição de pagamento | S |  |  | N |
| CONFIABFORN | Conf. Fornecedor | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| CUSGER | Custo Gerencial Calc. | F |  |  | N |
| CUSMEDICM | Custo com ICMS Calc. | F |  |  | N |
| CUSREP | Custo Reposição Calc. | F |  |  | N |
| CUSSEMICM | Custo sem ICMS Calc. | F |  |  | N |
| CUSTOFINAL | Custo Final | F |  |  | N |
| CUSVARIAVEL | Custo Variável Calc. | F |  |  | N |
| DHENTREGA | Data Entrega | H |  |  | N |
| DHFINAL | Data final da cotação | H |  |  | S |
| DHINICIAL | Data inicial da cotação | H |  |  | S |
| DIFALIQICMS | Diferença de aliq. ICMS | F |  |  | N |
| DIFERENCIADOR | Diferenciador | I |  |  | N |
| DTCOLETAPRECO | Data Coleta | H |  |  | N |
| DTENVIOCOT | Dt. envio da cotação | D |  |  | N |
| DTLIMITE | Dt. limite | D |  |  | N |
| DTLIMPRECO | Data limite preço | D |  |  | N |
| DTMOEDA | Data Moeda | H |  |  | N |
| EMPRESA | Empresa | I |  |  | S |
| FATMINIMO | Fat. Mínimo | F |  |  | N |
| FORNECAPROVADO | Fornecedor aprovado | I |  |  | S |
| FRETE | Frete Unitário | F |  |  | N |
| GARANTIA | Garantia | I |  |  | N |
| ICMS | ICMS Unitário | F |  |  | N |
| IPI | IPI unitário | F |  |  | N |
| MARCA | Marca | S |  |  | S |
| MELHOR | Melhor Cotação | S |  |  | N |
| MELHORFORNEC | Melhor Fornecedor | I |  |  | S |
| MELHORPRECO | Melhor preço | F |  |  | S |
| MODFRETE | Modalidade de frete | S |  |  | N |
| NOTACPACOT | Nota de compra gerada nesta cotação | I |  |  | S |
| NUMCOTACAO | Número da cotação | I |  |  | N |
| NUMEROOS | Número OS Gub. | I |  |  | N |
| NUNOTACPA | Nro. do Item na Nota do Pedido | I |  |  | N |
| OBS | Observação | S |  |  | N |
| OBSMOTCANC | Observação Motivo de Cancelamento | S |  |  | N |
| OUTROS | Outros | F |  |  | N |
| PERCACRESC | % Acréscimo | F |  |  | N |
| PERCDESC | % Desconto | F |  |  | N |
| PRAZOENTREGA | Prazo Entrega | I |  |  | N |
| PRAZOMEDIO | Prazo Médio | I |  |  | N |
| PRAZOPARC | Intervalo pagamento | I |  |  | N |
| PRAZOVALPROP | Prazo Validade | I |  |  | N |
| PRECO | Preço | F |  |  | N |
| PRECOCALC | Preço Calculado | F |  |  | N |
| PRECOMOE | Preço em moeda | F |  |  | N |
| PRODAGRUPMIN | Agrupamento mínimo | I |  |  | S |
| PRODDECQTD | Decimais para qtd | I |  |  | S |
| PRODDECVLR | Decimais para valor | I |  |  | S |
| QTDCOTADA | Quantidade | F |  |  | N |
| QTDPARCPAGT | Parcelas | I |  |  | N |
| QUALATEND | Qual. Atendimento | I |  |  | N |
| QUALPROD | Qual. Produto | I |  |  | N |
| RESPMINCOT | Respostas mínimas p/ cotação | I |  |  | N |
| RESULTCOT | Resultado | F |  |  | N |
| SEQITEMCOT | Numero item cotacação | I |  |  | N |
| SEQNOTACPA | Sequencia da nota de compra | I |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| STATUSENVIO | Status de envio | S |  |  | S |
| STATUSPRECIFICACAO | Status de precificação | S |  |  | S |
| STATUSPRODCOT | Situação do produto | S |  |  | N |
| TAXAJURO | Taxa Juro | F |  |  | N |
| TIPOCOLPRECO | Coleta de preço | S |  |  | N |
| TOTALPARCEIROS | Quantidade total de fornecedores | I |  |  | S |
| TOTALPRODUTO | Total | F |  |  | S |
| ULTCUSGER | Último Custo Gerencial | F |  |  | S |
| ULTCUSREP | Último Custo Reposição | F |  |  | S |
| ULTCUSVAR | Último Custo Variável | F |  |  | S |
| ULTVLRUNITCOMP | Último Vlr. Unit. Compra | F |  |  | S |
| USURESP | Usuário responsável | I |  |  | S |
| USUSOL | Usuário comprador | I |  |  | S |
| VALPROPOSTA | Validade Proposta | I |  |  | N |
| VLRACRESC | Valor do Acréscimo | F |  |  | N |
| VLRDESC | Valor do Desconto | F |  |  | N |
| VLRDESCMOE | Vlr. Desc. Moeda | F |  |  | N |
| VLRMOEDA | Valor Moeda | I |  |  | N |
| VLRSUBST | Valor S.T. | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | ItemCotacao / [TGFITC](TGFITC.md) | NUMCOTACAO->NUMCOTACAO | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | CentroResultado / [TSICUS](TSICUS.md) | CENTRESULT->CODCENCUS | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Empresa / [TSIEMP](TSIEMP.md) | EMPRESA->CODEMP | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | ItemCotacao / [TGFITC](TGFITC.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | MultiplasDatasEntrega / [TGFMDE](TGFMDE.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | ProdutoEspecifico / [TGFPRO](TGFPRO.md) | CODPRODESP->CODPROD | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Usuario / [TSIUSU](TSIUSU.md) | USUSOL->CODUSU | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| DescontoCotacao / [TGFITD](TGFITD.md) | ItemCotacao / [TGFITC](TGFITC.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacao / [TGFITC](TGFITC.md) | CabecalhoCotacao / [TGFCOT](TGFCOT.md) | NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacao / [TGFITC](TGFITC.md) | CentroResultado / [TSICUS](TSICUS.md) | CENTRESULT->CODCENCUS | I |
| ItemCotacao / [TGFITC](TGFITC.md) | ColetaItemCotacao / [TGFITC](TGFITC.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacao / [TGFITC](TGFITC.md) | DescontoCotacao / [TGFITD](TGFITD.md) | CABECALHO->CABECALHO; CODLOCAL->CODLOCAL; CODPARC->CODPARC; CODPROD->CODPROD; CONTROLE->CONTROLE; DIFERENCIADOR->DIFERENCIADOR; NUMCOTACAO->NUMCOTACAO | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Empresa / [TSIEMP](TSIEMP.md) | EMPRESA->CODEMP | I |
| ItemCotacao / [TGFITC](TGFITC.md) | LocalEstoque / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemCotacao / [TGFITC](TGFITC.md) | MotivoCancCotacao / [TGFMTC](TGFMTC.md) | CODMOTCAN->CODIGO | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItemCotacao / [TGFITC](TGFITC.md) | ParceiroFornecAprovado / [TGFPAR](TGFPAR.md) | FORNECAPROVADO->CODPARC | I |
| ItemCotacao / [TGFITC](TGFITC.md) | ParceiroMelhorFornec / [TGFPAR](TGFPAR.md) | MELHORFORNEC->CODPARC | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Usuario / [TSIUSU](TSIUSU.md) | USUSOL->CODUSU | I |
| ItemCotacao / [TGFITC](TGFITC.md) | UsuarioResponsavel / [TSIUSU](TSIUSU.md) | USURESP->CODUSU | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
