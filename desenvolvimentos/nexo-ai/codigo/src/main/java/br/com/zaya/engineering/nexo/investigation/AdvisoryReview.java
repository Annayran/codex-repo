package br.com.zaya.engineering.nexo.investigation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.zaya.engineering.nexo.diagnosis.Contracts.State;
import java.util.HashSet;
import java.util.Set;

/** Structural validation is not proof that a model's prose is factually correct. */
public final class AdvisoryReview {
    private AdvisoryReview() {}

    public static String validate(ObjectMapper mapper, String response, State state, Set<String> evidenceIds)
            throws java.io.IOException {
        require(response != null && response.length() <= 64000);
        JsonNode root;
        try (var parser = mapper.getFactory().createParser(response)) {
            parser.enable(com.fasterxml.jackson.core.JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
            root = mapper.readTree(parser);
            require(parser.nextToken() == null);
        }
        fields(root, "schemaVersion", "state", "problem", "observations", "hypotheses", "missingEvidence", "nextChecks");
        require(root.get("schemaVersion").isIntegralNumber()
                && root.get("schemaVersion").bigIntegerValue().equals(java.math.BigInteger.ONE));
        require(state != State.CONFIRMADA && root.get("state").isTextual()
                && root.get("state").textValue().equals(state.name()));
        text(root.get("problem"), 1000);
        claims(root.get("observations"), 1, 50, evidenceIds);
        claims(root.get("hypotheses"), 0, 10, evidenceIds);
        array(root.get("missingEvidence"), 1, 50);
        for (var value : root.get("missingEvidence")) text(value, 1000);
        array(root.get("nextChecks"), 1, 10);
        for (var check : root.get("nextChecks")) {
            fields(check, "action", "purpose", "readOnly");
            text(check.get("action"), 1000);
            text(check.get("purpose"), 1000);
            require(check.get("readOnly").isBoolean() && check.get("readOnly").booleanValue());
        }
        return mapper.writeValueAsString(root);
    }

    private static void claims(JsonNode values, int min, int max, Set<String> allowed) {
        array(values, min, max);
        for (var claim : values) {
            fields(claim, "statement", "evidenceIds", "limitation");
            text(claim.get("statement"), 1000);
            text(claim.get("limitation"), 1000);
            array(claim.get("evidenceIds"), 1, 50);
            var seen = new HashSet<String>();
            for (var id : claim.get("evidenceIds")) {
                text(id, 100);
                require(allowed.contains(id.textValue()) && seen.add(id.textValue()));
            }
        }
    }
    private static void fields(JsonNode value, String... names) {
        require(value != null && value.isObject() && value.size() == names.length);
        for (String name : names) require(value.has(name));
    }
    private static void array(JsonNode value, int min, int max) {
        require(value != null && value.isArray() && value.size() >= min && value.size() <= max);
    }
    private static void text(JsonNode value, int max) {
        require(value != null && value.isTextual() && !value.textValue().isBlank() && value.textValue().length() <= max);
    }
    private static void require(boolean condition) {
        if (!condition) throw new IllegalArgumentException("Invalid advisory review");
    }
}
