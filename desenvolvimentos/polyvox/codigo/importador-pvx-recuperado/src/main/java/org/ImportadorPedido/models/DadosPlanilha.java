/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAlias
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package org.ImportadorPedido.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DadosPlanilha {
    @JsonProperty(value="PVX")
    String pvx;
    @JsonProperty(value="PA")
    String pa;
    @JsonProperty(value="REFER\u00caNCIA")
    @JsonAlias(value={"REFERENCIA", "Referencia"})
    String ref;
    @JsonProperty(value="C\u00d3DIGO")
    @JsonAlias(value={"CODIGO", "C\u00f3digo", "Codigo", "COD", "cod"})
    BigDecimal cod;
    @JsonProperty(value="Unit")
    @JsonAlias(value={"UNIT", "Unidade"})
    String unit;
    @JsonProperty(value="Unit Price (USD)")
    @JsonAlias(value={"UNIT PRICE (USD)", "Unit Price", "UNITPRICE"})
    BigDecimal UnitPrice;
    @JsonProperty(value="Qty")
    @JsonAlias(value={"QTY", "Quantidade"})
    BigDecimal qty;
    @JsonProperty(value="total Price (USD)")
    @JsonAlias(value={"Total Price (USD)", "TOTAL PRICE (USD)", "TOTALPRICE"})
    BigDecimal totalPrice;
    @JsonProperty(value="Empresa")
    @JsonAlias(value={"EMPRESA"})
    BigDecimal empresa;
    @JsonProperty(value="Parceiro")
    @JsonAlias(value={"PARCEIRO", "CodParc", "CODPARC"})
    BigDecimal parceiro;

    public String getPvx() {
        return this.pvx;
    }

    public void setPvx(String pvx) {
        this.pvx = pvx;
    }

    public String getPa() {
        return this.pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getCod() {
        return this.cod;
    }

    public void setCod(BigDecimal cod) {
        this.cod = cod;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return this.UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.UnitPrice = unitPrice;
    }

    public BigDecimal getQty() {
        return this.qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(BigDecimal empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getParceiro() {
        return this.parceiro;
    }

    public void setParceiro(BigDecimal parceiro) {
        this.parceiro = parceiro;
    }

    public String toString() {
        return "NotaFiscal{pvx='" + this.pvx + '\'' + ",pa='" + this.pa + '\'' + ",ref='" + this.ref + '\'' + ",cod=" + this.cod + ",unit='" + this.unit + '\'' + ",UnitPrice=" + this.UnitPrice + ",qty=" + this.qty + ",totalPrice=" + this.totalPrice + ",empresa=" + this.empresa + ",parceiro=" + this.parceiro + '}';
    }
}

