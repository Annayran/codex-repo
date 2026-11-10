package br.com.zaya.topnutri.importacaoformulas.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public final class FormulaComponent {
    private final int lineNumber;
    private final String finishedProductCode;
    private final String componentCode;
    private final BigDecimal quantity;

    public FormulaComponent(
            int lineNumber,
            String finishedProductCode,
            String componentCode,
            BigDecimal quantity) {
        this.lineNumber = lineNumber;
        this.finishedProductCode = Objects.requireNonNull(finishedProductCode, "finishedProductCode");
        this.componentCode = Objects.requireNonNull(componentCode, "componentCode");
        this.quantity = Objects.requireNonNull(quantity, "quantity");
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getFinishedProductCode() {
        return finishedProductCode;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
