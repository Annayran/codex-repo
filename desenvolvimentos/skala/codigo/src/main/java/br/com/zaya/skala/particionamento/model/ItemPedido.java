package br.com.zaya.skala.particionamento.model;

public class ItemPedido {
    private String codProd;
    private String descProd;
    private double qtdPalete;
    private double qtdOrigem;
    private double undPorPalete;
    private double cxPorPalete;
    private int sequenciaOrigem;
    private double codLocalOrig;
    private String controle;
    private String statusLote;
    private String tipContest;
    private String codVol;
    private String usoProd;
    private double vlrUnit;
    private int nuNotaOrigem;
    private boolean selecionado = true;
    private boolean priorizado;
    private int ordemPrioridade;
    private String codFamilia;
    private String descFamilia;

    public ItemPedido() {
    }

    public ItemPedido(String codProd, String descProd, double qtdPalete, double undPorPalete, double cxPorPalete) {
        this.codProd = codProd;
        this.descProd = descProd;
        this.qtdPalete = qtdPalete;
        this.undPorPalete = undPorPalete;
        this.cxPorPalete = cxPorPalete;
    }

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

    public double getQtdPalete() {
        return qtdPalete;
    }

    public void setQtdPalete(double qtdPalete) {
        this.qtdPalete = qtdPalete;
    }

    public double getQtdOrigem() {
        return qtdOrigem;
    }

    public void setQtdOrigem(double qtdOrigem) {
        this.qtdOrigem = qtdOrigem;
    }

    public double getUndPorPalete() {
        return undPorPalete;
    }

    public void setUndPorPalete(double undPorPalete) {
        this.undPorPalete = undPorPalete;
    }

    public double getCxPorPalete() {
        return cxPorPalete;
    }

    public void setCxPorPalete(double cxPorPalete) {
        this.cxPorPalete = cxPorPalete;
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

    public String getTipContest() {
        return tipContest;
    }

    public void setTipContest(String tipContest) {
        this.tipContest = tipContest;
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

    public int getNuNotaOrigem() {
        return nuNotaOrigem;
    }

    public void setNuNotaOrigem(int nuNotaOrigem) {
        this.nuNotaOrigem = nuNotaOrigem;
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

    public String getGrupoMix() {
        return codFamilia;
    }

    public void setGrupoMix(String grupoMix) {
        this.codFamilia = grupoMix;
    }

    public String getCodFamilia() {
        return codFamilia;
    }

    public void setCodFamilia(String codFamilia) {
        this.codFamilia = codFamilia;
    }

    public String getDescFamilia() {
        return descFamilia;
    }

    public void setDescFamilia(String descFamilia) {
        this.descFamilia = descFamilia;
    }
}
