package tech.skala.action.integration.traycorp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.core.http.HttpConnection;
import tech.skala.action.core.http.HttpMethod;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TrayCorpConnector {

	private TrayCorpConnector() {
		throw new IllegalStateException("Utility class");
	}

	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.integration.traycorp.TrayCorpConnector");

	public static ApiResponse getPedidos(String startDate, String endDate) throws SkalaModelException {
		LOGGER.info("getPedidos");

		String path = String.format("%s?dataInicial=%s&dataFinal=%s&enumTipoFiltroData=DataPedido&situacoesPedido=1",
				TrayCorpUtils.GET_PEDIDOS, startDate, endDate);

		HttpConnection conn = buildConnectionProduction(path, null, HttpMethod.GET);
		return conn.doConnect();
	}

	public static ApiResponse atualizaPedidoParaEmPreparacao(BigDecimal pedidoId) throws SkalaModelException {
		LOGGER.info("atualizaStatusPedido");

		String path = TrayCorpUtils.PUT_STATUS.replace("{pedidoId}", pedidoId.toString());
		String payload = "{\"id\": " + TrayCorpUtils.SITUACAO_EM_PREPARACAO.toString() + "}";

		HttpConnection conn = buildConnectionProduction(path, payload, HttpMethod.PUT);
		return conn.doConnect();
	}

	private static HttpConnection buildConnectionProduction(String path, String payload, HttpMethod method) {
		Map<String, String> requestProperties = new HashMap<>();
		requestProperties.put("Content-Type", "application/json");
		requestProperties.put("Accept", "application/json");
		requestProperties.put("Authorization", TrayCorpUtils.TOKEN);

		return new HttpConnection.Builder(TrayCorpUtils.ENDPOINT, method).setPath(path).setPayload(payload)
				.setRequestProperties(requestProperties).build();
	}

}
