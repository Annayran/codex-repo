package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

/**
 * 
 * @author cassiomenezes
 * @since 2022-06-20
 *
 */
public class AlteraDataComercialAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		String msg;
		Registro[] linhas = contexto.getLinhas();
		action.validateRows(linhas);

		Timestamp dtComercial = (Timestamp) contexto.getParam("DTCOMERCIAL");

		try {
			action.start();
			
			for (Registro linha : linhas) {
				BigDecimal nuNota = (BigDecimal) linha.getCampo(SklFieldName.NUNOTA);

				ComercialHelper helper = new ComercialHelper();
				helper.alteraDataComercial(nuNota, dtComercial);
			}
			msg = "Data Comercial ajustada.";
			action.appendMessage(msg);
			
		} catch (Exception e) {
			action.error(e);

		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
	
}
