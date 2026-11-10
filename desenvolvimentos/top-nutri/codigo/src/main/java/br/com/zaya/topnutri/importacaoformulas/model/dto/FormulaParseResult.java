package br.com.zaya.topnutri.importacaoformulas.model.dto;

import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FormulaParseResult {
    private final List<ProductionFormula> formulas;
    private final List<ValidationIssue> issues;

    public FormulaParseResult(List<ProductionFormula> formulas, List<ValidationIssue> issues) {
        this.formulas = Collections.unmodifiableList(new ArrayList<ProductionFormula>(formulas));
        this.issues = Collections.unmodifiableList(new ArrayList<ValidationIssue>(issues));
    }

    public List<ProductionFormula> getFormulas() {
        return formulas;
    }

    public List<ValidationIssue> getIssues() {
        return issues;
    }

    public boolean isValid() {
        return issues.isEmpty();
    }
}
