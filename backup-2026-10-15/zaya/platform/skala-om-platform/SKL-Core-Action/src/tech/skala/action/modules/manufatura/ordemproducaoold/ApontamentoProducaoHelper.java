package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.modules.manufatura.lancamentodeop.LancamentoDeOP;
import tech.skala.action.modules.manufatura.ordemproducaoold.ApontamentoProducaoExceptions.NotaProducaoNaoGeradaException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ApontamentoProducaoHelper {
	
	private OrdemProducaoProcessor			processor;
	private final Logger					logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	public ApontamentoProducaoHelper(JdbcWrapper jdbc, BigDecimal idiProc, LancamentoDeOP lancamentoDeOP) {
		this.processor = new OrdemProducaoProcessor(jdbc, idiProc, lancamentoDeOP);
	}
	
	public ApontamentoProducaoHelper(JdbcWrapper jdbc, OrdemProducao ordemProducao) {
		this.processor = new OrdemProducaoProcessor(jdbc, ordemProducao);
	}

	public void processaOrdemProducao3() throws SkalaModelException {
		logger.info("2 - processaOrdemProducao3");
		
		criarApontamentoProducao();
		confirmarApontamentosProducao();
		
		gerarNotasProducao();
		finalizarOrdemProducao();
	}

	private void criarApontamentoProducao() throws SkalaModelException {
		logger.info("2.1 - criarApontamentoProducao");
		
		SessionHandle					hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					processor.criarApontamentoProducao();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("criarApontamentoProducao()", e);
		} finally {
			hnd = null;
		}
	}

	private void incluirTarefasTerceiro() throws SkalaModelException {
		SessionHandle					hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					processor.incluirTarefasTerceiro();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("incluirTarefasTerceiro()", e);
		} finally {
			hnd = null;
		}		
	}

	private void confirmarApontamentosProducao() throws SkalaModelException {
		logger.info("2.2 - confirmarApontamentosProducao");
		
		SessionHandle					hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					processor.confirmarApontamentosProducao();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("confirmarApontamentosProducao()", e);
		} finally {
			hnd = null;
		}		
	}

	private void gerarNotasProducao() throws SkalaModelException {
		logger.info("2.3 - gerarNotasProducao");
		
		SessionHandle					hnd = null;
		BigDecimal						nuApo = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					processor.gerarNotasProducao();
				}
			});
			
		} catch (NotaProducaoNaoGeradaException e) {
			nuApo = e.getNuApo();
			SkalaModelException.throwMe("gerarNotasProducao()", e);
		} catch (Exception e) {
			SkalaModelException.throwMe("gerarNotasProducao()", e);
		} finally {
			if (nuApo != null)
				excluirApontamento(nuApo);

			hnd = null;
		}
	}

	private void excluirApontamento(final BigDecimal nuApo) {
		SessionHandle					hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					JapeWrapper cabecalhoApontamentoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);
					cabecalhoApontamentoDAO.delete(nuApo);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

	}

	private void finalizarOrdemProducao() throws SkalaModelException {
		logger.info("2.4 - finalizarOrdemProducao");
		SessionHandle					hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					processor.finalizarOrdemProducao();
					processor.atualizaStatusProc(OrdemProducaoStatusEnum.FINALIZADO.getValue());
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("finalizarOrdemProducao()", e);
		} finally {
			hnd = null;
		}
	}

}
