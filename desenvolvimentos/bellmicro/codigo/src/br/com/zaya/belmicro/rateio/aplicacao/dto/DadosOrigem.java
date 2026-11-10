package br.com.zaya.belmicro.rateio.aplicacao.dto;

import br.com.zaya.belmicro.rateio.dominio.ContextoHash;
import br.com.zaya.belmicro.rateio.dominio.ResultadoCalculo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Objects;

/** Authoritative mapped data. Never constructed from browser fields. */
public final class DadosOrigem {
    private final OrigemCanonica origem;
    private final int codEmp;
    private final long codNat;
    private final Long codParc;
    private final Long codTipOper;
    private final BigDecimal base;
    private final LocalDate competencia;
    private final String versaoAlgoritmoMapeamento;
    private final Map<String, String> camposMateriais;
    private final boolean auxiliar;

    public DadosOrigem(OrigemCanonica origem, int codEmp, long codNat, Long codParc,
                       BigDecimal base, LocalDate competencia, String versaoAlgoritmoMapeamento,
                       Map<String, String> camposMateriais, boolean auxiliar) {
        this(origem,codEmp,codNat,codParc,null,base,competencia,versaoAlgoritmoMapeamento,camposMateriais,auxiliar);
    }

    public DadosOrigem(OrigemCanonica origem, int codEmp, long codNat, Long codParc, Long codTipOper,
                       BigDecimal base, LocalDate competencia, String versaoAlgoritmoMapeamento,
                       Map<String, String> camposMateriais, boolean auxiliar) {
        this.origem = Objects.requireNonNull(origem, "origem");
        this.codEmp = codEmp;
        this.codNat = codNat;
        this.codParc = codParc;
        this.codTipOper = codTipOper;
        this.base = base;
        this.competencia = competencia;
        this.versaoAlgoritmoMapeamento = versaoAlgoritmoMapeamento;
        this.camposMateriais = Collections.unmodifiableMap(new TreeMap<String, String>(
                Objects.requireNonNull(camposMateriais, "camposMateriais")));
        this.auxiliar = auxiliar;
    }
    public OrigemCanonica getOrigem() { return origem; }
    public int getCodEmp() { return codEmp; }
    public long getCodNat() { return codNat; }
    public Long getCodParc() { return codParc; }
    public Long getCodTipOper() { return codTipOper; }
    public BigDecimal getBase() { return base; }
    public LocalDate getCompetencia() { return competencia; }
    public String getVersaoAlgoritmoMapeamento() { return versaoAlgoritmoMapeamento; }
    public Map<String,String> getCamposMateriais() { return camposMateriais; }
    public boolean isAuxiliar() { return auxiliar; }

    public ContextoHash contextoHash(EntradaSimulacao entrada, ResultadoCalculo resultado) {
        // Criterion ID and version are encoded separately with a length prefix.
        String criterio = entrada.getCriterioId() == null ? ""
                : entrada.getCriterioId().length() + ":" + entrada.getCriterioId() + ":" + entrada.getCriterioVersao();
        Map<String,String> materiais = new TreeMap<String,String>(camposMateriais);
        materiais.put("origem.codTipOper",codTipOper == null ? "" : codTipOper.toString());
        return new ContextoHash(versaoAlgoritmoMapeamento, origem.getTipo().name(), origem.getId(),
                codEmp, codNat, codParc, competencia, entrada.getModalidade().name(), criterio,
                materiais, resultado);
    }
}
