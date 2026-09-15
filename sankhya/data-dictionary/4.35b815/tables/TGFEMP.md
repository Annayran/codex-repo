# TGFEMP — Empresa Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 613
- Instâncias DWF: 2
- Relações DWF: 154
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmpresaFinanceiro | Empresa Financeiro | S | N |
| EmpresaTabPreco | Parceiro Tabela de Preço | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AEDFE | Número de Autorização para Emissão de Documentos Fiscais Eletrônicos (AEDF-e) | S |  |  | N |
| AGRUPASERVFAT | Agrupar serviços faturamento? | S |  |  | N |
| ALIQCFBONIF | Alíquota de COFINS | F |  |  | N |
| ALIQCFCAMB | Alíquota de COFINS | F |  |  | N |
| ALIQCFDESC | Alíquota de COFINS | F |  |  | N |
| ALIQCFJUROS | Alíquota de COFINS | F |  |  | N |
| ALIQCFMULT | Alíquota de COFINS | F |  |  | N |
| ALIQFUNTTEL | Alíquota FUNTTEL | F |  |  | N |
| ALIQFUST | Alíquota FUST | F |  |  | N |
| ALIQPISBONIF | Alíquota de Pis | F |  |  | N |
| ALIQPISCAMB | Alíquota de PIS | F |  |  | N |
| ALIQPISDESC | Alíquota de Pis | F |  |  | N |
| ALIQPISJUROS | Alíquota de Pis | F |  |  | N |
| ALIQPISMULT | Alíquota de Pis | F |  |  | N |
| ALTCONTADOR | Alterou? | S |  |  | N |
| AMBIENTEGNRE | Ambiente p/ GNRE | S |  |  | N |
| AMBIENTEREINF | Ambiente EFD-Reinf | I |  |  | N |
| APIKEYPIXCHECKOUT | Chave da API Pix Checkout | S |  |  | N |
| APLCALCDIFALIQFRT | Aplicar Método para Calcular Diferencial de Alíquota sobre Frete Extra Nota? | S |  |  | N |
| ARQMODBOLETO | Modelo de boleto (e-mail) | S |  |  | N |
| ARREDPISCOFHALFEV | Arredondamento de PIS/COFINS usando HALF EVEN | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| ATUALCBENEFFAT | Atualização do cód. do benefício no faturamento pelo produto | S |  |  | N |
| BAIXAFINPREEMISSNFCE | Baixar financeiros na pré-transmissão da NFC-e? | S |  |  | N |
| BANCOPIXCHECKOUT | Código do banco Pix Checkout | I |  |  | N |
| BLQNFACOMPNFCPEND | Bloquear NF-e de acompanhamento em NFC-e pendente? | S |  |  | N |
| CAEPF | Cód. CAEPF - Cadastro de Atividade Econômica Pessoa Física | S |  |  | N |
| CAFIR | Cód. CAFIR - Cadastro de Imóveis Rurais | S |  |  | N |
| CALCFETHAB | Calcular FETHAB | S |  |  | N |
| CALCFUNRURAL | Calcula FUNRURAL/INSS? | S |  |  | N |
| CALCICMS | Calcula ICMS? | S |  |  | N |
| CALCICMSAT | Calcular ICMS Antecipado Entrada Interestaduais | S |  |  | N |
| CALCICMSSTNAOCONT | Permitir o cálculo do ICMS ST para consumidor final não contribuinte | S |  |  | N |
| CALCIRRF | Calcula IRRF ? | S |  |  | N |
| CALCISS | ISS | S |  |  | N |
| CALCPISCFSFIN | Calcula PIS e Cofins na confirmação do financeiro. | S |  |  | N |
| CALCVENCGNREAPU | Calcula vencimento GNRE por Apuração? | S |  |  | N |
| CALCVLRAQUISBEM | Calcular o valor de aquisição do bem | I |  |  | N |
| CALPERCPIS | Calcula percentual do PIS | I |  |  | S |
| CAMCONVPREF | Campos a serem convertidos conforme manual da prefeitura | S |  |  | N |
| CANCNFCOMFORAPRAZO | Permite cancelamento fora do prazo? | S |  |  | N |
| CCM | Cadastro de contribuinte mobiliário | S |  |  | N |
| CERTIFICADOPIX | Certificado Pix | S |  |  | N |
| CGCPROCURADOR | CNPJ/CPF Procurador | S |  |  | N |
| CHAVEACESSONFSE | Chave de Acesso da NFS-e | S |  |  | N |
| CHAVEDIGITALAGILIBLUE | Chave Digital NFSe | S |  |  | N |
| CHAVEPIXCHECKOUT | Chave PIX | S |  |  | N |
| CLASCONS_AGUA | Cód. Clase de Cons. Agua | S |  |  | N |
| CLASCONS_ENERG | Cód. Classe de Cons. Energia | S |  |  | N |
| CLASCONS_GAS | Cód. Classe de Cons. Gás | S |  |  | N |
| CLIENTIDPIXCHECKOUT | ID do Cliente Pix Checkout | S |  |  | N |
| CLIENTSECRETPIXCHECKOUT | Senha do Cliente Pix Checkout | S |  |  | N |
| CNPJEFRVINC | CNPJ da EFR Vinculada | S |  |  | N |
| CODBCOIPI | Banco | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCENCUSDESP | Centro Resultado desp. | I |  |  | N |
| CODCENCUSIPI | Centro Resultado | I |  |  | N |
| CODCLASSIFESTAB | Cód. de Classificação do Estabelecimento | I |  |  | N |
| CODCONTAFINTECH | Conta Fintech | I |  |  | N |
| CODCONTRINFSE | Código do contribuinte da NFS-e | S |  |  | N |
| CODCTABCOIPAD | Conta bancária padrão | I |  |  | N |
| CODCTABCOIPI | Conta Bancária | I |  |  | N |
| CODCTACTB_1 | Conta contábil 1 | I |  |  | N |
| CODCTACTB_2 | Conta contábil 2 | I |  |  | N |
| CODCTACTB_3 | Conta contábil 3 | I |  |  | N |
| CODCTACTBBONIF | Conta Contábil | I |  |  | N |
| CODCTACTBCAMB | Conta Contábil | I |  |  | N |
| CODCTACTBDESC | Conta Contábil | I |  |  | N |
| CODCTACTBJUROS | Conta Contábil | I |  |  | N |
| CODCTACTBMULT | Conta Contábil | I |  |  | N |
| CODCTAPIXTEF | Conta para Recebimento PIX TEF | I |  |  | N |
| CODEMP | Código | I |  |  | N |
| CODEMPGRUPFRETE | Empresa Agrupamento para Cálculo Frete | I |  |  | N |
| CODEMPIMPOSTO | Empresa modelo para regra fiscal | I |  |  | N |
| CODEMPMATRIZEFD | Empresa Matriz (EFD) | I |  |  | N |
| CODEMPMATRIZGNRE | Empresa certificado (GNRE) | I |  |  | N |
| CODEMPMATRIZNFSE | Empresa certificado (NFS-e) | I |  |  | N |
| CODEMPOC | Empresa p/ Ordem de Carga | I |  |  | N |
| CODEMPORIGMOVFIN | Empresa de origem da movimentação financeira | I |  |  | N |
| CODENDARMINDEF | Cód. End. Armazenamento Indefinido | I |  |  | N |
| CODENDAVARIA | Cód. End. Avaria | I |  |  | N |
| CODENDCROSSDOCK | Endereço CrossDocking | I |  |  | N |
| CODENDDIVERG | Cód. End. Divergência | I |  |  | N |
| CODENDPERDA | Cód. End. Perda | I |  |  | N |
| CODENDRETEXP | Endereço para retorno de expedição | I |  |  | N |
| CODENDSOBRA | Cód. End. Sobra | I |  |  | N |
| CODENDWMS | Endereço p/ armazenagem/separação | I |  |  | N |
| CODENQIPIENT | Código Enq. Legal IPI Entrada | I |  |  | N |
| CODENQIPISAI | Código Enq. Legal IPI Saída | I |  |  | N |
| CODFORDIFALIQ | Fórmula para cálculo diferencial de alíquota | I |  |  | N |
| CODFORMPREC | Fórmula de Custo/Preço | I |  |  | N |
| CODGRUPOTENSAO | Cód.Grupo de Tensão | S |  |  | N |
| CODLANCBCOPAG | Lançamento bancário despesas | I |  |  | N |
| CODLANCBCOREC | Lançamento bancário receitas | I |  |  | N |
| CODLOCALTERC | Local específico para estoque de terceiros | I |  |  | N |
| CODLOJAECONECT | Loja na Integração EConect | I |  |  | N |
| CODMETA | Meta Padrão para PCP | I |  |  | N |
| CODMODDACTE | Modelo DACTE | I |  |  | N |
| CODMODDANFE | Modelo do DANFE | I |  |  | N |
| CODMODDANFECONTINGENCIA | Modelo do DANFE em contingência | I |  |  | N |
| CODMODDANFESIMPLIFNFE | Modelo do DANFE simplificado | I |  |  | N |
| CODMODEMAILNFE | Modelo de e-mail para NF-e | I |  |  | N |
| CODMODNFCECOMPL | Modelo do DANFE da NFC-e completo/contingência | I |  |  | N |
| CODMODNFCESIMPL | Modelo do DANFE da NFC-e simplificado/normal | I |  |  | N |
| CODMODRETESTWMS | Modelo Retor. de Est. Gerencial WMS(Venda) | I |  |  | N |
| CODNATIPI | Natureza Despesa Receitas IPI | I |  |  | N |
| CODPARCCTB | Cód. Parceiro | I |  |  | N |
| CODPARCFINTECH | Código Parceiro Fintech | I |  |  | N |
| CODPARCIPI | Código do Parceiro | I |  |  | N |
| CODPARCNFCE | Parceiro padrão da NFC-e | I |  |  | N |
| CODRECEITAIPI | Código de Receita | S |  |  | N |
| CODRECEITAREINF | Código de Receita para atribuir Agregado/CSRF/PCC | S |  |  | N |
| CODRELMINUTAODP | Minuta de Despacho | I |  |  | N |
| CODRESPRETC180 | Cód.ind. responsável p. retenção do ICMS-ST | S |  |  | N |
| CODSMTP | Conta SMTP | I |  |  | N |
| CODSTPISCFBONIF | Cód. Sit. Tribut. PIS/COFINS Bonificação | S |  |  | N |
| CODSTPISCFCAMB | Cód. Sit. Tribut. PIS/COFINS Var. Cambial | S |  |  | N |
| CODSTPISCFDESC | Cód. Sit. Tribut. PIS/COFINS Descontos | S |  |  | N |
| CODSTPISCFJUROS | Cód. Sit. Tribut. PIS/COFINS Juros | S |  |  | N |
| CODSTPISCFMULT | Cód. Sit. Tribut. PIS/COFINS Multas | S |  |  | N |
| CODTAB | Tabela de preço para venda | I |  |  | N |
| CODTABALT | Tabela de preço alternativa | I |  |  | N |
| CODTABCALC | Tabela de preço padrão | I |  |  | N |
| CODTABCKC | Tabela de preço exclusiva | I |  |  | N |
| CODTIPOPERACOMP | Cód.Tipo Operação Nota de Acomp. de CF | I |  |  | N |
| CODTIPOPERIPI | Tipo Operação | I |  |  | N |
| CODTIPPARC | Cód. perfil | I |  |  | N |
| CODTIPTITIPI | Tipo de Título | I |  |  | N |
| CODTRIB20RED | 20 - Com redução de base de cálculo (Redução da base vai p/) | S |  |  | N |
| CODTRIB30 | 30 - Isenta e não tribut.e c/cobrança por S.T. (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB40ISENT | 40 - Isentas (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB41NAOTRIB | 41 - Não tributadas (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB50SUSP | 50 - Suspensão (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB51DIF | 51 - Diferimento (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB53 | 53 - Tributação monofásica sobre combustíveis com recolhimento diferido | S |  |  | N |
| CODTRIB60STANT | 60 - ICMS cobrado anteriormente por S.T. (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB61 | 61 - Tributação monofásica sobre combustíveis cobrada anteriormente | S |  |  | N |
| CODTRIB70REDST | 70 - C/ redução de base de cálculo e cobrança do ICMS por S.T.(Redução vai p/) | S |  |  | N |
| CODTRIB90CREDDEB | 90 - Outras Com Crédito/Débito (Valor Contábil vai p/) | S |  |  | N |
| CODTRIB90OUT | 90 - Outras (Valor Contábil vai p/) | S |  |  | N |
| CODUSUNFSE | Código do usuário da NFS-e | S |  |  | N |
| COMPIPI | Compensa o IPI? | S |  |  | N |
| COMPIPISEPICM | Compensa o IPI Separando o ICMS? | S |  |  | N |
| COMPLITEMNOTA | Compl. Itens Nota | S |  |  | N |
| COMPLRESTITUICAOICMSST | Complemento/Restituição ICMS/ST - MG | S |  |  | N |
| CONDESCITEM | Considerar desconto no valor da operação para cálculo do Diferencial de Alíquota (ICMS por dentro) | S |  |  | N |
| CONECTORNFSE | Conector NFS-e | S |  |  | N |
| CONESTORIGPROD | Controla estoque por origem de produto | I |  |  | N |
| CONICMSMAJFCPINT | Considerar ICMS e ST majorados com o FCP Interno | S |  |  | N |
| CONSDEVEVTR2050 | Considerar devoluções para geração do evento R-2050? | S |  |  | N |
| CONSDEVNFEREINF | Considerar devoluções de NF-e no R-2055 do EFD-REINF? | S |  |  | N |
| CONSDEVTEREVTR2050 | Considerar devolução de NF-e de Terceiros no R-2050? | S |  |  | N |
| CONSDIFPARCOUTICMS | Considerar o valor diferimento parcial de ICMS (CST 51) para "Outras ICMS" no Livro Fiscal? | S |  |  | N |
| CONSENTRPENDWMS | Considerar armazenagens pendentes no envio para expedição | S |  |  | N |
| CONSIDBENEF | Considerar benefícios ao alterar item da nota? | S |  |  | N |
| CONSIDERARRESCOMPLST | Considerar Operações com Ressarcimento/Complemento de ST | S |  |  | N |
| CONSIDQTCARCDESC | Considerar o campo “Qtd. de caracteres", consid. inf. da Lei da Transparência? | S |  |  | N |
| CONSOUTROSIMP | Considerar Outros Impostos? | S |  |  | N |
| CONSREPICMSBASEPISCOF | Considera Repasse ICMS na BC PIS/COFINS? | S |  |  | N |
| CONSVLRLIQNFSE | Considera valor líquido para NFS-e? | S |  |  | N |
| CONTBAIBEMSUBIMPREC | Contabilização da baixa do bem subtraindo os impostos recuperáveis? | S |  |  | N |
| CONTINGENCIACTE | Envio de contingência | S |  |  | N |
| CONTINGENCIANFCE | Envio em contingência | S |  |  | N |
| CONTINGENCIANFCOM | Envio em contingência | S |  |  | N |
| CONTINGENCIANFE | Envio em contingência | S |  |  | N |
| COPIASDACTE | Número de cópias do DACTE | I |  |  | N |
| COPIASDANFCE | Número de cópias DANFE (NFC-e) | I |  |  | N |
| COPIASDANFE | Número de cópias DANFE | I |  |  | N |
| COPIASDANFECONTING | Número de cópias do DANFE em contingência | I |  |  | N |
| CORAGENDADO | Agendado | I |  |  | N |
| CORATENDIDO | Atendido | I |  |  | N |
| CORBLOQUEADO | Bloqueado | I |  |  | N |
| CORENCAIXADO | Encaixado | I |  |  | N |
| CORFATURADO | Faturado | I |  |  | N |
| CORFIXADO | Fixado | I |  |  | N |
| CORTEFALTAWMS | Cortar no MGE itens em falta no WMS | S |  |  | N |
| CPFCONTADOR | CPF | S |  |  | N |
| CRCCONTADOR | CRC | S |  |  | N |
| CREDCOFINS | Tem Créd.COFINS? | S |  |  | N |
| CREDICMSCOMBUS | Creditar ICMS e ICMS/ST de NFe Compra Combustível (CST 060,061) | S |  |  | N |
| CREDICMSCST60AM | Creditar ICMS e ICMS ST de CT-e com CST 060 - Dec. 19671/99 AM | S |  |  | N |
| CREDICMSREMG | Creditar ICMS e ICMS/ST da NFe Entrada ref. Remessa Garantida (CST 010) | S |  |  | N |
| CREDPIS | Tem Créd.PIS? | S |  |  | N |
| CSTIPIENT | Código Sit.Trib.IPI Entrada | I |  |  | N |
| CSTIPISAI | Código Sit.Trib.IPI Saída | I |  |  | N |
| CSTNREDICMSBCPISCOF | CSTs que não Reduzem o ICMS da BC do PIS e COFINS. | S |  |  | N |
| CTACTBANLREGAPUR | Contas contábeis analíticas para geração dos registros de apuração de PIS/COFINS | S |  |  | N |
| DEDICMSBCPISCOFINS | Deduzir valor de ICMS próprio da compra da BC do PIS e COFINS da Venda? | S |  |  | N |
| DEDUZFCPBCPISCOFINS | Deduzir valor de FCP e FCP ST na BC do PIS e COFINS? | S |  |  | N |
| DEFCSTIPI00 | 00 - Entrada com Recuperação de Crédito | S |  |  | N |
| DEFCSTIPI01 | 01 - Entrada Tributada com Alíquota Zero | S |  |  | N |
| DEFCSTIPI02 | 02 - Entrada Isenta | S |  |  | N |
| DEFCSTIPI03 | 03 - Entrada Não Tributada | S |  |  | N |
| DEFCSTIPI04 | 04 - Entrada Imune | S |  |  | N |
| DEFCSTIPI05 | 05 - Entrada Com Suspensão | S |  |  | N |
| DEFCSTIPI49 | 49 - Outras Entradas | S |  |  | N |
| DEFCSTIPI50 | 50 - Saída Tributada | S |  |  | N |
| DEFCSTIPI51 | 51 - Saída Tribrutável com Alíquota Zero | S |  |  | N |
| DEFCSTIPI52 | 52 - Saída Isenta | S |  |  | N |
| DEFCSTIPI53 | 53 - Saída Não tributada | S |  |  | N |
| DEFCSTIPI54 | 54 - Saída Imune | S |  |  | N |
| DEFCSTIPI55 | 55 - Saída Com Suspensão | S |  |  | N |
| DEFCSTIPI99 | 99 - Outras Saídas | S |  |  | N |
| DENUNCESPCTE | Tem denúncia espontânea? | S |  |  | N |
| DESAPCPRODEPE | Desabilita a aplicação do PRODEPE do Dec. 38.296/2012 sobre as despesas acessórias | S |  |  | N |
| DESCREDDIFCIAP | Desconsiderar Crédito Dif. Alíquota No CIAP? | S |  |  | N |
| DESCRICAO | DescricaoConta | S |  |  | S |
| DESCRNAT | DescricaoNatureza | S |  |  | S |
| DESONERAFOLHACPRB | Desoneração da Folha - CPRB ? | S |  |  | N |
| DESRESTRFCPCST | Desabilita restrição de FCP por CST | S |  |  | N |
| DESRESTRFCPSTCST | Desabilita restrição de FCP/ST por CST | S |  |  | N |
| DESTIPIDEVCOM | Destacar o IPI na tag <vIPIDevol> em devoluções | S |  |  | N |
| DIAVENCIPI | Dia de vencimento | I |  |  | N |
| DIFALIQDESPACESS | Utilizar Fórmula do Diferencial de Alíquotas p/ Despesas Acessórias? | S |  |  | N |
| DIFALIQFCPINT | Considerar alíquota FCP/FECP no cálculo de Diferencial de Alíquota? | S |  |  | N |
| DIRFCPFRESP | Cpf | S |  |  | N |
| DIRFDEPDECJUD | Declarante depositário de crédito decorrente de decisão judicial | S |  |  | N |
| DIRFDEPFUNINV | Declarante de instituição administradora ou intermediadora de fundo ou clube de investimento | S |  |  | N |
| DIRFDHEVENTO | Data do Evento | D |  |  | N |
| DIRFENTIMUNE | Entidade em que a União detém maioria do capital social sujeito a voto | S |  |  | N |
| DIRFNATDECL | Natureza Declarante | S |  |  | N |
| DIRFPGEXT | Declarante de rendimentos pagos a residentes ou domiciliados no exterior | S |  |  | N |
| DIRFPGFUNDPUB | Fundação pública de direito privado | S |  |  | N |
| DIRFPLPRIASS | Plano privado de assistência à saúde – coletivo empresarial | S |  |  | N |
| DIRFSITESP | Situação Especial | S |  |  | N |
| DIRFSOCOST | Sócio ostensivo de sociedade em conta de participação | S |  |  | N |
| DTCERTIFNFEFIM | Data de fim da vigência do certificado digital | H |  |  | N |
| DTCERTIFNFEINI | Data de início da vigência do certificado digital | H |  |  | N |
| DTINICIOKARDEX | Data Início Kardex | H |  |  | N |
| DTOBITO | Data Óbito do Contribuinte | D |  |  | N |
| DTOPCAMB | Data da Operação Receita Variação Cambial(Campo 05) | S |  |  | N |
| DTOPJUROS | Data da Operação Juros (Campo 05) | S |  |  | N |
| DTOPMULTA | Data da Operação Multa (Campo 05) | S |  |  | N |
| DTREFPREPRODFICTREINF | Referência Atual Reinf - Pré-produção - dados fictícios | D |  |  | N |
| DTREFPREPRODREAISREINF | Referência Atual Reinf - Pré-produção - dados reais | D |  |  | N |
| DTREFPREPRODREAISREINFG4000 | Referência Atual Reinf Grupo 4000 - Pré-produção - dados reais | D |  |  | N |
| DTREFPRODREINF | Referência Atual Reinf - Produção | D |  |  | N |
| DTREFPRODREINFG4000 | Referência Atual Reinf Grupo 4000 - Produção | D |  |  | N |
| DTTRANSFFINSLUCR | Data transformação da entidade em fins lucrativos | D |  |  | N |
| DTVALFINREINF | Data Validade Final Reinf | D |  |  | N |
| DTVALINIREINF | Data Validade Inicial Reinf | D |  |  | N |
| EFDCODBCO | Banco | I |  |  | N |
| EFDCODCENCUS | Centro Resultado | I |  |  | N |
| EFDCODCTABCOINT | Conta Bancária | I |  |  | N |
| EFDCODNATDESPRECICMS | Natureza Despesa Receitas ICMS | I |  |  | N |
| EFDCODTIPOPER | Tipo Operação | I |  |  | N |
| EFDCODTIPTIT | Tipo de Título | I |  |  | N |
| EFDDIAVENC | Dia de vencimento | I |  |  | N |
| EFDH010 | Conta p/ inventário (H010) do EFD | S |  |  | N |
| EFDH010_PRTER | Conta p/ inventário Prop. c/ Terc (H010) do EFD | S |  |  | N |
| EFDH010_TER | Conta p/ inventário Terceiro (H010) do EFD | S |  |  | N |
| EFDTIPREENCHNOTA | Tipo Preenchimento Número Doc./Nota | S |  |  | N |
| EMAILCONTADOR | Email | S |  |  | N |
| EMAILNOTFECREINF | E-mail p/ notificação de fechamento do Reinf | S |  |  | N |
| EMITEEXPED | Emite expedição | S |  |  | N |
| EMITNFSENACIONAL | Emitir NFS-e Padrão Nacional | S |  |  | N |
| EMPADMPUBDIR | Empresa de Adm. Pública Direta? | S |  |  | N |
| EMPCENTRALMOVFIN | Empresa centralizadora da movimentação financeira | S |  |  | N |
| EMPDESTINOWMS | Empresa para estoque no WMS | I |  |  | N |
| EMPSOMA | Empresa para agrupar estoque | I |  |  | N |
| ENDECKTINDEF | Endereço de checkout indefinido | I |  |  | N |
| ENDEMOVINDEF | Endereço indefinido para movimentação | I |  |  | N |
| ENTAUTTARMAPA | Entrega Automática de Mapas | S |  |  | N |
| ENTPENBALCAOWMS | Envio ped. balcão considera entradas pendentes | S |  |  | N |
| ENTREGAECD | Entrega a ECD ? | S |  |  | N |
| ENVDETBENSMOVEIS | Enviar detalhamento de bens móveis (NCM) em operações de locação? | S |  |  | N |
| ENVEMAILCONF | Enviar XML por email na confirmação? | S |  |  | N |
| ENVINFISSSOMDEVIDO | Enviar o Valor do ISS e alíquota no XML, apenas quando o ISS for devido a outro município? | S |  |  | N |
| ENVINUTNOTAEXC | Enviar inutilização de numeração na exclusão da nota (NF-e/NFC-e/CT-e) | S |  |  | N |
| ENVIOSINCRONOCTE | Usar modo síncrono para envio do XML? | S |  |  | N |
| ENVIOSINCRONONFCE | Usar modo síncrono para envio do XML? | S |  |  | N |
| ENVIOSINCRONONFE | Usar modo síncrono para envio do XML? | S |  |  | N |
| ENVLEITRANSPDESC | Enviar as inf. da Lei da Transparência na desc. de cada item? | S |  |  | N |
| ENVLEITRANSPTOTOBS | Enviar inf. da Lei da Transparência com o total de impostos, na obs. da nota? | S |  |  | N |
| ENVMDFSINC | Usar modo síncrono para envio do XML | S |  |  | N |
| ENVRESPCONTCTE | Enviar responsável contábil no CT-e? | S |  |  | N |
| ENVRESPCONTNFE | Enviar responsável contábil na NFE? | S |  |  | N |
| ESCRITCOMPRAEMISSPROP | Escriturar compras de emissão própria (Tributação 10 e 70)? | S |  |  | N |
| ESTRNCREDICMSST | Considerar TOP’s de Estorno para Crédito de ICMS/ST | S |  |  | N |
| FILTROALIQICMSSQL | Filtro p/ obtenção da alíquota de ICMS | S |  |  | N |
| FISTEL | FISTEL | S |  |  | N |
| FLEX | Usar Créd. Flex? | S |  |  | N |
| FRAGMENTAESTWMS | Permitir estoque fragmentado | S |  |  | N |
| FRASENFSE | Frase de segurança da NFS-e | S |  |  | N |
| FRETESEPCONSTRANSP | Gravar Frete Separado p/ Conhecimento de Transporte (Modelo 8)? | S |  |  | N |
| FRETESEPSEMPRE | Gravar Frete Separado Sempre? | S |  |  | N |
| GER0220UNDFORN | Gerar registro 0220 utilizando UND enviada pelo Fornecedor | S |  |  | N |
| GERADEDUCAOPF | Gera deduções para Pessoa Física? | S |  |  | N |
| GERAGNRE | Gerar GNRE? | S |  |  | N |
| GERALCDPR | Gera informações para o Livro Caixa Digital Produtor Rural | S |  |  | N |
| GERARLIVROS | Gerar livros? | S |  |  | N |
| GERARPRODLIVENT | Gerar Produção no Livro de Entrada? | S |  |  | N |
| GERARPRODORIGKIT | Gerar Produto Origem de Kit? | S |  |  | N |
| GERARRETENCAO | Gerar retenção (valor da retenção considerado no total da nota) | I |  |  | N |
| GERARTAXFINEMBNFE | Desconsiderar valores de taxas financeiras na geração do XML? | S |  |  | N |
| GERCHAVEREFSIG | Gerar chave referenciada sigilosa? | S |  |  | N |
| GERCIAPCOMPEFD | Gerar CIAP de componentes para SPED | S |  |  | N |
| GERCTECREDPISCOF | Gerar CT-e somente com crédito de PIS/COFINS | S |  |  | N |
| GERLDPERTRI | Gerar Lucros e Dividendos com periodicidade trimestral | S |  |  | N |
| GERLIVICMSEMPSN | Escriturar valor de ICMS de entrada para Empresa Simples? | S |  |  | N |
| GERMULTPLACRES | Gerar múltiplas tags <Lacres> no XML? | S |  |  | N |
| GERNOTAENT | Gerar Notas de Entrada Form. Próprio na Obs. do Livro de Saída? | S |  |  | N |
| GERNOTAENTCANC | Gerar Notas de Entrada Canceladas Form. Próprio na Obs. do Livro de Saída? | S |  |  | N |
| GEROBSFING4000 | Gerar observação do financeiro p/ eventos Grupo 4000? | S |  |  | N |
| GEROBSIPIREGENT | Gerar Observação de IPI no registro de entrada? | S |  |  | N |
| GEROBSNOTAG4000 | Gerar observação da nota p/ eventos Grupo 4000? | S |  |  | N |
| GRAVAROBSNOTA | Gravar Observação da Nota? | S |  |  | N |
| GRAVAROBSPADRAO | Gravar Observação Padrão da Nota? | S |  |  | N |
| GRAVARSERIENOTA | Gravar Série Igual a da Nota? | S |  |  | N |
| HORAFINAL | Hora final | I |  |  | N |
| HORAINICIAL | Hora inicial | I |  |  | N |
| ICMSNORMALDIFICMSSN | Calcular diferencial de ICMS pela alíquota normal para Simples Nacional ? | S |  |  | N |
| ICMSSCTTD | Habilita Alíq. incidência ICMS SC nas TTDs 409, 410 e 411 | S |  |  | N |
| IDENTECT | Identificação Correios | S |  |  | N |
| IDENTECTRASTREIO | Usuário rastreamento | S |  |  | N |
| IDNFSEMS | Identificador da Empresa no Serviço de NFS-e | S |  |  | N |
| IGCERTIFSMTPXML | Ignorar validação do certificado no servidor? | S |  |  | N |
| IGPERMCOMPPRODS | Ignorar permissão para compra de produtos | S |  |  | N |
| IMPCTEEMISPROP | Importar CT-e de emissão própria? | S |  |  | N |
| IMPETIQSEPOC | Imprime etiquetas de separação por OC | S |  |  | N |
| IMPETIQVOLCONF | Imprimir etiquetas de volume na conferência | S |  |  | N |
| IMPETQVOL | Impressão de etiqueta de volumes | I |  |  | N |
| IMPEXPED | Impressora | S |  |  | N |
| IMPNFEEMISSPROP | Importa NF-e de emissão própria? | S |  |  | N |
| IMPRESSORAETQSEP | Impressora padrão da etiqueta de separação | S |  |  | N |
| IMPRESSORAETQVOL | Impressora padrão da Etiqueta de Volume | S |  |  | N |
| INCENTCULT | Tem incentivador cultural? | S |  |  | N |
| INCENTFISCALISSQN | Tem incentivo fiscal ISSQN? | S |  |  | N |
| INDESCRIT | Indicador da apuração das contribuições e créditos | I |  |  | N |
| INDTIPLAYOUTK010 | Indicador de tipo de Layout (Registro K010) | I |  |  | N |
| INDUNIAO | Entidade Vinculada a União | I |  |  | N |
| INIBELOTE | Inibe solicitação lote na conferência pedido/saída | S |  |  | N |
| INIBEREABMAXPICKING | Inibir o reabastecimento máximo do picking | S |  |  | N |
| INIBLOQREGCONF | Inibe bloqueio do endereço no registro de conferência | S |  |  | N |
| INSXMLPORIMPSMTPXML | Inserir XML no Portal de Importação | S |  |  | N |
| INTCONSSMTPXML | Intervalo de Consulta em minutos | I |  |  | N |
| INTEGRARIPIFIN | Integrar IPI a Recolher ao Financeiro | S |  |  | N |
| INTEGRAWMSPROD | Utiliza Integração da Produção x WMS | S |  |  | N |
| INTERVALO | Minutos p/ módulo | I |  |  | N |
| INTFINOBRICMSSTREC | Integrar o ICMS Normal Op. Própria a Recolher ao Financeiro | S |  |  | N |
| IPIINCICMS | IPI incide no ICMS ? | S |  |  | N |
| IPINAOINCID | Valor do IPI quando não incidir na base de ICMS | S |  |  | N |
| IPITRIBUTDEFEMP | Usar Tratamento de Código Tributação de IPI Definido no Cadastro de Empresas? | S |  |  | N |
| ISALIQINTSEXC | Usar a alíquota interna sem exceção para o cálculo do Diferencial de Alíquota | S |  |  | N |
| JOBKEYNDD | JobKey NDDigital | S |  |  | N |
| JOBMDENDDIGITAL | Nome Job NDDigital | S |  |  | N |
| JUSINUTNOTAEXC | Justificativa da inutilização de numeração na exclusão da nota (NF-e/NFC-e/CT-e) | S |  |  | N |
| LAYOUTDANFE | Layout do DANFE | S |  |  | N |
| LIVRO1PARA1TGFITE | Gerar linhas da Nota separadamente no Livro Fiscal? | S |  |  | N |
| LOCALPAD | Local Padrão | I |  |  | N |
| LOCALPADECONECT | Local Padrão EConect | I |  |  | N |
| LOGODANFE | Arquivo logomarca | S |  |  | N |
| MARCANOTRPS | Marcar nota como RPS por padrão? | S |  |  | N |
| MARGSEGPCP | Margem de segurança para PCP | F |  |  | N |
| MAXCREDINDENIZ | % Máx.Crédito de Indenização | F |  |  | N |
| MAXDEBINDENIZ | % Máx.Débito de Indenização | F |  |  | N |
| MAXNOTASLOTENFE | Max. notas lote NF-e/NFC-e | I |  |  | N |
| MDFEDOCSEMISPROPRIA | Gerar MDF-e apenas com documentos de emissão própria? | S |  |  | N |
| METCALCDIFICMS | Método para Calcular Diferencial de Alíquota | S |  |  | N |
| MIGRADANFSE | Empresa migrada para microsserviço de NFS-e | S |  |  | N |
| MODDUPL | Modelo duplicata | I |  |  | N |
| MODEMAILLIBLIM | Modelo de E-mail p/ Liberação de Limites | I |  |  | N |
| MODESTCPAWMS | Modelo Estorno WMS - Dev. Compra | I |  |  | N |
| MODETQVOL | Modelo padrão de Etiqueta de Volume | I |  |  | N |
| MODEXPED | Modelo | I |  |  | N |
| MSGINFADICFETHAB | Mensagem FETHAB para Inform.Adicional NF-e | S |  |  | N |
| NATCANPIX | Natureza Cancelamento Pix | I |  |  | N |
| NATPESSOAJURIDICA | Natureza da Pessoa Jurídica | S |  |  | N |
| NFE | Ambiente NF-e/NFC-e | S |  |  | N |
| NFECANEXTCANMES | Considerar NF-e Cancelada Extemporânea como Cancelada no mês no EFD | S |  |  | N |
| NFESTANTCONSFINAL | Valor da ST anterior no CST 60 do XML de NF-e p/ consumidor final | S |  |  | N |
| NFSE | Ambiente NFS-e | S |  |  | N |
| NFSEOBSITERPS | Conteúdo a ser enviado na descrição de NFS-e | I |  |  | N |
| NOINSCMUNPARDIF | Não enviar IM quando parceiro tomador reside em município diferente do prestador | S |  |  | N |
| NOMECONTADOR | Nome do Contador | S |  |  | N |
| NOTAENTAJUSTERECLAS | Modelo Ajuste de Entrada Reclassificação (Registro K220) | I |  |  | N |
| NOTAENTAJUSTEST | Modelo Ajuste de Entrada de Estoque | I |  |  | N |
| NOTAENTAJUSTESTCONS | Modelo Ajuste de Entrada de Estoque Consignado | I |  |  | N |
| NOTAENTAJUSTESTCTER | Modelo Ajuste de Entrada de Estoque com Terceiros | I |  |  | N |
| NOTAENTAJUSTESTDTER | Modelo Ajuste de Entrada de Estoque de Terceiros | I |  |  | N |
| NOTAENTSOBRAWMS | Nota modelo sobra estoque WMS (Entrada) | I |  |  | N |
| NOTAMODELODAGENDA | Modelo de nota e pedidos | I |  |  | N |
| NOTASAIAJUSTBEM | Modelo Ajuste de Baixa de Bem | I |  |  | N |
| NOTASAIAJUSTERECLAS | Modelo Ajuste de Saída Reclassificação (Registro K220) | I |  |  | N |
| NOTASAIAJUSTEST | Modelo Ajuste de Saída de Estoque | I |  |  | N |
| NOTASAIAJUSTESTCONS | Modelo Ajuste de Saída de Estoque Consignado | I |  |  | N |
| NOTASAIAJUSTESTCTER | Modelo Ajuste de Saída de Estoque com Terceiros | I |  |  | N |
| NOTASAIAJUSTESTDTER | Modelo Ajuste de Saída de Estoque de Terceiros | I |  |  | N |
| NOTASAIPERDAWMS | Nota modelo perda estoque WMS (Saída) | I |  |  | N |
| NROMAXPROD | Nro. máximo de produtos no endereço | I |  |  | N |
| NUMDOCR2020 | Nro. Documento do Evento R2020 | S |  |  | N |
| NUNOTADEMFX | NF demonstração | I |  |  | N |
| NUNOTAMODFX | NF modelo | I |  |  | N |
| NUNOTATRCFX | NF de troca | I |  |  | N |
| NURFE | Relatório formatado do DANFE | I |  |  | N |
| NURFECARTACORR | Relatório de carta de correção | I |  |  | N |
| NURFECARTACORRCTE | Relatório carta de correção | I |  |  | N |
| NURFEPRODUCAO | Modelo de etiqueta | I |  |  | N |
| OBTSTANTMEDENT | Obtém ST Anterior pela média das entradas? | S |  |  | N |
| ORIGDESCONTOS | Origem dos Descontos | I |  |  | N |
| ORIGPRODCOMIPI | Origens de produto p/compensar IPI no Livro Fiscal | S |  |  | N |
| PARTCANALVERDE | Participa do canal verde? | S |  |  | N |
| PARTICMETA | Participação na meta | F |  |  | N |
| PERCCFFAB | % Custo Fixo Fabricação | F |  |  | N |
| PERCCFOUTROS | % Custo Fixo Outros | F |  |  | N |
| PERCCFSERVICO | % Custo Fixo Serviço | F |  |  | N |
| PERCCOFINS | % COFINS | F |  |  | N |
| PERCCSL | % CSLL (Contrib. Social s/Lucro) | F |  |  | N |
| PERCCUSVAR | % Custo Variável | F |  |  | N |
| PERCMARGEM | % Outros | F |  |  | N |
| PERCPIS | % PIS | F |  |  | N |
| PERFILEFD | Perfil de Apresentação do Arquivo | S |  |  | N |
| PERMOUTROUSUSEPPED | Permite outro usuário continuar separação | S |  |  | N |
| PERPROJ | Periodicidade projeção | S |  |  | N |
| PESMAXSEPAGRU | Peso máx. do checkout por separação | F |  |  | N |
| PORTASMTPXML | Porta | I |  |  | N |
| PRAZORCANCCTE | Prazo regulamentar p/ cancelamento de CT-e (horas) | I |  |  | N |
| PRAZOTCANCCTE | Prazo com tolerância p/ cancelamento de CT-e (horas) | I |  |  | N |
| PREFIXSERIENACIONAL | Prefixo Série NFS-e Padrão Nacional | S |  |  | N |
| PRIORCODNAT | Prioridade de Cod. Natureza (M410/M810) | I |  |  | N |
| PRODEPE | Beneficiário de Incentivo PRODEPE/FUNCRESCE? | S |  |  | N |
| PROGAQUISALIM | Participa do Programa Aquisição de Alimentos (PAA) | S |  |  | N |
| PROIBDIGCONFENT | Proibir digitação de qtd. na conferência de entrada | S |  |  | N |
| PROIBESCCHECKOUTPED | Proibir escolha checkout na conferência do pedido | S |  |  | N |
| PROJONLINE | Projeção Online | S |  |  | N |
| PROXEXECSMTPXML | Próxima consulta | H |  |  | N |
| PRZREGCANNOTA | Prazo regulamentar p/ cancelamento de nota (horas) | I |  |  | N |
| PRZTOLCANNOTA | Prazo com tolerância p/ cancelamento de nota (horas) | I |  |  | N |
| QRCODENFCEVERSAO | Versão QR Code | S |  |  | N |
| QRCODENFCEVERSAO2 | Usar o QR Code versão 2.0? | S |  |  | N |
| QTCHECKSEP | Quantidade máx. de checkouts por separação | I |  |  | N |
| QTDCARNFSEOBSITERPS | Qtd. de caracteres a serem enviados na descrição de NFS-e | I |  |  | N |
| QTDCONFENTWMS | Qtd. padrão na conferência de entrada | I |  |  | N |
| QTDLIMITEMES | Limite de dias p/ histórico | I |  |  | N |
| QTPEDSEPAGR | Quantidade de pedidos por separação | I |  |  | N |
| RASTRESTOQUE | Rastreamento de Estoques | S |  |  | N |
| REABCOMPLETO | Reabastecer somente quantidade completa do pulmão | S |  |  | N |
| REABCORRECAOWMS | Gerar reabastecimento corretivo | S |  |  | N |
| RECMINTARE | Tem convênio TARE na geração do livro fiscal? | S |  |  | N |
| REDDIFALBCPISCOF | Deduzir DIFAL junto com o ICMS na BC do PIS e COFINS? | S |  |  | N |
| REDICMSBCPISCONFINS | Deduzir valor do ICMS na BC do PIS e COFINS? | S |  |  | N |
| REDISSBCPISCONF | Deduzir valor do ISS na BC do PIS e COFINS? | S |  |  | N |
| REDPISBCPISCOFINS | Deduzir valor do PIS/COFINS na BC do PIS e COFINS? | S |  |  | N |
| REDSTBCPISCOFINS | Deduzir valor do ICMS/ST na BC do PIS e COFINS? | S |  |  | N |
| REDSTCOMPBCPISCOFINS | Deduzir valor de ST da compra da BC do PIS e COFINS da Venda? | S |  |  | N |
| REG54SEQ997ST | Reg.54.Seq.997 p/ ST? | S |  |  | N |
| REGAPURTRIBSN | Regime de Apuração dos Tributos do Simples Nacional | S |  |  | N |
| REGIMEESPTRIBISS | Regime esp. tributação ISS (NFS-e) | S |  |  | N |
| RESPENTREINF | Responsável pela Entrega | I |  |  | N |
| RUPTURAEST | Calcular Ruptura de Estoque? | S |  |  | N |
| SALDOLIVQUINZ | Frequência saldo livro ICMS | S |  |  | N |
| SALDOLIVQUINZIPI | Frequência saldo livro IPI | S |  |  | N |
| SENHAECT | Senha Correios | S |  |  | N |
| SENHAECTRASTREIO | Senha rastreamento | S |  |  | N |
| SENHANFSE | Senha NFS-e | S |  |  | N |
| SENHASMTPXML | Senha do e-mail | S |  |  | N |
| SEPPULPARCIAL | Utiliza separação parcial no pulmão | S |  |  | N |
| SEQTOKENNFCE | Sequencia do token da NFC-e | I |  |  | N |
| SERIEACOMPCF | Série p/ Nota de Acomp. de CF | S |  |  | N |
| SERIEDEVWMS | Série p/ devolução de mercadorias | S |  |  | N |
| SERIETOPDIFMAIOR | Série TOP diferença a maior(peso variável) | S |  |  | N |
| SERIETOPDIFMENOR | Série TOP diferença a menor(peso variável) | S |  |  | N |
| SERNAOTRIBBASEISS | Serviços não Trib./Isentos para compor base do ISS? | S |  |  | N |
| SERVIDORSMTPXML | Servidor de e-mail | S |  |  | N |
| SITEMPREINF | Situação da Empresa | I |  |  | N |
| SOMARFCPBCCIAP | Somar FCP na Base de Crédito do CIAP? | S |  |  | N |
| STATUS | Status | I |  |  | N |
| STINCLUSA | Valor da Substituição Tributária ou Valor Contábil p/Cód.Trib.10 e 15 em Entradas | S |  |  | N |
| STINCLUSA70 | Valor da Substituição Tributaria ou Valor Contábil p/Cód.Trib.70 em Entradas | S |  |  | N |
| SUPRIMIREMAILDEST | Suprimir email do destinatário para NF-e? | S |  |  | N |
| TELCONTADOR | Telefone | S |  |  | N |
| TEMCOFINS | Calcula COFINS? | S |  |  | N |
| TEMCSSL | Calcula CSLL? | S |  |  | N |
| TEMDENUESPONT | Tem denúncia espontânea? | S |  |  | N |
| TEMPIS | Calcula PIS? | S |  |  | N |
| TEMPREMIACAOESTADUAL | Tem programa de premiação estadual? | S |  |  | N |
| TEMREAICMS | Tem Convênio REA/ICMS? | S |  |  | N |
| TERACPCTE | Termo de Acordo p/ CT-e | S |  |  | N |
| TIPCONEXSMTPXML | Tipo de Conexão | S |  |  | N |
| TIPCTACTBEFD | Tipo da Conta Contábil para EFD | S |  |  | N |
| TIPCTACTBEFDF | Tipo de conta contábil para EFD ICMS/IPI | S |  |  | N |
| TIPDATAEVTPAG | Tipo Data p/ Dados Eventos Grupo 4000 - IR | S |  |  | N |
| TIPDATAEVTPAGXIR | Tipo Data p/ Dados Eventos Grupo 4000 - Exceto IR | S |  |  | N |
| TIPDATAEVTSERV | Tipo de data p/ busca das notas de serviço dos eventos 2010 e 2020 (exceto financeiros) | S |  |  | N |
| TIPGERINFGVEICGREB | Gerar as informações do Grupos Veículo Transporte e Grupo Reboque | S |  |  | N |
| TIPICMSFENVALBEM | Tipo do ICMS do Frete Extra Nota no valor de aquisição/depreciação do bem | I |  |  | N |
| TIPICMSTOPENTSIMNAC | Considerar Tipo de ICMS da TOP p/ entrada em empresa Simp.Nac.? | S |  |  | N |
| TIPOATIVIDADE | Tipo de Atividade | I |  |  | N |
| TIPOATIVIDADEPISCOFINS | Tipo de Atividade | I |  |  | N |
| TIPOCORTEWMS | Tipo de corte na separação | S |  |  | N |
| TIPODACTE | Impressão do DACTE | S |  |  | N |
| TIPODANFE | Impressão do DANFE | S |  |  | N |
| TIPOENVIOCTE | Tipo de envio CT-e | S |  |  | N |
| TIPOENVIONFCE | Tipo de envio da NFC-e | S |  |  | N |
| TIPOENVIONFCOM | Tipo de envio NFCom | S |  |  | N |
| TIPOENVIONFE | Tipo de envio NF-e | S |  |  | N |
| TIPOEXPLORACAO | Tipo de Exploração | S |  |  | N |
| TIPOIMPKITFOX | Tipo Kit | S |  |  | N |
| TIPOIMPRESSORA | Tipo de Imp. | S |  |  | N |
| TIPOPIX | Tipo de PIX | S |  |  | N |
| TIPTRANSMNFSE | Tipo de Transmissão NFS-e | S |  |  | N |
| TIRASERVLRCONTAB | Tirar Serviço do Valor Contábil | S |  |  | N |
| TOKENIBPT | Token IBPT | S |  |  | N |
| TOKENNFCE | Token NFC-e | S |  |  | N |
| TOKENNFSE | Token NFS-e | S |  |  | N |
| TOPCANPIXFIN | TOP Cancelamento Pix - Financeiro | I |  |  | N |
| TOPCANPIXPAG | TOP Cancelamento Pix - Pagamento | I |  |  | N |
| TOPCANPIXREC | TOP Cancelamento Pix - Recebimento | I |  |  | N |
| TOPCORBANECONECT | TOP para CorBan | I |  |  | N |
| TOPDESPESA | TOP para despesa | I |  |  | N |
| TOPDEVOLUCAOWMS | TOP p/ devolução de mercadorias | I |  |  | N |
| TOPENTDIFPESOWMS | TOP diferença a maior(peso variável) | I |  |  | N |
| TOPENTRADAWMS | TOP de entrada de mercadorias | I |  |  | N |
| TOPENVWMSAGRUP | TOP pedido agrupado p/ enviar ao WMS (Expedição) | I |  |  | N |
| TOPNFCEECONECT | TOP para NFC-e | I |  |  | N |
| TOPRECARGACELECONECT | TOP recarga celular EConect | I |  |  | N |
| TOPSAIDADIFPESOWMS | TOP diferença a menor(peso variável) | I |  |  | N |
| TOPSANGSUPRI | TOP para sangria / suprimento | I |  |  | N |
| TOPSATECONECT | TOP para SAT | I |  |  | N |
| TPAMBCTE | Ambiente CT-e | S |  |  | N |
| TPAMBMDFE | Ambiente MDF-e | S |  |  | N |
| TPAMBNFCOM | Ambiente NFCom | S |  |  | N |
| TPASSINANTE | Cód.Tipo de Assinante | I |  |  | N |
| TPASSINANTEINF | Permite infomar Assinante na Central? | S |  |  | N |
| TPLIGACAO | Cód.Tipo de Ligação | I |  |  | N |
| TPLIGACAOINF | Permite informar Ligação na Central? | S |  |  | N |
| TPOBSFING4000 | Tipo de observação do financeiro p/ evento Grupo 4000 | I |  |  | N |
| TPOBSNOTAG4000 | Tipo de observação da nota p/ evento Grupo 4000 | I |  |  | N |
| TPOEMPRESA | Tipo de Empresa | I |  |  | N |
| TRABCOMIPI | Trabalha com IPI ? | S |  |  | N |
| TRANSPCARGA | Transportadora de cargas | S |  |  | N |
| TRATARDIFPARC | Tratar Diferenciamento Parcial? | S |  |  | N |
| TRATARTRIBUT | Tratar Código Tributação Isentas como Isentas? | S |  |  | N |
| TRATARTRIBUTDEFEMP | Usar Tratamento de Código Tributação Definido no Cadastro de Empresas? | S |  |  | N |
| TRATOCWMS | Tratamento p/falta de estoque no envio da OC | S |  |  | N |
| TRATSOBFINCONF | Tratar sobra ao final da conferência? | S |  |  | N |
| TRIBPISCFBONIF | Tributa PIS/COFINS sobre Aquisições em Bonificações? | S |  |  | N |
| TRIBPISCFCAMB | Tributa PIS/COFINS sobre Variação Cambial de Receitas? | S |  |  | N |
| TRIBPISCFDESC | Tributa PIS/COFINS sobre Descontos Obtidos de Despesas? | S |  |  | N |
| TRIBPISCFJUROS | Tributa PIS/COFINS sobre Juros Recebidos de Receitas? | S |  |  | N |
| TRIBPISCFMULT | Tributa PIS/COFINS sobre Receitas Obtidos de Multas? | S |  |  | N |
| TRIBREINF | Classificação Tributária - EFD-Reinf | I |  |  | N |
| UFCRCCONTADOR | UF CRC | S |  |  | N |
| ULTAUTORPAG | Última autorização de pagamento | I |  |  | N |
| ULTDUPL | Última duplicata | I |  |  | N |
| ULTNSU | Último NSU | S |  |  | N |
| ULTNSUCTE | Último NSU CT-e | S |  |  | N |
| ULTPAGCIAPMODC | Última Página do CIAP Modelo C | I |  |  | N |
| ULTPAGLIVENTRAD | Última página do registro de entradas | I |  |  | N |
| ULTPAGLIVICMS | Última Página Livro ICMS | I |  |  | N |
| ULTPAGLIVIPI | Última Página Livro IPI | I |  |  | N |
| ULTPAGLIVISS | Última página do livro ISS | I |  |  | N |
| ULTPAGLIVSAIDA | Última página do registro de saídas | I |  |  | N |
| URLPIXCHECKOUT | URL PIX | S |  |  | N |
| USACODBARRASCONCATWMS | Utiliza cód. de barras concatenado | S |  |  | N |
| USACUSMEDICMPRO | Utiliza custo médio das matérias primas no cálculo do custo com ICMS e sem ICMS do PA? | S |  |  | N |
| USACUSTOMEDIOMPS | Usar custo médio das MPs para calcular custo do PA | S |  |  | N |
| USADTFABLOTNFE | Usar data de fabricação do lote? | S |  |  | N |
| USADTVALLOTNFE | Usar data de validade do lote? | S |  |  | N |
| USAINDPRESTOPFAT | Buscar da TOP o indicador de presença ao faturar pedido/nota | S |  |  | N |
| USAINFOADCONFPED | Utiliza informação adicional na conferência do pedido? | S |  |  | N |
| USAMEDDIAIMP | Usar o valor unitário do item nos valores médios de imposto | S |  |  | N |
| USAMOVVERTWMS | Utiliza Movimentação Vertical | S |  |  | N |
| USANOMECOMPLITEM | Usar nome do campo no complemento para itens da nota? | S |  |  | N |
| USARECPARCIAL | Utiliza recebimento parcial? | S |  |  | N |
| USASEPAGRUPROD | Utiliza separação agrupada por produto | S |  |  | N |
| USASTEXTNOTARESTST | Usar ST Extra Nota na Restituição de ST | S |  |  | N |
| USATRIBUTITENS | Usar Código de Tributação dos Itens? | S |  |  | N |
| USATROCOCHECKOUT | Utilizar troco no PDV WEB? | S |  |  | N |
| USAVLRMEDTRANSFEMP | Usa valor médio de venda nas transferências entre empresas do mesmos grupo | S |  |  | N |
| USUARIONFSE | Usuário NFS-e | S |  |  | N |
| USUARIOSMTPXML | Usuário do e-mail | S |  |  | N |
| UTILIZADFETRANSP | Utilizar distribuição de DF-e? | S |  |  | N |
| UTILIZAEXPLOTE | Utiliza explosão de lote no recebimento | S |  |  | N |
| UTILIZAEXPLOTESEP | Utiliza explosão de lote na separação | S |  |  | N |
| UTILIZAMDE | Utiliza distribuição de DF-e? | S |  |  | N |
| UTILIZASEPPULMAO | Utiliza separação pulmão | S |  |  | N |
| UTILIZATRICROSSDOCKING | Utiliza triagem no Crossdocking | S |  |  | N |
| UTILIZAWMS | Controlado pelo WMS? | S |  |  | N |
| VALIDACPFCNPJ | CPF obrigatório | S |  |  | N |
| VERSAOCTE | Versão CT-e | I |  |  | N |
| VERSAOGNRE | Versão GNRE | S |  |  | N |
| VERSAOMDFE | Versão MDF-e | I |  |  | N |
| VERSAONFE | Versão NF-e/NFC-e | I |  |  | N |
| VERSAONT | Versão da nota técnica | I |  |  | N |
| VERSAONTCTE | Versão da nota técnica do CT-E | I |  |  | N |
| VERSAONTMDFE | Versão da nota técnica do MDF-e | I |  |  | N |
| VINCULOEFR | EFR ou Vinculada a EFR? | S |  |  | N |
| VLRBASESTC180 | Vlr. unitário base ST | S |  |  | N |
| VLRICMSOPANT | Vlr. do ICMS da operação anterior | S |  |  | N |
| VLRLIQITEMNFCE | Considerar o valor líquido do item na NFC-e? | S |  |  | N |
| VLRLIQITEMNFE | Considerar o valor líquido do item para NF-e (geração do XML e imp. do DANFE)? | S |  |  | N |
| VLRSTC180 | Vlr. unitário ST | S |  |  | N |
| VLRSTFCPOPANT | Vlr. ST FCP da operação anterior | S |  |  | N |
| VLRSTOPANT | Vlr. do ST da operação anterior | S |  |  | N |
| VLRUNITICMSOP | Vlr. unitário do ICMS da operação de saída | S |  |  | N |
| VLRUNITMERC | Vlr. unitário do item | S |  |  | N |
| VOLCHECKSEPROD | Volumetria máx. do checkout por separação(M3) | F |  |  | N |
| WMSARMTOTALCOL | Armazenar o total coletado no recebimento expresso | S |  |  | N |
| WMSCODENDFLUT | Cód. End. Flutuante | I |  |  | N |
| WMSCODENDGAR | Endereço especial de garantia | I |  |  | N |
| WMSDOCAREP | Permitir mais de uma OC na doca expedição | S |  |  | N |
| WMSDOCASEPBALCAO | Utiliza doca automaticamente separações balcão | S |  |  | N |
| WMSINFOPESODETVOL | Informa peso no detalhamento de volume | S |  |  | N |
| WMSLOCALAJEST | Local para ajuste de estoque | I |  |  | N |
| WMSMULTIUSUCONF | Múltiplos usuários na conferência de entrada | S |  |  | N |
| WMSPERSEPPRODAP | Permite separação por produto em área por pedido | S |  |  | N |
| WMSRASTSERMED | Habilita controle de rastreabilidade série de medicamentos no WMS | S |  |  | N |
| WMSUSAETIQPAL | Utiliza Etiqueta para Pálete | S |  |  | N |
| WMSUSAIMPFECHAVOL | Usa impressão de etiqueta ao fechar volume | S |  |  | N |
| WMSUSAREGVOLFAT | Utiliza registro de volumes no faturamento | S |  |  | N |
| WMSUSAREGVOLREC | Registra Volume na Conferência de Entrada | S |  |  | N |
| ZERARVALREMSEMIMP | Zerar Valores Contábeis nos Registros C100 e C190 | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| AutorizacaoAcessoXmlNFeMarca / [TGFAAXNM](TGFAAXNM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| CadastroPDV / [TGFPDV](TGFPDV.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| Crediario / [TGFCRED](TGFCRED.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| DescontoFob / [TGFDFO](TGFDFO.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| Empresa / [TSIEMP](TSIEMP.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | AutorizacaoAcessoXmlNFe / [TGFAAXN](TGFAAXN.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Banco / [TSIBCO](TSIBCO.md) | EFDCODBCO->CODBCO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | BancoIpi / [TSIBCO](TSIBCO.md) | CODBCOIPI->CODBCO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | BlocosEscrituracaoFiscal / [TGFEFB](TGFEFB.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTEST->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModeloAgenda / [TGFCAB](TGFCAB.md) | NOTAMODELODAGENDA->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModeloSaida / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTEST->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaModeloSaidaBem / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTBEM->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaWmsEnt / [TGFCAB](TGFCAB.md) | NOTAENTSOBRAWMS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CabecalhoNotaWmsSai / [TGFCAB](TGFCAB.md) | NOTASAIPERDAWMS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultadoDespesa / [TSICUS](TSICUS.md) | CODCENCUSDESP->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultadoEFDEmpresa / [TSICUS](TSICUS.md) | EFDCODCENCUS->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CentroResultadoIpi / [TSICUS](TSICUS.md) | CODCENCUSIPI->CODCENCUS | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CNAEEmpresa / [TGFCNAE](TGFCNAE.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTAPIXTEF->CODCTABCOINT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ContaBancariaIpi / [TSICTA](TSICTA.md) | CODCTABCOIPI->CODCTABCOINT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ControleNumeracao / [TGFNUM](TGFNUM.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaCadastro / [TSIEMP](TSIEMP.md) | CODEMPORIGMOVFIN->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaFrete / [TSIEMP](TSIEMP.md) | CODEMPGRUPFRETE->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaMatriz / [TSIEMP](TSIEMP.md) | CODEMPMATRIZEFD->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaMatrizGnre / [TSIEMP](TSIEMP.md) | CODEMPMATRIZGNRE->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaMatrizNfse / [TSIEMP](TSIEMP.md) | CODEMPMATRIZNFSE->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaModelo / [TSIEMP](TSIEMP.md) | CODEMPIMPOSTO->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaNegociacao / [TSIEMP](TSIEMP.md) | EMPDESTINOWMS->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaOC / [TSIEMP](TSIEMP.md) | CODEMPOC->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EmpresaSoma / [TSIEMP](TSIEMP.md) | EMPSOMA->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagem /  | CODENDWMS->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagem2 /  | CODENDAVARIA->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagem3 /  | CODENDDIVERG->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagem4 /  | CODENDPERDA->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagem5 /  | CODENDSOBRA->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagemChk /  | ENDECKTINDEF->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagemEsp /  | WMSCODENDGAR->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagemFlutuante /  | WMSCODENDFLUT->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagemIndef /  | ENDEMOVINDEF->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoArmazenagemIndefinida /  | CODENDARMINDEF->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoCrossDocking /  | CODENDCROSSDOCK->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EnderecoRetornoExpedicao /  | CODENDRETEXP->CODEND | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | EventoPeriodico /  | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FaturamentoCruzado / [TSIFAT](TSIFAT.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FormulaDiferencialAliquota / [TGFFDA](TGFFDA.md) | CODFORDIFALIQ->CODFORM | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FormulaPrecificacao / [TGFFOR](TGFFOR.md) | CODFORMPREC->CODFORMPREC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FundoSociedadeInvestimento /  | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANCBCOREC->CODLANC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | HistoricoBancarioDestino / [TGFHBC](TGFHBC.md) | CODLANCBCOPAG->CODLANC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | InscricaoUnidadeFederativa / [TSIIUF](TSIIUF.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalEstoqueTerceiro / [TGFLOC](TGFLOC.md) | CODLOCALTERC->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalEstoqueWms / [TGFLOC](TGFLOC.md) | WMSLOCALAJEST->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | LOCALPAD->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LocalPadraoEconet / [TGFLOC](TGFLOC.md) | LOCALPADECONECT->CODLOCAL | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDACTE / [TGFMON](TGFMON.md) | CODMODDACTE->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfe / [TGFMON](TGFMON.md) | CODMODDANFE->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfeContingencia / [TGFMON](TGFMON.md) | CODMODDANFECONTINGENCIA->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfeNFCeCompleto / [TGFMON](TGFMON.md) | CODMODNFCECOMPL->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloDanfeNFCeSimplificado / [TGFMON](TGFMON.md) | CODMODNFCESIMPL->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEmail / [TSIMEM](TSIMEM.md) | MODEMAILLIBLIM->CODMODELO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEmail2 / [TSIMEM](TSIMEM.md) | CODMODEMAILNFE->CODMODELO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaEstoqueComTerc / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTESTCTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaEstoqueConsignado / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTESTCONS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaEstoqueDeTerc / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTESTDTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEntradaReclassificacao / [TGFCAB](TGFCAB.md) | NOTAENTAJUSTERECLAS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEstornoWMSDevCompra / [TGFCAB](TGFCAB.md) | MODESTCPAWMS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloEtiqueta / [TSIRFE](TSIRFE.md) | NURFEPRODUCAO->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloImportacaoXML / [TGFMIX](TGFMIX.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODDUPL->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloNotaFiscalEstoque / [TGFMON](TGFMON.md) | MODEXPED->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloRetornoEstoqueWMS / [TGFCAB](TGFCAB.md) | CODMODRETESTWMS->NUNOTA | L |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaEstoqueComTerc / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTESTCTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaEstoqueConsignado / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTESTCONS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaEstoqueDeTerc / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTESTDTER->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloSaidaReclassificacao / [TGFCAB](TGFCAB.md) | NOTASAIAJUSTERECLAS->NUNOTA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Natureza / [TGFNAT](TGFNAT.md) | NATCANPIX->CODNAT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NaturezaIpi / [TGFNAT](TGFNAT.md) | CODNATIPI->CODNAT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NaturezaReceitaDespesaICMS / [TGFNAT](TGFNAT.md) | EFDCODNATDESPRECICMS->CODNAT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelaPrecoFilha2 / [TGFNTA](TGFNTA.md) | CODTABALT->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTABCALC->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelasPrecoOrigem / [TGFNTA](TGFNTA.md) | CODTABCKC->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NotaTecnicaCTe / [TGFCTENT](TGFCTENT.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NotaTecnicaNFCom / [TGFNFCOMNT](TGFNFCOMNT.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NotaTecnicaNFe / [TGFNFENT](TGFNFENT.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCTB->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARCNFCE->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ParceiroAtivoPj / [TGFPAR](TGFPAR.md) | CODPARCIPI->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ParceiroEntrega / [TGFPAR](TGFPAR.md) | RESPENTREINF->CODPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PartilhaSimplesNacXEmp / [TGFEMPSN](TGFEMPSN.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaBonificacao /  | CODCTACTBBONIF->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaCamb / TCBPLA | CODCTACTBCAMB->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaDesconto /  | CODCTACTBDESC->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaEmpresa /  | CODCTACTB_1->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaEmpresa2 /  | CODCTACTB_2->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaEmpresa3 /  | CODCTACTB_3->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaJuro /  | CODCTACTBJUROS->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PlanoContaMulta /  | CODCTACTBMULT->CODCTACTB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITAIPI->CODRECEITA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ReceitaDarfReinf / [TGFRDARF](TGFRDARF.md) | CODRECEITAREINF->CODRECEITA | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RegimeApurContribSocAproCred / [TGFRGM](TGFRGM.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RegistrosEscrituracaoFiscal / [TGFEFR](TGFEFR.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioCartaCorrecao / [TSIRFE](TSIRFE.md) | NURFECARTACORR->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioCartaCorrecaoCte / [TSIRFE](TSIRFE.md) | NURFECARTACORRCTE->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioDanfeSimplificado / [TSIRFE](TSIRFE.md) | CODMODDANFESIMPLIFNFE->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioEtiquetaVolume / [TSIRFE](TSIRFE.md) | MODETQVOL->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RelatorioMinutaDespacho / [TSIRFE](TSIRFE.md) | CODRELMINUTAODP->NURFE | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RoteamentoImpressaoEmpresa / [TSIRIM](TSIRIM.md) | CODEMP->CODIGO | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ServidorSMTP / [TSISMTP](TSISMTP.md) | CODSMTP->CODSMTP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | SociedadeCotaParticipacao /  | CODEMP->CODEMP | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TerceiroProducaoRural / [TGFLCDPRTERC](TGFLCDPRTERC.md) | CODEMP->CODEMP | I |
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
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | EFDCODTIPTIT->CODTIPTIT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | TipoTituloIpi / [TGFTIT](TGFTIT.md) | CODTIPTITIPI->CODTIPTIT | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| EmpresaTabPreco / [TGFEMP](TGFEMP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresaTabPreco / [TGFEMP](TGFEMP.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| Estoque / [TGFEST](TGFEST.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| FichaAcompanhamentoDiario / [TGFFAD](TGFFAD.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| LivroOutros / [TGFLOU](TGFLOU.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| MovimentoEpi / [TGFMEPI](TGFMEPI.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| NotaTecnicaCTe / [TGFCTENT](TGFCTENT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| NotaTecnicaNFe / [TGFNFENT](TGFNFENT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | EmpresaTabPreco / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| TerceiroProducaoRural / [TGFLCDPRTERC](TGFLCDPRTERC.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | LOTEMPPARCERIA->CODEMP | I |
| TopNaturezaOperacaoMunicipio / [TGFTNA](TGFTNA.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| VariacaoCustoEstoque / [TGFVCE](TGFVCE.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
