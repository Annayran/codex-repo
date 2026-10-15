package br.com.zaya.engineering.nexo.monitoring;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class MonitorContracts {
    private MonitorContracts() {}
    public enum Severity { BAIXA, MEDIA, ALTA, CRITICA }
    public enum Impact { UNKNOWN, LOCAL_BLOCK, PROCESS_OUTAGE, RECOVERED }
    public enum Origin { LIVE, HISTORICAL }
    public enum Status { ABERTA, EM_ANALISE, CORRECAO_PROPOSTA, EM_VALIDACAO, RESOLVIDA }
    public record FailureEvent(String eventId, String executionId, String component, String version,
                               String category, String exceptionType, Instant occurredAt, Impact impact,
                               Origin origin, String investigationId, String logExcerpt) {
        public FailureEvent(String eventId,String executionId,String component,String version,String category,
                            String exceptionType,Instant occurredAt,Impact impact,Origin origin,String investigationId) {
            this(eventId,executionId,component,version,category,exceptionType,occurredAt,impact,origin,investigationId,null);
        }
    }
    public record Audit(Instant at, String actor, String action, String reason) {}
    public record Issue(String id, String fingerprint, String component, String version, String category,
                        String exceptionType, Instant firstSeen, Instant lastSeen, long occurrences,
                        long historicalOccurrences, Severity severity, String severityReason, boolean severityReviewed,
                        Status status, String workOrderId, String owner, String proposal, String validation,
                        List<String> investigationIds, List<Audit> audit, String lastLogExcerpt) {}
    public record EventReceipt(String issueId, boolean duplicate, Severity severity, String workOrderId) {}
    public record EventIndex(String digest, String issueId, Instant occurredAt, Origin origin) {}
    public record Store(Map<String,Issue> issues, Map<String,EventIndex> events) {}
    public record Triage(Severity severity, Status status, String owner, String reason,
                         String proposal, String validation) {}
}
