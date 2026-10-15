package tech.skala.action.integration.pontoicarus;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.action.core.filamensagem.FilaMensagemUtils;
import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

public class SyncJob implements ScheduledAction {

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));
		
		JobAction action = new JobAction(this.getClass().getCanonicalName());
		try {
			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());
			
			Sync.sendRequest();
			
		} catch (Exception e) {
			String email = "suporte@platinacsc.com.br";
			String assunto = "Importação do colaborador com erro.";
			String mensagem = String.format("Verificar cadastro do colaborador no Sankhya. Origem do Erro: %s.",e.getMessage());

			FilaMensagemUtils.enviaAvisoEmail(email, assunto, mensagem, AuthenticationInfo.getCurrent().getUserID());

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
