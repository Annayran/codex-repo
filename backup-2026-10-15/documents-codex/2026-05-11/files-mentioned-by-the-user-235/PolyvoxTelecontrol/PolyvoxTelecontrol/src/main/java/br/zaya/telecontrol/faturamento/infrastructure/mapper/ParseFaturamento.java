package br.zaya.telecontrol.faturamento.infrastructure.mapper;

import br.zaya.telecontrol.models.Faturamento;
import br.zaya.telecontrol.models.FaturamentoGet;

/** Bridge de compatibilidade para o parser legado de faturamento. */
public class ParseFaturamento {
    public static String toJson(Faturamento fat) throws Exception {
        return br.zaya.telecontrol.parsers.ParseFaturamento.toJson(fat);
    }

    public static FaturamentoGet getFaturamento(String result) throws Exception {
        return br.zaya.telecontrol.parsers.ParseFaturamento.getFaturamento(result);
    }
}

