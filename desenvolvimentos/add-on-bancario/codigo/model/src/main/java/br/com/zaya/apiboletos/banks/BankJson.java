package br.com.zaya.apiboletos.banks;

import com.google.gson.*;
import java.math.BigDecimal;

public final class BankJson {
  public static JsonElement parse(String s) {
    return s == null || s.trim().isEmpty() ? new JsonObject() : new JsonParser().parse(s);
  }

  public static JsonObject object(JsonElement e) {
    return e != null && e.isJsonObject() ? e.getAsJsonObject() : new JsonObject();
  }

  public static JsonObject unwrap(JsonElement v) {
    JsonObject o = object(v);
    for (int i = 0; i < 3 && o.has("data") && o.get("data").isJsonObject(); i++)
      o = o.getAsJsonObject("data");
    return o;
  }

  public static String text(JsonObject o, String k) {
    return o.has(k) && o.get(k).isJsonPrimitive() ? o.get(k).getAsString() : null;
  }

  public static BigDecimal money(JsonObject o, String k) {
    String s = text(o, k);
    if (s == null) return BigDecimal.ZERO;
    if (!s.matches("-?[0-9]+(\\.[0-9]{1,2})?"))
      throw new IllegalArgumentException("FORMATO_MONETARIO_BANCARIO_INVALIDO");
    return new BigDecimal(s);
  }

  public static JsonArray array(JsonObject o, String k) {
    return o.has(k) && o.get(k).isJsonArray() ? o.getAsJsonArray(k) : new JsonArray();
  }

  public static JsonObject obj(Object... p) {
    JsonObject o = new JsonObject();
    for (int i = 0; i < p.length; i += 2) {
      Object v = p[i + 1];
      if (v instanceof Number) o.addProperty((String) p[i], (Number) v);
      else if (v instanceof Boolean) o.addProperty((String) p[i], (Boolean) v);
      else if (v instanceof JsonElement) o.add((String) p[i], (JsonElement) v);
      else o.addProperty((String) p[i], v == null ? null : String.valueOf(v));
    }
    return o;
  }

  private BankJson() {}
}
