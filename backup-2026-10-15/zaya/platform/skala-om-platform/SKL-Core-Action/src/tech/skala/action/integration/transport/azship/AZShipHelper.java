package tech.skala.action.integration.transport.azship;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.cbmsoft.modelcore.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.JsonUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.integration.transport.callcenter.CallCenter;
import tech.skala.action.integration.transport.callcenter.CallCenterProcessor;
import tech.skala.action.integration.transport.callcenter.OcorrenciaCallCenter;
import tech.skala.action.integration.transport.hub.HubTransportHelper;
import tech.skala.action.integration.transport.ocorrencianaentrega.Entregas;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaDeEntrega;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaOrigemEnum;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.commons.OcorrenciaTransportadoraFinder;

public class AZShipHelper {
	private static final Logger				LOGGER = new LoggerFactory(AZShipHelper.class.getCanonicalName()).getLogger();

	private static Gson gson = new Gson();
	private BigDecimal codEmp;
	private BigDecimal ordemCarga;
	private BigDecimal nuNota;
	private List<OrderDTO> orders;
	private HubTransportHelper helper;
	private String nomeProcesso;
	private String request;
	private String response;
	private String status;
	private List<EventDTO> events;
	private List<OccurrenceDTO> occurrences;
	private CallCenter callCenter;
	
	public AZShipHelper(BigDecimal codEmp, BigDecimal ordemCarga) {
		this.codEmp = codEmp;
		this.ordemCarga = ordemCarga;
	}

	public AZShipHelper(BigDecimal codEmp, BigDecimal ordemCarga, BigDecimal nuNota) {
		this.codEmp = codEmp;
		this.ordemCarga = ordemCarga;
		this.nuNota = nuNota;
	}

	public void sendShipping() throws Exception {
		ShippingSaveDTO shippingSaveDTO = getDataShipping();

		orders = getDataOrders();

		final String body = this.createJsonBody(shippingSaveDTO, orders);

		if (body != null) {
			String response = dispatchSendShipping(body);
			JsonParser jsonParser = new JsonParser();
			JsonElement shippingJE = jsonParser.parse(response);

			ResponseDTO res = gson.fromJson(shippingJE, ResponseDTO.class);

			if (res.getCode().compareTo(new BigDecimal(200)) == 0) {
				createLog();
			}

			this.nomeProcesso = String.format("%s.%s", getClass().getCanonicalName(),
					Thread.currentThread().getStackTrace()[1].getMethodName());
			this.request = body;
			this.response = response;

			setHubTransportAndRequestResponse();
		}
	}

