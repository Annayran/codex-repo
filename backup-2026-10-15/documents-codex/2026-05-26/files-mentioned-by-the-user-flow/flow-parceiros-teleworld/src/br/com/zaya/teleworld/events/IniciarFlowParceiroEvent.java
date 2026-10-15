package br.com.zaya.teleworld.events;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.workflow.model.engine.DefaultProcessEngineService;
import br.com.sankhya.workflow.model.engine.ProcessEngineServices;

public class IniciarFlowParceiroEvent implements EventoProgramavelJava {

    private static final String PROCESS_KEY_PARCEIROS = "flow_parceiros";
    private static final String STATUS_PENDENTE = "P";

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
        DynamicVO parceiroVO = (DynamicVO) event.getVo();

        if (isEmpty(parceiroVO.asString("AD_REVISADO"))) {
            parceiroVO.setProperty("AD_REVISADO", STATUS_PENDENTE);
        }
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
        DynamicVO parceiroVO = (DynamicVO) event.getVo();
        BigDecimal codParc = parceiroVO.asBigDecimal("CODPARC");

        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            return;
        }

        Map<String, Object> variaveis = new HashMap<String, Object>();
        variaveis.put("CODPARC", codParc);
        variaveis.put("AD_REVISADO", STATUS_PENDENTE);

        ProcessEngineServices processEngine = DefaultProcessEngineService.getInstance();
        processEngine.startProcessByKey(PROCESS_KEY_PARCEIROS, variaveis);
    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}
