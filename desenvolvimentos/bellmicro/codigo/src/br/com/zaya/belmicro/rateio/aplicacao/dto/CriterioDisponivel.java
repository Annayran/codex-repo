package br.com.zaya.belmicro.rateio.aplicacao.dto;

import java.util.Objects;

public final class CriterioDisponivel {
    private final String id;
    private final int versao;
    private final String descricao;
    public CriterioDisponivel(String id, int versao, String descricao) {
        this.id = Objects.requireNonNull(id, "id");
        if (id.trim().isEmpty() || versao <= 0) throw new IllegalArgumentException("Criterio invalido.");
        this.versao = versao;
        this.descricao = Objects.requireNonNull(descricao, "descricao");
    }
    public String getId() { return id; }
    public int getVersao() { return versao; }
    public String getDescricao() { return descricao; }
}
