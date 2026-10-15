package tech.skala.core.services;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.SessionBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.modelcore.util.BaseSPBean;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.SessionWrapper;
import tech.skala.core.util.json.SkalaJsonUtils;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.modules.manufatura.atividade.InstanciaAtividadeProcessor;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducao;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducaoFinder;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducaoModel;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducao;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducaoFinder;

/**
 * 
 * @author marcosdamasceno
 * @author hiagoalexandre
 * @author odilon
 * @since 2022-07-13
 * 
 * @ejb.bean name="ApontamentoProducaoSP"
 *           jndi-name="tech/skala/core/services/ApontamentoProducaoSP"
 *           type="Stateless" transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class ApontamentoProducaoSPBean extends BaseSPBean implements SessionBean {
	
	private static final long serialVersionUID = 5512277914888603700L;

	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.create();
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void buscaOrdemProducaoPorLinha(ServiceContext ctx) {
		SessionWrapper session = null;
		OrdemProducao ordemProducao = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			BigDecimal codWcp = null;

			JsonElement codWcpJE = paramsJO.get("codWcp");
			if (codWcpJE != null && codWcpJE.isJsonPrimitive())
				codWcp = codWcpJE.getAsBigDecimal();
			
			OrdemProducaoFinder finder = new OrdemProducaoFinder(codWcp);
			ordemProducao = finder.buscaOrdemProducaoPorLinha();

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, ordemProducao, errorMessagem);

			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void iniciaApontamento(ServiceContext ctx) {

		SessionWrapper session = null;
		OrdemProducao ordemProducao = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			String codBarra = null;
			BigDecimal nuvOP = null;
			JsonElement codBarraJE = paramsJO.get("codBarra");
			if (codBarraJE != null && codBarraJE.isJsonPrimitive()) {
				codBarra = codBarraJE.getAsString();

				ordemProducao = OrdemProducaoFinder.buscaOrdemProducaoPorCodBarra(codBarra);
				nuvOP = VolumeProducaoFinder.buscaNuvOPPorCodBarra(codBarra);
				
				OrdemProducaoModel model = new OrdemProducaoModel(ordemProducao);
				model.iniciaApontamento(nuvOP);
			}
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, ordemProducao, errorMessagem);

			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void confirmaApontamento(ServiceContext ctx) {

		SessionWrapper session = null;
		OrdemProducao ordemProducao = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			String codBarra = null;
			BigDecimal nuvOP;
			boolean ultimoApontamento;
			
			JsonElement codBarraJE = paramsJO.get("codBarra");
			if (SkalaJsonUtils.isJsonElementValid(codBarraJE)) {
				codBarra = codBarraJE.getAsString();
				
				ordemProducao = OrdemProducaoFinder.buscaOrdemProducaoPorCodBarra(codBarra);
				nuvOP = VolumeProducaoFinder.buscaNuvOPPorCodBarra(codBarra);
				
				List<VolumeProducao> apontamentosNaoProcessados = VolumeProducaoFinder.buscaApontamentosNaoProcessados(ordemProducao.getIdiProc());
				ultimoApontamento = (apontamentosNaoProcessados.isEmpty());
				
				OrdemProducaoModel model = new OrdemProducaoModel(ordemProducao);
				model.confirmaApontamento(nuvOP, ultimoApontamento);
			}
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, ordemProducao, errorMessagem);

			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void pararInstanciaAtividade(ServiceContext ctx) {

		SessionWrapper session = null;
		OrdemProducao ordemProducao = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			String codBarra = null;
			BigDecimal codMtp;
			String observacao = null;

			JsonElement codBarraJE = paramsJO.get("codBarra");
			JsonElement codMtpJE = paramsJO.get("codMtp");
			JsonElement observacaoJE = paramsJO.get("observacao");

			if (SkalaJsonUtils.isJsonElementValid(codBarraJE) && SkalaJsonUtils.isJsonElementValid(codMtpJE)
					&& SkalaJsonUtils.isJsonElementValid(observacaoJE)) {

				codBarra = codBarraJE.getAsString();
				codMtp = codMtpJE.getAsBigDecimal();
				observacao = observacaoJE.getAsString();
				
				ordemProducao = OrdemProducaoFinder.buscaOrdemProducaoPorCodBarra(codBarra);

				InstanciaAtividadeProcessor atividadeProcessor = new InstanciaAtividadeProcessor();
				atividadeProcessor.paraInstanciaAtividade(ordemProducao, codMtp, observacao);
			}

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, ordemProducao, errorMessagem);

			if (session != null)
				session.close();
		}
	}

	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void continuarInstanciaAtividade(ServiceContext ctx) {

		SessionWrapper session = null;
		OrdemProducao ordemProducao = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			String codBarra = null;

			JsonElement codBarraJE = paramsJO.get("codBarra");

			if (SkalaJsonUtils.isJsonElementValid(codBarraJE)) {
				codBarra = codBarraJE.getAsString();
				
				ordemProducao = OrdemProducaoFinder.buscaOrdemProducaoPorCodBarra(codBarra);

				InstanciaAtividadeProcessor atividadeProcessor = new InstanciaAtividadeProcessor();
				atividadeProcessor.continuaInstanciaAtividade(ordemProducao);
			}

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, ordemProducao, errorMessagem);

			if (session != null)
				session.close();
		}
	}	
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void finalizarInstanciaAtividade(ServiceContext ctx) {
		SessionWrapper session = null;
		OrdemProducao ordemProducao = null;
		String errorMessage = null;
		
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);
			
			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");
			
			String codBarra = null;
			
			JsonElement codBarraJE = paramsJO.get("codBarra");
			
			if(SkalaJsonUtils.isJsonElementValid(codBarraJE)) {
				codBarra = codBarraJE.getAsString();
				
				ordemProducao = OrdemProducaoFinder.buscaOrdemProducaoPorCodBarra(codBarra);

				InstanciaAtividadeProcessor atividadeProcessor = new InstanciaAtividadeProcessor();
				atividadeProcessor.finalizaInstanciaAtividade(ordemProducao);
				
			}
			
		} catch (Exception e) {
			errorMessage = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, ordemProducao, errorMessage);

			if (session != null)
				session.close();
		}
	}
	
	private void setContextResponse(ServiceContext ctx, OrdemProducao ordemProducao, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (ordemProducao == null) {
			status = "0";
			mensagem = "Ordem de Producao não localizada.";
		} else {
			status = "1";
			mensagem = "Ordem de Producao localizada.";
		}
		
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		String json = gson.toJson(ordemProducao);
		jsonResponse.add("ordemProducao", JsonUtils.parserString(json));

		ctx.setJsonResponse(jsonResponse);
	}
		
}
