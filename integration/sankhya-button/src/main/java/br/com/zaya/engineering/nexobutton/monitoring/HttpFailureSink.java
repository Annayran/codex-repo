package br.com.zaya.engineering.nexobutton.monitoring;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ServiceLoader;
import java.io.OutputStream;

/** Hosting supplies a protected provider; no credentials in source, forms, logs, or ordinary JAR. */
public final class HttpFailureSink implements FailureTelemetry.Sink {
    public interface ProtectedConfiguration { Configuration load() throws Exception; }
    public static final class Configuration {
        public final String endpoint,principal,password,installationId;
        public Configuration(String endpoint,String principal,String password){this(endpoint,principal,password,null);}
        public Configuration(String endpoint,String principal,String password,String installationId){this.endpoint=endpoint;this.principal=principal;this.password=password;this.installationId=installationId;}
    }
    public void send(FailureTelemetry.Event event) throws Exception {
        Configuration config=loadConfiguration();
        if(config==null)return;
        deliver(event,config,500);
    }
    public static Configuration loadConfiguration() throws Exception {
        Configuration config=null;
        for(ProtectedConfiguration provider:ServiceLoader.load(ProtectedConfiguration.class,HttpFailureSink.class.getClassLoader())){config=provider.load();break;}
        return config;
    }
    public static void deliver(Object event,Configuration config,int timeout) throws Exception {
        URI uri=URI.create(config.endpoint);
        if(!"https".equals(uri.getScheme())||uri.getUserInfo()!=null||uri.getFragment()!=null||uri.getQuery()!=null
                ||!"/monitoring/events".equals(uri.getPath())||config.principal==null||config.password==null)throw new IllegalArgumentException("Invalid monitoring configuration");
        byte[] bytes=new com.google.gson.GsonBuilder().disableHtmlEscaping().create().toJson(event).getBytes(StandardCharsets.UTF_8);
        if(bytes.length>8192)throw new IllegalArgumentException("Monitoring payload too large");
        HttpURLConnection connection=(HttpURLConnection)uri.toURL().openConnection();
        try {
            connection.setInstanceFollowRedirects(false);connection.setConnectTimeout(timeout);connection.setReadTimeout(timeout);
            connection.setRequestMethod("POST");connection.setDoOutput(true);connection.setFixedLengthStreamingMode(bytes.length);
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization","Basic "+Base64.getEncoder().encodeToString((config.principal+":"+config.password).getBytes(StandardCharsets.UTF_8)));
            try(OutputStream stream=connection.getOutputStream()){stream.write(bytes);}
            int status=connection.getResponseCode();if(status<200||status>=300)throw new java.io.IOException("Monitoring delivery rejected");
            java.io.ByteArrayOutputStream response=new java.io.ByteArrayOutputStream();
            try(java.io.InputStream input=connection.getInputStream()) {
                byte[] buffer=new byte[512];int read;
                while((read=input.read(buffer))!=-1){if(response.size()+read>4096)throw new java.io.IOException("Oversized receipt");response.write(buffer,0,read);}
            }
            com.google.gson.JsonObject receipt=new Gson().fromJson(new String(response.toByteArray(),StandardCharsets.UTF_8),com.google.gson.JsonObject.class);
            if(receipt==null||!receipt.has("issueId"))throw new java.io.IOException("Invalid monitoring receipt");
            java.util.UUID.fromString(receipt.get("issueId").getAsString());
        } finally {connection.disconnect();}
    }
}
