package br.com.zaya.models;

public class ImportacaoResultado {
    private final int recebidos;
    private final int inseridos;
    private final int duplicados;
    private final int invalidos;
    private final int falhas;
    private final int idsAutoGerados;
    private final int notasVinculadas;
    private final long duracaoMs;

    public ImportacaoResultado(
            int recebidos,
            int inseridos,
            int duplicados,
            int invalidos,
            int falhas,
            int idsAutoGerados,
            int notasVinculadas,
            long duracaoMs
    ) {
        this.recebidos = recebidos;
        this.inseridos = inseridos;
        this.duplicados = duplicados;
        this.invalidos = invalidos;
        this.falhas = falhas;
        this.idsAutoGerados = idsAutoGerados;
        this.notasVinculadas = notasVinculadas;
        this.duracaoMs = duracaoMs;
    }

    public int getRecebidos() {
        return recebidos;
    }

    public int getInseridos() {
        return inseridos;
    }

    public int getDuplicados() {
        return duplicados;
    }

    public int getInvalidos() {
        return invalidos;
    }

    public int getFalhas() {
        return falhas;
    }

    public int getIdsAutoGerados() {
        return idsAutoGerados;
    }

    public int getNotasVinculadas() {
        return notasVinculadas;
    }

    public long getDuracaoMs() {
        return duracaoMs;
    }

    public String toMensagemRetorno() {
        return String.format(
                "Integracao Koncili concluida. recebidos=%d inseridos=%d duplicados=%d invalidos=%d falhas=%d idsAutoGerados=%d notasVinculadas=%d duracaoMs=%d",
                recebidos,
                inseridos,
                duplicados,
                invalidos,
                falhas,
                idsAutoGerados,
                notasVinculadas,
                duracaoMs
        );
    }
}

