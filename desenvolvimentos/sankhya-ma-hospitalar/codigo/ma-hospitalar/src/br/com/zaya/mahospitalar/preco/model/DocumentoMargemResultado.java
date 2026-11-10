package br.com.zaya.mahospitalar.preco.model;

import java.math.BigDecimal;

public final class DocumentoMargemResultado {
    private final BigDecimal margem;
    private final BigDecimal margemMinima;
    private final BigDecimal receitaLiquida;
    private final BigDecimal resultado;
    private final String calculoHash;
    private final int eventoLiberacao;

    public DocumentoMargemResultado(BigDecimal margem, BigDecimal margemMinima,
                                    BigDecimal receitaLiquida, BigDecimal resultado,
                                    String calculoHash, int eventoLiberacao) {
        this.margem = margem;
        this.margemMinima = margemMinima;
        this.receitaLiquida = receitaLiquida;
        this.resultado = resultado;
        this.calculoHash = calculoHash;
        this.eventoLiberacao = eventoLiberacao;
    }

    public BigDecimal getMargem() {
        return margem;
    }

    public BigDecimal getMargemMinima() {
        return margemMinima;
    }

    public BigDecimal getReceitaLiquida() {
        return receitaLiquida;
    }

    public BigDecimal getResultado() {
        return resultado;
    }

    public String getCalculoHash() {
        return calculoHash;
    }

    public int getEventoLiberacao() {
        return eventoLiberacao;
    }

    public boolean exigeLiberacao() {
        return margem.compareTo(margemMinima) < 0;
    }
}
