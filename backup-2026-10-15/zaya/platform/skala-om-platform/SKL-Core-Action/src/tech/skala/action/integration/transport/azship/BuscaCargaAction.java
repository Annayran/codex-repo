package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class BuscaCargaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] rows = context.getLinhas();

			action.validateRows(rows);

			BigDecimal codEmp = BigDecimal.ZERO;
			BigDecimal ordemCarga = BigDecimal.ZERO;
			
			AZShipProcessor processor = null;
			
			for (final Registro row : rows) {
				codEmp = BigDecimalUtil.getBigDecimal(row.getCampo("CODEMP"));
				ordemCarga = BigDecimalUtil.getBigDecimal(row.getCampo("ORDEMCARGA"));

				processor = new AZShipProcessor();
				processor.getShipping(codEmp, ordemCarga);
				
			}
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();
			context.setMensagemRetorno(action.getMensagemRetorno());
		}		
	}

}
