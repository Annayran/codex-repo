package br.com.zaya.engineering.nexo.diagnosis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.time.Instant;

public final class Contracts {
    private Contracts() {}
    public enum State { CONFIRMADA, HIPOTESE, INCONCLUSIVA }
    public record Request(long nunota, String action, String error) {}
    public record Scope(String tenant, String environment) {}
    public record Evidence(String id, String source, String version, String process, String tenant,
                           boolean simulated, String observation) {}
    public record StockRow(String company, String location, String control, String partner, String type,
                           BigDecimal physical, BigDecimal reserved) {}
    public record Item(String product, String company, String location, String expectedLocation,
                       String unit, String stockUnit, String control, String stockControl,
                       BigDecimal required, BigDecimal availability, boolean ruleValidated,
                       String ruleReference, Boolean productActive, List<StockRow> stockRows) {
        public Item {
            stockRows = stockRows == null ? List.of() : List.copyOf(stockRows);
        }
        public Item(String product, String company, String location, String expectedLocation,
                    String unit, String stockUnit, String control, String stockControl,
                    BigDecimal required, BigDecimal availability, boolean ruleValidated, String ruleReference) {
            this(product, company, location, expectedLocation, unit, stockUnit, control, stockControl,
                    required, availability, ruleValidated, ruleReference, null, List.of());
        }
    }
    public record Snapshot(long nunota, List<Item> items, List<Evidence> evidence,
                           List<String> missing, String provenOrigin, List<String> originEvidence,
                           Map<Integer, List<String>> itemEvidence) {
        public Snapshot(long nunota, List<Item> items, List<Evidence> evidence, List<String> missing,
                        String provenOrigin, List<String> originEvidence) {
            this(nunota, items, evidence, missing, provenOrigin, originEvidence, Map.of());
        }
    }
    /** No tenant, permissions or causal verdict is accepted from the button payload. */
    public record CollectedEvidence(String id, String source, String version, String observation) {}
    public record CollectedItem(Item item, List<String> evidenceIds) {}
    public record LogExcerpt(String status, String correlation, int scannedBytes, boolean truncated, String content) {}
    public record EvidencePackage(int schemaVersion, long nunota, String action, String error,
                                  Instant collectedAt, String erpVersion, String collectorVersion,
                                  boolean simulated, List<CollectedItem> items,
                                  List<CollectedEvidence> evidence, List<String> missing, LogExcerpt logExcerpt) {
        public EvidencePackage(int schemaVersion,long nunota,String action,String error,Instant collectedAt,
                               String erpVersion,String collectorVersion,boolean simulated,List<CollectedItem> items,
                               List<CollectedEvidence> evidence,List<String> missing) {
            this(schemaVersion,nunota,action,error,collectedAt,erpVersion,collectorVersion,simulated,items,evidence,missing,null);
        }
    }
    public record Finding(String kind, String detail, List<String> evidenceIds) {}
    public record Trace(String tool, long nunota, String outcome, long elapsedMs) {}
    public record Receipt(String id, State state, boolean simulated) {}
    public record Diagnosis(String id, boolean simulated, String symptom, String action, String process,
                            State state, List<String> facts, List<Finding> divergences,
                            List<Finding> hypotheses, String rootCause, List<String> missing,
                            String nextVerification, List<Evidence> evidence, List<Trace> tools,
                            String explanation) {}
}
