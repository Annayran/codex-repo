package tech.skala.action.integration.prodwin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.core.http.HttpConnection;
import tech.skala.action.core.http.HttpMethod;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProdwinConnector {

	private static final Logger LOGGER = Logger
			.getLogger("tech.skala.action.integration.prodwin.ProdWinConnector");
	
	private ProdwinConnector() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ApiResponse productionOrder(String payload) throws SkalaModelException {
		LOGGER.info("productionOrder");

		HttpConnection conn = buildConnectionProduction(ProdwinUtils.PRODUCTION_ORDER, payload, HttpMethod.POST);

		return conn.doConnect();
	}
	
	private static HttpConnection buildConnectionProduction(String path, String payload, HttpMethod method) {
		Map<String, String> requestProperties = new HashMap<>();
		requestProperties.put("Accept", ProdwinUtils.ACCEPT);
		requestProperties.put("Content-Type", ProdwinUtils.CONTENT_TYPE_JSON);

		return new HttpConnection.Builder(ProdwinUtils.ENDPOINT, method).setPath(path).setPayload(payload)
				.setRequestProperties(requestProperties).build();
	}
}
