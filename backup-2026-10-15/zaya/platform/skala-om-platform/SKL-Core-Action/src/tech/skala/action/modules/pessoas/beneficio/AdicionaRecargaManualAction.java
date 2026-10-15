package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class AdicionaRecargaManualAction implements AcaoRotinaJava{
	
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		BigDecimal indCre = BigDecimalUtil.getBigDecimal(contexto.getParam("INDICEBEN"));

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			AdicionaRecargaManual helper = new AdicionaRecargaManual( linhas, indCre);
			helper.adicionaRecargaManual();
		} catch (Exception e) {
			action.error(e);

		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
}
