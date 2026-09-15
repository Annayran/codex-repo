# TSIEMP — Empresas

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 77
- Instâncias DWF: 24
- Relações DWF: 184
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Empresa | Empresa | S | S |
| EmpresaBaixa | Empresa para baixa | S | N |
| EmpresaCadastro | Cadastro de Empresas | S | N |
| EmpresaContablidade | Empresa Contabilidade | S | N |
| EmpresaDestinoEtapaProducao | Empresa Destino | S | N |
| EmpresaFinanceira | Empresa Financeiro | S | N |
| EmpresaFrete | Empresa Ordem de Carga | N | N |
| EmpresaFuncionario | Empresa do funcionário | S | N |
| EmpresaFuncionarioLider | Empresa Funcionário Líder | S | N |
| EmpresaGOL | Empresa para agrupar no GOL | S | N |
| EmpresaMatriz | Empresa Matriz | S | N |
| EmpresaMatrizGnre | Empresa Matriz para GNRE | S | N |
| EmpresaMatrizNfse | Empresa Matriz para Nfse | S | N |
| EmpresaModelo | Empresa Modelo | S | N |
| EmpresaNegociacao | Empresa da Negociação | S | N |
| EmpresaOC | Empresa Ordem de Carga | N | N |
| EmpresaOrganograma | Empresa de Organograma | S | N |
| EmpresaOrigemEtapaProducao | Empresa Origem | S | N |
| EmpresaOrigemFuncionario | Empresa origem do funcionário | S | N |
| EmpresaPreferencial | Empresa Preferencial do parceiro | S | N |
| EmpresaSoma | EmpresaSoma | S | N |
| EmpresaSubstituido | EmpresaSubstituido | S | N |
| ImovelRural | Imóvel Rural | S | N |
| TimEmpresa | Empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACDINTISENMULTA | Acordo internacional Isenção | I |  |  | N |
| ATIVECON | Atividade Econômica | I |  |  | N |
| CARGAS | Cargas | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CGC | CNPJ/CPF | S |  |  | N |
| CLASSTRIB | Classificação Tributária | I |  |  | N |
| CNAEPREPON | CNAE Preponderante | I |  |  | N |
| CNPJPREFEITURA | CNPJ Unidade Gestora | S |  |  | N |
| CODBAI | Bairro | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODCNL | Código Nacional Localidade | S |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODEMPMATRIZ | Empresa Matriz | I |  |  | N |
| CODEND | Endereço | I |  |  | N |
| CODMUN | Cód. Município | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODREGTRIB | Cód. Regime Tribut. | I |  |  | N |
| COMISSOES | Comissões | S |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| CONTABILIDADE | Contabilidade | H |  |  | N |
| COOPERATIVA | Cooperativa | S |  |  | N |
| COREMPRESA | Cor da empresa | I |  |  | N |
| COTM | COTM - CERTIFICADO DO OPERADOR DE TRANSPORTE MULTIMODAL | S |  |  | N |
| CPFRESP | CPF do responsável | S |  |  | N |
| DHCONSITIMEND | Última Consulta IMENDES | H |  |  | N |
| DTCONVSOC | Data Conversão (Sociedade Simples p/ Empresária) | D |  |  | N |
| DTREGJUNTA | Data Registro na Junta (NIRE) | D |  |  | N |
| DUPLIV | Gera duplicata do Livro para a Empresa do Parceiro | S |  |  | N |
| EMAIL | Email | S |  |  | N |
| EMPAGRUPARGOL | Empresa p/ Agrupar no GOL | I |  |  | N |
| EMPIDENOTAS | ID NFS-e | S |  |  | N |
| ESTOQUE | Estoque | S |  |  | N |
| FAX | Fax | S |  |  | N |
| FINANCEIRO | Financeiro | S |  |  | N |
| FOLHAPAGTO | Folha de Pagamento | S |  |  | N |
| HOMEPAGE | Home Page | S |  |  | N |
| INDCONSTR | Construtora | I |  |  | N |
| INDCOOP | Cooperativa | I |  |  | N |
| INDOPCCP | Tributação da Contribuição Previdenciária | I |  |  | N |
| INDSITESP | Situação Especial | I |  |  | N |
| INFOOBRA | Contribuição Patronal | I |  |  | N |
| INSCESTAD | Inscrição Estadual | S |  |  | N |
| INSCMUN | Inscrição Municipal | S |  |  | N |
| ISUFEMIT | Inscrição da Suframa | S |  |  | N |
| LATITUDE | Latitude | S |  |  | N |
| LIMCURVA_B | Limite Curva B | F |  |  | N |
| LIMCURVA_C | Limite Curva C | F |  |  | N |
| LIVROSFISCAIS | Livros Fiscais | S |  |  | N |
| LOGOMARCA | Logomarca | B |  |  | N |
| LONGITUDE | Longitude | S |  |  | N |
| MODELONFDES | Modelo Nota Fiscal | S |  |  | N |
| NATESTAB | Natureza Estabelecimento | I |  |  | N |
| NATJUR | Natureza Jurídica | I |  |  | N |
| NOMEFANTASIA | Nome Fantasia | S |  |  | N |
| NUMEND | Número | S |  |  | N |
| NUMPROPR | Número de Proprietários | I |  |  | N |
| NUMTAF | Termo de Autorização de Fretamento – TAF | S |  |  | N |
| NUREST | Número do Registro Estadual | S |  |  | N |
| PRINCTITULAR | Principal Titular | S |  |  | N |
| PRODUCAO | Produção | S |  |  | N |
| PRODUTORRURAL | Produtor Rural | S |  |  | N |
| QTDACESSOS | Quantidade de usuários logados simultaneamente | I |  |  | N |
| RAMOATIV | Ramo de Atividade | S |  |  | N |
| RAZAOABREV | Razão Abreviada | S |  |  | N |
| RAZAOSOCIAL | Razão Social | S |  |  | N |
| RAZAOSOCIALCOMPLETA | Razão Social Completa | S |  |  | N |
| REGJUNTACOM | Reg. Junta Comercial (NIRE) | S |  |  | N |
| RNTRC | RNTRC | S |  |  | N |
| SERIENFDES | Série NF DES-BH | S |  |  | N |
| SIMPLES | Optante pelo SIMPLES | S |  |  | N |
| SIMPLESNACNAUF | Tem convênio Simples Nacional no Estado | S |  |  | N |
| SUPDECISAO | Suporte a Decisão | S |  |  | N |
| TELEFONE | Telefone | S |  |  | N |
| TELEX | Telex | S |  |  | N |
| TIPOREGRA | Tipo p/ Certificação de Regra | S |  |  | S |
| TIPOSN | Tipo de Partilha/Anexo SN | I |  |  | N |
| USARAZAOSOCIAL | Usar como Razão Social o campo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AjusteDocumentoCheckout / [TFXADF](TFXADF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AliquotaEspecialICMS / [TGFAEI](TGFAEI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AliquotaImposto / [TGFIMA](TGFIMA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODIGO->CODEMP | I |
| AliquotaISS / [TGFISS](TGFISS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| BaseStMediaVendaEmp / [TGFSTMVE](TGFSTMVE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoInventarioBem / [TCICABCTE](TCICABCTE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | EmpresaFuncionario / [TSIEMP](TSIEMP.md) | CODEMPFUNC->CODEMP | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | EmpresaNegociacao / [TSIEMP](TSIEMP.md) | CODEMPNEGOC->CODEMP | I |
| CabecalhoNotaExcluida / [TGFCAB_EXC](TGFCAB_EXC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | EmpresaFuncionario / [TSIEMP](TSIEMP.md) | CODEMPFUNC->CODEMP | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | EmpresaNegociacao / [TSIEMP](TSIEMP.md) | CODEMPNEGOC->CODEMP | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CadastroContingenciaPdv / [TFXCNF](TFXCNF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPRESA->CODEMP | I |
| CadastroDePdv / [TFXPDV](TFXPDV.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPRESA->CODEMP | I |
| CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CadastroMaquina / [TGFMAQ](TGFMAQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CargosEtapasProcesso / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CNAEEmpresa / [TGFCNAE](TGFCNAE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CodLegalCat42PorCFOP / [TGFCLCAT42](TGFCLCAT42.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Empresa / [TSIEMP](TSIEMP.md) | EMPRESA->CODEMP | I |
| CompraPendente / [TGFCVP](TGFCVP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoDFe / [TSICDFE](TSICDFE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoEmpresa / [TGFSEM](TGFSEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoLancamentoExtrato / [TGFCLX](TGFCLX.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConfiguracaoPedidoOffline / [TSICPO](TSICPO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContagemEstoque / [TGFCTE](TGFCTE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContagemEstoqueSerie / [TGFCTS](TGFCTS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContaUsuario / [TSIUCT](TSIUCT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Contingencia / [TGFCNF](TGFCNF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Contrato / [TCSCON](TCSCON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaCadastro / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaContablidade / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaSoma / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaSubstituido / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControleFCI / [TGFMFCI](TGFMFCI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControleNumeracao / [TGFNUM](TGFNUM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControleVeiculo / [TGFCVE](TGFCVE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Custo / [TGFCUS](TGFCUS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Desconto / [TGFDES](TGFDES.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| DescontoFob / [TGFDFO](TGFDFO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| DetalhesPedidoRessuprimento / [TGFHPRF](TGFHPRF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPORIG->CODEMP | I |
| DetalhesProdutoRessuprimento / [TGFHDRF](TGFHDRF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPORIG->CODEMP | I |
| Diario / [TCADIA](TCADIA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| DisciplinaSemanal / [TCADSE](TCADSE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Empresa / [TSIEMP](TSIEMP.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODEMP->CODIGO | I |
| Empresa / [TSIEMP](TSIEMP.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Empresa / [TSIEMP](TSIEMP.md) | CertificacaoRegra / [TGFCER](TGFCER.md) | CODEMP->CHAVE; TIPOREGRA->TIPO | I |
| Empresa / [TSIEMP](TSIEMP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Empresa / [TSIEMP](TSIEMP.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| Empresa / [TSIEMP](TSIEMP.md) | EmpresaGOL / [TSIEMP](TSIEMP.md) | EMPAGRUPARGOL->CODEMP | I |
| Empresa / [TSIEMP](TSIEMP.md) | EmpresaMatriz / [TSIEMP](TSIEMP.md) | CODEMPMATRIZ->CODEMP | I |
| Empresa / [TSIEMP](TSIEMP.md) | EmpresaPessoal /  | CODEMP->CODEMP | I |
| Empresa / [TSIEMP](TSIEMP.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Empresa / [TSIEMP](TSIEMP.md) | FuncionarioTomador / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Empresa / [TSIEMP](TSIEMP.md) | LocalRetirada / [TGFLCR](TGFLCR.md) | CODEMP->CODEMP | I |
| EmpresaCadastro / [TSIEMP](TSIEMP.md) | ChequeCancelado /  | CODEMP->CODEMP | I |
| EmpresaCadastro / [TSIEMP](TSIEMP.md) | EmpresaPessoal /  | CODEMP->CODEMP | I |
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
| EmpresaFuncionarioLider / [TSIEMP](TSIEMP.md) | EmpresaPessoal /  | CODEMP->CODEMP | I |
| EmpresaNegociacao / [TSIEMP](TSIEMP.md) | EmpresaPessoal /  | CODEMP->CODEMP | I |
| EmpresaParceiroInterdependente / [TGFPEIN](TGFPEIN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresasEtapasProcesso / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresaSoma / [TSIEMP](TSIEMP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| EmpresaSoma / [TSIEMP](TSIEMP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EmpresaTabPreco / [TGFEMP](TGFEMP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EquipamentoProtecaoIndividual / [TGFEPI](TGFEPI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Equipe / [TCSEQP](TCSEQP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFUN->CODEMP | I |
| Estoque / [TGFEST](TGFEST.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EtapaProducao / [TGFETA](TGFETA.md) | EmpresaDestinoEtapaProducao / [TSIEMP](TSIEMP.md) | CODEMPDEST->CODEMP | I |
| EtapaProducao / [TGFETA](TGFETA.md) | EmpresaOrigemEtapaProducao / [TSIEMP](TSIEMP.md) | CODEMPORIG->CODEMP | I |
| EtapasSelecaoCargo / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EtapasSelecaoEmpresa / [TRSECG](TRSECG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Financeiro / [TGFFIN](TGFFIN.md) | EmpresaBaixa / [TSIEMP](TSIEMP.md) | CODEMPBAIXA->CODEMP | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ImovelRural / [TSIEMP](TSIEMP.md) | CODIMOVELRURAL->CODEMP | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| FormatadorRemessaPorEmpresa / [TGFFEM](TGFFEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| InventarioBem / [TCICTE](TCICTE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemApuracaoSTAnteriorPorMedia / [TGFIAPURSTMEDENT](TGFIAPURSTMEDENT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Empresa / [TSIEMP](TSIEMP.md) | EMPRESA->CODEMP | I |
| ItemNota / [TGFITE](TGFITE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemRastreamentoEstoque / [TGFIRAST](TGFIRAST.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODINSTPRINC->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | EmpresaBaixa / [TSIEMP](TSIEMP.md) | CODINSTSECINI->CODEMP | I |
| ItemRegras / [TGFITR](TGFITR.md) | EmpresaMatriz / [TSIEMP](TSIEMP.md) | CODINSTSECFIN->CODEMP | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| LimitacoesIntervaloUnico / [TGMCFGI](TGMCFGI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Local / [TCILOC](TCILOC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| LocalRetirada / [TGFLCR](TGFLCR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Materiais / [TCAMAT](TCAMAT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MembroEquipe / [TCSMEQ](TCSMEQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFUN->CODEMP | I |
| MensagensMDe / [TGFMDEMSGS](TGFMDEMSGS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MensalidadePeriodo / [TCAPRE](TCAPRE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MetaAtual / [TGMMET](TGMMET.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MovimentacaoPisCofinsAquisicao / [TCIMOVAQ](TCIMOVAQ.md) | EmpresaContablidade / [TSIEMP](TSIEMP.md) | CODEMPCONTAB->CODEMP | I |
| NotaCancelada / [TGFCAN](TGFCAN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| NotaContingencia / [TGFICNF](TGFICNF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OrdemDespacho / [TGFODP](TGFODP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParametrosPdv / [TFXPAF](TFXPAF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPRESA->CODEMP | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Parceiro / [TGFPAR](TGFPAR.md) | EmpresaPreferencial / [TSIEMP](TSIEMP.md) | CODEMPPREF->CODEMP | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParcelamentoVencimento / [TCAPAR](TCAPAR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Periodo / [TCAPER](TCAPER.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PlanejamentoCompra / [TGFPCA](TGFPCA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PrecoMedioProdutoEmpresa / [TGFPMPE](TGFPMPE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | TimEmpresa / [TSIEMP](TSIEMP.md) | TIMPARCPROJ->CODEMP | I |
| QuantidadeParceiroUsuario / [TGFQPR](TGFQPR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RastreamentoEmpresa / [TGFRASTEMP](TGFRASTEMP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RedeAvaliadores / [TGPREL](TGPREL.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFUNC->CODEMP | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RemanejamentoTurma / [TCAREM](TCAREM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPDEST->CODEMP | I |
| SeparacaoFaturamento / [TGFFCI](TGFFCI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| SerieEmpresa / [TGFSPE](TGFSPE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| TermoAcordoApuracao / [TGFTAA](TGFTAA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | Empresa / [TSIEMP](TSIEMP.md) | ADMEMPRESANF->CODEMP | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | Empresa / [TSIEMP](TSIEMP.md) | IMBCODEMP->CODEMP | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Empresa / [TSIEMP](TSIEMP.md) | LOTEMPRESA->CODEMP | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | EmpresaContablidade / [TSIEMP](TSIEMP.md) | LOTEMPPARCERIA->CODEMP | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Empresa / [TSIEMP](TSIEMP.md) | TXCCODEMP->CODEMP | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPBAIXAORIG->CODEMP | I |
| Turma / [TCATUR](TCATUR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| UsoSeparacao / [TGFUSE](TGFUSE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Usuario / [TSIUSU](TSIUSU.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPFOLHA->CODEMP | I |
| VendaPendente / [TGFVCP](TGFVCP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Verbas / [TGFGEV](TGFGEV.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
