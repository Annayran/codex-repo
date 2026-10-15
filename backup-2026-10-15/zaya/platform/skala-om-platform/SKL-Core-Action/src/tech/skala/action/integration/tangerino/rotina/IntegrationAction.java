package tech.skala.action.integration.tangerino.rotina;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import com.sankhya.util.StringUtils;
import tech.skala.action.integration.tangerino.controller.TangerinoController;
import tech.skala.core.commons.extensions.Action;


public class IntegrationAction implements AcaoRotinaJava {
	Action action = new Action(this.getClass().getCanonicalName());

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		try {
			action.start();
			
			TangerinoController tangerinoController = new TangerinoController();
			String retorno = tangerinoController.gerenciar();
			
			if (StringUtils.isNotEmpty(retorno))
				action.appendMessageWithLineSeparator(retorno);
			else
				action.appendMessageWithLineSeparator("Algo aconteceu que não estavamos esperando!");
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
		
	}
	
}
