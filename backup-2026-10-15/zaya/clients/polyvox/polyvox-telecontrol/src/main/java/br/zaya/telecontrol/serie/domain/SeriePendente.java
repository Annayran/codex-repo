package br.zaya.telecontrol.serie.domain;

import java.math.BigDecimal;
import java.sql.Date;

public class SeriePendente {
    private final BigDecimal codprod;
    private final String serie;
    private final String referenciaOriginal;
    private final String referenciaEnvio;
    private final Date dataVenda;
    private final Date dataFabricacao;
    private final String revenda;

    public SeriePendente(BigDecimal codprod, String serie, String referenciaOriginal, String referenciaEnvio,
                         Date dataVenda, Date dataFabricacao, String revenda) {
        this.codprod = codprod;
        this.serie = serie;
        this.referenciaOriginal = referenciaOriginal;
        this.referenciaEnvio = referenciaEnvio;
        this.dataVenda = dataVenda;
        this.dataFabricacao = dataFabricacao;
        this.revenda = revenda;
    }

    public BigDecimal getCodprod() {
        return codprod;
    }

    public String getSerie() {
        return serie;
    }

    public String getReferenciaOriginal() {
        return referenciaOriginal;
    }

    public String getReferenciaEnvio() {
        return referenciaEnvio;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public String getRevenda() {
        return revenda;
    }
}
