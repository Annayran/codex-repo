package ProdutoSerie.events;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;

public class Evento implements EventoProgramavelJava {
    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    public void afterInsert(PersistenceEvent event) throws Exception {
    }

    public void afterUpdate(PersistenceEvent event) throws Exception {
    }

    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    public void beforeCommit(TransactionContext ctx) throws Exception {
    }
}
