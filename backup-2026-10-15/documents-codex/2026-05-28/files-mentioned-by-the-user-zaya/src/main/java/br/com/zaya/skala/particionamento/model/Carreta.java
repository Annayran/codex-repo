package br.com.zaya.skala.particionamento.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.Timestamp;

public class Carreta {
    private final int numero;
    private final double capacidade;
    private double capacidadeLivre;
    private Timestamp dhAgendamento;
    private final List<ItemCarreta> itens = new ArrayList<ItemCarreta>();

    public Carreta(int numero, double capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.capacidadeLivre = capacidade;
    }

    public int getNumero() {
        return numero;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public double getCapacidadeLivre() {
        return capacidadeLivre;
    }

    public void setCapacidadeLivre(double capacidadeLivre) {
        this.capacidadeLivre = capacidadeLivre;
    }

    public Timestamp getDhAgendamento() {
        return dhAgendamento;
    }

    public void setDhAgendamento(Timestamp dhAgendamento) {
        this.dhAgendamento = dhAgendamento;
    }

    public List<ItemCarreta> getItens() {
        return itens;
    }

    public List<ItemCarreta> getItensSomenteLeitura() {
        return Collections.unmodifiableList(itens);
    }

    public double getPaletesUsados() {
        return capacidade - capacidadeLivre;
    }

    public boolean temCapacidade(double tolerancia) {
        return capacidadeLivre > tolerancia;
    }

    @Override
    public String toString() {
        return "Carreta{" +
                "numero=" + numero +
                ", capacidade=" + capacidade +
                ", capacidadeLivre=" + capacidadeLivre +
                ", itens=" + itens +
                '}';
    }
}
