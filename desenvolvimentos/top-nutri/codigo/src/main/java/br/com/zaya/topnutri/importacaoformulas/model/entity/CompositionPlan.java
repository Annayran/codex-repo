package br.com.zaya.topnutri.importacaoformulas.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class CompositionPlan {
    private final ProductionFormula sourceFormula;
    private final List<FormulaComponent> directComponents;
    private final List<FormulaComponent> micronutrients;
    private final List<FormulaComponent> normalizedMicronutrients;
    private final IntermediateProductAllocation intermediateProduct;

    public CompositionPlan(
            ProductionFormula sourceFormula,
            List<FormulaComponent> directComponents,
            List<FormulaComponent> micronutrients,
            List<FormulaComponent> normalizedMicronutrients,
            IntermediateProductAllocation intermediateProduct) {
        this.sourceFormula = Objects.requireNonNull(sourceFormula, "sourceFormula");
        this.directComponents = Collections.unmodifiableList(
                new ArrayList<FormulaComponent>(directComponents));
        this.micronutrients = Collections.unmodifiableList(
                new ArrayList<FormulaComponent>(micronutrients));
        this.normalizedMicronutrients = Collections.unmodifiableList(
                new ArrayList<FormulaComponent>(normalizedMicronutrients));
        this.intermediateProduct = intermediateProduct;
    }

    public ProductionFormula getSourceFormula() {
        return sourceFormula;
    }

    public List<FormulaComponent> getDirectComponents() {
        return directComponents;
    }

    public List<FormulaComponent> getMicronutrients() {
        return micronutrients;
    }

    /** Composicao proporcional do PI, cuja soma e exatamente um quilograma. */
    public List<FormulaComponent> getNormalizedMicronutrients() {
        return normalizedMicronutrients;
    }

    public Optional<IntermediateProductAllocation> getIntermediateProduct() {
        return Optional.ofNullable(intermediateProduct);
    }
}
