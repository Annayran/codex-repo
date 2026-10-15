package br.zaya.pedidocompra.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de Pedido de Compra
 * Armazena os dados extraídos do PDF do pedido
 */
public class PedidoCompra {

    private String numeroPedido;
    private String data;
    private String fornecedor;
    private String cnpj;
    private BigDecimal valorTotal;
    private String descricaoServico;
    private List<ItemPedido> itens;

    public PedidoCompra() {
        this.itens = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
    }

    // Getters e Setters
    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemPedido item) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        this.itens.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoCompra that = (PedidoCompra) o;
        return Objects.equals(numeroPedido, that.numeroPedido) &&
               Objects.equals(data, that.data) &&
               Objects.equals(fornecedor, that.fornecedor) &&
               Objects.equals(cnpj, that.cnpj) &&
               Objects.equals(valorTotal, that.valorTotal) &&
               Objects.equals(descricaoServico, that.descricaoServico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedido, data, fornecedor, cnpj, valorTotal, descricaoServico);
    }

    @Override
    public String toString() {
        return "PedidoCompra{" +
                "numeroPedido='" + numeroPedido + '\'' +
                ", data=" + data +
                ", fornecedor='" + fornecedor + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", valorTotal=" + valorTotal +
                ", descricaoServico='" + descricaoServico + '\'' +
                ", itens=" + (itens != null ? itens.size() : 0) +
                '}';
    }
}

