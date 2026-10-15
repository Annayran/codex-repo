package br.zaya.telecontrol.parsers;

import br.zaya.telecontrol.models.Faturamento;
import br.zaya.telecontrol.models.FaturamentoGet;
import com.google.gson.Gson;

public class ParseFaturamento {
	public static String toJson(Faturamento fat) throws Exception {
		return (new Gson()).toJson(fat, Faturamento.class);
	}

	public static FaturamentoGet getFaturamento(String result) throws Exception {
		return (FaturamentoGet) (new Gson()).fromJson(result, FaturamentoGet.class);
	}
}
