package tech.skala.core.services;

import java.math.BigDecimal;

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
import tech.skala.modules.estoque.ProdutoEtiquetaDTO;
import tech.skala.modules.estoque.ProdutoEtiquetaFinder;

/**
 * 
 * @author hiagoalexandre
 * @since 2024-09-03
 * 
 * @ejb.bean name="SkalaEtiquetaSP"
 *           jndi-name="tech/skala/core/services/SkalaEtiquetaSP"
 *           type="Stateless" transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class SkalaEtiquetaSPBean extends BaseSPBean implements SessionBean {

	private static final long serialVersionUID = 7026580848595082670L;

	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.create();
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void getProdutoEtiqueta(ServiceContext ctx) {
		SessionWrapper session = null;
		ProdutoEtiquetaDTO produtoEtiqueta = null;
		String errorMessagem = null;

		BigDecimal codProd;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			if (ctx.getJsonRequestBody() != null) {
				
				JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");
				JsonElement codProdJE = paramsJO.get("codProd");
				if (SkalaJsonUtils.isJsonElementValid(codProdJE)) {
					codProd = codProdJE.getAsBigDecimal();

					produtoEtiqueta = ProdutoEtiquetaFinder.getProdutoEtiqueta(codProd);

				}
			}

		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage("Não foi possível localizar Produto p/ Etiqueta.", e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, produtoEtiqueta, errorMessagem);

			if (session != null)
				session.close();
		}
	}

	private void setContextResponse(ServiceContext ctx, ProdutoEtiquetaDTO produtoEtiqueta, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (produtoEtiqueta == null) {
			mensagem = "Produto não localizado.";
		} else {
			mensagem = "Produto localizado.";
		}
		
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		String produtoJson = gson.toJson(produtoEtiqueta);
		jsonResponse.add("produto", JsonUtils.parserString(produtoJson));
		
		ctx.setJsonResponse(jsonResponse);

	}

}
