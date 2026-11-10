/*
 * Decompiled with CFR 0.152.
 */
package org.ImportadorPedido.models;

import java.math.BigDecimal;

public class ItemImportado {
    private final String pvx;
    private final BigDecimal empresa;
    private final String pa;
    private final String referencia;
    private final BigDecimal cod;
    private final String unit;
    private final BigDecimal unitPrice;
    private final BigDecimal qty;
    private final BigDecimal totalPrice;

    public ItemImportado(String pvx, BigDecimal empresa, String pa, String referencia, BigDecimal cod, String unit, BigDecimal unitPrice, BigDecimal qty, BigDecimal totalPrice) {
        this.pvx = pvx;
        this.empresa = empresa;
        this.pa = pa;
        this.referencia = referencia;
        this.cod = cod;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }

    public String getPvx() {
        return this.pvx;
    }

    public BigDecimal getEmpresa() {
        return this.empresa;
    }

    public String getPa() {
        return this.pa;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public BigDecimal getCod() {
        return this.cod;
    }

    public String getUnit() {
        return this.unit;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public BigDecimal getQty() {
        return this.qty;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }
}

