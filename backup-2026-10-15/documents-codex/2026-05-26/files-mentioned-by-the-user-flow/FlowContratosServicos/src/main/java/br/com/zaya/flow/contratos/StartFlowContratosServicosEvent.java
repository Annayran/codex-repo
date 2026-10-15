package br.com.zaya.flow.contratos;

import java.math.BigDecimal;

import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.ws.ServiceContext;
import br.com.sankhya.ws.transformer.json.Json2XMLParser;
import br.com.sankhya.workflow.model.services.ListaTarefaSP;
import br.com.sankhya.workflow.model.services.ListaTarefaSPHome;

import static br.com.sankhya.dwf.services.ServiceUtils.getStatelessFacade;

public class StartFlowContratosServicosEvent implements EventoProgramavelJava {

    private static final BigDecimal COD_PRN_CONTRATOS_SERVICOS = BigDecimal.valueOf(8L);
    private static final String PROCESS_ENTITY = "PROCESS_8_VERSION_44";
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

            log("Iniciando processo. Origem=" + origem + ", codPrn=" + COD_PRN_CONTRATOS_SERVICOS
                    + ", AD_IDNEGOCIO=" + idNegocio + ", CODPARC=" + codParc + ", NUNOTA=" + nuNota);

            iniciarProcessoContratosServicos(idNegocio, codParc, nuNota);

            log("Processo iniciado com sucesso para NUNOTA=" + nuNota + ".");
        } catch (Exception e) {
            log("Erro ao iniciar processo de contratos/servicos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void iniciarProcessoContratosServicos(BigDecimal idNegocio, BigDecimal codParc, BigDecimal nuNota)
            throws Exception {
        String json = montarJsonStartProcess(idNegocio, codParc, nuNota);

        log("JSON startProcess montado para NUNOTA=" + nuNota + ".");

        JsonObject requestBody = JsonUtils.convertStringToJsonObject(json);
        log("JSON convertido para JsonObject.");

        ServiceContext sctx = new ServiceContext(null);
        sctx.makeCurrent();
        sctx.setJsonRequestBody(requestBody);
        sctx.setRequestBody(Json2XMLParser.jsonToElement("param", sctx.getJsonRequestBody()));

        log("RequestBody XML configurado. Chamando ListaTarefaSP.startProcess...");

        ListaTarefaSP listaTarefaSP = (ListaTarefaSP) getStatelessFacade(ListaTarefaSPHome.JNDI_NAME,
                ListaTarefaSPHome.class);
        listaTarefaSP.startProcess(sctx);
    }

    private String montarJsonStartProcess(BigDecimal idNegocio, BigDecimal codParc, BigDecimal nuNota) {
        return String.format(
                "{\n" +
                        "  \"codPrn\": %s,\n" +
                        "  \"formulario\": {\n" +
                        "    \"embarcado\": [\n" +
                        "      {\n" +
                        "        \"entityName\": \"%s\",\n" +
                        "        \"records\": [\n" +
                        "          {\n" +
                        "            \"record\": [\n" +
                        "              {\"name\": \"ID_NEGOCIO\", \"value\": %s},\n" +
                        "              {\"name\": \"CODPARC\", \"value\": %s},\n" +
                        "              {\"name\": \"NUNOTA\", \"value\": %s}\n" +
                        "            ],\n" +
                        "            \"detalhes\": []\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"configFields\": []\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"telas\": null\n" +
                        "}\n",
                COD_PRN_CONTRATOS_SERVICOS.toPlainString(), PROCESS_ENTITY, idNegocio.toPlainString(),
                codParc.toPlainString(), nuNota.toPlainString());
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
