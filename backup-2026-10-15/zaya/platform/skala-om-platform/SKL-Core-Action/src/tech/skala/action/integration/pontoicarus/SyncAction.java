package tech.skala.action.integration.pontoicarus;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.extensions.Action;

/**
 * Sincronizacao com API Ponto Icarus.
 * 
 * @author cassiomenezes
 * @since 2022-04-06
 * 
 */
public class SyncAction implements AcaoRotinaJava {
	Action action = new Action(this.getClass().getCanonicalName());

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {

		try {
			action.start();
			
			Sync.sendRequest();
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
