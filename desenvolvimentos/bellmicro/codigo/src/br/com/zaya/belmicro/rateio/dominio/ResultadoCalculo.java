package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResultadoCalculo {
    private final BigDecimal base;
    private final List<ParcelaSimulacao> parcelas;
    private final List<GrupoPrevisto> grupos;

    public ResultadoCalculo(BigDecimal base, List<ParcelaSimulacao> parcelas, List<GrupoPrevisto> grupos) {
        this.base = base;
        this.parcelas = Collections.unmodifiableList(new ArrayList<ParcelaSimulacao>(parcelas));
        this.grupos = Collections.unmodifiableList(new ArrayList<GrupoPrevisto>(grupos));
    }

    public BigDecimal getBase() { return base; }
    public List<ParcelaSimulacao> getParcelas() { return parcelas; }
    public List<GrupoPrevisto> getGrupos() { return grupos; }
}
