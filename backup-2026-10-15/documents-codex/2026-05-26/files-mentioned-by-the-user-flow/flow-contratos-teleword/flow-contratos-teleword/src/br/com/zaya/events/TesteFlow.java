package br.com.zaya.events;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.workflow.api.SankhyaFlow;

public class TesteFlow implements EventoProgramavelJava{

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {
        
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
        
    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        SankhyaFlow.sendSignal("baixouTitulo");
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beforeCommit'");
    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beforeDelete'");
    }

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beforeInsert'");
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beforeUpdate'");
    }
    
}
