package br.com.zaya.flow.contratos;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;

public class StartFlowContratosServicosEvent implements EventoProgramavelJava {

    private static final String LOG_PREFIX = "[FlowContratosServicos][StartProcess] ";

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
        tentarIniciarProcesso(event, "afterInsert");
    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        if (!algumCampoRelevanteAlterado(event)) {
            log("afterUpdate ignorado: nenhum campo relevante foi alterado.");
            return;
        }

        tentarIniciarProcesso(event, "afterUpdate");
    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    private void tentarIniciarProcesso(PersistenceEvent event, String origem) {
        try {
            DynamicVO cabVO = (DynamicVO) event.getVo();

            BigDecimal idNegocio = cabVO.asBigDecimal("AD_IDNEGOCIO");
            BigDecimal codParc = cabVO.asBigDecimal("CODPARC");
            BigDecimal nuNota = cabVO.asBigDecimal("NUNOTA");

            if (idNegocio == null || codParc == null || nuNota == null) {
                log("Processo nao iniciado em " + origem
                        + ": AD_IDNEGOCIO, CODPARC ou NUNOTA sem valor. AD_IDNEGOCIO=" + idNegocio
                        + ", CODPARC=" + codParc + ", NUNOTA=" + nuNota);
                return;
            }

            log("Iniciando processo. Origem=" + origem + ", AD_IDNEGOCIO=" + idNegocio + ", CODPARC=" + codParc
                    + ", NUNOTA=" + nuNota);

            BigDecimal idInstPrn = StartFlowContratosServicosHelperSupport.iniciarProcesso(
                    idNegocio,
                    codParc,
                    nuNota,
                    "StartProcessEvent");

            log("Processo iniciado com sucesso para NUNOTA=" + nuNota + ". IDINSTPRN=" + idInstPrn);
        } catch (Exception e) {
            log("Erro ao iniciar processo de contratos/servicos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean algumCampoRelevanteAlterado(PersistenceEvent event) {
        try {
            return event.getModifingFields() != null
                    && (event.getModifingFields().isModifing("AD_IDNEGOCIO")
                            || event.getModifingFields().isModifing("CODPARC")
                            || event.getModifingFields().isModifing("NUNOTA"));
        } catch (Exception e) {
            log("Nao foi possivel validar campos alterados no afterUpdate: " + e.getMessage());
            return false;
        }
    }

    private void log(String msg) {
        System.out.println(LOG_PREFIX + msg);
    }
}
