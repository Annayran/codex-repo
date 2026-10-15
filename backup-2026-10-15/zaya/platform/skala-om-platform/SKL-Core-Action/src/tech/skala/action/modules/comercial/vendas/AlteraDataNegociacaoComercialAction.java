package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AlteraDataNegociacaoComercialAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		Timestamp data = (Timestamp) contexto.getParam("DATA");

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			BigDecimal nuNota;

			CentralProcessor processor = new CentralProcessor();
			for (Registro linha : linhas) {
				nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));
				processor.alterarDataNegociacaoComercial(nuNota, data);
			}

			msg = "Data de negociação e comercial alteradas com sucesso!";
			action.appendMessageWithLineSeparator(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}