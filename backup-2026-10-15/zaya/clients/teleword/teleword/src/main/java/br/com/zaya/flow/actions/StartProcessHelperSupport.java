package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.workflow.model.helper.ListaTarefaHelper;
import br.com.sankhya.workflow.utils.WorkflowUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

final class StartProcessHelperSupport {

    private static final BigDecimal COD_PRN_PARCEIROS = BigDecimal.valueOf(4L);

    private StartProcessHelperSupport() {
    }

    static BigDecimal iniciarProcessoParceiro(BigDecimal codParc, String origemLog) throws Exception {
        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: CODPARC nao informado.");
        }

        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = null;

        try {
            jdbc = dwfFacade.getJdbcWrapper();

            String processKey = WorkflowUtils.getProcessKeyByProcessId(COD_PRN_PARCEIROS);
            JsonArray formFormatadoArray = buildFormFormatado(codParc);

            System.out.println("[Teleword][" + origemLog + "] Iniciando Flow via ListaTarefaHelper. codPrn="
                    + COD_PRN_PARCEIROS + ", processKey=" + processKey + ", codParc=" + codParc);
            System.out.println("[Teleword][" + origemLog + "] formFormatado=" + formFormatadoArray);

            BigDecimal idInstPrn = new ListaTarefaHelper().startProcess(
                    jdbc,
                    COD_PRN_PARCEIROS,
                    processKey,
                    null,
                    formFormatadoArray,
                    null,
                    null);

            System.out.println("[Teleword][" + origemLog + "] Flow iniciado com sucesso. IDINSTPRN=" + idInstPrn);
            return idInstPrn;
        } finally {
            JdbcWrapper.closeSession(jdbc);
        }
    }

    private static JsonArray buildFormFormatado(BigDecimal codParc) {
        JsonArray records = new JsonArray();

        JsonObject codParcRecord = new JsonObject();
        codParcRecord.addProperty("name", "CODPARC");
        codParcRecord.addProperty("value", codParc);
        records.add(codParcRecord);

        JsonObject recordsJSON = new JsonObject();
        recordsJSON.add("record", records);

        JsonArray recordsArray = new JsonArray();
        recordsArray.add(recordsJSON);

        JsonObject formFormatado = new JsonObject();
        formFormatado.addProperty("entityName", "AD_PARCPEND");
        formFormatado.addProperty("parentEntity", "-99999999");
        formFormatado.add("records", recordsArray);

        JsonArray formFormatadoArray = new JsonArray();
        formFormatadoArray.add(formFormatado);

        return formFormatadoArray;
    }
}
