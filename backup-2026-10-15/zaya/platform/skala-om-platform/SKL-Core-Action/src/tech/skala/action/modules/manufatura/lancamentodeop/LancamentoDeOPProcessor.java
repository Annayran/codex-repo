package tech.skala.action.modules.manufatura.lancamentodeop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.mgeprod.model.geradorIMPS.LancamentoOPBuilder;
import br.com.sankhya.mgeprod.model.geradorIMPS.ProdutoGeradorLancamento;
import br.com.sankhya.mgeprod.model.helper.ControleProcessoHelper;
import br.com.sankhya.mgeprod.model.helper.LancadorOrdemDeProducao;
import br.com.sankhya.mgeprod.model.lancamento.LancamentoOP;
import br.com.sankhya.mgeprod.model.lancamento.TipoProcesso;
import br.com.sankhya.mgeprod.model.utils.ProdutoControle;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.DeleteProductionOrderLaunchNotPossibleException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.ProductionOrderCreationNotPossibleException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.ProductionOrderNotIncludedException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.ProductionOrderNotIncludedForProductException;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOPExceptions.WorkCenterNotSetException;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public final class LancamentoDeOPProcessor {
	private final Logger								logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private LancamentoDeOP								lancamentoDeOP;

	public LancamentoDeOPProcessor(LancamentoDeOP lancamentoDeOP) {
		this.lancamentoDeOP = lancamentoDeOP;
	}

	@Deprecated
	/* Método descontinuado após implementação de métodos utilizados na versão HTML5 da tela
	 * Ordens Produção - Nova.
	 * @Since 2022-09-22
	 */
	public void incluiOrdensDeProducaoNoLancamento() throws ProductionOrderNotIncludedException, DeleteProductionOrderLaunchNotPossibleException {
		SessionHandle					hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					incluirOrdensDeProducaoNoLancamento();
				}
			});
			
		} catch (Exception e) {
			deletaLancamentoOP();

			throw new LancamentoDeOPExceptions.ProductionOrderNotIncludedException(e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	@Deprecated
	/* Método descontinuado após implementação de métodos utilizados na versão HTML5 da tela
	 * Ordens Produção - Nova.
	 * @Since 2022-09-22
	 */
	protected void incluirOrdensDeProducaoNoLancamento() throws ProductionOrderNotIncludedForProductException {
		
		for (ProdutoAcabadoAProduzir pa : lancamentoDeOP.getProdutosAProduzir())
			incluirOrdemDeProducaoNoLancamento(pa);
		
	}

	@Deprecated
	/* Método descontinuado após implementação de métodos utilizados na versão HTML5 da tela
	 * Ordens Produção - Nova.
	 * @Since 2022-09-22
	 */
	public void incluirOrdemDeProducaoNoLancamento(ProdutoAcabadoAProduzir produtoAProduzir) throws ProductionOrderNotIncludedForProductException {
		JdbcWrapper jdbc = null;

		BigDecimal nuLop = lancamentoDeOP.getNuLop();
		BigDecimal seqOP;
		BigDecimal codPlP = lancamentoDeOP.getCodPlP();
		BigDecimal codProdPA = produtoAProduzir.getCodProdPA();
		String controlePA = produtoAProduzir.getControlePA();
		BigDecimal qtdAProduzir = produtoAProduzir.getQtdProduzir();

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			LancamentoOPBuilder lancOPBuilder = LancamentoOPBuilder.build(jdbc, nuLop);
			seqOP = lancOPBuilder.inserirOPProducao(codProdPA, controlePA, codPlP, null);
			lancOPBuilder.persistir();

			if(seqOP != null)
				lancOPBuilder.alterarTamanhoLoteProduto(seqOP.intValue(), codProdPA, controlePA, qtdAProduzir, true);

			produtoAProduzir.setNuLop(nuLop);
			produtoAProduzir.setSeqOP(seqOP);
			
			String msg = String.format("%s", produtoAProduzir);
			logger.info(msg);

		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.ProductionOrderNotIncludedForProductException(e, codProdPA);			
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

	}

	public void deletaLancamentoOP() throws DeleteProductionOrderLaunchNotPossibleException {
		SessionHandle					hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					deletarLancamentoOP();
				}
			});
			
		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.DeleteProductionOrderLaunchNotPossibleException(e, lancamentoDeOP.getNuLop());
		} finally {
			JapeSession.close(hnd);
		}
	}

	protected void deletarLancamentoOP() throws DeleteProductionOrderLaunchNotPossibleException {
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			LancamentoOP lancamentoOPBean = LancamentoOP.load(lancamentoDeOP.getNuLop(), jdbc);
			lancamentoOPBean.excluir();

		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.DeleteProductionOrderLaunchNotPossibleException(e, lancamentoDeOP.getNuLop());			
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

	}

	public void alocaCentroDeTrabalho() throws WorkCenterNotSetException {
		SessionHandle					hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					alocarCentroDeTrabalho();
				}
			});
			
		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.WorkCenterNotSetException(e, lancamentoDeOP.getCodWcp(), lancamentoDeOP.getNuLop());
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	public void alocarCentroDeTrabalho() throws UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ALOCACAO_WORK_CENTER_LCTO_OP);
		Collection<DynamicVO> vos;
		
		try {
			vos = dao.find("this.NULOP = ? ", lancamentoDeOP.getNuLop());
			
			EntityUpdate entityUpd;
			for (DynamicVO vo:vos) {
				entityUpd = new EntityUpdate(DynamicEntityNames.ALOCACAO_WORK_CENTER_LCTO_OP, vo);
				entityUpd.setField(SklFieldName.CODWCP, lancamentoDeOP.getCodWcp());
				entityUpd.fluidUpdate();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
		
	}
	
	public void lancaOrdensProducao() throws ProductionOrderCreationNotPossibleException {
		lancaOrdensProducao(null);
	}
	
	public void lancaOrdensProducao(final BigDecimal nuNota) throws ProductionOrderCreationNotPossibleException {
		logger.info("1.3.4 - lancaOrdensProducao");
		
		SessionHandle					hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					lancarOrdensProducao();
					
					if(nuNota != null) {
						vincularNotaOrdensProducao(nuNota);
						incluirDataHoraLog(nuNota);
					}
					
				}
			});
			
		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.ProductionOrderCreationNotPossibleException(e, lancamentoDeOP.getNuLop());
		} finally {
			JapeSession.close(hnd);
		}
	}

	protected void incluirDataHoraLog(BigDecimal nuNota) throws SkalaModelException {
		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] {nuNota});
		EntityUpdate update = new EntityUpdate("AD_PLTPRLOGCABIND", pk);
		update.setField("DHINCOP", TimeUtils.getNow());
		update.fluidUpdate();
		
	}

	private void lancarOrdensProducao() throws ProductionOrderCreationNotPossibleException {
		logger.info("1.3.4.1 - lancarOrdensProducao");
		
		JdbcWrapper jdbc = null;

		String msg;
		LancamentoOP lancamentoOPBean;
		boolean ignorarWarnings = true;
		
		msg = String.format("[NULOP %s] Gerando Ordens de Producao...", lancamentoDeOP.getNuLop());
		logger.info(msg);
		
		JapeSession.putProperty("mge.prod.lacamentoOP.by.MPS", Boolean.TRUE);
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			lancamentoOPBean = LancamentoOP.load(lancamentoDeOP.getNuLop(), jdbc);
			
			ControleProcessoHelper controleProcessoHelper = new ControleProcessoHelper(jdbc);
			LancadorOrdemDeProducao lancadorOP = new LancadorOrdemDeProducao(controleProcessoHelper,
					EntityFacadeFactory.getDWFFacade(), jdbc);		
			
			lancadorOP.validarLancamentoDeProducao(lancamentoDeOP.getNuLop());
			ArrayList<String> erros = (ArrayList<String>) lancadorOP.getErros();
			ArrayList<String> alertas = (ArrayList<String>) lancadorOP.getAlertas();
			
			if (erros.isEmpty() && (ignorarWarnings || alertas.isEmpty())) {
				lancadorOP.lancarOrdensDeProducao(lancamentoOPBean);
				if (!lancamentoOPBean.getReutilizar())
					lancamentoOPBean.excluir();

				this.lancamentoDeOP.setOrdensDeProducaoLancadas(lancadorOP.getOrdensLancadas());
			} else {
				lancamentoDeOP.setErros(erros);
			}
			
		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.ProductionOrderCreationNotPossibleException(e, lancamentoDeOP.getNuLop());			
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

	}

	public void insereProdutoOrdemProducao() throws SkalaModelException {
		logger.info("1.3.1 - insereProdutoOrdemProducao");
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					for (ProdutoAcabadoAProduzir pa : lancamentoDeOP.getProdutosAProduzir())
						inserirProdutoOrdemProducao(pa);
				}

			});

		} catch (Exception e) {
			deletaLancamentoOP();

			throw new LancamentoDeOPExceptions.ProductionOrderNotIncludedException(e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	
	private void inserirProdutoOrdemProducao(ProdutoAcabadoAProduzir pa)
			throws SkalaModelException {
		JdbcWrapper jdbc = null;

		BigDecimal seqOP = null;
		BigDecimal codProd = pa.getCodProdPA();
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			LancamentoOPBuilder lancOPBuilder = LancamentoOPBuilder.build(jdbc, lancamentoDeOP.getNuLop());

			BigDecimal codplp = lancamentoDeOP.getCodPlP();
			BigDecimal codParc = (lancamentoDeOP.isTerceiro()) ? null : lancamentoDeOP.getCodParc();
			BigDecimal idProc = (lancamentoDeOP.isTerceiro()) ? pa.getIdProc() : lancamentoDeOP.getIdProc();
			BigDecimal qtdCapacidadePad = (lancamentoDeOP.isTerceiro()) ? pa.getQtdProduzir() : lancamentoDeOP.getQtdCapacidadePad();

			String controlePA = pa.getControlePA();

			JapeSession.putProperty("mge.prod.lacamentoHtml.idProc", idProc);
			
			ProdutoGeradorLancamento produtoLancamento = lancOPBuilder.buildProdutoLancamento(idProc, codplp, codProd,
					codParc, pa.getNroLote(), controlePA, seqOP, false);

			seqOP = lancOPBuilder.inserirOPProducao(codProd, controlePA, codplp, null,
					qtdCapacidadePad, null, codParc, produtoLancamento);

			lancOPBuilder.inserirPAemOP(TipoProcesso.PRODUCAO, seqOP, new ProdutoControle(codProd, controlePA), codplp,
					produtoLancamento);

			lancOPBuilder.persistir();
			
			pa.setNuLop(lancamentoDeOP.getNuLop());
			pa.setSeqOP(seqOP);
		} catch (Exception e) {
			throw new LancamentoDeOPExceptions.ProductionOrderNotIncludedForProductException(e, codProd);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	public void alteraNumeroLoteProdutos(ProdutoAcabadoAProduzir produtoAcabado) throws SkalaModelException {
		logger.info("1.3.2 - alteraNumeroLoteProdutos");
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					alterarNumeroLoteProdutos(produtoAcabado);
				}

			});

		} catch (Exception e) {
			deletaLancamentoOP();
			SkalaModelException.throwMe("Não foi possível alterar o Nro Lote do Produto.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}
	
	private void alterarNumeroLoteProdutos(ProdutoAcabadoAProduzir produtoAcabado) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			LancamentoOPBuilder lancOPBuilder = LancamentoOPBuilder.build(jdbc, produtoAcabado.getNuLop());
			lancOPBuilder.alterarNumeroLoteProduto(produtoAcabado.getSeqOP(), produtoAcabado.getCodProdPA(),
					produtoAcabado.getControlePA(), produtoAcabado.getNroLote());

		} catch (Exception e) {
			String msg = String.format("Não foi possível alterar o numero do lote para Lancamento %s Sequencia %s.%n",
					produtoAcabado.getNuLop(), produtoAcabado.getSeqOP());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	public void atualizaTerceiro() throws SkalaModelException {
		logger.info("1.3.3 - atualizaTerceiro");
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					atualizarTerceiroLancamentoOP();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível alterar o Terceiro do Lancamento.", e);
		} finally {
			JapeSession.close(hnd);
		}
			
	}
	
	private void atualizarTerceiroLancamentoOP() throws SkalaModelException {
		JdbcWrapper jdbc = null;

		if (!lancamentoDeOP.isTerceiro())
			return;

		String msg;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			JapeWrapper terceiroLancamentoOPDAO = JapeFactory.dao(DynamicEntityNames.TERCEIRO_LANCAMENTO_OP);
			Collection<DynamicVO> terceirosLancamentoVO = terceiroLancamentoOPDAO.find("this.NULOP = ?", lancamentoDeOP.getNuLop());

			FluidUpdateVO updVO;
			for (DynamicVO terceiroLancamentoVO : terceirosLancamentoVO) {
				updVO = terceiroLancamentoOPDAO.prepareToUpdate(terceiroLancamentoVO);
				updVO.set("CODPARCTERC", lancamentoDeOP.getCodParc());
				updVO.update();
			}
		} catch (Exception e) {
			msg = String.format("Não foi possível alterar o Terceiro do Lancamento %s.%n", lancamentoDeOP.getNuLop());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

	}
	
	public void getTarifasTerceiro() throws SkalaModelException {
		logger.info("1.3.5 - getTarifasTerceiro");
		
		JdbcWrapper jdbc = null;
		
		if (!lancamentoDeOP.isTerceiro())
			return;

		JapeWrapper cabecalhoInstanciaProcessoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO);
		JapeWrapper terceiroProcessoProdutivoDAO = JapeFactory.dao(DynamicEntityNames.TERCEIRO_PROCESSO_PRODUTIVO);

		DynamicVO cabecalhoInstanciaProcessoVO;
		DynamicVO terceiroProcessoProdutivoVO;
		
		BigDecimal ideFx = BigDecimal.ZERO;
		BigDecimal idProc;
		BigDecimal codProdPA;
		BigDecimal codParcTerc = lancamentoDeOP.getCodParc();
		BigDecimal codTarifaTerc;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			for (BigDecimal idiProc : lancamentoDeOP.getOrdensDeProducaoLancadas()) {
				cabecalhoInstanciaProcessoVO = cabecalhoInstanciaProcessoDAO.findByPK(idiProc);
				idProc = cabecalhoInstanciaProcessoVO.asBigDecimal("IDPROC");

				for (ProdutoAcabadoAProduzir pa : lancamentoDeOP.getProdutosAProduzir()) {
					if (pa.getCodTarifaTerc() == null) {
						codProdPA = pa.getCodProdPA();

						terceiroProcessoProdutivoVO = terceiroProcessoProdutivoDAO.findByPK(idProc, ideFx, codProdPA,
								codParcTerc);
						codTarifaTerc = terceiroProcessoProdutivoVO.asBigDecimal("AD_CODPROD");

						pa.setCodTarifaTerc(codTarifaTerc);
					}
				}
			}

		} catch (Exception e) {
			String msg = String.format("Não foi possível localizar Tarifas de Terceiro do Lancamento %s.%n",
					lancamentoDeOP.getNuLop());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
		
	}

	public void vincularNotaOrdensProducao(BigDecimal nuNota) throws SkalaModelException {
		logger.info("1.3.4.2 - vincularNotaOrdensProducao");
		
		for (BigDecimal idiProc:lancamentoDeOP.getOrdensDeProducaoLancadas()) 
			vincularNotaOrdemProducao(nuNota, idiProc);
	
	}

	private void vincularNotaOrdemProducao(BigDecimal nuNota, BigDecimal idiProc) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		JapeWrapper nxopDAO = JapeFactory.dao("AD_PLTPRNXOP");

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			String msg = String.format("[NUNOTA %s] Vinculando Nota x OP %s...", nuNota, idiProc);
			logger.info(msg);

			FluidCreateVO newVO;
			newVO = nxopDAO.create();
			newVO.set(SklFieldName.NUNOTA, nuNota);
			newVO.set(SklFieldName.IDIPROC, idiProc);
			newVO.save();
		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível vincular as Ordens de Produção geradas.%n", nuNota);
			SkalaModelException.throwMe(msg,e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
		
	}

}
