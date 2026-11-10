package br.com.zaya.belmicro.rateio.dominio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/** Only material, nonvolatile fields. Values must be normalized by the mapping adapter. */
public final class ContextoHash {
    private final String versaoAlgoritmo;
    private final String tipoOrigem;
    private final long idOrigem;
    private final int codEmp;
    private final long codNat;
    private final Long codParc;
    private final LocalDate competencia;
    private final String modalidade;
    private final String criterioVersao;
    private final Map<String, String> camposMateriais;
    private final ResultadoCalculo resultado;

    public ContextoHash(String versaoAlgoritmo, String tipoOrigem, long idOrigem, int codEmp,
                        long codNat, Long codParc, LocalDate competencia, String modalidade,
                        String criterioVersao, Map<String, String> camposMateriais, ResultadoCalculo resultado) {
        if (versaoAlgoritmo == null || versaoAlgoritmo.trim().isEmpty()
                || !("NOTA".equals(tipoOrigem) || "TITULO".equals(tipoOrigem))
                || idOrigem <= 0 || codEmp <= 0 || codNat <= 0 || competencia == null || resultado == null
                || !("MANUAL".equals(modalidade) || "CRITERIO".equals(modalidade))
                || (codParc != null && codParc <= 0)
                || ("CRITERIO".equals(modalidade) && (criterioVersao == null || criterioVersao.trim().isEmpty()))
                || ("MANUAL".equals(modalidade) && criterioVersao != null && !criterioVersao.isEmpty())
                || camposMateriais == null) {
            throw new ErroRateio("BASE_INCOMPLETA", "Contexto material do hash incompleto ou invalido.");
        }
        for (Map.Entry<String, String> campo : camposMateriais.entrySet()) {
            if (campo.getKey() == null || campo.getValue() == null) {
                throw new ErroRateio("BASE_INCOMPLETA", "Campo material ausente.");
            }
        }
        this.versaoAlgoritmo = versaoAlgoritmo;
        this.tipoOrigem = tipoOrigem;
        this.idOrigem = idOrigem;
        this.codEmp = codEmp;
        this.codNat = codNat;
        this.codParc = codParc;
        this.competencia = competencia;
        this.modalidade = modalidade;
        this.criterioVersao = criterioVersao == null ? "" : criterioVersao;
        this.camposMateriais = Collections.unmodifiableMap(new TreeMap<String, String>(camposMateriais));
        this.resultado = resultado;
    }

    public String getVersaoAlgoritmo() { return versaoAlgoritmo; }
    public String getTipoOrigem() { return tipoOrigem; }
    public long getIdOrigem() { return idOrigem; }
    public int getCodEmp() { return codEmp; }
    public long getCodNat() { return codNat; }
    public Long getCodParc() { return codParc; }
    public LocalDate getCompetencia() { return competencia; }
    public String getModalidade() { return modalidade; }
    public String getCriterioVersao() { return criterioVersao; }
    public Map<String, String> getCamposMateriais() { return camposMateriais; }
    public ResultadoCalculo getResultado() { return resultado; }
}
