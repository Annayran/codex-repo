package br.com.zaya.like.Actions.dto;

import java.math.BigDecimal;

public class ItemMomentoNegociacaoDTO {

    private BigDecimal nuNegociacao;
    private BigDecimal sequencia;
    private BigDecimal idArte;
    private String referencia;
    private BigDecimal codProduto;
    private String descricaoProduto;
    private String tipoProduto;
    private String statusArte;
    private String observacao;
    private String observacaoArte;
    private String tituloArte;
    private String statusSuprimento;
    private String statusProducao;
    private BigDecimal qtdNegociada;
    private BigDecimal qtdLiberadaCQ;
    private BigDecimal qtdLiberadaEstoque;
    private BigDecimal qtdDisponivelEstoque;
    private BigDecimal qtdPendenteCompra;
    private BigDecimal qtdDisponivelFaturamento;
    private BigDecimal qtdFaturada;

    public BigDecimal getNuNegociacao() {
        return nuNegociacao;
    }

    public void setNuNegociacao(BigDecimal nuNegociacao) {
        this.nuNegociacao = nuNegociacao;
    }

    public BigDecimal getSequencia() {
        return sequencia;
    }

    public void setSequencia(BigDecimal sequencia) {
        this.sequencia = sequencia;
    }

    public BigDecimal getIdArte() {
        return idArte;
    }

    public void setIdArte(BigDecimal idArte) {
        this.idArte = idArte;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(BigDecimal codProduto) {
        this.codProduto = codProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getStatusArte() {
        return statusArte;
    }

    public void setStatusArte(String statusArte) {
        this.statusArte = statusArte;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getObservacaoArte() {
        return observacaoArte;
    }

    public void setObservacaoArte(String observacaoArte) {
        this.observacaoArte = observacaoArte;
    }

    public String getTituloArte() {
        return tituloArte;
    }

    public void setTituloArte(String tituloArte) {
        this.tituloArte = tituloArte;
    }

    public String getStatusSuprimento() {
        return statusSuprimento;
    }

    public void setStatusSuprimento(String statusSuprimento) {
        this.statusSuprimento = statusSuprimento;
    }

    public String getStatusProducao() {
        return statusProducao;
    }

    public void setStatusProducao(String statusProducao) {
        this.statusProducao = statusProducao;
    }

    public BigDecimal getQtdNegociada() {
        return qtdNegociada;
    }

    public void setQtdNegociada(BigDecimal qtdNegociada) {
        this.qtdNegociada = qtdNegociada;
    }

    public BigDecimal getQtdLiberadaCQ() {
        return qtdLiberadaCQ;
    }

    public void setQtdLiberadaCQ(BigDecimal qtdLiberadaCQ) {
        this.qtdLiberadaCQ = qtdLiberadaCQ;
    }

    public BigDecimal getQtdLiberadaEstoque() {
        return qtdLiberadaEstoque;
    }

    public void setQtdLiberadaEstoque(BigDecimal qtdLiberadaEstoque) {
        this.qtdLiberadaEstoque = qtdLiberadaEstoque;
    }

    public BigDecimal getQtdDisponivelEstoque() {
        return qtdDisponivelEstoque;
    }

    public void setQtdDisponivelEstoque(BigDecimal qtdDisponivelEstoque) {
        this.qtdDisponivelEstoque = qtdDisponivelEstoque;
    }

    public BigDecimal getQtdPendenteCompra() {
        return qtdPendenteCompra;
    }

    public void setQtdPendenteCompra(BigDecimal qtdPendenteCompra) {
        this.qtdPendenteCompra = qtdPendenteCompra;
    }

    public BigDecimal getQtdDisponivelFaturamento() {
        return qtdDisponivelFaturamento;
    }

    public void setQtdDisponivelFaturamento(BigDecimal qtdDisponivelFaturamento) {
        this.qtdDisponivelFaturamento = qtdDisponivelFaturamento;
    }

    public BigDecimal getQtdFaturada() {
        return qtdFaturada;
    }

    public void setQtdFaturada(BigDecimal qtdFaturada) {
        this.qtdFaturada = qtdFaturada;
    }
}
