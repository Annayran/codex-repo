package br.zaya.telecontrol.posto.application;

public class EnvioPostoService {
    public static void postPosto() throws Exception {
        br.zaya.telecontrol.services.EnvioPosto.postPosto();
    }

    public static void postPosto(String where) throws Exception {
        br.zaya.telecontrol.services.EnvioPosto.postPosto(where);
    }
}
