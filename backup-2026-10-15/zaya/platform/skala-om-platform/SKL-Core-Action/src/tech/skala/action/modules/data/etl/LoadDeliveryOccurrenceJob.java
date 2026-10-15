package tech.skala.action.modules.data.etl;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

public class LoadDeliveryOccurrenceJob implements ScheduledAction {

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));
		
		JobAction action = new JobAction(this.getClass().getCanonicalName());
		
		try {
			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());

			contexto.log("Executando...");
			
			LoadDeliveryOccurrenceProcessor processor = new LoadDeliveryOccurrenceProcessor();
			processor.carregarOcorrencias();
			
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
