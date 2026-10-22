package br.com.zaya.mahospitalar.genesis.importacao;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RegistroBaseGenesis {
    private final AbaBaseGenesis aba;
    private final BigDecimal idGenesis;
    private final Map<String, Object> campos;

    public RegistroBaseGenesis(AbaBaseGenesis aba, BigDecimal idGenesis, Map<String, Object> campos) {
        this.aba = aba;
        this.idGenesis = idGenesis;
        this.campos = Collections.unmodifiableMap(new LinkedHashMap<String, Object>(campos));
    }

    public AbaBaseGenesis getAba() {
        return aba;
    }

    public BigDecimal getIdGenesis() {
        return idGenesis;
    }

    public Map<String, Object> getCampos() {
        return campos;
    }
}
