package br.com.zaya.mahospitalar.genesis.importacao;

public final class ResultadoLoteGenesis {
    private final int inseridos;
    private final int atualizados;

    public ResultadoLoteGenesis(int inseridos, int atualizados) {
        this.inseridos = inseridos;
        this.atualizados = atualizados;
    }

    public int getInseridos() {
        return inseridos;
    }

    public int getAtualizados() {
        return atualizados;
    }

    public int getTotal() {
        return inseridos + atualizados;
    }

    public String resumir() {
        return "processados=" + getTotal() + ", inseridos=" + inseridos + ", atualizados=" + atualizados;
    }
}
