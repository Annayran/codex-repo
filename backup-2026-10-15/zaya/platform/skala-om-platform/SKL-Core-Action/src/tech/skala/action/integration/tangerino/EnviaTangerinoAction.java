package tech.skala.action.integration.tangerino;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.action.integration.tangerino.model.integracao.Colaborador;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeDTO;
import tech.skala.core.commons.extensions.Action;

public class EnviaTangerinoAction implements AcaoRotinaJava {
	Action action = new Action(this.getClass().getCanonicalName());

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {

		try {
			action.start();
			
			TangerinoProcessor processor = new TangerinoProcessor();
			processor.enviar();

			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
		
	}

}
