package br.com.zaya.mahospitalar.genesis.domain;

public final class ReferenciasBaseInstaladaGenesis {
    private final long idCliente;
    private final long idSetor;
    private final long idModelo;
    private final Long idItemMaterial;
    private final String codigoExternoItem;

    public ReferenciasBaseInstaladaGenesis(long idCliente, long idSetor, long idModelo,
                                           Long idItemMaterial, String codigoExternoItem) {
        this.idCliente = idCliente;
        this.idSetor = idSetor;
        this.idModelo = idModelo;
        this.idItemMaterial = idItemMaterial;
        this.codigoExternoItem = codigoExternoItem;
    }

    public long getIdCliente() { return idCliente; }
    public long getIdSetor() { return idSetor; }
    public long getIdModelo() { return idModelo; }
    public Long getIdItemMaterial() { return idItemMaterial; }
    public String getCodigoExternoItem() { return codigoExternoItem; }
    public boolean isItemExistente() { return idItemMaterial != null; }
}
