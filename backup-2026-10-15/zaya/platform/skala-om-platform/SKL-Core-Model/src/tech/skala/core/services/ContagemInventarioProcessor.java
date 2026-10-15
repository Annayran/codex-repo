package tech.skala.core.services;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.inventario.ContagemInventario;
import tech.skala.modules.inventario.ProdutoContagem;

public class ContagemInventarioProcessor {
	
	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.create();
	
	public void processaContagens(JsonObject paramsJO) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					List<ContagemInventario> contagens = getContagensFromJson(paramsJO);
					
					for (ContagemInventario contagem : contagens)
						processarContagem(contagem);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar a contagem.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	private List<ContagemInventario> getContagensFromJson(JsonObject paramsJO) {
		JsonElement contagensJE = paramsJO.get("contagens");

		ContagemInventario contagem;
		List<ContagemInventario> contagens = new LinkedList<>();

		if (contagensJE != null) {
			for (JsonElement contagemJE : contagensJE.getAsJsonArray()) {
				contagem = gson.fromJson(contagemJE, ContagemInventario.class);
				contagens.add(contagem);
			}
		}

		return contagens;
	}

	private void processarContagem(ContagemInventario contagem) throws SkalaModelException {

		try {
			contagem.persists();

			for (ProdutoContagem produto : contagem.getProdutos()) {
				produto.setCodEmp(contagem.getCodEmp());
				produto.setCodLocal(contagem.getCodLocal());
				produto.setCodParc(contagem.getCodParc());
				produto.setSequencia(contagem.getSequencia());
				produto.setIdIvt(contagem.getIdIvt());
				produto.setTipo(contagem.getTipo());

				produto.persists();

			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível persistir as informações de contagem.", e);
		}
	}

}
