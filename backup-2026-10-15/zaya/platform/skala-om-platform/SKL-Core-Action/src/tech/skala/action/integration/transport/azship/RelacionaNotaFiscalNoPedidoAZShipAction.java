package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.core.commons.extensions.Action;

public class RelacionaNotaFiscalNoPedidoAZShipAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			BigDecimal numNotaNF = BigDecimalUtil.getBigDecimal(contexto.getParam("NUMNOTANF"));
			BigDecimal numNotaOR = BigDecimalUtil.getBigDecimal(contexto.getParam("NUMNOTAOR"));

			UpdateOrderNFDTO dto = new UpdateOrderNFDTO();
			dto.setNumNotaNFe(numNotaNF);
			dto.setNumNotaOR(numNotaOR);

			Gson gson = new GsonBuilder().create();
			String payload = gson.toJson(dto, dto.getClass());

			ApiResponse apiResponse = AZShipConnector.updateNF(payload);
			JsonObject json = JsonUtils.convertStringToJsonObject(apiResponse.getResponse());
			BigDecimal code = JsonUtils.getBigDecimal(json, "code");

			if (BigDecimal.TEN.longValue() == code.longValue()) {
				String message = JsonUtils.getString(json, "message");
				msg = String.format(
						"[NUMNOTA %s] Nota Fiscal não vinculada ao pedido dentro da Plataforma AZShip.<br><b>%s.",
						numNotaOR, message);
				action.appendMessageWithLineSeparator(msg);
			} else {
				msg = String.format(
						"[NUMNOTAOR %s][NUMNOTANF %s] O Pedido foi atualizado com a informação da Nota fiscal dentro da Plataforma AZShip.",
						numNotaOR, numNotaNF);
				action.appendMessageWithLineSeparator(msg);
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}