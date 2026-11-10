package br.com.zaya.apiboletos.sankhya;

import static br.com.zaya.apiboletos.persistence.Sql.*;

import br.com.zaya.apiboletos.application.RuntimeModule;
import br.com.zaya.apiboletos.domain.Models;
import br.com.zaya.apiboletos.domain.Models.*;
import br.com.zaya.apiboletos.persistence.*;
import java.sql.*;
import java.util.*;

/** Entrada em TX atual: uma falha de infraestrutura propaga e desfaz título e intenção juntos. */
public final class BillingEvents {
  public static void title(long fin) throws Exception {
    if (!RuntimeModule.enabled()) return;
    SankhyaDatabase.current(
        c -> {
          java.util.List<java.util.Map<String, Object>> headers =
              rows(c, 1, "SELECT CODEMP,CODCTABCOINT,CODTIPTIT FROM TGFFIN WHERE NUFIN=?", fin);
          if (headers.isEmpty()) return null;
          java.util.Map<String, Object> header = headers.get(0);
          for (Config cfg : RuntimeModule.REPOSITORY.configs(c)) {
            if (!cfg.active
                || !cfg.sendEnabled
                || cfg.company != number(header, "CODEMP")
                || cfg.account != number(header, "CODCTABCOINT")
                || !cfg.titleTypes.contains(number(header, "CODTIPTIT"))) continue;
            Title t;
            try {
              t = NativeFinancial.load(c, fin);
              t.validate(cfg, false);
            } catch (IllegalArgumentException expected) {
              return null;
            }
            RuntimeModule.ENGINE.enqueue(c, cfg, t, false, 0);
          }
          return null;
        });
  }

  public static void note(long note) throws Exception {
    if (!RuntimeModule.enabled()) return;
    List<Long> ids =
        SankhyaDatabase.current(
            c -> {
              List<Long> out = new ArrayList<Long>();
              for (Map<String, Object> r :
                  rows(c, 1000, "SELECT NUFIN FROM TGFFIN WHERE NUNOTA=? ORDER BY NUFIN", note))
                out.add(number(r, "NUFIN"));
              return out;
            });
    for (long id : ids) title(id);
  }

  public static void changed(long fin) throws Exception {
    if (!RuntimeModule.enabled()) return;
    SankhyaDatabase.current(
        c -> {
          Charge ch = RuntimeModule.REPOSITORY.latest(c, fin);
          if (ch == null) {
            title(fin);
            return null;
          }
          if (ch.state != ChargeState.REGISTRADA) return null;
          Title current = NativeFinancial.load(c, fin);
          if (!current.dueDate.equals(ch.snapshot.dueDate))
            enqueueChange(c, ch, Command.VENCIMENTO, "data_vencimento", current.dueDate);
          if (current.amount.compareTo(ch.snapshot.amount) != 0)
            enqueueChange(c, ch, Command.VALOR, "valor_nominal", current.amount.toPlainString());
          return null;
        });
  }

  private static void enqueueChange(
      Connection c, Charge ch, Command command, String field, String value) throws Exception {
    for (Map<String, Object> row :
        rows(
            c,
            100,
            "SELECT JSONDATA FROM ZYCB_OPER WHERE IDCOB=? AND TIPO=? AND STATUS IN"
                + " ('PENDENTE','PROCESSANDO','AGUARDANDO','INCERTO')",
            ch.id,
            command.name())) {
      Operation existing = Json.read(text(row, "JSONDATA"), Operation.class);
      if (value.equals(existing.values.get(field))) return;
    }
    Operation op = new Operation();
    op.id = Models.id();
    op.chargeId = ch.id;
    op.command = command;
    op.values.put(field, value);
    op.key = "ALTERACAO:" + ch.id + ":" + op.id;
    RuntimeModule.REPOSITORY.enqueue(c, op);
  }

  public static void cancel(long fin) throws Exception {
    if (!RuntimeModule.enabled()) return;
    SankhyaDatabase.current(
        c -> {
          Charge ch = RuntimeModule.REPOSITORY.latest(c, fin);
          if (ch == null || ch.state == ChargeState.CANCELADA) return null;
          List<Map<String, Object>> pending =
              rows(
                  c,
                  1,
                  "SELECT ID,DHENVIO FROM ZYCB_OPER WHERE IDCOB=? AND TIPO='REGISTRAR'",
                  ch.id);
          if (!pending.isEmpty() && pending.get(0).get("DHENVIO") == null) {
            int changed =
                update(
                    c,
                    "UPDATE ZYCB_OPER SET STATUS='REJEITADO',ERRO='DOCUMENTO_CANCELADO' WHERE ID=?"
                        + " AND STATUS='PENDENTE' AND DHENVIO IS NULL",
                    text(pending.get(0), "ID"));
            if (changed == 1) {
              ch.state = ChargeState.CANCELADA;
              RuntimeModule.REPOSITORY.saveCharge(c, ch);
              return null;
            }
          }
          Operation op = new Operation();
          op.id = Models.id();
          op.chargeId = ch.id;
          op.command = Command.CANCELAR;
          op.key = "CANCELAR_DOCUMENTO:" + ch.id;
          if (rows(c, 1, "SELECT ID FROM ZYCB_OPER WHERE CHAVE=?", op.key).isEmpty())
            RuntimeModule.REPOSITORY.enqueue(c, op);
          return null;
        });
  }

  public static void cancelNote(long note) throws Exception {
    List<Long> ids =
        SankhyaDatabase.current(
            c -> {
              List<Long> out = new ArrayList<Long>();
              for (Map<String, Object> r :
                  rows(c, 1000, "SELECT NUFIN FROM TGFFIN WHERE NUNOTA=?", note))
                out.add(number(r, "NUFIN"));
              return out;
            });
    for (long fin : ids) cancel(fin);
  }

  private BillingEvents() {}
}
