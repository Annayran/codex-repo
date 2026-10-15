package tech.skala.action.modules.controladoria;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class ProcessaPlanilhaVTMAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] rows = contexto.getLinhas();
			
			action.validateRows(rows);
			action.validateUniqueRowSelected(rows);
			
			ProcessaPlanilhaVTMAnexo processor = new ProcessaPlanilhaVTMAnexo(rows);
			processor.processarPlanilha();

			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "A Planilha foi processada.";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}