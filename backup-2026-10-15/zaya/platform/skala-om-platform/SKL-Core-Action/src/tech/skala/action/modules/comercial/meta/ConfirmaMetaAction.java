package tech.skala.action.modules.comercial.meta;

import java.sql.Timestamp;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class ConfirmaMetaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			Timestamp dtRef = (Timestamp) linhas[0].getCampo("DTREF");

			MetasOrcamentoProcessor processor = new MetasOrcamentoProcessor();
			processor.confirmaMetas(dtRef);

			action.appendMessageWithLineSeparator("Metas Confirmadas!");

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}