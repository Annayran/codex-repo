package br.zaya.telecontrol.parsers;

import br.zaya.telecontrol.models.OrdemServico;
import br.zaya.telecontrol.models.OrdemServicoList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParseOrdemServico {
    public static OrdemServicoList getOrdemServicoResultado(String result) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result, OrdemServicoList.class);
    }

    public static String toPrettyJson(OrdemServico result) {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        return gson.toJson(result, OrdemServico.class);
    }
}
