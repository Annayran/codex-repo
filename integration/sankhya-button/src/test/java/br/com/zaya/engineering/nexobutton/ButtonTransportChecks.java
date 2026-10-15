package br.com.zaya.engineering.nexobutton;

import com.sun.net.httpserver.HttpServer;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class ButtonTransportChecks {
    public static void main(String[] args) throws Exception {
        final AtomicInteger calls = new AtomicInteger();
        final AtomicInteger responseStatus = new AtomicInteger(200);
        final AtomicInteger responseSize = new AtomicInteger(0);
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1",0),0);
        server.createContext("/investigations/packages", exchange -> {
            calls.incrementAndGet();
            if (!"receipt=true".equals(exchange.getRequestURI().getQuery())) throw new AssertionError("Small receipt expected");
            if (!"POST".equals(exchange.getRequestMethod()) || !exchange.getRequestHeaders().getFirst("Authorization").startsWith("Basic "))
                throw new AssertionError("Authenticated POST expected");
            ByteArrayOutputStream incoming = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096]; int length;
            try (InputStream body = exchange.getRequestBody()) { while ((length=body.read(buffer))!=-1) incoming.write(buffer,0,length); }
            String json = new String(incoming.toByteArray(),StandardCharsets.UTF_8);
            if (!json.contains("\"nunota\":123") || !json.contains("\"items\":[]")) throw new AssertionError("Package mismatch");
            if (responseStatus.get()==302) exchange.getResponseHeaders().add("Location","/credential-capture");
            String text = responseSize.get()==0 ? "{\"id\":\"00000000-0000-0000-0000-000000000001\",\"state\":\"INCONCLUSIVA\"}"
                    : new String(new char[responseSize.get()]).replace('\0','x');
            byte[] response = text.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(responseStatus.get(),response.length);
            try (OutputStream output = exchange.getResponseBody()) { output.write(response); }
        });
        server.createContext("/credential-capture",exchange -> { throw new AssertionError("Redirect must not be followed"); });
        server.start();
        try {
            URI url = URI.create("http://127.0.0.1:"+server.getAddress().getPort()+"/investigations/packages");
            NexoButtonClient client = NexoButtonClient.localFixture(url,"fixture-client","fixture-password");
            String packet = MinimalPackage.collect(123,"Error with \"quotes\", newline\n and backslash \\","unverified-test-version");
            if (!packet.contains("\\\"quotes\\\"") || !packet.contains("\\u000a") || !packet.contains("backslash \\\\")) throw new AssertionError("JSON escaping failed");
            if (!client.send(packet).contains("INCONCLUSIVA")) throw new AssertionError("Response missing");
            responseStatus.set(302);
            expect(IOException.class,() -> client.send(packet));
            if (calls.get()!=2) throw new AssertionError("Unexpected retry or redirect");
            responseStatus.set(401);
            expect(IOException.class,() -> client.send(packet));
            responseStatus.set(200); responseSize.set(270000);
            expect(IOException.class,() -> client.send(packet));
            expect(IllegalArgumentException.class,() -> client.send(new String(new char[270000])));
            expect(IllegalArgumentException.class,() -> new NexoButtonClient(url,"fixture-client","fixture-password",5000));
            expect(IllegalArgumentException.class,() -> NexoButtonClient.localFixture(URI.create("http://example.invalid/investigations/packages"),"x","x"));
            expect(IllegalArgumentException.class,() -> new NexoButtonClient(URI.create("https://example.invalid/investigations/packages"),"","x",5000));
            System.out.println("Button transport: authenticated POST, escaping, no redirects/retries, HTTP failure, payload/response bounds and HTTPS checks passed.");
        } finally { server.stop(0); }
    }
    private interface Operation { void run() throws Exception; }
    private static void expect(Class<? extends Exception> expected, Operation operation) throws Exception {
        try { operation.run(); } catch (Exception failure) {
            if (expected.isInstance(failure)) return;
            throw failure;
        }
        throw new AssertionError("Expected bounded failure: "+expected.getSimpleName());
    }
}
