package br.zaya.telecontrol.parsers;

import br.com.sankhya.jape.vo.DynamicVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ParseProduto {
	public static String toJson(DynamicVO produtoVO) {
		JsonObject json = new JsonObject();
		json.addProperty("referencia", produtoVO.asString("REFERENCIA"));
		json.addProperty("descricao", produtoVO.asString("DESCRICAO"));
		// Adicionar outros campos conforme necessidade da API Telecontrol
		return (new Gson()).toJson(json);
	}

	public static String toJsonPut(DynamicVO produtoVO) {
		return toJson(produtoVO);
	}
}
