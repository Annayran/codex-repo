package br.com.zaya.like.Actions.dto;

import java.math.BigDecimal;

public class ItemSuprimentoDTO {

    private BigDecimal nuNota;
    private BigDecimal sequencia;
    private BigDecimal codProduto;
    private String referencia;
    private String descricaoProduto;
    private BigDecimal qtdNeg;
    private BigDecimal qtdEntregue;
    private String pendente;
    private String itemCancelado;
    private String ocorrenciaCancelamento;

    public BigDecimal getNuNota() {
        return nuNota;
    }

    public void setNuNota(BigDecimal nuNota) {
        this.nuNota = nuNota;
    }

    public BigDecimal getSequencia() {
        return sequencia;
    }

    public void setSequencia(BigDecimal sequencia) {
        this.sequencia = sequencia;
    }

    public BigDecimal getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(BigDecimal codProduto) {
        this.codProduto = codProduto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public BigDecimal getQtdNeg() {
        return qtdNeg;
    }

    public void setQtdNeg(BigDecimal qtdNeg) {
        this.qtdNeg = qtdNeg;
    }

    public BigDecimal getQtdEntregue() {
        return qtdEntregue;
    }

    public void setQtdEntregue(BigDecimal qtdEntregue) {
        this.qtdEntregue = qtdEntregue;
    }

    public String getPendente() {
        return pendente;
    }

    public void setPendente(String pendente) {
        this.pendente = pendente;
    }

    public String getItemCancelado() {
        return itemCancelado;
    }

    public void setItemCancelado(String itemCancelado) {
        this.itemCancelado = itemCancelado;
    }

    public String getOcorrenciaCancelamento() {
        return ocorrenciaCancelamento;
    }

    public void setOcorrenciaCancelamento(String ocorrenciaCancelamento) {
        this.ocorrenciaCancelamento = ocorrenciaCancelamento;
    }

    public boolean isTotalmenteEntregue() {
        return valor(qtdEntregue).compareTo(valor(qtdNeg)) == 0;
    }

    public boolean isPendente() {
        return "S".equalsIgnoreCase(pendente);
    }

    public boolean isCancelado() {
        return "S".equalsIgnoreCase(itemCancelado);
    }

    private BigDecimal valor(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
