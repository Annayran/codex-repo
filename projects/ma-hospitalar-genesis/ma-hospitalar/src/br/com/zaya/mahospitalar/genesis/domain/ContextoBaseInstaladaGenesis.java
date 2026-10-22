package br.com.zaya.mahospitalar.genesis.domain;

public final class ContextoBaseInstaladaGenesis {
    public final ReferenciaGenesisLocal cliente;
    public final ReferenciaGenesisLocal setor;
    public final ReferenciaGenesisLocal material;
    public final ReferenciaGenesisLocal fabricante;
    public final ReferenciaGenesisLocal modelo;
    public final ReferenciaGenesisLocal itemMaterial;

    public ContextoBaseInstaladaGenesis(ReferenciaGenesisLocal cliente,
                                        ReferenciaGenesisLocal setor,
                                        ReferenciaGenesisLocal material,
                                        ReferenciaGenesisLocal fabricante,
                                        ReferenciaGenesisLocal modelo,
                                        ReferenciaGenesisLocal itemMaterial) {
        this.cliente = cliente;
        this.setor = setor;
        this.material = material;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.itemMaterial = itemMaterial;
    }
}
