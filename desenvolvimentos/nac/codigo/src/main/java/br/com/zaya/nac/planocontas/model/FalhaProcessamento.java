package br.com.zaya.nac.planocontas.model;

import java.math.BigDecimal;

public final class FalhaProcessamento {

    private final BigDecimal codigoParceiro;
    private final String motivo;

    public FalhaProcessamento(BigDecimal codigoParceiro, String motivo) {
        this.codigoParceiro = codigoParceiro;
        this.motivo = motivo;
    }

    public BigDecimal getCodigoParceiro() {
        return codigoParceiro;
    }

    public String getMotivo() {
        return motivo;
    }
}
