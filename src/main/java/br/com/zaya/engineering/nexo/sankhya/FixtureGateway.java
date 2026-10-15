package br.com.zaya.engineering.nexo.sankhya;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class FixtureGateway implements SankhyaGateway {
    public Snapshot readConfirmationContext(Scope scope, long nunota, int maxItems, long timeoutMs) {
        if (!scope.environment().equals("fixture") || !List.of("demo-a", "demo-b").contains(scope.tenant()))
            throw new SecurityException("Fixture scope unavailable");
        long base = scope.tenant().equals("demo-a") ? 1000 : 2000;
        if (nunota < base + 1 || nunota > base + 6) throw new NotFoundException();
        int scenario = (int) (nunota - base);
        if (maxItems < 1 || timeoutMs < 1) throw new IllegalArgumentException("Execution budget exhausted");
        String id = "fixture-context";
        var evidence = new Evidence(id, "fixture://confirmation/" + scenario, "1", "confirmation-stock",
                scope.tenant(), true, scenario == 5
                    ? "Synthetic balance=20; policy fixture-rule-v1 excludes 19 units; supplied availability=1. This is not a Sankhya formula."
                    : "Synthetic note and item; no ERP query executed");
        var item = new Item("SYNTHETIC-PRODUCT", "SYNTHETIC-COMPANY", "L1", scenario == 2 ? "L2" : "L1",
                "UN", scenario == 3 ? "CX" : "UN", "A", scenario == 4 ? "B" : "A",
                BigDecimal.TEN, scenario == 1 || scenario == 5 ? BigDecimal.ONE : BigDecimal.valueOf(20),
                scenario != 6, "fixture-rule-v1");
        var missing = scenario == 6 ? List.of("Validated availability policy missing", "Historical settings missing")
                : List.of("ERP event history and related documents unavailable", "Current and historical operation/product settings not inspected", "Customizations and integrations not inspected");
        var itemEvidence = new Evidence("fixture-item-1", "fixture://confirmation/"+scenario+"/item/1", "1", "confirmation-stock",
                scope.tenant(), true, "company="+item.company()+"; product="+item.product()+"; location="+item.location()
                +"; expectedLocation="+item.expectedLocation()+"; unit="+item.unit()+"; stockUnit="+item.stockUnit()
                +"; control="+item.control()+"; stockControl="+item.stockControl()+"; required="+item.required()
                +"; suppliedAvailability="+item.availability()+"; policyValidatedInFixture="+item.ruleValidated()
                +"; policy="+item.ruleReference());
        return new Snapshot(nunota, List.of(item), List.of(evidence, itemEvidence), missing, null, List.of());
    }
}
