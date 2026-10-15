package br.zaya.telecontrol.listabasica.application;

public class EnvioListaBasicaService {
    public static void postListaBasica() throws Exception {
        br.zaya.telecontrol.services.EnvioListaBasica.postListaBasica();
    }

    public static void postListaBasica(String where) throws Exception {
        br.zaya.telecontrol.services.EnvioListaBasica.postListaBasica(where);
    }

    public static void deleteListaBasica(String where) throws Exception {
        br.zaya.telecontrol.services.EnvioListaBasica.deleteListaBasica(where);
    }
}
