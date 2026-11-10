package br.com.zaya.teleworld.flow;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.zaya.teleworld.flow.controller.LiberacaoProcessoController;
import br.com.zaya.teleworld.flow.model.service.LiberacaoProcessoService;
import br.com.zaya.teleworld.flow.model.service.RotinaLog;
import br.com.zaya.teleworld.flow.sankhya.services.FlowSinalizador;

/** Evento de atualizacao de CabecalhoNota (TGFCAB). */
public class SinalLiberacaoProcesso implements EventoProgramavelJava {
    private final LiberacaoProcessoController controller = new LiberacaoProcessoController(
            new LiberacaoProcessoService(new FlowSinalizador()));

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        RotinaLog.executar("SinalLiberacaoProcesso.afterUpdate", () -> controller.atualizar(event));
    }
    @Override
    public void afterInsert(PersistenceEvent event) throws Exception { }
    @Override
    public void afterDelete(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeCommit(TransactionContext context) throws Exception { }
}


