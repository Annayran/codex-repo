package tech.skala.action.integration.prodwin;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class SendProductionOrderAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		try {
			action.start();
			
			Registro[] rows = context.getLinhas();

			action.validateRows(rows);
			
			SendProductionOrderHelper helper;
			for (Registro row : rows) {
				helper = new SendProductionOrderHelper(action, row);
				helper.execute();				
			}
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();
			context.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
