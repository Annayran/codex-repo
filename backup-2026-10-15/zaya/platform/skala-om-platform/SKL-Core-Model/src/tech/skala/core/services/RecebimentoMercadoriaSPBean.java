package tech.skala.core.services;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.ejb.SessionBean;

import com.cbmsoft.modelcore.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.modelcore.util.BaseSPBean;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.beans.suprimento.recebimento.Conferencia;
import tech.skala.core.beans.suprimento.recebimento.Recebimento;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.SessionWrapper;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaFinder;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaProcessor;

/**
 * 
 * @author cassiomenezes
 * @author marcosdamasceno
 * @author hiagoalexandre
 * @since 2022-07-04
 * 
 * @ejb.bean name="RecebimentoMercadoriaSP"
 *           jndi-name="tech/skala/core/services/RecebimentoMercadoriaSP"
 *           type="Stateless" transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class RecebimentoMercadoriaSPBean extends BaseSPBean implements SessionBean {
	private final Logger				logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	
	private static final long serialVersionUID = -5546979684035439488L;
	
	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.create();
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void buscaRecebimentoEntrada(ServiceContext ctx) {
		SessionWrapper session = null;
		Recebimento recebimento = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			String chaveNfe = null;
			String cnpj = null;
			BigDecimal numNota = null;
			
			JsonElement chaveNfeJE = paramsJO.get("chaveNfe");
			if (chaveNfeJE == null || !chaveNfeJE.isJsonPrimitive()) {
				JsonElement cnpjJE = paramsJO.get("cnpj");
				if (cnpjJE != null && cnpjJE.isJsonPrimitive())
					cnpj = cnpjJE.getAsString();

				JsonElement numNotaJE = paramsJO.get("numNota");
				if (numNotaJE != null && numNotaJE.isJsonPrimitive())
					numNota = numNotaJE.getAsBigDecimal();

			} else {
				chaveNfe = chaveNfeJE.getAsString();
			}
			
			RecebimentoMercadoriaFinder model = new RecebimentoMercadoriaFinder();
			recebimento = (chaveNfe == null) ? model.buscaRecebimento(cnpj, numNota) : model.buscaRecebimento(chaveNfe);
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, recebimento, errorMessagem);

			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void enviaConferenciaEntrada(ServiceContext ctx) {
		
		SessionWrapper session = null;
		String msg;
		String errorMessagem = null;
		StringBuilder sbMessage = new StringBuilder();
		
		Conferencia conferencia = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);
			
			JsonObject jsonResponse = new JsonObject();
			
			JsonObject jsonRequestBody = ctx.getJsonRequestBody();
			JsonObject paramsJO = JsonUtils.getJsonObject(jsonRequestBody,"params");
			JsonObject recebimentoJO = JsonUtils.getJsonObject(paramsJO,"recebimento");
			Recebimento recebimento = gson.fromJson(recebimentoJO, Recebimento.class);

			logger.info("recebimento: " + recebimentoJO.toString());
			
			RecebimentoMercadoriaProcessor processor = new RecebimentoMercadoriaProcessor();
			processor.processaConferenciaEntrada(recebimento);
			
			conferencia = recebimento.getConferencia();
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();

		} finally {
			setContextResponseConferencia(ctx, conferencia, errorMessagem);
			
			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @param ctx
	 * @param recebimento
	 * @param sbErrorMessagem 
	 */
	private void setContextResponse(ServiceContext ctx, Recebimento recebimento, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (recebimento == null) {
			status = "0";
			mensagem = "Recebimento não localizado.";
		} else {
			status = "1";
			mensagem = "Recebimento localizado.";
		}
		
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		String recebimentoJson = gson.toJson(recebimento);
		jsonResponse.add("recebimento", JsonUtils.parserString(recebimentoJson));
		
		ctx.setJsonResponse(jsonResponse);

	}
	
	/**
	 * @param ctx
	 * @param conferencia
	 * @param sbErrorMessagem 
	 */
	private void setContextResponseConferencia(ServiceContext ctx, Conferencia conferencia, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (conferencia == null) {
			status = "0";
			mensagem = "Conferência não processada.";
		} else {
			status = "1";
			mensagem = "Conferência processada.";
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
