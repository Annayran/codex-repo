package br.com.zaya.apiboletos.banks;

import br.com.zaya.apiboletos.domain.Models.Credentials;
import br.com.zaya.apiboletos.security.TlsMaterial;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;

/** PATCH nativo; sem reflexão, overrides de método ou configuração SSL global. */
public final class ApacheTransport implements HttpTransport {
  public Response exchange(
      String method, URI uri, Map<String, String> headers, String body, Credentials credentials)
      throws Exception {
    if (!"https".equalsIgnoreCase(uri.getScheme())
        || uri.getUserInfo() != null
        || uri.getPort() != -1) throw new SecurityException("URL_BANCARIA_INVALIDA");
    RequestConfig limits =
        RequestConfig.custom()
            .setConnectTimeout(15000)
            .setSocketTimeout(30000)
            .setConnectionRequestTimeout(15000)
            .build();
    try (CloseableHttpClient client =
        HttpClients.custom()
            .setSSLContext(TlsMaterial.context(credentials))
            .setDefaultRequestConfig(limits)
            .disableAutomaticRetries()
            .disableRedirectHandling()
            .build()) {
      HttpRequestBase request;
      if ("GET".equals(method)) request = new HttpGet(uri);
      else if ("POST".equals(method)) request = new HttpPost(uri);
      else if ("PATCH".equals(method)) request = new HttpPatch(uri);
      else throw new IllegalArgumentException("METODO_NAO_PERMITIDO");
      for (Map.Entry<String, String> h : headers.entrySet())
        request.setHeader(h.getKey(), h.getValue());
      if (body != null)
        ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(body, "UTF-8"));
      try (CloseableHttpResponse response = client.execute(request)) {
        Map<String, String> out = new LinkedHashMap<String, String>();
        for (Header h : response.getAllHeaders()) out.put(h.getName(), h.getValue());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        if (response.getEntity() != null)
          try (InputStream in = response.getEntity().getContent()) {
            byte[] chunk = new byte[8192];
            int n;
            while ((n = in.read(chunk)) != -1) {
              if (buffer.size() + n > 4194304) throw new IOException("RESPOSTA_EXCEDE_LIMITE");
              buffer.write(chunk, 0, n);
            }
          }
        return new Response(
            response.getStatusLine().getStatusCode(),
            new String(buffer.toByteArray(), StandardCharsets.UTF_8),
            out);
      }
    }
  }
}
