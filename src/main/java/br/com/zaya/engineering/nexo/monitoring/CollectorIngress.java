package br.com.zaya.engineering.nexo.monitoring;

import com.sun.net.httpserver.HttpServer;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.*;
import java.util.concurrent.*;

/** Temporary narrow ingress: collector credential can only post events, never read or triage OS. */
public final class CollectorIngress {
    public static void main(String[] args) throws Exception {
        int port=Integer.parseInt(args[0]);
        int backendPort=Integer.parseInt(args[1]);
        String front=required("NEXO_INGRESS_FRONT_AUTH"),back=required("NEXO_INGRESS_BACK_AUTH");
        Instant expiry=OffsetDateTime.parse(required("NEXO_INGRESS_EXPIRY")).toInstant();
        if(!expiry.isAfter(Instant.now()))throw new IllegalStateException("Ingress identity expired");
        System.setProperty("sun.net.httpserver.maxReqTime","10");
        System.setProperty("sun.net.httpserver.maxRspTime","10");
        HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).followRedirects(HttpClient.Redirect.NEVER).build();
        HttpServer server=HttpServer.create(new InetSocketAddress(InetAddress.getByName("127.0.0.1"),port),8);
        ExecutorService workers=new ThreadPoolExecutor(2,2,0,TimeUnit.SECONDS,new ArrayBlockingQueue<>(8),new ThreadPoolExecutor.AbortPolicy());
        server.setExecutor(workers);
        server.createContext("/",exchange->{
            try {
                if(!Instant.now().isBefore(expiry)){reply(exchange,403,"expired");return;}
                String auth=exchange.getRequestHeaders().getFirst("Authorization");
                if(auth==null||!MessageDigest.isEqual(front.getBytes(StandardCharsets.UTF_8),auth.getBytes(StandardCharsets.UTF_8))){reply(exchange,401,"unauthorized");return;}
                String path=exchange.getRequestURI().getPath();
                if(exchange.getRequestURI().getRawQuery()!=null){reply(exchange,404,"not_found");return;}
                if("/health".equals(path)&&"GET".equals(exchange.getRequestMethod())){reply(exchange,200,"ready");return;}
                if(!"/monitoring/events".equals(path)||!"POST".equals(exchange.getRequestMethod())){reply(exchange,404,"not_found");return;}
                String length=exchange.getRequestHeaders().getFirst("Content-Length");
                if(length==null){reply(exchange,411,"length_required");return;}
                long count=Long.parseLong(length);
                if(count<1||count>8192){reply(exchange,413,"too_large");return;}
                byte[] body=exchange.getRequestBody().readNBytes((int)count+1);
                if(body.length!=count){reply(exchange,400,"invalid_length");return;}
                HttpRequest request=HttpRequest.newBuilder(URI.create("http://127.0.0.1:"+backendPort+"/monitoring/events"))
                        .timeout(Duration.ofSeconds(5)).header("Authorization",back).header("Content-Type","application/json")
                        .POST(HttpRequest.BodyPublishers.ofByteArray(body)).build();
                HttpResponse<java.io.InputStream> response=client.send(request,HttpResponse.BodyHandlers.ofInputStream());
                byte[] receipt;
                try(var responseBody=response.body()) {
                    if(response.statusCode()!=200){reply(exchange,response.statusCode(),"receiver_rejected");return;}
                    receipt=responseBody.readNBytes(4097);
                }
                if(receipt.length>4096){reply(exchange,502,"invalid_receipt");return;}
                exchange.getResponseHeaders().set("Content-Type","application/json; charset=UTF-8");
                exchange.getResponseHeaders().set("Cache-Control","no-store");
                exchange.sendResponseHeaders(200,receipt.length);exchange.getResponseBody().write(receipt);
            } catch(Exception failure){try{reply(exchange,502,"receiver_unavailable");}catch(Exception ignored){}}
            finally{exchange.close();}
        });
        ScheduledExecutorService timer=Executors.newSingleThreadScheduledExecutor();
        timer.schedule(()->{server.stop(0);workers.shutdownNow();timer.shutdown();},Math.max(1,Duration.between(Instant.now(),expiry).toMillis()),TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{server.stop(0);workers.shutdownNow();timer.shutdownNow();}));
        server.start();System.out.println("Nexo collector ingress ready; narrow route; automatic expiry enabled");
    }
    private static String required(String name){String value=System.getenv(name);if(value==null||value.isBlank())throw new IllegalStateException("Missing ingress configuration");return value;}
    private static void reply(com.sun.net.httpserver.HttpExchange exchange,int status,String result)throws java.io.IOException {
        byte[] bytes=("{\"status\":\""+result+"\"}").getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type","application/json; charset=UTF-8");exchange.getResponseHeaders().set("Cache-Control","no-store");
        exchange.sendResponseHeaders(status,bytes.length);exchange.getResponseBody().write(bytes);
    }
}
