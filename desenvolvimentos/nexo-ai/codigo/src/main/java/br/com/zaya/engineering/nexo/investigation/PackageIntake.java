package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.*;

@ApplicationScoped
public class PackageIntake {
    @Inject ScopeRegistry scopes;
    public Snapshot validate(Scope scope, EvidencePackage packet, int maxItems) {
        if (packet == null || packet.schemaVersion() != 1 || packet.nunota() <= 0
                || !"CONFIRMAR_NOTA".equals(packet.action()) || !text(packet.error(), 2000, true)
                || packet.collectedAt() == null || !text(packet.erpVersion(), 80, true)
                || !text(packet.collectorVersion(), 80, true) || packet.items() == null
                || packet.items().size() > maxItems || packet.evidence() == null || packet.evidence().size() > 200
                || packet.missing() == null || packet.missing().size() > 50)
            throw invalid();
        Instant now = Instant.now();
        if (packet.collectedAt().isAfter(now.plusSeconds(60)) || packet.collectedAt().isBefore(now.minusSeconds(86400)))
            throw new BadRequestException("Collect a fresh package; timestamp outside 24-hour window");
        boolean simulated = packet.simulated() || scope.environment().equals("fixture");
        var evidence = new ArrayList<Evidence>();
        var ids = new HashSet<String>();
        for (var source : packet.evidence()) {
            if (source == null || !text(source.id(), 100, true) || source.id().startsWith("nexo-")
                    || !ids.add(source.id()) || !text(source.source(), 500, true)
                    || !text(source.version(), 80, true) || !text(source.observation(), 2000, true)) throw invalid();
            // Tenant/process are stamped by the server, never copied from the sender.
            evidence.add(new Evidence(source.id(), source.source(), source.version(), "confirmation-stock",
                    scope.tenant(), simulated, source.observation()));
        }
        var missing = new ArrayList<String>();
        for (var value : packet.missing()) {
            if (!text(value, 1000, true)) throw invalid();
            missing.add(value);
        }
        missing.add("Button collection is reported evidence; no direct ERP access or transaction-time snapshot was verified");
        missing.add("Root-cause origin requires reviewed historical evidence; no submitted verdict is accepted");
        if (packet.items().isEmpty()) missing.add("Items and stock dimensions were not collected");
        var items = new ArrayList<Item>();
        var references = new HashMap<Integer, List<String>>();
        for (var collected : packet.items()) {
            if (collected == null || collected.item() == null || collected.evidenceIds() == null
                    || collected.evidenceIds().isEmpty() || collected.evidenceIds().size() > 20
                    || collected.evidenceIds().stream().anyMatch(id -> id == null || !ids.contains(id))) throw invalid();
            var item = collected.item();
            for (var value : Arrays.asList(item.product(), item.company(), item.location(), item.expectedLocation(),
                    item.unit(), item.stockUnit(), item.control(), item.stockControl(), item.ruleReference()))
                if (!text(value, 120, false)) throw invalid();
            if (!decimal(item.required()) || !decimal(item.availability())
                    || item.required() != null && item.required().signum() <= 0) throw invalid();
            if (item.stockRows().size() > 8) throw invalid();
            for (var row : item.stockRows()) {
                if (row == null || !decimal(row.physical()) || !decimal(row.reserved())) throw invalid();
                for (var value : Arrays.asList(row.company(), row.location(), row.control(), row.partner(), row.type()))
                    if (!text(value,120,false)) throw invalid();
                if (!Objects.equals(row.company(), item.company()) || !Objects.equals(row.location(), item.location())
                        || !Objects.equals(row.control(), item.control())) throw invalid();
            }
            boolean approved = item.ruleValidated() && scopes.policyApproved(scope, item.ruleReference(), packet.erpVersion(), packet.collectorVersion());
            if (!approved) missing.add("Availability policy not approved on server for item " + (items.size()+1));
            var normalized = new Item(item.product(), item.company(), item.location(), item.expectedLocation(),
                    item.unit(), item.stockUnit(), item.control(), item.stockControl(), item.required(), item.availability(),
                    approved, item.ruleReference(), item.productActive(), item.stockRows());
            references.put(items.size(), List.copyOf(collected.evidenceIds()));
            items.add(normalized);
            evidence.add(new Evidence("nexo-item-"+items.size(), "collection://normalized-item/"+items.size(), packet.collectorVersion(),
                    "confirmation-stock", scope.tenant(), simulated,
                    "Reported item: product="+item.product()+"; company="+item.company()+"; location="+item.location()
                    +"; expectedLocation="+item.expectedLocation()+"; unit="+item.unit()+"; stockUnit="+item.stockUnit()
                    +"; control="+item.control()+"; stockControl="+item.stockControl()+"; required="+item.required()
                    +"; suppliedAvailability="+item.availability()+"; serverApprovedPolicy="+approved+"; policy="+item.ruleReference()
                    +"; productActive="+item.productActive()+"; stockRows="+item.stockRows().size()));
        }
        evidence.add(new Evidence("nexo-package", "collection://action-button", packet.collectorVersion(), "confirmation-stock",
                scope.tenant(), simulated, "Collection timestamp="+packet.collectedAt()+"; ERP version reported="+packet.erpVersion()
                +"; note="+packet.nunota()+"; no ERP query executed by central service"));
        return new Snapshot(packet.nunota(), List.copyOf(items), List.copyOf(evidence), List.copyOf(missing),
                null, List.of(), Map.copyOf(references));
    }
    private static boolean text(String value, int max, boolean required) {
        return value == null ? !required : value.length() <= max && (!required || !value.isBlank());
    }
    private static boolean decimal(java.math.BigDecimal value) {
        return value == null || value.precision() <= 28 && Math.abs((long)value.scale()) <= 8;
    }
    private static BadRequestException invalid() { return new BadRequestException("Invalid or oversized evidence package or unresolved item reference"); }
}
