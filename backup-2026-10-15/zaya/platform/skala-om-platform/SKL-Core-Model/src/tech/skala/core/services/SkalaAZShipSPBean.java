package tech.skala.core.services;

import java.util.logging.Logger;

import javax.ejb.SessionBean;

import com.cbmsoft.modelcore.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.modelcore.util.BaseSPBean;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.dd.DTO;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.SessionWrapper;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.modules.logistica.azship.ShippingDTO;
import tech.skala.modules.logistica.azship.SkalaAZShipProcessor;
import tech.skala.modules.logistica.azship.UpdateDriverVehicleDTO;
import tech.skala.modules.logistica.azship.UpdateEventDTO;

/**
 * 
 * @author hiagoalexandre
 * @since 2023-06-23
 * 
 * @ejb.bean name="SkalaAZShipSP"
 *           jndi-name="tech/skala/core/services/SkalaAZShipSP" type="Stateless"
 *           transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class SkalaAZShipSPBean extends BaseSPBean implements SessionBean {

	private static final Logger LOGGER = new LoggerFactory(SkalaAZShipSPBean.class.getCanonicalName()).getLogger();
	private static final long serialVersionUID = 56998327014516709L;

	private static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("dd/MM/yyyy HH:mm:ss").create();

	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void shipping(ServiceContext ctx) {
		
		String message = "endpoint: SkalaAZShipSPBean.shipping";
		LOGGER.info(message);
		
		SessionWrapper session = null;
		String errorMessagem = null;

		ShippingDTO shippingDTO = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			shippingDTO = gson.fromJson(paramsJO, ShippingDTO.class);

			if (shippingDTO != null) {

				message = String.format("shipping recebido: %s", shippingDTO);
				LOGGER.info(message);

				/*
				 * é necessário analisar os pedidos de acordo com o estado, pra poder fechar a
				 * OR
				 * 
				 * fracionado: é necessário fechar os pedidos por estado há transportador que
				 * exige por cliente há transportador que exige por região, de acordo com a área
				 * de atendimento
				 * 
				 * fechada: 1 cliente, apenas 1 pedido 1 cliente, vários pedidos 2 ou mais
				 * clientes, 1 cliente por filho
				 */

				// Sempre empresa 2
				// Cria O.C Pai
				// Cria O.C Filha
				// Relaciona na OR

				SkalaAZShipProcessor processor = new SkalaAZShipProcessor();
				processor.processaOrdemCarga(shippingDTO);

			}

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage("Ocorreu um erro durante a criação da Ordem de Carga.", e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, shippingDTO, errorMessagem);

			if (session != null)
				session.close();
		}

	}

	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void atualizaMotorista(ServiceContext ctx) {
		
		String message = "endpoint: SkalaAZShipSPBean.atualizaMotorista";
		LOGGER.info(message);
		
		SessionWrapper session = null;

		String errorMessagem = null;
		UpdateDriverVehicleDTO dto = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			dto = gson.fromJson(paramsJO, UpdateDriverVehicleDTO.class);

			if (dto != null) {

				message = String.format("DTO recebido: %s", dto);
				LOGGER.info(message);

				SkalaAZShipProcessor processor = new SkalaAZShipProcessor();
				processor.updateDriverVehicle(dto);

			}

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage("Ocorreu um erro durante a atualização do Motorista para a Ordem de Carga.", e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, dto, errorMessagem);

			if (session != null)
				session.close();
		}

	}

	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void atualizaEventos(ServiceContext ctx) {
		
		String message = "endpoint: SkalaAZShipSPBean.atualizaEventos";
		LOGGER.info(message);
		
		SessionWrapper session = null;

		String errorMessagem = null;
		UpdateEventDTO dto = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			dto = gson.fromJson(paramsJO, UpdateEventDTO.class);

			if (dto != null) {

				message = String.format("DTO recebido: %s", dto);
				LOGGER.info(message);

				SkalaAZShipProcessor processor = new SkalaAZShipProcessor();
				processor.atualizaEventos(dto);

			}

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage("Ocorreu um erro durante a atualização dos eventos para a Ordem de Carga.", e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, dto, errorMessagem);

			if (session != null)
				session.close();
		}

	}

	private void setContextResponse(ServiceContext ctx, DTO shippingDTO, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (shippingDTO == null) {
			status = "0";
			mensagem = "Carga não localizada.";
		} else {
			status = "1";
			mensagem = "Carga localizada.";
		}

		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		ctx.setJsonResponse(jsonResponse);

	}
}
