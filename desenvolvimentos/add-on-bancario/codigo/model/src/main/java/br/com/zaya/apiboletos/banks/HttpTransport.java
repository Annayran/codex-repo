package br.com.zaya.apiboletos.banks;

import br.com.zaya.apiboletos.domain.Models.Credentials;
import java.net.URI;
import java.util.*;

public interface HttpTransport {
  final class Response {
    public final int status;
    public final String body;
    public final Map<String, String> headers;

    public Response(int status, String body, Map<String, String> headers) {
      this.status = status;
      this.body = body;
      this.headers = headers;
    }

    public String header(String name) {
      for (Map.Entry<String, String> h : headers.entrySet())
        if (h.getKey().equalsIgnoreCase(name)) return h.getValue();
      return null;
    }
  }

  Response exchange(
      String method, URI uri, Map<String, String> headers, String body, Credentials credentials)
      throws Exception;
}
