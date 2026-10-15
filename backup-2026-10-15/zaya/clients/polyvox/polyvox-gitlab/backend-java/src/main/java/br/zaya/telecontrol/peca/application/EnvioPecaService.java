package br.zaya.telecontrol.peca.application;

public class EnvioPecaService {
    public static void postPeca() throws Exception {
        br.zaya.telecontrol.services.EnvioPeca.postPeca();
    }

    public static void postPeca(String where) throws Exception {
        br.zaya.telecontrol.services.EnvioPeca.postPeca(where);
    }
}
