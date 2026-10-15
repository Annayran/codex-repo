package br.com.zaya.engineering.nexo;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;
import br.com.zaya.engineering.nexo.sankhya.SankhyaGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PackageIntakeTest {
    @Test void filteredLogIsPersistedDownloadableAndIsolated() {
        var original=packet(1);
        var log=new LogExcerpt("FOUND","TEXT_MATCH_UNVERIFIED",1234,false,
                "ORA-20101: Produto 19391 nao esta ativo synthetic@example.invalid\npassword=SYNTHETIC-CREDENTIAL\n at demo.Trigger.run(Trigger.java:437)");
        var upload=new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),
                original.collectorVersion(),true,original.items(),original.evidence(),original.missing(),log);
        String id=request("demo-a").body(upload).post("/investigations/packages?receipt=true").then().statusCode(200).extract().path("id");
        request("demo-a").get("/investigations/"+id+"/log").then().statusCode(200)
                .header("Content-Disposition",containsString(id+".log")).header("X-Content-Type-Options",is("nosniff"))
                .body(containsString("ORA-20101"),containsString("TEXT_MATCH_UNVERIFIED"),
                        not(containsString("synthetic@example.invalid")),not(containsString("SYNTHETIC-CREDENTIAL")));
        request("demo-b").get("/investigations/"+id+"/log").then().statusCode(404);
        given().get("/investigations/"+id+"/log").then().statusCode(401);
        var noMatch=new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),
                original.collectorVersion(),true,original.items(),original.evidence(),original.missing(),
                new LogExcerpt("NOT_FOUND","TEXT_MATCH_UNVERIFIED",1234,false,""));
        String missingId=request("demo-a").body(noMatch).post("/investigations/packages?receipt=true").then().statusCode(200).extract().path("id");
        request("demo-a").get("/investigations/"+missingId+"/log").then().statusCode(404);
        var invalid=new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),
                original.collectorVersion(),true,original.items(),original.evidence(),original.missing(),
                new LogExcerpt("FOUND","CONFIRMED",1,false,"example"));
        request("demo-a").body(invalid).post("/investigations/packages").then().statusCode(400);
    }
    @Test void productStatusAndPhysicalStockArePreservedWithoutInventingAvailability() {
        var original=packet(1);
        var item=new Item("19391","1","10",null,"UN","UN",null,null,BigDecimal.TEN,null,false,null,
                false,List.of(new StockRow("1","10",null,"0","P",new BigDecimal("3"),new BigDecimal("2"))));
        var upload=new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),
                "note-product-stock-v1",true,List.of(new CollectedItem(item,List.of("item-1"))),original.evidence(),List.of());
        String id=request("demo-a").body(upload).post("/investigations/packages").then().statusCode(200)
                .body("state",is("HIPOTESE"),"divergences.kind",hasItem("PRODUTO_INATIVO"))
                .extract().path("id");
        request("demo-a").get("/investigations/"+id+"/context").then().statusCode(200)
                .body("items[0].productActive",is(false),"items[0].stockRows[0].physical",is(3),
                        "items[0].stockRows[0].reserved",is(2),"items[0].availability",nullValue());
        var mismatch=new Item("19391","1","10",null,"UN","UN",null,null,BigDecimal.TEN,null,false,null,
                true,List.of(new StockRow("2","10",null,"0","P",BigDecimal.ONE,BigDecimal.ZERO)));
        var invalid=new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),
                "note-product-stock-v1",true,List.of(new CollectedItem(mismatch,List.of("item-1"))),original.evidence(),List.of());
        request("demo-a").body(invalid).post("/investigations/packages").then().statusCode(400);
    }
    @Inject ObjectMapper mapper;
    @Inject SankhyaGateway gateway;
    private io.restassured.specification.RequestSpecification request(String user) {
        return given().auth().preemptive().basic(user,user.equals("demo-a") ? "fixture-only-a" : "fixture-only-b")
                .contentType("application/json");
    }
    private EvidencePackage packet(int scenario) {
        var snapshot = gateway.readConfirmationContext(new Scope("demo-a","fixture"), 1000+scenario,100,1000);
        return new EvidencePackage(1,987654,"CONFIRMAR_NOTA","Synthetic exact error",Instant.now(),"unverified-demo-version","fixture-button-v1",true,
                List.of(new CollectedItem(snapshot.items().getFirst(),List.of("item-1"))),
                List.of(new CollectedEvidence("item-1","fixture://button/item/1","1",snapshot.evidence().getLast().observation())),snapshot.missing());
    }
    @Test void uploadedSixScenariosDoNotNeedErpLookup() {
        for (int scenario=1; scenario<=6; scenario++) request("demo-a").body(packet(scenario)).post("/investigations/packages")
                .then().statusCode(200).body("state",is(scenario==6 ? "INCONCLUSIVA" : "HIPOTESE"),"rootCause",nullValue(),
                        "tools.tool",hasItem("receiveEvidencePackage"),"tools.tool",not(hasItem("readConfirmationContext")),
                        "evidence.tenant",everyItem(is("demo-a")));
    }
    @Test void clientCannotSupplyScopeCauseOrApproval() throws Exception {
        var json = mapper.valueToTree(packet(1));
        var object = (com.fasterxml.jackson.databind.node.ObjectNode) json;
        object.put("tenant","demo-b"); object.put("environment","production"); object.put("rootCause","Invented cause");
        object.put("state","CONFIRMADA"); object.put("simulated",false);
        var item = (com.fasterxml.jackson.databind.node.ObjectNode) object.path("items").get(0).path("item");
        item.put("ruleReference","unapproved-policy"); item.put("ruleValidated",true);
        request("demo-a").body(mapper.writeValueAsString(object)).post("/investigations/packages").then().statusCode(200)
                .body("state",is("INCONCLUSIVA"),"rootCause",nullValue(),"simulated",is(true),"evidence.tenant",everyItem(is("demo-a")),
                        "missing",hasItem(containsString("not approved on server")));
    }
    @Test void packagePersistenceUsesAuthenticatedScope() {
        String id = request("demo-a").body(packet(1)).post("/investigations/packages").then().statusCode(200).extract().path("id");
        request("demo-a").get("/investigations/"+id).then().statusCode(200);
        request("demo-a").get("/investigations/"+id+"/context").then().statusCode(200)
                .body("nunota",is(987654),"items[0].required",is(10),"items[0].availability",is(1));
        request("demo-b").get("/investigations/"+id).then().statusCode(404);
        request("demo-b").get("/investigations/"+id+"/context").then().statusCode(404);
        // Same note number in different customers is legitimate; credentials determine scope.
        request("demo-b").body(packet(1)).post("/investigations/packages").then().statusCode(200).body("evidence.tenant",everyItem(is("demo-b")));
    }
    @Test void incompletePackageDoesNotInventAvailability() {
        var original = packet(1);
        var partial = new Item("P","E",null,null,"UN",null,null,null,BigDecimal.TEN,null,true,"fixture-rule-v1");
        var upload = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),original.collectorVersion(),true,
                List.of(new CollectedItem(partial,List.of("item-1"))),original.evidence(),List.of());
        request("demo-a").body(upload).post("/investigations/packages").then().statusCode(200)
                .body("state",is("INCONCLUSIVA"),"missing",hasItem(containsString("Incomplete stock dimensions")));
        var minimal = new EvidencePackage(1,987654,"CONFIRMAR_NOTA","error",Instant.now(),"unknown","minimal-v1",false,List.of(),List.of(),List.of());
        request("demo-a").body(minimal).post("/investigations/packages").then().statusCode(200).body("state",is("INCONCLUSIVA"));
    }
    @Test void invalidPackagesAndReferencesAreRejected() throws Exception {
        var original = packet(1);
        var json = (com.fasterxml.jackson.databind.node.ObjectNode) mapper.valueToTree(original);
        json.put("schemaVersion",2);
        request("demo-a").body(json.toString()).post("/investigations/packages").then().statusCode(400);
        json.put("schemaVersion",1); json.put("collectedAt",Instant.now().minusSeconds(90000).toString());
        request("demo-a").body(json.toString()).post("/investigations/packages").then().statusCode(400);
        json.put("collectedAt",Instant.now().plusSeconds(120).toString());
        request("demo-a").body(json.toString()).post("/investigations/packages").then().statusCode(400);
        var unknownReference = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),original.collectorVersion(),true,
                List.of(new CollectedItem(original.items().getFirst().item(),List.of("missing-reference"))),original.evidence(),List.of());
        request("demo-a").body(unknownReference).post("/investigations/packages").then().statusCode(400);
        var tooMany = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),original.collectorVersion(),true,
                Collections.nCopies(101,original.items().getFirst()),original.evidence(),List.of());
        request("demo-a").body(tooMany).post("/investigations/packages").then().statusCode(400);
        given().contentType("application/json").body(original).post("/investigations/packages").then().statusCode(401);
    }
    @Test void itemFindingsReferenceOnlyTheirOwnEvidence() {
        var original = packet(1);
        var normal = new Item("P2","E","L","L","UN","UN","","",BigDecimal.ONE,BigDecimal.TEN,true,"fixture-rule-v1");
        var upload = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),original.collectorVersion(),true,
                List.of(original.items().getFirst(),new CollectedItem(normal,List.of("item-2"))),
                List.of(original.evidence().getFirst(),new CollectedEvidence("item-2","fixture://item/2","1","Second normal item")),List.of());
        request("demo-a").body(upload).post("/investigations/packages").then().statusCode(200)
                .body("divergences.size()",is(1),"divergences[0].evidenceIds",contains("item-1"));
    }
    @Test void buttonCanReceiveOnlyTheInvestigationReceipt() {
        String id = request("demo-a").body(packet(1)).post("/investigations/packages?receipt=true").then().statusCode(200)
                .body("id",notNullValue(),"state",is("HIPOTESE"),"simulated",is(true),"evidence",nullValue()).extract().path("id");
        request("demo-a").get("/investigations/"+id).then().statusCode(200).body("evidence",not(empty()));
    }
    @Test void policyApprovalDoesNotTransferToAnotherErpOrCollectorVersion() {
        var original = packet(1);
        var upload = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),"different-version",original.collectorVersion(),true,
                original.items(),original.evidence(),List.of());
        request("demo-a").body(upload).post("/investigations/packages").then().statusCode(200).body("state",is("INCONCLUSIVA"));
        var anotherCollector = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),"different-collector",true,
                original.items(),original.evidence(),List.of());
        request("demo-a").body(anotherCollector).post("/investigations/packages").then().statusCode(200).body("state",is("INCONCLUSIVA"));
    }
    @Test void blankStockDimensionsAreUnknownRatherThanEqual() {
        var original = packet(1);
        var item = new Item("P","E","","","UN","UN","","",BigDecimal.TEN,BigDecimal.ONE,true,"fixture-rule-v1");
        var upload = new EvidencePackage(1,original.nunota(),original.action(),original.error(),Instant.now(),original.erpVersion(),original.collectorVersion(),true,
                List.of(new CollectedItem(item,List.of("item-1"))),original.evidence(),List.of());
        request("demo-a").body(upload).post("/investigations/packages").then().statusCode(200)
                .body("state",is("INCONCLUSIVA"),"missing",hasItem(containsString("Incomplete stock dimensions")));
    }
}
