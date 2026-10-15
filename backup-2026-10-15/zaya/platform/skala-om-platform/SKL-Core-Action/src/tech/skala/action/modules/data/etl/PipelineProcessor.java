package tech.skala.action.modules.data.etl;

import java.util.logging.Logger;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.util.time.Timer;

public class PipelineProcessor {
	private static final Logger LOGGER = Logger.getLogger(PipelineProcessor.class.getCanonicalName());

	private PipelineProcessor() {
		throw new IllegalStateException("Utility class");
	}

	public static PipelineReturn atualizaLancamentosCentral() {
		String returnMessage;

		boolean success = false;
		try {
			atualizarLancamentosCentral();

			returnMessage = "Lancamentos Central atualizados.";
			success = true;
		} catch (Exception e) {
			returnMessage = SkalaModelException.buildErrorMessage("Não foi possível atualizar Lancamentos Central.", e);
		}

		return new PipelineReturn(returnMessage, success);
	}

	private static void atualizarLancamentosCentral() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					Timer timer = new Timer();
					timer.start();

					String msg = String.format("atualizarLancamentosCentral inicio %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// exclusao dos registros que existem na consolidacao, mas que foram apagados do
					// sistema
					executeSql("PipelineProcessor_delItensExcluidos.sql");
					msg = String.format("delItensExcluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					executeSql("PipelineProcessor_delCabecalhosExcluidos.sql");
					msg = String.format("delCabecalhosExcluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// limpar registros da tabela temporaria de exclusao
					executeSql("PipelineProcessor_delCabecalhosTemporariosExcluidos.sql");
					msg = String.format("delCabecalhosTemporariosExcluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// incluir registros da tabela temporaria de exclusao
					executeSql("PipelineProcessor_insCabecalhosTemporariosExcluidos.sql");
					msg = String.format("insCabecalhosTemporariosExcluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// inclusao na consolidacao dos novos registros
					executeSql("PipelineProcessor_delItensPeriodo.sql");
					msg = String.format("delItensPeriodo %s", timer.getElapsedTime());
					LOGGER.info(msg);

					executeSql("PipelineProcessor_delCabecalhosPeriodo.sql");
					msg = String.format("delCabecalhosPeriodo %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// limpar registros da tabela temporaria de exclusao
					executeSql("PipelineProcessor_delCabecalhosTemporariosExcluidos.sql");
					msg = String.format("delCabecalhosTemporariosExcluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// limpar registros da tabela temporaria de inclusao
					executeSql("PipelineProcessor_delCabecalhosTemporariosIncluidos.sql");
					msg = String.format("delCabecalhosTemporariosIncluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// incluir registros da tabela temporaria de inclusao
					executeSql("PipelineProcessor_insCabecalhosTemporariosIncluidos.sql");
					msg = String.format("insCabecalhosTemporariosIncluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// inclusao na consolidacao dos novos registros
					executeSql("PipelineProcessor_insCabecalhos.sql");
					msg = String.format("insCabecalhos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					executeSql("PipelineProcessor_insItens.sql");
					msg = String.format("insItens %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// limpar registros da tabela temporaria de inclusao
					executeSql("PipelineProcessor_delCabecalhosTemporariosIncluidos.sql");
					msg = String.format("delCabecalhosTemporariosIncluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("atualizarLancamentosCentral()", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public static String atualizaLancamentosPendentesFinanceiro() {
		String returnMessage;

		try {
			atualizarLancamentosPendentesFinanceiro();

			returnMessage = "Lancamentos Pendentes Financeiros atualizados.";
		} catch (Exception e) {
			returnMessage = SkalaModelException
					.buildErrorMessage("Não foi possível atualizar Lancamentos Pendentes do Financeiros.", e);
		}

		return returnMessage;
	}

	private static void atualizarLancamentosPendentesFinanceiro() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					executeSql("PipelineProcessor_delLancamentosPendentes.sql");
					executeSql("PipelineProcessor_insLancamentosPendentes.sql");
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("atualizarLancamentosPendentesFinanceiro()", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public static String atualizaLancamentosBaixadosFinanceiro() {
		String returnMessage;

		try {
			atualizarLancamentosBaixadosFinanceiro();

			returnMessage = "Lancamentos Baixados Financeiros atualizados.";
		} catch (Exception e) {
			returnMessage = SkalaModelException
					.buildErrorMessage("Não foi possível atualizar Lancamentos Baixados do Financeiros.", e);
		}

		return returnMessage;
	}

	private static void atualizarLancamentosBaixadosFinanceiro() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					executeSql("PipelineProcessor_delLancamentosBaixados.sql");
					executeSql("PipelineProcessor_insLancamentosBaixados.sql");
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("atualizarLancamentosBaixadosFinanceiro()", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private static void executeSql(String resourcePath) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(PipelineProcessor.class, resourcePath);
			sql.executeUpdate();
		} catch (Exception e) {
			SkalaModelException.throwMe("executeSql()", e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	public static PipelineReturn atualizaEstoque() {
		String returnMessage;

		boolean success = false;
		try {
			atualizarEstoque();

			returnMessage = "Estoques atualizados.";
			success = true;
		} catch (Exception e) {
			returnMessage = SkalaModelException.buildErrorMessage("Não foi possível atualizar o Estoque.", e);
		}

		return new PipelineReturn(returnMessage, success);
	}

	private static void atualizarEstoque() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					executeSql("PipelineProcessor_delEstoquePeriodo.sql");
					executeSql("PipelineProcessor_delEstoqueExcluidos.sql");
					executeSql("PipelineProcessor_delEstoqueData.sql");
					executeSql("PipelineProcessor_insItensEstoque.sql");
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("atualizarEstoque()", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public static PipelineReturn atualizaCarteira() {
		String returnMessage;

		boolean success = false;
		try {
			atualizarCarteira();

			returnMessage = "Carteira atualizada.";
			success = true;
		} catch (Exception e) {
			returnMessage = SkalaModelException.buildErrorMessage("Não foi possível atualizar Carteira.", e);
		}

		return new PipelineReturn(returnMessage, success);
	}

	private static void atualizarCarteira() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					Timer timer = new Timer();
					timer.start();

					String msg = String.format("atualizarCarteira inicio %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// exclusao dos registros da consolidacao
					executeSql("PipelineProcessor_delItensCarteira.sql");
					msg = String.format("delItensCarteira %s", timer.getElapsedTime());
					LOGGER.info(msg);

					executeSql("PipelineProcessor_delCabecalhosCarteira.sql");
					msg = String.format("delCabecalhosCarteira %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// limpar registros da tabela temporaria
					executeSql("PipelineProcessor_delCabecalhosCarteiraTemporariosIncluidos.sql");
					msg = String.format("delCabecalhosCarteiraTemporariosIncluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);

					// incluir registros na tabela temporaria
					executeSql("PipelineProcessor_insCabecalhosCarteiraTemporariosIncluidos.sql");
					msg = String.format("insCabecalhosCarteiraTemporariosIncluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);
					
					// inclusao na consolidacao dos novos registros
					executeSql("PipelineProcessor_insCabecalhosCarteira.sql");
					msg = String.format("insCabecalhosCarteira %s", timer.getElapsedTime());
					LOGGER.info(msg);

					executeSql("PipelineProcessor_insItensCarteira.sql");
					msg = String.format("insItensCarteira %s", timer.getElapsedTime());
					LOGGER.info(msg);
					
					// limpar registros da tabela temporaria
					executeSql("PipelineProcessor_delCabecalhosCarteiraTemporariosIncluidos.sql");
					msg = String.format("delCabecalhosCarteiraTemporariosIncluidos %s", timer.getElapsedTime());
					LOGGER.info(msg);
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("atualizarCarteira()", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public static PipelineReturn atualizaEntradaPendente() {
		String returnMessage;

		boolean success = false;
		try {
			atualizarEntradaPendente();

			returnMessage = "Entradas pendentes atualizadas.";
			success = true;
		} catch (Exception e) {
			returnMessage = SkalaModelException.buildErrorMessage("Não foi possível atualizar Entradas Pendentes.", e);
		}

		return new PipelineReturn(returnMessage, success);
	}

	private static void atualizarEntradaPendente() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					executeSql("PipelineProcessor_delEntradasPendentes.sql");
					executeSql("PipelineProcessor_insEntradasPendentes.sql");
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("atualizarEntradaPendente()", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

}
