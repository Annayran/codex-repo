package tech.skala.modules.manufatura.atividade;

import java.math.BigDecimal;
import java.util.List;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.mgeprod.model.helper.ApontamentoHelper;
import br.com.sankhya.mgeprod.model.helper.OperacaoProducaoHelper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducao;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducaoFinder;
import tech.skala.modules.manufatura.apontamento.ApontamentoProducaoProcessor;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducao;

public class InstanciaAtividadeProcessor {

	public void iniciaAtividade(OrdemProducao ordemProducao) throws SkalaModelException {
		for (InstanciaAtividade atividade : ordemProducao.getAtividades()) {
			if (InstanciaAtividadeSituacaoEnum.AGUARDANDO_ACEITE.getValue().equals(atividade.getSituacao()))
				iniciarInstanciaAtividades(ordemProducao.getIdiProc(), atividade);
		}
	}

	private void iniciarInstanciaAtividades(BigDecimal idiProc, InstanciaAtividade atividade)
			throws SkalaModelException {
		JdbcWrapper jdbc = null;
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance(EntityFacadeFactory.getDWFFacade());
			helper.iniciarInstanciaAtividade(atividade.getIdiAtv(), jdbc);

		} catch (Exception e) {
			String msg = String.format("[OP %s] Nï¿½o foi possï¿½vel iniciar Atividade %s da Ordem de Produï¿½ï¿½o.", idiProc,
					atividade.getIdiAtv());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
	}

	public void paraInstanciaAtividade(OrdemProducao ordemProducao, BigDecimal codMtp, String observacao) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ordemProducao.setAtividades(InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));

					ExecucaoInstanciaAtividadeProcessor execucaoProcessor = new ExecucaoInstanciaAtividadeProcessor();
					for (InstanciaAtividade atividade : ordemProducao.getAtividades())
						execucaoProcessor.pararInstanciaAtividade(atividade, codMtp, observacao);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível iniciar parada.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void continuaInstanciaAtividade(OrdemProducao ordemProducao) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ordemProducao.setAtividades(InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));
					
					ExecucaoInstanciaAtividadeProcessor execucaoProcessor = new ExecucaoInstanciaAtividadeProcessor();
					for (InstanciaAtividade atividade : ordemProducao.getAtividades())
						execucaoProcessor.continuarInstanciaAtividade(atividade);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Nï¿½o foi possï¿½vel continuar instancia atividade.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	public void finalizaInstanciaAtividade(OrdemProducao ordemProducao) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ordemProducao.setAtividades(InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));
				
					for (InstanciaAtividade atividade : ordemProducao.getAtividades()) {
						verificaApontamentoPendente(atividade);
						
						finalizarInstanciaAtividade(atividade.getIdiAtv());
					}
						
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Nï¿½o foi possivel finalizar a atividade", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	/**
	 * Método não utilizado, pois neste momento 
	 * estaremos apenas finalizando a atividade com o método nativo do sistema
	 * 
	 * @param ordemProducao
	 * @param apontamento
	 * @throws SkalaModelException
	 */
	private void finalizarProducaoProdutoAcabadoApontamento(OrdemProducao ordemProducao,
			ApontamentoProducao apontamento) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			ApontamentoProducaoProcessor apontamentoProcessor = new ApontamentoProducaoProcessor();
			apontamentoProcessor.processarPerdaUltimoApontamento(apontamento.getIdiAtv(), ordemProducao.getIdiProc(),
					apontamento.getNuApo());

			ApontamentoHelper apt = new ApontamentoHelper(jdbc);
			apt.finalizarProducaoProdutoAcabadosApontamento(ordemProducao.getIdiProc(), apontamento.getNuApo());
		} catch (Exception e) {
			SkalaModelException.throwMe("Nï¿½o foi possivel processar perda para ultimo apontamento.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
		
	}

	private void verificaApontamentoPendente(InstanciaAtividade atividade) throws SkalaModelException {
		List<ApontamentoProducao> apontamentosPendentes = ApontamentoProducaoFinder
				.buscaApontamentosPendentesPorAtividade(atividade);

		if (!apontamentosPendentes.isEmpty()) {
			String msg = String.format(
					"[IDIATV %s] Existem apontamentos pendentes para a atividade.",
					atividade.getIdiAtv());
			throw new SkalaModelException(msg);
		}
	}

	public void finalizarInstanciaAtividade(BigDecimal idiAtv) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			BigDecimal codUserExecutante = AuthenticationInfo.getCurrent().getUserID();
			JapeSession.putProperty("br.com.sankhya.mgeprod.apontamento.user", codUserExecutante);

			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance();
			helper.setCodUserExecutante(codUserExecutante);
			helper.finalizarInstanciaAtividade(idiAtv, true, jdbc);
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Nï¿½o foi possivel finalizar a atividade", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

	}
	
}
