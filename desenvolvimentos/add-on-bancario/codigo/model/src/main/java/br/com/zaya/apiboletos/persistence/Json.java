package br.com.zaya.apiboletos.persistence;

import com.google.gson.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class Json {
  public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

  public static String write(Object v) {
    return GSON.toJson(v);
  }

  public static <T> T read(String s, Class<T> type) {
    return GSON.fromJson(s, type);
  }

  public static String hash(String s) {
    try {
      byte[] b = MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
      StringBuilder out = new StringBuilder();
      for (byte x : b) out.append(String.format("%02x", x & 255));
      return out.toString();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private Json() {}
}
