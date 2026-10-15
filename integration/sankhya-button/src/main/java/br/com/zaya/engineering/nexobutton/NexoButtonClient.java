package br.com.zaya.engineering.nexobutton;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** Java 8 transport for a server-side action. Never use this credential in browser code. */
public final class NexoButtonClient {
    private static final int MAX_BYTES = 256 * 1024;
    private final URI endpoint;
    private final String authorization;
    private final int readTimeoutMs;

    public NexoButtonClient(URI endpoint, String principal, String password, int readTimeoutMs) {
        this(endpoint, principal, password, readTimeoutMs, false);
    }
    private NexoButtonClient(URI endpoint, String principal, String password, int timeout, boolean localFixture) {
        boolean loopback = endpoint != null && ("127.0.0.1".equals(endpoint.getHost()) || "localhost".equals(endpoint.getHost()));
        if (endpoint == null || endpoint.getHost() == null || endpoint.getUserInfo() != null
                || endpoint.getQuery() != null || endpoint.getFragment() != null
                || !endpoint.getPath().endsWith("/investigations/packages")
                || !("https".equals(endpoint.getScheme()) || localFixture && loopback && "http".equals(endpoint.getScheme()))
                || principal == null || principal.isEmpty() || principal.contains(":")
                || password == null || password.isEmpty() || timeout < 1000 || timeout > 60000)
            throw new IllegalArgumentException("Trusted HTTPS endpoint and server-side credentials required");
        this.endpoint = endpoint;
        this.authorization = "Basic " + Base64.getEncoder().encodeToString((principal+":"+password).getBytes(StandardCharsets.UTF_8));
        this.readTimeoutMs = timeout;
    }
    public static NexoButtonClient localFixture(URI endpoint, String principal, String password) {
        return new NexoButtonClient(endpoint, principal, password, 5000, true);
    }
    public String send(String packageJson) throws IOException {
        if (packageJson == null) throw new IllegalArgumentException("Evidence package required");
        byte[] payload = packageJson.getBytes(StandardCharsets.UTF_8);
        if (payload.length == 0 || payload.length > MAX_BYTES) throw new IllegalArgumentException("Package exceeds 256 KiB");
        // Keep the action response small; the complete diagnosis is retrieved by authorized support via GET.
        HttpURLConnection connection = (HttpURLConnection) URI.create(endpoint.toString()+"?receipt=true").toURL().openConnection();
        try {
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(readTimeoutMs);
            connection.setRequestProperty("Authorization", authorization);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(payload.length);
            try (OutputStream output = connection.getOutputStream()) { output.write(payload); }
            int status = connection.getResponseCode();
            if (status != 200) throw new IOException("Nexo returned HTTP " + status + "; investigate delivery before retrying");
            try (InputStream input = connection.getInputStream(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int count;
                while ((count = input.read(buffer)) != -1) {
                    if (output.size() + count > MAX_BYTES) throw new IOException("Nexo response exceeds limit");
                    output.write(buffer, 0, count);
                }
                return new String(output.toByteArray(), StandardCharsets.UTF_8);
            }
        } finally { connection.disconnect(); }
    }
}
