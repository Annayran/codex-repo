package br.zaya.telecontrol.serie.infrastructure.mapper;

import br.zaya.telecontrol.models.Serie;
import br.zaya.telecontrol.serie.domain.SeriePendente;
import com.google.gson.Gson;

public class ParseSerie {
    public static String toJson(SeriePendente pendente) throws Exception {
        Serie serie = new Serie();
        serie.setSerie(pendente.getSerie());
        serie.setReferenciaProduto(pendente.getReferenciaEnvio());
        serie.setDataFabricacao(toString(pendente.getDataFabricacao()));
        serie.setDataVenda(toString(pendente.getDataVenda()));
        serie.setRevenda(pendente.getRevenda());
        return new Gson().toJson(serie, Serie.class);
    }

    private static String toString(java.sql.Date date) {
        return date == null ? null : date.toString();
    }
}
