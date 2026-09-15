# TGFITE — Itens de Entrada e Saída de Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 223
- Instâncias DWF: 3
- Relações DWF: 37
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemNota | Item Nota/Pedido | S | N |
| ItemNotaOrigem | Item da Nota de Origem | S | N |
| ItensFox | Item da Nota de Checkout | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQFETHAB | Alíquota FETHAB | F |  |  | N |
| ALIQFUNTTEL | Alíquota FUNTTEL | F |  |  | N |
| ALIQFUST | Alíquota FUST | F |  |  | N |
| ALIQICMS | Alíq. ICMS | F |  |  | N |
| ALIQICMSAT | Alíquota ICMS AT | F |  |  | N |
| ALIQICMSRED | Alíq. ICMS Reduzida | F |  |  | N |
| ALIQINTERICMSAT | Alíquota ICMS Interna AT | F |  |  | N |
| ALIQIPI | Alíq. IPI | F |  |  | N |
| ALIQISS | Alíq. ISS | F |  |  | N |
| ALIQSTEXTRANOTA | Alíquota ST Extra Nota | F |  |  | N |
| ALIQSTFCPSTANT | Alíquota da ST de oper. ant. | F |  |  | N |
| ALTPRECO | Alt. Preço Igual | S |  |  | N |
| ALTURA | Altura | F |  |  | N |
| ATUALESTOQUE | Atualiza estoque | I |  |  | N |
| ATUALESTTERC | Estoque terc. | S |  |  | N |
| BASECALCSTEXTRANOTA | Base de Cálculo ST Extra Nota | F |  |  | N |
| BASEFUNTTEL | Base FUNTTEL | F |  |  | N |
| BASEFUST | Base FUST | F |  |  | N |
| BASEICMS | Base ICMS | F |  |  | N |
| BASEICMSAT | Base de ICMS AT | F |  |  | N |
| BASEICMSSTFRETE | Base do ICMS/ST do Frete | F |  |  | N |
| BASEIPI | Base do IPI | F |  |  | N |
| BASEISS | Base do ISS | F |  |  | N |
| BASESTANT | Base ST ant. | F |  |  | N |
| BASESTEXTRANOTA | Base ST Extra Nota | F |  |  | N |
| BASESTFCPINTANT | Base ST FCP Interno Anterior | F |  |  | N |
| BASESTUFDEST | Base ST UF Destino | F |  |  | N |
| BASESUBSTIT | Base substituição | F |  |  | N |
| BASESUBSTITANT | Base de Cálc. da ST de oper. ant. | F |  |  | N |
| BASESUBSTITUNITORIG | Base ST unitária do pedido de origem | F |  |  | N |
| BASESUBSTSEMRED | Base ST S/Redução | F |  |  | N |
| BASICMMOD | Mod. Base ICMS | I |  |  | N |
| BASICMSTMOD | Mod. Base ICMS ST | I |  |  | N |
| CNPJFABRICANTE | CNPJ do Fabricante da Mercadoria | S |  |  | N |
| CODAGREGACAO | Cód. de Agregação | S |  |  | N |
| CODANTECIPST | Cód.Antecipação ST | S |  |  | N |
| CODBARRAPDV | Cód. de Barras | S |  |  | N |
| CODBENEFNAUF | Cód. de Benefício Fiscal na UF | S |  |  | N |
| CODCAV | Cód. cavalete | I |  |  | N |
| CODCFO | CFOP | I |  |  | N |
| CODCFPS | Cód. CFPS | I |  |  | N |
| CODDOCARRECAD | Cód. Mod. Documento de arrecadação | I |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| CODEND | Endereço WMS | I |  |  | N |
| CODENQIPI | Cód. Enq. Legal IPI | I |  |  | N |
| CODESPECST | Código Especificador ST | I |  |  | N |
| CODEXEC | Executante | I |  |  | N |
| CODFCI | Código da FCI | S |  |  | N |
| CODIGONFCOM | Cod. Item NFCom | S |  |  | N |
| CODINDOPERITEM | Cód. Ind. Oper. Forn. (IndOper) - Serviço | S |  |  | S |
| CODIPI | Código do IPI | I |  |  | N |
| CODLOCALDEST | Local destino | I |  |  | S |
| CODLOCALORIG | Local origem | I |  |  | N |
| CODLOCALTERC | Local terceiros | I |  |  | N |
| CODMOTDESONERAICMS | Cód.Mot.Desoneração ICMS | I |  |  | N |
| CODMOTDESONERAST | Cód. Mot. Desoneração do ICMS ST | I |  |  | N |
| CODNATREND | Código Natureza de Rendimento | I |  |  | N |
| CODOBSPADRAO | Obs Padrão | I |  |  | N |
| CODPARCEXEC | Cód. Parceiro Exec | I |  |  | N |
| CODPROC | Cód. processo | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPROMO | Cód. promoção | I |  |  | N |
| CODSIT08EFD | Considerar a situação do documento '08' nos EFDs | S |  |  | N |
| CODTPA | Tipo Atendimento | I |  |  | N |
| CODTRIB | Tributação | I |  |  | N |
| CODTRIBISS | Cód. trib. ISS | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CODVOLPAD | Unidade padrão | S |  |  | S |
| CODVOLPARC | Cód. Volume Parceiro | S |  |  | N |
| COMPLDESC | Complemento | S |  |  | S |
| CONTROLE | Controle | S |  |  | N |
| CONTROLEDEST | Controle destino | S |  |  | S |
| CSOSN | Cód. situação op. Simples nacional (CSON) | I |  |  | N |
| CSTIPI | C.S.T IPI | I |  |  | N |
| CUSTO | Custo | F |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |
| DTINICIO | Dt.Prev.Entrega | D |  |  | N |
| DTVIGOR | Última Dt. Vigor | D |  |  | S |
| ENDIMAGEM | End. imagem | S |  |  | N |
| ESPESSURA | Espessura | F |  |  | N |
| ESTOQUE | Estoque | F |  |  | S |
| FATURAR | Faturar | S |  |  | N |
| GERAPRODUCAO | Necessidade de Produção | S |  |  | N |
| GRUPOTRANSG | Grupo transg. | I |  |  | N |
| GTINNFE | GTINNFE | S |  |  | N |
| GTINTRIBNFE | GTINTRIBNFE | S |  |  | N |
| ICMSSTFRETE | Vlr. ICMS/ST sobre o frete | F |  |  | N |
| IDALIQICMS | Cód. Alíq. ICMS | I |  |  | N |
| IDALIQICMSAT | Cod. Aliq. ICMS AT | I |  |  | N |
| IDALIQICMSDIFICMS | Cód. Alíq. Dif. ICMS | I |  |  | N |
| IDALIQISS | Código Alíquota ISS | I |  |  | N |
| IDDESCPARCERIA | Id de desconto da parceria | F |  |  | N |
| IMPXML | Impostos Provenientes do Xml | S |  |  | N |
| INDDEVOLUCAONFCOM | Indicador de devolução | S |  |  | N |
| INDESCALA | Indicador de Escala Relevante | S |  |  | N |
| INDREPDES | Indicador Repasse Desoneração | S |  |  | N |
| INDTOT | Não compõe total NF-e | S |  |  | N |
| LARGURA | Largura | F |  |  | N |
| M3 | Metro Cúbico | F |  |  | N |
| MARCA | Marca | S |  |  | S |
| MARGLUCRO | Margem de Lucro | F |  |  | N |
| NCM | NCM | S |  |  | S |
| NPROCSUFRAMA | Número do Processo na Suframa | S |  |  | N |
| NROPROCESSO | Nro do Processo Judicial/Adm (ISS) | S |  |  | S |
| NRSERIERESERVA | Nro série | S |  |  | N |
| NUFOP | Finalidade da Operação | I |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUMDOCARRECAD | Número Documento de arrecadação | S |  |  | N |
| NUMEROOS | Número OS Gub. | I |  |  | N |
| NUMPEDIDO2 | Número Pedido | S |  |  | N |
| NUNOTA | Nro único | I |  |  | N |
| NUPROMOCAO | Nro. Promoção | I |  |  | N |
| NUTAB | Tabela | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| OPERATUAL | Operação Atual da Produção | S |  |  | S |
| ORIGEMBUSCA | Origem da busca | S |  |  | N |
| ORIGPROD | Origem do Produto | S |  |  | N |
| PENDENTE | Pendente | S |  |  | N |
| PERCCOM | % comissão | F |  |  | N |
| PERCCOMGER | % comiss. Gerente | F |  |  | N |
| PERCDESC | % desconto | F |  |  | N |
| PERCDESCBASE | Desc. p/ Preço Base | F |  |  | N |
| PERCDESCBONIF | % desc. bonif. | F |  |  | N |
| PERCDESCDIGITADO | % desc. digitado | F |  |  | N |
| PERCDESCFORNECEDOR | % Desconto Fornecedor | F |  |  | N |
| PERCDESCPROM | % desc. promoção | F |  |  | N |
| PERCDESCTGFDES | % desc. promoção | F |  |  | N |
| PERCGERMIN | % Germinação | F |  |  | N |
| PERCPUREZA | % Pureza | F |  |  | N |
| PERCREDVLRIPI | % Redução de Vlr. IPI | F |  |  | N |
| PERCSTFCPINTANT | % ST FCP Interno Anterior | F |  |  | N |
| PERCUSAQUDECPE | Percentual/Coeficiente sobre Custo Aquisição - PE | F |  |  | N |
| PERCUSAQUDECPEEST | Percentual/Coeficiente sobre Custo Aquisição - PE - Origem Estrangeira | F |  |  | N |
| PESOBRUTO | Peso bruto | F |  |  | S |
| PESOLIQ | Peso líq. | F |  |  | S |
| PRECOBASE | Preço base | F |  |  | N |
| PRECOBASEQTD | Preço base qtd. | F |  |  | N |
| PRODUTONFE | Produto para NF-e | S |  |  | N |
| PRODUTOPESQUISADO | Produto pesquisado | I |  |  | N |
| QTDCONFERIDA | Qtd. corte | F |  |  | N |
| QTDENTREGUE | Qtd. entregue | F |  |  | N |
| QTDFAT | Qtd. faturada | F |  |  | N |
| QTDFIXADA | Qtd. fixada | F |  |  | N |
| QTDFORMULA | Qtd. fórmula | F |  |  | N |
| QTDNEG | Quantidade | F |  |  | N |
| QTDPECA | Qtd. peça | F |  |  | N |
| QTDPENDENTE | Qtd. pendente | F |  |  | S |
| QTDTRIBEXPORT | Qtd. tributação para exportação | F |  |  | N |
| QTDUNIDPAD | Qtd. unid. padrão | F |  |  | S |
| QTDVOL | Base Cálc. Amostragem | I |  |  | N |
| QTDWMS | Qtd. WMS | F |  |  | N |
| REDBASEST | Redução da Base ST | F |  |  | N |
| REFERENCIA | Referência do produto | S |  |  | S |
| REFFORN | Referência do Fornecedor | S |  |  | S |
| RESERVA | Reserva | S |  |  | N |
| RESERVADO | Qtd. Reservada | F |  |  | S |
| SEQFISCALAGRUP | Seq. Fiscal (Agrupado) | I |  |  | N |
| SEQPEDIDO2 | Seq. no Pedido | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SEQUENCIAFISCAL | Sequência Fiscal | I |  |  | N |
| SOLCOMPRA | Solic. compra | S |  |  | N |
| STATUSLOTE | Status do Lote | S |  |  | N |
| STATUSNOTA | Status da Nota | S |  |  | N |
| STATUSPROC | Situação Atual da Produção | S |  |  | S |
| TERCEIROS | Terceiros | S |  |  | N |
| TIPENTREGA | Tipo entrega | S |  |  | N |
| TIPOSEPARACAO | Tipo de separação | S |  |  | N |
| TIPUTILCOM | Tipo de Utilização | I |  |  | N |
| UNIDADE | Unidade do Parceiro | S |  |  | N |
| USOPROD | Uso do Produto | S |  |  | N |
| VARIACAOFCP | Variação da Fórmula | I |  |  | N |
| VITEM_IBSCBS | Valor Total do Item da NF-e | F |  |  | N |
| VLRACRESCDESC | Vlr. acresc./desc. | F |  |  | N |
| VLRCOM | Vlr. comissão | F |  |  | N |
| VLRCOMGER | Comiss. gerente | F |  |  | N |
| VLRCREDPRES | Valor do Crédito Presumido de ICMS no CT-e (vCred) | F |  |  | N |
| VLRCUS | Vlr. custos | F |  |  | N |
| VLRCUSAQUDECPE | Valor Custo Aquisição - PE | F |  |  | N |
| VLRDESC | Vlr. desconto | F |  |  | N |
| VLRDESCBONIF | Bonificação | F |  |  | N |
| VLRDESCDIGITADO | Vlr. desc. digitado | F |  |  | N |
| VLRDESCMOE | Vlr. Desc. Moeda | F |  |  | N |
| VLRDESCPARCERIA | Valor cupom desconto parceria | F |  |  | N |
| VLRFETHAB | Vlr. FETHAB | F |  |  | N |
| VLRFUNTTEL | Valor FUNTTEL | F |  |  | N |
| VLRFUST | Valor FUST | F |  |  | N |
| VLRICMS | Vlr. ICMS | F |  |  | N |
| VLRICMSANT | Vlr ICMS destacado da oper. própria de oper. ant. | F |  |  | N |
| VLRICMSAT | Valor de ICMS AT | F |  |  | N |
| VLRICMSDIFERIDO | Vlr. ICMS Diferido | F |  |  | N |
| VLRICMSUFDEST | Vlr. ICMS UF Destino | F |  |  | N |
| VLRIPI | Vlr. IPI | F |  |  | N |
| VLRISS | Vlr. ISS | F |  |  | N |
| VLRLIQPROM | Vlr. liq. promoção | F |  |  | N |
| VLROUTROITEM | Vlr. Outros | F |  |  | N |
| VLRPROMO | Vlr. Promoção | F |  |  | N |
| VLRPTOPUREZA | Vlr.Ponto Pureza | F |  |  | N |
| VLRREPRED | Vlr. redução | F |  |  | N |
| VLRREPREDSEMDESC | Vlr. redução sem desconto | F |  |  | N |
| VLRREPREDST | Vlr. redução ST | F |  |  | N |
| VLRRETENCAO | Vlr. retenção | F |  |  | N |
| VLRSTEXTRANOTA | Valor ST Extra Nota | F |  |  | N |
| VLRSTFCPINTANT | Vlr. ST FCP Interno Anterior | F |  |  | N |
| VLRSUBST | Vlr. substituição | F |  |  | N |
| VLRSUBSTANT | Vlr. do ICMS da  ST da oper. ant. | F |  |  | N |
| VLRSUBSTUNITORIG | Vlr. ST unitário do pedido de origem | F |  |  | N |
| VLRSUGERIDO | Vlr. Sugerido | F |  |  | S |
| VLRTOT | Vlr. total | F |  |  | N |
| VLRTOTLIQ | Total líq. | F |  |  | S |
| VLRTOTLIQMOE | Total líq. Moeda | F |  |  | S |
| VLRTOTLIQREF | Vlr. Tot.Líq. Desejado | F |  |  | S |
| VLRTOTMOE | Vlr. Tot. Moeda | F |  |  | N |
| VLRTROCA | Vlr. Troca | F |  |  | N |
| VLRUNIDPAD | Vlr. unid. padrão | F |  |  | S |
| VLRUNIT | Vlr. unitário | F |  |  | N |
| VLRUNITDOLAR | Vlr.Unitário em Dólar | F |  |  | N |
| VLRUNITLIQ | Preço líq. | F |  |  | S |
| VLRUNITLIQMOE | Preço Líq. Moeda | F |  |  | S |
| VLRUNITLOC | Vlr. Unit. Locação | F |  |  | N |
| VLRUNITMOE | Vlr. Unit. Moeda | F |  |  | N |
| VLRVENDAPROMO | Vlr. Venda Promoção | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcompanhamentoNota / [TGFACT](TGFACT.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| AnaliseRentabilidade / [TGFARE](TGFARE.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA; VLRDESC->VLRDESC; VLRUNIT->VLRUNIT | I |
| BemNotafiscal / [TCIIBE](TCIIBE.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA | I |
| CompraVendavariosPedido / [TGFVAR](TGFVAR.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| CompraVendavariosPedido / [TGFVAR](TGFVAR.md) | ItemNotaOrigem / [TGFITE](TGFITE.md) | NUNOTAORIG->NUNOTA; SEQUENCIAORIG->SEQUENCIA | I |
| CustoItem / [TGFCUSITE](TGFCUSITE.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemConferenciaPedido / [TGFCOI](TGFCOI.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTAORIG->NUNOTA; SEQUENCIAORIG->SEQUENCIA | I |
| ItemExecucaoServico / [TGFIES](TGFIES.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | AliquotaICMS / [TGFICM](TGFICM.md) | IDALIQICMS->IDALIQ | I |
| ItemNota / [TGFITE](TGFITE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ItemNota / [TGFITE](TGFITE.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| ItemNota / [TGFITE](TGFITE.md) | CustoDevolucao / [TGFCTD](TGFCTD.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | CustoItem / [TGFCUSITE](TGFCUSITE.md) | CODPROD->CODPROD; NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemNota / [TGFITE](TGFITE.md) | EnderecoArmazenagem /  | CODEND->CODEND | I |
| ItemNota / [TGFITE](TGFITE.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |
| ItemNota / [TGFITE](TGFITE.md) | HistoricoItemPendente / [TGFHIP](TGFHIP.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | ImpostoItemNota / [TGFDIN](TGFDIN.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | ImpostosImportacao / [TGFIII](TGFIII.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ItemNota / [TGFITE](TGFITE.md) | LocalFinanceiroEntDest / [TGFLOC](TGFLOC.md) | CODLOCALDEST->CODLOCAL | I |
| ItemNota / [TGFITE](TGFITE.md) | PrevisaoEntrega / [TGFDTP](TGFDTP.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemNota / [TGFITE](TGFITE.md) | SerieProduto / [TGFSER](TGFSER.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNota / [TGFITE](TGFITE.md) | TipoAtendimento / [TGFTPA](TGFTPA.md) | CODTPA->CODTPA | I |
| ItemNota / [TGFITE](TGFITE.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ItemNota / [TGFITE](TGFITE.md) | Vendedor2 / [TGFVEN](TGFVEN.md) | CODEXEC->CODVEND | I |
| ItemNota / [TGFITE](TGFITE.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ItemNotaData / [TGFIDT](TGFIDT.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItensApuracaoReceitaMensal / [TGFIARM](TGFIARM.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| MovimentoEpi / [TGFMEPI](TGFMEPI.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| MultiplosComponentesServicoI / [TGFMCSI](TGFMCSI.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| NumeroAtoConcDrawBack / [TGFDRAW](TGFDRAW.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTAORIG->NUNOTA; SEQUENCIAORIG->SEQUENCIA | I |
| PrevisaoEntrega / [TGFDTP](TGFDTP.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
