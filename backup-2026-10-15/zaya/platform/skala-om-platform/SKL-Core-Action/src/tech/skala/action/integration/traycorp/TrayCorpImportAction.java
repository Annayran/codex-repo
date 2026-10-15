package tech.skala.action.integration.traycorp;


import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.extensions.Action;

public class TrayCorpImportAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			TrayCorpImport helper = new TrayCorpImport();
			TrayCorpImportLog importLog = helper.importaPedidos();

			if (importLog != null) {
				msg = String.format("%s%n", importLog);
				action.appendMessage(msg);			
		}

			action.appendMessageWithLineSeparator("Pedidos importados.");

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}