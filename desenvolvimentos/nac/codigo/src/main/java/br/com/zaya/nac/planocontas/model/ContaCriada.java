package br.com.zaya.nac.planocontas.model;

import java.math.BigDecimal;

public final class ContaCriada {

    private final BigDecimal codigo;
    private final String conta;

    public ContaCriada(BigDecimal codigo, String conta) {
        this.codigo = codigo;
        this.conta = conta;
    }

    public BigDecimal getCodigo() {
        return codigo;
    }

    public String getConta() {
        return conta;
    }
}

