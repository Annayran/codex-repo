package br.com.zaya.apiboletos.sankhya;

import br.com.sankhya.jape.event.*;
import br.com.sankhya.studio.annotations.Listener;

/** SQL interno é controlado pelos serviços; CRUD genérico não pode corromper fila/configuração. */
@Listener(
    instanceNames = {
      "ZycbConfiguracao",
      "ZycbCobranca",
      "ZycbOperacao",
      "ZycbRecebimento",
      "ZycbAuditoria"
    })
public final class ReadOnlyEntities extends PersistenceEventAdapter {
  public void beforeInsert(PersistenceEvent e) {
    throw new SecurityException("USE_SERVICOS_ZYCB");
  }

  public void beforeUpdate(PersistenceEvent e) {
    throw new SecurityException("USE_SERVICOS_ZYCB");
  }

  public void beforeDelete(PersistenceEvent e) {
    throw new SecurityException("HISTORICO_ZYCB_IMUTAVEL");
  }
}
