package br.zaya.telecontrol.osintegracao.application;

public class SincronizacaoResultado {
    private final int osProcessadas;
    private final int osComErro;
    private final String aviso;
    private final String erro;

    public SincronizacaoResultado(int osProcessadas, int osComErro, String aviso, String erro) {
        this.osProcessadas = osProcessadas;
        this.osComErro = osComErro;
        this.aviso = aviso;
        this.erro = erro;
    }

    public int getOsProcessadas() {
        return osProcessadas;
    }

    public int getOsComErro() {
        return osComErro;
    }

    public String getAviso() {
        return aviso;
    }

    public String getErro() {
        return erro;
    }
}

