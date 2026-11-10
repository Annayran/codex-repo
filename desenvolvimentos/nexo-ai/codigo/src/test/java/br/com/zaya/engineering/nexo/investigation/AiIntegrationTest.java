package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import br.com.zaya.engineering.nexo.sankhya.FixtureGateway;
import br.com.zaya.engineering.nexo.knowledge.KnowledgeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class AiIntegrationTest {
    @TempDir Path storage;
    private InvestigationService service(ExplanationModel model) {
        var s = new InvestigationService();
        s.mapper = new ObjectMapper(); s.gateway = new FixtureGateway(); s.knowledge = new KnowledgeStore();
        s.scopes = new ScopeRegistry(); s.scopes.demoIdentities = true; s.scopes.manifest = Optional.empty(); s.scopes.load();
        s.mode = "fixture"; s.storage = storage.toString(); s.ai = true; s.maxItems = 100; s.timeout = 10000; s.model = model;
        return s;
    }
    private Diagnosis run(InvestigationService service) {
        return service.investigate(new Scope("demo-a", "fixture"), new Request(1003, "CONFIRMAR_NOTA", "PRIVATE_REPORT_SENTINEL"));
    }
    @Test void acceptedAdvisoryIsPersistedWithoutRawIncident() throws Exception {
        var s = service(new ExplanationModel() {
            public String plan(String input) { assertFalse(input.contains("PRIVATE_REPORT_SENTINEL")); return "CONTEXT_FIRST"; }
            public String explain(String input) {
                assertFalse(input.contains("PRIVATE_REPORT_SENTINEL"));
                assertTrue(input.contains("UNIDADE"));
                try {
                    return Files.readString(Path.of("docs/examples/ai-review-reference.json"))
                            .replace("synthetic-item", "fixture-item-1")
                            .replace("synthetic-log", "fixture-context").replace("synthetic-report", "fixture-context");
                } catch (Exception e) { throw new IllegalStateException(e); }
            }
        });
        var diagnosis = run(s);
        assertEquals(State.HIPOTESE, diagnosis.state()); assertNull(diagnosis.rootCause());
        assertEquals("HIPOTESE", s.mapper.readTree(diagnosis.explanation()).get("state").textValue());
        var stored = s.mapper.readTree(Files.readString(storage.resolve("demo-a/fixture/" + diagnosis.id() + ".json")));
        assertEquals(diagnosis.explanation(), stored.get("diagnosis").get("explanation").textValue());
        var reviewed = s.review(new Scope("demo-a", "fixture"), diagnosis.id());
        assertNotEquals(diagnosis.id(), reviewed.id());
        assertEquals(diagnosis.state(), reviewed.state());
        assertTrue(reviewed.tools().stream().anyMatch(t -> t.outcome().equals("source=" + diagnosis.id())));
        assertThrows(jakarta.ws.rs.NotFoundException.class,
                () -> s.review(new Scope("demo-b", "fixture"), diagnosis.id()));
        s.ai = false;
        assertThrows(jakarta.ws.rs.ServiceUnavailableException.class,
                () -> s.review(new Scope("demo-a", "fixture"), diagnosis.id()));
    }
    @Test void invalidOutputAndPlanKeepDeterministicReport() {
        var diagnosis = run(service(new ExplanationModel() {
            public String plan(String input) { return "DELETE"; }
            public String explain(String input) { return "{\"state\":\"CONFIRMADA\"}"; }
        }));
        assertEquals(State.HIPOTESE, diagnosis.state()); assertNull(diagnosis.rootCause());
        assertTrue(diagnosis.explanation().contains("invalid"));
        assertTrue(diagnosis.missing().stream().anyMatch(v -> v.contains("unauthorized plan")));
        assertFalse(diagnosis.divergences().isEmpty());
    }
    @Test void providerFailureKeepsReportAndDoesNotLeakException() {
        var diagnosis = run(service(new ExplanationModel() {
            public String plan(String input) { throw new IllegalStateException("SECRET_SENTINEL"); }
            public String explain(String input) { throw new IllegalStateException("SECRET_SENTINEL"); }
        }));
        assertEquals(State.HIPOTESE, diagnosis.state()); assertNull(diagnosis.rootCause());
        assertTrue(diagnosis.explanation().contains("unavailable"));
        assertFalse(diagnosis.toString().contains("SECRET_SENTINEL"));
    }
    @Test void packageReceiptNeverWaitsForModel() {
        var s = service(new ExplanationModel() {
            public String plan(String input) { fail("Receipt must not call model"); return null; }
            public String explain(String input) { fail("Receipt must not call model"); return null; }
        });
        s.intake = new PackageIntake(); s.intake.scopes = s.scopes;
        var packet = new EvidencePackage(1,1003,"CONFIRMAR_NOTA","Synthetic error",java.time.Instant.now(),
                "test-version","test-collector",true,java.util.List.of(),java.util.List.of(),java.util.List.of());
        var receipt = s.receive(new Scope("demo-a","fixture"),packet);
        assertEquals(State.INCONCLUSIVA, receipt.state());
        assertEquals("Deterministic report; no model invoked.", receipt.explanation());
    }
}
