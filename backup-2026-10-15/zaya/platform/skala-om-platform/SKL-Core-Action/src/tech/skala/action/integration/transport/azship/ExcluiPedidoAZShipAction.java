package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;

import com.google.gson.JsonObject;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class ExcluiPedidoAZShipAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal numNota;
			for (Registro linha : linhas) {
				numNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUMNOTA));

				ApiResponse apiResponse = AZShipConnector.deleteOrder(numNota);

				JsonObject json = JsonUtils.convertStringToJsonObject(apiResponse.getResponse());
				BigDecimal code = JsonUtils.getBigDecimal(json, "code");

				if (BigDecimal.TEN.longValue() == code.longValue()) {
					String message = JsonUtils.getString(json, "message");
					msg = String.format("[NUMNOTA %s] Solicitação não processada.<br><b>%s.", numNota, message);
					action.appendMessageWithLineSeparator(msg);
				} else {
					msg = String.format("[NUMNOTA %s] O Pedido foi excluido da Plataforma AZShip.", numNota);
					action.appendMessageWithLineSeparator(msg);
				}

			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}