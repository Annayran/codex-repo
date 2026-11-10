package br.com.zaya.engineering.nexo.diagnosis;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import java.util.ArrayList;
import java.util.List;

public final class Checks {
    public List<Finding> verify(Snapshot snapshot) {
        var findings = new ArrayList<Finding>();
        for (int index=0; index<snapshot.items().size(); index++) {
            var item = snapshot.items().get(index);
            var refs = snapshot.itemEvidence().getOrDefault(index, snapshot.evidence().stream().map(Evidence::id).toList());
            if (Boolean.FALSE.equals(item.productActive()))
                findings.add(new Finding("PRODUTO_INATIVO", "Product reported inactive at collection time; transaction-time state and causal origin not established", refs));
            if (item.stockRows().stream().anyMatch(row -> row.physical() != null && row.physical().signum() < 0))
                findings.add(new Finding("ESTOQUE_FISICO_NEGATIVO", "Negative physical stock reported; availability and causal origin not established", refs));
            if (known(item.location()) && known(item.expectedLocation()) && different(item.location(), item.expectedLocation())) findings.add(new Finding("LOCAL", "Reported location differs from reported expected location", refs));
            if (known(item.unit()) && known(item.stockUnit()) && different(item.unit(), item.stockUnit())) findings.add(new Finding("UNIDADE", "Units differ; validated conversion required", refs));
            if (different(item.control(), item.stockControl())) findings.add(new Finding("CONTROLE", "Controls differ", refs));
            boolean sameDimensions = known(item.location()) && known(item.expectedLocation()) && same(item.location(), item.expectedLocation())
                    && known(item.unit()) && known(item.stockUnit()) && same(item.unit(), item.stockUnit())
                    && same(item.control(), item.stockControl()) && known(item.product()) && known(item.company());
            if (sameDimensions && item.ruleValidated() && item.ruleReference() != null && item.availability() != null && item.required() != null
                    && item.availability().compareTo(item.required()) < 0)
                findings.add(new Finding("DISPONIBILIDADE", "Availability supplied by validated policy is below required quantity; origin not established", refs));
        }
        return List.copyOf(findings);
    }
    public List<String> missing(Snapshot snapshot) {
        var missing = new ArrayList<String>();
        for (int i=0; i<snapshot.items().size(); i++) {
            var item = snapshot.items().get(i);
            if (!known(item.product()) || !known(item.company()) || !known(item.location()) || !known(item.expectedLocation())
                    || !known(item.unit()) || !known(item.stockUnit()) || item.control() == null || item.stockControl() == null)
                missing.add("Incomplete stock dimensions for item " + (i+1));
            if (item.required() == null || item.availability() == null) missing.add("Quantity or availability missing for item " + (i+1));
        }
        return List.copyOf(missing);
    }
    private static boolean same(String a, String b) { return a != null && b != null && a.equals(b); }
    private static boolean known(String value) { return value != null && !value.isBlank(); }
    private static boolean different(String a, String b) { return a != null && b != null && !a.equals(b); }
}
