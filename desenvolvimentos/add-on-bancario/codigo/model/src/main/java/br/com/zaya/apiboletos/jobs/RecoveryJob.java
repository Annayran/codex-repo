package br.com.zaya.apiboletos.jobs;

import br.com.sankhya.studio.annotations.Job;
import br.com.sankhya.studio.annotations.enums.EJBTransactionType;
import br.com.sankhya.studio.stereotypes.IJob;
import br.com.zaya.apiboletos.application.RuntimeModule;
import br.com.zaya.apiboletos.domain.Models.Config;
import br.com.zaya.apiboletos.persistence.*;
import br.com.zaya.apiboletos.sankhya.BillingEvents;
import java.util.*;

@Job(
    serviceName = "ZycbRecoveryJobSP",
    frequency = "&300000",
    transactionType = EJBTransactionType.NotSupported)
public final class RecoveryJob extends IJob {
  public void onSchedule() {
    if (!RuntimeModule.enabled()) return;
    try {
      for (Config cfg : RuntimeModule.DB.transaction(c -> RuntimeModule.REPOSITORY.configs(c))) {
        if (!cfg.active || !cfg.sendEnabled) continue;
        RuntimeModule.DB.transaction(
            c -> {
              List<Map<String, Object>> state =
                  Sql.rows(c, 1, "SELECT CURSORFIN FROM ZYCB_SCAN WHERE IDCFG=?", cfg.id);
              long old = state.isEmpty() ? cfg.cutover : Sql.number(state.get(0), "CURSORFIN");
              if (state.isEmpty())
                Sql.update(c, "INSERT INTO ZYCB_SCAN (IDCFG,CURSORFIN) VALUES (?,?)", cfg.id, old);
              List<Map<String, Object>> rows =
                  Sql.rows(
                      c,
                      cfg.batchSize,
                      "SELECT F.NUFIN FROM TGFFIN F WHERE F.CODEMP=? AND F.CODCTABCOINT=? AND"
                          + " F.NUFIN>? AND F.DHBAIXA IS NULL AND NOT EXISTS (SELECT 1 FROM"
                          + " ZYCB_COB B WHERE B.NUFIN=F.NUFIN) ORDER BY F.NUFIN",
                      cfg.company,
                      cfg.account,
                      old);
              long next = cfg.cutover;
              for (Map<String, Object> row : rows) {
                next = Sql.number(row, "NUFIN");
                BillingEvents.title(next);
              }
              if (Sql.update(
                      c,
                      "UPDATE ZYCB_SCAN SET CURSORFIN=? WHERE IDCFG=? AND CURSORFIN=?",
                      next,
                      cfg.id,
                      old)
                  != 1) throw new IllegalStateException("RECUPERACAO_CONCORRENTE");
              return null;
            });
      }
    } catch (Exception e) {
      throw new IllegalStateException("ZYCB_JOB_RECUPERACAO_FALHOU");
    }
  }
}
