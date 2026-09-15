# TGFTOP — Tipos de Operação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 371
- Instâncias DWF: 32
- Relações DWF: 114
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| grupoTipoOperacao | Grupos Tipo Operação | S | N |
| TipoOperacao | Tipo de Operação | S | N |
| TipoOperacao2 | Tipo de Operação | S | N |
| TipoOperacao3 | Tipo de Operação de (Requisição) | S | N |
| TipoOperacaoAcompCF | Cód.Tipo Operação Nota de Acomp. de CF | N | N |
| TipoOperacaoBaixa | Tipo de Operação para Baixa | S | N |
| TipoOperacaoDenegada | TOP para NF-e Denegada: | S | N |
| TipoOperacaoDenegadaCTe | TOP para CT-e Denegada: | S | N |
| TipoOperacaoDespesa | TOP para despesa | N | N |
| TipoOperacaoDestino | Tipo de Operação Destino | S | N |
| TipoOperacaoDevMerc | TOP p/ devolução de mercadorias | N | N |
| TipoOperacaoEFDEmpresa | Tipo Operação | S | N |
| TipoOperacaoEntrDifPeso | TOP p/ Diferença de Peso a Maior | N | N |
| TipoOperacaoEntrMerc | TOP de entrada de mercadorias | S | N |
| TipoOperacaoGrupoFaturamento | Tipo de Operação do Grupo de Faturamento | N | N |
| TipoOperacaoIpi | Tipo de Operação | S | N |
| TipoOperacaoPedidoAgrupa | TOP pedido agrupado p/ enviar ao WMS | S | N |
| TipoOperacaoPerda | TipoOperacaoPerda | S | N |
| TipoOperacaoRetorno | Tipo de operação pendente de retorno. | S | N |
| TipoOperacaoSaidaDifPeso | TOP p/ Diferença de Peso a Menor | N | N |
| TipoOperacaoSangria | TOP para sangria / suprimento | N | N |
| TipoOperacaoSeparacao | Tipo de Operação para Separação | N | N |
| TopAtendimento | Top de Antedimento | S | N |
| TopBackorder | Top de Back order | S | N |
| TOPDespesa | TOPDespesa | S | N |
| TopEntrada | Top de Entrada | S | N |
| TopPrincipal | Tipo Operação Principal | S | N |
| TopProducao | Top de Produção | S | N |
| TOPReceita | TOPReceita | S | N |
| TopRemessa | Top para Remessa | N | N |
| TopSaida | Top de saída | S | N |
| TopSeparacao | Tipo Operação Separacao | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITAFATACIMA | Aceitar faturar quantidade maior que a da origem? | S |  |  | N |
| ADIARATUALEST | Atualizar Estoq.a partir da Confirmação | S |  |  | N |
| AGRUPAPRODNFE | Agrupar produtos semelhantes na NF-e? | S |  |  | N |
| AGRUPASERVFAT | Agrupa serviços faturamento | S |  |  | N |
| AJUSTAVP | Ajusta Valor Presente | S |  |  | N |
| ALTITEMPARCFAT | Permite alterar itens após faturar parcialmente | S |  |  | N |
| ALTNFCONF | Permitir Alteração após confirmar | S |  |  | N |
| ANALISEGIRO | Análise de Giro | I |  |  | N |
| APLICLEITRANSP | Aplicar a lei da transparência | S |  |  | N |
| APLICTABIRFINSS | Aplicar tabela de IRPF/INSS | S |  |  | N |
| ARMAZENAGEM | Armazenagem | S |  |  | N |
| ARMTIPAPU | Tipo de Movimento Armazenagem | S |  |  | N |
| ARREDQTDUNALT | Arredonda Qtd. unid. alternativa? | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| ATUALACDC | Atualizar Acréscimos/Decréscimos | S |  |  | N |
| ATUALBEM | Atualização do Bem | S |  |  | N |
| ATUALCOM | Comissão | S |  |  | N |
| ATUALCOMOS | Calcular comissão para O.S. | S |  |  | N |
| ATUALCTB | Atualiza Contabilidade | S |  |  | N |
| ATUALDATRECWMS | Atualização Data Rec. no WMS | S |  |  | N |
| ATUALEST | Atualização do Estoque | S |  |  | N |
| ATUALESTMP | Atualiza estoque MP | I |  |  | N |
| ATUALESTTERC | Estoque com/de Terceiros | S |  |  | N |
| ATUALESTWMSTERC | Atualiza Estoque de Terceiros WMS? | S |  |  | N |
| ATUALFIN | Financeiro | I |  |  | N |
| ATUALFINTERC | Soma itens com/de Terceiros ao Financeiro | S |  |  | N |
| ATUALINDENIZ | Saldo de Indenização | I |  |  | N |
| ATUALIZARATEIO | Atualiza Rateio | S |  |  | N |
| ATUALLIVFIS | Atualização de Livro ICMS | S |  |  | N |
| ATUALLIVISS | Atualização livros de ISS | S |  |  | N |
| ATUALPRECOFAT | Recalcular preço prod. ao faturar | S |  |  | N |
| ATUALTRANSG | Saldo de Transgênico | I |  |  | N |
| ATUALULTIMACOMP | Atualizar Última Compra | S |  |  | N |
| ATUALULTIMAVEND | Atualizar Última Venda | S |  |  | N |
| ATUSALDOCONT | Atualização do saldo dos contratos | S |  |  | N |
| AUTDIGITAL | Autorização por Digital | S |  |  | N |
| AVISARCOMP | Avisar sobre componentes | S |  |  | N |
| BASEICMSFRETECALCST | Usar base ICMS Frete Extra Nota p/calc.ST | S |  |  | N |
| BASENUMERACAO | Base numeração | S |  |  | N |
| BLOQESTVENC | Bloqueia saída de estoque vencido | S |  |  | N |
| BONIFICACAO | Bonificação | S |  |  | N |
| BUSCMPTERC | Apoio p/Devolução de Produtos de Terceiros | S |  |  | N |
| CALCDIFALPART | Calcular DIFAL Partilhado | S |  |  | N |
| CALCDIFICMS | Calcular diferença de ICMS | S |  |  | N |
| CALCFCPINT | Calcular FCP (ICMS/ST) Interno? | S |  |  | N |
| CALCFETHAB | Calcular FETHAB | S |  |  | N |
| CALCFUNTTELTOP | Calcular FUNTTEL? | S |  |  | N |
| CALCFUSTTOP | Calcular FUST? | S |  |  | N |
| CALCICMS | Cálculo de ICMS, IPI, ISS, IBS, CBS e IS | S |  |  | N |
| CALCICMSREGTTS | Calcular ICMS Regime TTS E-Commerce Vinculado? | S |  |  | N |
| CALCPESOCONFIRM | Calcular Peso na Confirmação? | S |  |  | N |
| CALCPISCFSFIN | Calcula PIS/COFINS por percentual das notas | S |  |  | N |
| CALCSTFRTXTNOTPROP | Calc. ICMS/ST extra nota p/ frete extra nota proporcional aos itens | S |  |  | N |
| CAMGEREDICONF | Caminho para geração de EDI na confirmação | S |  |  | N |
| CAT1799SPCCO | Cód.Complem.Oper. (CAT-17/99) | I |  |  | N |
| CLASSIFICMS | Classificação ICMS | S |  |  | N |
| CODCFO_COMBUST_LUBRIF | CFOP p/Combustíveis e Lubrificantes | I |  |  | N |
| CODCFO_ENTRADA | Entrada | I |  |  | N |
| CODCFO_ENTRADA_FORA | Entrada | I |  |  | N |
| CODCFO_SAIDA | Saída | I |  |  | N |
| CODCFO_SAIDA_FORA | Saída | I |  |  | N |
| CODCFO_TERC | CFOP p/Produtos de Terceiros | I |  |  | N |
| CODCFPS | Código. CFPS | I |  |  | N |
| CODCONTARURAL | Classificação da conta Produtor Rural | S |  |  | N |
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODENQIPIENT | Código Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Código Enq. Legal IPI Saída | I |  |  | N |
| CODINDOPER | Código Indicador de Operação de Fornecimento | S |  |  | N |
| CODINTERM | Intermediador da Transação | I |  |  | N |
| CODLOCALIMPXML | Local padrão para importar XML | I |  |  | N |
| CODMODCFECANC | Modelo de impressão de cancelamento CF-e | I |  |  | N |
| CODMODDAD | Modelo para Dados Adicionais de NF-e | I |  |  | N |
| CODMODDOC | Modelo do Documento | I |  |  | N |
| CODMODDOCISS | Modelo Documento ISS | S |  |  | N |
| CODMODNF | Modelo de impressão de nota fiscal | I |  |  | N |
| CODMODNFSE | Mod. Imp. NFS-e | I |  |  | N |
| CODMODRO | Modelo de impressão de romaneio | I |  |  | N |
| CODMODRPS | Mod. Imp. RPS | I |  |  | N |
| CODMOEDAVP | Cód. Moeda Ajusta Valor Presente | I |  |  | N |
| CODNATOPERISS | Cód. Natureza Oper. ISS (NFS-e) | S |  |  | N |
| CODREMEDI | Modelo p/geração de EDI na confirmação | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODTIPOPERDENEG | Tipo Operação NF-e Denegada | I |  |  | N |
| CODTIPOPERDESTINO | Cód.Tipo Operação Faturamento | I |  |  | N |
| CODTIPOPERPENRET | Tipo Operação NF-e Pendente de Retorno | I |  |  | N |
| CODTIPOPERREM | Tipo Operação Remessa | I |  |  | N |
| CODTIPOPERSEPARACAO | Tipo Operação Separação | I |  |  | N |
| CODTOPDENEGCTE | Tipo Operação CT-e Denegado | I |  |  | N |
| CODTRIB | Tributação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COFINSPROPDESTAQUE | COFINS proporcional para Destaque | S |  |  | N |
| COFINSPROPEMBALAGEM | COFINS proporcional para Embalagem | S |  |  | N |
| COFINSPROPFRETE | COFINS proporcional para Frete | S |  |  | N |
| COFINSPROPJURO | COFINS proporcional para Juro | S |  |  | N |
| COFINSPROPSEG | COFINS proporcional para Seguro | S |  |  | N |
| COFINSSTPROPDESTAQUE | COFINS ST Prop. Destaque | S |  |  | N |
| COFINSSTPROPEMBALAGEM | COFINS ST Prop. Embalagem | S |  |  | N |
| COFINSSTPROPFRETE | COFINS ST Prop. Frete | S |  |  | N |
| COFINSSTPROPJURO | COFINS ST Prop. Juro | S |  |  | N |
| COFINSSTPROPSEG | COFINS ST Prop. Seguro | S |  |  | N |
| COMPLEMENTO | Tipo de Emissão | S |  |  | N |
| CONFCFOP | Exige Conferência de CFOP | S |  |  | N |
| CONFIMPOSTO | Exige Conferência de Impostos | S |  |  | N |
| CONFVALEVENT68 | Validar diferença para | I |  |  | N |
| CONFVLREVENT68 | Valor para validar o evento 68 | S |  |  | N |
| CONSAUXILIAR | Consultas Auxiliares | S |  |  | N |
| CONSDISBAIXEST | Considerar estoque real na baixa de estoque | S |  |  | N |
| CONSIGNACAO | Consignação | S |  |  | N |
| CONSNFERELCANCEFD | Considerar NFe relacionada como cancelada no EFD Contribuições | S |  |  | N |
| CONSTOPAPURSIMP | Considerar na Apuração do Simples? | S |  |  | N |
| CONSVLRREMRETIND | Considerar o valor da remessa, no retorno de industrialização como | I |  |  | N |
| CONTLAUDOSINT | Participa da contagem de laudos internos | S |  |  | N |
| COPIADTPREVORIG | Copiar prev. entrega da origem | S |  |  | N |
| COPIARLIBER | Copiar liberações quando faturando individualmente | S |  |  | N |
| CSTIPIENT | Código Sit.Trib.IPI Entrada | I |  |  | N |
| CSTIPISAI | Código Sit.Trib.IPI Saída | I |  |  | N |
| CTE | CT-e | S |  |  | N |
| CUPOMFISCAL | TOP de Cupom Fiscal | S |  |  | N |
| DATARETROFAT | Permitir Data Retroativa no Faturamento | S |  |  | N |
| DEDFCPBCPISCOFINS | Deduzir valor de FCP na BC do PIS e COFINS? | S |  |  | N |
| DENTROESTADO | Top trabalha com CFOP para | S |  |  | N |
| DESCCSTBCPISCF | Desconsiderar CST’s de ICMS da BC do PIS/COFINS? | S |  |  | N |
| DESCONNFSE | Desconto Condicionado para NFS-e | S |  |  | N |
| DESCONSIDNFEORIGEM | Desconsidera Nfe de orig. referenciada? (Importação de XML) | S |  |  | N |
| DESCONSIDPEDXML | Desconsiderar pedido na geração do XML? | S |  |  | N |
| DESCONSLCDPR | Desconsiderar Top na Geração do Livro Caixa Digital do Produtor Rural | S |  |  | N |
| DESCQTDGRUDESCPRO | Aplica Desc. Promocional por Qtd/Grupo de Desc. Prod? | S |  |  | N |
| DESCREMAIL | Descrição do e-mail | S |  |  | N |
| DESCROPER | Descrição | S |  |  | N |
| DESTOPADRCST1400 | Desconsiderar TOP na geração da ADRC-ST no R1400? | S |  |  | N |
| DEVSEMDESTAQUEIPI | Devolver sem destacar IPI | S |  |  | N |
| DEVSEMDESTAQUEST | Devolver sem destacar ST | S |  |  | N |
| DHALTER | Data e hora alteração | H |  |  | N |
| DIGINFIMPORTA | Digitar informações sobre Importação | S |  |  | N |
| DIGPUREZA | Digitar % Pureza e % Germinação | S |  |  | N |
| DISTSTVLRUNITFAT | Distribuir valor do ST no preço unitário ao faturar | S |  |  | N |
| EDITANALISERENTAB | Permite edição de análise de rentabilidade | S |  |  | N |
| EMITEBOLETA | Imprimir Pix/Boleto/Duplicata | S |  |  | N |
| EMITENOTA | Imprimir | S |  |  | N |
| EMPFUNCDIF | Permitir Req. Func. de outra Empresa | S |  |  | N |
| ENVGARANTIA | Envio em Garantia? | S |  |  | N |
| ENVIARWMSCONF | Enviar automaticamente para o WMS na Confirmação | S |  |  | N |
| ENVWMSCONFIRMADA | Permite confirmação antes do envio para o WMS? | S |  |  | N |
| ESTOQUEMPTERCEIRO | Estoque MP de Terceiros | S |  |  | N |
| EXECITE | Exige executante nos itens | S |  |  | N |
| EXIGANALITENS | Exigir liberação com análise dos itens antes da confirmação | S |  |  | N |
| EXIGEAGENDAWMS | Exige agendamento p/Carga e Descarga no WMS | S |  |  | N |
| EXIGEANALISECRED | Exige análise de crédito | S |  |  | N |
| EXIGECONF | Exige conferência | S |  |  | N |
| EXIGECONFIRMACAOMDE | Exigir confirmação do MD-e antes da confirmação | S |  |  | N |
| EXIGECOTACAO | Exige Cotação | S |  |  | N |
| EXIGEDTVAL | Exige data de validade | S |  |  | N |
| EXIGEGAR | Exige garantia | S |  |  | N |
| EXIGELAUDO | Exige Laudo de análise | S |  |  | N |
| EXIGELIB | Exigir Liberação antes da Confirmação | S |  |  | N |
| EXIGELIBSEMPRE | Sempre exigir Liberação | S |  |  | N |
| EXIGEPEDFRET | Exige Pedido de Frete p/Frete Extra Nota | S |  |  | N |
| EXIGETRANSP | Exige Transportadora | S |  |  | N |
| EXPGRS | Exportação para GRS | I |  |  | N |
| EXPORTA | Exporta Replicação | S |  |  | N |
| EXPTES | Exportação para tesouraria | S |  |  | N |
| FATCONTPORPESO | Faturar contrato por peso | S |  |  | N |
| FATENTPROD | Na Entrada de Produtos | S |  |  | N |
| FATESTCONF | Fatura Produtos com Estoque na Confirmação | S |  |  | N |
| FATFORAPLANENT | Permitir faturamento fora do planejamento de entrega | S |  |  | N |
| FORMRECISS | Forma de Recolhimento ISS | S |  |  | N |
| GERAAMOSTRALAUDO | Gerar amostras para laudo | S |  |  | N |
| GERABONPRE | Gera Bonificação/Premiação | S |  |  | N |
| GERADEMANDAMPS | Gerar demanda para MPS | S |  |  | N |
| GERAENDENTRNFE | Gerar endereço de entrega no XML da NF-e | S |  |  | N |
| GERAGNRE | Gerar GNRE? | S |  |  | N |
| GERAPLANPROD | Gerar plano de produção | S |  |  | N |
| GERAR1400SPED | Gerar Registro 1400 no SPED Fiscal ? | S |  |  | N |
| GERARPARCDEST | Gerar se parc.destinatário preenchido | S |  |  | N |
| GERARTAGJNFE | Gerar tag na NFe para Negociação de Veículos Novos | S |  |  | N |
| GERC100CODCIT08 | Gerar C100 com Cód. Situação 08 (EFD ICMS/IPI) | S |  |  | N |
| GERCORAPON | Gerar Correção de Apontamento (K280) | S |  |  | N |
| GERINFOEFDPAG | Gerar informações do EFD Reinf Grupo 4000? | S |  |  | N |
| GOLDEV | Identificador de Devolução | I |  |  | N |
| GOLMPSINAL | Tipo para análise de MP | I |  |  | N |
| GOLSINAL | Tipo para análise | I |  |  | N |
| GRAHISALTPED | Gravar histórico de alterações do pedido | S |  |  | N |
| GRUPO | Grupo | S |  |  | N |
| ICMSORIGESTPED | Calcular ICMS de Origem Estrangeira | S |  |  | N |
| ICMSPROPDESTAQUE | ICMS proporcional para Destaque | S |  |  | N |
| ICMSPROPEMBALAGEM | ICMS proporcional para Embalagem | S |  |  | N |
| ICMSPROPFRETE | ICMS proporcional para Frete | S |  |  | N |
| ICMSPROPJURO | ICMS proporcional para Juro | S |  |  | N |
| ICMSPROPSEG | ICMS proporcional para Seguro | S |  |  | N |
| IGNEXPAUTLOT | Ignorar explosão automática de lotes nesta TOP | S |  |  | N |
| IGNOBSORIGREM | Ignorar Observação da Nota de Origem | S |  |  | N |
| IGNORACOMPLITEM | Ignorar complemento dos itens nas informações adicionais dos produtos? | S |  |  | N |
| IGNORAMPPVPA | Ignora MP no preço de venda do PA | S |  |  | N |
| IGNORARAGRUPMINDEV | Ignorar agrupamento mínimo de compra | S |  |  | N |
| IMPNAOCONF | Imprimir nota adicional antes de confirmada | S |  |  | N |
| IMPNOTAADICIONAL | Imprimir Nota Adicional | S |  |  | N |
| IMPXMLMANTDESPACES | Importar o XML mantendo as despesas acessórias com os valores do XML | S |  |  | N |
| INDCONSFINAL | Operação de Uso/Consumo Próprio | S |  |  | N |
| INDLOCBENSMOVEIS | Operação de Locação de Bens Móveis | S |  |  | N |
| INDNATFRT | Indicador da Natureza do Frete Contratado | S |  |  | N |
| INDPRESNFCE | Indicador de Presença para NF-e/NFC-e/CF-e | S |  |  | N |
| INDTERC | Industrialização efetuada por terceiros | S |  |  | N |
| INDTIPREC | Indicador do Tipo de Receita | S |  |  | N |
| INFCONTRATO | Informar Contrato na Nota | S |  |  | N |
| INTEGRAEVENTO | Integra evento | S |  |  | N |
| INTEGSERCON | Integ. Série ao Contrato | S |  |  | N |
| INTERMED | Indicador de Intermediador/Marketplace | S |  |  | N |
| IPIEMBUT | IPI Embutido | S |  |  | N |
| IPIINCICMS | IPI Incide na base de ICMS | S |  |  | N |
| IPIPROPDESTAQUE | IPI proporcional para Destaque | S |  |  | N |
| IPIPROPEMBALAGEM | IPI proporcional para Embalagem | S |  |  | N |
| IPIPROPFRETE | IPI proporcional para Frete | S |  |  | N |
| IPIPROPJURO | IPI proporcional para Juro | S |  |  | N |
| IPIPROPSEG | IPI proporcional para Seguro | S |  |  | N |
| LAUDOITEM | Laudo por item | S |  |  | N |
| LIGORIGORIG | Ligar com a Origem da Origem com quantidade negativa? | S |  |  | N |
| MARCARNAOPENDENTE | Deixar pedido como NÃO pendente quando faturar com corte | S |  |  | N |
| MOVENDFLUTUANTE | Movimentação de Endereços Flutuantes? | S |  |  | N |
| MPNUMAUTLOTE | Numerar automaticamente MP por Lote | S |  |  | N |
| NADCALCTOTREF | Impedir adiantamento do cálculo dos totalizadores de IBS/CBS? | S |  |  | N |
| NAOINCCONF | Nunca incluir Confirmada | S |  |  | N |
| NATBCCRED | Natureza da Base de Cálculo do Crédito de Pis/Cofins | S |  |  | N |
| NATEFDCONTM410M810 | Cód. Natureza (PIS/COFINS M410/M810) | I |  |  | N |
| NATOPERSPED | Natureza da Operação (SPED) | S |  |  | N |
| NFCOM | NFCom | S |  |  | N |
| NFE | NF-e | S |  |  | N |
| NFEESTORNO | NF-e de estorno | S |  |  | N |
| NFESEMDTENTSAI | Imprimir NF-e sem Data de Entrada/Saída | S |  |  | N |
| NFSE | NFS-e | S |  |  | N |
| NFSEPORNAT | NFS-e por natureza | S |  |  | N |
| NFSETIPOPER | Tipo de Operação | S |  |  | N |
| NUCCO | Configuração p/ conferência | I |  |  | N |
| NUFOP | Finalidade da Operação | I |  |  | N |
| NULAYOUT | Nro. Layout | I |  |  | N |
| NULAYOUTCVR | Nro. Layout CVR | I |  |  | N |
| NUMPROCESSO | Número do processo | S |  |  | N |
| NUMSOMAUT | Numeração somente automática | S |  |  | N |
| NUNOTAMODELO | Nro único modelo | I |  |  | N |
| OBTERVLRMOEDAFAT | Obter valor da moeda ao faturar | S |  |  | N |
| OC | Tipo de Ordem de Carga | S |  |  | N |
| OPERACAOAMOSTRA | TOP de Amostra Grátis | S |  |  | N |
| OPERCOMMOEDA | Operação em Moeda | S |  |  | N |
| OPERESSACOMP | Operação com Ressarcimento/Complemento de ST | S |  |  | N |
| ORCAMENTO | Orçamento | S |  |  | N |
| OUTDESPSTEXTNOTA | Frete/Seguro/Outras Despesas para ST extra nota | S |  |  | N |
| PEDFRETE | Pedido de Frete | S |  |  | N |
| PENDENTE | A nota fica como pendente | S |  |  | N |
| PERCMINBASEINSS | % Mín. da Base de INSS sobre o Total da Nota | F |  |  | N |
| PERCTOLVARVLRUNIT | % Tolerância na variação do Vlr. unitário | F |  |  | N |
| PERMCONFPARCIALWMS | Permite conferência parcial no WMS? | S |  |  | N |
| PERMDESTVBATPREBCUS | Permite destinação de Verba do tipo de Rebaixa de Custo | S |  |  | N |
| PERMFINMENORVLRNOTA | Permite financeiro menor que o valor total da nota | S |  |  | N |
| PERMITECNAEDIFNOTA | Possibilita emissão de NFS-e com múltiplos CNAEs | S |  |  | N |
| PERMTRANSGALPAO | Permite transferência entre galpões | S |  |  | N |
| PESAITEM | Pesagem por item | S |  |  | N |
| PISPROPDESTAQUE | PIS proporcional para Destaque | S |  |  | N |
| PISPROPEMBALAGEM | PIS proporcional para Embalagem | S |  |  | N |
| PISPROPFRETE | PIS proporcional para Frete | S |  |  | N |
| PISPROPJURO | PIS proporcional para Juro | S |  |  | N |
| PISPROPSEG | PIS proporcional para Seguro | S |  |  | N |
| PISSTPROPDESTAQUE | PIS ST Prop. Destaque | S |  |  | N |
| PISSTPROPEMBALAGEM | PIS ST Prop. Embalagem | S |  |  | N |
| PISSTPROPFRETE | PIS ST Prop. Frete | S |  |  | N |
| PISSTPROPJURO | PIS ST Prop. Juro | S |  |  | N |
| PISSTPROPSEG | PIS ST Prop. Seguro | S |  |  | N |
| PODEAJUSTARORIGPRODWMS | Permite Ajustar Nota Origem quando ligada a Produção | S |  |  | N |
| PODEFIXAR | Pode Fixar Preço | S |  |  | N |
| PODEPESAGEM | Tipo de Pesagem | S |  |  | N |
| PODETRANSFENT | Pode Transformar Entradas | S |  |  | N |
| PRECIFICA | Precifica | S |  |  | N |
| PRODREP | Aceitar Produto Repetido | S |  |  | N |
| PRODUETLOC | Produção por Etapa e Local? | S |  |  | N |
| PROVISENTREGA | Exige previsão de entregas | S |  |  | N |
| RATAUTPROD | Ratear automaticamente por produto | S |  |  | N |
| RECBRUTACIAP | Receita Bruta p/ CIAP | S |  |  | N |
| REDICMSBCPISCONFINS | Deduzir valor do ICMS na BC do PIS e COFINS? | S |  |  | N |
| REDPISBCPISCOFINS | Deduzir valor do PIS/COFINS da BC do PIS e COFINS? | S |  |  | N |
| REDSTBCPISCOFINS | Deduzir valor do ICMS/ST na BC do PIS e COFINS? | S |  |  | N |
| REFNFE | Buscar NF de origem p/ referenciar na NFe | S |  |  | N |
| RESERVASEMLOTE | Permitir reserva de estoque sem lote? | S |  |  | N |
| SALVARCONFSEMPERG | Salvar doc. confirmado sem perguntar | S |  |  | N |
| SEMCREDIPIST | Operação sem direito a crédito IPI/ST | S |  |  | N |
| SEMMOEDAFIN | Não preencher moeda para Financeiro | S |  |  | N |
| SEPBALCAO | Separação em Balcão | S |  |  | N |
| SIMULACAUTOFRETE | Simulação de frete automática | S |  |  | N |
| SOLCOMPRA | Gera Solicitação de Compra na Confirmação | S |  |  | N |
| SOMARCOFINSST | Somar COFINS ST ao Total da Nota | S |  |  | N |
| SOMARIPI | Somar IPI ao total da nota | S |  |  | N |
| SOMARPISST | Somar PIS ST ao Total da Nota | S |  |  | N |
| SOMASUBST | Soma ST no total da nota | S |  |  | N |
| STATUSBAIXAEST | Status para Baixa no estoque | S |  |  | N |
| STATUSLOTE | Exige Status para o Lote na Compra | S |  |  | N |
| STPROPDESTAQUE | S.T.proporcional para Destaque | S |  |  | N |
| STPROPEMBALAGEM | S.T.proporcional para Embalagem | S |  |  | N |
| STPROPFRETE | S.T.proporcional para Frete | S |  |  | N |
| STPROPFRETEEXT | para Frete Extra | S |  |  | N |
| STPROPJURO | S.T.proporcional para Juro | S |  |  | N |
| STPROPSEG | S.T.proporcional para Seguro | S |  |  | N |
| SUGERELOCALPARC | Sugerir local padrão do parceiro? | S |  |  | N |
| TEMCBS | Tem CBS | S |  |  | N |
| TEMCOFINS | Tem COFINS | S |  |  | N |
| TEMCSL | Tem CSLL | S |  |  | N |
| TEMFINORIGEM | Permite Atualização de Financeiro na TOP de Origem | S |  |  | N |
| TEMFUNRURAL | Tem FUNRURAL/INSS | S |  |  | N |
| TEMIBS | Tem IBS | S |  |  | N |
| TEMIBSCBSMONO | Tem IBS/CBS Monofásico | S |  |  | N |
| TEMICMS | Tem ICMS | S |  |  | N |
| TEMII | Tem II | S |  |  | N |
| TEMINDEX | Utiliza Indexação | S |  |  | N |
| TEMIPI | Tem IPI | S |  |  | N |
| TEMIRF | Tem IRF | S |  |  | N |
| TEMIS | Tem IS | S |  |  | N |
| TEMISS | Tem ISS | S |  |  | N |
| TEMPIS | Tem PIS | S |  |  | N |
| TEMREAICMS | Tem Convênio REA/ICMS | S |  |  | N |
| TEMVAVTCON | Tem VA e/ou VT no contrato | S |  |  | N |
| TIPALTDTVENC | Tipo de Alteração da Data de Vencimento | S |  |  | N |
| TIPATUALFIN | Atualização do financeiro | S |  |  | N |
| TIPATUALWMS | Atualização no WMS | S |  |  | N |
| TIPCOMPLCUST | Tipo complemento custo | I |  |  | N |
| TIPFATSERV | Faturamento relacionado a Ordem de Serviço | S |  |  | N |
| TIPICMS | Tipo de ICMS | S |  |  | N |
| TIPIPI | Tipo de IPI | S |  |  | N |
| TIPIVASUBST | Tipo de Calculo IVA ponderado | S |  |  | N |
| TIPMODALCTE | Modal CT-e | S |  |  | N |
| TIPMOV | Tipo de movimento | S |  |  | N |
| TIPMOVBEMSPED | Tipo de Mov. do  Bem (SPED) | S |  |  | N |
| TIPOCTE | Tipo de emissão CT-e | S |  |  | N |
| TIPOEMISSAONFCOM | Tipo de Emissão | S |  |  | N |
| TIPOIMPKIT | Kit/Componentes - Impressão e Livro Fiscal | S |  |  | N |
| TIPONUMERACAO | Tipo de Numeração | S |  |  | N |
| TIPSERVCTE | Tipo de serviço CT-e | I |  |  | N |
| TOPATENDIMENTO | Top de Atendimento | I |  |  | N |
| TOPBACKORDER | Top Back Order | I |  |  | N |
| TOPPISCOFREDAQUIS | Calcula PIS/COFINS red. pela aquisição | S |  |  | N |
| TPNFCRED | Tipo de Nota Fiscal de Crédito | S |  |  | N |
| TPNFDEB | Tipo de Nota Fiscal de Débito | S |  |  | N |
| TPOPERGOV | Tipo de operação com o ente governamental | S |  |  | N |
| TPOPERGOVCTE | Tipo de operação com o ente governamental do CT-e | S |  |  | N |
| TPOPERGOVNFCOM | Tipo de operação com o ente governamental | S |  |  | N |
| TRANSFCBGLOBAL | Número de Série Global - Transferência | S |  |  | N |
| TRANSFWMS | Transferência no WMS | S |  |  | N |
| TRAVAFIMIMP | Travar até terminar a impressão | S |  |  | N |
| USAALIQNATRATF100 | Usar Alíq.da Nat. do Rateio p/registro F100 do SPED PIS/COFINS ? | S |  |  | N |
| USACUSMEDBASEST | Usa Cus. Méd. Base ST? | S |  |  | N |
| USARCONFCEGA | Usa conferência Cega | S |  |  | N |
| USARECPARCIAL | Utiliza recebimento parcial? | S |  |  | N |
| USARPRECOCUSTO | Usar como Preço | S |  |  | N |
| USASERVTABIRFINSS | Usar serviço p/ cálculo com tabela de IRPF/INSS | S |  |  | N |
| USATABALTEMP | Usar tabela de preço alternativa da empresa? | S |  |  | N |
| USAVENDAMAIS | Utilizar no Sankhya Venda Mais | S |  |  | N |
| USOPRODSEPARACAO | Uso da Separação | S |  |  | N |
| UTILIZAWMS | Endereçamento no WMS | S |  |  | N |
| VALDISPESTDEV | Valida disponibilidade de estoque na devolução? | S |  |  | N |
| VALEST | Validar Estoque p/ Reservar | S |  |  | N |
| VALESTMAXIMO | Valida Estoque Máximo | S |  |  | N |
| VALIDAAGRUPMIN | Valida agrupamento mínimo | S |  |  | N |
| VALIDAATRASO | Atraso | S |  |  | N |
| VALIDADATA | Reiniciar numeração por data | S |  |  | N |
| VALIDAMEDIANEGOC | Validar Média de Negociações Mensais | S |  |  | N |
| VALNUM | Valida Numeração por | S |  |  | N |
| VALSITCADRF | Valida situação cadastro na ReceitaWS ? | S |  |  | N |
| VALSITCADSEFAZ | Valida situação cadastro na SEFAZ ? | S |  |  | N |
| VALTBCOMPCR | Validar Tabela de compra por CR? | S |  |  | N |
| VALTOTNOTAGERLIV | Validar Totais da Nota na Geração do Livro? | S |  |  | N |
| VALVARIACVLRUNIT | Validar variações do vlr. unit. orig./dest. | S |  |  | N |
| VALVCTLAUDOEST | Valida laudo vencido na baixa de estoque | S |  |  | N |
| VENDITE | Exige vendedor nos itens | S |  |  | N |
| VLRBASEPGTO | Valor Base Pagamento | F |  |  | N |
| VLRLIQITEMNFE | Considera valor líquido do item na NF-e ou NFC-e ou CF-e(geração do XML e imp.Danfe) | S |  |  | N |
| VLRMINAP | Valor mínimo p/Autorização de Pagamento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaImposto / [TGFIMA](TGFIMA.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODIGO->CODTIPOPER | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| BaseStMediaVendaTop / [TGFSTMVT](TGFSTMVT.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoNota3 / [TGFCAB](TGFCAB.md) | TipoOperacao3 / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoNotaExcluida / [TGFCAB_EXC](TGFCAB_EXC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXA->CODTIPOPER | I |
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| ContaBancaria / [TSICTA](TSICTA.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXABOLRAP->CODTIPOPER | I |
| Contrato / [TCSCON](TCSCON.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| ContratoCivil / [TCCCON](TCCCON.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoAcompCF / [TGFTOP](TGFTOP.md) | CODTIPOPERACOMP->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoDespesa / [TGFTOP](TGFTOP.md) | TOPDESPESA->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoDevMerc / [TGFTOP](TGFTOP.md) | TOPDEVOLUCAOWMS->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoEFDEmpresa / [TGFTOP](TGFTOP.md) | EFDCODTIPOPER->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoEntrDifPeso / [TGFTOP](TGFTOP.md) | TOPENTDIFPESOWMS->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoEntrMerc / [TGFTOP](TGFTOP.md) | TOPENTRADAWMS->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoIpi / [TGFTOP](TGFTOP.md) | CODTIPOPERIPI->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoPedidoAgrupa / [TGFTOP](TGFTOP.md) | TOPENVWMSAGRUP->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoSaidaDifPeso / [TGFTOP](TGFTOP.md) | TOPSAIDADIFPESOWMS->CODTIPOPER | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoOperacaoSangria / [TGFTOP](TGFTOP.md) | TOPSANGSUPRI->CODTIPOPER | I |
| EtapaEstruturaProducao / [TGFEEP](TGFEEP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | TOPPROD->CODTIPOPER | I |
| EtapaProducao / [TGFETA](TGFETA.md) | TopEntrada / [TGFTOP](TGFTOP.md) | TOPENTRADA->CODTIPOPER | I |
| EtapaProducao / [TGFETA](TGFETA.md) | TopProducao / [TGFTOP](TGFTOP.md) | TOPPRODUCAO->CODTIPOPER | I |
| EtapaProducao / [TGFETA](TGFETA.md) | TopSaida / [TGFTOP](TGFTOP.md) | TOPSAIDA->CODTIPOPER | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPERREC->CODTIPOPER | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERDESP->CODTIPOPER | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXA->CODTIPOPER; DHTIPOPERBAIXA->DHALTER | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| IpiSuspenso / [TGFISU](TGFISU.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ItemRegras / [TGFITR](TGFITR.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODINSTPRINC->CODTIPOPER | I |
| ItemRegras / [TGFITR](TGFITR.md) | TOPDespesa / [TGFTOP](TGFTOP.md) | CODINSTSECFIN->CODTIPOPER | I |
| ItemRegras / [TGFITR](TGFITR.md) | TOPReceita / [TGFTOP](TGFTOP.md) | CODINSTSECINI->CODTIPOPER | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| MovimentoBancarioDevCheque / [TGFMBC](TGFMBC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| OrdemCarga / [TGFORD](TGFORD.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPERTRANSB->CODTIPOPER | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHALTERTOP->DHALTER | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| Produto / [TGFPRO](TGFPRO.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | TopSeparacao / [TGFTOP](TGFTOP.md) | CODTIPOPERDEST->CODTIPOPER | I |
| Servico / [TGFPRO](TGFPRO.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| TCOMNotaDebCred / [TGFTOPNDC](TGFTOPNDC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | TXCCODTIPOPER->CODTIPOPER | I |
| TipoMeta / [TGMTME](TGMTME.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODTIPOPER->CODIGO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | NUNOTAMODELO->NUNOTA | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalCombustivelLubrif / [TGFCFO](TGFCFO.md) | CODCFO_COMBUST_LUBRIF->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoEntrada / [TGFCFO](TGFCFO.md) | CODCFO_ENTRADA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoEntradaEstado / [TGFCFO](TGFCFO.md) | CODCFO_ENTRADA_FORA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoSaida / [TGFCFO](TGFCFO.md) | CODCFO_SAIDA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoSaidaEstado / [TGFCFO](TGFCFO.md) | CODCFO_SAIDA_FORA->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalOperacaoTerceiros / [TGFCFO](TGFCFO.md) | CODCFO_TERC->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ClaFiscalPrestacaoServico / [TGFCFO](TGFCFO.md) | CODCFPS->CODCFO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | CodigoIndicadorOperacao / [TGFINDOPER](TGFINDOPER.md) | CODINDOPER->CODINDOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | NUCCO->NUCCO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | EmailTOP / [TGFTEM](TGFTEM.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | FormatadorRemessa / [TSIREM](TSIREM.md) | CODREMEDI->CODIGO | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LayoutNota / [TGFLAY](TGFLAY.md) | NULAYOUT->NULAYOUT | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LayoutVendaRapida / [TGFLVR](TGFLVR.md) | NULAYOUTCVR->NULAYOUT | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LiberacaoPorTipoOperacao / [TGFTOL](TGFTOL.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | LocalPadraoImportarXML / [TGFLOC](TGFLOC.md) | CODLOCALIMPXML->CODLOCAL | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloDadosAdicionais / [TGFMON](TGFMON.md) | CODMODDAD->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloImpressaoCfeCanc / [TGFMON](TGFMON.md) | CODMODCFECANC->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloNFSe / [TGFMON](TGFMON.md) | CODMODNFSE->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | CODMODNF->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloRoman / [TGFMON](TGFMON.md) | CODMODRO->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ModeloRPS / [TGFMON](TGFMON.md) | CODMODRPS->CODMODNF | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDAVP->CODMOEDA | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | OrdemSugestaoLocal / [TGFOSL](TGFOSL.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODINTERM->CODPARC | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | L |
| TipoOperacao / [TGFTOP](TGFTOP.md) | PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | SerieEmpresa / [TGFSPE](TGFSPE.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TipoOperacaoDenegada / [TGFTOP](TGFTOP.md) | CODTIPOPERDENEG->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TipoOperacaoDenegadaCTe / [TGFTOP](TGFTOP.md) | CODTOPDENEGCTE->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TipoOperacaoDestino / [TGFTOP](TGFTOP.md) | CODTIPOPERDESTINO->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TipoOperacaoRetorno / [TGFTOP](TGFTOP.md) | CODTIPOPERPENRET->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TipoOperacaoSeparacao / [TGFTOP](TGFTOP.md) | CODTIPOPERSEPARACAO->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TopAtendimento / [TGFTOP](TGFTOP.md) | TOPATENDIMENTO->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TopBackorder / [TGFTOP](TGFTOP.md) | TOPBACKORDER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TopRegraNegocio / [TGFTRN](TGFTRN.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | TopRemessa / [TGFTOP](TGFTOP.md) | CODTIPOPERREM->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | UsoSeparacao / [TGFUSE](TGFUSE.md) | CODTIPOPER->CODTIPOPER | I |
| TopAliquotaEspecialICMS / [TGFAETOP](TGFAETOP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| TopRegraNegocio / [TGFTRN](TGFTRN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| Transferencia / [TGMTRA](TGMTRA.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPERSEP->CODTIPOPER | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | TopPrincipal / [TGFTOP](TGFTOP.md) | CODTIPOPERPRINC->CODTIPOPER | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | TopSeparacao / [TGFTOP](TGFTOP.md) | CODTIPOPERSEP->CODTIPOPER | I |
| Usuario / [TSIUSU](TSIUSU.md) | TOPDespesa / [TGFTOP](TGFTOP.md) | TOPBAIXADESPESA->CODTIPOPER | I |
| Usuario / [TSIUSU](TSIUSU.md) | TOPReceita / [TGFTOP](TGFTOP.md) | TOPBAIXARECEITA->CODTIPOPER | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| Verbas / [TGFGEV](TGFGEV.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | TIPOOPER->CODTIPOPER | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXA->CODTIPOPER; DHTIPOPERBAIXA->DHALTER | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXA->CODTIPOPER; DHTIPOPERBAIXA->DHALTER | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
