package br.com.zaya.topnutri.importacaoformulas.model.service;

import br.com.zaya.topnutri.importacaoformulas.model.entity.CompositionPlan;
import br.com.zaya.topnutri.importacaoformulas.model.entity.FormulaComponent;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductCategory;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;
import br.com.zaya.topnutri.importacaoformulas.model.repository.MicroIntermediateProductResolver;
import br.com.zaya.topnutri.importacaoformulas.model.repository.ProductCategoryProvider;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormulaCompositionPlannerTest {
    @Test
    void shouldMoveOnlyCategoryMToExclusiveIntermediateProduct() throws Exception {
        final Map<String, ProductCategory> categories = new HashMap<String, ProductCategory>();
        categories.put("MICRO1", ProductCategory.MICRONUTRIENT);
        categories.put("MICRO2", ProductCategory.MICRONUTRIENT);
        categories.put("MACRO", ProductCategory.MACRONUTRIENT);

        FormulaCompositionPlanner planner = planner(categories, Optional.of("PI0001"));
        CompositionPlan plan = planner.plan(formula(
                component("MICRO1", "0.100000"),
                component("MICRO2", "0.200000"),
                component("MACRO", "24.000000")));

        assertEquals(1, plan.getDirectComponents().size());
        assertEquals(2, plan.getMicronutrients().size());
        assertEquals(2, plan.getNormalizedMicronutrients().size());
        assertEquals(0, BigDecimal.ONE.compareTo(
                plan.getNormalizedMicronutrients().get(0).getQuantity()
                        .add(plan.getNormalizedMicronutrients().get(1).getQuantity())));
        assertEquals(0, new BigDecimal("0.3333333333333333333333333333333333")
                .compareTo(plan.getNormalizedMicronutrients().get(0).getQuantity()));
        assertTrue(plan.getIntermediateProduct().isPresent());
        assertEquals("PI0001", plan.getIntermediateProduct().get().getProductCode());
        assertEquals(0, new BigDecimal("0.300000").compareTo(
                plan.getIntermediateProduct().get().getQuantity()));
    }

    @Test
    void shouldNotResolveOrCreateEmptyIntermediateProductWithOnlyMacros() throws Exception {
        final int[] resolverCalls = {0};
        ProductCategoryProvider categories = code -> Optional.of(ProductCategory.MACRONUTRIENT);
        MicroIntermediateProductResolver resolver = code -> {
            resolverCalls[0]++;
            return Optional.empty();
        };

        CompositionPlan plan = new FormulaCompositionPlanner(categories, resolver)
                .plan(formula(component("MACRO", "25.000000")));

        assertEquals(1, plan.getDirectComponents().size());
        assertTrue(plan.getMicronutrients().isEmpty());
        assertFalse(plan.getIntermediateProduct().isPresent());
        assertEquals(0, resolverCalls[0]);
    }

    @Test
    void shouldBlockComponentWithoutCategory() {
        FormulaCompositionPlanner planner = planner(
                Collections.<String, ProductCategory>emptyMap(), Optional.of("PI0001"));

        assertThrows(
                FormulaPlanningException.class,
                () -> planner.plan(formula(component("SEM_CATEGORIA", "25.000000"))));
    }

    @Test
    void shouldBlockPackagingReceivedInOptimalFile() {
        Map<String, ProductCategory> categories = Collections.singletonMap(
                "EMB", ProductCategory.PACKAGING);

        assertThrows(
                FormulaPlanningException.class,
                () -> planner(categories, Optional.of("PI0001"))
                        .plan(formula(component("EMB", "1.000000"))));
    }

    @Test
    void shouldBlockMicronutrientsWithoutExclusiveIntermediateProduct() {
        Map<String, ProductCategory> categories = Collections.singletonMap(
                "MICRO1", ProductCategory.MICRONUTRIENT);
        FormulaCompositionPlanner planner = planner(categories, Optional.empty());

        assertThrows(
                FormulaPlanningException.class,
                () -> planner.plan(formula(component("MICRO1", "25.000000"))));
    }

    private FormulaCompositionPlanner planner(
            final Map<String, ProductCategory> categories,
            final Optional<String> intermediateProduct) {
        ProductCategoryProvider categoryProvider = code -> Optional.ofNullable(categories.get(code));
        MicroIntermediateProductResolver resolver = code -> intermediateProduct;
        return new FormulaCompositionPlanner(categoryProvider, resolver);
    }

    private ProductionFormula formula(FormulaComponent... components) {
        return new ProductionFormula(
                1,
                "PA0001",
                "FÓRMULA TESTE",
                new BigDecimal("25.000000"),
                "@none",
                Arrays.asList(components));
    }

    private FormulaComponent component(String code, String quantity) {
        return new FormulaComponent(2, "PA0001", code, new BigDecimal(quantity));
    }
}
