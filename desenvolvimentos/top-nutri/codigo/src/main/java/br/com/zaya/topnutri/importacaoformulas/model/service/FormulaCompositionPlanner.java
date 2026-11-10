package br.com.zaya.topnutri.importacaoformulas.model.service;

import br.com.zaya.topnutri.importacaoformulas.model.entity.CompositionPlan;
import br.com.zaya.topnutri.importacaoformulas.model.entity.FormulaComponent;
import br.com.zaya.topnutri.importacaoformulas.model.entity.IntermediateProductAllocation;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductCategory;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;
import br.com.zaya.topnutri.importacaoformulas.model.repository.MicroIntermediateProductResolver;
import br.com.zaya.topnutri.importacaoformulas.model.repository.ProductCategoryProvider;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FormulaCompositionPlanner {
    private static final MathContext NORMALIZATION_CONTEXT = MathContext.DECIMAL128;
    private final ProductCategoryProvider categoryProvider;
    private final MicroIntermediateProductResolver intermediateProductResolver;

    public FormulaCompositionPlanner(
            ProductCategoryProvider categoryProvider,
            MicroIntermediateProductResolver intermediateProductResolver) {
        this.categoryProvider = categoryProvider;
        this.intermediateProductResolver = intermediateProductResolver;
    }

    public CompositionPlan plan(ProductionFormula formula) throws FormulaPlanningException {
        List<FormulaComponent> direct = new ArrayList<FormulaComponent>();
        List<FormulaComponent> micronutrients = new ArrayList<FormulaComponent>();
        BigDecimal micronutrientTotal = BigDecimal.ZERO;

        for (FormulaComponent component : formula.getComponents()) {
            Optional<ProductCategory> category = categoryProvider.findCategory(component.getComponentCode());
            if (!category.isPresent()) {
                throw new FormulaPlanningException(
                        "Componente " + component.getComponentCode()
                                + " sem TGFPRO.AD_CATEGORIA valida na linha "
                                + component.getLineNumber() + ". Classifique como Macro ou Micro.");
            }
            if (category.get() == ProductCategory.MICRONUTRIENT) {
                micronutrients.add(component);
                micronutrientTotal = micronutrientTotal.add(component.getQuantity());
            } else if (category.get() == ProductCategory.MACRONUTRIENT) {
                direct.add(component);
            } else {
                throw new FormulaPlanningException(
                        "Componente " + component.getComponentCode() + " possui categoria "
                                + category.get().getMaterialType() + " na linha "
                                + component.getLineNumber()
                                + ". O TXT deve conter somente Macro ou Micro.");
            }
        }

        IntermediateProductAllocation intermediateProduct = null;
        List<FormulaComponent> normalizedMicronutrients =
                new ArrayList<FormulaComponent>();
        if (!micronutrients.isEmpty()) {
            if (micronutrientTotal.compareTo(BigDecimal.ZERO) <= 0) {
                throw new FormulaPlanningException(
                        "A soma dos micronutrientes deve ser maior que zero para o produto acabado "
                                + formula.getFinishedProductCode() + ".");
            }
            Optional<String> productCode = intermediateProductResolver
                    .findExclusiveIntermediateProduct(formula.getFinishedProductCode());
            if (!productCode.isPresent()) {
                throw new FormulaPlanningException(
                        "PI Micro exclusivo não localizado para o produto acabado "
                                + formula.getFinishedProductCode() + ".");
            }
            intermediateProduct = new IntermediateProductAllocation(
                    productCode.get(), micronutrientTotal);
            normalizedMicronutrients = normalizeToOneKilogram(
                    micronutrients, micronutrientTotal);
        }

        return new CompositionPlan(
                formula, direct, micronutrients, normalizedMicronutrients,
                intermediateProduct);
    }

    private List<FormulaComponent> normalizeToOneKilogram(
            List<FormulaComponent> micronutrients,
            BigDecimal total) {
        List<FormulaComponent> normalized = new ArrayList<FormulaComponent>();
        BigDecimal accumulated = BigDecimal.ZERO;
        for (int index = 0; index < micronutrients.size(); index++) {
            FormulaComponent source = micronutrients.get(index);
            BigDecimal quantity;
            if (index == micronutrients.size() - 1) {
                quantity = BigDecimal.ONE.subtract(accumulated);
            } else {
                quantity = source.getQuantity().divide(total, NORMALIZATION_CONTEXT);
                accumulated = accumulated.add(quantity);
            }
            normalized.add(new FormulaComponent(
                    source.getLineNumber(),
                    source.getFinishedProductCode(),
                    source.getComponentCode(),
                    quantity));
        }
        return normalized;
    }
}
