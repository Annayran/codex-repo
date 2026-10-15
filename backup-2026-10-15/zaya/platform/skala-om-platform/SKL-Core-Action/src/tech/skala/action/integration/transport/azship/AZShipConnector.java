package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.core.http.HttpConnection;
import tech.skala.action.core.http.HttpMethod;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AZShipConnector {

	private AZShipConnector() {
		throw new IllegalStateException("Utility class");
	}

	private static final Logger LOGGER = Logger
			.getLogger("tech.skala.action.integration.transport.azship.AZShipConnector");

	public static ApiResponse postShipping(String payload) throws SkalaModelException {
		LOGGER.info("postShipping");

		HttpConnection conn = buildConnectionProduction(AZShipConstants.POST_SHIPPING, payload, HttpMethod.POST);

		return conn.doConnect();
	}

	public static ApiResponse getShipping(BigDecimal ordemCarga) throws SkalaModelException {
		LOGGER.info("getShipping");

		String path = AZShipConstants.GET_SHIPPINGS_ID.replace("{code}", ordemCarga.toString());
		HttpConnection conn = buildConnectionProduction(path, null, HttpMethod.GET);

		return conn.doConnect();
	}

	public static ApiResponse getOccurrenceOrder(BigDecimal ordemCarga) throws SkalaModelException {
		LOGGER.info("getOccurrenceOrder");

		String path = AZShipConstants.GET_OCCURRENCE_CODE.replace("{shippingCode}", ordemCarga.toString());
		HttpConnection conn = buildConnectionProduction(path, null, HttpMethod.GET);

		return conn.doConnect();
	}

	public static ApiResponse getOccurrenceCodeOrder(BigDecimal ordemCarga, BigDecimal nuNota)
			throws SkalaModelException {
		LOGGER.info("getOccurrenceCodeOrder");

		String path = AZShipConstants.GET_OCCURRENCE_CODE_ORDER.replace("{shippingCode}", ordemCarga.toString())
				.replace("{orderCode}", nuNota.toString());
		HttpConnection conn = buildConnectionProduction(path, null, HttpMethod.GET);

		return conn.doConnect();
	}

	public static ApiResponse postRemessaPedido(String remessasJSON) throws SkalaModelException {
		LOGGER.info("postRemessaPedido");

		HttpConnection conn = buildConnectionProduction(AZShipConstants.POST_ORDER, remessasJSON, HttpMethod.POST);

		return conn.doConnect();
	}

	public static ApiResponse deleteOrder(BigDecimal numNota) throws SkalaModelException {
		LOGGER.info("deleteOrder");

		String path = AZShipConstants.DELETE_ORDER.replace("{code}", numNota.toString());
		HttpConnection conn = buildConnectionProduction(path, null, HttpMethod.DELETE);

		return conn.doConnect();
	}

	public static ApiResponse updateNF(String payload) throws SkalaModelException {
		LOGGER.info("updateNF");

		HttpConnection conn = buildConnectionProduction(AZShipConstants.UPDATE_ORDER_NF, payload, HttpMethod.POST);

		return conn.doConnect();
	}

	private static HttpConnection buildConnectionProduction(String path, String payload, HttpMethod method) {
		Map<String, String> requestProperties = new HashMap<>();
		requestProperties.put("x-azship-token", AZShipConstants.TOKEN);
		requestProperties.put("Content-Type", AZShipConstants.CONTENT_TYPE_JSON);

		return new HttpConnection.Builder(AZShipConstants.ENDPOINT, method).setPath(path).setPayload(payload)
				.setRequestProperties(requestProperties).build();
	}
}
