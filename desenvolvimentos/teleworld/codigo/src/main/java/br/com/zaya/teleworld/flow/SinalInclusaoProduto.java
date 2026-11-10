package br.com.zaya.teleworld.flow;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.teleworld.flow.controller.RevisaoProdutoController;
import br.com.zaya.teleworld.flow.model.service.RotinaLog;

/** Cadastrar exclusivamente em ItemNota (TGFITE). */
public class SinalInclusaoProduto implements EventoProgramavelJava {
    private final RevisaoProdutoController controller = new RevisaoProdutoController();

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
        RotinaLog.executar("SinalInclusaoProduto.afterInsert", () -> controller.incluir((DynamicVO) event.getVo()));
    }
    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception { }
    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception { }
    @Override
    public void afterDelete(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeCommit(TransactionContext context) throws Exception { }
}
