package br.com.zaya.topnutri.importacaoformulas.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FormulaImportSimulation {
    private final List<ProductImportSimulation> products;
    private final List<ValidationIssue> issues;

    public FormulaImportSimulation(
            List<ProductImportSimulation> products,
            List<ValidationIssue> issues) {
        this.products = Collections.unmodifiableList(
                new ArrayList<ProductImportSimulation>(products));
        this.issues = Collections.unmodifiableList(
                new ArrayList<ValidationIssue>(issues));
    }

    public List<ProductImportSimulation> getProducts() { return products; }
    public List<ValidationIssue> getIssues() { return issues; }
    public boolean isValid() { return issues.isEmpty(); }
}
