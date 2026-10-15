package br.com.zaya.skala.particionamento.model;

public class ParticionamentoOeConfig {
    private int numero;
    private int nuNotaOrigem;
    private String dhAgendamento;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNuNotaOrigem() {
        return nuNotaOrigem;
    }

    public void setNuNotaOrigem(int nuNotaOrigem) {
        this.nuNotaOrigem = nuNotaOrigem;
    }

    public String getDhAgendamento() {
        return dhAgendamento;
    }

    public void setDhAgendamento(String dhAgendamento) {
        this.dhAgendamento = dhAgendamento;
    }
}
