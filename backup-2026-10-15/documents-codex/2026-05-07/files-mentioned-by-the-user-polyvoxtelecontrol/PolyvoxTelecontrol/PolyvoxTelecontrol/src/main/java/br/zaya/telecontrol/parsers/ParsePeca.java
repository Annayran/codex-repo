package br.zaya.telecontrol.parsers;

import br.com.sankhya.jape.vo.DynamicVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ParsePeca {
    public static String toJson(DynamicVO pecaVO) throws Exception {
        JsonObject json = dadosComuns(pecaVO);
        json.addProperty("referencia", pecaVO.asString("REFERENCIA"));
        json.addProperty("descricao", pecaVO.asString("DESCRICAO"));
        return new Gson().toJson(json);
    }

    public static String toJsonPut(DynamicVO pecaVO) throws Exception {
        return new Gson().toJson(dadosComuns(pecaVO));
    }

    private static JsonObject dadosComuns(DynamicVO pecaVO) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("ipi", String.valueOf(pecaVO.asInt("IPI")));
        json.addProperty("multiplo", pecaVO.asInt("MULTIPLO"));
        json.addProperty("devolucaoObrigatoria", pecaVO.asBoolean("DEVOLUCAOOBRIGATORIA"));
        json.addProperty("garantiaDiferenciada", pecaVO.asInt("GARANTIADIFERENCIADA"));
        json.addProperty("numeroSeriePeca", pecaVO.asBoolean("NUMEROSERIEPECA"));
        json.addProperty("acessorio", pecaVO.asBoolean("ACESSORIO"));
        json.addProperty("itemAparencia", pecaVO.asBoolean("ITEMAPARENCIA"));
        json.addProperty("origem", pecaVO.asString("ORIGEM"));
        json.addProperty("ativo", pecaVO.asBoolean("ATIVO"));
        json.addProperty("bloqueadaVenda", pecaVO.asBoolean("BLOQUEADAVENDA"));
        json.addProperty("bloqueadaGarantia", pecaVO.asBoolean("BLOQUEADAGARANTIA"));
        json.addProperty("estoque", pecaVO.asDouble("ESTOQUE"));
        json.addProperty("unidade", pecaVO.asString("UNIDADE"));
        json.addProperty("classificacaoFiscal", pecaVO.asString("CLASSIFICACAOFISCAL"));
        return json;
    }
}
