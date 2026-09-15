# TIMLOT — TimLoteamento

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 74
- Instâncias DWF: 1
- Relações DWF: 26
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLoteamento | Loteamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LOTABREVIADO | Descr. Abreviada | S |  |  | N |
| LOTAPLICARFRUICAO | Aplicar taxas de fruição | S |  |  | N |
| LOTARQARQUIVO | Arquivo | I |  |  | N |
| LOTARQGAVETA | Gaveta | I |  |  | N |
| LOTARQPASTA | Pasta | S |  |  | N |
| LOTARRAS | ARRAS % | F |  |  | N |
| LOTATRASOJUROS | Juros (mora) % | F |  |  | N |
| LOTATRASOMULTA | Multa % | F |  |  | N |
| LOTAVULSO | Loteamento | S |  |  | N |
| LOTBAIRRO | Bairro | I |  |  | N |
| LOTBASEPRICORR | Primeira Correção | S |  |  | N |
| LOTBASEPRIJUROS | Juros Inicia | S |  |  | N |
| LOTCARENCIA | Dias de Carência p/ contrato | I |  |  | N |
| LOTCARTORIOSUGE | Cartório Sugerido | I |  |  | N |
| LOTCCENTRADA | Conta Bancária p/ Comissão | I |  |  | N |
| LOTCIDADE | Cidade | I |  |  | N |
| LOTCIRCLESIZE | Tamanho do Círculo | I |  |  | N |
| LOTCODIGO | Código | I |  |  | N |
| LOTCONTACOMP | Compartilhada | S |  |  | N |
| LOTCONTACORRENTE | Conta Bancária | I |  |  | N |
| LOTDATABASE | Data base | S |  |  | N |
| LOTDATADECADASTRO | Dt. Cadastro | D |  |  | N |
| LOTDATADECRETO | Dt. Decreto | D |  |  | N |
| LOTDATADELANCAMENTO | Dt. Lançamento | D |  |  | N |
| LOTDECRETO | Decreto | S |  |  | N |
| LOTDESCONTO | Desconto % | F |  |  | N |
| LOTDESCPARCELA | Desconto por Pontualidade % | F |  |  | N |
| LOTDTNAOCOBRAR | Definido Em | H |  |  | N |
| LOTEMPPARCERIA | Parceira | I |  |  | N |
| LOTEMPRESA | Empresa p/ NF | I |  |  | N |
| LOTENTRADA | Total Comissões % | F |  |  | N |
| LOTENTRADACOMPOE | Compõe o preço | S |  |  | N |
| LOTESTAGIO | Estágio | S |  |  | N |
| LOTESTAGIOAPRES | Estágio de Lote após rescisão | S |  |  | N |
| LOTGERARECA | Comissionar por | S |  |  | N |
| LOTGERENTE | Gerente | I |  |  | N |
| LOTIMOBILIARIA | Imobiliária | I |  |  | N |
| LOTINDEXADOR | Indexador | I |  |  | N |
| LOTIPTUNAPARCELA | ITU na parcela | S |  |  | N |
| LOTJUROSDOFINANCIAMENTO | Tx. Juros % | F |  |  | N |
| LOTLOCALIZACAO | Localização | S |  |  | N |
| LOTLOTEAMENTE | Descrição | S |  |  | N |
| LOTMAXIMODEPARCELAS | Máximo de parcelas | I |  |  | N |
| LOTMDCARTORIO | Cartório | I |  |  | N |
| LOTMDDATA | Dt. Registro | D |  |  | N |
| LOTMDFOLHA | Folha | S |  |  | N |
| LOTMDLIVRO | Livro | S |  |  | N |
| LOTMDREGISTRO | Registro | S |  |  | N |
| LOTMINIMODEPARCELAS | Mínimo de parcelas | I |  |  | N |
| LOTMODFINPARC | Mod. Parcela Financiamento | I |  |  | N |
| LOTMODFINRENEGPARC | Mod. Parcela Renegociação | I |  |  | N |
| LOTNAOCOBRAR | Não cobrar | S |  |  | N |
| LOTPERCFRUICAO | Fruição % | F |  |  | N |
| LOTPERIODICIDADE | Periodicidade | I |  |  | N |
| LOTPLANOS | Planos | S |  |  | N |
| LOTPODEESCRITURAR | Pode escriturar | S |  |  | N |
| LOTPQNAOCOBRAR | Motivo | S |  |  | N |
| LOTPREMIO | Prêmio % | F |  |  | N |
| LOTPREVISAODEINFLACAO | Previsão de inflação % | F |  |  | N |
| LOTPROJETO | Projeto | I |  |  | N |
| LOTPROPRIO | Tipo de Contrato | S |  |  | N |
| LOTQUANTIDADEDELOTES | Qtd. lotes | I |  |  | N |
| LOTRECAADM | Comissão Adminis. % | F |  |  | N |
| LOTRECAPARCEIRO | Comissão Franqueada % | F |  |  | N |
| LOTRECPARCELAS | Onde Receber Parc. | S |  |  | N |
| LOTREGIAO | Região | I |  |  | N |
| LOTSEMBOLETO | Bloquear Boleto | S |  |  | N |
| LOTSINAL | Comissão Corretor % | F |  |  | N |
| LOTSOMACOMISSOES | Soma Comissões % | F |  |  | S |
| LOTTABELAPRICE | Price | S |  |  | N |
| LOTTAXAADM | Tx. de Administração % | F |  |  | N |
| LOTTAXALIMITADO | Limitado a % | F |  |  | N |
| LOTTAXAPARTIC | Participação % | F |  |  | N |
| LOTTOTALDAAREA | Total da Área | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | SACLOTEAMENTO->LOTCODIGO | I |
| TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | IEOLOTEAMEN->LOTCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | FILTROLOTEAMENTO->LOTCODIGO | I |
| TimLote / [TIMLTE](TIMLTE.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | LTELOTEAMENTO->LOTCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Bairro / [TSIBAI](TSIBAI.md) | LOTBAIRRO->CODBAI | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Cidade / [TSICID](TSICID.md) | LOTCIDADE->CODCID | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | ContaBancaria / [TSICTA](TSICTA.md) | LOTCONTACORRENTE->CODCTABCOINT | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | ContaDestino / [TSICTA](TSICTA.md) | LOTCCENTRADA->CODCTABCOINT | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Empresa / [TSIEMP](TSIEMP.md) | LOTEMPRESA->CODEMP | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | EmpresaContablidade / [TSIEMP](TSIEMP.md) | LOTEMPPARCERIA->CODEMP | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LOTEMPPARCERIA->CODEMP | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Financeiro / [TGFFIN](TGFFIN.md) | LOTMODFINPARC->NUFIN | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Moeda / [TSIMOE](TSIMOE.md) | LOTINDEXADOR->CODMOEDA | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | LOTPROJETO->CODPROJ | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Regiao / [TSIREG](TSIREG.md) | LOTREGIAO->CODREG | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | LOTCODIGO->IEOLOTEAMEN | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCartorio / [TIMCAR](TIMCAR.md) | LOTMDCARTORIO->CARCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCartorioAverbacao / [TIMCAR](TIMCAR.md) | LOTCARTORIOSUGE->CARCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCorretor / [TIMCOR](TIMCOR.md) | LOTGERENTE->CORCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimFinanceiro / [TGFFIN](TGFFIN.md) | LOTMODFINRENEGPARC->NUFIN | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | LOTIMOBILIARIA->IMBCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | LOTCODIGO->LTPLOTEAMENTO | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | APSLOTEAMENTO->LOTCODIGO | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | TXCLOTEAMENTO->LOTCODIGO | I |
| TimViewCompradorLoteFiltro / [VIMAPR](VIMAPR.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | LTELOTEAMENTO->LOTCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | VDOLOTEAMENTO->LOTCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
