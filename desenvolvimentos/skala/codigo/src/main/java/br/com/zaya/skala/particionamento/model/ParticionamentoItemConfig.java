package br.com.zaya.skala.particionamento.model;

public class ParticionamentoItemConfig {
    private int nuNota;
    private int sequencia;
    private String codProd;
    private boolean selecionado = true;
    private boolean priorizado;
    private int ordemPrioridade;
    private String grupo;

    public int getNuNota() {
        return nuNota;
    }

    public void setNuNota(int nuNota) {
        this.nuNota = nuNota;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isPriorizado() {
        return priorizado;
    }

    public void setPriorizado(boolean priorizado) {
        this.priorizado = priorizado;
    }

    public int getOrdemPrioridade() {
        return ordemPrioridade;
    }

    public void setOrdemPrioridade(int ordemPrioridade) {
        this.ordemPrioridade = ordemPrioridade;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
