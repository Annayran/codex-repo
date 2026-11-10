package br.com.zaya.mahospitalar.genesis.domain;

public enum TipoEntidade {
    CLIENTE("/clientes/", 10),
    SETOR("/setores/", 20),
    MATERIAL("/materiais/", 30),
    FABRICANTE("/empresas/", 30),
    MODELO("/modelos/", 40),
    ITEM_MATERIAL("/itens-materiais/", 50),
    ESTOQUE("/estoques/", 60);

    private final String endpoint;
    private final int prioridade;

    TipoEntidade(String endpoint, int prioridade) {
        this.endpoint = endpoint;
        this.prioridade = prioridade;
    }

    public String getEndpoint() { return endpoint; }
    public int getPrioridade() { return prioridade; }
}
