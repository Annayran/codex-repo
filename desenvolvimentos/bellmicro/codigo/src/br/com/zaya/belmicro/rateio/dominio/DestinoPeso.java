package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;

/** Input weight and approved header compatibility. No client codes are embedded. */
public final class DestinoPeso {
    private final int codEmp;
    private final int codCencus;
    private final String componente;
    private final BigDecimal peso;
    private final String evidencia;
    private final String chaveCompatibilidade;

    public DestinoPeso(int codEmp, int codCencus, String componente, BigDecimal peso, String evidencia) {
        this(codEmp, codCencus, componente, peso, evidencia, "");
    }

    public DestinoPeso(int codEmp, int codCencus, String componente, BigDecimal peso,
                       String evidencia, String chaveCompatibilidade) {
        if (codEmp <= 0 || codCencus <= 0) {
            throw new ErroRateio("CADASTRO_INVALIDO", "Empresa e CR devem ser positivos.");
        }
        if (componente == null || componente.trim().isEmpty() || chaveCompatibilidade == null) {
            throw new ErroRateio("BASE_INCOMPLETA", "Componente e compatibilidade devem ser definidos.");
        }
        if (peso == null || peso.signum() < 0) {
            throw new ErroRateio("BASE_INCOMPLETA", "Peso ausente ou negativo.");
        }
        this.codEmp = codEmp;
        this.codCencus = codCencus;
        this.componente = componente;
        this.peso = peso;
        this.evidencia = evidencia == null ? "" : evidencia;
        this.chaveCompatibilidade = chaveCompatibilidade;
    }

    public int getCodEmp() { return codEmp; }
    public int getCodCencus() { return codCencus; }
    public String getComponente() { return componente; }
    public BigDecimal getPeso() { return peso; }
    public String getEvidencia() { return evidencia; }
    public String getChaveCompatibilidade() { return chaveCompatibilidade; }
}
