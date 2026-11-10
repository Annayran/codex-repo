package br.com.zaya.mahospitalar.genesis.domain;

public final class PayloadGenesis {
    private final TipoEntidade tipo;
    private final String chaveSankhya;
    private final String codigoExterno;
    private final Object dto;
    private final String json;
    private final String hash;

    public PayloadGenesis(TipoEntidade tipo, String chaveSankhya, String codigoExterno,
                          Object dto, String json, String hash) {
        this.tipo = tipo;
        this.chaveSankhya = chaveSankhya;
        this.codigoExterno = codigoExterno;
        this.dto = dto;
        this.json = json;
        this.hash = hash;
    }

    public TipoEntidade getTipo() { return tipo; }
    public String getChaveSankhya() { return chaveSankhya; }
    public String getCodigoExterno() { return codigoExterno; }
    public Object getDto() { return dto; }
    public String getJson() { return json; }
    public String getHash() { return hash; }
}
