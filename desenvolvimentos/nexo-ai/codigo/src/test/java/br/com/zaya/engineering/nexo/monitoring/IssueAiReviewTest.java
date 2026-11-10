package br.com.zaya.engineering.nexo.monitoring;

import br.com.zaya.engineering.nexo.investigation.ExplanationModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IssueAiReviewTest {
    private IssueAnalysis.Report report() {
        return new IssueAnalysis.Report("HIPOTESE","Bloqueio","Validação entre quantidades reportada.",
                List.of("PRIVATE_COMPONENT_SENTINEL"),List.of("Hipótese"),List.of("Contexto ausente"),
                List.of(new IssueAnalysis.Step("Consultar os vínculos","Verificar a hipótese")),
                "Procedimento revisado","Reproduzir em homologação","NAO_SOLICITADA",null);
    }
    @Test void modelOnlyReceivesCuratedSignalsAndCannotConfirmCause() {
        var service=new IssueAiReview();service.enabled=true;service.mapper=new ObjectMapper();
        service.model=new ExplanationModel() {
            public String plan(String input){throw new AssertionError("No planning tools for OS");}
            public String explain(String input){
                assertFalse(input.contains("PRIVATE_COMPONENT_SENTINEL"));
                return """
                    {"schemaVersion":1,"state":"HIPOTESE","problem":"Verificar quantidades",
                    "observations":[{"statement":"Validação reportada","evidenceIds":["signal"],"limitation":"Sem dados do item"}],
                    "hypotheses":[],"missingEvidence":["Vínculos da nota"],
                    "nextChecks":[{"action":"Consultar vínculos","purpose":"Verificar o bloqueio","readOnly":true}]}
                    """;
            }
        };
        var reviewed=service.review(report());assertEquals("CONCLUIDA",reviewed.aiStatus());
        assertEquals("HIPOTESE",reviewed.state());assertEquals(report().proposedSolution(),reviewed.proposedSolution());
        assertNotNull(reviewed.advisory());
        service.model=new ExplanationModel(){public String plan(String s){return "";}public String explain(String s){return "{\"state\":\"CONFIRMADA\"}";}};
        var invalid=service.review(report());assertNull(invalid.advisory());assertEquals("INDISPONIVEL_OU_RESPOSTA_INVALIDA",invalid.aiStatus());
        assertEquals(report().summary(),invalid.summary());
    }
}
