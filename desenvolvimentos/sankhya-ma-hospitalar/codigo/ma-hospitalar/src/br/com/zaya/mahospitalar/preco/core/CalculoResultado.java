package br.com.zaya.mahospitalar.preco.core;

import java.math.BigDecimal;

public final class CalculoResultado {
    private final BigDecimal receitaBruta;
    private final BigDecimal receitaLiquida;
    private final BigDecimal deducoesOperacionais;
    private final BigDecimal resultado;
    private final BigDecimal margemContribuicao;
    private final BigDecimal precoUnitario;

    public CalculoResultado(BigDecimal receitaBruta,
                            BigDecimal receitaLiquida,
                            BigDecimal deducoesOperacionais,
                            BigDecimal resultado,
                            BigDecimal margemContribuicao,
                            BigDecimal precoUnitario) {
        this.receitaBruta = receitaBruta;
        this.receitaLiquida = receitaLiquida;
        this.deducoesOperacionais = deducoesOperacionais;
        this.resultado = resultado;
        this.margemContribuicao = margemContribuicao;
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getReceitaBruta() {
        return receitaBruta;
    }

    public BigDecimal getReceitaLiquida() {
        return receitaLiquida;
    }

    public BigDecimal getDeducoesOperacionais() {
        return deducoesOperacionais;
    }

    public BigDecimal getResultado() {
        return resultado;
    }

    public BigDecimal getMargemContribuicao() {
        return margemContribuicao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
}
