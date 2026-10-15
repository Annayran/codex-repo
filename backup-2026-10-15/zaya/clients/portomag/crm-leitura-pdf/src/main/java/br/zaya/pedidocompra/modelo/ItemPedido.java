package br.zaya.pedidocompra.modelo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Modelo de Item do Pedido de Compra
 * Representa cada linha de produto/serviço do pedido
 */
public class ItemPedido {

    private String codigo;
    private String descricao;
    private Integer quantidade;
    private BigDecimal valor;
    private String unidade;

    public ItemPedido() {
        this.quantidade = 0;
        this.valor = BigDecimal.ZERO;
    }

    public ItemPedido(String codigo, String descricao, Integer quantidade, BigDecimal valor) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * Calcula o valor total do item (quantidade * valor unitário)
     */
    public BigDecimal getValorTotalItem() {
        if (quantidade == null || valor == null) {
            return BigDecimal.ZERO;
        }
        return valor.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(codigo, that.codigo) &&
               Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descricao);
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", unidade='" + unidade + '\'' +
                ", valor=" + valor +
                '}';
    }
}

