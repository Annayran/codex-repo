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
    private String actor(){return identity.getPrincipal().getName();}
    @POST @Path("/events") public EventReceipt receive(FailureEvent event){return monitor.receive(investigations.scope(actor()),event);}
    @GET @Path("/issues") public List<Issue> list(@QueryParam("offset") @DefaultValue("0") int offset){return monitor.list(investigations.scope(actor()),offset);}
    @GET @Path("/issues/{id}") public Issue find(@PathParam("id") String id){return monitor.find(investigations.scope(actor()),id);}
    @PUT @Path("/issues/{id}") public Issue triage(@PathParam("id")String id,Triage update){return monitor.triage(investigations.scope(actor()),id,actor(),update);}
}
