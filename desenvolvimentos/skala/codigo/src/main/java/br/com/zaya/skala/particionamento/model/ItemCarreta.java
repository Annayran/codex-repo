package br.com.zaya.skala.particionamento.model;

public class ItemCarreta {
    private String codProd;
    private String descProd;
    private double qtdAlocada;
    private double qtdOrigem;
    private double qtdUnidade;
    private double qtdCaixa;
    private int sequenciaOrigem;
    private double codLocalOrig;
    private String controle;
    private String statusLote;
    private String codVol;
    private String usoProd;
    private double vlrUnit;

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getDescProd() {
        return descProd;
    }

    public void setDescProd(String descProd) {
        this.descProd = descProd;
    }

    public double getQtdAlocada() {
        return qtdAlocada;
    }

    public void setQtdAlocada(double qtdAlocada) {
        this.qtdAlocada = qtdAlocada;
    }

    public double getQtdOrigem() {
        return qtdOrigem;
    }

    public void setQtdOrigem(double qtdOrigem) {
        this.qtdOrigem = qtdOrigem;
    }

    public double getQtdUnidade() {
        return qtdUnidade;
    }

    public void setQtdUnidade(double qtdUnidade) {
        this.qtdUnidade = qtdUnidade;
    }

    public double getQtdCaixa() {
        return qtdCaixa;
    }

    public void setQtdCaixa(double qtdCaixa) {
        this.qtdCaixa = qtdCaixa;
    }

    public int getSequenciaOrigem() {
        return sequenciaOrigem;
    }

    public void setSequenciaOrigem(int sequenciaOrigem) {
        this.sequenciaOrigem = sequenciaOrigem;
    }

    public double getCodLocalOrig() {
        return codLocalOrig;
    }

    public void setCodLocalOrig(double codLocalOrig) {
        this.codLocalOrig = codLocalOrig;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public String getStatusLote() {
        return statusLote;
    }

    public void setStatusLote(String statusLote) {
        this.statusLote = statusLote;
    }

    public String getCodVol() {
        return codVol;
    }

    public void setCodVol(String codVol) {
        this.codVol = codVol;
    }

    public String getUsoProd() {
        return usoProd;
    }

    public void setUsoProd(String usoProd) {
        this.usoProd = usoProd;
    }

    public double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    @Override
    public String toString() {
        return "ItemCarreta{" +
                "codProd='" + codProd + '\'' +
                ", qtdAlocada=" + qtdAlocada +
                ", qtdUnidade=" + qtdUnidade +
                ", qtdCaixa=" + qtdCaixa +
                '}';
    }
}
