package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

public class BuscaCargaJob implements ScheduledAction {

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));

		JobAction action = new JobAction(this.getClass().getCanonicalName());

		try {
			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());

			contexto.log("Executando...");
			
			buscaCargas();
			
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

	private void buscaCargas() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(getClass(), "BuscaCargaJob_queCargas.sql");
			rs = sql.executeQuery();

			BigDecimal codEmp = BigDecimal.ZERO;
			BigDecimal ordemCarga = BigDecimal.ZERO;

			AZShipProcessor processor = null;

			while (rs.next()) {
				codEmp = BigDecimalUtil.getBigDecimal(rs.getBigDecimal(SklFieldName.CODEMP));
				ordemCarga = BigDecimalUtil.getBigDecimal(rs.getBigDecimal(SklFieldName.ORDEMCARGA));

				processor = new AZShipProcessor();
				processor.getShipping(codEmp, ordemCarga);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar cargas.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

}