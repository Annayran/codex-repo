package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class ReprocessaRateioProdutoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			BigDecimal nuNota;
			for (Registro linha:linhas) {
				nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo("NUNOTA"));
				ReprocessaRateioProduto.geraRateio(nuNota);
			}
			
			msg = "Rateio(s) realizado(s) com sucesso!";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}