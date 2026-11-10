package br.com.zaya.topnutri.importacaoformulas.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ProductionFormula {
    private final int lineNumber;
    private final String finishedProductCode;
    private final String description;
    private final BigDecimal outputQuantity;
    private final String ignoredField;
    private final List<FormulaComponent> components;

    public ProductionFormula(
            int lineNumber,
            String finishedProductCode,
            String description,
            BigDecimal outputQuantity,
            String ignoredField,
            List<FormulaComponent> components) {
        this.lineNumber = lineNumber;
        this.finishedProductCode = Objects.requireNonNull(finishedProductCode, "finishedProductCode");
        this.description = Objects.requireNonNull(description, "description");
        this.outputQuantity = Objects.requireNonNull(outputQuantity, "outputQuantity");
        this.ignoredField = Objects.requireNonNull(ignoredField, "ignoredField");
        this.components = Collections.unmodifiableList(new ArrayList<FormulaComponent>(components));
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getFinishedProductCode() {
        return finishedProductCode;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getOutputQuantity() {
        return outputQuantity;
    }

    public String getIgnoredField() {
        return ignoredField;
    }

    public List<FormulaComponent> getComponents() {
        return components;
    }

    public BigDecimal getComponentTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (FormulaComponent component : components) {
            total = total.add(component.getQuantity());
        }
        return total;
    }

    public BigDecimal getQuantityDifference() {
        return getComponentTotal().subtract(outputQuantity);
    }
}
