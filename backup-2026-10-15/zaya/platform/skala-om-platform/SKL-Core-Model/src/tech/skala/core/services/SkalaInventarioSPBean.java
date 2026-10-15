package tech.skala.core.services;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.SessionBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.BaseSPBean;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.SessionWrapper;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.modules.inventario.Inventario;
import tech.skala.modules.inventario.InventarioFinder;
import tech.skala.modules.inventario.ProdutoContagemFinder;
import tech.skala.modules.inventario.ProdutoInventario;

/**
 * 
 * @author hiagoalexandre
 * @since 2022-11-03
 * 
 * @ejb.bean name="SkalaInventarioSP"
 *           jndi-name="tech/skala/core/services/SkalaInventarioSP"
 *           type="Stateless" transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class SkalaInventarioSPBean extends BaseSPBean implements SessionBean {

	private static final long serialVersionUID = -5779414833938996975L;

	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.create();
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void buscaTarefasContagem(ServiceContext ctx){
		SessionWrapper session = null;
		List<Inventario> inventarios = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);
			
			if(ctx.getJsonRequestBody() != null) {
				JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");
				
				ContagemInventarioProcessor processor = new ContagemInventarioProcessor();
				processor.processaContagens(paramsJO);
			}
			
			BigDecimal codExecutante = AuthenticationInfo.getCurrent().getUserID();
			inventarios = InventarioFinder.buscaInventariosPorUsuarioExecutante(codExecutante);
			
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			JsonObject jsonResponse = setContextResponse(inventarios, errorMessagem);
			ctx.setJsonResponse(jsonResponse);

			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void buscaProdutosLote(ServiceContext ctx) {
		SessionWrapper session = null;
		List<ProdutoInventario> produtos = null;
		String errorMessagem = null;

		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);
			
			produtos = ProdutoContagemFinder.buscaProdutosInventario();
			
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			JsonObject jsonResponse = setContextResponse(produtos, errorMessagem);
			ctx.setJsonResponse(jsonResponse);

			if (session != null)
				session.close();
		}
	}
	
	private JsonObject setContextResponse(List<?> list, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		boolean isInventario = false;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if(list == null) {
			status = "0";
			mensagem = "Dados não encontrados.";
		} else {
			status = "1";
			isInventario = list.get(0) instanceof Inventario;
			
			mensagem = (isInventario) ? "Inventário(s) " : "Produto(s) ";
			mensagem += "localizado(s).";
		}
				
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		String json = gson.toJson(list);
		jsonResponse.add(isInventario ? "inventarios" : "produtos", JsonUtils.parserString(json));

		return jsonResponse;
				
	}
}
