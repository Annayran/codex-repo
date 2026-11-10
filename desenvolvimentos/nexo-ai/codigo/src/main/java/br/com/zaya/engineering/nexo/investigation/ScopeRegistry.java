package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.Scope;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.nio.file.*;
import java.util.*;

/** Administrator-managed mapping. Authentication must succeed before resolving this registry. */
@ApplicationScoped
public class ScopeRegistry {
    public record PolicyApproval(String reference, String erpVersion, String collectorVersion) {}
    public record Binding(String principal, String tenant, String environment, List<PolicyApproval> approvedPolicies) {}
    @Inject ObjectMapper mapper;
    @ConfigProperty(name="nexo.identity-manifest") Optional<String> manifest;
    @ConfigProperty(name="nexo.demo-identities", defaultValue="false") boolean demoIdentities;
    private final Map<String, Binding> identities = new HashMap<>();
    @PostConstruct void load() {
        if (demoIdentities) {
            for (String user : List.of("demo-a", "demo-b"))
                identities.put(user, new Binding(user, user, "fixture",
                        List.of(new PolicyApproval("fixture-rule-v1", "unverified-demo-version", "fixture-button-v1"))));
        }
        if (manifest.isEmpty()) return;
        try {
            var path = Path.of(manifest.get());
            if (Files.size(path) > 65536) throw new IllegalArgumentException("Identity manifest too large");
            for (var binding : mapper.readValue(path.toFile(), Binding[].class)) {
                if (binding.principal() == null || !slug(binding.tenant()) || !slug(binding.environment())
                        || binding.approvedPolicies() == null || binding.approvedPolicies().size() > 50
                        || binding.approvedPolicies().stream().anyMatch(p -> p == null || !bounded(p.reference(),120)
                                || !bounded(p.erpVersion(),80) || !bounded(p.collectorVersion(),80))
                        || identities.putIfAbsent(binding.principal(), binding) != null)
                    throw new IllegalArgumentException("Invalid or duplicate identity binding");
            }
        } catch (java.io.IOException e) { throw new IllegalStateException("Unable to load identity manifest"); }
    }
    private static boolean slug(String value) { return value != null && value.matches("[a-z0-9][a-z0-9_-]{0,63}"); }
    private static boolean bounded(String value, int max) { return value != null && !value.isBlank() && value.length() <= max; }
    public Scope resolve(String principal) {
        var binding = identities.get(principal);
        if (binding == null) throw new ForbiddenException();
        return new Scope(binding.tenant(), binding.environment());
    }
    public boolean authorized(Scope scope) {
        return identities.values().stream().anyMatch(b -> b.tenant().equals(scope.tenant()) && b.environment().equals(scope.environment()));
    }
    public boolean policyApproved(Scope scope, String reference, String erpVersion, String collectorVersion) {
        return reference != null && identities.values().stream().anyMatch(b -> b.tenant().equals(scope.tenant())
                && b.environment().equals(scope.environment()) && b.approvedPolicies().stream().anyMatch(p -> p.reference().equals(reference)
                        && p.erpVersion().equals(erpVersion) && p.collectorVersion().equals(collectorVersion)));
    }
}
