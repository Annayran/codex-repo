package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.Finding;
import java.util.*;

/** Compress repetitive findings for a small local model; full evidence remains in the stored report. */
public final class ModelEvidenceSummary {
    private ModelEvidenceSummary() {}
    public static Map<String,Object> summarize(List<Finding> findings, List<String> missing) {
        var grouped = new LinkedHashMap<String,List<Finding>>();
        findings.stream().sorted(Comparator.comparingInt(f -> "PRODUTO_INATIVO".equals(f.kind()) ? 0 : 1))
                .forEach(f -> grouped.computeIfAbsent(f.kind(), k -> new ArrayList<>()).add(f));
        var summaries = new ArrayList<Map<String,Object>>();
        var ids = new LinkedHashSet<String>();
        for(var entry : grouped.entrySet().stream().limit(8).toList()) {
            var refs = entry.getValue().stream().flatMap(f -> f.evidenceIds().stream()).distinct().limit(4).toList();
            ids.addAll(refs);
            summaries.add(Map.of("kind",entry.getKey(),"detail",entry.getValue().getFirst().detail(),
                    "count",entry.getValue().size(),"evidenceIds",refs));
        }
        var gaps = missing.stream().map(s -> s.replaceAll(" for item \\d+$", " (one or more items)"))
                .distinct().limit(8).toList();
        return Map.of("findings",summaries,"evidenceIds",ids,"missing",gaps,"summaryOnly",true);
    }
}
