# TGFCAB — Entrada e Saída de Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 388
- Instâncias DWF: 27
- Relações DWF: 237
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoFox | Nota venda do Checkout | S | N |
| CabecalhoNota | Nota/Pedido | S | S |
| CabecalhoNota2 | Nota/Pedido | S | N |
| CabecalhoNota3 | Nota/Pedido | S | N |
| CabecalhoNotaBaixa | Nota de Baixa | S | N |
| CabecalhoNotaDevVenda | Nota Devolução Venda | S | N |
| CabecalhoNotaModelo | Cabeçalho Nota Modelo | S | N |
| CabeçalhoNotaModelo2 | CabeçalhoNotaModelo2 | S | N |
| CabecalhoNotaModeloAgenda | Modelo de Notas e Pedidos | S | N |
| CabecalhoNotaModeloSaida | Modelo de nota de saída | S | N |
| CabecalhoNotaModeloSaidaBem | Modelo de nota de saída Bem | S | N |
| CabecalhoNotaRetorno | Nota Retorno | S | N |
| CabecalhoNotaSaida | Nota de Saída | S | N |
| CabecalhoNotaWmsEnt | Nota modelo sobra estoque WMS (Entrada) | S | N |
| CabecalhoNotaWmsSai | Nota modelo perda estoque WMS (Saída) | S | N |
| ModeloEntradaEstoqueComTerc | Modelo Ajuste de Entrada de Estoque com Terceiros | S | N |
| ModeloEntradaEstoqueConsignado | Modelo Ajuste de Entrada de Estoque Consignado | S | N |
| ModeloEntradaEstoqueDeTerc | Modelo Ajuste de Entrada de Estoque de Terceiros | S | N |
| ModeloEntradaReclassificacao | Modelo Ajuste de Entrada Reclassificação (Registro K220) | S | N |
| ModeloEstornoWMSDevCompra | Modelo Estorno WMS - Dev Compra | S | N |
| ModeloRetornoEstoqueWMS | Modelo Retor. de Est. Gerencial WMS(Venda) | S | N |
| ModeloSaidaEstoqueComTerc | Modelo Ajuste de Saída de Estoque com Terceiros | S | N |
| ModeloSaidaEstoqueConsignado | Modelo Ajuste de Saída de Estoque Consignado | S | N |
| ModeloSaidaEstoqueDeTerc | Modelo Ajuste de Saída de Estoque de Terceiros | S | N |
| ModeloSaidaReclassificacao | Modelo Ajuste de Saída Reclassificação (Registro K220) | S | N |
| NotaOrigem | Nota Origem | N | N |
| PedidoOrigem | Pedido de Origem | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGRUPFINNOTA | Agrupar financeiro | S |  |  | N |
| ALIQICMSRET | Alíquota da Retenção | F |  |  | N |
| ALIQIRF | Alíquota IRF | F |  |  | N |
| ANTT | RNTRC | S |  |  | N |
| APROVADO | Aprovado | S |  |  | N |
| BASECOFINS | Base do COFINS | F |  |  | N |
| BASECOFINSST | Base do COFINSST | F |  |  | N |
| BASEICMS | Base do ICMS | F |  |  | N |
| BASEICMSAT | Base de ICMS AT | F |  |  | N |
| BASEICMSFRETE | Base do ICMS do Frete | F |  |  | N |
| BASEICMSSTFRETE | Base do ICMS/ST do Frete | F |  |  | N |
| BASEINSS | Base de INSS | F |  |  | N |
| BASEIPI | Base do IPI | F |  |  | N |
| BASEIRF | Base do IRF | F |  |  | N |
| BASEISS | Base do ISS | F |  |  | N |
| BASEPIS | Base de PIS | F |  |  | N |
| BASEPISST | Base do PISST | F |  |  | N |
| BASESUBSTIT | Base da Substituição | F |  |  | N |
| BASESUBSTSEMRED | Base Substituição Sem Redução | F |  |  | N |
| BCICMSRET | Base de Cálculo da Retenção do ICMS | F |  |  | N |
| CARACAD | Característica adicional do transporte | S |  |  | N |
| CARACSER | Característica adicional do serviço | S |  |  | N |
| CHAVECTE | Chave CTe | S |  |  | N |
| CHAVECTEREF | Chave CT-e referenciada | S |  |  | N |
| CHAVENFCOM | Chave NFCom | S |  |  | N |
| CHAVENFE | Chave NF-e | S |  |  | N |
| CHAVENFEREF | Chave NF-e referenciada | S |  |  | N |
| CHAVENFETERCEIROREF | Chave NF-e de Terceiro referenciada | S |  |  | N |
| CHAVENFSE | Chave NFS-e | S |  |  | N |
| CHAVESINIEF1324 | Chaves de acesso AJUSTE SINIEF 13/24 | S |  |  | N |
| CHVNFEINEREF | Chave NFe Referenciada Inexistente | S |  |  | N |
| CIF_FOB | CIF / FOB | S |  |  | N |
| CIOT | CIOT | I |  |  | N |
| CLASCONS | Cód. Classe de Cons. | S |  |  | N |
| CLASSIFICMS | Cód. Classificação ICMS | S |  |  | N |
| CLIENTEIDPARCERIA | Id do cliente no parceiro | F |  |  | N |
| CMUNFATGER | Código do Município do Fato Gerador | I |  |  | N |
| CNPJADQUIRENTE | CNPJ/CPF do Adquirente | S |  |  | N |
| CODART | Cód. Art | S |  |  | N |
| CODAUTHVM | Cód. de Autorização Venda Mais | S |  |  | S |
| CODCC | CODCC | I |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCFO | CFOP | I |  |  | N |
| CODCHECKOUT | Cód. Checkout | I |  |  | N |
| CODCID | Cidade | I |  |  | N |
| CODCIDDESTINO | Cód. Cidade de Destino | I |  |  | N |
| CODCIDENTREGA | Cód. Cidade de Entrega | I |  |  | N |
| CODCIDFIMCTE | Cód. Cid. Fim CT-e | I |  |  | N |
| CODCIDINICTE | Cód. Cid. Inicio CT-e | I |  |  | N |
| CODCIDORIGEM | Cód. Cidade de Origem | I |  |  | N |
| CODCIDPREST | Cidade de Prestação do Serviço | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODCONTATOENTREGA | Contato de Entrega | I |  |  | N |
| CODDOCA | Doca | I |  |  | N |
| CODDOCARRECAD | Cód. Mod. Documento de arrecadação | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEMPFUNC | Empresa do funcionário | I |  |  | N |
| CODEMPNEGOC | Empresa da negociação | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODGRUPOTENSAO | Cód.Grupo de Tensão | S |  |  | N |
| CODGUF | Código GNRE Unidade Federativa | I |  |  | N |
| CODHISTAC | Histórico | I |  |  | S |
| CODINDOPER | Cód. Ind. Oper. Forn. (IndOper) - TOP | S |  |  | S |
| CODINTERM | Intermediador da Transação | I |  |  | N |
| CODLOCALORIG | Local | I |  |  | S |
| CODLOCOPER | Cod. Local Operação | I |  |  | N |
| CODMAQ | Identifica impressora CF | I |  |  | N |
| CODMODDOCNOTA | Modelo do Documento Fiscal | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODMOTORISTA | Motorista | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODOBRA | Cód. da Obra | S |  |  | N |
| CODOBSPADRAO | Observação padrão | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPARCCONSIGNATARIO | Consignatário (Expedidor) | I |  |  | N |
| CODPARCDESCARGAMDFE | Parceiro Descarregamento (MDF-e) | I |  |  | N |
| CODPARCDEST | Parceiro Destinatário | I |  |  | N |
| CODPARCREDESPACHO | Redespacho (Recebedor) | I |  |  | N |
| CODPARCREMETENTE | Parceiro Remetente | I |  |  | N |
| CODPARCRETIRADA | Cód. Parceiro Retirada | I |  |  | N |
| CODPARCTRANSP | Parceiro Transportadora | I |  |  | N |
| CODPARCTRANSPFINAL | Transportadora Final | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODRASTREAMENTOECT | Cód. Rastreamento ECT | S |  |  | N |
| CODSITE | Site | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPVENDA | Tipo Negociação | I |  |  | N |
| CODTPD | Cód. Tipo de Pedido | I |  |  | N |
| CODUFDESTINO | Cód. UF de Destino | I |  |  | N |
| CODUFENTREGA | Cód. UF de Entrega | I |  |  | N |
| CODUFORIGEM | Cód. UF de Origem | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUCOMPRADOR | Cód. Usuário Comprador | I |  |  | N |
| CODUSUINC | Cód. Usuário Inclusão | I |  |  | N |
| CODVEICULO | Veículo | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CODVENDTEC | Vendedor Técnico | I |  |  | N |
| CODVTP | Cód. Meio de Transporte | I |  |  | N |
| COMGER | Comissão Gerente | F |  |  | N |
| COMISSAO | Comissão | F |  |  | N |
| CONFIRMADA | Confirmada | S |  |  | S |
| CONFIRMNOTAFAT | Confirmar ao faturar | S |  |  | N |
| CONTABILIZADO | Contabilizado? | S |  |  | S |
| CPFCNPJADQUIRENTE | CPF/CNPJ Adquirente | S |  |  | N |
| CTEGLOBAL | CT-e Globalizado | S |  |  | N |
| CTELOTACAO | Lotação | S |  |  | N |
| DANFE | Nro. DANFE | I |  |  | N |
| DESCRHISTAC | Descrição histórico | S |  |  | S |
| DESCTERMACORD | Desc. ref. termo de acordo CT-e | F |  |  | S |
| DHEMISSEPEC | Dt. Emissão Epec | H |  |  | N |
| DHPROTOC | Dt. Protocolo NF-e | H |  |  | N |
| DHPROTOCCTE | Dh. Protocolo CT-e | H |  |  | N |
| DHREGDPEC | Dt. Reg. EPEC | H |  |  | N |
| DHTIPOPER | Dt./Hr. Operação | H |  |  | N |
| DHTIPVENDA | Dt./Hr. Tipo de negociação | H |  |  | N |
| DHVIAGEM | Data e hora da viagem | H |  |  | N |
| DIGITAL | Digital | S |  |  | N |
| DIRECAOVIAG | Direção da viagem | S |  |  | N |
| DISDESAUTIMPEMB | Distribui desconto autom. com impostos embutidos? | S |  |  | N |
| DTADIAM | Adiamento | H |  |  | N |
| DTALTER | Dt. de Alteração | H |  |  | N |
| DTCONTAB | Dt. da Contabilização | H |  |  | N |
| DTDECLARA | Dt. Declaração CT-e de Anulação | H |  |  | N |
| DTENTSAI | Dt. Entrada/Saída | D |  |  | N |
| DTENTSAIINFO | Data Extemporânea | D |  |  | N |
| DTENVIOPMB | Dt. de Envio à PMB | H |  |  | N |
| DTENVSUF | Dt. de Envio Suframa | H |  |  | N |
| DTESCRCONT | Dt. Escrituração Contábil. | D |  |  | N |
| DTFATUR | Dt. do Faturamento | H |  |  | N |
| DTMOV | Dt. do Movimento | D |  |  | N |
| DTNEG | Dt. Neg. | D |  |  | N |
| DTPREVENT | Previsão de entrega | H |  |  | N |
| DTREF | Data de Referência | D |  |  | N |
| DTREF2 | Data de Referência 2 | D |  |  | N |
| DTREF3 | Data de Referência 3 | D |  |  | N |
| DTREMRET | Data de Remessa/Retorno | H |  |  | N |
| DTVAL | Dt. da Validade | H |  |  | N |
| DTVALAUTVENDAMAIS | Dt. Validade Crédito | D |  |  | S |
| EXIGEISSQN | DES-BH Exigibilidade do ISSQN | S |  |  | N |
| FISTEL | FISTEL (Nota Fiscal de Comunicação/Telecomunicação) | S |  |  | N |
| FORMPGTCTE | Forma de Pagamento do CT-e | S |  |  | N |
| FRETEVLRPAGO | Vlr. frete pago | F |  |  | N |
| FUSOEMISSEPEC | Fuso Horário Emissão EPEC | S |  |  | N |
| HISTCONFIG | Gravar o histórico de configuração do parâmetro CONSIMPRETNOTA | S |  |  | N |
| HRADIAM | Hr. Adiamento | T |  |  | N |
| HRENTSAI | Hr. Ent./Saída | H |  |  | N |
| HRMOV | Hr. do Movimento | T |  |  | N |
| ICMSFRETE | ICMS sobre o frete | F |  |  | N |
| ICMSSTFRETE | Vlr. ICMS/ST sobre o frete | F |  |  | N |
| IDBALSA01 | ID da Balsa 1 | S |  |  | N |
| IDBALSA02 | ID da BAlsa 2 | S |  |  | N |
| IDBALSA03 | ID da Balsa 3 | S |  |  | N |
| IDDESCPARCERIA | Id de desconto da parceria | F |  |  | N |
| IDIPROC | Nro. Ordem Produção | I |  |  | N |
| IDNAVIO | Identificação do navio | S |  |  | N |
| IDPONTUACAOPARCERIA | Id de pontuação no parceiro | F |  |  | N |
| INDCONSFINAL | Operação de Uso/Consumo Próprio | S |  |  | N |
| INDDEST | Controle do Indicador de Destinatário | S |  |  | N |
| INDNEGMODAL | Indicador negociável Multimodal | S |  |  | N |
| INDPRESNFCE | Indicador de Presença para NF-e/NFC-e | S |  |  | N |
| INTERMED | Indicador de Intermediador/Marketplace | S |  |  | N |
| IPIEMB | IPI da embalagem | F |  |  | N |
| IRFRETIDO | o INSS é retido? | S |  |  | N |
| IRINNAVIO | IRIN do navio | S |  |  | N |
| ISSRETIDO | ISS Retido na fonte | S |  |  | N |
| KMVEICULO | KM | I |  |  | N |
| LACRES | Lacres | S |  |  | N |
| LATITUDE | Latitude | S |  |  | N |
| LIBCONF | Liberado conferência | S |  |  | N |
| LIBPENDENTE | Liberação Pendente | S |  |  | S |
| LOCALCOLETA | Local de coleta | S |  |  | N |
| LOCALENTREGA | Local de Entrega | S |  |  | N |
| LOCALFINALSERV | Finalização do Serviço | S |  |  | N |
| LOCEMBARQ | Local do Embarque | S |  |  | N |
| LONGITUDE | Longitude | S |  |  | N |
| LOTACAO | Lotação CT-e | S |  |  | N |
| M3 | Metro Cúbico | F |  |  | N |
| M3AENTREGAR | M3 a Entregar | F |  |  | S |
| MARCA | Marca | S |  |  | N |
| MD5MODCOMTEL | MD5MODCOMTEL | S |  |  | N |
| MODELONFDES | DES-BH Modelo NF | S |  |  | N |
| MODENTREGA | Mod. Entrega | S |  |  | N |
| MODRECEBPDVWEB | Modo de recebimento PDV | S |  |  | N |
| MOTNAORETERISSQN | DES-BH Motivo de não Retenção ISSQN | S |  |  | N |
| NATUREZAOPERDES | DES-BH Tipo de Negócio | S |  |  | N |
| NFCOM | NFCom | S |  |  | S |
| NFEDEVRECUSA | NF-e de Devolução via Recusa | S |  |  | N |
| NFSEID | NFSe ID | S |  |  | N |
| NOMEADQUIRENTE | Nome Adquirente | S |  |  | N |
| NOTAEMPENHO | Nota de empenho | S |  |  | N |
| NOTAPORPEDIDOPDV | Nota gerada por pedido | S |  |  | N |
| NOTASCF | Notas do conhecimento de frete | S |  |  | N |
| NROCAIXA | Nro. Caixa | I |  |  | N |
| NROREDZ | Nro. Redução Z | I |  |  | N |
| NUCFR | Cód. cálculo de frete | I |  |  | N |
| NUCONFATUAL | Nro Conferência atual | I |  |  | N |
| NUFOP | Finalidade da Operação | I |  |  | N |
| NULOTECTE | Lote CT-e | I |  |  | N |
| NULOTENFCOM | Lote NFCom | I |  |  | N |
| NULOTENFE | Lote NF-e | I |  |  | N |
| NULOTENFSE | Lote NFS-e | I |  |  | N |
| NUMALEATORIO | Nro. Aleat. NF-e | I |  |  | N |
| NUMALEATORIOCTE | Nro. Aleat. CT-e | I |  |  | N |
| NUMCF | Nro. Conhec. de Frete | I |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUMCOTACAO | Nro. da Cotação | I |  |  | N |
| NUMCSTC | Nro. Característica Sv Tele/Comunicação | I |  |  | N |
| NUMDOCARRECAD | Número Documento de arrecadação | S |  |  | N |
| NUMERACAOVOLUMES | Numeração de Volumes | S |  |  | N |
| NUMGUIA | Número da Guia | I |  |  | N |
| NUMNFSE | Nro. NFS-e | S |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUMOS | Nro. da OS | I |  |  | N |
| NUMPEDIDO2 | Número Pedido | S |  |  | N |
| NUMPROTOC | Nro. Protocolo NF-e | S |  |  | N |
| NUMPROTOCCTE | Nro. Protocolo CT-e | S |  |  | N |
| NUMPROTOCINCTE | Nro. Protocolo Insucesso CT-e | S |  |  | N |
| NUMPROTOCINNFE | Nro. Protocolo Insucesso NF-e | S |  |  | N |
| NUMPROTOCNFCOM | Nro. Protocolo NFCom | S |  |  | N |
| NUMREGDPEC | Nro. Reg. DPEC | S |  |  | N |
| NUMTERMTEL | Nro. Identificação do Terminal Telefônico | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| NUNOTAPEDFRET | Nro. Único Ped. Frete | I |  |  | N |
| NUNOTAREC | Numero da Nota Gerada para o Retorno | I |  |  | N |
| NUNOTASUB | Nro. Unico. Nota Substituída | I |  |  | N |
| NUODP | Ordem de Despacho | I |  |  | N |
| NUPCA | Núm. Planejamento de Compras | I |  |  | N |
| NUPEDFRETE | Nro. Pedido Frete | I |  |  | N |
| NURD8 | Nro. Único do RD8 | I |  |  | N |
| NURECEBIMENTO | Nro. Recebimento | I |  |  | N |
| NUREM | Remessa | I |  |  | N |
| NUSESSAO | Número da Sessão do Aparelho Fiscal | I |  |  | N |
| NUTRANSF | Nro. Transferência | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| OBSERVACAOAC | Observação | S |  |  | S |
| OCCN48 | Ordem de Carga CN48 | I |  |  | N |
| ORDEMCARGA | Ordem de carga | I |  |  | N |
| PEDIDOIMPRESSO | Pedido foi impresso? | S |  |  | S |
| PENDENTE | Pendente | S |  |  | N |
| PERCDESC | Percentual de Desconto | F |  |  | N |
| PERCDESCFOB | Perc. desc. FOB | F |  |  | N |
| PERMALTERCENTRAL | Permite Alteração na Central | S |  |  | N |
| PESO | Peso | F |  |  | N |
| PESOAENTREGAR | Peso a Entregar | F |  |  | S |
| PESOBRUTO | Peso bruto | F |  |  | N |
| PESOBRUTOITENS | Peso bruto dos Itens | F |  |  | S |
| PESOBRUTOMANUAL | Peso bruto manual | S |  |  | N |
| PESOLIQITENS | Peso liq. dos Itens | F |  |  | S |
| PESOLIQUIMANUAL | Peso liquido Manual | S |  |  | N |
| PLACA | Placa | S |  |  | N |
| PREMIACAOESTADUAL | Premiação Estadual | S |  |  | N |
| PRODPRED | Produto Predominante | S |  |  | N |
| PRODUETLOC | Produção por Etapa e Local? | S |  |  | N |
| QTDPRODDISTINTOS | Qtd. Prod. Distintos | I |  |  | S |
| QTDUSU | Quantidade de usuário / login | I |  |  | N |
| QTDVOL | Qtd. volumes | I |  |  | N |
| RATEADO | Rateado | S |  |  | N |
| REBOQUE1 | Reboque 1 | I |  |  | N |
| REBOQUE2 | Reboque 2 | I |  |  | N |
| REBOQUE3 | Reboque 3 | I |  |  | N |
| REGESPTRIBUT | DES-BH Regime Especial de Tributação | S |  |  | N |
| RETGERWMS | Retorno Gerencial de Estoque | S |  |  | N |
| RETORNADOAC | Entregue | S |  |  | S |
| RETORNOEQUIPFISCAL | Retorno Equipamento Fiscal | S |  |  | N |
| SEQCARGA | Sequência da carga | I |  |  | N |
| SERIEGUIA | Série da Guia | S |  |  | N |
| SERIENFDES | DES-BH Série NF | S |  |  | N |
| SERIENFSE | Série NFS-e | S |  |  | N |
| SERIENOTA | Série da nota | S |  |  | N |
| SITESPECIALRESP | DES-BH Situação Especial de Responsabilidade | S |  |  | N |
| SITUACAOCTE | Situação CT-e | S |  |  | N |
| SITUACAONFCOM | Situação NFCom | S |  |  | N |
| SITUACAOWMS | Situação WMS | I |  |  | S |
| SOMDESPADUNFENAC | Somar despesas aduaneiras à NF-e de Nacionalização | I |  |  | N |
| SOMICMSNFENAC | Tratar ICMS em NF-e Nacionalização | I |  |  | N |
| SOMPISCOFNFENAC | Tratar PIS/COFINS em NF-e Nacionalização | I |  |  | N |
| STATUSAUTORIZACAOVM | Status Autorização Venda Mais | S |  |  | S |
| STATUSCFE | Status CF-e | S |  |  | N |
| STATUSCONFERENCIA | Status conferência | S |  |  | S |
| STATUSCTE | Status CT-e | S |  |  | N |
| STATUSNFCOM | Status NFCom | S |  |  | N |
| STATUSNFE | Status NF-e | S |  |  | N |
| STATUSNFSE | Status NFS-e | S |  |  | N |
| STATUSNOTA | Status da Nota | S |  |  | N |
| STATUSVM | Status Venda Mais | S |  |  | S |
| STATUSWMS | Status WMS | S |  |  | S |
| SUMVLRIIOUTNOTA | Tratar II em NF-e Nacionalização | I |  |  | N |
| TERMACORDNOTA | Termo de Acordo  p/ CT-e na Nota | S |  |  | N |
| TIMCODPROD | Serviço | I |  |  | S |
| TIMCONTRATOLTV | Contrato de Venda de Lote | I |  |  | N |
| TIMCONTRATOVENDA | Contrato de Venda de Imóvel | I |  |  | N |
| TIMDESCPROD | Descrição (Serviço da Nota) | S |  |  | S |
| TIMNUFINORIG | Financeiro Repasse | I |  |  | N |
| TIMNUNOTAMOD | Nota Modelo | I |  |  | N |
| TIMORIGEM | Origem | S |  |  | N |
| TIPCLIENTESERVCOM | Tipo Cliente de Serviços de Comunicação | I |  |  | N |
| TIPFRETE | Tipo do frete | S |  |  | N |
| TIPIPIEMB | Tipo do Valor Embalagem | S |  |  | N |
| TIPLIBERACAO | Liberação | S |  |  | S |
| TIPMOV | Tipo de Movimento | S |  |  | N |
| TIPNOTAPMB | Tipo de Nota PMB | S |  |  | N |
| TIPOCTE | Tipo do CT-e | S |  |  | S |
| TIPOGUIA | Tipo da Guia | S |  |  | N |
| TIPOPTAGJNFE | Tipo Operação Veículos Novos | I |  |  | N |
| TIPPROCIMP | Tipo Processo de Importação | S |  |  | N |
| TIPSERVCTE | Tipo de serviço CT-e | I |  |  | S |
| TOTALCUSTOPROD | Custo Total do Produto | F |  |  | N |
| TOTALCUSTOSERV | Custo Total do Serviço | F |  |  | N |
| TOTDISPDESC | Total Disponível p/ Desc. | F |  |  | N |
| TPAMBCTE | Ambiente CT-e | S |  |  | N |
| TPAMBNFCOM | Ambiente NFCom | S |  |  | N |
| TPAMBNFE | Ambiente NF-e | S |  |  | N |
| TPAMBNFSE | Ambiente NFS-e | S |  |  | N |
| TPASSINANTE | Cód.Tipo de Assinante | I |  |  | N |
| TPEMISCTE | Tipo de emissão do CT-e | I |  |  | N |
| TPEMISNFCOM | Emissão NFCom | I |  |  | N |
| TPEMISNFE | Emissão NF-e | I |  |  | N |
| TPEMISNFSE | Emissão NFS-e | I |  |  | N |
| TPFRETAMENTO | Tipo Fretamento | I |  |  | N |
| TPLIGACAO | Cód.Tipo de Ligação | I |  |  | N |
| TPRETISS | Tipo de Retenção do ISS | S |  |  | N |
| TROCO | Troco | F |  |  | N |
| UFADQUIRENTE | UF do Adquirente | S |  |  | N |
| UFEMBARQ | UF Local Embarque | S |  |  | N |
| UFEMISS | UF de emissão | I |  |  | N |
| UFEMISSAO | UF de emissão | S |  |  | N |
| UFVEICULO | UF do veículo | I |  |  | N |
| VALORDESONPISCOFINS | Valor PIS/COFINS Desonerados | F |  |  | N |
| VENCFRETE | Vencimento do frete | D |  |  | N |
| VENCIPI | Vencimento do IPI | H |  |  | N |
| VIATRANSP | Tipo de Transporte | S |  |  | N |
| VLRAFRMM | Valor AFRMM | F |  |  | N |
| VLRCARGAAVERB | Valor da Carga Para Averbação | F |  |  | N |
| VLRCOFINS | Vlr. COFINS | F |  |  | N |
| VLRCOFINSST | Vlr. COFINSST | F |  |  | N |
| VLRCOMPENSACAO | Valor do crédito | F |  |  | S |
| VLRDESCPARCERIA | Valor cupom desconto parceria | F |  |  | N |
| VLRDESCSERV | Total desc. serviços | F |  |  | N |
| VLRDESCTOT | Desconto no total | F |  |  | N |
| VLRDESCTOTITEM | Desconto total por item | F |  |  | N |
| VLRDESCTOTITEMMOE | Desc.Total dos itens em Moeda | F |  |  | N |
| VLRDESTAQUE | Vlr. Destaque | F |  |  | N |
| VLREMB | Vlr. da Embalagem | F |  |  | N |
| VLRFETHAB | Vlr. FETHAB | F |  |  | N |
| VLRFRETE | Vlr. do Frete | F |  |  | N |
| VLRFRETECALC | Vlr. frete calc. | F |  |  | N |
| VLRFRETECPL | Vlr. Frete Complementar | F |  |  | N |
| VLRFRETETOTAL | Vlr. Frete Total | F |  |  | N |
| VLRICMS | Vlr. do ICMS | F |  |  | N |
| VLRICMSAT | Valor de ICMS AT | F |  |  | N |
| VLRICMSDIFALDEST | Valor DIFAL UF Destino | F |  |  | N |
| VLRICMSDIFALREM | Valor DIFAL UF Remet. | F |  |  | N |
| VLRICMSDIFERIDO | Vlr. ICMS Diferido | F |  |  | S |
| VLRICMSEMB | Valor do ICMS da embalagem | F |  |  | N |
| VLRICMSFCP | Valor do ICMS Fundo de Pobreza | F |  |  | N |
| VLRICMSFCPINT | Valor do ICMS FCP Interno | F |  |  | N |
| VLRICMSRET | Valor do ICMS Retido | F |  |  | N |
| VLRICMSSEG | Vlr. do ICMS do seguro | F |  |  | N |
| VLRINDENIZ | Vlr. Indenização | F |  |  | N |
| VLRINDENIZDIST | Vlr. Indenização Distribuído | F |  |  | N |
| VLRINSS | Vlr. de INSS | F |  |  | N |
| VLRIPI | Vlr. do IPI | F |  |  | N |
| VLRIRF | Vlr. do IRF | F |  |  | N |
| VLRISS | Valor do ISS | F |  |  | N |
| VLRJURO | Valor do Juro | F |  |  | N |
| VLRJURODIST | Valor de juro distribuído | F |  |  | N |
| VLRLIQITEMNFE | VLRLIQITEMNFE | S |  |  | N |
| VLRMERCADORIA | Vlr. da Mercadoria | F |  |  | N |
| VLRMOEDA | Vlr. Moeda | F |  |  | N |
| VLRNOTA | Vlr. Nota | F |  |  | N |
| VLROUTROS | Vlr. Outros | F |  |  | N |
| VLRPIS | Vlr. PIS | F |  |  | N |
| VLRPISST | Vlr. PISST | F |  |  | N |
| VLRPRESTAFRMM | Vlr. prest. AFRMM | F |  |  | N |
| VLRREPREDST | Vlr. redução ST | F |  |  | N |
| VLRREPREDTOT | Desconto Redução de Base | F |  |  | N |
| VLRREPREDTOTSEMDESC | Vlr. redução total sem desconto | F |  |  | N |
| VLRREPTERC | Vlr. Repasse a Terceiros (NFS-e) | F |  |  | N |
| VLRROYALT | Vlr. ROYALT | F |  |  | N |
| VLRSEG | Vlr. do Seguro | F |  |  | N |
| VLRSERVICO | Valor do Serviço | F |  |  | N |
| VLRSTEXTRANOTATOT | Valor Total ST Extra Nota | F |  |  | N |
| VLRSTFCPINT | Vlr. ST FCP Interno | F |  |  | N |
| VLRSTFCPINTANT | Vlr. ST FCP Interno Anterior | F |  |  | N |
| VLRSUBST | Vlr. da Substituição | F |  |  | N |
| VLRTOTLIQITEMMOE | Total Líq. Itens em Moeda | F |  |  | N |
| VLRVENDOR | Vlr. do Vendor | F |  |  | N |
| VOLUME | Volume | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcompanhamentoNota / [TGFACT](TGFACT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | CHAVENFE->CHAVENFE; DTENTSAI->DTENTSAI; DTNEG->DTNEG; VLRNOTA->VLRNOTA | I |
| ArquivoNFe / [TGFNFE](TGFNFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| AutoRelacionamentoTgfcab / [TGFCABLIG](TGFCABLIG.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Bilhetagem / [TGFBIL](TGFBIL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAFATUR->NUNOTA | I |
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMCOTACAO->NUMCOTACAO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | AcertoOrdemCargaHist / [TGFACH](TGFACH.md) | CODHISTAC->CODHIST | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | AcompanhamentoNota / [TGFACT](TGFACT.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ArquivoCTe / [TGFNCTE](TGFNCTE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ArquivoNFe / [TGFNFE](TGFNFE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ArquivoNFSe / [TGFNFSE](TGFNFSE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CabecalhoConferencia / [TGFCON2](TGFCON2.md) | NUCONFATUAL->NUCONF | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTASUB->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDFIMCTE->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeFatoGerador / [TSICID](TSICID.md) | CMUNFATGER->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDINICTE->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CidadeTrabalho / [TSICID](TSICID.md) | CODCIDPREST->CODCID | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CODCFO->CODCFO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ColetaEntregaPorNota / [TGFNCE](TGFNCE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ComissaoMultipla / [TGFCCM](TGFCCM.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ComplementosGTVE / [TGFCGTVE](TGFCGTVE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ContainerCTe / [TGFCONT](TGFCONT.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ContatoEntrega / [TGFCTT](TGFCTT.md) | CODCONTATOENTREGA->CODCONTATO; CODPARC->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ControleMultimodal / [TGFCMN](TGFCMN.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | DefensivosAgricolas / [TGFDFAGR](TGFDFAGR.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | DescontoFinanceiro / [TGFDFN](TGFDFN.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | DocumentoAnterior / [TGFDANT](TGFDANT.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | EmpresaFuncionario / [TSIEMP](TSIEMP.md) | CODEMPFUNC->CODEMP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | EmpresaNegociacao / [TSIEMP](TSIEMP.md) | CODEMPNEGOC->CODEMP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Funcionario /  | CODEMPFUNC->CODEMP; CODFUNC->CODFUNC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ImpostosReformaTrib / [TGFREFIMP](TGFREFIMP.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | InformacoesGTVE / [TGFIGTVE](TGFIGTVE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | LacreCTe / [TGFLACR](TGFLACR.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | CODLOCOPER->CODLOCOPER | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | LoteNotaFiscalEletronica / [TGFLNF](TGFLNF.md) | NULOTENFE->NULOTE | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | MotoristaCTe / [TGFMOT](TGFMOT.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | NotaConhecimentoTransporte / [TGFNCT](TGFNCT.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | OrdemDespacho / [TGFODP](TGFODP.md) | NUODP->NUODP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroConsignatario / [TGFPAR](TGFPAR.md) | CODPARCCONSIGNATARIO->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroDescargaMdfe / [TGFPAR](TGFPAR.md) | CODPARCDESCARGAMDFE->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroIntermed / [TGFPAR](TGFPAR.md) | CODINTERM->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroRedespacho / [TGFPAR](TGFPAR.md) | CODPARCREDESPACHO->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroRemetente / [TGFPAR](TGFPAR.md) | CODPARCREMETENTE->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroRetirada / [TGFPAR](TGFPAR.md) | CODPARCRETIRADA->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | SeguroTransporte / [TGFSEG](TGFSEG.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | SimulacaoFormaPagamento / [TGFSFP](TGFSFP.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Site / [TGFSIT](TGFSIT.md) | CODSITE->CODSITE | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | NUCFR->NUCFR | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | TIMCONTRATOVENDA->CLACODIGO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TIMCONTRATOLTV->LTVCODIGO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TipoPedido / [TGFTPD](TGFTPD.md) | CODTPD->CODTPD | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TransportadoraFinal / [TGFPAR](TGFPAR.md) | CODPARCTRANSPFINAL->CODPARC | L |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFVEICULO->CODUF | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeFederativaEmissao / [TSIUFS](TSIUFS.md) | UFEMISS->CODUF | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeMedida / [TGFUNM](TGFUNM.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeTransporte / [TGFUTR](TGFUTR.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ValePedagio / [TGFVALP](TGFVALP.md) | NUNOTA->NUNOTA | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ValorComissaoVendedor / [TGFCOM](TGFCOM.md) | NUNOTA->NUNOTAORIG | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | VeiculoReboque1 / [TGFVEI](TGFVEI.md) | REBOQUE1->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | VeiculoReboque2 / [TGFVEI](TGFVEI.md) | REBOQUE2->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | VeiculoReboque3 / [TGFVEI](TGFVEI.md) | REBOQUE3->CODVEICULO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | L |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Vendedor2 / [TGFVEN](TGFVEN.md) | CODVENDTEC->CODVEND | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ViaTransporte / [TGFVTP](TGFVTP.md) | CODVTP->CODVTP | I |
| CabecalhoNota3 / [TGFCAB](TGFCAB.md) | TipoOperacao3 / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoNotaData / [TGFCAD](TGFCAD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDFIMCTE->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDINICTE->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CidadeTrabalho / [TSICID](TSICID.md) | CODCIDPREST->CODCID | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | EmpresaFuncionario / [TSIEMP](TSIEMP.md) | CODEMPFUNC->CODEMP | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | EmpresaNegociacao / [TSIEMP](TSIEMP.md) | CODEMPNEGOC->CODEMP | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Funcionario /  | CODEMP->CODEMP; CODFUNC->CODFUNC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | OrdemCarga / [TGFORD](TGFORD.md) | ORDEMCARGA->ORDEMCARGA | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroConsignatario / [TGFPAR](TGFPAR.md) | CODPARCCONSIGNATARIO->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroRedespacho / [TGFPAR](TGFPAR.md) | CODPARCREDESPACHO->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroRemetente / [TGFPAR](TGFPAR.md) | CODPARCREMETENTE->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Vendedor2 / [TGFVEN](TGFVEN.md) | CODVENDTEC->CODVEND | I |
| CabecalhoNotaVendaMais / [TGFCABVM](TGFCABVM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| CarimboCabecalhoNota / [TC_TGFCAB](TC_TGFCAB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUCHAVE->NUNOTA | I |
| ColetaEntregaPorNota / [TGFNCE](TGFNCE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ComplementosGTVE / [TGFCGTVE](TGFCGTVE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | MODNOTADEVOLUCAO->NUNOTA | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | CabeçalhoNotaModelo2 / [TGFCAB](TGFCAB.md) | MODNOTAPEDCOMPL->NUNOTA | I |
| ContainerCTe / [TGFCONT](TGFCONT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Contrato / [TCSCON](TCSCON.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAPEDLOC->NUNOTA | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMNOTATRANS->NUNOTA | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Crediario / [TGFCRED](TGFCRED.md) | CabecalhoNotaSaida / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DadoImportadoColetor / [TGFCCX](TGFCCX.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DefensivosAgricolas / [TGFDFAGR](TGFDFAGR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DescontoGrandeCarga / [TGFDGC](TGFDGC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DescontoProgressivo / [TGFIDA](TGFIDA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DespesaInstalacaoBem / [TCIDIB](TCIDIB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTADESP->NUNOTA | I |
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DocumentoAnterior / [TGFDANT](TGFDANT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| DocumentoAnterior / [TGFDANT](TGFDANT.md) | NotaOrigem / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| EmailCotacaoEnviada / [TGFENV](TGFENV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTEST->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModeloAgenda / [TGFCAB](TGFCAB.md) | NOTAMODELODAGENDA->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModeloSaida / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTEST->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModeloSaidaBem / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTBEM->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaWmsEnt / [TGFCAB](TGFCAB.md) | NOTAENTSOBRAWMS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaWmsSai / [TGFCAB](TGFCAB.md) | NOTASAIPERDAWMS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaEstoqueComTerc / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTESTCTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaEstoqueConsignado / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTESTCONS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaEstoqueDeTerc / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTESTDTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaReclassificacao / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTERECLAS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEstornoWMSDevCompra / [TGFCAB](TGFCAB.md) | MODESTCPAWMS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloRetornoEstoqueWMS / [TGFCAB](TGFCAB.md) | CODMODRETESTWMS->NUNOTA | L |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaEstoqueComTerc / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTESTCTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaEstoqueConsignado / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTESTCONS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaEstoqueDeTerc / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTESTDTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaReclassificacao / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTERECLAS->NUNOTA | I |
| EstruturaProducao / [TGFESP](TGFESP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EventoEconf / [TGFECO](TGFECO.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EventoNotaFiscalEletronica / [TGFENFE](TGFENFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ExcecaoFatPedido / [TCSEFP](TCSEFP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| FichaAcompanhamentoDiario / [TGFFAD](TGFFAD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| FreteNotaFinanceiro / [TGFFNF](TGFFNF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| GuiaTransporteValores / [TGFGTV](TGFGTV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| HistCabProcessoAdmJudicial / [TGFHCABPAJ](TGFHCABPAJ.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaBaixa / [TGFCAB](TGFCAB.md) | NUNOTABAIXA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaDevVenda / [TGFCAB](TGFCAB.md) | NUNOTADEVVENDA->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaRetorno / [TGFCAB](TGFCAB.md) | NUNOTADEV->NUNOTA | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | CabecalhoNotaSaida / [TGFCAB](TGFCAB.md) | NUNOTASAIDA->NUNOTA | I |
| ImpostosReformaTrib / [TGFREFIMP](TGFREFIMP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InformacaoNotaExportacao / [TGFEXPN](TGFEXPN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InformacaoNotaExportacaoInd / [TGFEXPC](TGFEXPC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InformacoesGTVE / [TGFIGTVE](TGFIGTVE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InventarioBem / [TCICTE](TCICTE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ItemExecucaoServico / [TGFIES](TGFIES.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAPEDCOMPRA->NUNOTA | I |
| ItemNota / [TGFITE](TGFITE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMNOTATRANS->NUNOTA | I |
| LacreCTe / [TGFLACR](TGFLACR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMNOTATRANS->NUNOTA | I |
| LiberacaoBloqueio / [TGFLIB](TGFLIB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| LoteNotaFiscalEletronica / [TGFLNF](TGFLNF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NULOTE->NULOTENFE | I |
| MaoForno / [TGFNMA](TGFNMA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ModeloImportacaoXML / [TGFMIX](TGFMIX.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ModeloProjeto / [TCSMET](TCSMET.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NOTAMODPREVENDA->NUNOTA | I |
| MotivoSubstituicaoNfse / [TGFSUB](TGFSUB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| MotoristaCTe / [TGFMOT](TGFMOT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| MovimentoFinanciamento / [TGFMFO](TGFMFO.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | PedidoOrigem / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotaCancelada / [TGFCAN](TGFCAN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotaConhecimentoTransporte / [TGFNCT](TGFNCT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotaContingencia / [TGFICNF](TGFICNF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotasDocumentoArrecadacao / [TGFNDA](TGFNDA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotasMDF / [TGFNMDFE](TGFNMDFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| OrdemCarga / [TGFORD](TGFORD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | ORDEMCARGA->ORDEMCARGA | I |
| OrdemDespacho / [TGFODP](TGFODP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUODP->NUODP | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Parceiro / [TGFPAR](TGFPAR.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | MODELONOTACOMPRA->NUNOTA | I |
| PedidoPorEvento / [TGFPEV](TGFPEV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| PedidoProjeto / [TCSFPD](TCSFPD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| PedidosPendentes / [TGFPPD](TGFPPD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | NotaOrigem / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | PedidoOrigem / [TGFCAB](TGFCAB.md) | PEDIDOORIGEM->NUNOTA | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RastreabilidadeContratoAgrupa / [TGFRCA](TGFRCA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RegistroVolume / [TGFVOR](TGFVOR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RejeicaoNfe / [TGFREJNFE](TGFREJNFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CODMODDEST->NUNOTA | I |
| ResumoLoteFaturamento / [TGFRFT](TGFRFT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RomaneioCarga / [TGFROM](TGFROM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAOP->NUNOTA | I |
| SeguroTransporte / [TGFSEG](TGFSEG.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| SerieProduto / [TGFSER](TGFSER.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| SimulacaoFormaPagamento / [TGFSFP](TGFSFP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| StatusPedido / [TGFSTA](TGFSTA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| TimFormaPagamento / [TIMPGT](TIMPGT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| TimMFD / [TIMMFD](TIMMFD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | MFDNUNOTA->NUNOTA | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CNFNUNOTA->NUNOTA | I |
| TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | RCLNOTAFISCAL->NUNOTA | I |
| TipoAtendimento / [TGFTPA](TGFTPA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | MODELO->NUNOTA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | NUNOTAMODELO->NUNOTA | I |
| TipoPedido / [TGFTPD](TGFTPD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | CODMODPED->NUNOTA | I |
| UnidadeCarga / [TGFUCA](TGFUCA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| UnidadeMedida / [TGFUNM](TGFUNM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| UnidadeTransporte / [TGFUTR](TGFUTR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ValePedagio / [TGFVALP](TGFVALP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| ViaTransporte / [TGFVTP](TGFVTP.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CODMODPED->NUNOTA | I |
| VinculoBonificacaoVerba / [TGFVBV](TGFVBV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| VinculoLocacaoBemMovel / [TCOMVLB](TCOMVLB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| XmlEventoReformaTributaria / [TGFXMLRTC](TGFXMLRTC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
