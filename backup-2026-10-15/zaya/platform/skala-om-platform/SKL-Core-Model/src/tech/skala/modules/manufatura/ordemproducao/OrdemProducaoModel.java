package tech.skala.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.mgeprod.model.helper.ControleProcessoHelper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducao;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducaoFinder;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducaoProcessor;
import tech.skala.modules.manufatura.apontamento.OrdemProducaoStatusEnum;
import tech.skala.modules.manufatura.atividade.InstanciaAtividadeFinder;
import tech.skala.modules.manufatura.atividade.InstanciaAtividadeProcessor;

/**
 * Classe responsável por prover recursos recursos que irão suportar manipulacao
 * de ordens de producao.
 * 
 * @author hiagoalexandre
 * @author odilon
 * @since 2022-08-17
 */
public class OrdemProducaoModel {

	private final Logger				logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	
	private OrdemProducao ordemProducao;

	public OrdemProducaoModel(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	/**
	 * Método responsável por iniciar Ordem de produção
	 * 
	 * O método deverá tratar duas situações:
	 * 
	 * <li>Quando o Status da Ordem de Produção for <b>CRIADO</b></li>
	 * 	<ol>
	 * 		<li>Chamar o método nativo do sistema para Iniciar a Ordem de Produção.</li>
	 * 		<li>Aceitar a Atividade.</li>
	 * 		<li>Iniciar a Atividade.</li>
	 * 	</ol>
	 * 
	 * <li>Uma vez que o Status da Ordem de Produção já esteja como
	 * <b>EM_ANDAMENTO</b>.</li>
	 * 	<ol>
	 * 		<li>Criar um novo apontamento.</li>
	 * 		<li>Alterar a quantidade apontada.</li>
	 * 		<li>Confirmar o apontamento.</li>
	 * 	</ol>
	 * 
	 * @param nuvOP
	 * 
	 * @throws SkalaModelException Caso ocorra um erro durante a inicialização de um
	 *                             apontamento.
	 */
	public void iniciaApontamento(BigDecimal nuvOP) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					if (OrdemProducaoStatusEnum.CRIADO.getValue().equals(ordemProducao.getStatusProc())) {
						inicializarOrdemProducao();
						iniciaAtividade();
					}

					criaApontamentosProducao(nuvOP);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível criar Apontamento.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void iniciaAtividadeWithTX() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					iniciaAtividade();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível iniciar a atividade", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	/**
	 * Método responsável por realizar a inicialização de uma Atividade.
	 * 
	 * <p>
	 * Este método chama o método nativo da biblioteca do sistema para iniciar uma
	 * determinada Atividade de uma Ordem de Produção.
	 * </p>
	 * 
	 * @throws SkalaModelException Caso ocorra um problema durante a inicialização
	 *                             da Atividade.
	 */
	private void iniciaAtividade() throws SkalaModelException {
		if (ordemProducao.getAtividades().isEmpty())
			ordemProducao
					.setAtividades(InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));

		InstanciaAtividadeProcessor atividadeProcessor = new InstanciaAtividadeProcessor();
		atividadeProcessor.iniciaAtividade(ordemProducao);
	}

	private void criaApontamentosProducao(BigDecimal nuvOP) throws SkalaModelException {
		if (ordemProducao.getAtividades().isEmpty())
			ordemProducao
					.setAtividades(InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));

		ApontamentoProducaoProcessor apontamentoProcessor = new ApontamentoProducaoProcessor();
		apontamentoProcessor.criaApontamentoProducao(ordemProducao, nuvOP);
	}

	private void inicializaOrdemProducaoWithTX() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					inicializarOrdemProducao();
				}

			});

		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível inicializar a Ordem de Produção.",
					ordemProducao.getIdiProc());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	/**
	 * Método responsável por realizar a inicialização de uma Ordem de Produção.
	 * 
	 * <p>
	 * Este método chama o método nativo da biblioteca do sistema para iniciar uma
	 * determinada Ordem de Produção.
	 * </p>
	 * 
	 * @throws SkalaModelException Caso ocorra um problema durante a inicialização
	 *                             da Ordem de Produção.
	 */
	private void inicializarOrdemProducao() throws SkalaModelException {
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			ControleProcessoHelper cph = new ControleProcessoHelper(jdbc);
			cph.iniciaOrdemDeProducao(ordemProducao.getIdiProc());
		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível inicializar a Ordem de Produção.",
					ordemProducao.getIdiProc());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
	}

	/**
	 * Método responsável por concluir um apontamento.
	 * 
	 * @param nuvOP é o identificador único de um Volume de Produção.
	 * @param ultimoApontamento é um booleano que identifica se é o ultimo apontamento ou não
	 * 
	 * @throws SkalaModelException caso ocorra um erro durante a confirmação do
	 *                             apontamento.
	 */
	public void confirmaApontamento(BigDecimal nuvOP, boolean isUltimoApontamento) throws SkalaModelException {
		SessionHandle hnd = null;
		
		
		ApontamentoProducaoProcessor apontamentoProcessor = new ApontamentoProducaoProcessor();
		ApontamentoProducao apontamento = ApontamentoProducaoFinder.buscaApontamentoPorCaixa(ordemProducao, nuvOP);
		try {
			JapeSession.putProperty("br.com.sankhya.mgeprod.is.ultimo.apontamento", isUltimoApontamento);
			JapeSession.putProperty("br.com.sankhya.mgeprod.gera.nota.producao", true);
			
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					if (ordemProducao.getAtividades().isEmpty())
						ordemProducao.setAtividades(
								InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));

					apontamentoProcessor.confirmaApontamento(ordemProducao, nuvOP, apontamento);
					logger.info(String.format("Apontamento %s é o ultimo apontamento? %s", apontamento.getIdiAtv(), isUltimoApontamento));
					
					apontamentoProcessor.executaOperacoesEstoqueAtividade(apontamento, ordemProducao, isUltimoApontamento);
				}

			});

		} catch (Exception e) {
			String msg = String.format("[OP %s] Não foi possível concluir Apontamento.", ordemProducao.getIdiProc());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

}
