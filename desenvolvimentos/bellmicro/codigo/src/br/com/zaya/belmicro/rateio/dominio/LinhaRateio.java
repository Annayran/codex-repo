package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;

/** Consolidated preview, not a persisted native rateio. */
public final class LinhaRateio {
    private final int codCencus;
    private final BigDecimal valor;
    private final BigDecimal percentual;

    public LinhaRateio(int codCencus, BigDecimal valor, BigDecimal percentual) {
        this.codCencus = codCencus;
        this.valor = valor;
        this.percentual = percentual;
    }

    public int getCodCencus() { return codCencus; }
    public BigDecimal getValor() { return valor; }
    public BigDecimal getPercentual() { return percentual; }
}
