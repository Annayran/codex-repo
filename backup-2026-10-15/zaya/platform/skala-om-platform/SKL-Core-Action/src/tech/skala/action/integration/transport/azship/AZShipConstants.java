package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AZShipConstants {

	private AZShipConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String AZS_LOG = "AD_PLTAZSLOG";
	public static final String AZS_OCP = "AD_PLVAZSOCP";

	public static final String TOKEN_HOM = "da0a82e2-73b2-4aec-b95e-b47cbe56e080";
	public static final String TOKEN = "d0f55be7-d816-48d1-be66-50b908ff6196";
	public static final String CONTENT_TYPE_JSON = "application/json";

	public static final String ENDPOINT_HOM = "https://ws.staging.azship.com.br/api/platina";
	public static final String ENDPOINT = "https://ws.azship.com.br/api/platina";

	public static final String GET_SHIPPINGS_ID = "/shipping/{code}";
	public static final String POST_SHIPPING = "/shipping";
	public static final String GET_OCCURRENCE_CODE = "/occurrence/search?shippingCode={shippingCode}";
	public static final String GET_OCCURRENCE_CODE_ORDER = "/occurrence/search?shippingCode={shippingCode}&orderCode={orderCode}";

	public static final String POST_ORDER = "/order";
	public static final String DELETE_ORDER = "/order/{code}";
	public static final String UPDATE_ORDER_NF = "/order/updateNf";

	public static final Map<String, BigDecimal> getOcorrenciaAZShip() {
		
		Map<String, BigDecimal> ocorrenciaAZShip = new HashMap<>();
		ocorrenciaAZShip.put("Frete lançado", BigDecimal.ONE);
		ocorrenciaAZShip.put("Transportadora Vinculada", BigDecimal.valueOf(2));
		ocorrenciaAZShip.put("Aguardando Motorista", BigDecimal.valueOf(3));
		ocorrenciaAZShip.put("Motorista/Veículo vinculado", BigDecimal.valueOf(4));
		ocorrenciaAZShip.put("Check-in Realizado", BigDecimal.valueOf(5));
		ocorrenciaAZShip.put("Comentário durante check-in", BigDecimal.valueOf(6));
		ocorrenciaAZShip.put("Solicitação de entrada", BigDecimal.valueOf(7));
		ocorrenciaAZShip.put("Comentário ao solicitar entrada", BigDecimal.valueOf(8));
		ocorrenciaAZShip.put("Marcação de entrada", BigDecimal.valueOf(9));
		ocorrenciaAZShip.put("Comentário na marcação de entrada", BigDecimal.valueOf(10));
		ocorrenciaAZShip.put("Coleta iniciada", BigDecimal.valueOf(11));
		ocorrenciaAZShip.put("Comentário ao iniciar coleta", BigDecimal.valueOf(12));
		ocorrenciaAZShip.put("Coleta finalizada", BigDecimal.valueOf(13));
		ocorrenciaAZShip.put("Comentário ao finalizar coleta", BigDecimal.valueOf(14));
		ocorrenciaAZShip.put("Carga em trânsito", BigDecimal.valueOf(15));
		ocorrenciaAZShip.put("Próximo ao cliente", BigDecimal.valueOf(16));
		ocorrenciaAZShip.put("Entrega Realizada", BigDecimal.valueOf(17));
		ocorrenciaAZShip.put("Finalizado", BigDecimal.valueOf(18));

		return ocorrenciaAZShip;
	}
}
