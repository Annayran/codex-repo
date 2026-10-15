package br.com.zaya.engineering.nexo;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;
import java.net.*;
import java.net.http.*;
import java.nio.file.Path;
import java.time.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class CollectorIngressTest {
    @Test void forwardsOnlyAuthenticatedEventsAndExpiresWithoutStoppingBackend()throws Exception {
        var calls=new AtomicInteger();
        HttpServer backend=HttpServer.create(new InetSocketAddress("127.0.0.1",0),4);
        backend.createContext("/monitoring/events",e->{
            assertEquals("Basic backend-fixture",e.getRequestHeaders().getFirst("Authorization"));
            assertNull(e.getRequestHeaders().getFirst("Cookie"));
            calls.incrementAndGet();e.getRequestBody().readAllBytes();
            byte[] bytes="{\"issueId\":\"6a391fb1-4c36-4d56-9b82-58c469af256b\"}".getBytes(java.nio.charset.StandardCharsets.UTF_8);
            e.sendResponseHeaders(200,bytes.length);e.getResponseBody().write(bytes);e.close();
        });backend.start();
        int port;try(var socket=new java.net.ServerSocket(0)){port=socket.getLocalPort();}
        var builder=new ProcessBuilder(Path.of(System.getProperty("java.home"),"bin","java.exe").toString(),
                "-Djdk.net.unixdomain.tmpdir="+Path.of("target").toAbsolutePath(),"-cp","target/classes","br.com.zaya.engineering.nexo.monitoring.CollectorIngress",String.valueOf(port),String.valueOf(backend.getAddress().getPort()));
        builder.environment().put("NEXO_INGRESS_FRONT_AUTH","Basic front-fixture");
        builder.environment().put("NEXO_INGRESS_BACK_AUTH","Basic backend-fixture");
        builder.environment().put("NEXO_INGRESS_EXPIRY",OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(12).toString());
        builder.redirectOutput(ProcessBuilder.Redirect.DISCARD).redirectError(ProcessBuilder.Redirect.DISCARD);
        Process process=builder.start();
        HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(1)).build();
        URI base=URI.create("http://127.0.0.1:"+port);
        try {
            boolean ready=false;
            for(int i=0;i<30;i++){try{var r=client.send(HttpRequest.newBuilder(base.resolve("/health")).header("Authorization","Basic front-fixture").GET().build(),HttpResponse.BodyHandlers.ofString());if(r.statusCode()==200){ready=true;break;}}catch(java.io.IOException ignored){}Thread.sleep(100);}
            assertTrue(ready);
            assertEquals(401,client.send(HttpRequest.newBuilder(base.resolve("/health")).GET().build(),HttpResponse.BodyHandlers.ofString()).statusCode());
            assertEquals(404,client.send(HttpRequest.newBuilder(base.resolve("/monitoring/issues")).header("Authorization","Basic front-fixture").GET().build(),HttpResponse.BodyHandlers.ofString()).statusCode());
            var response=client.send(HttpRequest.newBuilder(base.resolve("/monitoring/events")).header("Authorization","Basic front-fixture")
                    .header("Cookie","must-not-forward").POST(HttpRequest.BodyPublishers.ofString("{}" )).build(),HttpResponse.BodyHandlers.ofString());
            assertEquals(200,response.statusCode());assertTrue(response.body().contains("issueId"));
            assertEquals(413,client.send(HttpRequest.newBuilder(base.resolve("/monitoring/events")).header("Authorization","Basic front-fixture")
                    .POST(HttpRequest.BodyPublishers.ofString("x".repeat(8193))).build(),HttpResponse.BodyHandlers.ofString()).statusCode());
            assertEquals(1,calls.get());
            assertTrue(process.waitFor(20,TimeUnit.SECONDS),"Ingress must expire by itself");
            assertEquals(1,calls.get());
            assertEquals(404,client.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:"+backend.getAddress().getPort()+"/health")).GET().build(),HttpResponse.BodyHandlers.ofString()).statusCode(),"Backend remains available after ingress expiry");
        }finally{process.destroyForcibly();backend.stop(0);}
    }
}
