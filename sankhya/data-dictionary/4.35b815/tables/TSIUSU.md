# TSIUSU — Usuários

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 111
- Instâncias DWF: 27
- Relações DWF: 386
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Atendente | Atendente | S | N |
| Executante | Executante | S | N |
| ExecutanteRemetente | Executante Remetente | S | N |
| Liberador | Liberador | S | N |
| Responsavel | Responsável | S | N |
| Solicitante | Solicitante | S | N |
| TimExecutanteSac | Executantes do SAC | S | N |
| TimUsuarioAlteracao | Usuário de Alteração | S | N |
| TimUsuarioInclusao | Usuário de Inclusão | S | N |
| TimUsuarioOrigemSac | Usuários que lançaram o SAC | S | N |
| UltimoExecutante | Último Executante | S | N |
| Usuario | Usuário | S | S |
| Usuario2 | Usuário 2 | S | N |
| Usuario3 | Usuario ultima alteração | S | N |
| Usuario4 | Usuario responsavle cancelamento | S | N |
| UsuarioAvaliador | Avaliador | N | N |
| UsuarioBaixa | Usuário Baixa (Financeiro) | S | N |
| UsuarioCancelamento | UsuarioCancelamento | S | N |
| UsuarioCobranca | Unidade de Carregamento | S | N |
| UsuarioColeta | Usuário Coleta | S | N |
| UsuarioComprador | Usuário Comprador | S | N |
| UsuarioConferencia | Usuário Conferência | S | N |
| UsuarioFinal | Usuário Final | S | N |
| UsuarioLiberacao | Usuário Liberação | S | N |
| UsuarioPJLider | Usuário PJ Líder | S | N |
| UsuarioProtocolo | Usuário Protocolo | S | N |
| UsuarioResponsavel | Usuário Responsável | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABREGAVETA | Abre Gaveta | S |  |  | N |
| ACCOUNTDHEXPIRA | Data de expiração do Account TOKEN | H |  |  | N |
| ACCOUNTEMAIL | Sankhya ID | S |  |  | N |
| ACCOUNTID | Account ID | S |  |  | N |
| ACCOUNTNOMEFOTO | Nome da foto do Account | S |  |  | N |
| ACCOUNTPARTNER | Cód. Parceiro | I |  |  | N |
| ACCOUNTTOKEN | Account TOKEN | C |  |  | N |
| ACESSAFORMULAREL | Acessa fórmulas relacionadas | S |  |  | N |
| ACESSOPDVCANCITENS | Cancelamento de itens | S |  |  | N |
| ACESSOPDVSANG | Sangria | S |  |  | N |
| ACESSOPDVSANGPDESP | Sangria para despesas | S |  |  | N |
| ACESSOPDVSUPR | Suprimento | S |  |  | N |
| ACESSOVISUALCAB | Ver Pedidos/Notas | S |  |  | N |
| ALTCTAFAT | Altera conta bancária no faturamento? | S |  |  | N |
| ALTCTAIMPBOL | Altera conta bancária na impressão de boleto? | S |  |  | N |
| ALTORDCFECH | Altera Ordem de Carga fechada? | S |  |  | N |
| APENASCOMPLIB | Acessar apenas por computadores liberados? | S |  |  | N |
| APROVCOT | Aprovar cotação | S |  |  | N |
| ATUNUVERSAO | ATUNUVERSAO | S |  |  | N |
| AVISAVARPRECO | Acompanha alteração de custo(WMS) | S |  |  | N |
| BAIXADESP | Baixa Despesa | S |  |  | N |
| BAIXAREC | Baixa Receita | S |  |  | N |
| CAIXA | Caixa | S |  |  | N |
| CODCARGAACESSO | Carga horária para acesso | I |  |  | N |
| CODCENCUSPAD | Cód.Centro Resultado Padrão | I |  |  | N |
| CODCONTATOPERFIL | Contato Perfil | I |  |  | N |
| CODCTABCOINT | Conta | I |  |  | N |
| CODCTABCOINT2 | Conta 2 | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEQUIP | Equipamento Fiscal | I |  |  | N |
| CODETAPA | Etapa | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODGRUPO | Grupo | I |  |  | N |
| CODIDECONECT | Cód. Ident. | S |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCPERFIL | Cód. Parceiro Perfil | I |  |  | N |
| CODPERFIL | Cód. Perfil | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CONTACESSO | Controle | S |  |  | N |
| CONTAGOL | Controle GOL | S |  |  | N |
| CORCODIGO | Corretor | I |  |  | N |
| CPF | CPF | S |  |  | N |
| DESCTOTALNOTAPDV | Desconto no Total | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| DTLIMACESSO | Data limite de acesso | D |  |  | N |
| DTNASC | Data Nascimento | D |  |  | N |
| DTULTACESSO | Data Último Acesso | H |  |  | N |
| DTULTIMASENHA | Data da Última senha | H |  |  | N |
| EMAIL | Email | S |  |  | N |
| EMAILSOLLIB | Email p/ solicitação liberação | S |  |  | N |
| EXCLIBORC | Pode excluir liberações/negações de orçamento | S |  |  | N |
| EXIBIRVALANALRENT | Exibir valores na Análise de Rentabilidade? | S |  |  | N |
| FOTO | Foto | B |  |  | N |
| GESTOR | Gestor | S |  |  | N |
| GRUPOREDE | Grupo de Rede | S |  |  | N |
| IDPERFILECONECT | Cód. Perfil | S |  |  | N |
| IGNORAHORASCRUZ | Ignora Horas Cruzadas | S |  |  | N |
| IGNORALDAP | Ignorar login via LDAP | S |  |  | N |
| IMP2SANSUPCAI | Imprime segunda via sangria suprimento e fechamento de caixa | S |  |  | N |
| IMPLANTADOR | Implantador | S |  |  | N |
| IMPNFCENTRAL | Imprimir/Reimprimir Nota nas Centrais | S |  |  | N |
| INFRECSEN | Informação de Recuperação de Senha | S |  |  | N |
| INSTALAPACOTESS | Permite instalar pacote Sankhya Store? | S |  |  | N |
| INTEGRAECONECT | Integrar com EConect | S |  |  | N |
| INTERNO | Senha | S |  |  | N |
| LOCALE | Idioma | S |  |  | N |
| MINUTOSFIN | Finaliza se Inativo em (minutos) | I |  |  | N |
| NIVEL | Nível | I |  |  | N |
| NIVELACESSO | Nível de Acesso | S |  |  | N |
| NOMEFILA | Nome Fila p/ num. boleto | S |  |  | N |
| NOMEUSU | Nome | S |  |  | N |
| NOMEUSUCPLT | Nome Completo | S |  |  | N |
| NOTSLAFILA | Notifica Fila SLA | S |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| OPERACIONAL | Operacional | S |  |  | N |
| PERMALTMOEDA | Permite alterar valor de moeda na baixa? | S |  |  | N |
| PERMEXPREL | Permite exportar relatórios ? | S |  |  | N |
| PERMIMPRIMEREL | Permite imprimir relatórios? | S |  |  | N |
| PERMREPERRO | Permite reportar erros ? | S |  |  | N |
| PORTASMTP | Porta SMTP | I |  |  | N |
| PROPRIETARIO | Proprietário | S |  |  | N |
| RENDIASVALJUR | Dias tol. juros reneg | I |  |  | N |
| RESTRINGECART | Restringir carteira? | S |  |  | N |
| RG | RG | S |  |  | N |
| SEGURANCASMTP | Conexão segura? | S |  |  | N |
| SELECTWCAPO | Seleciona Centro de Trabalho em Apontamento de Produção | S |  |  | N |
| SENHAANTIGA | Senha Antiga | S |  |  | S |
| SENHAECONECT | Senha | I |  |  | N |
| SENHANOVA | Senha Nova | S |  |  | S |
| SENHANUNCAEXPIRA | Senha expira | S |  |  | N |
| SENHAREPETE | Senha Repetir | S |  |  | S |
| SENHASMTP | Senha SMTP | S |  |  | N |
| SERVIDORSMTP | Servidor de SMTP | S |  |  | N |
| TEMECF | Tem ECF | S |  |  | N |
| TIMBAIXAWORD | Permitir Baixar Word | S |  |  | N |
| TIMBAIXTITRECABE | Baixar tít. reg. lote c/tít. entrada/comissão em aberto | S |  |  | N |
| TIMPATHSCANNER | Path Scanner | S |  |  | N |
| TIMVERTODASFACS | Visualizar todas as FACs? | S |  |  | N |
| TIPENVNOTSOL | Notificar solicitante de liberações | S |  |  | N |
| TIPOSMTP | Tipo de SMTP | S |  |  | N |
| TIPOUSU | Tipo do Usuário | I |  |  | N |
| TOKENCHECKOUT | Token de Identificação do Checkout | S |  |  | N |
| TOLERANCIAACESSO | Tolerância acesso fora horário | I |  |  | N |
| TOPBAIXADESPESA | Tipo Operação Baixa Despesa | I |  |  | N |
| TOPBAIXARECEITA | Tipo Operação Baixa Receita | I |  |  | N |
| USUARIOSMTP | Usuário SMTP | S |  |  | N |
| USUREDE | Usuário de Rede | S |  |  | N |
| VERCABPROPRIA | Ver as Próprias Notas | S |  |  | N |
| VISACESOUTUSU | Visualiza acesso de outros Usuários | S |  |  | N |
| VLRMAXAUT | Valor Máximo de autorização | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcaoAgendada / [TSIAAG](TSIAAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULOGIN->CODUSU | I |
| AcessoRemoto / [TSIACR](TSIACR.md) | Usuario / [TSIUSU](TSIUSU.md) | USUARIOOM->CODUSU | I |
| AcessoRemoto / [TSIACR](TSIACR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | USUARIOAUTORIZOU->CODUSU | I |
| AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AcompanhamentoEtapa / [TGFEAC](TGFEAC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AcompanhamentoNota / [TGFACT](TGFACT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Acordo / [TGFACO](TGFACO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| AdiantamentoBaixaFinanceiro / [TGFABF](TGFABF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AdiantamentoSaldoFlex / [TGFASA](TGFASA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Agendamento / [TCSAGE](TCSAGE.md) | Executante / [TSIUSU](TSIUSU.md) | EXECUTANTE->CODUSU | I |
| Agendamento / [TCSAGE](TCSAGE.md) | Responsavel / [TSIUSU](TSIUSU.md) | RESPONSAVEL->CODUSU | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODSOLICITANTE->CODUSU | I |
| AnaliseCreditoSerasa / [TSERACS](TSERACS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Anexo / [TSIATA](TSIATA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AnexoSistema / [TSIANX](TSIANX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AnexoSistema / [TSIANX](TSIANX.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALT->CODUSU | I |
| ApontamentoExecucaoTarefa / [TWFIEXE](TWFIEXE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ApontamentoExecucaoTarefa / [TWFIEXE](TWFIEXE.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ApuracaoArmazenagemDesc / [TGADES](TGADES.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ArquivoRetornoCartao / [TGFARC](TGFARC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AuditoriaCadastroClienteCred / [TGFCREDAUDIT](TGFCREDAUDIT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAUT->CODUSU | I |
| AutorizacaoInvestimento / [TGMAI](TGMAI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| AvisoSistema / [TSIAVI](TSIAVI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| BaseStMediaVenda / [TGFSTMV](TGFSTMV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| BaseStMediaVendaHist / [TGFSTMVH](TGFSTMVH.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| BloqDesbloqEstoque / [TGABD](TGABD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUDES->CODUSU | I |
| BloqDesbloqEstoque / [TGABD](TGABD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUBLOQ->CODUSU | I |
| BloqueioControle / [TRDBLC](TRDBLC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoApurSTAnt / [TGFAPURSTMEDENT](TGFAPURSTMEDENT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCONF->CODUSU | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | UsuarioComprador / [TSIUSU](TSIUSU.md) | CODUSUREQ->CODUSU | I |
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | UsuarioResponsavel / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| CabecalhoNotaExcluida / [TGFCAB_EXC](TGFCAB_EXC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| CabecalhoRastreamentoEstoque / [TGFCRAST](TGFCRAST.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CacheServicoBoaVista / [TGFBOA](TGFBOA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CacheServicoEquifax / [TGFEQX](TGFEQX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | Usuario / [TSIUSU](TSIUSU.md) | AUTOR->CODUSU | I |
| CadastroFiltrosAPI / [TSICFA](TSICFA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | ALTERADOPOR->CODUSU | I |
| CadastroSefaz / [TGFCSZ](TGFCSZ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Candidato / [TRSCAN](TRSCAN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAPROV->CODUSU | I |
| CaracteristicaAnalisavelUsu / [TGFCAU](TGFCAU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCLC->CODUSU | I |
| CartaoCredito / [TGFCCR](TGFCCR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CategoriaMedicamento / [TGFCAT](TGFCAT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CentroResultado / [TSICUS](TSICUS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ClasseTerapeutica / [TGFTER](TGFTER.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ClassificacaoMedicamento / [TGFCPR](TGFCPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Usuario / [TSIUSU](TSIUSU.md) | USUSOL->CODUSU | I |
| ComissaoNegociacao / [TCSCNV](TCSCNV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAPROV->CODUSU | I |
| ConsolidacaoAcessos / [TGFUCM](TGFUCM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ConsolidacaoConfig / [TGFCMV](TGFCMV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ConsolidadorDados / [TSICND](TSICND.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODOPEREXCL->CODUSU | I |
| ContaSincAgenda / [TCSSAG](TCSSAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Contato / [TGFCTT](TGFCTT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ContaUsuario / [TSIUCT](TSIUCT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ContingenciaLoginUnico / [TSLCLU](TSLCLU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Contrato / [TCSCON](TCSCON.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Contrato / [TCSCON](TCSCON.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODMONSANKHYA->CODUSU | I |
| Contrato / [TCSCON](TCSCON.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ContratoCivil / [TCCCON](TCCCON.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUVAR->CODUSU | I |
| ControleAcessoDashboardsGOL / [TSIACD](TSIACD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ControleAcessoRelatorio / [TSIACI](TSIACI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ControleAlteracaoBanco / [TCSALT](TCSALT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ControleTentativaLogin / [TSICTLG](TSICTLG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CredenciaisProcesso / [TWFCRD](TWFCRD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| CredenciaisProcesso / [TWFCRD](TWFCRD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUPUBLICACAO->CODUSU | I |
| DashboardPlayer / [TSITVP](TSITVP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| DbExplorerQuery / [TSIDBQUERY](TSIDBQUERY.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| DetailMetaForecast / [TGMDFM](TGMDFM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EmailCotacaoEnviada / [TGFENV](TGFENV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EntidadesRecentesFavoritas / [TSIERF](TSIERF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EquipeEtapa / [TCSFEE](TCSFEE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EquipeProjeto / [TCSFEQ](TCSFEQ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EstatisticaServicoSistema / [TSIESTATSERVICO](TSIESTATSERVICO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EtapaPersonalizada / [TRSEPE](TRSEPE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| EventosCheque / [TGFECQ](TGFECQ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ExcecoesUsuariosLgpd / [TSIEXU](TSIEXU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ExecutantePorFluxoProcesso / [TCSEFL](TCSEFL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ExecutantesHorasCruzadas / [TCSEXN](TCSEXN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODEXECUTANTE->CODUSU | I |
| ExercicioGestaoIndicadores / [TMIEXE](TMIEXE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ExtratorDados / [TSIEXT](TSIEXT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| FilaImpressaoBoleta / [TSIFIL](TSIFIL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| FilaImpressaoTopUsuario / [TSIUFI](TSIUFI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | TIMREPINTELIGENTE->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | UsuarioBaixa / [TSIUSU](TSIUSU.md) | CODUSUBAIXA->CODUSU | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| GadgetBiaConhecimento / [TSIGBC](TSIGBC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| GestorProcesso / [TWFGPR](TWFGPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODGESTOR->CODUSU | I |
| GrupoAnaliseCredito / [TGCCRED](TGCCRED.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| GrupoInformacaoAnaliseCred / [TGCGRU](TGCGRU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| GruposPorUsuario / [TSIGPU](TSIGPU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoAlteracaoFAP / [TCSFHA](TCSFHA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoAlteracaoRegras / [TSIHAB](TSIHAB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoCabecalhoPendente / [TGFHCP](TGFHCP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoCopiaConfiguracao / [TSIHCF](TSIHCF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoEtapa / [TCSEHI](TCSEHI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoItemPendente / [TGFHIP](TGFHIP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoPedido / [TGFHPE](TGFHPE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoSankhyaStore / [TSSHIS](TSSHIS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCAD->CODUSU | I |
| HistoricoStatusProjeto / [TCSFHS](TCSFHS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoSuplementacao / [TCSHSE](TCSHSE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| HistoricoTransPro / [TGAHTP](TGAHTP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImagemAlternativaProduto / [TGFIMAL](TGFIMAL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoLegacyAcessos / [TSIILA](TSIILA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoTributoFederal / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoTributoICMS / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoTributoIPI / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUIMP->CODUSU | I |
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUPROC->CODUSU | I |
| ImpostoItemNota / [TGFDIN](TGFDIN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImpostoItemVR / [TLFDINVR](TLFDINVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImpressoraSubstituta / [TSISBP](TSISBP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Indicador / [TMIIND](TMIIND.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| IndiceEfetividade / [TCSIE](TCSIE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| InformacaoCreditoParceiro / [TGCCAL](TGCCAL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| InformacaoNecessariaCalc / [TGCINF](TGCINF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| InformacaoQuestionario / [TPQINF](TPQINF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUCANCEL->CODUSU | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUDONO->CODUSU | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUSOLICITANTE->CODUSU | I |
| InstanciaTarefa / [TWFITAR](TWFITAR.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| IsencaoQuebra / [TGAIQT](TGAIQT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemCotacao / [TGFITC](TGFITC.md) | Usuario / [TSIUSU](TSIUSU.md) | USUSOL->CODUSU | I |
| ItemCotacao / [TGFITC](TGFITC.md) | UsuarioResponsavel / [TSIUSU](TSIUSU.md) | USURESP->CODUSU | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | ExecutanteRemetente / [TSIUSU](TSIUSU.md) | CODUSUREM->CODUSU | I |
| JuroMensal / [TGFJUM](TGFJUM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| KeepConnection / [TSIKPC](TSIKPC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LiberacaoAcessoPC / [TSILBA](TSILBA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUSOLIC->CODUSU | I |
| LiberacaoAcessoPC / [TSILBA](TSILBA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSULIBER->CODUSU | I |
| LiberacaoBloqueio / [TGFLIB](TGFLIB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LiberacaoCascata / [TGFLIC](TGFLIC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| LiberacaoFiltros / [TSILBF](TSILBF.md) | Usuario / [TSIUSU](TSIUSU.md) | AUTOR->CODUSU | I |
| LiberacaoFiltros / [TSILBF](TSILBF.md) | Usuario2 / [TSIUSU](TSIUSU.md) | APROVADOPOR->CODUSU | I |
| LiberacaoFiltros / [TSILBF](TSILBF.md) | Usuario3 / [TSIUSU](TSIUSU.md) | REPROVADOPOR->CODUSU | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| LiberacaoLimite / [TSILIB](TSILIB.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUSOLICIT->CODUSU | I |
| LicencaAtiva / [TCSLIATIVA](TCSLIATIVA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LicencaPendente / [TCSLIPENDENTE](TCSLIPENDENTE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LimiteCredito / [TGCPAR](TGCPAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LimiteLiberacao / [TSILIM](TSILIM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LimiteLiberacaoBonificacao / [TSILBO](TSILBO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LimiteOrcamentarioCR / [TSILOR](TSILOR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Local / [TCILOC](TCILOC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LogAcessos / [TSIRLG](TSIRLG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LogApontamento / [TAPLOG](TAPLOG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LogImportacaoLegacy / [TSILIL](TSILIL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LogOcorrenciasBancarias / [TSIOBCLOG](TSIOBCLOG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LogSistema / [TSILOG](TSILOG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LoteFDIC / [TGFFDIC](TGFFDIC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ManifestoConsultaDocumento / [TGFMDECD](TGFMDECD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ManutencaoProgramada / [TSIMPA](TSIMPA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MensagemEntreUsuario / [TGFMEN](TGFMEN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUORIG->CODUSU | I |
| MensagemEntreUsuario / [TGFMEN](TGFMEN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUDEST->CODUSU | I |
| ModeloEmail / [TSIMEM](TSIMEM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUREMET->CODUSU | I |
| MonitoriaTEF / [TGFMTEF](TGFMTEF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MovimentacaoCaixa / [TGFMCX](TGFMCX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDFilaMensagem / [TMDFMG](TMDFMG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDMensagem / [TMDMSG](TMDMSG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| NegociacaoHorasCruzadas / [TCSNHC](TCSNHC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| NivelCargo / [TGPNVC](TGPNVC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| NotaPendenteAutorizacao / [TGFNPA](TGFNPA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| OcorrenciasManifesto / [TGFOMDF](TGFOMDF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Atendente / [TSIUSU](TSIUSU.md) | CODATEND->CODUSU | L |
| OrdemServico / [TCSOSE](TCSOSE.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | L |
| OrdemServico / [TCSOSE](TCSOSE.md) | Solicitante / [TSIUSU](TSIUSU.md) | CODUSUSOLICITANTE->CODUSU | I |
| PacotesSankhyaStore / [TSSPAC](TSSPAC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCAD->CODUSU | I |
| ParametroSistema / [TSIPAR](TSIPAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ParametrosPdv / [TFXPAF](TFXPAF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUARIO->CODUSU | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Parceiro / [TGFPAR](TGFPAR.md) | UsuarioCobranca / [TSIUSU](TSIUSU.md) | CODUSUCOBR->CODUSU | I |
| ParceirosRota / [TGFRTP](TGFRTP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PedidoPorEvento / [TGFPEV](TGFPEV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PermissaoAcesso / [TSIPER](TSIPER.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PermissoesPorOcorrencia / [TGFPPO](TGFPPO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Planejamento / [TPQPLA](TPQPLA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAVALIADOR->CODUSU | I |
| PlanejamentoEntrega / [TGFPLAN](TGFPLAN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PlanoAcao / [TMIPLA](TMIPLA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUEXEC->CODUSU | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODRESP->CODUSU | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUULTALT->CODUSU | I |
| PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PrecoPorFaixa / [TCSPPF](TCSPPF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PrevisaoEntrega / [TGFDTP](TGFDTP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Processo / [TGFPRC](TGFPRC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUDONO->CODUSU | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESPONSAVEL->CODUSU | I |
| ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODCOORD->CODUSU | I |
| PropostaNegociacaoPreVenda / [TCSPNP](TCSPNP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProrrogacaoVendaMais / [TGFPRORVM](TGFPRORVM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUPROR->CODUSU | I |
| QuantidadeParceiroUsuario / [TGFQPR](TGFQPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RealizadoMeta / [TMIRME](TMIRME.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegistroAlteracaoFinanceiro / [TGFRAF](TGFRAF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegistroAvaria / [TGFAVR](TGFAVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegistroVolume / [TGFVOR](TGFVOR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegraNegocio / [TGFRNG](TGFRNG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ReintegraPrevidencia / [TGFRNT](TGFRNT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RelacaoPDVUsuario / [TGFPWU](TGFPWU.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Atendente / [TSIUSU](TSIUSU.md) | CODATENDENTE->CODUSU | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RelacionamentoUsuario / [TCSRUS](TCSRUS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RelacionamentoUsuario / [TCSRUS](TCSRUS.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUREL->CODUSU | I |
| Relatorio / [TSIRFE](TSIRFE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCOORCAMPO->CODUSU | I |
| RelatorioProducao / [THGPRD](THGPRD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUCOORPROJETO->CODUSU | I |
| Renegociacao / [TGFREN](TGFREN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUREQUISITANTE->CODUSU | I |
| RespostaPergunta / [TPQRES](TPQRES.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | UsuarioAvaliador / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ServidorImpressao / [TSISVP](TSISVP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| SessaoIteracaoTela / [TSISESSAOITERACAO](TSISESSAOITERACAO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| SimulacaoPrecoNegociacao / [TCSSPN](TCSSPN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODSOLICITANTE->CODUSU | I |
| SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODAPROVADOR->CODUSU | I |
| StatusPedido / [TGFSTA](TGFSTA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TasksHistorico / [TCSLIHIST](TCSLIHIST.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TasksHistorico / [TCSLIHIST](TCSLIHIST.md) | UsuarioLiberacao / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | Usuario / [TSIUSU](TSIUSU.md) | ACMUSUINCLUSAO->CODUSU | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | Usuario2 / [TSIUSU](TSIUSU.md) | ACMUSUALTER->CODUSU | I |
| TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | Usuario / [TSIUSU](TSIUSU.md) | SCAUSUARIO->CODUSU | I |
| TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | SCAUSUDEST->CODUSU | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | Usuario / [TSIUSU](TSIUSU.md) | SACUSUARIO->CODUSU | I |
| TimBenfeitoriasLote / [TIMLBF](TIMLBF.md) | Usuario / [TSIUSU](TSIUSU.md) | LBFUSUARIO->CODUSU | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | ADMUSUARIO->CODUSU | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | ADMCODUSUINC->CODUSU | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Usuario / [TSIUSU](TSIUSU.md) | LTVUSUINCLUSAO->CODUSU | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Usuario2 / [TSIUSU](TSIUSU.md) | LTVUSUALTERACAO->CODUSU | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | CORUSUARIOALT->CODUSU | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | CORUSUARIO->CODUSU | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | DTLUSUARIOALT->CODUSU | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | DTLUSUARIO->CODUSU | I |
| TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | Usuario / [TSIUSU](TSIUSU.md) | LDTUSUARIO->CODUSU | I |
| TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | Usuario2 / [TSIUSU](TSIUSU.md) | LDTUSUARIOALT->CODUSU | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Usuario / [TSIUSU](TSIUSU.md) | EPRUSUARIOALT->CODUSU | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | EPRUSUARIO->CODUSU | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Usuario / [TSIUSU](TSIUSU.md) | FACUSUARIO->CODUSU | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | Usuario2 / [TSIUSU](TSIUSU.md) | FACUSUALTER->CODUSU | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | IMVUSUARIO->CODUSU | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | IMVCODUSUINC->CODUSU | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Usuario / [TSIUSU](TSIUSU.md) | IMVUSUARIO->CODUSU | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | Usuario / [TSIUSU](TSIUSU.md) | IPRUSUARIO->CODUSU | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | IPRCODUSUENT->CODUSU | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | UsuarioBaixa / [TSIUSU](TSIUSU.md) | IPRCODUSUDEV->CODUSU | I |
| TimImovelLugarCerto / [TIMILC](TIMILC.md) | Usuario / [TSIUSU](TSIUSU.md) | ILCUSUARIO->CODUSU | I |
| TimInteresseProspect / [TIMLPP](TIMLPP.md) | Usuario / [TSIUSU](TSIUSU.md) | LPPUSUARIO->CODUSU | I |
| TimIptuReajuste / [TIMRIP](TIMRIP.md) | Usuario / [TSIUSU](TSIUSU.md) | RIPUSUINC->CODUSU | I |
| TimIptuReajuste / [TIMRIP](TIMRIP.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RIPUSUALTER->CODUSU | I |
| TimItensCheckList / [TIMCHI](TIMCHI.md) | Usuario / [TSIUSU](TSIUSU.md) | CHIUSUARIO->CODUSU | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | LOCUSUARIO->CODUSU | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | LOCCODUSUINC->CODUSU | I |
| TimLote / [TIMLTE](TIMLTE.md) | Usuario / [TSIUSU](TSIUSU.md) | LTEUSUINCLUSAO->CODUSU | I |
| TimLote / [TIMLTE](TIMLTE.md) | Usuario2 / [TSIUSU](TSIUSU.md) | LTEUSUALTERACAO->CODUSU | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Usuario / [TSIUSU](TSIUSU.md) | MFDUSUINCLUIU->CODUSU | I |
| TimMFD / [TIMMFD](TIMMFD.md) | Usuario2 / [TSIUSU](TSIUSU.md) | MFDUSUALTEROU->CODUSU | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | LCRUSUARIO->CODUSU | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | LCRCODUSUINC->CODUSU | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | Usuario / [TSIUSU](TSIUSU.md) | PPVUSUINCLUSAO->CODUSU | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | Usuario2 / [TSIUSU](TSIUSU.md) | PPVUSUALTERACAO->CODUSU | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | Usuario / [TSIUSU](TSIUSU.md) | IMPUSUARIO->CODUSU | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Usuario / [TSIUSU](TSIUSU.md) | PPRUSUARIO->CODUSU | I |
| TimProspect / [TIMPPR](TIMPPR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | PPRUSUALTER->CODUSU | I |
| TimReajuste / [TIMRET](TIMRET.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | RETDTUSUALTEROU->CODUSU | I |
| TimReajuste / [TIMRET](TIMRET.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | RETDTUSUINCLIU->CODUSU | I |
| TimReajusteLocacao / [TIMRLO](TIMRLO.md) | Usuario / [TSIUSU](TSIUSU.md) | RLOUSUINCLUSAO->CODUSU | I |
| TimReajusteLocacao / [TIMRLO](TIMRLO.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RLOUSUALTERACAO->CODUSU | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | Usuario / [TSIUSU](TSIUSU.md) | RENUSUINCLUSAO->CODUSU | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RENUSUALTERACAO->CODUSU | I |
| TimRenovacao / [TIMRCT](TIMRCT.md) | Usuario / [TSIUSU](TSIUSU.md) | RCTCODUSUINCLUSAO->CODUSU | I |
| TimRenovacao / [TIMRCT](TIMRCT.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RCTCODUSUALTERACAO->CODUSU | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | Usuario / [TSIUSU](TSIUSU.md) | CTRCODUSUINCLUSAO->CODUSU | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CTRCODUSUALTERACAO->CODUSU | I |
| TimRescisao / [TIMDLV](TIMDLV.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | DLVUSUALTERACAO->CODUSU | I |
| TimRescisao / [TIMDLV](TIMDLV.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | DLVUSUINCLUSAO->CODUSU | I |
| TimRescisao / [TIMDLV](TIMDLV.md) | Usuario / [TSIUSU](TSIUSU.md) | DLVUSUARIO->CODUSU | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | Usuario / [TSIUSU](TSIUSU.md) | FACUSUARIO->CODUSU | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | Usuario / [TSIUSU](TSIUSU.md) | VDOUSUINC->CODUSU | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | Usuario2 / [TSIUSU](TSIUSU.md) | VDOUSUALT->CODUSU | I |
| TipoRecurso / [TCSTFU](TCSTFU.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TrabalhoImpressao / [TSIJPR](TSIJPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TSI001 / [TSI001](TSI001.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| TSI001 / [TSI001](TSI001.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| UnidadeGerencial / [TMIUNG](TMIUNG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | AcessoUnidadeGerencial / [TMIAUN](TMIAUN.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | CODEQUIP->CODEQUIP | I |
| Usuario / [TSIUSU](TSIUSU.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUSPAD->CODCENCUS | I |
| Usuario / [TSIUSU](TSIUSU.md) | CertificacaoRegra / [TGFCER](TGFCER.md) | CODUSU->CHAVE | I |
| Usuario / [TSIUSU](TSIUSU.md) | ContaSincAgenda / [TCSSAG](TCSSAG.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATOPERFIL->CODCONTATO; CODPARCPERFIL->CODPARC | I |
| Usuario / [TSIUSU](TSIUSU.md) | ContaUsuario / [TSIUCT](TSIUCT.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | DashboardPlayer / [TSITVP](TSITVP.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | DataSourceUsuario / [TSIDSU](TSIDSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Usuario / [TSIUSU](TSIUSU.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| Usuario / [TSIUSU](TSIUSU.md) | FilaImpressaoBoleta / [TSIFIL](TSIFIL.md) | NOMEFILA->NOMEFILA | I |
| Usuario / [TSIUSU](TSIUSU.md) | FilaImpressaoTopUsuario / [TSIUFI](TSIUFI.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | Funcionario /  | CODEMP->CODEMP; CODFUNC->CODFUNC | I |
| Usuario / [TSIUSU](TSIUSU.md) | GruposPorUsuario / [TSIGPU](TSIGPU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | GrupoUsuario / [TSIGRU](TSIGRU.md) | CODGRUPO->CODGRUPO | I |
| Usuario / [TSIUSU](TSIUSU.md) | IdentificacaoCargaHoraria /  | CODCARGAACESSO->CODCARGAHOR | I |
| Usuario / [TSIUSU](TSIUSU.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Usuario / [TSIUSU](TSIUSU.md) | ParceiroPrestador / [TGFPAR](TGFPAR.md) | CODPARCPERFIL->CODPARC | I |
| Usuario / [TSIUSU](TSIUSU.md) | QuantidadeParceiroUsuario / [TGFQPR](TGFQPR.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | RoteamentoImpressaoUsuario / [TSIRIM](TSIRIM.md) | CODUSU->CODIGO | I |
| Usuario / [TSIUSU](TSIUSU.md) | ServicoProdutoExecutante / [TGFSEU](TGFSEU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CORCODIGO->CORCODIGO | I |
| Usuario / [TSIUSU](TSIUSU.md) | TOPDespesa / [TGFTOP](TGFTOP.md) | TOPBAIXADESPESA->CODTIPOPER | I |
| Usuario / [TSIUSU](TSIUSU.md) | TOPReceita / [TGFTOP](TGFTOP.md) | TOPBAIXARECEITA->CODTIPOPER | I |
| Usuario / [TSIUSU](TSIUSU.md) | UsuarioSuplementar / [TSISUPL](TSISUPL.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | UsuarioTarefa /  | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | UsuarioUnidade /  | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| UsuarioCandidatoProcesso / [TWFUCP](TWFUCP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioFiltroAPI / [TSIUFA](TSIUFA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioFirebaseMessaging / [TSIFCM](TSIFCM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioLiberadorMeta / [TGMUSLB](TGMUSLB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioMonitorado / [TGFMNT](TGFMNT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioPorUnidadeGerencial / [TMIUXG](TMIUXG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODSUBSTITUTO->CODUSU | I |
| UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODANTERIOR->CODUSU | I |
| UsuarioSuplementar / [TSISUPL](TSISUPL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| UsuarioSuplementar / [TSISUPL](TSISUPL.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUSUPL->CODUSU | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODPARC->CODPARC | I |
| VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Verbas / [TGFGEV](TGFGEV.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODRESP->CODUSU | I |
| Verbas / [TGFGEV](TGFGEV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUULTALT->CODUSU | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewCustomizada / [TSICVIEWS](TSICVIEWS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | UsuarioBaixa / [TSIUSU](TSIUSU.md) | CODUSUBAIXA->CODUSU | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | UsuarioBaixa / [TSIUSU](TSIUSU.md) | CODUSUBAIXA->CODUSU | I |
| ViewInstanciaTarefa / [VWFITAR](VWFITAR.md) | Usuario / [TSIUSU](TSIUSU.md) | PROCESSUSERCREATECOD->CODUSU; TASKUSERASSIGNEECOD->CODUSU | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Solicitante / [TSIUSU](TSIUSU.md) | CODUSUSOLICIT->CODUSU | I |
| ViewLiberacaoLimite / [VSILIB](VSILIB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSULIB->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUINC->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario2 / [TSIUSU](TSIUSU.md) | SOLICITANTE->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUDONO->CODUSU | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | Usuario4 / [TSIUSU](TSIUSU.md) | CODUSUCANCEL->CODUSU | I |
| ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODUSURESPONSAVEL->CODUSU | I |
| ViewSelecaoEtapaCandidato / [VGFSELECAO_ETAPA_CANDIDATO](VGFSELECAO_ETAPA_CANDIDATO.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | Usuario / [TSIUSU](TSIUSU.md) | SOLICITANTE->CODUSU | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RESPONSAVEL_TAREFA_ATUAL->CODUSU | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUCANCEL->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
