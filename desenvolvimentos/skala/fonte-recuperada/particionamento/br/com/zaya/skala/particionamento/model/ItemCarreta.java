package br.com.zaya.skala.particionamento.model;

import java.math.BigDecimal;

public class ItemCarreta {
   private String codProd;
   private String descProd;
   private double qtdAlocada;
   private double qtdOrigem;
   private double qtdUnidade;
   private double qtdCaixa;
   private BigDecimal qtdUnidadeExata;
   private BigDecimal qtdCaixaExata;
   private int sequenciaOrigem;
   private int nuNotaOrigem;
   private double codLocalOrig;
   private String controle;
   private String statusLote;
   private String codVol;
   private String usoProd;
   private double vlrUnit;

   public ItemCarreta() {
      this.qtdUnidadeExata = BigDecimal.ZERO;
      this.qtdCaixaExata = BigDecimal.ZERO;
   }

   public String getCodProd() {
      return this.codProd;
   }

   public void setCodProd(String codProd) {
      this.codProd = codProd;
   }

   public String getDescProd() {
      return this.descProd;
   }

   public void setDescProd(String descProd) {
      this.descProd = descProd;
   }

   public double getQtdAlocada() {
      return this.qtdAlocada;
   }

   public void setQtdAlocada(double qtdAlocada) {
      this.qtdAlocada = qtdAlocada;
   }

   public double getQtdOrigem() {
      return this.qtdOrigem;
   }

   public void setQtdOrigem(double qtdOrigem) {
      this.qtdOrigem = qtdOrigem;
   }

   public double getQtdUnidade() {
      return this.qtdUnidade;
   }

   public void setQtdUnidade(double qtdUnidade) {
      this.qtdUnidade = qtdUnidade;
      this.qtdUnidadeExata = BigDecimal.valueOf(qtdUnidade);
   }

   public double getQtdCaixa() {
      return this.qtdCaixa;
   }

   public void setQtdCaixa(double qtdCaixa) {
      this.qtdCaixa = qtdCaixa;
      this.qtdCaixaExata = BigDecimal.valueOf(qtdCaixa);
   }

   public BigDecimal getQtdUnidadeExata() {
      return this.qtdUnidadeExata;
   }

   public void setQtdUnidadeExata(BigDecimal value) {
      this.qtdUnidadeExata = value == null ? BigDecimal.ZERO : value;
      this.qtdUnidade = this.qtdUnidadeExata.doubleValue();
   }

   public BigDecimal getQtdCaixaExata() {
      return this.qtdCaixaExata;
   }

   public void setQtdCaixaExata(BigDecimal value) {
      this.qtdCaixaExata = value == null ? BigDecimal.ZERO : value;
      this.qtdCaixa = this.qtdCaixaExata.doubleValue();
   }

   public int getSequenciaOrigem() {
      return this.sequenciaOrigem;
   }

   public void setSequenciaOrigem(int sequenciaOrigem) {
      this.sequenciaOrigem = sequenciaOrigem;
   }

   public int getNuNotaOrigem() {
      return this.nuNotaOrigem;
   }

   public void setNuNotaOrigem(int nuNotaOrigem) {
      this.nuNotaOrigem = nuNotaOrigem;
   }

   public double getCodLocalOrig() {
      return this.codLocalOrig;
   }

   public void setCodLocalOrig(double codLocalOrig) {
      this.codLocalOrig = codLocalOrig;
   }

   public String getControle() {
      return this.controle;
   }

   public void setControle(String controle) {
      this.controle = controle;
   }

   public String getStatusLote() {
      return this.statusLote;
   }

   public void setStatusLote(String statusLote) {
      this.statusLote = statusLote;
   }

   public String getCodVol() {
      return this.codVol;
   }

   public void setCodVol(String codVol) {
      this.codVol = codVol;
   }

   public String getUsoProd() {
      return this.usoProd;
   }

   public void setUsoProd(String usoProd) {
      this.usoProd = usoProd;
   }

   public double getVlrUnit() {
      return this.vlrUnit;
   }

   public void setVlrUnit(double vlrUnit) {
      this.vlrUnit = vlrUnit;
   }

   public String toString() {
      return "ItemCarreta{codProd='" + this.codProd + '\'' + ", qtdAlocada=" + this.qtdAlocada + ", qtdUnidade=" + this.qtdUnidade + ", qtdCaixa=" + this.qtdCaixa + '}';
   }
}
