package br.com.zaya.mahospitalar.preco.core;

import java.math.BigDecimal;

public final class CalculoEntrada {
    private final BigDecimal coeficienteReceita;
    private final BigDecimal creditoFixo;
    private final BigDecimal coeficienteOperacional;
    private final BigDecimal despesaFixa;
    private final BigDecimal custoGerencial;
    private final BigDecimal quantidade;
    private final BigDecimal cotacaoMoeda;

    public CalculoEntrada(BigDecimal coeficienteReceita,
                          BigDecimal creditoFixo,
                          BigDecimal coeficienteOperacional,
                          BigDecimal despesaFixa,
                          BigDecimal custoGerencial,
                          BigDecimal quantidade,
                          BigDecimal cotacaoMoeda) {
        this.coeficienteReceita = coeficienteReceita;
        this.creditoFixo = creditoFixo;
        this.coeficienteOperacional = coeficienteOperacional;
        this.despesaFixa = despesaFixa;
        this.custoGerencial = custoGerencial;
        this.quantidade = quantidade;
        this.cotacaoMoeda = cotacaoMoeda;
    }

    public BigDecimal getCoeficienteReceita() {
        return coeficienteReceita;
    }

    public BigDecimal getCreditoFixo() {
        return creditoFixo;
    }

    public BigDecimal getCoeficienteOperacional() {
        return coeficienteOperacional;
    }

    public BigDecimal getDespesaFixa() {
        return despesaFixa;
    }

    public BigDecimal getCustoGerencial() {
        return custoGerencial;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getCotacaoMoeda() {
        return cotacaoMoeda;
    }
}
