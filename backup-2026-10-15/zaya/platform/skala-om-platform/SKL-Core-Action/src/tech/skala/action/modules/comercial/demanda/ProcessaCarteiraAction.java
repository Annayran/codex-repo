package tech.skala.action.modules.comercial.demanda;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.action.beans.demanda.DemandaTipoOrigemEnum;
import tech.skala.core.commons.extensions.Action;

public class ProcessaCarteiraAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			ProcessaCarteira helper = new ProcessaCarteira();
			helper.processarCarteira(action, DemandaTipoOrigemEnum.MANUAL.getValue());
			

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
