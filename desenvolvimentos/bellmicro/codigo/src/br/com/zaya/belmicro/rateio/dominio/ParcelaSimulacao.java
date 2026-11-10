package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;

public final class ParcelaSimulacao {
    private final DestinoPeso destino;
    private final BigDecimal participacaoGlobal;
    private final BigDecimal valor;
    private final boolean origem;

    public ParcelaSimulacao(DestinoPeso destino, BigDecimal participacaoGlobal,
                            BigDecimal valor, boolean origem) {
        this.destino = destino;
        this.participacaoGlobal = participacaoGlobal;
        this.valor = valor;
        this.origem = origem;
    }

    public int getCodEmp() { return destino.getCodEmp(); }
    public int getCodCencus() { return destino.getCodCencus(); }
    public String getComponente() { return destino.getComponente(); }
    public BigDecimal getPeso() { return destino.getPeso(); }
    public String getEvidencia() { return destino.getEvidencia(); }
    public String getChaveCompatibilidade() { return destino.getChaveCompatibilidade(); }
    public BigDecimal getParticipacaoGlobal() { return participacaoGlobal; }
    public BigDecimal getValor() { return valor; }
    public boolean isOrigem() { return origem; }
}
