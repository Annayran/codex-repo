package br.com.zaya.topnutri.importacaoformulas.model.service;

import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaParseResult;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ValidationIssue;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class FormulaBatchValidator {
    private static final BigDecimal MAXIMUM_MASS_VARIATION = new BigDecimal("0.05");

    public FormulaParseResult validate(FormulaParseResult parsed) {
        List<ValidationIssue> issues = new ArrayList<ValidationIssue>(parsed.getIssues());

        for (ProductionFormula formula : parsed.getFormulas()) {
            validateFormula(formula, issues);
        }
        return new FormulaParseResult(parsed.getFormulas(), issues);
    }

    private void validateFormula(ProductionFormula formula, List<ValidationIssue> issues) {
        if (formula.getComponents().isEmpty()) {
            issues.add(new ValidationIssue(
                    formula.getLineNumber(),
                    "FORMULA_WITHOUT_COMPONENTS",
                    "Fórmula deve possuir ao menos um componente."));
            return;
        }

        BigDecimal total = formula.getComponentTotal();
        BigDecimal absoluteDifference = formula.getQuantityDifference().abs();
        BigDecimal maximumDifference = formula.getOutputQuantity()
                .abs()
                .multiply(MAXIMUM_MASS_VARIATION);
        if (absoluteDifference.compareTo(maximumDifference) > 0) {
            issues.add(new ValidationIssue(
                    formula.getLineNumber(),
                    "MASS_MISMATCH",
                    "A soma dos componentes do PA " + formula.getFinishedProductCode()
                            + " ultrapassa o limite permitido de 5%. Revise o arquivo de origem."));
        }
    }
}
