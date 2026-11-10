package br.com.zaya.apiboletos.sankhya;

import br.com.sankhya.jape.event.*;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.studio.annotations.Listener;

@Listener(instanceNames = {"CabecalhoNota"})
public final class NoteListener extends PersistenceEventAdapter {
  public void afterUpdate(PersistenceEvent e) throws Exception {
    if (!e.getModifingFields().isModifingAny("STATUSNOTA,STATUSNFE,STATUSNFSE,CODTIPOPER")) return;
    DynamicVO note = (DynamicVO) e.getVo();
    long id = note.asBigDecimal("NUNOTA").longValue();
    if ("L".equals(note.asString("STATUSNOTA"))) BillingEvents.note(id);
    else if (e.getModifingFields().isModifingAny("STATUSNOTA")) BillingEvents.cancelNote(id);
  }

  public void beforeDelete(PersistenceEvent e) throws Exception {
    BillingEvents.cancelNote(((DynamicVO) e.getVo()).asBigDecimal("NUNOTA").longValue());
  }
}
