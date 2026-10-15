package tech.skala.action.integration.traycorp;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

public class TrayCorpImportJob implements ScheduledAction {

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));

		JobAction action = new JobAction(this.getClass().getCanonicalName());
		try {
			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());

			TrayCorpImport helper = new TrayCorpImport();
			TrayCorpImportLog importLog = helper.importaPedidos();

			if (importLog != null) {
				String msg = String.format("%s%n", importLog);
				action.appendMessageWithLineSeparator(msg);
			}

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

}
