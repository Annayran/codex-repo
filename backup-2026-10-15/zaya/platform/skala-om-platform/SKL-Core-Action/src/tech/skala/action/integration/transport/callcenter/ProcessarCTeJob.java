package tech.skala.action.integration.transport.callcenter;

import java.sql.ResultSet;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.centrais.CabecalhoNotaDAO;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

public class ProcessarCTeJob implements ScheduledAction {

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));

		JobAction action = new JobAction(this.getClass().getCanonicalName());

		try {
			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());

			contexto.log("Executando...");

			buscaCTeParaProcessar();

		} catch (Exception e) {
			action.error(e);

		} finally {
			try {
				action.stop();
			} catch (TemporizadorNaoIniciadoException e) {
				e.printStackTrace();
			}

			contexto.info(action.getMensagemRetorno());

		}

	}

	private void buscaCTeParaProcessar() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(getClass(), "ProcessarCTeJob.sql");
			rs = sql.executeQuery();

			String chaveCTe;

			while (rs.next()) {
				chaveCTe = (rs.getString("CHAVECTE"));

				processaCTe(chaveCTe);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar CT-e.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private void processaCTe(String chaveCTe) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					for (DynamicVO vo : CabecalhoNotaDAO.getCabecalhoFromChaveCTe(chaveCTe))
						CallCenterProcessor.processarCTe(vo);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu um erro ao Processar os CT-e", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}
