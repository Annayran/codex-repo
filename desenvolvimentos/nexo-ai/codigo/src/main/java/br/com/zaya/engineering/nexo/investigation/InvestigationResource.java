package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/investigations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("investigator")
public class InvestigationResource {
    @Inject SecurityIdentity identity;
    @Inject InvestigationService service;
    @GET @Path("/capabilities") public java.util.Map<String,Boolean> capabilities() {
        var scope=service.scope(identity.getPrincipal().getName());
        return java.util.Map.of("syntheticTesting", service.syntheticTesting(scope));
    }
    @POST public Diagnosis investigate(Request request) { return service.investigate(service.scope(identity.getPrincipal().getName()), request); }
    @POST @Path("/packages") public Response receive(EvidencePackage packet, @QueryParam("receipt") boolean receiptOnly) {
        var diagnosis = service.receive(service.scope(identity.getPrincipal().getName()), packet);
        return Response.ok(receiptOnly ? new Receipt(diagnosis.id(), diagnosis.state(), diagnosis.simulated()) : diagnosis).build();
    }
    @GET @Path("/{id}") public Diagnosis find(@PathParam("id") String id) { return service.find(service.scope(identity.getPrincipal().getName()), id); }
    @POST @Path("/{id}/review") public Diagnosis review(@PathParam("id") String id) { return service.review(service.scope(identity.getPrincipal().getName()), id); }
    @GET @Path("/{id}/analysis") public OperationalAnalysis.Report analysis(@PathParam("id") String id) {
        return service.analysis(service.scope(identity.getPrincipal().getName()),id);
    }
    @GET @Path("/{id}/log") @Produces("text/plain; charset=UTF-8")
    public Response log(@PathParam("id") String id) {
        String content=service.log(service.scope(identity.getPrincipal().getName()),id);
        return Response.ok(content).header("Content-Disposition","attachment; filename=\""+id+".log\"")
                .header("X-Content-Type-Options","nosniff").build();
    }
    @GET @Path("/{id}/context") public Snapshot context(@PathParam("id") String id) {
        return service.context(service.scope(identity.getPrincipal().getName()), id);
    }
}
