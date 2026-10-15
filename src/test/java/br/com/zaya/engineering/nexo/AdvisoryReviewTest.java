package br.com.zaya.engineering.nexo;

import br.com.zaya.engineering.nexo.investigation.AdvisoryReview;
import br.com.zaya.engineering.nexo.diagnosis.Contracts.State;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class AdvisoryReviewTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Set<String> ids = Set.of("synthetic-item", "synthetic-log", "synthetic-report");
    private String sample() throws Exception {
        return Files.readString(Path.of("docs/examples/ai-review-reference.json"));
    }
    @Test void acceptsReferenceAndPreservesUncertainty() throws Exception {
        var result = mapper.readTree(AdvisoryReview.validate(mapper, sample(), State.HIPOTESE, ids));
        assertEquals("HIPOTESE", result.get("state").textValue());
        assertThrows(IllegalArgumentException.class, () -> AdvisoryReview.validate(mapper, sample(), State.INCONCLUSIVA, ids));
        assertThrows(IllegalArgumentException.class, () -> AdvisoryReview.validate(mapper, sample(), State.CONFIRMADA, ids));
    }
    @Test void rejectsInventedReferencesAndWriteFlags() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> AdvisoryReview.validate(mapper, sample(), State.HIPOTESE, Set.of()));
        var root = (ObjectNode) mapper.readTree(sample());
        ((ObjectNode) root.get("nextChecks").get(0)).put("readOnly", false);
        assertThrows(IllegalArgumentException.class, () -> AdvisoryReview.validate(mapper, root.toString(), State.HIPOTESE, ids));
    }
    @Test void rejectsMalformedDuplicateTrailingAndOversizedOutput() throws Exception {
        assertThrows(Exception.class, () -> AdvisoryReview.validate(mapper, "```json\n" + sample(), State.HIPOTESE, ids));
        assertThrows(Exception.class, () -> AdvisoryReview.validate(mapper, sample() + " {}", State.HIPOTESE, ids));
        assertThrows(Exception.class, () -> AdvisoryReview.validate(mapper, sample().replace("\"schemaVersion\": 1", "\"schemaVersion\": 1, \"schemaVersion\": 1"), State.HIPOTESE, ids));
        assertThrows(IllegalArgumentException.class, () -> AdvisoryReview.validate(mapper, "x".repeat(64001), State.HIPOTESE, ids));
        var root = (ObjectNode) mapper.readTree(sample());
        root.put("extra", true);
        assertThrows(IllegalArgumentException.class, () -> AdvisoryReview.validate(mapper, root.toString(), State.HIPOTESE, ids));
    }
}
