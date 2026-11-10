package br.com.zaya.apiboletos.sankhya;

import br.com.sankhya.jape.event.*;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.studio.annotations.Listener;

@Listener(instanceNames = {"Financeiro"})
public final class FinancialListener extends PersistenceEventAdapter {
  public void beforeUpdate(PersistenceEvent e) throws Exception {
    if (!br.com.zaya.apiboletos.application.RuntimeModule.enabled()
        || !e.getModifingFields().isModifingAny("CODEMP,CODCTABCOINT,CODPARC")) return;
    long id = ((DynamicVO) e.getVo()).asBigDecimal("NUFIN").longValue();
    br.com.zaya.apiboletos.domain.Models.Charge ch =
        SankhyaDatabase.current(
            c -> br.com.zaya.apiboletos.application.RuntimeModule.REPOSITORY.latest(c, id));
    if (ch != null && ch.state != br.com.zaya.apiboletos.domain.Models.ChargeState.CANCELADA)
      throw new IllegalStateException("CANCELE_COBRANCA_ANTES_DE_ALTERAR_IDENTIFICACAO");
  }

  public void afterInsert(PersistenceEvent e) throws Exception {
    BillingEvents.title(((DynamicVO) e.getVo()).asBigDecimal("NUFIN").longValue());
  }

  public void afterUpdate(PersistenceEvent e) throws Exception {
    long id = ((DynamicVO) e.getVo()).asBigDecimal("NUFIN").longValue();
    if (e.getModifingFields().isModifingAny("NURENEG")) BillingEvents.cancel(id);
    else if (e.getModifingFields().isModifingAny("DTVENC,VLRDESDOB,VLRDESC"))
      BillingEvents.changed(id);
    else if (e.getModifingFields().isModifingAny("PROVISAO,RECDESP,CODTIPTIT,CODEMP,CODCTABCOINT"))
      BillingEvents.title(id);
  }

  public void beforeDelete(PersistenceEvent e) throws Exception {
    BillingEvents.cancel(((DynamicVO) e.getVo()).asBigDecimal("NUFIN").longValue());
  }
}
