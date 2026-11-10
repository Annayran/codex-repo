package br.com.zaya.engineering.nexo;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import br.com.zaya.engineering.nexo.knowledge.KnowledgeStore;
import br.com.zaya.engineering.nexo.sankhya.FixtureGateway;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@QuarkusTest
class InvestigationTest {
    private io.restassured.specification.RequestSpecification request(String user) {
        return given().auth().preemptive().basic(user, user.equals("demo-a") ? "fixture-only-a" : "fixture-only-b")
                .contentType("application/json");
    }
    @Test void sixScenariosNeverInventOrigin() {
        for (int i=1; i<=6; i++) {
            request("demo-a").body(new Request(1000+i, "CONFIRMAR_NOTA", "Synthetic stock error"))
                    .post("/investigations").then().statusCode(200)
                    .body("simulated", is(true), "rootCause", nullValue(), "state", is(i==6 ? "INCONCLUSIVA" : "HIPOTESE"));
        }
    }
    @Test void expectedDivergences() {
        List<String> kinds = List.of("DISPONIBILIDADE", "LOCAL", "UNIDADE", "CONTROLE", "DISPONIBILIDADE");
        for (int i=1; i<=5; i++) request("demo-a").body(new Request(1000+i,"CONFIRMAR_NOTA","error"))
                .post("/investigations").then().statusCode(200).body("divergences[0].kind",is(kinds.get(i-1)));
    }
    @Test void authenticatedTenantIsolatesNotesAndPersistence() {
        String id = request("demo-a").body(new Request(1001,"CONFIRMAR_NOTA","error"))
                .post("/investigations").then().statusCode(200).extract().path("id");
        request("demo-a").get("/investigations/"+id).then().statusCode(200);
        request("demo-b").get("/investigations/"+id).then().statusCode(404);
        request("demo-b").body(new Request(1001,"CONFIRMAR_NOTA","error")).post("/investigations").then().statusCode(404);
        request("demo-b").body("{\"nunota\":1001,\"action\":\"CONFIRMAR_NOTA\",\"error\":\"error\",\"tenant\":\"demo-a\",\"environment\":\"production\"}")
                .post("/investigations").then().statusCode(404);
        request("demo-b").body(new Request(2001,"CONFIRMAR_NOTA","error")).post("/investigations").then().statusCode(200);
    }
    @Test void authenticationAndInputLimits() {
        given().get("/investigations/capabilities").then().statusCode(401);
        request("demo-a").get("/investigations/capabilities").then().statusCode(200).body("syntheticTesting",is(true));
        given().contentType("application/json").body(new Request(1001,"CONFIRMAR_NOTA","error")).post("/investigations").then().statusCode(401);
        request("demo-a").body(new Request(1001,"DELETE","error")).post("/investigations").then().statusCode(400);
        request("demo-a").body(new Request(1001,"CONFIRMAR_NOTA","x".repeat(2001))).post("/investigations").then().statusCode(400);
        request("demo-a").body(new Request(Long.MAX_VALUE,"CONFIRMAR_NOTA","error")).post("/investigations").then().statusCode(404);
        request("demo-a").body(new Request(1001,"CONFIRMAR_NOTA","x".repeat(270000))).post("/investigations").then().statusCode(413);
    }
    @Test void executionBudgetAndKnowledgeIsolation() {
        var gateway = new FixtureGateway();
        var scope = new Scope("demo-a","fixture");
        assertThrows(IllegalArgumentException.class, () -> gateway.readConfirmationContext(scope,1001,0,100));
        assertThrows(IllegalArgumentException.class, () -> gateway.readConfirmationContext(scope,1001,100,0));
        assertThrows(SecurityException.class, () -> gateway.readConfirmationContext(new Scope("demo-a","production"),1001,100,100));
        var store = new KnowledgeStore();
        store.importReviewed(new KnowledgeStore.Document(new Evidence("doc","fixture://doc","v1","confirmation-stock","demo-a",true,"reference"),"fixture","untrusted content"));
        assertEquals(1,store.retrieve(scope,"confirmation-stock").size());
        assertTrue(store.retrieve(new Scope("demo-b","fixture"),"confirmation-stock").isEmpty());
        assertTrue(store.retrieve(new Scope("demo-a","production"),"confirmation-stock").isEmpty());
    }
    @Test void reviewedManifestIsRetrievedOnlyForItsScope() {
        request("demo-a").body(new Request(1005,"CONFIRMAR_NOTA","error")).post("/investigations").then()
                .statusCode(200).body("evidence.id",hasItem("synthetic-policy-document"), "tools.tool",hasItem("retrieveReviewedKnowledge"));
        request("demo-b").body(new Request(2005,"CONFIRMAR_NOTA","error")).post("/investigations").then()
                .statusCode(200).body("evidence.id",not(hasItem("synthetic-policy-document")));
    }
}
