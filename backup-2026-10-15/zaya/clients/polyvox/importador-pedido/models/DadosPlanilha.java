package org.ImportadorPedido.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public class DadosPlanilha {

    @JsonProperty("PVX")
    String pvx;

    @JsonProperty("PA")
    String pa;

    @JsonProperty("REFERÊNCIA")
    @JsonAlias({"REFERENCIA", "Referencia"})
    String ref;

    @JsonProperty("CÓDIGO")
    @JsonAlias({"CODIGO", "Código", "Codigo", "COD", "cod"})
    BigDecimal cod;

    @JsonProperty("Unit")
    @JsonAlias({"UNIT", "Unidade"})
    String unit;

    @JsonProperty("Unit Price (USD)")
    @JsonAlias({"UNIT PRICE (USD)", "Unit Price", "UNITPRICE"})
    BigDecimal UnitPrice;

    @JsonProperty("Qty")
    @JsonAlias({"QTY", "Quantidade"})
    BigDecimal qty;

    @JsonProperty("total Price (USD)")
    @JsonAlias({"Total Price (USD)", "TOTAL PRICE (USD)", "TOTALPRICE"})
    BigDecimal totalPrice;

    @JsonProperty("Empresa")
    @JsonAlias({"EMPRESA"})
    BigDecimal empresa;

    @JsonProperty("Parceiro")
    @JsonAlias({"PARCEIRO", "CodParc", "CODPARC"})
    BigDecimal parceiro;

    public String getPvx() {
        return pvx;
    }

    public void setPvx(String pvx) {
        this.pvx = pvx;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getCod() {
        return cod;
    }

    public void setCod(BigDecimal cod) {
        this.cod = cod;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        UnitPrice = unitPrice;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getEmpresa() {
        return empresa;
    }

    public void setEmpresa(BigDecimal empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getParceiro() {
        return parceiro;
    }

    public void setParceiro(BigDecimal parceiro) {
        this.parceiro = parceiro;
    }

    @Override
    public String toString() {
        return "NotaFiscal{" +
                "pvx='" + pvx + '\'' +
                ",pa='" + pa + '\'' +
                ",ref='" + ref + '\'' +
                ",cod=" + cod +
                ",unit='" + unit + '\'' +
                ",UnitPrice=" + UnitPrice +
                ",qty=" + qty +
                ",totalPrice=" + totalPrice +
                ",empresa=" + empresa +
                ",parceiro=" + parceiro +
                '}';
    }
}
