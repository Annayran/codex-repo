package br.com.zaya.teleworld.fornecedorxml.sankhya.events;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.teleworld.fornecedorxml.model.service.FornecedorXmlQueueService;

public class FornecedorXmlEvent implements EventoProgramavelJava {
    private final FornecedorXmlQueueService service = new FornecedorXmlQueueService();

    @Override
    public void afterInsert(PersistenceEvent event) {
        avaliar(event);
    }

    @Override
    public void afterUpdate(PersistenceEvent event) {
        if (event.getModifingFields() == null
                || event.getModifingFields().isModifing("CODPARC")
                || event.getModifingFields().isModifing("CNPJPARC")
                || event.getModifingFields().isModifing("STATUS")
                || event.getModifingFields().isModifing("IMPORTADOMDE")
                || event.getModifingFields().isModifing("XML")) {
            avaliar(event);
        }
    }

    private void avaliar(PersistenceEvent event) {
        try {
            service.avaliar((DynamicVO) event.getVo());
        } catch (Exception erro) {
            System.out.println("[Teleworld][FornecedorXmlEvent] Falha ao registrar a fila; "
                    + "a importacao sera preservada: " + erro.getMessage());
            erro.printStackTrace();
        }
    }

    @Override public void beforeInsert(PersistenceEvent event) { }
    @Override public void beforeUpdate(PersistenceEvent event) { }
    @Override public void beforeDelete(PersistenceEvent event) { }
    @Override public void afterDelete(PersistenceEvent event) { }
    @Override public void beforeCommit(TransactionContext tranCtx) { }
}
