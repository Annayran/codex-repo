# TGFPRO — Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 388
- Instâncias DWF: 10
- Relações DWF: 322
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Produto | Produto | S | S |
| Produto3 | Produto | S | N |
| ProdutoApontamento1 | Produto/serviço para agrupar apontamento 1 | S | N |
| ProdutoApontamento2 | Produto/serviço para agrupar apontamento 2 | S | N |
| ProdutoEspecifico | Produto Específico | S | N |
| ProdutoIntermediario | Produto Intermediário | S | N |
| ProdutoSubstituido | ProdutoSubstituido | S | N |
| ProdutoSubstitutoKit | Produto Substituto do Kit | S | N |
| Servico | Serviço | S | S |
| TimServicoTxAdm | Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACRESCMAX | % Acréscimo Máximo | F |  |  | N |
| AGRUPCOMPMINIMO | Agrupamento Mínimo | F |  |  | N |
| AGRUPMIN | Agrupamento mínimo | F |  |  | N |
| ALERTAESTMIN | Alerta estoque mínimo | S |  |  | N |
| ALIQFECOP | Alíquota FECOP | F |  |  | N |
| ALIQFETHAB | Alíquota FETHAB | F |  |  | N |
| ALIQGERAL | Alíquota Geral | F |  |  | N |
| ALIQICMSINTEFD | Alíquota Interna de ICMS | F |  |  | N |
| ALIQIMP | ALIQIMP | F |  |  | N |
| ALIQINTERNACAT42 | Alíquota interna para CAT42 | F |  |  | N |
| ALIQNAC | ALIQNAC | F |  |  | N |
| ALTURA | Altura | F |  |  | N |
| AP1RCTEGO | Produto constante no apêndice I do RCTE/GO? | S |  |  | N |
| APLICACAO | Aplicações | I |  |  | N |
| APLICASAZO | Aplica Sazonalidade | S |  |  | N |
| APRESDETALHE | Apresenta tela de detalhes | S |  |  | N |
| APRESFORM | Apresenta em fórmulas de composição | S |  |  | N |
| APURAPRODEPE | Cód. Apur. Inc. PRODEPE/FUNCRESCE | S |  |  | N |
| ARMAZELOTE | Armazena por Lote | S |  |  | N |
| ARREDAGRUP | Arredondamento para Agrupamento | F |  |  | N |
| ARREDPRECO | Múltiplo p/ Preço | F |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| BALANCA | Utiliza balança | S |  |  | N |
| BLOQVENDAFRAC | Bloquear venda fracionada? | S |  |  | N |
| CALCDIFAL | Calcular DIFAL ? | S |  |  | N |
| CALCFUNTTELPRO | Calcular FUNTTEL? | S |  |  | N |
| CALCFUSTPRO | Calcular FUST? | S |  |  | N |
| CALCULOGIRO | Calcular giro pelo agendador | S |  |  | N |
| CALRUPTURAESTOQUE | Calcula Ruptura de Estoque? | S |  |  | N |
| CAMADAS | Camadas | I |  |  | N |
| CARACTERISTICAS | Características | S |  |  | N |
| CARENCIA | Carência (dias) | I |  |  | N |
| CAT1799SPRES | Ressarcimento de ST (CAT-17/99)? | S |  |  | N |
| CIENTIFICO | Nome científico | S |  |  | N |
| CLASSEAGT | Classe do agrotóxico | I |  |  | N |
| CLASSETOX | Classe tóxica | I |  |  | N |
| CLASSIFCESSAOOBRA | Classificação Cessão M.d.Obra | I |  |  | N |
| CLASSUBTRIB | Classificação Substituição Tributária | I |  |  | N |
| CNAE | CNAE | I |  |  | N |
| CNPJFABRICANTE | CNPJ do Fabricante da Mercadoria | S |  |  | N |
| CODAGREGACAO | Cód. de Agregação | S |  |  | N |
| CODANP | Código ANP | I |  |  | N |
| CODANVISA | Cód. ANVISA | S |  |  | N |
| CODAREASEP | Cód.Área Separação | I |  |  | N |
| CODATIVREINTEGRA | Código Atividade Reintegra | S |  |  | N |
| CODAUTCODIF | Autorização CODIF | S |  |  | N |
| CODBARBALANCA | Cód.Barras Balança por Unidade | S |  |  | N |
| CODBARCOMP | Conferência por Codigo de Barras Composto | S |  |  | N |
| CODBARDIFGTIN | Código de Barras diferente do padrão GTIN | S |  |  | N |
| CODBARTRIBDIFGTIN | Código de Barras da Un. Trib. diferente do padrão GTIN | S |  |  | N |
| CODBENEFNAUF | Cód. de Benefício Fiscal na UF | S |  |  | N |
| CODCAT | Categoria | I |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCLASTRIBNAC | Código Classificação Tributária Nacional | S |  |  | N |
| CODCOMP | Componente do Serviço | I |  |  | N |
| CODCONFKIT | Código Configuração Kit | I |  |  | N |
| CODCOS | Status OS | I |  |  | N |
| CODCPR | Classificação | I |  |  | N |
| CODCPRB | Cód. Atividade CPRB (Reintegra/Prev.) | S |  |  | N |
| CODCTACTB | Conta contábil 1 | I |  |  | N |
| CODCTACTB2 | Conta contábil 2 | I |  |  | N |
| CODCTACTB3 | Conta contábil 3 | I |  |  | N |
| CODCTACTB4 | Conta contábil 4 | I |  |  | N |
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODENQIPIENT | Código Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Código Enq. Legal IPI Saída | I |  |  | N |
| CODESPECST | CEST - Código Especificador ST | I |  |  | N |
| CODEXNCM | Cód. Exceção NCM | I |  |  | N |
| CODFAB | Cód. Fabricante | I |  |  | N |
| CODFCI | Código da FCI | S |  |  | N |
| CODFCICALC | Código da FCI | S |  |  | S |
| CODFILTRO | Filtro p/ Cálc.Custo baseado no Financeiro | I |  |  | N |
| CODFILTROCTA | Filtro p/ Cálc.Custo baseado em Lanç. Contábeis | I |  |  | N |
| CODFILTROREQ | Filtro p/ Cálc.Custo baseado na Requisição | I |  |  | N |
| CODFORMAPONTA | Fórmula Apontamento | I |  |  | N |
| CODFORMPREC | Fórmula de precificação | I |  |  | N |
| CODGAR | Extensão de garantia | I |  |  | N |
| CODGENERO | Gênero | I |  |  | N |
| CODGPROD | Grupo Produção | I |  |  | N |
| CODGRUPOPROD | Grupo | I |  |  | N |
| CODICMSFAST | Alíquota ICMS do Fast Service | F |  |  | N |
| CODIDCNAE | Código ID CNAE | I |  |  | N |
| CODIGONFCOM | Cód. Item NFCom | S |  |  | N |
| CODINDOPERPROD | Código Indicador de Operação de Fornecimento (indoper) | S |  |  | N |
| CODIPI | IPI | I |  |  | N |
| CODLOCALPADRAO | Local Padrão | I |  |  | N |
| CODLST | Tipo de Serviço | I |  |  | N |
| CODMARCA | Marca | I |  |  | N |
| CODMOEDA | Moeda p/ preço | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODNATREND | Código Natureza Rendimento | I |  |  | N |
| CODNBS | Código NBS | I |  |  | N |
| CODPAIS | País de origem | I |  |  | N |
| CODPARCCONSIG | Parceiro Consignante | I |  |  | N |
| CODPARCFORN | Parceiro Fornecedor preferencial | I |  |  | N |
| CODPAT | Princ. Ativo Medicamento | I |  |  | N |
| CODPROD | Código | I |  |  | N |
| CODPRODAGRUPAPT | Produto/Serviço p/ agrupar apontamento | I |  |  | N |
| CODPRODAGRUPAPTENC | Produto/Serviço p/ agrupar apontamento de encarregado | I |  |  | N |
| CODPRODROI | Número do Item (Portaria 384/01) | S |  |  | N |
| CODPRODSUBKIT | Código do Produto Substituto do Kit | I |  |  | N |
| CODPRODSUBST | Código do Produto Substituto | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODREGMAPA | Registro no M.A.P.A | S |  |  | N |
| CODRFA | Regra armazenagem | I |  |  | N |
| CODSERVTELECOM | Cód. Serviço de Telecomunicação | I |  |  | N |
| CODTAB | Tabela c/ base p/ substituição na venda | I |  |  | N |
| CODTER | Classe Terapêutica | I |  |  | N |
| CODTRIBMUNISS | Cód. Trib. Município NFS-e | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVOL | Unidade padrão | S |  |  | N |
| CODVOLCOMPRA | Unid. Compra | S |  |  | N |
| CODVOLFETHAB | Unidade Padrão p/ FETHAB | S |  |  | N |
| CODVOLKANBAN | Unidade de movimentação padrão | S |  |  | N |
| CODVOLPESOVAR | Unidade de separação para produtos com peso variável | S |  |  | N |
| CODVOLPLAN | Unid. Planejamento | S |  |  | N |
| CODVOLRES | Unidade para Resumo de Entrega | S |  |  | N |
| CODVTP | Meio de transporte | I |  |  | N |
| COMERCIALIZACAOAGRI | Comercialização Agrícola? | S |  |  | N |
| COMGER | % Comissão gerente | F |  |  | N |
| COMPLDESC | Complemento | S |  |  | N |
| COMPONOBRIG | Validação de componentes | S |  |  | N |
| COMVEND | % Comissão vendedor | F |  |  | N |
| CONCENTRACAO | Concentração | S |  |  | N |
| CONESTORIGPROD | Controla estoque por origem de produto | S |  |  | N |
| CONFCEGAPESO | Conferência cega por peso | S |  |  | N |
| CONFERE | Confere por Cód. Barra | S |  |  | N |
| CONSPRODCAT42 | Considerar produto na geração da Cat 42? | S |  |  | N |
| CONTROLADO | Controlado | S |  |  | N |
| CONTROLMEDIC | Realiza controle por medição | S |  |  | N |
| CONVERVOL | Conversão de Volume | F |  |  | N |
| CORFONTCONSPRECO | Cor da Fonte | I |  |  | N |
| CORFUNDOCONSPRECO | Cor do Fundo | I |  |  | N |
| CREDPISCOFINSAQUIPAR | Parcelas Créd. PIS/COFINS sobre Aquisição | I |  |  | N |
| CSTIPIENT | Código Sit.Trib.IPI Entrada | I |  |  | N |
| CSTIPISAI | Código Sit.Trib.IPI Saída | I |  |  | N |
| CULTURA | Nome da cultura | S |  |  | N |
| DECCUSTO | Decimais para o custo | I |  |  | N |
| DECQTD | Decimais para quantidade | I |  |  | N |
| DECVLR | Decimais para o valor | I |  |  | N |
| DESCMAX | % Desconto Máximo | F |  |  | N |
| DESCMAXFLEX | % Desconto Máximo Flex | F |  |  | N |
| DESCPRODDRCST | Desconsiderar produto na geração da DRCST? | S |  |  | N |
| DESCRANP | Descrição ANP | S |  |  | N |
| DESCRPROD | Descrição | S |  |  | N |
| DESCRPRODNFE | Descrição para NFE | S |  |  | N |
| DESCVENCONSUL | Desconsiderar nos result. venda consultiva | S |  |  | N |
| DESDESCCALCPIS | Desconsiderar desconto no cálculo de PIS/COFINS? | S |  |  | N |
| DESVIOMAX | % Desvio Máximo | F |  |  | N |
| DESVIOMAXTOLCONFSEP | Desvio máximo tolerado na conferência da separação | I |  |  | N |
| DESVIOMINTOLCONFSEP | Desvio mínimo tolerado na conferência da separação | I |  |  | N |
| DIASEXPEDICAO | Dias para Expedição | I |  |  | N |
| DIMENSOES | Informar dimensões | S |  |  | N |
| DOSAGEM | Dosagem indicada | F |  |  | N |
| DOSAGEMPOR | Qtd.Hectares/dosagem | F |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| DTALTEREMQ | Data de Alteração (Estoque Máximo) | H |  |  | N |
| DTALTERESQ | Data de Alteração (Estoque de Segurança) | H |  |  | N |
| DTSUBST | Data da Substituição do Produto | H |  |  | N |
| DTVALDIF | Dt.Val. diferentes na armazenagem | S |  |  | N |
| ENDIMAGEM | Endereço da Imagem | S |  |  | N |
| ENDMODROTULO | Endereço do Modelo do Rótulo | S |  |  | N |
| ENQREINTEGRA | Enquadrado no Reintegra/Prev.? | S |  |  | N |
| EPOCAAPLIC | Época de aplicação | S |  |  | N |
| ESPESSURA | Espessura/Profundidade | F |  |  | N |
| ESTMAX | Estoque Máximo | F |  |  | N |
| ESTMAXDIAS | Estoque Máximo (dias úteis) | I |  |  | N |
| ESTMAXQTD | Estoque Máximo | F |  |  | N |
| ESTMIN | Estoque Mínimo | F |  |  | N |
| ESTSEGDIAS | Estoque de Segurança (dias úteis) | I |  |  | N |
| ESTSEGQTD | Estoque de Segurança | F |  |  | N |
| EXCLUIRCONF | Excluir do processo de conferência | S |  |  | N |
| EXIGEBENEFIC | Exige beneficiamento | S |  |  | N |
| EXIGELASTROCAMADAS | Exige Lastro e Camadas | S |  |  | N |
| FABRICANTE | Fabricante | S |  |  | N |
| FATTOTAL | Faturar Total | S |  |  | N |
| FIXOAGENDA | Fixar na agenda | S |  |  | N |
| FLEX | FLEX | S |  |  | N |
| FORMULACAO | Formulação | S |  |  | N |
| FRAGMENTALOTE | Fragmenta lote no envio para separação | S |  |  | N |
| GERAPLAPROD | Plan. Produção pelo Est. Mínimo | S |  |  | N |
| GERIMPNRETREINFAQ | Gera Imposto não-retido evento 2055 da EFD-Reinf? | S |  |  | N |
| GRADEPADRAO | Grade Pradão | C |  |  | N |
| GRUPOCOFINS | Grupo COFINS | S |  |  | N |
| GRUPOCSSL | Grupo CSLL | S |  |  | N |
| GRUPODESCPROD | Grupo Desconto | S |  |  | N |
| GRUPOIBSCBS | Grupo IBS/CBS | I |  |  | N |
| GRUPOICMS | Grupo ICMS | I |  |  | N |
| GRUPOICMS2 | Grupo de ICMS 2 | I |  |  | N |
| GRUPOIS | Grupo IS | I |  |  | N |
| GRUPOPIS | Grupo PIS | S |  |  | N |
| GRUPOQUIMICO | Grupo químico | I |  |  | N |
| GRUPOTRANSG | Grupo transg. | I |  |  | N |
| HOMEPAGE | Home Page | S |  |  | N |
| HRDOBRADA | Hora Dobrada | S |  |  | N |
| ICMSGERENCIA | Considerar débito ICMS nas consultas gerenciais? | S |  |  | N |
| IDENCORRELATO | Identificação de Correlato | S |  |  | N |
| IDENCOSME | Identificação de Cosmecêutico | S |  |  | N |
| IDENOTC | Identificação de OTC | S |  |  | N |
| IDENPORTARIA | Identificação de Portaria | S |  |  | N |
| IDENTIMOB | Identificação do Imobilizado | I |  |  | N |
| IDGRADE | Modelo de Grade | I |  |  | N |
| IMAGEM | Imagem | B |  |  | N |
| IMPETIQCONF | Imprime etiqueta de volumes na conferência | S |  |  | N |
| IMPETIQSEPWMS | Imprime etiqueta para separação | S |  |  | N |
| IMPLAUDOLOTE | Imprime laudo por lote | S |  |  | N |
| IMPORDEMCORTE | Imprime ordem de corte | S |  |  | N |
| INCPESOBRUTO | Incremento de peso bruto | F |  |  | N |
| INCPESOLIQUIDO | Incremento de peso líquido | F |  |  | N |
| INDESCALA | Indicador de Escala Relevante | S |  |  | N |
| INDESPPRODEPE | Indicador Esp. Inc. PRODEPE/FUNCRESCE | S |  |  | N |
| INDTIPREFBCICMSST | Indicador de Tipo de Referência da BC do ICMS ST | S |  |  | N |
| INFPUREZA | Informa % Pureza e % Germinação | S |  |  | N |
| INTEGRAECONECT | Integrar com EConect | S |  |  | N |
| INTEGRAFOX | Integra com Sankhya Checkout | I |  |  | N |
| INTERVALO | Intervalo (dias) | I |  |  | N |
| LARGURA | Largura | F |  |  | N |
| LASTRO | Lastro | I |  |  | N |
| LEADTIME | Lead time de compra | I |  |  | N |
| LISCONTEST | Lista Controle de estoque | S |  |  | N |
| LISTALPM | Lista de Procedimentos Médicos | S |  |  | N |
| LOCAL | Local | S |  |  | N |
| LOCALIZACAO | Localização | S |  |  | N |
| LOTECOMPMINIMO | Compra Mínima | F |  |  | N |
| LOTECOMPRAS | Lote de Compra (dias úteis) | I |  |  | N |
| M3 | Metros Cúbicos | F |  |  | N |
| MANEJOINT | Manejo integrado | S |  |  | N |
| MARCA | Marca | S |  |  | N |
| MARGLUCRO | % Margem de lucro | F |  |  | N |
| MAXMULTECONECT | Máx. Multiplicador | I |  |  | N |
| MEDAUX | Medida auxiliar | F |  |  | N |
| MODETIQSEPWMS | Modelo de etiqueta para separação | I |  |  | N |
| MODOAPLIC | Modo de aplicação | S |  |  | N |
| MOTISENCAOANVISA | Motivo Isenção Anvisa | S |  |  | N |
| MOTIVOINCEXC | Filtro para motivos SubOS | S |  |  | N |
| MULTIPVENDA | Multiplicador para venda | F |  |  | N |
| MVAAJUSTADO | % MVA Ajustado | F |  |  | N |
| MVAORIGINALADRCST | MVA Original para ADRC-ST (PR) | F |  |  | N |
| MVAORIGINALDRCST | MVA Original para DRCST | F |  |  | N |
| MVAPADRAO | MVA Padrão | F |  |  | N |
| NATEFDCONTM410M810 | Cód. Natureza (PIS/COFINS M410/M810) | I |  |  | N |
| NATUREZAOPERDES | Natureza da Operação de Declaração Eletrônica de Serviços | S |  |  | N |
| NCM | NCM | S |  |  | N |
| NOMETAB | Nome da tabela | S |  |  | N |
| NOTIFCONF | Notificação para conferência | S |  |  | N |
| NPROCSUFRAMA | Número do Processo na Suframa | S |  |  | N |
| NROPROCESSO | Nro do Processo Judicial/Adm (ISS) | S |  |  | N |
| NUMITEMREA | Nro.Item REA/ICMS | I |  |  | N |
| NURFE | Modelo de etiqueta | I |  |  | N |
| NUVERSAO | Versão | I |  |  | N |
| OBRACONSTCIVIL | Obra de Construção Civil | I |  |  | N |
| OBSETIQUETA | Observação | S |  |  | N |
| OBTSTANTMEDENT | Obtém ST Anterior pela média das entradas? | S |  |  | N |
| ONEROSO | Oneroso | S |  |  | N |
| OPEEXPFETHAB | Resp. pelo Recolhimento em Operações de Exportação | S |  |  | N |
| OPEINTESTFETHAB | Resp. pelo Recolhimento em Operações Interestaduais | S |  |  | N |
| OPEINTFETHAB | Resp. pelo Recolhimento em Operações Internas | S |  |  | N |
| ORDEMMEDIDA | Ordem medida | F |  |  | N |
| ORIGPROD | Origem do produto | S |  |  | N |
| PADRAO | Produto padrão | S |  |  | N |
| PARTICIPAADRCST | Considerar geração da ADRC-ST (PR)? | S |  |  | N |
| PERCAUMENTCUSTO | % Aviso Var. Custo | F |  |  | N |
| PERCCMTEST | % Carga Média Trib. Estadual | F |  |  | N |
| PERCCMTFED | % Carga Média Trib. Federal | F |  |  | N |
| PERCCMTIMP | % Carga Média Trib. Importação | F |  |  | N |
| PERCCMTMUN | % Carga Média Trib. Municipal | F |  |  | N |
| PERCCMTNAC | % Carga Média Trib. Nacional | F |  |  | N |
| PERCENTSEPPUL | Percentual para separação pulmão(0-100) | F |  |  | N |
| PERCINDMISTURA | Percentual do Índice de Mistura | F |  |  | N |
| PERCINSS | % INSS | F |  |  | N |
| PERCINSSESPECIAL | % INSS Especial | F |  |  | N |
| PERCIRF | % IRF | F |  |  | N |
| PERCMISTGLP | Percentual de mistura GLP | F |  |  | N |
| PERCMISTGNI | Percentual de mistura de Gás Natural Importado | F |  |  | N |
| PERCMISTGNN | Percentual de mistura de Gás Natural Nacional | F |  |  | N |
| PERCQUEBRATEC | % Quebra Técnica | F |  |  | N |
| PERCREDBASEICMSEFET | Perc. Red. Base Icms Efetivo | F |  |  | N |
| PERCTOLPESOMAIOR | % de tolerância de peso a maior no recebimento | F |  |  | N |
| PERCTOLPESOMAIORSEP | % de tolerância de peso a maior na separação | F |  |  | N |
| PERCTOLPESOMENOR | % de tolerância de peso a menor no recebimento | F |  |  | N |
| PERCTOLPESOMENORSEP | % de tolerância de peso a menor na separação | F |  |  | N |
| PERMCOMPPROD | Permite comprar este produto? | S |  |  | N |
| PESOBRUTO | Peso bruto | F |  |  | N |
| PESOLIQ | Peso líquido | F |  |  | N |
| PRAZOVAL | Prazo validade/tolerância | I |  |  | N |
| PRINCATIVO | Princípio ativo | I |  |  | N |
| PRODALIADRCST | Produto alimentício conforme art. 119 do Anexo IX do RICMS/2017 PR? | S |  |  | N |
| PRODFALTA | Produto em Falta | S |  |  | N |
| PRODINTERNO | Considerar na Integração Impostos | S |  |  | N |
| PRODSUJFECOP | Produto sujeito ao FECOP? | S |  |  | N |
| PRODUTONFE | Cód. Produto p/ NF-e/NFC-e/CF-e | I |  |  | N |
| PROMOCAO | Promoção | S |  |  | N |
| QTDAGRUPAMENTOMTZ | Qtd. multiplicador Compra | F |  |  | N |
| QTDEMB | Quantidade de embalagens | I |  |  | N |
| QTDIDENTIF | Qtd. identificadores | I |  |  | N |
| QTDNFLAUDOSINT | Qtd. Notas entre laudos internos | I |  |  | N |
| RASTRESTOQUE | Rastreamento de Estoques | S |  |  | N |
| RECEITUARIO | Exige receituário agronômico | S |  |  | N |
| RECUPAVARIA | Recuperação de Avaria? | S |  |  | N |
| REDBASEINSS | % Red. Base INSS | F |  |  | N |
| REDBASEIRF | % Red. Base IRF | F |  |  | N |
| REFERENCIA | Referência | S |  |  | N |
| REFFORN | Referência do Fornecedor | S |  |  | N |
| REFMERCMED | Referência no Mercado de Medicamento | S |  |  | N |
| REGISTRARPESO | Registrar peso | S |  |  | N |
| REGRAWMS | Regras de WMS | S |  |  | N |
| REMETER | Remeter? | S |  |  | N |
| RENDIMENTO | Rendimento | S |  |  | N |
| RENDIMENTOHA | Dosagem/Ha Planejamento | F |  |  | N |
| SELECIONADO | Seleção | S |  |  | N |
| SEQSCA | Sub-Categoria | I |  |  | N |
| SEQSPR | Sub-Classificação | I |  |  | N |
| SEQSTE | Sub-Terapêutica | I |  |  | N |
| SERFATURCON | Série para faturamento em contratos | S |  |  | N |
| SERVDESPNTRIB | Tratar serviço como despesa não tributável no JSON? | S |  |  | N |
| SERVPRESTTER | Serviço prestado por terceiro? | S |  |  | N |
| SHELFLIFE | Shelf life | I |  |  | N |
| SHELFLIFEMIN | Shelf life mín. em recebimentos | I |  |  | N |
| SOLCOMPRA | Solicita compra? | S |  |  | N |
| STATUSINCEXC | Filtro para status SubOS | S |  |  | N |
| STATUSMED | Status do produto | I |  |  | N |
| STATUSNCM | NCM Válido | S |  |  | N |
| TAMANHOMEDIOPECA | Tamanho médio por peça (metros) | I |  |  | N |
| TAMLOTE | Tamanho Lote | I |  |  | N |
| TAMSERIE | Tamanho Série | I |  |  | N |
| TEMCIAP | Atualizar CIAP? | S |  |  | N |
| TEMCOMISSAO | Calcular comissão | S |  |  | N |
| TEMCREDPISCOFINSAQUI | Tem Créd. PIS/COFINS sobre Aquisição? | S |  |  | N |
| TEMCREDPISCOFINSDEPR | Tem Créd. PIS/COFINS sobre Deprec. mensal? | S |  |  | N |
| TEMICMS | Calcular ICMS? | S |  |  | N |
| TEMINSS | Tem INSS? | S |  |  | N |
| TEMIPICOMPRA | Tem IPI na compra? | S |  |  | N |
| TEMIPIVENDA | Tem IPI na venda? | S |  |  | N |
| TEMIRF | Tem IRF | S |  |  | N |
| TEMISS | Tem ISS | S |  |  | N |
| TEMMEDICAO | Realiza controle por medição | S |  |  | N |
| TEMPMAXIMA | Temperatura Máxima em ºC | I |  |  | N |
| TEMPMINIMA | Temperatura Mínima em ºC | I |  |  | N |
| TEMPOSERV | Tempo Estimado | H |  |  | N |
| TEMRASTROLOTE | Tem Rastro do Lote | S |  |  | N |
| TERMOLABIL | Termolábil | S |  |  | N |
| TIPCONTEST | Tipo de controle de estoque | S |  |  | N |
| TIPCONTESTWMS | Usa controle adicional no WMS ? | S |  |  | N |
| TIPGTINNFE | EAN/GTIN Produto p/ NF-e | I |  |  | N |
| TIPLANCNOTA | Digitação na nota | S |  |  | N |
| TIPO | Tipo | S |  |  | S |
| TIPOCONTAGEM | Tipo de Contagem | S |  |  | N |
| TIPOIDENTIF | Tipo identificador | S |  |  | N |
| TIPOINSSESPECIAL | Tipo de INSS Especial | S |  |  | N |
| TIPOITEMSPED | Tipo do item p/ SPED | S |  |  | N |
| TIPOKIT | Tipo de Kit | S |  |  | N |
| TIPOSN | Tipo de Partilha/Anexo | I |  |  | N |
| TIPSERNFE | Tipo de série para NF-e | S |  |  | N |
| TIPSUBST | Tipo de substituição | S |  |  | N |
| TIPUTILCOM | Tipo de Utilização | I |  |  | N |
| TITCONTEST | Título Controle de estoque | S |  |  | N |
| TOPFATURCON | TOP para faturamento em contratos | I |  |  | N |
| TPCREDPRESIBSZFM | Classificação para subapuração do IBS na ZFM | S |  |  | N |
| TPIRRFEXT | Tributação IRRF - Exterior REINF | I |  |  | N |
| UNIDADE | Unid. de Medida | S |  |  | N |
| UNIDMINARMAZ | Unid. Min. Armazenagem WMS | S |  |  | N |
| USACODBARRASQTD | Cód.Barras com quantidade | S |  |  | N |
| USACONTPESOVAR | Produto controlado por peso variável | S |  |  | N |
| USAIMPAGRUPMIN | Imprimir Etiqueta de Volume por Agrupamento Mínimo | S |  |  | N |
| USALOCAL | Usa local | S |  |  | N |
| USALOTEDTFAB | Utiliza data de Fabricação | S |  |  | N |
| USALOTEDTVAL | Utiliza data de Validade | S |  |  | N |
| USASERIEFAB | Exige Nro de Série do Fabricante | S |  |  | N |
| USASERIESEPWMS | Coletar Série na Conferência de Expedição do WMS? | S |  |  | N |
| USASTATUSLOTE | Usa Status de Lote | S |  |  | N |
| USOPROD | Usado como | S |  |  | N |
| UTILIMOB | Utilização do Imobilizado | I |  |  | N |
| UTILIZAENDFLUT | Utiliza endereço flutuante | S |  |  | N |
| UTILIZAWMS | Controlado pelo WMS | S |  |  | N |
| UTILSMARTCARD | Utiliza SmartCard? | S |  |  | N |
| VALCAPM3 | Validar capac.produção diária em M3 | S |  |  | N |
| VALCBGLOBAL | Valida Cód. Barra de Nro Série Global | S |  |  | N |
| VENCOMPINDIV | Aceitar venda fora do KIT | S |  |  | N |
| VISIVELAPPOS | Apresentar no aplicativo de Ordem de Serviço | S |  |  | N |
| VLRCOMERC | Valor de Comercialização - (R$) | F |  |  | N |
| VLRCOMERCCALC | Valor de Comercialização - (R$) | F |  |  | S |
| VLRPARCIMPEXT | Valor da Parcela Importada do Exterior - (R$) | F |  |  | N |
| VLRPARCIMPEXTCALC | Valor da Parcela Importada do Exterior - (R$) | F |  |  | S |
| VLRPARTIDAGLP | Valor de Partida GLP | F |  |  | N |
| VOLDOSAGEM | Unidade dosagem | S |  |  | N |
| VOLDOSAGEMPOR | Volume dosagem por | S |  |  | N |
| WMSPRODRASTSERMED | Produto rastreado por série e controle de medicamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcabamentoCivil / [TCCACA](TCCACA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AgendaFixa / [TCSAGF](TCSAGF.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Agendamento / [TCSAGE](TCSAGE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Produto / [TGFPRO](TGFPRO.md) | CODIGO->CODPROD | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Servico / [TGFPRO](TGFPRO.md) | CODIGO->CODPROD | I |
| AliquotaIPI / [TGFIPI](TGFIPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODIPI->CODIPI | I |
| AliquotaISS / [TGFISS](TGFISS.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ArmazenaDisponibilidadeDiaMaq / [TGFDID](TGFDID.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ArmazenaDisponibilidadeDiaMaq / [TGFDID](TGFDID.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| BaseConhecimento / [TCSERR](TCSERR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Carrinho / [TGFCAC](TGFCAC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | ProdutoEspecifico / [TGFPRO](TGFPRO.md) | CODPRODESP->CODPROD | I |
| ComercialProduto / [TCSCPR](TCSCPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Comissao / [TCSCOM](TCSCOM.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Conta / [TCICTA](TCICTA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContagemEstoqueSerie / [TGFCTS](TGFCTS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContagemPagina / [TGFCTP](TGFCTP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERVEX->CODPROD | I |
| ContratoServico / [TCSCSE](TCSCSE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ControleFCI / [TGFMFCI](TGFMFCI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Custo / [TGFCUS](TGFCUS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Custo / [TGFCUS](TGFCUS.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| CustoItem / [TGFCUSITE](TGFCUSITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DemonstrativoTcidem / [TCIDEM](TCIDEM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Desconto / [TGFDES](TGFDES.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DescontoCotacao / [TGFITD](TGFITD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DescontoFob / [TGFDFO](TGFDFO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DesenvolvProdContEntExistentes / [TGFPPE](TGFPPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DesenvolvProdContEst / [TGFPPR](TGFPPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalheDesenhoChapas / [TGFDDC](TGFDDC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalheImportacaoTributoFed / [TGFDITBF](TGFDITBF.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalheImportacaoTributoICMS / [TGFDITBI](TGFDITBI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalheImportacaoTributoIPI / [TGFDITBP](TGFDITBP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DiagnosticosProdutos / [TGADPR](TGADPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DisponibilidadeDiaria / [TGFDID](TGFDID.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DisponibilidadeDiaria / [TGFDID](TGFDID.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EntradaSaidaEstoque / [TGFESE](TGFESE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EquipamentoApontamento / [TAPEQT](TAPEQT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EquipamentoProjeto / [TAPEPJ](TAPEPJ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Estoque / [TGFEST](TGFEST.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EstoqueUnidadesCivil / [VGUNIDADESCC](VGUNIDADESCC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EventoVeiculo / [TSIEVE](TSIEVE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| Excecao / [TGFEXC](TGFEXC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| FamiliaProduto / [TGFFAM](TGFFAM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODFILHO->CODPROD | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| GarantiaProduto / [TGFGAR](TGFGAR.md) | Produto / [TGFPRO](TGFPRO.md) | CODGAR->CODGAR | I |
| GarantiaProdutoGestFinanc / [TGFGAR](TGFGAR.md) | Servico / [TGFPRO](TGFPRO.md) | CODGAR->CODPROD | I |
| GiroProduto / [TGFGIR](TGFGIR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| GradeProduto / [TGFGRA](TGFGRA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| GrupoPlanejamentoPlantio / [TGAGPL](TGAGPL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| GrupoProducaoProduto / [TGAGRU](TGAGRU.md) | Produto / [TGFPRO](TGFPRO.md) | CODGPROD->CODGPROD | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | Produto / [TGFPRO](TGFPRO.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODIPI->CODIPI | I |
| HistoricoEtapa / [TCSEHI](TCSEHI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| HistoricoPedido / [TGFHPE](TGFHPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| HistoricoUnidadeAlternativa / [TGFVOAH](TGFVOAH.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ImagemAlternativaProduto / [TGFIMAL](TGFIMAL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| InsumoEtapa / [TCSFPE](TCSFPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| InsumoModeloEtapa / [TCSMPR](TCSMPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| InventarioBem / [TCICTE](TCICTE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| IpiSuspenso / [TGFISU](TGFISU.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemAgenda / [TGFIAS](TGFIAS.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ItemApuracaoSTAnteriorPorMedia / [TGFIAPURSTMEDENT](TGFIAPURSTMEDENT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | Servico / [TGFPRO](TGFPRO.md) | CODMATPRIMA->CODPROD | I |
| ItemContratoCivil / [TCCITE](TCCITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemDespesaInstalacaoBem / [TCIDIBI](TCIDIBI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemGrupoEvolucaoPreco / [TGVIGEP](TGVIGEP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemKitPropostaComercial / [TGIITE](TGIITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemMovimentoRM / [TAPIRM](TAPIRM.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ItemNota / [TGFITE](TGFITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemPlanejamentoEntrega / [TGFPLAI](TGFPLAI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemRastreamentoEstoque / [TGFIRAST](TGFIRAST.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | Produto / [TGFPRO](TGFPRO.md) | CODINSTPRINC->CODPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | Produto3 / [TGFPRO](TGFPRO.md) | CODINSTSECFIN->CODPROD | I |
| ItemResumoFaturamento / [TAPIFT](TAPIFT.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ItemVolumeConferencia / [TGFIVC](TGFIVC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItensApuracaoReceitaMensal / [TGFIARM](TGFIARM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| LinhaBaseEtapa / [TCSLBE](TCSLBE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Local / [TCILOC](TCILOC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MaoForno / [TGFNMA](TGFNMA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MarcaParceiro / [TGFPMA](TGFPMA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MetaAtual / [TGMMET](TGMMET.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ModeloEtapa / [TCSMOD](TCSMOD.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MovimentacaoImobilizado / [TCIMOV](TCIMOV.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MovimentoEpi / [TGFMEPI](TGFMEPI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MultiplosComponentesServico / [TGFMCS](TGFMCS.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Natureza / [TGFNAT](TGFNAT.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERVUNICO->CODPROD | I |
| NaturezaServico / [TCSNTS](TCSNTS.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| NotasAjustesCPRB / [TGFNCPRB](TGFNCPRB.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Servico / [TGFPRO](TGFPRO.md) | CODPARC->CODPARCFORN | I |
| ParceiroServico / [TGFPAS](TGFPAS.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ParceiroTabelaProgressiva / [TGFPTP](TGFPTP.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PlanejamentoProducaoQuibasa / [TGFPLP](TGFPLP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PrecoContrato / [TCSPRE](TCSPRE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PrecoContrato / [TCSPRE](TCSPRE.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PrecoMedioProdutoEmpresa / [TGFPMPE](TGFPMPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODPROD->CODIGO | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaInternaDestino / [TGFAID](TGFAID.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaIPI / [TGFIPI](TGFIPI.md) | CODIPI->CODIPI | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaPisCofinsCssl1 / [TGFIFE](TGFIFE.md) | GRUPOPIS->GRUPOIMP | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaPisCofinsCssl2 / [TGFIFE](TGFIFE.md) | GRUPOCOFINS->GRUPOIMP | I |
| Produto / [TGFPRO](TGFPRO.md) | AliquotaPisCofinsCssl3 / [TGFIFE](TGFIFE.md) | GRUPOCSSL->GRUPOIMP | I |
| Produto / [TGFPRO](TGFPRO.md) | AreaSeparacao /  | CODAREASEP->CODAREASEP | I |
| Produto / [TGFPRO](TGFPRO.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| Produto / [TGFPRO](TGFPRO.md) | CapacidadeRecurso /  | CODPROD->CODPRODPA | I |
| Produto / [TGFPRO](TGFPRO.md) | CategoriaMedicamento / [TGFCAT](TGFCAT.md) | CODCAT->CODCAT | I |
| Produto / [TGFPRO](TGFPRO.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Produto / [TGFPRO](TGFPRO.md) | ClasseTerapeutica / [TGFTER](TGFTER.md) | CODTER->CODTER | I |
| Produto / [TGFPRO](TGFPRO.md) | ClassificacaoMedicamento / [TGFCPR](TGFCPR.md) | CODCPR->CODCPR | I |
| Produto / [TGFPRO](TGFPRO.md) | CodigoBarras / [TGFBAR](TGFBAR.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | CodigoItemNFCom / [TGFCINFCOM](TGFCINFCOM.md) | CODIGONFCOM->CODIGO | I |
| Produto / [TGFPRO](TGFPRO.md) | ComponenteManufaturaPA /  | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | IDGRADE->IDGRADE | I |
| Produto / [TGFPRO](TGFPRO.md) | ConfiguracaoKit / [TSIKIT](TSIKIT.md) | CODCONFKIT->CODCONFKIT | I |
| Produto / [TGFPRO](TGFPRO.md) | DisponibilidadeDiaria / [TGFDID](TGFDID.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | EFDProduto0200 /  | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Estoque / [TGFEST](TGFEST.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | EstruturaLote / [TCIEST](TCIEST.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | FamiliaProduto / [TGFFAM](TGFFAM.md) | CODPROD->CODPRODPAI | I |
| Produto / [TGFPRO](TGFPRO.md) | Flex / [TGFPRF](TGFPRF.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Formula / [TSIFOR](TSIFOR.md) | CODFILTRO->CODFORM | I |
| Produto / [TGFPRO](TGFPRO.md) | FormulaPrecificacao / [TGFFOR](TGFFOR.md) | CODFORMPREC->CODFORMPREC | I |
| Produto / [TGFPRO](TGFPRO.md) | FormulaRequisicao / [TSIFOR](TSIFOR.md) | CODFILTROREQ->CODFORM | I |
| Produto / [TGFPRO](TGFPRO.md) | GeneroProduto / [TGFSTG](TGFSTG.md) | CODGENERO->CODIGO | I |
| Produto / [TGFPRO](TGFPRO.md) | GradeProduto / [TGFGRA](TGFGRA.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | GrupoProducaoProduto / [TGAGRU](TGAGRU.md) | CODGPROD->CODGPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | LigacaoClassificacaoProduto / [TGFCLP](TGFCLP.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALPADRAO->CODLOCAL | I |
| Produto / [TGFPRO](TGFPRO.md) | MarcaProduto / [TGFMAR](TGFMAR.md) | CODMARCA->CODIGO | I |
| Produto / [TGFPRO](TGFPRO.md) | ModeloEtiqueta / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| Produto / [TGFPRO](TGFPRO.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Produto / [TGFPRO](TGFPRO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Produto / [TGFPRO](TGFPRO.md) | Ncm / [TGFNCM](TGFNCM.md) | NCM->CODNCM | I |
| Produto / [TGFPRO](TGFPRO.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Produto / [TGFPRO](TGFPRO.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCONSIG->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | ParceiroFabricante / [TGFPAR](TGFPAR.md) | CODFAB->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | I |
| Produto / [TGFPRO](TGFPRO.md) | PlanoContaConta4 /  | CODCTACTB4->CODCTACTB | I |
| Produto / [TGFPRO](TGFPRO.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| Produto / [TGFPRO](TGFPRO.md) | PlanoContaEmpresa2 /  | CODCTACTB2->CODCTACTB | I |
| Produto / [TGFPRO](TGFPRO.md) | PlanoContaEmpresa3 /  | CODCTACTB3->CODCTACTB | I |
| Produto / [TGFPRO](TGFPRO.md) | PrincipioAtivo / [TGFPAT](TGFPAT.md) | CODPAT->CODPAT | I |
| Produto / [TGFPRO](TGFPRO.md) | Produto3 / [TGFPRO](TGFPRO.md) | CODPRODSUBST->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoAlternativo / [TGFPAL](TGFPAL.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoApontamento1 / [TGFPRO](TGFPRO.md) | CODPRODAGRUPAPT->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoApontamento2 / [TGFPRO](TGFPRO.md) | CODPRODAGRUPAPTENC->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoData / [TGFPRD](TGFPRD.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoEndereco /  | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoModeloVeiculo / [TGFPLM](TGFPLM.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | ProdutoSubstitutoKit / [TGFPRO](TGFPRO.md) | CODPRODSUBKIT->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Produto / [TGFPRO](TGFPRO.md) | RastreamentoEmpresa / [TGFRASTEMP](TGFRASTEMP.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | RecursosPA /  | CODPROD->CODPRODPA | I |
| Produto / [TGFPRO](TGFPRO.md) | RegimeEspecialIcms / [TGFREA](TGFREA.md) | NUMITEMREA->NUMITEMREA | I |
| Produto / [TGFPRO](TGFPRO.md) | RegraArmazenagem /  | CODRFA->CODRFA | I |
| Produto / [TGFPRO](TGFPRO.md) | RelacaoGenericoEspecifico / [TGFGXE](TGFGXE.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | RelatorioEtiquetaSeparacao / [TSIRFE](TSIRFE.md) | MODETIQSEPWMS->NURFE | I |
| Produto / [TGFPRO](TGFPRO.md) | SazonalidadeProduto / [TGFSAZ](TGFSAZ.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Servico / [TGFPRO](TGFPRO.md) | CODGAR->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | SubCategoriaMedicamento / [TGFSCA](TGFSCA.md) | CODCAT->CODCAT; SEQSCA->SEQUENCIA | I |
| Produto / [TGFPRO](TGFPRO.md) | SubClasseTerapeutica / [TGFSTE](TGFSTE.md) | CODTER->CODTER; SEQSTE->SEQUENCIA | I |
| Produto / [TGFPRO](TGFPRO.md) | SubClassificacaoMedicamento / [TGFSPR](TGFSPR.md) | CODCPR->CODCPR; SEQSPR->SEQUENCIA | I |
| Produto / [TGFPRO](TGFPRO.md) | TabelaCPRB / [TGFCPRB](TGFCPRB.md) | CODCPRB->CODCPRB | I |
| Produto / [TGFPRO](TGFPRO.md) | TaxaDepreciacao / [TCITAX](TCITAX.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| Produto / [TGFPRO](TGFPRO.md) | UnidadeCompra / [TGFVOL](TGFVOL.md) | CODVOLCOMPRA->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | UnidadeMinArmazenagemWMS /  | UNIDMINARMAZ->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | UnidadeMovArmazenagemProduto / [TGFPUMA](TGFPUMA.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | UnidadeSeparacaoPesoVariavel / [TGFVOL](TGFVOL.md) | CODVOLPESOVAR->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VendaCasada / [TGFVCS](TGFVCS.md) | CODPROD->CODORIG | I |
| Produto / [TGFPRO](TGFPRO.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| Produto / [TGFPRO](TGFPRO.md) | VinculoAmostrasProduto / [TGFAMP](TGFAMP.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeAlternativo / [TGFVOA](TGFVOA.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeFETHAB / [TGFVOL](TGFVOL.md) | CODVOLFETHAB->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeKanban / [TGFVOL](TGFVOL.md) | CODVOLKANBAN->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeResumoEntrega / [TGFVOL](TGFVOL.md) | CODVOLRES->CODVOL | I |
| ProdutoAliquotaEspecialICMS / [TGFAEPRO](TGFAEPRO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoAlternativo / [TGFPAL](TGFPAL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODALT->CODPROD | I |
| ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoData / [TGFPRD](TGFPRD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoData / [TGFPRD](TGFPRD.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoDivergente / [TGFPDD](TGFPDD.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoModeloVeiculo / [TGFPLM](TGFPLM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoNegociacao / [TCSPRN](TCSPRN.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoPeca / [TGFPPP](TGFPPP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoPorNegociacaoPreVenda / [TCSPXT](TCSPXT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoProjeto / [TCSFPR](TCSFPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServico / [TCSSER](TCSSER.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Recibo / [TGFREC](TGFREC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegistroAvaria / [TGFAVR](TGFAVR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegistroVolume / [TGFVOR](TGFVOR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| RelacaoGenericoEspecifico / [TGFGXE](TGFGXE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODESP->CODPROD | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| saldoImobilizado / [TCISAL](TCISAL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| SerieProduto / [TGFSER](TGFSER.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| SerieProdutoConferencia / [TGFCONSER](TGFCONSER.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODPROD->CODIGO | I |
| Servico / [TGFPRO](TGFPRO.md) | AliquotaISS / [TGFISS](TGFISS.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | CadastroNaturezaRendimentos /  | CODNATREND->CODNATREND | I |
| Servico / [TGFPRO](TGFPRO.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Servico / [TGFPRO](TGFPRO.md) | ClassificacaoItemTelecomunicao / [TGFCIT](TGFCIT.md) | CODSERVTELECOM->CODSERVTELECOM | I |
| Servico / [TGFPRO](TGFPRO.md) | ClassificacaoOs / [TCSCOS](TCSCOS.md) | CODCOS->CODCOS | I |
| Servico / [TGFPRO](TGFPRO.md) | ClassTributariaNacional / [TGFCLASTRIBNAC](TGFCLASTRIBNAC.md) | CODCLASTRIBNAC->CODTRIBNAC | I |
| Servico / [TGFPRO](TGFPRO.md) | CodigoIndicadorOperacao / [TGFINDOPER](TGFINDOPER.md) | CODINDOPERPROD->CODINDOPER | I |
| Servico / [TGFPRO](TGFPRO.md) | CodigoItemNFCom / [TGFCINFCOM](TGFCINFCOM.md) | CODIGONFCOM->CODIGO | I |
| Servico / [TGFPRO](TGFPRO.md) | ComponenteServico / [TGFCSV](TGFCSV.md) | CODCOMP->CODCOMP | I |
| Servico / [TGFPRO](TGFPRO.md) | ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | DisponibilidadeDiaria / [TGFDID](TGFDID.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORMAPONTA->CODFORM | I |
| Servico / [TGFPRO](TGFPRO.md) | Formula2 / [TSIFOR](TSIFOR.md) | CODFILTRO->CODFORM | I |
| Servico / [TGFPRO](TGFPRO.md) | FormulaPrecificacao / [TGFFOR](TGFFOR.md) | CODFORMPREC->CODFORMPREC | I |
| Servico / [TGFPRO](TGFPRO.md) | GradeProduto / [TGFGRA](TGFGRA.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| Servico / [TGFPRO](TGFPRO.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Servico / [TGFPRO](TGFPRO.md) | MultiplosComponentesServico / [TGFMCS](TGFMCS.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Servico / [TGFPRO](TGFPRO.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| Servico / [TGFPRO](TGFPRO.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | I |
| Servico / [TGFPRO](TGFPRO.md) | PLanoContaConta2 /  | CODCTACTB2->CODCTACTB | I |
| Servico / [TGFPRO](TGFPRO.md) | PlanoContaConta3 /  | CODCTACTB3->CODCTACTB | I |
| Servico / [TGFPRO](TGFPRO.md) | PlanoContaConta4 /  | CODCTACTB4->CODCTACTB | I |
| Servico / [TGFPRO](TGFPRO.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| Servico / [TGFPRO](TGFPRO.md) | ProdutoApontamento1 / [TGFPRO](TGFPRO.md) | CODPRODAGRUPAPT->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ProdutoApontamento2 / [TGFPRO](TGFPRO.md) | CODPRODAGRUPAPTENC->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ProdutoData / [TGFPRD](TGFPRD.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Servico / [TGFPRO](TGFPRO.md) | RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | RelacionamentoServicoStatus / [TCSSES](TCSSES.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | TabelaCPRB / [TGFCPRB](TGFCPRB.md) | CODCPRB->CODCPRB | I |
| Servico / [TGFPRO](TGFPRO.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| Servico / [TGFPRO](TGFPRO.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| Servico / [TGFPRO](TGFPRO.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| ServicoEtapa / [TCSESA](TCSESA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ServicoModEtapa / [TCSMSA](TCSMSA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ServicoModEtapa / [TCSMSA](TCSMSA.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ServicoObjeto / [TAPSEO](TAPSEO.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| ServicoProdutoExecutante / [TGFSEU](TGFSEU.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ServicoProdutoExecutante / [TGFSEU](TGFSEU.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |
| StatusSerie / [TGFSSE](TGFSSE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TaxaDepreciacao / [TCITAX](TCITAX.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TaxaDepreciacaoAjuste / [TCITAXAJ](TCITAXAJ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TaxasEspeciais / [TGFTAX](TGFTAX.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | Servico / [TGFPRO](TGFPRO.md) | IMBCODSERVTXINTER->CODPROD | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimServicoTxAdm / [TGFPRO](TGFPRO.md) | IMBCODSERVTXADM->CODPROD | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | Produto / [TGFPRO](TGFPRO.md) | CNFCODPROD->CODPROD | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TransicaoPorFluxoProcesso / [TCSTFL](TCSTFL.md) | Produto / [TGFPRO](TGFPRO.md) | CODSERVORIG->CODPROD | I |
| TransicaoPorFluxoProcesso / [TCSTFL](TCSTFL.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERVDEST->CODPROD | I |
| UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| VariacaoCustoEstoque / [TGFVCE](TGFVCE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| VendaCasada / [TGFVCS](TGFVCS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODSUG->CODPROD | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ViewPerfilGradeProduto / [VGFGRA](VGFGRA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ViewSerie / [VGFSER](VGFSER.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| VinculoLocacaoBemMovel / [TCOMVLB](TCOMVLB.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
