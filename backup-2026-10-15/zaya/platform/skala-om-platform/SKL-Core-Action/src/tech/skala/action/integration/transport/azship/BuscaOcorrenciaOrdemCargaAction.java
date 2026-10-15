package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class BuscaOcorrenciaOrdemCargaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		try {
			action.start();
			
			Registro[] rows = context.getLinhas();

			action.validateRows(rows);

			BigDecimal codEmp = BigDecimal.ZERO;
			BigDecimal shippingCode = BigDecimal.ZERO;
			BigDecimal orderCode = BigDecimal.ZERO;		
			
			AZShipHelper helper = null;
			
			for (final Registro row : rows) {
				codEmp = BigDecimalUtil.getBigDecimal(row.getCampo("CODEMP"));
				shippingCode = BigDecimalUtil.getBigDecimal(row.getCampo("ORDEMCARGA"));

				helper = new AZShipHelper(codEmp, shippingCode, orderCode);
				helper.getOccurrenceByShippingCode();
			}
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();
			context.setMensagemRetorno(action.getMensagemRetorno());
		}
		
	}

}
