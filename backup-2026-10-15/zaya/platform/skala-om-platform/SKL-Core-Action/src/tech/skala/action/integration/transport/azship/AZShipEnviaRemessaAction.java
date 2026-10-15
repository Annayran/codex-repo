package tech.skala.action.integration.transport.azship;

import java.util.ArrayList;
import java.util.List;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class AZShipEnviaRemessaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			AZShipRemessa remessa;
			AZShipRemessaProcessor processor;
			
			List<AZShipRemessa> remessas = new ArrayList<>(); 
			for(Registro linha : linhas) {
				remessa = new AZShipRemessa();
				remessa.loadByRecord(linha);
				
				remessas.add(remessa);
			}
			processor = new AZShipRemessaProcessor(remessas);
			String msg = processor.enviarRemessa();
			
			action.appendMessageWithLineSeparator(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}