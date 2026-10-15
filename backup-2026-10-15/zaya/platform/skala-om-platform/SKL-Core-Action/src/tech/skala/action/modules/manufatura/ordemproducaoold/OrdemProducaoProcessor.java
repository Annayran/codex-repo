package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.CbmModelException;
import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.mgeprod.model.helper.ApontamentoHelper;
import br.com.sankhya.mgeprod.model.helper.ControleProcessoHelper;
import br.com.sankhya.mgeprod.model.helper.MomentoOperacaoEstoque;
import br.com.sankhya.mgeprod.model.helper.OperacaoProducaoHelper;
import br.com.sankhya.mgeprod.model.helper.OperacoesEstoqueHelper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.ordemproducaoold.ApontamentoProducaoExceptions.NotaProducaoNaoGeradaException;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducaoSituacaoEnum;

/**
 * Classe utilitaria para processamento de uma ordem de producao.
 * <p>
 * Classe utilitaria contendo metodos que efetuam modificacoes em uma ordem de producao
 * como inicializacao, apontamento, geracao de nota de producao, etc. visando 
 * automatizacao de tarefas do usuario.
 * <p>
 * 
 * @author cassiomenezes
 * @since 2021-11-21
 */
public class OrdemProducaoProcessor {
	private final Logger					logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final EntityFacade				dwfFacade = EntityFacadeFactory.getDWFFacade();
	
	private JdbcWrapper						jdbc;
	private BigDecimal						idiProc;
	private LancamentoDeOP					lancamentoDeOP;
	private OrdemProducao					ordemProducao;
	private BigDecimal						codusu;
	
	public OrdemProducaoProcessor(JdbcWrapper jdbc) {
		this.jdbc = jdbc;
	}

	public OrdemProducaoProcessor(JdbcWrapper jdbc, OrdemProducao ordemProducao) {
		this.jdbc = jdbc;
		this.codusu = AuthenticationInfo.getCurrent().getUserID();
		setOrdemProducao(ordemProducao);
	}
	
	public OrdemProducaoProcessor(JdbcWrapper jdbc, BigDecimal idiProc) {
		this.jdbc = jdbc;
		this.codusu = AuthenticationInfo.getCurrent().getUserID();
		this.idiProc = idiProc;
	}

