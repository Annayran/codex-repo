package br.com.zaya.engineering.nexo.monitoring;

import br.com.zaya.engineering.nexo.investigation.InvestigationService;
import br.com.zaya.engineering.nexo.monitoring.MonitorContracts.*;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/monitoring") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("investigator")
public class MonitorResource {
    @Inject MonitorService monitor;
    @Inject InvestigationService investigations;
    @Inject SecurityIdentity identity;
    @Inject IssueAiReview ai;
    private String actor(){return identity.getPrincipal().getName();}
    public record ManualReport(String eventId,java.time.Instant occurredAt,String error) {}
    @POST @Path("/manual") public EventReceipt manual(ManualReport report) {
        if(report==null||report.error()==null||report.error().isBlank()||report.error().length()>2000)
            throw new BadRequestException("Relato de até 2000 caracteres obrigatório");
        String text=java.text.Normalizer.normalize(report.error(),java.text.Normalizer.Form.NFD).replaceAll("\\p{M}","").toLowerCase(java.util.Locale.ROOT);
        String category=text.contains("sem familia")?"PRODUCT_FAMILY_MISSING":text.contains("quantidade entregue")?"DELIVERED_QTY":
                text.contains("combinacao global")?"NO_GLOBAL_COMBINATION":text.contains("particionamento ativo")?"NO_ACTIVE_PARTITION":
                text.contains("estoque insuficiente")?"INSUFFICIENT_STOCK":text.contains("inativo")||text.contains("nao esta ativo")?"PRODUCT_INACTIVE":"UNCLASSIFIED";
        return receive(new FailureEvent(report.eventId(),report.eventId(),"skala.relato.manual","NAO_VERIFICADA",category,
                "ManualReportedFailure",report.occurredAt(),Impact.UNKNOWN,Origin.HISTORICAL,null,report.error()));
    }
    @POST @Path("/events") public EventReceipt receive(FailureEvent event){return monitor.receive(investigations.scope(actor()),event);}
    @GET @Path("/issues") public List<Issue> list(@QueryParam("offset") @DefaultValue("0") int offset){return monitor.list(investigations.scope(actor()),offset);}
    @GET @Path("/issues/{id}") public Issue find(@PathParam("id") String id){return monitor.find(investigations.scope(actor()),id);}
    @GET @Path("/issues/{id}/analysis") public IssueAnalysis.Report analysis(@PathParam("id") String id){return IssueAnalysis.analyze(find(id));}
    @POST @Path("/issues/{id}/analysis/review") public IssueAnalysis.Report review(@PathParam("id") String id){return ai.review(analysis(id));}
    @PUT @Path("/issues/{id}") public Issue triage(@PathParam("id")String id,Triage update){return monitor.triage(investigations.scope(actor()),id,actor(),update);}
}