	private String dispatchSendShipping(String body) throws Exception {
		Integer statusCode = null;
		String responseBody = null;
		String queryString;
		final HttpClient httpClient = new HttpClient();

		try {
			StringRequestEntity requestEntity = new StringRequestEntity(body, "application/json", "UTF-8");

			String msg = String.format("body: %s", body);
			LOGGER.info(msg);

			final PostMethod method = new PostMethod(AZShipConstants.ENDPOINT + AZShipConstants.POST_SHIPPING);
			method.addRequestHeader(new Header("x-azship-token", AZShipConstants.TOKEN));
			method.addRequestHeader(new Header("Content-Type", AZShipConstants.CONTENT_TYPE_JSON));
			method.setRequestEntity(requestEntity);

			queryString = method.getQueryString();
			msg = String.format("queryString: %s", queryString);
			LOGGER.info(msg);

			statusCode = httpClient.executeMethod(method);
			msg = String.format("statusCode: %d", statusCode);
			LOGGER.info(msg);
			
			responseBody = method.getResponseBodyAsString();
			msg = String.format("responseBody: %s", responseBody);
			LOGGER.info(msg);
			
			ResponseDTO responseDTO = gson.fromJson(responseBody, ResponseDTO.class);
			msg = String.format("response: %s", responseDTO);
			LOGGER.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	public void getShipping() throws Exception {
		orders = getDataOrders();

		String url = (AZShipConstants.ENDPOINT + AZShipConstants.GET_SHIPPINGS_ID).replace("{code}", ordemCarga.toString());

		this.nomeProcesso = String.format("%s.%s", getClass().getCanonicalName(),
				Thread.currentThread().getStackTrace()[1].getMethodName());
		this.request = url;

		dispatchGetShipping(url);
	}

	private String dispatchGetShipping(String url) throws Exception {
		Integer statusCode = null;
		String responseBody = null;
		ShippingDTO shipping = null;
		ResponseDTO responseDTO = null;
		final HttpClient request = new HttpClient();

		try {
			final GetMethod method = new GetMethod(url);
			method.addRequestHeader(new Header("x-azship-token", AZShipConstants.TOKEN));
			method.addRequestHeader(new Header("Content-Type", AZShipConstants.CONTENT_TYPE_JSON));

			statusCode = request.executeMethod(method);
			responseBody = method.getResponseBodyAsString();

			String msg = String.format("url: %s", url);
			LOGGER.info(msg);
			msg = String.format("statusCode: %d", statusCode);
			LOGGER.info(msg);
			msg = String.format("responseBody: %s", responseBody);
			LOGGER.info(msg);

			this.response = responseBody;

			if (statusCode == 200) {
				shipping = gson.fromJson(responseBody, ShippingDTO.class);
				msg = String.format("shipping: %s", shipping.toString());
				LOGGER.info(msg);

				this.status = shipping.getStatus();
				this.events = shipping.getEvents();

				List<String> statusInvalidos = Arrays.asList("Entrega finalizada","Motorista Avaliado");
				if(!statusInvalidos.contains(this.status)) {
					setHubTransportStatus();
					
					if (!events.isEmpty())
						setHubTransportEvents();
				}
				

			} else {
				responseDTO = gson.fromJson(responseBody, ResponseDTO.class);
				msg = String.format("shipping: %s", responseDTO);
				LOGGER.info(msg);

				setHubTransportAndRequestResponse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	public void getOccurrenceByShippingCode() throws Exception {
		orders = getDataOrders();

		String url = (AZShipConstants.ENDPOINT + AZShipConstants.GET_OCCURRENCE_CODE).replace("{shippingCode}",
				ordemCarga.toString());

		this.nomeProcesso = String.format("%s.%s", getClass().getCanonicalName(),
				Thread.currentThread().getStackTrace()[1].getMethodName());
		this.request = url;

		dispatchGetOcurrence(url);
	}

	public void getOccurrenceByShippingCodeAndOrderCode() throws Exception {
		orders = getDataOrders();

		String url = (AZShipConstants.ENDPOINT + AZShipConstants.GET_OCCURRENCE_CODE_ORDER)
				.replace("{shippingCode}", ordemCarga.toString()).replace("{orderCode}", nuNota.toString());

		this.nomeProcesso = String.format("%s.%s", getClass().getCanonicalName(),
				Thread.currentThread().getStackTrace()[1].getMethodName());
		this.request = url;

		dispatchGetOcurrence(url);
	}

	private String dispatchGetOcurrence(String url) throws Exception {
		Integer statusCode = null;
		String responseBody = null;
		final HttpClient request = new HttpClient();

		try {
			final GetMethod method = new GetMethod(url);
			method.addRequestHeader(new Header("x-azship-token", AZShipConstants.TOKEN));
			method.addRequestHeader(new Header("Content-Type", AZShipConstants.CONTENT_TYPE_JSON));

			statusCode = request.executeMethod(method);
			responseBody = method.getResponseBodyAsString();

			String msg = String.format("url: %s", url);
			LOGGER.info(msg);
			msg = String.format("statusCode: %d", statusCode);
			LOGGER.info(msg);
			msg = String.format("responseBody: %s", responseBody);
			LOGGER.info(msg);

			this.response = responseBody;

			if (statusCode == 200 && !responseBody.equals("[]")) {
				Type collectionType = new TypeToken<List<OccurrenceDTO>>() {
				}.getType();
				occurrences = new Gson().fromJson(responseBody, collectionType);

				msg = String.format("occurrence: %s", occurrences.toString());
				LOGGER.info(msg);

				if (url.contains("orderCode")) {
					setHubTransportOccurrenceByShippingCodeAndOrderCode();
				} else {
					setHubTransportOccurrenceByShippingCode();
				}
			} else {
				msg = String.format("occurrence: %s", response);
				LOGGER.info(msg);

				setHubTransportAndRequestResponse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	/*
	 * 
	 */
	private ShippingSaveDTO getDataShipping() throws SkalaModelException {
		String[] targets = getListTransporters();

		ShippingSaveDTO shippingSaveDTO = new ShippingSaveDTO();

		shippingSaveDTO.setCode(this.ordemCarga.toString());
		shippingSaveDTO.setTargets(targets);
		shippingSaveDTO.setFreightPrice(BigDecimal.ZERO);
		shippingSaveDTO.setTollPrice(BigDecimal.ZERO);
		shippingSaveDTO.setFeeDeliveryPrice(BigDecimal.ZERO);
		shippingSaveDTO.setObservation("");

		return shippingSaveDTO;
	}

	private String[] getListTransporters() throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ORDEM_CARGA);
		
		String[] targets = new String[1];
		try {
			DynamicVO ordemCargaVO = dao.findByPK(codEmp, ordemCarga);
			String cnpj = ordemCargaVO.asString("Transportadora.CGC_CPF");
			
			targets[0] = cnpj;
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar transportador para carga", e);
		}
		
		return targets;
	}

	private List<OrderDTO> getDataOrders() {
		orders = new ArrayList<>();

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(getClass(), "CriaCarga_queDadosPedidos.sql");
			sql.setNamedParameter("CODEMP", this.codEmp);
			sql.setNamedParameter("ORDEMCARGA", this.ordemCarga);

			rs = sql.executeQuery();

			OrderDTO order = null;
			while (rs.next()) {
				order = new OrderDTO();
				order.setCode(rs.getBigDecimal("NUNOTA").toString());
				order.setCollectDocument(rs.getString("CGC"));
				order.setCollectAmount(rs.getBigDecimal("QTD"));
				order.setCollectWeight(rs.getBigDecimal("PESOBRUTO"));
				order.setCollectDate(rs.getString("DTPREVSAIDA"));
				order.setDeliveryDocument(rs.getString("CGC_CPF"));
				order.setDeliveryName(rs.getString("RAZAOSOCIAL"));
				order.setDeliveryStreet(rs.getString("LOGRADOURO"));
				order.setDeliveryCEP(rs.getString("CEP"));
				order.setDeliveryDistrict(rs.getString("NOMEBAI"));
				order.setDeliveryCity(rs.getString("NOMECID"));
				order.setDeliveryUF(rs.getString("UF"));
				order.setDeliveryLatitude(rs.getBigDecimal("LATITUDE"));
				order.setDeliveryLongitude(rs.getBigDecimal("LONGITUDE"));
				order.setDeliveryDate(rs.getString("DTPREVENT"));
				order.setOrderValue(rs.getBigDecimal("VLRNOTA"));
				order.setObservation(rs.getString("OBSERVACAO"));

				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return orders;
	}

	private String createJsonBody(ShippingSaveDTO shippingSaveDTO, List<OrderDTO> orders) {
		final String jsonObject = new Gson().toJson(shippingSaveDTO, ShippingSaveDTO.class);

		JsonObject jsonShipping = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		JsonParser parser = new JsonParser();
		jsonShipping = (JsonObject) parser.parse(jsonObject);

		for (OrderDTO order : orders) {
			jsonArray.add(JsonUtils.convertStringToJsonObject(gson.toJson(order, OrderDTO.class)));
		}

		jsonShipping.add("orders", jsonArray);

		if (!jsonShipping.isJsonNull()) {
			return jsonShipping.toString();
		}

		return null;
	}

	private void createLog() {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				JapeWrapper azsOcpDao = JapeFactory.dao("AD_PLVAZSOCP");
				JapeWrapper azsLogDao = JapeFactory.dao("AD_PLTAZSLOG");

				DynamicVO azsOcpVO = azsOcpDao.findByPK(codEmp, ordemCarga);
				DynamicVO azsLogVO = azsLogDao.findByPK(codEmp, ordemCarga);

				@Override
				public void doWithTx() throws Exception {
					if (azsLogVO == null) {
						FluidCreateVO azsLogNewVO = azsLogDao.create();
						azsLogNewVO.set("CODEMP", codEmp);
						azsLogNewVO.set("ORDEMCARGA", ordemCarga);
						azsLogNewVO.set("ORDEMFILHA", azsOcpVO.asString("ORDEMFILHA"));
						azsLogNewVO.set("AZSENVIO", "S");
						azsLogNewVO.set("AZSDHENVIO", TimeUtils.getNow());
						azsLogNewVO.save();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}
	}

	private void setHubTransportAndRequestResponse() throws Exception {
		for (OrderDTO order : orders) {
			helper = new HubTransportHelper(codEmp, ordemCarga, BigDecimalUtil.getBigDecimal(order.getCode()));
			helper.create();
			helper.createRequestResponse(nomeProcesso, request, response);
		}
	}

	private void setHubTransportStatus() throws Exception {
		for (OrderDTO order : orders) {
			helper = new HubTransportHelper(codEmp, ordemCarga, BigDecimalUtil.getBigDecimal(order.getCode()));
			helper.create();
			helper.createRequestResponse(nomeProcesso, request, response);
			helper.createStatus(status);
		}
	}

	private void setHubTransportEvents() throws Exception {
		
		Map<String, BigDecimal> ocorrenciaAZShip = new HashMap<>();
	    ocorrenciaAZShip.put("Frete lançado",BigDecimal.ONE);
	    ocorrenciaAZShip.put("Transportadora Vinculada",BigDecimal.valueOf(2));
	    ocorrenciaAZShip.put("Aguardando Motorista",BigDecimal.valueOf(3));
	    ocorrenciaAZShip.put("Motorista/Veículo vinculado",BigDecimal.valueOf(4));
	    ocorrenciaAZShip.put("Check-in Realizado",BigDecimal.valueOf(5));
	    ocorrenciaAZShip.put("Comentário durante check-in",BigDecimal.valueOf(6));
	    ocorrenciaAZShip.put("Solicitação de entrada",BigDecimal.valueOf(7));
	    ocorrenciaAZShip.put("Comentário ao solicitar entrada",BigDecimal.valueOf(8));
	    ocorrenciaAZShip.put("Marcação de entrada",BigDecimal.valueOf(9));
	    ocorrenciaAZShip.put("Comentário na marcação de entrada",BigDecimal.valueOf(10));
	    ocorrenciaAZShip.put("Coleta iniciada",BigDecimal.valueOf(11));
	    ocorrenciaAZShip.put("Comentário ao iniciar coleta",BigDecimal.valueOf(12));
	    ocorrenciaAZShip.put("Coleta finalizada",BigDecimal.valueOf(13));
	    ocorrenciaAZShip.put("Comentário ao finalizar coleta",BigDecimal.valueOf(14));
	    ocorrenciaAZShip.put("Carga em trânsito",BigDecimal.valueOf(15));
	    ocorrenciaAZShip.put("Próximo ao cliente",BigDecimal.valueOf(16));
	    ocorrenciaAZShip.put("Entrega Realizada",BigDecimal.valueOf(17));
	    ocorrenciaAZShip.put("Finalizado",BigDecimal.valueOf(18));
		
		for (OrderDTO order : orders) {
			
			LOGGER.info("nunota: " + BigDecimalUtil.getBigDecimal(order.getCode()));
			
			DynamicVO cabVO = processaCallCenter(BigDecimalUtil.getBigDecimal(order.getCode()));
			
			helper = new HubTransportHelper(codEmp, ordemCarga, BigDecimalUtil.getBigDecimal(order.getCode()));
			helper.create();

			helper.clearEvents();
			
			String msg = String.format("NF-eVO is null? %s", cabVO == null);
			LOGGER.info(msg);
			
			for (EventDTO event : events) {
				helper.createEvent(event);
				
				msg = String.format("[EVENT.NAME %s] Existe no Mapa de ocorrências AZShip? %s", event.getName(), ocorrenciaAZShip.containsKey(event.getName()));
				LOGGER.info(msg);
				
				BigDecimal codOcoTransp;
				if (ocorrenciaAZShip.containsKey(event.getName()) && cabVO != null) {
					LOGGER.info("Entrou para gerar ocorrência");
					
					codOcoTransp = ocorrenciaAZShip.get(event.getName());
					geraOcorrenciaCallCenter(codOcoTransp, event);
					
					BigDecimal nuNotaNFe = cabVO.asBigDecimal(SklFieldName.NUNOTA);
					geraOcorrenciaDeEntrega(nuNotaNFe, event, codOcoTransp);
					
				}
			}
		}
	}

	private void geraOcorrenciaDeEntrega(BigDecimal nuNotaNFe, EventDTO event, BigDecimal codOcoTransp) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					Entregas entrega = buildEntregaOcorrencia(nuNotaNFe);
					geraOcorrenciaEntrega(entrega, event, codOcoTransp);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrências de Entrega.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	private void geraOcorrenciaEntrega(Entregas entrega, EventDTO event, BigDecimal codOcoTransp) throws SkalaModelException {

		try {
			BigDecimal codOcoPlatina;
			OcorrenciaDeEntrega ocorrenciaDeEntrega = new OcorrenciaDeEntrega();

			ocorrenciaDeEntrega.setNuNota(entrega.getNuNota());
			codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoTransp, BigDecimal.valueOf(25772));

			ocorrenciaDeEntrega.setCodOco(codOcoPlatina);
			ocorrenciaDeEntrega.setDhOcorrencia(event.getDate());
			if (!entrega.getOcorrencias().contains(ocorrenciaDeEntrega) && codOcoPlatina != null) {
				ocorrenciaDeEntrega.setObservacao(event.getObservation());
				ocorrenciaDeEntrega.setOrigem(OcorrenciaOrigemEnum.INTEGRACAO.getValue());
				ocorrenciaDeEntrega.persists();

				entrega.getOcorrencias().add(ocorrenciaDeEntrega);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrência na Entrega.", e);
		}

	}


	private Entregas buildEntregaOcorrencia(BigDecimal nuNotaNFe) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFENT");
		Entregas entrega = new Entregas();

		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nuNotaNFe);

			if (vo == null) {
				entrega.setNuNota(nuNotaNFe);
				entrega.setCodEmp(codEmp);
				entrega.setOrdemCarga(ordemCarga);
				// calcular Data Limite de Entrega
				// entrega.setDtLimiteEntrega(null);

				entrega.persists();
			} else {
				entrega.loadByVO(vo);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar Ocorrência de Entrega.", e);
		}
		return entrega;
	}

	private void geraOcorrenciaCallCenter(BigDecimal codOcoTransp, EventDTO event) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					BigDecimal codOcoPlatina;
					OcorrenciaCallCenter ocorrencia = new OcorrenciaCallCenter();

					ocorrencia.setCodCal(callCenter.getCodCal());
					codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoTransp,
							BigDecimal.valueOf(25772));
					ocorrencia.setCodOco(codOcoPlatina);
					ocorrencia.setDhOcorrencia(event.getDate());

					String msg = String.format("[CODOCOAZSHIP - %s][CODOCOPLATINA - %s] Localizou uma ocorrencia da Platina.", codOcoTransp, codOcoPlatina);
					LOGGER.info(msg);
					
					if (!callCenter.getOcorrencias().contains(ocorrencia) && codOcoPlatina != null) {
						msg = String.format("[CODCAL %s][CODOCO %s] O Call Center não possui a ocorrência lançada... Inserindo.", callCenter.getCodCal(), codOcoPlatina);
						LOGGER.info(msg);
						
						ocorrencia.setObservacao((2L == codOcoPlatina.longValue()) ? event.getName() : event.getObservation());
						ocorrencia.setUsuarioExt(event.getOwner());
						
						msg = "É uma ocorrência de entrega ? " + (2L == codOcoPlatina.longValue());
						LOGGER.info(msg);
						
						if(!buscaOcorrenciaEntrega(callCenter, codOcoPlatina)) {
							ocorrencia.persists();							
							callCenter.getOcorrencias().add(ocorrencia);
							
							msg = String.format("[CODCAL %s][CODOCO %s] Ocorrência cadastrada.", callCenter.getCodCal(), codOcoPlatina);
							LOGGER.info(msg);
						}
						
					}

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível cadastrar ocorrência.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	protected boolean buscaOcorrenciaEntrega(CallCenter callCenter, BigDecimal codOcoPlatina) {

		if(2L != codOcoPlatina.longValue())
			return false;
		
		boolean encontrei = false;
		for(OcorrenciaCallCenter ocorrencia : callCenter.getOcorrencias()) {
			
			if(2L == ocorrencia.getCodOco().longValue()) {
				encontrei = true;
				break;
			}
		}
		return encontrei;
	}

	private DynamicVO processaCallCenter(BigDecimal nuNota) throws Exception {
		DynamicVO cabVO = null;
		
		JapeWrapper varDAO = JapeFactory.dao(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO);
		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		
		DynamicVO varVO = varDAO.findOne("this.NUNOTAORIG = ?", nuNota);
		BigDecimal nuNotaNFe;
		
		if (varVO != null) {
			nuNotaNFe = varVO.asBigDecimal(SklFieldName.NUNOTA);
			cabVO = cabDAO.findByPK(nuNotaNFe);
			
			if("L".equals(cabVO.asString(SklFieldName.STATUSNOTA))) {
				processarCallCenter(cabVO);				
			} else {
				cabVO = null;
			}
			
		}
		
		return cabVO;
	}

	private void processarCallCenter(DynamicVO cabVO) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					callCenter = CallCenterProcessor.localizaOuCriaNovoCallCenter(cabVO);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar CallCenter.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}
	
	private void setHubTransportOccurrenceByShippingCode() throws Exception {
		for (OccurrenceDTO occurrence : occurrences) {
			for (OrderDTO order : orders) {
				helper = new HubTransportHelper(codEmp, ordemCarga, BigDecimalUtil.getBigDecimal(order.getCode()));
				helper.create();
				helper.createRequestResponse(nomeProcesso, request, response);
				helper.createOccurrence(occurrence);
			}
		}
	}

	private void setHubTransportOccurrenceByShippingCodeAndOrderCode() throws Exception {
		for (OccurrenceDTO occurrence : occurrences) {
			helper = new HubTransportHelper(codEmp, ordemCarga,
					BigDecimalUtil.getBigDecimal(occurrence.getOrderCode()));
			helper.create();
			helper.createRequestResponse(nomeProcesso, request, response);
			helper.createOccurrence(occurrence);
		}
	}
}
