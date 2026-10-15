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

    public ItemImportado(String pvx,
                         BigDecimal empresa,
                         String pa,
                         String referencia,
                         BigDecimal cod,
                         String unit,
                         BigDecimal unitPrice,
                         BigDecimal qty,
                         BigDecimal totalPrice) {
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
        return pvx;
    }

    public BigDecimal getEmpresa() {
        return empresa;
    }

    public String getPa() {
        return pa;
    }

    public String getReferencia() {
        return referencia;
    }

    public BigDecimal getCod() {
        return cod;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}

