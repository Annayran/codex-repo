package br.com.zaya.like.Actions.dto;

import java.math.BigDecimal;

public class MomentoNegociacaoDTO {

    private BigDecimal nuNegociacao;
    private BigDecimal numeroNegociacao;
    private String cliente;
    private String vendedor;
    private String statusNegociacao;
    private String statusSuprimento;
    private String statusLayout;
    private String statusProducaoFinal;
    private String statusFaturamento;
    private String statusEntrega;

    public BigDecimal getNuNegociacao() {
        return nuNegociacao;
    }

    public void setNuNegociacao(BigDecimal nuNegociacao) {
        this.nuNegociacao = nuNegociacao;
    }

    public BigDecimal getNumeroNegociacao() {
        return numeroNegociacao;
    }

    public void setNumeroNegociacao(BigDecimal numeroNegociacao) {
        this.numeroNegociacao = numeroNegociacao;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getStatusNegociacao() {
        return statusNegociacao;
    }

    public void setStatusNegociacao(String statusNegociacao) {
        this.statusNegociacao = statusNegociacao;
    }

    public String getStatusSuprimento() {
        return statusSuprimento;
    }

    public void setStatusSuprimento(String statusSuprimento) {
        this.statusSuprimento = statusSuprimento;
    }

    public String getStatusLayout() {
        return statusLayout;
    }

    public void setStatusLayout(String statusLayout) {
        this.statusLayout = statusLayout;
    }

    public String getStatusProducaoFinal() {
        return statusProducaoFinal;
    }

    public void setStatusProducaoFinal(String statusProducaoFinal) {
        this.statusProducaoFinal = statusProducaoFinal;
    }

    public String getStatusFaturamento() {
        return statusFaturamento;
    }

    public void setStatusFaturamento(String statusFaturamento) {
        this.statusFaturamento = statusFaturamento;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }
}
