package br.com.zaya.apiboletos.persistence;

import java.sql.*;
import java.util.*;

public final class Sql {
  public static int update(Connection c, String sql, Object... p) throws SQLException {
    try (PreparedStatement s = c.prepareStatement(sql)) {
      bind(s, p);
      return s.executeUpdate();
    }
  }

  public static List<Map<String, Object>> rows(Connection c, int limit, String sql, Object... p)
      throws SQLException {
    try (PreparedStatement s = c.prepareStatement(sql)) {
      bind(s, p);
      s.setMaxRows(limit);
      try (ResultSet r = s.executeQuery()) {
        List<Map<String, Object>> out = new ArrayList<Map<String, Object>>();
        ResultSetMetaData m = r.getMetaData();
        while (r.next()) {
          Map<String, Object> row = new LinkedHashMap<String, Object>();
          for (int i = 1; i <= m.getColumnCount(); i++) {
            Object v = r.getObject(i);
            if (v instanceof Clob) v = r.getString(i);
            row.put(m.getColumnLabel(i).toUpperCase(Locale.ROOT), v);
          }
          out.add(row);
        }
        return out;
      }
    }
  }

  public static void bind(PreparedStatement s, Object... p) throws SQLException {
    for (int i = 0; i < p.length; i++) s.setObject(i + 1, p[i]);
  }

  public static String text(Map<String, Object> r, String k) {
    Object v = r.get(k);
    return v == null ? null : String.valueOf(v);
  }

  public static long number(Map<String, Object> r, String k) {
    Object v = r.get(k);
    return v == null ? 0 : ((Number) v).longValue();
  }

  private Sql() {}
}
