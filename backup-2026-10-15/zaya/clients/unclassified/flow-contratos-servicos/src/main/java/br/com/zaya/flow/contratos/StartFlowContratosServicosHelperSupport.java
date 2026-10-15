package br.com.zaya.flow.contratos;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.workflow.model.helper.ListaTarefaHelper;
import br.com.sankhya.workflow.utils.WorkflowUtils;

final class StartFlowContratosServicosHelperSupport {

    private static final BigDecimal COD_PRN_CONTRATOS_SERVICOS = BigDecimal.valueOf(8L);
    private static final String PROCESS_ENTITY = "PROCESS_8_VERSION_44";

    private StartFlowContratosServicosHelperSupport() {
    }

    static BigDecimal iniciarProcesso(BigDecimal idNegocio, BigDecimal codParc, BigDecimal nuNota, String origemLog)
            throws Exception {
        if (idNegocio == null || codParc == null || nuNota == null) {
            throw new Exception("Nao foi possivel iniciar o Flow Contratos / Servicos: AD_IDNEGOCIO, CODPARC ou NUNOTA nao informado.");
        }

        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = null;

        try {
            jdbc = dwfFacade.getJdbcWrapper();

            String processKey = WorkflowUtils.getProcessKeyByProcessId(COD_PRN_CONTRATOS_SERVICOS);
            JsonArray formEmbarcadoArray = buildFormEmbarcado(idNegocio, codParc, nuNota);

            System.out.println("[FlowContratosServicos][" + origemLog + "] Iniciando Flow via ListaTarefaHelper. codPrn="
                    + COD_PRN_CONTRATOS_SERVICOS + ", processKey=" + processKey + ", AD_IDNEGOCIO=" + idNegocio
                    + ", CODPARC=" + codParc + ", NUNOTA=" + nuNota);
            System.out.println("[FlowContratosServicos][" + origemLog + "] formEmbarcado=" + formEmbarcadoArray);

            BigDecimal idInstPrn = new ListaTarefaHelper().startProcess(
                    jdbc,
                    COD_PRN_CONTRATOS_SERVICOS,
                    processKey,
                    formEmbarcadoArray,
                    null,
                    null,
                    null);

            System.out.println("[FlowContratosServicos][" + origemLog + "] Flow iniciado com sucesso. IDINSTPRN="
                    + idInstPrn);
            return idInstPrn;
        } finally {
            JdbcWrapper.closeSession(jdbc);
        }
    }

    private static JsonArray buildFormEmbarcado(BigDecimal idNegocio, BigDecimal codParc, BigDecimal nuNota) {
        JsonArray record = new JsonArray();
        record.add(buildCampo("ID_NEGOCIO", idNegocio));
        record.add(buildCampo("CODPARC", codParc));
        record.add(buildCampo("NUNOTA", nuNota));

        JsonObject recordsJSON = new JsonObject();
        recordsJSON.add("record", record);
        recordsJSON.add("detalhes", new JsonArray());

        JsonArray recordsArray = new JsonArray();
        recordsArray.add(recordsJSON);

        JsonObject formEmbarcado = new JsonObject();
        formEmbarcado.addProperty("entityName", PROCESS_ENTITY);
        formEmbarcado.add("records", recordsArray);
        formEmbarcado.add("configFields", new JsonArray());

        JsonArray formEmbarcadoArray = new JsonArray();
        formEmbarcadoArray.add(formEmbarcado);

        return formEmbarcadoArray;
    }

    private static JsonObject buildCampo(String nome, BigDecimal valor) {
        JsonObject campo = new JsonObject();
        campo.addProperty("name", nome);
        campo.addProperty("value", valor);
        return campo;
    }
}
