package br.zaya.telecontrol.listabasica.application;

public class SincronizaListaBasicaService {

    public static br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado sincronizarMateriasPrimas() throws Exception {
        return br.zaya.telecontrol.services.SincronizaListaBasicaService.sincronizarMateriasPrimas();
    }

    public static br.zaya.telecontrol.services.SincronizaListaBasicaService.Resultado sincronizarMateriasPrimas(String where) throws Exception {
        return br.zaya.telecontrol.services.SincronizaListaBasicaService.sincronizarMateriasPrimas(where);
    }
}
