package br.zaya.telecontrol.listabasica.application;

import java.math.BigDecimal;

public class BuscaListaBasicaService {
    public static br.zaya.telecontrol.services.BuscaListaBasicaService.Resultado buscarPorProduto(BigDecimal codprod)
            throws Exception {
        return br.zaya.telecontrol.services.BuscaListaBasicaService.buscarPorProduto(codprod);
    }

    public static br.zaya.telecontrol.services.BuscaListaBasicaService.Resultado buscarPorReferencia(String referencia)
            throws Exception {
        return br.zaya.telecontrol.services.BuscaListaBasicaService.buscarPorReferencia(referencia);
    }

    public static void deletarNaApiPorProduto(BigDecimal codprod) throws Exception {
        br.zaya.telecontrol.services.BuscaListaBasicaService.deletarNaApiPorProduto(codprod);
    }

    public static void deletarNaApiPorReferencia(String referencia) throws Exception {
        br.zaya.telecontrol.services.BuscaListaBasicaService.deletarNaApiPorReferencia(referencia);
    }
}
