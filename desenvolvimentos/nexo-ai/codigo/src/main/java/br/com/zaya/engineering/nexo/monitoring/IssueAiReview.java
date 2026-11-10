package br.com.zaya.engineering.nexo.monitoring;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.State;
import br.com.zaya.engineering.nexo.investigation.AdvisoryReview;
import br.com.zaya.engineering.nexo.investigation.ExplanationModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.*;

@ApplicationScoped
public class IssueAiReview {
    @Inject ExplanationModel model;
    @Inject ObjectMapper mapper;
    @ConfigProperty(name="nexo.ai.enabled") boolean enabled;

    public IssueAnalysis.Report review(IssueAnalysis.Report report) {
        if(!enabled)return result(report,"DESATIVADA",null);
        try {
            // No raw log, note IDs, component names or client data leave this service.
            var findings=new ArrayList<Map<String,String>>(); var ids=new HashSet<String>();
            findings.add(Map.of("id","signal","finding",report.summary()));ids.add("signal");
            String input=mapper.writeValueAsString(Map.of("state",report.state(),"findings",findings,
                    "missingEvidence",List.of("Sem dados de cadastro, itens, versão implantada ou correlação da transação."),
                    "nextChecks",report.nextChecks()));
            String validated=AdvisoryReview.validate(mapper,model.explain(input),State.valueOf(report.state()),ids);
            return result(report,"CONCLUIDA",mapper.readTree(validated));
        } catch(Exception unavailable) {return result(report,"INDISPONIVEL_OU_RESPOSTA_INVALIDA",null);}
    }
    private IssueAnalysis.Report result(IssueAnalysis.Report r,String status,Object advisory) {
        return new IssueAnalysis.Report(r.state(),r.title(),r.summary(),r.evidence(),r.hypotheses(),r.missingEvidence(),
                r.nextChecks(),r.proposedSolution(),r.validationPlan(),status,advisory);
    }
}
