package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GrupoPrevisto {
    private final int codEmp;
    private final String chaveCompatibilidade;
    private final BigDecimal valorTotal;
    private final List<LinhaRateio> linhasPorCr;

    public GrupoPrevisto(int codEmp, String chaveCompatibilidade, BigDecimal valorTotal,
                          List<LinhaRateio> linhasPorCr) {
        this.codEmp = codEmp;
        this.chaveCompatibilidade = chaveCompatibilidade;
        this.valorTotal = valorTotal;
        this.linhasPorCr = Collections.unmodifiableList(new ArrayList<LinhaRateio>(linhasPorCr));
    }

    public int getCodEmp() { return codEmp; }
    public String getChaveCompatibilidade() { return chaveCompatibilidade; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public List<LinhaRateio> getLinhasPorCr() { return linhasPorCr; }
    public List<BigDecimal> getPercentuaisInternos() {
        List<BigDecimal> valores = new ArrayList<BigDecimal>();
        for (LinhaRateio linha : linhasPorCr) valores.add(linha.getPercentual());
        return Collections.unmodifiableList(valores);
    }
}
