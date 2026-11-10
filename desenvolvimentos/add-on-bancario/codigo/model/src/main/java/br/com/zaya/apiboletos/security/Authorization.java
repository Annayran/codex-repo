package br.com.zaya.apiboletos.security;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.zaya.apiboletos.persistence.Sql;
import br.com.zaya.apiboletos.sankhya.SankhyaDatabase;
import java.util.*;

public final class Authorization {
  public static long user() {
    AuthenticationInfo a = AuthenticationInfo.getCurrentOrNull();
    if (a == null) throw new SecurityException("SESSAO_NAO_AUTENTICADA");
    return a.getUserID().longValueExact();
  }

  public static long require(long company, String level) throws Exception {
    long user = user();
    if (user == 0) return user;
    String role =
        SankhyaDatabase.current(
            c -> {
              List<Map<String, Object>> r =
                  Sql.rows(
                      c,
                      1,
                      "SELECT PERFIL FROM ZYCB_ACESSO WHERE CODUSU=? AND CODEMP=?",
                      user,
                      company);
              return r.isEmpty() ? null : Sql.text(r.get(0), "PERFIL");
            });
    if (role == null
        || ("ADMIN".equals(level) && !"ADMIN".equals(role))
        || ("OPERADOR".equals(level) && "CONSULTA".equals(role)))
      throw new SecurityException("ACESSO_NEGADO");
    return user;
  }

  private Authorization() {}
}
