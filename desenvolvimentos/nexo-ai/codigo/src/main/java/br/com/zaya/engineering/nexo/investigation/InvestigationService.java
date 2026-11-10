package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.*;
import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import br.com.zaya.engineering.nexo.knowledge.KnowledgeStore;
import br.com.zaya.engineering.nexo.sankhya.SankhyaGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.nio.file.*;
import java.util.*;

@ApplicationScoped
public class InvestigationService {
    public record StoredInvestigation(Diagnosis diagnosis, Snapshot context, LogExcerpt logExcerpt) {}
    @Inject SankhyaGateway gateway;
    @Inject KnowledgeStore knowledge;
    @Inject ExplanationModel model;
    @Inject ObjectMapper mapper;
    @Inject ScopeRegistry scopes;
    @Inject PackageIntake intake;
    @ConfigProperty(name="nexo.gateway") String mode;
    @ConfigProperty(name="nexo.storage") String storage;
    @ConfigProperty(name="nexo.ai.enabled") boolean ai;
    @ConfigProperty(name="nexo.max-items") int maxItems;
    @ConfigProperty(name="nexo.timeout-ms") long timeout;

    public Scope scope(String principal) {
        return scopes.resolve(principal);
    }
    public boolean syntheticTesting(Scope scope) {
        return "fixture".equals(mode) && "fixture".equals(scope.environment())
                && List.of("demo-a","demo-b").contains(scope.tenant());
    }
    public Diagnosis investigate(Scope scope, Request request) {
        if (!"fixture".equals(mode)) throw new ServiceUnavailableException("Real gateway requires homologated queries");
        return run(scope, request, () -> gateway.readConfirmationContext(scope, request.nunota(), maxItems, timeout),
                "readConfirmationContext", "SIMULATED");
    }
    public Diagnosis receive(Scope scope, EvidencePackage packet) {
        if (!scopes.authorized(scope)) throw new ForbiddenException();
        if (maxItems < 1 || maxItems > 100) throw new ServiceUnavailableException("Invalid execution limits");
        var snapshot = intake.validate(scope, packet, maxItems);
        return run(scope, new Request(packet.nunota(), packet.action(), packet.error()), () -> snapshot,
                "receiveEvidencePackage", "RECEIVED_PACKAGE", LogExcerptFilter.normalize(packet.logExcerpt()), false);
    }
    public Diagnosis review(Scope scope, String id) {
        if (!ai) throw new ServiceUnavailableException("AI review is disabled");
        var original = find(scope, id);
        var snapshot = context(scope, id);
        LogExcerpt excerpt = null;
        try {
            var stored = mapper.readTree(directory(scope).resolve(id + ".json").toFile());
            if (stored.hasNonNull("logExcerpt")) excerpt = mapper.treeToValue(stored.get("logExcerpt"), LogExcerpt.class);
        } catch (java.io.IOException failure) { throw new InternalServerErrorException("Investigation read failed"); }
        return run(scope, new Request(snapshot.nunota(), original.action(), original.symptom()), () -> snapshot,
                "reviewStoredEvidence", "source=" + id, excerpt, true);
    }
    public OperationalAnalysis.Report analysis(Scope scope, String id) {
        find(scope,id);
        return OperationalAnalysis.analyze(context(scope,id));
    }
    private Diagnosis run(Scope scope, Request request, java.util.function.Supplier<Snapshot> context,
                          String contextTool, String contextOutcome) {
        return run(scope,request,context,contextTool,contextOutcome,null);
    }
    private Diagnosis run(Scope scope, Request request, java.util.function.Supplier<Snapshot> context,
                          String contextTool, String contextOutcome, LogExcerpt logExcerpt) {
        return run(scope, request, context, contextTool, contextOutcome, logExcerpt, ai);
    }
    private Diagnosis run(Scope scope, Request request, java.util.function.Supplier<Snapshot> context,
                          String contextTool, String contextOutcome, LogExcerpt logExcerpt, boolean useModel) {
        if (request == null || request.nunota() <= 0 || !"CONFIRMAR_NOTA".equals(request.action())
                || request.error() == null || request.error().isBlank() || request.error().length() > 2000)
            throw new BadRequestException("nunota > 0, action CONFIRMAR_NOTA and bounded error required");
        if (maxItems < 1 || maxItems > 100 || timeout < 1 || timeout > 10000) throw new ServiceUnavailableException("Invalid execution limits");
        var traces = new ArrayList<Trace>();
        var missingPlanning = new ArrayList<String>();
        String order = "CONTEXT_FIRST";
        if (useModel) {
            try {
                // Do not transmit raw incident text: it may contain credentials or personal data.
                String selected = model.plan("action=" + request.action() + "; process=confirmation-stock; reported stock confirmation error").strip();
                if (List.of("CONTEXT_FIRST", "KNOWLEDGE_FIRST").contains(selected)) order = selected;
                else missingPlanning.add("AI returned an unauthorized plan; fixed read-only plan used");
            } catch (Exception failure) { missingPlanning.add("AI planning unavailable; fixed read-only plan used"); }
        }
        List<KnowledgeStore.Document> documents = null;
        if (order.equals("KNOWLEDGE_FIRST")) {
            long retrievalStart = System.nanoTime();
            documents = knowledge.retrieve(scope, "confirmation-stock");
            traces.add(new Trace("retrieveReviewedKnowledge", request.nunota(), "documents="+documents.size(), (System.nanoTime()-retrievalStart)/1000000));
        }
        long start = System.nanoTime();
        var snapshot = context.get();
        if ((System.nanoTime() - start) / 1000000 > timeout || snapshot.items().size() > maxItems)
            throw new ServiceUnavailableException("Execution budget exceeded");
        if (snapshot.evidence().stream().anyMatch(e -> !e.tenant().equals(scope.tenant()))) throw new ForbiddenException();
        var findings = new Checks().verify(snapshot);
        traces.add(new Trace(contextTool, request.nunota(), contextOutcome, (System.nanoTime()-start)/1000000));
        if (documents == null) {
            long retrievalStart = System.nanoTime();
            documents = knowledge.retrieve(scope, "confirmation-stock");
            traces.add(new Trace("retrieveReviewedKnowledge", request.nunota(), "documents="+documents.size(), (System.nanoTime()-retrievalStart)/1000000));
        }
        var evidence = new ArrayList<>(snapshot.evidence());
        documents.forEach(d -> evidence.add(d.metadata()));
        var missing = new ArrayList<>(snapshot.missing());
        if (logExcerpt!=null) missing.add("Log excerpt status="+logExcerpt.status()+"; textual matching is not causal or request correlation");
        missing.addAll(new Checks().missing(snapshot));
        missing.addAll(missingPlanning);
        if (documents.isEmpty()) missing.add("Client-specific reviewed library has not been imported");
        // Finding a shortage does not demonstrate its origin. A complete causal chain is required.
        boolean confirmed = snapshot.provenOrigin() != null && !snapshot.originEvidence().isEmpty()
                && snapshot.originEvidence().stream().allMatch(id -> evidence.stream().anyMatch(e -> e.id().equals(id)))
                && evidence.stream().noneMatch(Evidence::simulated);
        var state = confirmed ? State.CONFIRMADA : findings.isEmpty() ? State.INCONCLUSIVA : State.HIPOTESE;
        String explanation = "Deterministic report; no model invoked.";
        if (useModel) {
            long explanationStart = System.nanoTime();
            boolean responseReceived = false;
            try {
                // Only findings, gaps and reviewed knowledge are transmitted; raw error/log are excluded.
                var allowedIds = evidence.stream().map(Evidence::id).collect(java.util.stream.Collectors.toSet());
                String response = model.explain(mapper.writeValueAsString(Map.of("state", state.name(),
                        "summary", ModelEvidenceSummary.summarize(findings,missing),
                        "untrustedKnowledge", documents.stream().map(d -> Map.of("evidenceId", d.metadata().id(),
                        "content", d.content().substring(0, Math.min(1000, d.content().length())))).toList())));
                responseReceived = true;
                explanation = AdvisoryReview.validate(mapper, response, state, allowedIds);
                traces.add(new Trace("explainAdvisory", request.nunota(), "ACCEPTED",
                        (System.nanoTime()-explanationStart)/1000000));
            } catch (Exception failure) {
                explanation = "AI explanation unavailable or invalid; deterministic report preserved.";
                traces.add(new Trace("explainAdvisory", request.nunota(), responseReceived ? "REJECTED_RESPONSE" : "PROVIDER_FAILURE",
                        (System.nanoTime()-explanationStart)/1000000));
            }
        }
        var diagnosis = new Diagnosis(UUID.randomUUID().toString(), evidence.stream().anyMatch(Evidence::simulated),
                request.error(), request.action(), "confirmation-stock", state,
                snapshot.evidence().stream().map(Evidence::observation).toList(), findings,
                findings.isEmpty() ? List.of() : List.of(new Finding("ORIGEM", "Origin requires event history and historical configuration", findings.getFirst().evidenceIds())),
                confirmed ? snapshot.provenOrigin() : null, List.copyOf(missing),
                "Validate dimension mappings and availability policy; obtain historical events in homologation",
                List.copyOf(evidence), List.copyOf(traces), explanation);
        save(scope, diagnosis, snapshot, logExcerpt);
        return diagnosis;
    }
    private Path directory(Scope scope) {
        if (!scopes.authorized(scope)) throw new ForbiddenException();
        return Path.of(storage).resolve(scope.tenant()).resolve(scope.environment());
    }
    private void save(Scope scope, Diagnosis diagnosis, Snapshot snapshot, LogExcerpt logExcerpt) {
        try {
            var dir = directory(scope); Files.createDirectories(dir);
            var temp = Files.createTempFile(dir, "investigation-", ".tmp");
            Path logFile=dir.resolve(diagnosis.id()+".log");
            boolean committed=false;
            try {
                mapper.writeValue(temp.toFile(), new StoredInvestigation(diagnosis, snapshot, logExcerpt));
                if (logExcerpt!=null && logExcerpt.status().equals("FOUND")) {
                    var logTemp=Files.createTempFile(dir,"excerpt-", ".tmp");
                    try {
                        Files.writeString(logTemp,LogExcerptFilter.fileContent(logExcerpt));
                        Files.move(logTemp,logFile,StandardCopyOption.ATOMIC_MOVE);
                    } finally { Files.deleteIfExists(logTemp); }
                }
                Files.move(temp, dir.resolve(diagnosis.id()+".json"), StandardCopyOption.ATOMIC_MOVE);
                committed=true;
            } finally {
                Files.deleteIfExists(temp);
                if (!committed) Files.deleteIfExists(logFile);
            }
        } catch (java.io.IOException e) { throw new InternalServerErrorException("Investigation persistence failed"); }
    }
    public Diagnosis find(Scope scope, String id) {
        try {
            UUID.fromString(id);
            var file = directory(scope).resolve(id+".json");
            if (!Files.exists(file)) throw new NotFoundException();
            var node = mapper.readTree(file.toFile());
            // Preserve access to reports persisted by version 0.1 before context storage existed.
            return mapper.treeToValue(node.has("diagnosis") ? node.get("diagnosis") : node, Diagnosis.class);
        } catch (IllegalArgumentException e) { throw new NotFoundException(); }
        catch (java.io.IOException e) { throw new InternalServerErrorException("Investigation read failed"); }
    }
    public String log(Scope scope,String id) {
        try {
            UUID.fromString(id);
            var dir=directory(scope);
            var file=dir.resolve(id+".log");
            if (!Files.exists(dir.resolve(id+".json")) || !Files.exists(file)) throw new NotFoundException("No collected log excerpt for this investigation");
            return Files.readString(file);
        } catch (IllegalArgumentException failure) { throw new NotFoundException(); }
        catch (java.io.IOException failure) { throw new InternalServerErrorException("Log excerpt read failed"); }
    }
    public Snapshot context(Scope scope, String id) {
        try {
            UUID.fromString(id);
            var file = directory(scope).resolve(id+".json");
            if (!Files.exists(file)) throw new NotFoundException();
            var node = mapper.readTree(file.toFile());
            if (!node.has("context")) throw new NotFoundException("Legacy report has no persisted context");
            return mapper.treeToValue(node.get("context"), Snapshot.class);
        } catch (IllegalArgumentException e) { throw new NotFoundException(); }
        catch (java.io.IOException e) { throw new InternalServerErrorException("Investigation context read failed"); }
    }
}
