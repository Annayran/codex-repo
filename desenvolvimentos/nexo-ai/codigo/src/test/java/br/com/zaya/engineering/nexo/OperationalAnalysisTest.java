package br.com.zaya.engineering.nexo;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import br.com.zaya.engineering.nexo.investigation.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class OperationalAnalysisTest {
    private Snapshot snapshot(Boolean active) {
        var item=new Item("SYNTHETIC","2","L",null,"CX","UN",null,null,null,null,false,null,active,List.of());
        return new Snapshot(1003,List.of(item),List.of(),List.of(),null,List.of());
    }
    @Test void inactiveProductGetsConditionalDocumentedProcedure() {
        var report=OperationalAnalysis.analyze(snapshot(false));
        assertTrue(report.manualResolution().stream().anyMatch(s->s.contains("SYNTHETIC")));
        assertTrue(report.manualResolution().stream().anyMatch(s->s.contains("Se a reativação for autorizada")));
        assertTrue(report.manualResolution().stream().anyMatch(s->s.contains("Propriedades ou Geral")));
        assertEquals(2,report.sources().size());
        assertTrue(report.limits().stream().anyMatch(s->s.contains("não comprova estoque insuficiente")));
    }
    @Test void unknownAndActiveDoNotGetActivationAdvice() {
        for(var snapshot:List.of(snapshot(null),snapshot(true))) {
            var report=OperationalAnalysis.analyze(snapshot);
            assertTrue(report.sources().isEmpty());
            assertFalse(report.manualResolution().stream().anyMatch(s->s.contains("marcar Ativo")));
        }
    }
    @Test void repeatedFindingsAreGroupedWithInactiveFirstAndBoundedReferences() {
        var findings=new java.util.ArrayList<Finding>();
        for(int i=1;i<=43;i++) findings.add(new Finding("UNIDADE","Units differ",List.of("item-"+i)));
        findings.add(new Finding("PRODUTO_INATIVO","Inactive",List.of("item-22")));
        var result=ModelEvidenceSummary.summarize(findings,List.of("Quantity missing for item 1","Quantity missing for item 2"));
        var groups=(List<Map<String,Object>>)result.get("findings");
        assertEquals(2,groups.size()); assertEquals("PRODUTO_INATIVO",groups.getFirst().get("kind"));
        assertEquals(43,groups.get(1).get("count"));
        assertEquals(4,((List<?>)groups.get(1).get("evidenceIds")).size());
        assertEquals(1,((List<?>)result.get("missing")).size());
    }
}
