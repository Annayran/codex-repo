package br.zaya.telecontrol.parsers;

import br.com.sankhya.jape.vo.DynamicVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ParseListaBasica {
    public static String toJson(DynamicVO listaVO) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("referenciaProduto", listaVO.asString("REFPRODUTO"));
        json.addProperty("referenciaPeca", listaVO.asString("REFPECA"));
        json.addProperty("quantidade", listaVO.asInt("QTD"));
        json.addProperty("posicao", listaVO.asString("POSICAO"));
        return new Gson().toJson(json);
    }

    public static String toJsonPut(DynamicVO listaVO) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("quantidade", listaVO.asInt("QTD"));
        json.addProperty("posicao", listaVO.asString("POSICAO"));
        return new Gson().toJson(json);
    }
}
