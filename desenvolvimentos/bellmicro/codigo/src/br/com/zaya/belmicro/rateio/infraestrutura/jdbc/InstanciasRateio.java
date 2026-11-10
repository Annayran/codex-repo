package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/** Instance names confirmed by the customer's dictionary screenshot on 27/10/2026. */
public final class InstanciasRateio {
    private InstanciasRateio() { }
    public static Map<TabelaRateio,String> confirmadas() {
        Map<TabelaRateio,String> nomes=new EnumMap<TabelaRateio,String>(TabelaRateio.class);
        nomes.put(TabelaRateio.CRITERIO,"AD_RATCRI");
        nomes.put(TabelaRateio.VERSAO,"AD_RATVER");
        nomes.put(TabelaRateio.DESTINO,"AD_RATDES");
        nomes.put(TabelaRateio.SIMULACAO,"AD_RATSIM");
        nomes.put(TabelaRateio.PARCELA,"AD_RATPAR");
        nomes.put(TabelaRateio.GRUPO,"AD_RATGRP");
        nomes.put(TabelaRateio.EXECUCAO,"AD_RATEXE");
        nomes.put(TabelaRateio.EVENTO,"AD_RATEVT");
        nomes.put(TabelaRateio.INTERNO,"AD_RATINT");
        nomes.put(TabelaRateio.VINCULO,"AD_RATVIN");
        nomes.put(TabelaRateio.FINANCEIRO,"AD_RATFIN");
        return Collections.unmodifiableMap(nomes);
    }
}
