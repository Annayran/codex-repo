package br.com.zaya.mahospitalar.genesis.domain;

public final class ReferenciaGenesisLocal {
    private final Long idRegistro;
    private final Long idGenesis;
    private final String codigoExterno;
    private final boolean herdadaDoModelo;

    public ReferenciaGenesisLocal(Long idRegistro, Long idGenesis, String codigoExterno) {
        this(idRegistro,idGenesis,codigoExterno,false);
    }
    private ReferenciaGenesisLocal(Long idRegistro, Long idGenesis, String codigoExterno, boolean herdadaDoModelo) {
        this.idRegistro = idRegistro;
        this.idGenesis = idGenesis;
        this.codigoExterno = codigoExterno;
        this.herdadaDoModelo = herdadaDoModelo;
    }

    public Long getIdRegistro() { return idRegistro; }
    public Long getIdGenesis() { return idGenesis; }
    public String getCodigoExterno() { return codigoExterno; }
    public boolean existe() { return idRegistro != null; }
    public boolean isHerdadaDoModelo() { return herdadaDoModelo; }
    public ReferenciaGenesisLocal herdadaDoModelo() {
        return new ReferenciaGenesisLocal(idRegistro,idGenesis,codigoExterno,true);
    }

    public static ReferenciaGenesisLocal ausente() {
        return new ReferenciaGenesisLocal(null, null, null);
    }
}
