package br.com.zaya.mahospitalar.genesis.event;

import br.com.sankhya.jape.event.PersistenceEvent;

/** Register on TGFCAN, not on cancellation attempts/occurrences. */
public final class RegistroNotaCanceladaGenesisEvent extends CancelamentoNotaGenesisEvent {
    @Override public void beforeInsert(PersistenceEvent event) throws Exception { preparar(event); }
    @Override public void beforeDelete(PersistenceEvent event) { }
}
