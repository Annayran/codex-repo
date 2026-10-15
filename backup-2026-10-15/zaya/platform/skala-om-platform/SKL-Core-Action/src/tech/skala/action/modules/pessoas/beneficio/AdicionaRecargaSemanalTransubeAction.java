package tech.skala.action.modules.pessoas.beneficio;

import java.sql.Timestamp;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.extensions.Action;

public class AdicionaRecargaSemanalTransubeAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		Timestamp dtRef = (Timestamp) contexto.getParam("DTREF");
		Timestamp dtPon = (Timestamp) contexto.getParam("DTPON");
		try {
			action.start();
			
			CalculaPreBeneficio helper = new CalculaPreBeneficio(action, dtRef, dtPon);
			helper.adicionaRecargaSemanal();
			
		} catch (Exception e) {
			action.error(e);

		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}