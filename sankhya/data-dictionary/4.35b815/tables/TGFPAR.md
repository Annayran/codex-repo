# TGFPAR — Parceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 253
- Instâncias DWF: 42
- Relações DWF: 331
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FuncionarioTomador | Funcionário Tomador | S | N |
| GrupoEconomico | Grupo Econômico | S | N |
| Motorista | Motorista do Veículo | S | N |
| Parceiro | Parceiro | S | S |
| Parceiro2 | Parceiro | S | N |
| ParceiroAdministrador | Parc. Administradora | S | N |
| ParceiroAtendido | Parceiro Atendido | S | N |
| ParceiroAtivoPj | Parceiro | S | S |
| ParceiroColeta | Parceiro Coleta CT-e | S | N |
| ParceiroConsignatario | Parceiro Consignatário | S | N |
| ParceiroDescargaMdfe | Parceiro Descarregamento (MDF-e) | S | N |
| ParceiroDestinatario | Parceiro Destinatario CT-e | S | N |
| ParceiroDestino | Parceiro Destino | S | N |
| ParceiroEntrega | Parceiro Entrega CT-e | S | N |
| ParceiroExpedidor | Parceiro Expedidor CT-e | S | N |
| ParceiroF2 | Parceiro F2 | S | N |
| ParceiroFabricante | ParceiroFabricante | S | N |
| ParceiroFornecAprovado | Fornecedor Aprovado | S | N |
| ParceiroFornecedor | Parceiro Fornecedor | S | N |
| ParceiroFornecedorCotacao | Fornecedor | S | N |
| ParceiroIntermed | Parceiro Destino | S | N |
| ParceiroIntermediador | Parc. Intermediador | S | N |
| ParceiroMatriz | Parceiro Matriz | S | N |
| ParceiroMelhorFornec | Melhor Fornecedor | S | N |
| ParceiroOrigem | Parceiro de Origem | S | N |
| ParceiroPagante | Parceiro Pagante | S | N |
| ParceiroPgtoFrete | Parceiro | S | N |
| ParceiroPrestador | Parceiro Prestador utilizado em contratos com SLA | S | N |
| ParceiroRecebedor | Parceiro Recebedor CT-e | S | N |
| ParceiroRedespacho | Parceiro Redespacho | S | N |
| ParceiroRemetente | Parceiro Remetente | S | N |
| ParceiroRemetenteCte | Parceiro Remetente CT-e | S | N |
| ParceiroResponsavel | Parceiro Responsável | S | N |
| ParceiroRetirada | Parceiro Retirada | S | N |
| ParceiroTabPreco | Parceiro Tabela de Preço | N | N |
| ParceiroTransportadora | Parceiro Transportadora NF-e | S | N |
| ParceiroTransportadora2 | Parceiro Transportadora NF-e | S | N |
| Procedencia | Procedencia de pesagem | S | N |
| ResponsavelPagador | Parceiro Responsável pelo Pagamento | S | N |
| TimProprietarioImovel | Proprietários dos imóveis | S | N |
| Transportadora | Transportadora | S | N |
| TransportadoraFinal | Transportadora Final | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENCIA | Agência | S |  |  | N |
| AGRONOMO | Agrônomo | S |  |  | N |
| AGRUPAR | Agrupar Pagamentos na geração p/Banco | S |  |  | N |
| ALIQISSRETSIMPLES | Alíquota ISS Ret/Simples | F |  |  | N |
| ALUNO | Aluno | S |  |  | N |
| APLICLEITRANSP | Aplicar a lei da transparência | S |  |  | N |
| ARREDPRIMEIRAPARC | Ajusta arredondamento na 1ª parcela | S |  |  | N |
| ASSOCIACAODESP | Associação Desportiva | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| BASEPARCELECONECT | Partir de | I |  |  | N |
| BASEPRAZOECONECT | Partir de | I |  |  | N |
| BLOQUEAR | Bloquear venda a prazo | S |  |  | N |
| CAIXAPOSTAL | Caixa Postal | S |  |  | N |
| CALCFETHAB | Calcular FETHAB | S |  |  | N |
| CALCINSS | Calcula FUNRURAL/INSS | S |  |  | N |
| CEI | Cad. Específico do INSS - CEI | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CGC_CPF | CNPJ / CPF | S |  |  | N |
| CHAVEPIX | Chave Pix | S |  |  | N |
| CLASSIFICMS | Classificação ICMS | S |  |  | N |
| CLIENTE | Cliente | S |  |  | N |
| CNAE | CNAE principal do contribuinte | S |  |  | N |
| CODAGE | Agência bancária | S |  |  | N |
| CODANT | Código no SGE | I |  |  | N |
| CODASSESSOR | Assessor do Parceiro | I |  |  | N |
| CODBAI | Bairro | I |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODCONTATOPADCOT | Contato padrão para cotação | I |  |  | N |
| CODCRED | Crédito | I |  |  | N |
| CODCTABCO | Conta bancária parceiro | S |  |  | N |
| CODCTABCOINT | Conta bancária empresa | I |  |  | N |
| CODCTACTB | Conta contábil 1 | I |  |  | N |
| CODCTACTB2 | Conta contábil 2 | I |  |  | N |
| CODCTACTB3 | Conta contábil 3 | I |  |  | N |
| CODCTACTB4 | Conta contábil 4 | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEMPPREF | Empresa Preferencial | I |  |  | N |
| CODEND | Endereço | I |  |  | N |
| CODENQIPIENT | Código Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Código Enq. Legal IPI Saída | I |  |  | N |
| CODGRUPO | Grupo Cobrança | I |  |  | N |
| CODIDENTCONS | Código de identificação do consumidor ou assinante | S |  |  | N |
| CODLOCALPADRAO | Local padrão para produtos | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCGRUECONOMICO | Cód. Grupo Econômico | I |  |  | N |
| CODPARCMATRIZ | Matriz | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODROTA | Rota | I |  |  | N |
| CODTAB | Tabela de Preço | I |  |  | N |
| CODTABST | Tabela c/base p/S.T.na venda | I |  |  | N |
| CODTIPPARC | Perfil Principal | I |  |  | N |
| CODUNIMED | Cód. Unimed | S |  |  | N |
| CODUSU | Cód. Usuário Alteração | I |  |  | N |
| CODUSUCOBR | Cód. Usuário Cobrador | I |  |  | N |
| CODVEND | Vend. Preferencial | I |  |  | N |
| COMOCONHECEU | Como nos conheceu | S |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| CONSIDTOTITENSTRIB | Considerar valor total dos itens mais valor total tributado para tags vTprest e vRec? | S |  |  | N |
| CONSPARCADRCST | Considerar parceiro no registro 1210 e 1220 da ADRC-ST (PR) | S |  |  | N |
| CONTACESSO | Informações adicionais | S |  |  | N |
| CPFPRODRURAL | CPF Prod. Rural | S |  |  | N |
| CSTIPIENT | Código Sit.Trib.IPI Entrada | I |  |  | N |
| CSTIPISAI | Código Sit.Trib.IPI Saída | I |  |  | N |
| CTAADIANT | Conta Adiantamento | S |  |  | N |
| DEDUZIPIBCPISCF | Deduzir valor de IPI da BC do PIS e COFINS | S |  |  | N |
| DESCBONIF | Desconto bonificado | S |  |  | N |
| DESCFIN | % Desc. Financeiro | F |  |  | N |
| DESCONSDESCINSS | Desconsiderar desconto da nota/item na base de cálculo do INSS | S |  |  | N |
| DESCONSIDESCBASE | Desconsiderar desconto da nota/item na base de cálculo dos impostos ISS e IRF | S |  |  | N |
| DESCRROTA | Descrição da Rota | S |  |  | S |
| DESCSTIVA | Considera desconto no cálculo de ST por IVA | S |  |  | N |
| DHCADRF | Data/Hora da Última Consulta ReceitaWS | H |  |  | N |
| DHCADSEFAZ | Data/Hora da Última Consulta SEFAZ | H |  |  | N |
| DIAFECHAECONECT | Dia Fechamento | I |  |  | N |
| DIAPAGTOECONECT | Dia Pagamento | I |  |  | N |
| DIASEM | Dia Visita | I |  |  | N |
| DIASVARPAGTO | Antecipação/Atraso recebimento(em dias) | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| DTBAIXA | Data da baixa do contribuinte | D |  |  | N |
| DTCAD | Data cadastramento | D |  |  | N |
| DTEMISNFEFORN | Data de Inicio para Emissão de NFe | D |  |  | N |
| DTINIATIV | Início da Atividade do Contribuinte | D |  |  | N |
| DTNASC | Data nascimento/Fundação | D |  |  | N |
| DTULTCONTATO | Dt. Último contato | H |  |  | N |
| DTULTNEGOC | Última negociação | H |  |  | N |
| DTULTSIT | Última mod. da situação cadastral | D |  |  | N |
| ECOMPRA | Email p/ pedido de compra | S |  |  | N |
| EMAIL | Email | S |  |  | N |
| EMAILCTE | E-mail específico p/ envio CT-e | S |  |  | N |
| EMAILDANFE | Enviar DANFE por e-mail? | S |  |  | N |
| EMAILNFE | E-mail p/ envio NF-e/NFS-e/CT-e | S |  |  | N |
| EMAILNFSE | E-mail específico p/ envio NFS-e | S |  |  | N |
| EMAILNOTIFENTREGA | E-mail p/ notificação de entrega | S |  |  | N |
| ENQART227 | Enquadro no Art. 227. para cálculo de IPI? | S |  |  | N |
| ENTREGAENDCONTATO | Utiliza endereço de entrega do contato | S |  |  | N |
| ENVIADANFEREDESPACHO | Enviar DANFE/XML por e-mail para Redespacho (Recebedor)? | S |  |  | N |
| ENVIPEDEMAILTOP | Envio email de pedido / nota | S |  |  | N |
| ESTABTRANSP | Estabelecimento para conversão de CFOP | S |  |  | N |
| EVENDA | Email p/ pedido de venda | S |  |  | N |
| EXIGCONTATOENTCAB | Exigir Contato de Entrega no cabeçalho | S |  |  | N |
| EXIGENOMEPARC | Usar razão social no Boleto Rápido | S |  |  | N |
| FAX | Celular/Fax | S |  |  | N |
| FLEX | Usar Créd.Flex? | S |  |  | N |
| FORNECEDOR | Fornecedor | S |  |  | N |
| GRUPOAUTOR | Grupo de autorização | S |  |  | N |
| GRUPOIBSCBS | Grupo IBS/CBS | S |  |  | N |
| GRUPOIS | Grupo IS | S |  |  | N |
| GRUPOPISCOFINS | Grupo PIS/COFINS | I |  |  | N |
| HOMEPAGE | HomePage | S |  |  | N |
| IDENTINSCESTAD | Insc. Estadual / Identidade | S |  |  | N |
| IDESTRANGEIRO | Identificação de Estrangeiro | S |  |  | N |
| IMPAGRUPFIN | Impedir agrupamento de boletos | S |  |  | N |
| IMPLAUDOLOTE | Imprime laudo lote | S |  |  | N |
| INAPLICPRODEPE | Parceiro elegível à inaplicabilidade do PRODEPE? | S |  |  | N |
| INDAQUISICAO | Indicador de Aquisição | S |  |  | N |
| INDCOMERCIALIZACAO | Indicador de Comercialização | S |  |  | N |
| INDCREDCTE | Contribuinte credenciado a emitir CTe | I |  |  | N |
| INDCREDNFE | Contribuinte credenciado a emitir NF-e | I |  |  | N |
| INDNATRET | Natureza da Retenção na Fonte | S |  |  | N |
| INDOPCCP | Tributação Contr.Previdenciária (Prod.Rural) | I |  |  | N |
| INSCESTADNAUF | Insc. Estadual na UF | S |  |  | N |
| INSCMUN | Cad.Mun.Contribuintes | S |  |  | N |
| INTEGRAECONECT | Integração EConect | S |  |  | N |
| INTERVANALISCRED | Intervalo p/ análise de crédito | I |  |  | N |
| IPIINCICMS | IPI incide no cálculo do ICMS | S |  |  | N |
| LATITUDE | Latitude | S |  |  | N |
| LIMCRED | Limite de crédito | F |  |  | N |
| LIMCREDMENSAL | Limite crédito mensal | F |  |  | N |
| LONGITUDE | Longitude | S |  |  | N |
| MEDICO | Médico | S |  |  | N |
| MEIRJ | Micro empresário individual | S |  |  | N |
| MODELONFDES | Modelo da nota fiscal | S |  |  | N |
| MODELONOTACOMPRA | Modelo para Importação de XML Compra | I |  |  | N |
| MOTBLOQ | Motivo de Bloqueio | S |  |  | N |
| MOTNAORETERISSQN | Motivo de não Retenção ISSQN | S |  |  | N |
| MOTORISTA | Motorista | S |  |  | N |
| NATUREZAOPERDES | Tipo de Negócio | S |  |  | N |
| NOMEAGE | Nome da agência | S |  |  | N |
| NOMEPARC | Nome Parceiro | S |  |  | N |
| NUFOP | Finalidade da Operação | I |  |  | N |
| NUMEND | Número | S |  |  | N |
| NUVERSAO | Versão | I |  |  | N |
| OBSERVACOES | Observações | S |  |  | N |
| OPERLOGIST | Operador logístico | S |  |  | N |
| ORGAOPUB | Órgão Público | S |  |  | N |
| ORGPUBLNFSE | Orgão público (NFS-e) | S |  |  | N |
| PARCELAMECONECT | Parcelamentos | S |  |  | N |
| PARCINTER | Parceiro com Interdependência | S |  |  | N |
| PARCSUBSTISS | Substituto Tributário - ISS | S |  |  | N |
| PERCCUSVAR | % de Custo Variável | F |  |  | N |
| PERCDESCESPECIAL | % Desc. Especial | F |  |  | N |
| PERCJURO | % de juro (diário) | F |  |  | N |
| PERCMULTA | % de multa | F |  |  | N |
| PERCREDINSS | % a ser subtraído da Alíquota do FUNRURAL/INSS | F |  |  | N |
| PERFILECONECT | Perfil | S |  |  | N |
| POTENCIALNEG | Potencial de compra | F |  |  | N |
| PRAZOCONTATO | Prazo p/ contato | I |  |  | N |
| PRAZOPAG | Prazo médio pagamento | I |  |  | N |
| PRAZOPARCECONECT | Prazos | S |  |  | N |
| PREDUTORGOV | % Redução Alíquota (Gov.) | F |  |  | N |
| PRODUTORTEMNF | Produtor tem NF | S |  |  | N |
| PROFESSOR | Professor | S |  |  | N |
| PROVACRESC | Provisão acréscimo | F |  |  | N |
| PROVACRESCCAC | Provisão do carrinho | F |  |  | N |
| QTDMAXPEDCPA | Qtd máx itens por pedido | I |  |  | N |
| QTDMAXTITVENCIDOS | Qtd. máx. de títulos vencidos | I |  |  | N |
| RAMAL | Ramal | I |  |  | N |
| RAZAOSOCIAL | Razão social | S |  |  | N |
| REDE | Nome da Rede na operação com TEF | S |  |  | N |
| REGAPUR | Regime de apuração | S |  |  | N |
| REGIMEESPTRIBISS | Regime esp. tributação ISS (NFS-e) | S |  |  | N |
| REGISTROANS | Registro ANS | I |  |  | N |
| RETEMCOFINS | Retém COFINS | S |  |  | N |
| RETEMCSL | Retém CSL | S |  |  | N |
| RETEMINSS | Calcula IRF | S |  |  | N |
| RETEMISS | Retém ISS | S |  |  | N |
| RETEMPIS | Retém PIS | S |  |  | N |
| RETSTVENDA | Retirar ST do preço de venda do item | S |  |  | N |
| SALDODISP | Saldo Disponível | F |  |  | N |
| SALDODISPCAC | Saldo disponivel do carrinho | F |  |  | N |
| SELECIONADO | Seleção | S |  |  | N |
| SENHAECONECT | Senha | S |  |  | N |
| SERIENFDES | Série da Nota Fiscal | S |  |  | N |
| SEXO | Sexo | S |  |  | N |
| SIMPLES | Optante pelo Simples Nacional? | S |  |  | N |
| SITCADRF | Situação Cadastral ReceitaWS | I |  |  | N |
| SITCADSEFAZ | Situação Cadastral SEFAZ | I |  |  | N |
| SITCCF | Situação no CCF | S |  |  | N |
| SITESPECIALRESP | Situação Especial de Responsabilidade | S |  |  | N |
| SITRECEITA | Situação na Receita Federal | S |  |  | N |
| SITSINTEGRA | Situação no Sintegra | S |  |  | N |
| SITUACAO | Situação | S |  |  | N |
| STATUSEDZ | Status envio EDZ | S |  |  | N |
| TARE | Tare | S |  |  | N |
| TELEFONE | Telefone | S |  |  | N |
| TEMIPI | Tem IPI | S |  |  | N |
| TERMACORD | Tem Termo de Acordo p/ CT-e? | S |  |  | N |
| TIMAINVESTIR | Investimento | F |  |  | N |
| TIMBAIRROCOMPRA | Bairro da Compra | I |  |  | N |
| TIMCARTORIO | Cartório | I |  |  | N |
| TIMCARTREGIMEBENSV | Regime de Casamento | S |  |  | N |
| TIMCOMPRADOR | Comprador | S |  |  | N |
| TIMCORRESPBANCARIO | Corr. Bancário | S |  |  | N |
| TIMCORRETOR | Corretor | S |  |  | N |
| TIMDATACI | Dt. Cart. Identidade | D |  |  | N |
| TIMESTADOCIVIL | Estado Civil | S |  |  | N |
| TIMFAIXASALARIAL | Faixa Salarial | S |  |  | N |
| TIMFIADOR | Fiador | S |  |  | N |
| TIMINQUILINO | Inquilino | S |  |  | N |
| TIMMAE | Mãe | S |  |  | N |
| TIMNACIONALIDAD | Nacionalidade | I |  |  | N |
| TIMORGAO | Orgão | S |  |  | N |
| TIMOUTTELS | Outras Informações de Contato | S |  |  | N |
| TIMPAI | Pai | S |  |  | N |
| TIMPROFISSAO | Prof. / Ramo Atividade | I |  |  | N |
| TIMPROPRIETA | Proprietário | S |  |  | N |
| TIMPROPVENDA | Prop. Venda | S |  |  | N |
| TIMQUERCOMPRAR | Quer Comprar | S |  |  | N |
| TIMREFERENCIA01 | Referência 01 | S |  |  | N |
| TIMREFERENCIA02 | Referência 02 | S |  |  | N |
| TIMSENHADESC | Conf. Senha | S |  |  | N |
| TIMSENHASITE | Senha do Site | S |  |  | N |
| TIMTELEFONE01 | Telefone 01 | S |  |  | N |
| TIMTELEFONE02 | Telefone 02 | S |  |  | N |
| TIMTIPOMORADIA | Tipo de Moradia | S |  |  | N |
| TIPANEXONFE | Tipo do anexo | S |  |  | N |
| TIPCLIENTESERVCOM | Tipo Cliente de Serviços de Comunicação | I |  |  | N |
| TIPGERBOLCENT | Geração de boleto nas centrais | S |  |  | N |
| TIPJURO | Tipo de juro | S |  |  | N |
| TIPLOTACAO | Tipo de Lotação | S |  |  | N |
| TIPOFATUR | Tipo de faturamento | S |  |  | N |
| TIPOGERBOLETO | Tipo geração boleto | S |  |  | N |
| TIPOIMUNIDADEISS | Tipo de Imunidade ISS | I |  |  | N |
| TIPOSPARC | Tipo | S |  |  | S |
| TIPPESSOA | Tipo de pessoa | S |  |  | N |
| TIPTRANSP | Tipo de Transportador | S |  |  | N |
| TOLERINADIMP | Tolerância p/ inadimplência | I |  |  | N |
| TPCOMPRAGOV | Tipo de Ente Governamental | S |  |  | N |
| TRANSPORTADORA | Transportadora | S |  |  | N |
| TRANSPPROPRIA | Transportadora própria | S |  |  | N |
| TRUNCPARCELA | Truncar valores no parcelamento | S |  |  | N |
| UNIDIMPORT | Unidade para considerar na importação | I |  |  | N |
| USATABCRFORN | Usa Tabela p/ compra por CR? | S |  |  | N |
| USUARIO | Usuário | S |  |  | N |
| UTILIZANUCADPARC | Utilizar código de identificação do consumidor ou assinante? | S |  |  | N |
| VALDESCGRDCAR | Validar Desc. Grande Carga? | S |  |  | N |
| VENDAMIN | Venda Mínima | F |  |  | N |
| VENDEDOR | Vendedor | S |  |  | N |
| VLRLIQITEMNFCE | Considera valor líquido do item na NFC-e (geração do XML e imp.Danfe) | S |  |  | N |
| VLRLIQITEMNFE | Considera valor líquido do item na NF-e (geração do XML e imp.Danfe) | S |  |  | N |
| VLRMINPEDCPA | Vlr mín por pedido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendaFixa / [TCSAGF](TCSAGF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODIGO->CODPARC | I |
| AliquotaISSParceiro / [TGFAIP](TGFAIP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ArquivoImportacaoPedido / [TGFAIMPPED](TGFAIMPPED.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| BaseStMediaVendaPar / [TGFSTMVP](TGFSTMVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| BoletoRapido / [TGFPJB](TGFPJB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroConsignatario / [TGFPAR](TGFPAR.md) | CODPARCCONSIGNATARIO->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroDescargaMdfe / [TGFPAR](TGFPAR.md) | CODPARCDESCARGAMDFE->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroIntermed / [TGFPAR](TGFPAR.md) | CODINTERM->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroRedespacho / [TGFPAR](TGFPAR.md) | CODPARCREDESPACHO->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroRemetente / [TGFPAR](TGFPAR.md) | CODPARCREMETENTE->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ParceiroRetirada / [TGFPAR](TGFPAR.md) | CODPARCRETIRADA->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TransportadoraFinal / [TGFPAR](TGFPAR.md) | CODPARCTRANSPFINAL->CODPARC | L |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroConsignatario / [TGFPAR](TGFPAR.md) | CODPARCCONSIGNATARIO->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroRedespacho / [TGFPAR](TGFPAR.md) | CODPARCREDESPACHO->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | ParceiroRemetente / [TGFPAR](TGFPAR.md) | CODPARCREMETENTE->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFAT->CODPARC | I |
| CabecalhoPropostaComercial / [TGICAB](TGICAB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CadastroSefaz / [TGFCSZ](TGFCSZ.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CartaoCredito / [TGFCCR](TGFCCR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CentroResultado / [TSICUS](TSICUS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CentroResultado / [TSICUS](TSICUS.md) | ParceiroResponsavel / [TGFPAR](TGFPAR.md) | CODPARCRESP->CODPARC | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Cidade / [TSICID](TSICID.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCNFSE->CODPARC | I |
| ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODCLIENTE->CODPARC | I |
| CodigoIdentOperacaoTransporte / [TGFCIOT](TGFCIOT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCIOT->CODPARC | I |
| ColetaEntregaPorNota / [TGFNCE](TGFNCE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ComercialProduto / [TCSCPR](TCSCPR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODFORN->CODPARC | I |
| Comissao / [TCSCOM](TCSCOM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CompradoresCivil / [TCCCOM](TCCCOM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Contato / [TGFCTT](TGFCTT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Contato / [TGFCTT](TGFCTT.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | TIMPROCURADOR->CODPARC | I |
| ContratanteMDFE / [TGFCONTR](TGFCONTR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCONTR->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | ParceiroAtendido / [TGFPAR](TGFPAR.md) | CODPARCSEC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | ParceiroPrestador / [TGFPAR](TGFPAR.md) | CODPARCPREST->CODPARC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | ResponsavelPagador / [TGFPAR](TGFPAR.md) | RESPPAGAR->CODPARC | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFIN->CODPARC | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | Procedencia / [TGFPAR](TGFPAR.md) | CODPROC->CODPARC | I |
| ControleAlteracaoBanco / [TCSALT](TCSALT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ControleEntrega / [TGFVIS](TGFVIS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ControleMultimodal / [TGFCMN](TGFCMN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTO->CODPARC | I |
| CorretorCivil / [TCCCOR](TCCCOR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Desconto / [TGFDES](TGFDES.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DescontoCotacao / [TGFITD](TGFITD.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | ParceiroMatriz / [TGFPAR](TGFPAR.md) | CODPARCMATRIZ->CODPARC | I |
| DescricaoNaturezaParceiro / [TGFDNP](TGFDNP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DetalheReajusteContrato / [TGFDPR](TGFDPR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODEMPDEST->CODPARC | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODEMPDEST->CODPARC | I |
| Diario / [TCADIA](TCADIA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DocumentoAnterior / [TGFDANT](TGFDANT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Empresa / [TSIEMP](TSIEMP.md) | FuncionarioTomador / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCTB->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARCNFCE->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ParceiroAtivoPj / [TGFPAR](TGFPAR.md) | CODPARCIPI->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ParceiroEntrega / [TGFPAR](TGFPAR.md) | RESPENTREINF->CODPARC | I |
| EmpresaParceiroInterdependente / [TGFPEIN](TGFPEIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EmpresaSoma / [TSIEMP](TSIEMP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Estoque / [TGFEST](TGFEST.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| FaturaCartao / [TGFFTC](TGFFTC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| GiroProduto / [TGFGIR](TGFGIR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| GrupoEconomicoContrato / [TCSGEC](TCSGEC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroColeta / [TGFPAR](TGFPAR.md) | CODPARCCOLETACTE->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroDestinatario / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroEntrega / [TGFPAR](TGFPAR.md) | CODPARCENTREGACTE->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroExpedidor / [TGFPAR](TGFPAR.md) | CODPARCEXPED->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroRecebedor / [TGFPAR](TGFPAR.md) | CODPARCRECEB->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroRemetenteCte / [TGFPAR](TGFPAR.md) | CODPARCREMET->CODPARC | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ParceiroTransportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| InformacaoCreditoParceiro / [TGCCAL](TGCCAL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| InssReferencia / [TGFINSSREF](TGFINSSREF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| IpiSuspenso / [TGFISU](TGFISU.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItemCotacao / [TGFITC](TGFITC.md) | ParceiroFornecAprovado / [TGFPAR](TGFPAR.md) | FORNECAPROVADO->CODPARC | I |
| ItemCotacao / [TGFITC](TGFITC.md) | ParceiroMelhorFornec / [TGFPAR](TGFPAR.md) | MELHORFORNEC->CODPARC | I |
| ItemCotacaoParceiro / [TGFITC_COT](TGFITC_COT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItemExecucaoServico / [TGFIES](TGFIES.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItemFaturaCartao / [TGFIFC](TGFIFC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCLOJISTA->CODPARC | I |
| ItemKitPropostaComercial / [TGIITE](TGIITE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ItemRegras / [TGFITR](TGFITR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODINSTPRINC->CODPARC | I |
| ItemRegras / [TGFITR](TGFITR.md) | ParceiroF2 / [TGFPAR](TGFPAR.md) | CODINSTSECFIN->CODPARC | I |
| ItemRegras / [TGFITR](TGFITR.md) | ParceiroMatriz / [TGFPAR](TGFPAR.md) | CODINSTSECINI->CODPARC | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | Procedencia / [TGFPAR](TGFPAR.md) | CODPROC->CODPARC | I |
| ItensPrecificacao / [TCACPI](TCACPI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| KitPropostaComercial / [TGIKIT](TGIKIT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LimiteCredito / [TGCPAR](TGCPAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LocalFinanceiro / [TGFLOC](TGFLOC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LocalRetirada / [TGFLCR](TGFLCR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCRETIRADA->CODPARC | I |
| LogFaturaCartao / [TSILOGFTC](TSILOGFTC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LoteFDIC / [TGFFDIC](TGFFDIC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | ParceiroPgtoFrete / [TGFPAR](TGFPAR.md) | CODPARCINTPGFRE->CODPARC | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODALU->CODPARC; CODPROF->CODPARC | I |
| MetaAtual / [TGMMET](TGMMET.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MotoristaCTe / [TGFMOT](TGFMOT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MotoristaMDF / [TGFMME](TGFMME.md) | Motorista / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MovimentoMensalSintetico / [TGFMMS](TGFMMS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NotaCancelada / [TGFCAN](TGFCAN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NotaPendenteAutorizacao / [TGFNPA](TGFNPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Motorista / [TGFPAR](TGFPAR.md) | CODPARCMOTORISTA->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | CODPARCORIG->CODPARC | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| OrdemDespacho / [TGFODP](TGFODP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | ParceiroAtendido / [TGFPAR](TGFPAR.md) | CODPARCATEND->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODPARC->CODIGO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Assessor / [TGFVEN](TGFVEN.md) | CODASSESSOR->CODVEND | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Parceiro / [TGFPAR](TGFPAR.md) | BairroRecebimento / [TSIBAI](TSIBAI.md) | TIMBAIRROCOMPRA->CODBAI | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | MODELONOTACOMPRA->NUNOTA | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ComplementoParc / [TGFCPL](TGFCPL.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Contato / [TGFCTT](TGFCTT.md) | CODPARC->CODPARC | L |
| Parceiro / [TGFPAR](TGFPAR.md) | ContatoEntrega / [TGFCTT](TGFCTT.md) | CODPARC->CODPARC; CODCONTATOPADCOT->CODCONTATO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ConvenioContaParceiro / [TGFCPC](TGFCPC.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | CreditoPorParceiro / [TGFLCP](TGFLCP.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | DependenteIR / [TGFDPP](TGFDPP.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Parceiro / [TGFPAR](TGFPAR.md) | EmpresaParceiroInterdependente / [TGFPEIN](TGFPEIN.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | EmpresaPreferencial / [TSIEMP](TSIEMP.md) | CODEMPPREF->CODEMP | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Parceiro / [TGFPAR](TGFPAR.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |
| Parceiro / [TGFPAR](TGFPAR.md) | GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | CODCRED->CODCRED | I |
| Parceiro / [TGFPAR](TGFPAR.md) | GrupoCobranca / [TGFGCB](TGFGCB.md) | CODGRUPO->CODGRUPO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | LimiteCredito / [TGCPAR](TGCPAR.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALPADRAO->CODLOCAL | I |
| Parceiro / [TGFPAR](TGFPAR.md) | LocalOperacao / [TGFLOCOPER](TGFLOCOPER.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | MarcaParceiro / [TGFPMA](TGFPMA.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | MoedaPortalCotacao / [TGFMOPC](TGFMOPC.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTABST->CODTAB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Pais / [TSIPAI](TSIPAI.md) | TIMNACIONALIDAD->CODPAIS | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCGRUECONOMICO->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroAutorizacaoXml / [TGFPAXN](TGFPAXN.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroMatriz / [TGFPAR](TGFPAR.md) | CODPARCMATRIZ->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroServico / [TGFPAS](TGFPAS.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ParceirosRota / [TGFRTP](TGFRTP.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | PerfilParceiro / [TGFPPA](TGFPPA.md) | CODPARC->CODPARC | L |
| Parceiro / [TGFPAR](TGFPAR.md) | PlanoConta /  | CODCTACTB->CODCTACTB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | PlanoConta2 /  | CODCTACTB2->CODCTACTB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | PlanoConta3 /  | CODCTACTB3->CODCTACTB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | PlanoConta4 /  | CODCTACTB4->CODCTACTB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | PrazoPorParceiro / [TGFPPZ](TGFPPZ.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | RedesTEF / [TSIRTEF](TSIRTEF.md) | REDE->REDE | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Parceiro / [TGFPAR](TGFPAR.md) | RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Servico / [TGFPRO](TGFPRO.md) | CODPARC->CODPARCFORN | I |
| Parceiro / [TGFPAR](TGFPAR.md) | TabelaPrecoCR / [TGFCRT](TGFCRT.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | TimCartorio / [TIMCAR](TIMCAR.md) | TIMCARTORIO->CARCODIGO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | CODPARC->CBLLOCADOR | I |
| Parceiro / [TGFPAR](TGFPAR.md) | TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | TIMPROFISSAO->PRFCODIGO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Parceiro / [TGFPAR](TGFPAR.md) | UsuarioCobranca / [TSIUSU](TSIUSU.md) | CODUSUCOBR->CODUSU | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ParceiroAliquotaEspecialICMS / [TGFAEPAR](TGFAEPAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroAutorizacaoXml / [TGFPAXN](TGFPAXN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARXN->CODPARC | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | ParceiroTabPreco / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroInstalacao / [TCSCPA](TCSCPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | ParceiroF2 / [TGFPAR](TGFPAR.md) | CODPARCB2B->CODPARC | I |
| ParceiroServico / [TGFPAS](TGFPAS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceirosRota / [TGFRTP](TGFRTP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroTabelaProgressiva / [TGFPTP](TGFPTP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroTabPreco / [TGFPAR](TGFPAR.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| ParceiroUniversidade / [INSTITUTION](INSTITUTION.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PerfilContato / [TGFPPA](TGFPPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PerfilParceiro / [TGFPPA](TGFPPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTO->CODPARC | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTO->CODPARC | I |
| Planejamento / [TPQPLA](TPQPLA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARCAPU->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCONSIG->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | ParceiroFabricante / [TGFPAR](TGFPAR.md) | CODFAB->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RedeAvaliadores / [TGPREL](TGPREL.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Regiao / [TSIREG](TSIREG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODREG->CODREG | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | CODPARCORIG->CODPARC | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| RotaCarga / [TSIDIS](TSIDIS.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | CODPARCORIG->CODPARC | I |
| SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCSEG->CODPARC | I |
| SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARCRESPSEG->CODPARC | I |
| Servico / [TGFPRO](TGFPRO.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| TerceiroProducaoRural / [TGFLCDPRTERC](TGFLCDPRTERC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | Parceiro / [TGFPAR](TGFPAR.md) | TVPPESSOA->CODPARC | I |
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | TVPPROCURADOR->CODPARC | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | SACPARCEIRO->CODPARC | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | ParceiroF2 / [TGFPAR](TGFPAR.md) | SACCODPARCPREST->CODPARC | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | Parceiro / [TGFPAR](TGFPAR.md) | IRBPROPIMOVEL->CODPARC | I |
| TimCartorio / [TIMCAR](TIMCAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CARPARCEIRO->CODPARC | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | Parceiro / [TGFPAR](TGFPAR.md) | LVCCLIENTE->CODPARC | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LVCPROCURADOR->CODPARC | I |
| TimCompradorVenda / [TIMCLC](TIMCLC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CLCCOMPRADOR->CODPARC | I |
| TimContaBancariaLocador / [TIMCBL](TIMCBL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CBLLOCADOR->CODPARC | I |
| TimContaControlada / [TIMCIL](TIMCIL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CILPARCRESPONSAVEL->CODPARC | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | Parceiro / [TGFPAR](TGFPAR.md) | FILTROCOMPRADOR->CODPARC | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | FILTROVENDEDOR->CODPARC | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Parceiro / [TGFPAR](TGFPAR.md) | FILTROCAMPRADOR->CODPARC | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | FILTROOLDCOMPRADOR->CODPARC | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CORPARCEIRO->CODPARC | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Parceiro / [TGFPAR](TGFPAR.md) | DTLPARCEIRO->CODPARC | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | DTLPARCDESP->CODPARC | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | Parceiro / [TGFPAR](TGFPAR.md) | EDGCONSTRUTORA->CODPARC | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | FACCODPARC->CODPARC | I |
| TimFinanceiroRepasse / [TGFFIN](TGFFIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | Parceiro / [TGFPAR](TGFPAR.md) | IMBPARCEIRO->CODPARC | I |
| TimImovelConta / [TIMCIM](TIMCIM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CIMPARCDESP->CODPARC | I |
| TimLocacaoFiador / [TIMLCF](TIMLCF.md) | Parceiro / [TGFPAR](TGFPAR.md) | LCFFIADOR->CODPARC | I |
| TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | Parceiro / [TGFPAR](TGFPAR.md) | LGSSEGURADORA->CODPARC | I |
| TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LGSCORRETORA->CODPARC | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | Parceiro / [TGFPAR](TGFPAR.md) | LTCSEGURADORA->CODPARC | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LTCCORRETORA->CODPARC | I |
| TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | Parceiro / [TGFPAR](TGFPAR.md) | SGISEGURADORA->CODPARC | I |
| TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | SGICORRETORA->CODPARC | I |
| TimLocatario / [TIMLCL](TIMLCL.md) | Parceiro / [TGFPAR](TGFPAR.md) | LCLLOCATARIO->CODPARC | I |
| TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | Parceiro / [TGFPAR](TGFPAR.md) | LTPPROPRIETARIO->CODPARC | I |
| TimLoteamentoProprietarios / [TIMLTP](TIMLTP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LTPPROCURADOR->CODPARC | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Parceiro / [TGFPAR](TGFPAR.md) | MFDPARCEIRO->CODPARC | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | MFDCORRESPBANC->CODPARC | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | Parceiro / [TGFPAR](TGFPAR.md) | TNPPESSOA->CODPARC | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | TNPPROCURADOR->CODPARC | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | Parceiro / [TGFPAR](TGFPAR.md) | IMPPROPRIETARIO->CODPARC | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | ParceiroOrigem / [TGFPAR](TGFPAR.md) | IMPPROCURADOR->CODPARC | I |
| TimRegistroAprovSaldo / [TIMAPS](TIMAPS.md) | Parceiro / [TGFPAR](TGFPAR.md) | APSCOMPRADOR->CODPARC | I |
| TimRescisaoLocDetalhamentos / [TIMRCM](TIMRCM.md) | Parceiro / [TGFPAR](TGFPAR.md) | RCMPARCDESP->CODPARC | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Parceiro / [TGFPAR](TGFPAR.md) | TXCPARCEIRO->CODPARC | I |
| TimViewCompradorLoteFiltro / [VIMAPR](VIMAPR.md) | Parceiro / [TGFPAR](TGFPAR.md) | LVCCLIENTE->CODPARC | I |
| TimViewCompradorLoteFiltro / [VIMAPR](VIMAPR.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LVCPROCURADOR->CODPARC | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | Parceiro / [TGFPAR](TGFPAR.md) | VDOPARCEIRO->CODPARC | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODINTERM->CODPARC | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCTEF->CODPARC | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| TransportadoraCalculoFrete / [TGFCFP](TGFCFP.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCSECRECEST->CODPARC | I |
| UnidadeFiscalParceiro / [TGFUNP](TGFUNP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Usuario / [TSIUSU](TSIUSU.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Usuario / [TSIUSU](TSIUSU.md) | ParceiroPrestador / [TGFPAR](TGFPAR.md) | CODPARCPERFIL->CODPARC | I |
| UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ValePedagio / [TGFVALP](TGFVALP.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ValePedagio / [TGFVALP](TGFVALP.md) | ParceiroPagante / [TGFPAR](TGFPAR.md) | CODPARCPAG->CODPARC | I |
| ValePedagioMDFE / [TGFMDFEVALP](TGFMDFEVALP.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ValePedagioMDFE / [TGFMDFEVALP](TGFMDFEVALP.md) | ParceiroPagante / [TGFPAR](TGFPAR.md) | CODPARCPAG->CODPARC | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCPROPANTT->CODPARC | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| VendedorParceiro / [TGFRPV](TGFRPV.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Verbas / [TGFGEV](TGFGEV.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCFAT->CODPARC | I |
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewFinanceiroNotaFrete / [VGFFNF](VGFFNF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| ViewPerfilParceiro / [VGFPPA](VGFPPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ViewRegistroCobrancasPix / [VGFMPX](VGFMPX.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
