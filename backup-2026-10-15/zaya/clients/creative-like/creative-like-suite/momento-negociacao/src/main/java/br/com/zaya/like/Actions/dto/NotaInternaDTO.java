package br.com.zaya.like.Actions.dto;

import java.math.BigDecimal;

public class NotaInternaDTO {

    private BigDecimal nuNegociacao;
    private BigDecimal nuNota;
    private BigDecimal numeroNota;
    private BigDecimal codTipOper;
    private String tipMov;

    public BigDecimal getNuNegociacao() {
        return nuNegociacao;
    }

    public void setNuNegociacao(BigDecimal nuNegociacao) {
        this.nuNegociacao = nuNegociacao;
    }

    public BigDecimal getNuNota() {
        return nuNota;
    }

    public void setNuNota(BigDecimal nuNota) {
        this.nuNota = nuNota;
    }

    public BigDecimal getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(BigDecimal numeroNota) {
        this.numeroNota = numeroNota;
    }

    public BigDecimal getCodTipOper() {
        return codTipOper;
    }

    public void setCodTipOper(BigDecimal codTipOper) {
        this.codTipOper = codTipOper;
    }

    public String getTipMov() {
        return tipMov;
    }

    public void setTipMov(String tipMov) {
        this.tipMov = tipMov;
    }
}
