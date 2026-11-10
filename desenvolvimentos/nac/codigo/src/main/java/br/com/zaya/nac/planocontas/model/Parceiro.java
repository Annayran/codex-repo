package br.com.zaya.nac.planocontas.model;

import java.math.BigDecimal;

public final class Parceiro {

    private final BigDecimal codigo;
    private final String nome;
    private final boolean cliente;
    private final boolean fornecedor;
    private final BigDecimal contaCliente;
    private final BigDecimal contaFornecedor;
    private final boolean contaClienteGerada;
    private final boolean contaFornecedorGerada;

    public Parceiro(
            BigDecimal codigo,
            String nome,
            boolean cliente,
            boolean fornecedor,
            BigDecimal contaCliente,
            BigDecimal contaFornecedor,
            boolean contaClienteGerada,
            boolean contaFornecedorGerada) {
        this.codigo = codigo;
        this.nome = nome;
        this.cliente = cliente;
        this.fornecedor = fornecedor;
        this.contaCliente = contaCliente;
        this.contaFornecedor = contaFornecedor;
        this.contaClienteGerada = contaClienteGerada;
        this.contaFornecedorGerada = contaFornecedorGerada;
    }

    public BigDecimal getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public boolean isCliente() {
        return cliente;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public BigDecimal getContaCliente() {
        return contaCliente;
    }

    public BigDecimal getContaFornecedor() {
        return contaFornecedor;
    }

    public boolean isContaClienteGerada() {
        return contaClienteGerada;
    }

    public boolean isContaFornecedorGerada() {
        return contaFornecedorGerada;
    }
}
