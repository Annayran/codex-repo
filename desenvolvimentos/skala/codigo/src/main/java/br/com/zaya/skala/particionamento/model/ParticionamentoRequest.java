package br.com.zaya.skala.particionamento.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticionamentoRequest {
    private final List<Integer> nuNotas = new ArrayList<Integer>();
    private final List<ParticionamentoItemConfig> itens = new ArrayList<ParticionamentoItemConfig>();
    private final List<ParticionamentoOeConfig> oes = new ArrayList<ParticionamentoOeConfig>();
    private double capacidadePaletes;
    private String modoParticionamento;
    private String observacao;
    private boolean origemModal;

    public List<Integer> getNuNotas() {
        return Collections.unmodifiableList(nuNotas);
    }

    public void addNuNota(int nuNota) {
        if (nuNota > 0 && !nuNotas.contains(nuNota)) {
            nuNotas.add(nuNota);
        }
    }

    public List<ParticionamentoItemConfig> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void addItem(ParticionamentoItemConfig item) {
        if (item != null) {
            itens.add(item);
        }
    }

    public List<ParticionamentoOeConfig> getOes() {
        return Collections.unmodifiableList(oes);
    }

    public void addOe(ParticionamentoOeConfig oe) {
        if (oe != null) {
            oes.add(oe);
        }
    }

    public String getDhAgendamentoOe(int nuNotaOrigem, int numeroOe) {
        ParticionamentoOeConfig fallback = null;
        for (ParticionamentoOeConfig oe : oes) {
            if (oe.getNumero() != numeroOe) {
                continue;
            }
            if (oe.getNuNotaOrigem() == nuNotaOrigem) {
                return oe.getDhAgendamento();
            }
            if (oe.getNuNotaOrigem() <= 0 && fallback == null) {
                fallback = oe;
            }
        }
        return fallback == null ? null : fallback.getDhAgendamento();
    }

    public double getCapacidadePaletes() {
        return capacidadePaletes;
    }

    public void setCapacidadePaletes(double capacidadePaletes) {
        this.capacidadePaletes = capacidadePaletes;
    }

    public String getModoParticionamento() {
        return modoParticionamento;
    }

    public void setModoParticionamento(String modoParticionamento) {
        this.modoParticionamento = modoParticionamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isOrigemModal() {
        return origemModal;
    }

    public void setOrigemModal(boolean origemModal) {
        this.origemModal = origemModal;
    }

    public boolean temConfiguracaoItens() {
        return !itens.isEmpty();
    }
}
