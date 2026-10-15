package br.com.zaya.engineering.nexo;

import br.com.zaya.engineering.nexo.monitoring.MonitorContracts.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class MonitoringTest {
    private io.restassured.specification.RequestSpecification request(String user) {
        return given().auth().preemptive().basic(user,user.equals("demo-a")?"fixture-only-a":"fixture-only-b").contentType("application/json");
    }
    private FailureEvent event(String component,Origin origin,Instant at) {
        return new FailureEvent(UUID.randomUUID().toString(),UUID.randomUUID().toString(),component,"test-v1",
                "NO_ACTIVE_PARTITION","java.lang.IllegalStateException",at,Impact.LOCAL_BLOCK,origin,null);
    }
    private String receive(FailureEvent event) {
        return request("demo-a").body(event).post("/monitoring/events").then().statusCode(200).extract().path("issueId");
    }
    @Test void deduplicatesDeliveryAndGroupsDistinctOccurrences() {
        var first=event("test."+UUID.randomUUID(),Origin.LIVE,Instant.now());String id=receive(first);
        request("demo-a").body(first).post("/monitoring/events").then().statusCode(200).body("duplicate",is(true));
        receive(event(first.component(),Origin.LIVE,Instant.now()));
        request("demo-a").get("/monitoring/issues/"+id).then().statusCode(200)
                .body("occurrences",is(2),"severity",is("MEDIA"),"severityReviewed",is(false),"status",is("ABERTA"));
        var altered=new FailureEvent(first.eventId(),first.executionId(),first.component(),"different-v2",first.category(),
                first.exceptionType(),first.occurredAt(),first.impact(),first.origin(),null);
        request("demo-a").body(altered).post("/monitoring/events").then().statusCode(409);
    }
    @Test void requiresAuthenticatedScopeAndActualErrorType() {
        var event=event("test."+UUID.randomUUID(),Origin.LIVE,Instant.now());String id=receive(event);
        given().contentType("application/json").body(event).post("/monitoring/events").then().statusCode(401);
        request("demo-b").get("/monitoring/issues/"+id).then().statusCode(404);
        var bad=new FailureEvent(event.eventId(),event.executionId(),event.component(),event.version(),"success",null,
                event.occurredAt(),Impact.RECOVERED,Origin.LIVE,null);
        request("demo-a").body(bad).post("/monitoring/events").then().statusCode(400);
        request("demo-a").body(event("bad.component",Origin.LIVE,Instant.now().minusSeconds(90000)))
                .post("/monitoring/events").then().statusCode(400);
    }
    @Test void criticalRequiresHumanReviewAndResolutionRequiresValidation() {
        String id=receive(event("test."+UUID.randomUUID(),Origin.LIVE,Instant.now()));
        var invalid=new Triage(Severity.CRITICA,Status.RESOLVIDA,"Responsável","Impacto revisado","Proposta"," ");
        request("demo-a").body(invalid).put("/monitoring/issues/"+id).then().statusCode(400);
        var update=new Triage(Severity.CRITICA,Status.RESOLVIDA,"Responsável","Impacto revisado","Proposta em homologação","Teste registrado");
        request("demo-a").body(update).put("/monitoring/issues/"+id).then().statusCode(200)
                .body("severity",is("CRITICA"),"severityReviewed",is(true),"audit.size()",is(2),"status",is("RESOLVIDA"));
        request("demo-b").body(update).put("/monitoring/issues/"+id).then().statusCode(404);
    }
    @Test void recurrenceReopensAndHistoricalImportDoesNot() {
        var event=event("test."+UUID.randomUUID(),Origin.LIVE,Instant.now());String id=receive(event);
        var update=new Triage(Severity.MEDIA,Status.RESOLVIDA,"Responsável","Verificado","Proposta","Resultado do teste");
        request("demo-a").body(update).put("/monitoring/issues/"+id).then().statusCode(200);
        receive(event(event.component(),Origin.HISTORICAL,Instant.now().minusSeconds(100000)));
        request("demo-a").get("/monitoring/issues/"+id).then().statusCode(200).body("status",is("RESOLVIDA"),"historicalOccurrences",is(1));
        receive(event(event.component(),Origin.LIVE,Instant.now().plusSeconds(2)));
        request("demo-a").get("/monitoring/issues/"+id).then().statusCode(200).body("status",is("ABERTA"),"validation",is(""),"audit.action",hasItem("REABERTA"));
    }
    @Test void frequencyUsesRecentLiveEventsNotOldHistory() {
        String component="test."+UUID.randomUUID();String id=null;
        for(int i=0;i<10;i++)id=receive(event(component,Origin.LIVE,Instant.now().minusSeconds(1800)));
        request("demo-a").get("/monitoring/issues/"+id).then().statusCode(200).body("severity",is("MEDIA"));
        for(int i=0;i<10;i++)receive(event(component,Origin.LIVE,Instant.now()));
        request("demo-a").get("/monitoring/issues/"+id).then().statusCode(200).body("severity",is("ALTA"),"severityReviewed",is(false));
    }
    @Test void logExcerptIsFilteredPersistedAndIdempotent() {
        var base=event("log."+UUID.randomUUID(),Origin.LIVE,Instant.now());
        var event=new FailureEvent(base.eventId(),base.executionId(),base.component(),base.version(),base.category(),base.exceptionType(),
                base.occurredAt(),base.impact(),base.origin(),null,"ERROR ORA-20101: falha\npassword=secret\nuser@example.org");
        String id=receive(event);
        request("demo-a").body(event).post("/monitoring/events").then().statusCode(200).body("duplicate",is(true));
        request("demo-a").get("/monitoring/issues/"+id).then().statusCode(200)
                .body("lastLogExcerpt",containsString("ORA-20101"),"lastLogExcerpt",not(containsString("secret")),
                        "lastLogExcerpt",not(containsString("user@example.org")),"occurrences",is(1));
        var update=new Triage(Severity.MEDIA,Status.EM_ANALISE,"Responsável","Análise",""," ");
        request("demo-a").body(update).put("/monitoring/issues/"+id).then().statusCode(200).body("lastLogExcerpt",containsString("ORA-20101"));
        var bad=new FailureEvent(UUID.randomUUID().toString(),base.executionId(),base.component(),base.version(),base.category(),base.exceptionType(),
                base.occurredAt(),base.impact(),base.origin(),null,"x".repeat(2401));
        request("demo-a").body(bad).post("/monitoring/events").then().statusCode(400);
    }
}
