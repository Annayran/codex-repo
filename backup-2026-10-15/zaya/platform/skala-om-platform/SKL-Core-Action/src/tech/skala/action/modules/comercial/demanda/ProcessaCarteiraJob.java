package tech.skala.action.modules.comercial.demanda;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.action.beans.demanda.DemandaTipoOrigemEnum;
import tech.skala.action.core.filamensagem.FilaMensagemUtils;
import tech.skala.core.commons.extensions.JobAction;
import tech.skala.core.main.Skl;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;



public class ProcessaCarteiraJob implements ScheduledAction{

	@Override
	public void onTime(ScheduledActionContext contexto) {
		contexto.log(Skl.getVersion(this.getClass().getCanonicalName()));

		JobAction action = new JobAction(this.getClass().getCanonicalName());

		try {

			action.start();
			action.setupContext(contexto.getJobMetadata().getUid());
			
		
			String msg = String.format("Usuário logado: %s-%s%n", AuthenticationInfo.getCurrent().getUserID(),
					AuthenticationInfo.getCurrent().getName());
			action.appendMessage(msg);

			ProcessaCarteira helper = new ProcessaCarteira();
			helper.processarCarteira(action, DemandaTipoOrigemEnum.AGENDADOR.getValue());

		} catch (Exception e) {
			String email = "suporte@platinacsc.com.br";
			String assunto = "Demanda não calculada.";
			String mensagem = String.format("A Demanda do dia %s não foi calculada.", TimeUtils.formataDDMMYYYY(TimeUtils.getNow()));
			
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