	public OrdemProducaoProcessor(JdbcWrapper jdbc, BigDecimal idiProc, LancamentoDeOP lancamentoDeOP) {
		this.jdbc = jdbc;
		this.codusu = AuthenticationInfo.getCurrent().getUserID();
		this.idiProc = idiProc;
		this.lancamentoDeOP = lancamentoDeOP;
	}
	
	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
		this.idiProc = ordemProducao.getIdiProc();
	}

	public void criarApontamentoProducao() throws CbmModelException, SkalaModelException {
		loadOrdemProducao();
		
		inicializarOrdemProducao();
		loadAtividades();
		
		criarApontamentosProducao();
		
		if (lancamentoDeOP.isTerceiro())
			incluirTarefasTerceiro();
	}
	
	private void loadOrdemProducao() throws CbmModelException {
		if (ordemProducao == null) {
			this.ordemProducao = new OrdemProducao();
			this.ordemProducao.setIdiProc(idiProc);
			this.ordemProducao.load();
		}
	}
	
	public void inicializarOrdemProducao() throws CbmModelException {
		String msg;
		
		if (OrdemProducaoStatusEnum.CRIADO.getValue().equalsIgnoreCase(ordemProducao.getStatusProc())) {
			msg = String.format("[OP %s] Inicializando Ordem de Producao...", idiProc);
			logger.info(msg);
			
			try {
				ControleProcessoHelper cph = new ControleProcessoHelper(jdbc);
				cph.iniciaOrdemDeProducao(idiProc);
				
			} catch (Exception e) {
				msg = String.format("[OP %s] Não foi possível inicializar a Ordem de Produção.", idiProc);
				msg = CbmModelException.buildErrorMessage(e, msg);
				throw new CbmModelException(msg,e);
			}

		} else {
			msg = String.format("[OP %s] Ordem de Producao inicializada.", idiProc);
			logger.info(msg);
		}
		
	}
	
	private void loadAtividades() throws CbmModelException {
		if (ordemProducao.getAtividades().isEmpty())
			this.ordemProducao.loadAtividades();
	}

	private void criarApontamentosProducao() throws SkalaModelException {
		if (OrdemProducaoStatusEnum.FINALIZADO.getValue().equalsIgnoreCase(ordemProducao.getStatusProc()))
			return;
		
		BigDecimal idiAtv;
		for (InstanciaAtividade atividade : ordemProducao.getAtividades()) {
			idiAtv = atividade.getIdiAtv();
			if (InstanciaAtividadeSituacaoEnum.AGUARDANDO_ACEITE.getValue().equals(atividade.getSituacao()))
				iniciarInstanciaAtividades(idiAtv);
			
			criarApontamentos(idiAtv);
			loadApontamentos(idiAtv);
		}

	}
	
	private void iniciarInstanciaAtividades(BigDecimal idiAtv) throws SkalaModelException {
		String msg;

		msg = String.format("[OP %s] Iniciando atividade %s...", idiProc, idiAtv);
		logger.info(msg);
		
		try {
			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance(dwfFacade);
			helper.iniciarInstanciaAtividade(idiAtv, jdbc);
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível iniciar Atividade %s da Ordem de Produção.", idiProc, idiAtv);
			SkalaModelException.throwMe(msg, e);
		}
	}

	private void criarApontamentos(BigDecimal idiAtv) throws SkalaModelException {
		String msg;

		msg = String.format("[OP %s] Criando apontamentos para atividade %s...", idiProc, idiAtv);
		logger.info(msg);
		
		ApontamentoHelper apt = new ApontamentoHelper(jdbc);
		try {
			apt.criarApontamentosAtividade(codusu, idiAtv);
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível criar Apontamentos para Atividade %s da Ordem de Produção.",
					idiProc, idiAtv);
			SkalaModelException.throwMe(msg, e);
		}
	}
	
	private void loadApontamentos(BigDecimal idiAtv) throws SkalaModelException {
		if (ordemProducao.getApontamentos().isEmpty())
			this.ordemProducao.loadApontamentos(idiAtv);
	}
	
	public void incluirTarefasTerceiro() throws CbmModelException, SkalaModelException {
		String msg;
		BigDecimal nuApo;
		BigDecimal seqApa = BigDecimal.ONE;
		BigDecimal codProdPA;
		BigDecimal codTarifaTerc;
		BigDecimal qtdApontamento;
		JapeWrapper apontamentoPADAO = JapeFactory.dao(DynamicEntityNames.APONTAMENTO_PA);
		Collection<DynamicVO> apontamentosPAVO;
		JapeWrapper apontamentoMateriaisDAO = JapeFactory.dao(DynamicEntityNames.APONTAMENTO_MATERIAIS);
		FluidCreateVO apontamentoMateriaisNewVO;
		
		try {
			for (ApontamentoProducao apontamento : ordemProducao.getApontamentos()) {
				nuApo = apontamento.getNuApo();
				
				apontamentosPAVO = apontamentoPADAO.find("this.NUAPO = ?", nuApo);
				for (DynamicVO apontamentoPAVO : apontamentosPAVO) {
					codProdPA = apontamentoPAVO.asBigDecimal(SklFieldName.CODPRODPA);
					
					for (ProdutoAcabadoAProduzir pa:lancamentoDeOP.getProdutosAProduzir()) {
						if (codProdPA.equals(pa.getCodProdPA())) {
							codTarifaTerc = pa.getCodTarifaTerc();
							qtdApontamento = pa.getQtdProduzir();
							
							msg = String.format("[OP %s] Incluindo Tarifa de Terceirização %s para o Produto %s Apontamento %s...",
									idiProc, codTarifaTerc, codProdPA, nuApo);
							logger.info(msg);
							
							apontamentoMateriaisNewVO = apontamentoMateriaisDAO.create();
							apontamentoMateriaisNewVO.set("NUAPO", nuApo);
							apontamentoMateriaisNewVO.set("SEQAPA", seqApa);
							apontamentoMateriaisNewVO.set("CODPRODMP", codTarifaTerc);
							apontamentoMateriaisNewVO.set("CONTROLEMP", " ");
							apontamentoMateriaisNewVO.set("QTD", qtdApontamento);
							apontamentoMateriaisNewVO.set("CODVOL", "SV");
							apontamentoMateriaisNewVO.set("TIPOUSO", ApontamentoMateriaisTipoUsoEnum.CONSUMIDO.getValue());
							apontamentoMateriaisNewVO.save();
							
							break;
						}
					}
				}
				
			}
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível confirmar Apontamentos da Ordem de Produção.",
					idiProc);
			SkalaModelException.throwMe(msg, e);
		}
	}

	public void confirmarApontamentosProducao() throws CbmModelException {
		String msg;
		BigDecimal nuApo;
		JapeWrapper cabecalhoApontamentoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);
		DynamicVO apontamentoVO;
				
		try {
			ApontamentoHelper apt = new ApontamentoHelper(jdbc);
			
			loadApontamentos();
			for(ApontamentoProducao apontamento : ordemProducao.getApontamentos()) {
				if (ApontamentoProducaoSituacaoEnum.PENDENTE.getValue().equalsIgnoreCase(apontamento.getSituacao())) {
					nuApo = apontamento.getNuApo();
					
					msg = String.format("[OP %s] Confirmando apontamento %s...", idiProc, nuApo);
					logger.info(msg);
					
					apontamentoVO = cabecalhoApontamentoDAO.findByPK(nuApo);
					apt.validarConfirmacaoApontamento(apontamentoVO, true);
					apt.confirmarApontamento(nuApo, true, true);
				}
			}
		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível confirmar Apontamentos da Ordem de Produção.",
					idiProc);
			msg = CbmModelException.buildErrorMessage(e, msg);
			throw new CbmModelException(msg,e);
		}
	}
	
	private void loadApontamentos() throws CbmModelException {
		if (ordemProducao.getApontamentos().isEmpty())
			this.ordemProducao.loadApontamentos();
	}

	public void gerarNotasProducao() throws CbmModelException, NotaProducaoNaoGeradaException {
		BigDecimal idiAtv;
		BigDecimal nuApo = null;

		try {
			ApontamentoHelper apt = new ApontamentoHelper(jdbc);
			for (ApontamentoProducao apontamento : ordemProducao.getApontamentos()) {
				idiAtv = apontamento.getIdiAtv();
				nuApo = apontamento.getNuApo();
				
				OperacoesEstoqueHelper operacoesEstoqueHelper = new OperacoesEstoqueHelper(jdbc);
				operacoesEstoqueHelper.executarOperacoesEstoqueAtividade(idiAtv, nuApo, MomentoOperacaoEstoque.APONTAMENTO_PA);
				
				apt.finalizarProducaoProdutoAcabadosApontamento(idiProc, nuApo);
			}
			
		} catch (Exception e) {
			throw new ApontamentoProducaoExceptions.NotaProducaoNaoGeradaException(nuApo, e);
		}
		
	}

	public void finalizarOrdemProducao() throws CbmModelException {
		String msg;
		
		msg = String.format("[OP %s] Finalizando Ordem de Producao...", idiProc);
		logger.info(msg);

		try {
			JapeSession.putProperty("br.com.sankhya.mgeprod.apontamento.user", codusu);
			
			ArrayList<BigDecimal> listaIdAtividades = new ArrayList<>();
			
			for (InstanciaAtividade atividade:ordemProducao.getAtividades())
				listaIdAtividades.add(atividade.getIdiAtv());
			
			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance();
			helper.setCodUserExecutante(codusu);
			helper.finalizarInstanciaAtividades(listaIdAtividades, true, jdbc);

		} catch (Exception e) {
			msg = String.format("[OP %s] Não foi possível gerar as Notas de Produção.", idiProc);
			msg = CbmModelException.buildErrorMessage(e, msg);
			throw new CbmModelException(msg,e);
		}
	}
	
	/** deprecated 
	 * 	@since 2022-09-20
	 * 
	public boolean substituiMPAlternativa() throws Exception {
		JapeWrapper cabecalhoInstanciaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);
		JapeWrapper elementoFluxoDAO = JapeFactory.dao(DynamicEntityNames.ELEMENTO_FLUXO);
		JapeWrapper listaMateriaisAtividadeDAO = JapeFactory.dao(DynamicEntityNames.LISTA_MATERIAIS_ATIVIDADE);
		JapeWrapper materiaPrimaAlternativaWcDAO = JapeFactory.dao("AD_PLTPRWCMPAMP");
		
		BigDecimal codWcp = ordemProducao.getCodWcp();
		BigDecimal codProdPa = ordemProducao.getProdutoAcabado().getCodProdPA();
		
		String msg = String.format("OP %s - substituiMPAlternativa - codWcp %s codProdPa %s", idiProc, codWcp, codProdPa);
		logger.info(msg);
		
		Collection<DynamicVO> alternativasVO = materiaPrimaAlternativaWcDAO.find("this.CODWCP = ?", codWcp);
		if (alternativasVO.isEmpty()) {
			msg = String.format("OP %s - substituiMPAlternativa - nao encontrado", idiProc);
			logger.info(msg);
			
			return false;
		}
		
		Map<BigDecimal, BigDecimal> mpAlternativa = new HashMap<>();
		for (DynamicVO alternativaVO:alternativasVO) {
			mpAlternativa.put(alternativaVO.asBigDecimal("CODPRODMP"), alternativaVO.asBigDecimal("CODPRODMPALT"));
		}
		
		DynamicVO cabecalhoInstanciaVO = cabecalhoInstanciaDAO.findByPK(idiProc);
		BigDecimal idProc = cabecalhoInstanciaVO.asBigDecimal("IDPROC");
		
		StringBuilder sbWhere = new StringBuilder();
		String where = null;
		BigDecimal idEfx = null;
		sbWhere.append("this.IDEFX = ? AND this.CODPRODPA = ? ");
		sbWhere.append("AND EXISTS(SELECT 1 FROM AD_PLTPRWCMPAMP T WHERE T.CODWCP = ? AND T.CODPRODMP = this.CODPRODMP) ");
		where = sbWhere.toString();

		msg = String.format("OP %s - substituiMPAlternativa - idProc %s codProdutoAcabadoAProduzir %s", idiProc, idProc, codProdPa);
		logger.info(msg);

		Collection<DynamicVO> elementosFluxoVO = elementoFluxoDAO.find("this.IDPROC = ? AND this.TIPO = ?", idProc, new BigDecimal(BPMConstants.Task.USER));
		for (DynamicVO elementoFluxoVO:elementosFluxoVO) {
			idEfx = elementoFluxoVO.asBigDecimal("IDEFX");
			
			msg = String.format("OP %s - substituiMPAlternativa - idProc %s idEfx %s", idiProc, idProc, idEfx);
			logger.info(msg);

			Collection<DynamicVO> listaMateriaisVO = listaMateriaisAtividadeDAO.find(where, idEfx, codProdPa, codWcp);
			trocaMateriaPrima(idiProc, listaMateriaisAtividadeDAO, listaMateriaisVO, mpAlternativa);
		}
		
		return true;
	}
	*/
	
	private void trocaMateriaPrima(final BigDecimal idiProc, final JapeWrapper listaMateriaisAtividadeDAO, final Collection<DynamicVO> listaMateriaisVO, final Map<BigDecimal, BigDecimal> mpAlternativa) throws Exception {
		String msg;
		BigDecimal codProdMp = null;
		
		for (DynamicVO listaVO:listaMateriaisVO) {
			codProdMp = listaVO.asBigDecimal("CODPRODMP");
			if (mpAlternativa.containsKey(codProdMp)) {
				FluidUpdateVO listaUpdVO = listaMateriaisAtividadeDAO.prepareToUpdate(listaVO);
				listaUpdVO.set("CODPRODMP", mpAlternativa.get(codProdMp));
				listaUpdVO.update();
				
				msg = String.format("OP %s - trocaMateriaPrima - %s por %s", idiProc, codProdMp, mpAlternativa.get(codProdMp));
				logger.info(msg);
			}
		}
	}
	
	/** 
	 * 	deprecated
	 *  @since 2022-09-20
	public boolean restauraMPOriginal() throws Exception {
		JapeWrapper cabecalhoInstanciaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);
		JapeWrapper elementoFluxoDAO = JapeFactory.dao(DynamicEntityNames.ELEMENTO_FLUXO);
		JapeWrapper listaMateriaisAtividadeDAO = JapeFactory.dao(DynamicEntityNames.LISTA_MATERIAIS_ATIVIDADE);
		JapeWrapper materiaPrimaAlternativaWcDAO = JapeFactory.dao("AD_PLTPRWCMPAMP");
		
		BigDecimal codWcp = ordemProducao.getCodWcp();
		BigDecimal codProdPa = ordemProducao.getProdutoAcabado().getCodProdPA();
		
		String msg;
		
		Collection<DynamicVO> alternativasVO = materiaPrimaAlternativaWcDAO.find("this.CODWCP = ?", codWcp);
		if (alternativasVO.isEmpty()) {
			msg = String.format("OP %s - substituiMPAlternativa - nao encontrado", idiProc);
			logger.info(msg);
			
			return false;
		}
		
		Map<BigDecimal, BigDecimal> mpAlternativa = new HashMap<>();
		for (DynamicVO alternativaVO:alternativasVO) {
			mpAlternativa.put(alternativaVO.asBigDecimal("CODPRODMPALT"), alternativaVO.asBigDecimal("CODPRODMP"));
		}
		
		DynamicVO cabecalhoInstanciaVO = cabecalhoInstanciaDAO.findByPK(idiProc);
		BigDecimal idProc = cabecalhoInstanciaVO.asBigDecimal("IDPROC");
		
		StringBuilder sbWhere = new StringBuilder();
		String where = null;
		BigDecimal idEfx = null;
		sbWhere.append("this.IDEFX = ? AND this.CODPRODPA = ? ");
		sbWhere.append("AND EXISTS(SELECT 1 FROM AD_PLTPRWCMPAMP T WHERE T.CODWCP = ? AND T.CODPRODMPALT = this.CODPRODMP) ");
		where = sbWhere.toString();

		msg = String.format("OP %s - restauraMPOriginal - idProc %s codProdutoAcabadoAProduzir %S", idiProc, idProc, codProdPa);
		logger.info(msg);

		Collection<DynamicVO> elementosFluxoVO = elementoFluxoDAO.find("this.IDPROC = ? AND this.TIPO = ?", idProc, new BigDecimal(BPMConstants.Task.USER));
		for (DynamicVO elementoFluxoVO:elementosFluxoVO) {
			idEfx = elementoFluxoVO.asBigDecimal("IDEFX");
			
			msg = String.format("OP %s - restauraMPOriginal - idProc %s idEfx %s", idiProc, idProc, idEfx);
			logger.info(msg);

			Collection<DynamicVO> listaMateriaisVO = listaMateriaisAtividadeDAO.find(where, idEfx, codProdPa, codWcp);
			trocaMateriaPrima(idiProc, listaMateriaisAtividadeDAO, listaMateriaisVO, mpAlternativa);
		}
		
		return true;
	}
	*/
	
	public void atualizaStatusProc(String status) throws CbmModelException {
		
		ProgramacaoProducaoSimplificadaProcessor.atualizarStatusProc(idiProc, status);
		
	}
	
	public void reabrirOrdemProducao() throws SkalaModelException  {
		CabecalhoInstanciaProcessoStatus status = new CabecalhoInstanciaProcessoStatus(true);
		boolean hasAtividade = setSituacaoAtividade(status);
		
		if (hasAtividade)
			status.setStatusProc(CabecalhoInstanciaProcessoStatusEnum.EM_ANDAMENTO.getValue());
		
		restaurarSaldoAProduzir();
		setStatusProc(status);
	}
	
	private void restaurarSaldoAProduzir() throws SkalaModelException {
		
		try {
			NativeSql sql = new NativeSql(jdbc);
			sql.setReuseStatements(true);
			
			sql.setNamedParameter("IDIPROC", idiProc);
			sql.executeUpdate("UPDATE TPRIPA SET CONCLUIDO = 'N' WHERE IDIPROC = :IDIPROC AND CONCLUIDO = 'S'");
			
			StringBuilder updateApontamento = new StringBuilder();
			updateApontamento.append("UPDATE TPRAPA ");
			updateApontamento.append("SET QTDPERDA = 0 ");
			updateApontamento.append("WHERE ");
			updateApontamento.append("NUAPO IN ");
			updateApontamento.append("(SELECT NUAPO FROM TPRAPO WHERE IDIATV IN ");
			updateApontamento.append("(SELECT IDIATV FROM TPRIATV WHERE IDIPROC = :IDIPROC)) ");
			updateApontamento.append("AND QTDPERDA > 0");
			
			sql.executeUpdate(updateApontamento.toString());
			
		} catch (Exception e) {
			String msg = String.format("[IDIPROC %s] Não foi possível restaurar Saldo a Produzir. ", idiProc);
			SkalaModelException.throwMe(msg, e);
		}

	}

	private boolean setSituacaoAtividade(CabecalhoInstanciaProcessoStatus status) throws SkalaModelException {
		boolean hasAtividade = false;
		
		try {
			JapeWrapper instanciaAtividadeDAO = JapeFactory.dao(DynamicEntityNames.INSTANCIA_ATIVIDADE);
			Collection<DynamicVO> atividadesVO = instanciaAtividadeDAO.find("this.IDIPROC = ?", idiProc);
			hasAtividade = !atividadesVO.isEmpty();
			
			BigDecimal tempoGastoMin = (status.getDhTermino() == null) ? BigDecimal.ZERO : BigDecimalUtil.getBigDecimal(TimeUtils.getDifferenceInMinutes(TimeUtils.getNow(), status.getDhTermino()));
			
			FluidUpdateVO atividadeUpdVO;
			for (DynamicVO atividadeVO : atividadesVO) {
				atividadeUpdVO = instanciaAtividadeDAO.prepareToUpdate(atividadeVO);
				atividadeUpdVO.set("CODUSUFIN", status.getCodUsuFinal());
				atividadeUpdVO.set("DHFINAL", status.getDhTermino());
				atividadeUpdVO.set("TEMPOGASTOMIN", tempoGastoMin);
				atividadeUpdVO.update();
			}
			
		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível atualizar a situação da Atividade.", idiProc);
			SkalaModelException.throwMe(msg, e);
		}
		
		return hasAtividade;
		
	}

	public void finalizarOrdemProducaoStatus() throws SkalaModelException {
		CabecalhoInstanciaProcessoStatus status = new CabecalhoInstanciaProcessoStatus(false);
		setSituacaoAtividade(status);
		
		alterarStatusProdutoAcabado();
		setStatusProc(status);
	}
	
	public void alterarStatusProdutoAcabado() throws SkalaModelException {

		try {
			JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);
			Collection<DynamicVO> produtoAcabadoVO = produtoAcabadoAProduzirDAO.find("this.IDIPROC = ? AND this.CONCLUIDO = 'N'", idiProc);

			if (produtoAcabadoVO.isEmpty()) {
				return;
			}

			FluidUpdateVO updVO;
			for (DynamicVO vo : produtoAcabadoVO) {
				updVO = produtoAcabadoAProduzirDAO.prepareToUpdate(vo);
				updVO.set("CONCLUIDO", "S");
				updVO.update();
			}
		} catch (Exception e) {
			String msg = String.format("[IDPROC %s] Não foi possível concluir o Produto Acabado a Produzir.", idiProc);
			SkalaModelException.throwMe(msg, e);
		}

	}
	
	protected void setStatusProc(CabecalhoInstanciaProcessoStatus status) throws SkalaModelException {
		
		try {
			JapeWrapper cabInstanciaProcessoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);
			DynamicVO cabInstanciaProcessoVO = cabInstanciaProcessoDAO.findByPK(idiProc);
			
			FluidUpdateVO cabInstanciaProcessoUpdVO = cabInstanciaProcessoDAO.prepareToUpdate(cabInstanciaProcessoVO);
			cabInstanciaProcessoUpdVO.set("STATUSPROC", status.getStatusProc());
			cabInstanciaProcessoUpdVO.set("DHTERMINO", status.getDhTermino());
			cabInstanciaProcessoUpdVO.set("CODUSUFINAL", status.getCodUsuFinal());
			cabInstanciaProcessoUpdVO.update();
		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível atualizar o <i>status</i> da Ordem de Produção.", idiProc);
			SkalaModelException.throwMe(msg, e);
		}
		
	}

}
