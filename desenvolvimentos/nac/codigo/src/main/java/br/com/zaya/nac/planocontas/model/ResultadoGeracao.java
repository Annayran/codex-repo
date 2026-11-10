package br.com.zaya.nac.planocontas.model;

import java.math.BigDecimal;

public final class ResultadoGeracao {

    private final BigDecimal codigoParceiro;
    private final String nomeParceiro;
    private final BigDecimal codigoConta;
    private final String conta;
    private final String campoVinculado;

    public ResultadoGeracao(
            BigDecimal codigoParceiro,
            String nomeParceiro,
            BigDecimal codigoConta,
            String conta,
            String campoVinculado) {
        this.codigoParceiro = codigoParceiro;
        this.nomeParceiro = nomeParceiro;
        this.codigoConta = codigoConta;
        this.conta = conta;
        this.campoVinculado = campoVinculado;
    }

    public BigDecimal getCodigoParceiro() {
        return codigoParceiro;
    }

    public String getNomeParceiro() {
        return nomeParceiro;
    }

    public BigDecimal getCodigoConta() {
        return codigoConta;
    }

    public String getConta() {
        return conta;
    }

    public String getCampoVinculado() {
        return campoVinculado;
    }
}

