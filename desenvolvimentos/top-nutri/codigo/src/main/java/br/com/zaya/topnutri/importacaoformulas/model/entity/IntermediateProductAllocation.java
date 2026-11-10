package br.com.zaya.topnutri.importacaoformulas.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public final class IntermediateProductAllocation {
    private final String productCode;
    private final BigDecimal quantity;

    public IntermediateProductAllocation(String productCode, BigDecimal quantity) {
        this.productCode = Objects.requireNonNull(productCode, "productCode");
        this.quantity = Objects.requireNonNull(quantity, "quantity");
    }

    public String getProductCode() {
        return productCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
