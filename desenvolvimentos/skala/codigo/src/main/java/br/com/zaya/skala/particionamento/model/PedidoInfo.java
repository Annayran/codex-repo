package br.com.zaya.skala.particionamento.model;

public class PedidoInfo {
    private final int nuNota;
    private final int codTipOper;
    private final int codParc;
    private final String adPermitePart;
    private final String adParticionado;
    private final String statusNota;

    public PedidoInfo(int nuNota, int codTipOper, int codParc, String adPermitePart, String adParticionado, String statusNota) {
        this.nuNota = nuNota;
        this.codTipOper = codTipOper;
        this.codParc = codParc;
        this.adPermitePart = adPermitePart;
        this.adParticionado = adParticionado;
        this.statusNota = statusNota;
    }

    public int getNuNota() {
        return nuNota;
    }

    public int getCodTipOper() {
        return codTipOper;
    }

    public int getCodParc() {
        return codParc;
    }

    public String getAdPermitePart() {
        return adPermitePart;
    }

    public String getAdParticionado() {
        return adParticionado;
    }

    public String getStatusNota() {
        return statusNota;
    }
}
