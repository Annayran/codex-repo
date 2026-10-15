package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JsonUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.integration.transport.ProcessamentoOcorrenciaFactory;
import tech.skala.action.integration.transport.hub.HubTransport;
import tech.skala.action.integration.transport.hub.HubTransportDAO;
import tech.skala.action.integration.transport.hub.HubTransportRequestResponse;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AZShipProcessor {

	public void getShipping(BigDecimal codEmp, BigDecimal ordemCarga) throws SkalaModelException {

		ApiResponse apiResponse = AZShipConnector.getShipping(ordemCarga);
		try {

			if (apiResponse.isSuccess()) {
				ShippingDTO shippingDTO = new Gson().fromJson(apiResponse.getResponse(), ShippingDTO.class);

				ProcessamentoOcorrenciaFactory ocorrenciaFactory = new ProcessamentoOcorrenciaAZShipFactory(codEmp,
						ordemCarga, shippingDTO);
				ocorrenciaFactory.process();

			}

		} finally {
			List<OrderDTO> ordersDTO = AZShipDAO.buscaInformacoesPedido(codEmp, ordemCarga);

			BigDecimal nuNota;
			HubTransport hubTransport;
			for (OrderDTO orderDTO : ordersDTO) {
				nuNota = BigDecimalUtil.getBigDecimal(orderDTO.getCode());
				hubTransport = HubTransportDAO.findHubTransportPeloNuNota(nuNota);

				if (hubTransport != null) {
					createRequestResponse(hubTransport, apiResponse);
				}

			}

		}
	}

	public void sendShipping(BigDecimal codEmp, BigDecimal ordemCarga) throws SkalaModelException {

		ShippingSaveDTO shippingSaveDTO = AZShipDAO.buscaShippingSave(codEmp, ordemCarga);
		String payload = createJsonBody(shippingSaveDTO, codEmp, ordemCarga);

		ApiResponse apiResponse = AZShipConnector.postShipping(payload);

		try {
			if(apiResponse.isSuccess()) {
				
				
				
			}
			
		} finally {
			
			BigDecimal nuNota;
			for (OrderDTO orderDTO : shippingSaveDTO.getOrders()) {
				nuNota = BigDecimalUtil.getBigDecimal(orderDTO.getCode());
				HubTransport hubTransport = HubTransportDAO.findHubTransportPeloNuNota(nuNota);
				
				
			}
		}
		
	}

	private String createJsonBody(ShippingSaveDTO shippingSaveDTO, BigDecimal codEmp, BigDecimal ordemCarga)
			throws SkalaModelException {

		List<OrderDTO> ordersDTO = AZShipDAO.buscaInformacoesPedido(codEmp, ordemCarga);

		final String jsonObject = new Gson().toJson(shippingSaveDTO, ShippingSaveDTO.class);

		JsonObject jsonShipping = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		JsonParser parser = new JsonParser();
		jsonShipping = (JsonObject) parser.parse(jsonObject);

		for (OrderDTO orderDTO : ordersDTO)
			jsonArray.add(JsonUtils.convertStringToJsonObject(new Gson().toJson(orderDTO, OrderDTO.class)));

		jsonShipping.add("orders", jsonArray);

		return !jsonShipping.isJsonNull() ? jsonShipping.toString() : null;

	}

	private void createRequestResponse(HubTransport hubTransport, ApiResponse apiResponse) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					String nomeProcesso = String.format("%s.%s", getClass().getCanonicalName(),
							Thread.currentThread().getStackTrace()[1].getMethodName());

					String url = String.format("%s%s", AZShipConstants.ENDPOINT, AZShipConstants.GET_SHIPPINGS_ID
							.replace("{code}", hubTransport.getOrdemCarga().toString()));

					HubTransportRequestResponse requestResponse = new HubTransportRequestResponse(hubTransport);
					requestResponse.setDtReq(TimeUtils.getNow());
					requestResponse.setNomeProcesso(nomeProcesso);
					requestResponse.setResponse(apiResponse.getResponse());
					requestResponse.setRequest(url);
					requestResponse.persists();

					hubTransport.getRequestResponses().add(requestResponse);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível criar o Request Response.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void buscaCarga(BigDecimal ordemCarga) throws SkalaModelException {

		ApiResponse apiResponse = AZShipConnector.getShipping(ordemCarga);

		if (422 == apiResponse.getResponseCode())
			HubTransportDAO.excluiRegistroHubTransport(ordemCarga);

	}

}
