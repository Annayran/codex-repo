package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.workflow.model.services.ListaTarefaSP;
import br.com.sankhya.workflow.model.services.ListaTarefaSPHome;
import br.com.sankhya.ws.ServiceContext;
import br.com.sankhya.ws.transformer.json.Json2XMLParser;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import static br.com.sankhya.dwf.services.ServiceUtils.getStatelessFacade;

public class StartProcess implements EventoProgramavelJava {

    private static final BigDecimal COD_PRN_PARCEIROS = BigDecimal.valueOf(4L);
    private static final String STATUS_PENDENTE = "P";

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        DynamicVO parceiroVO = (DynamicVO) event.getVo();

        if (!STATUS_PENDENTE.equals(getString(parceiroVO.getProperty("AD_REVISADO")))) {
            return;
        }

        if (!alterouParaPendente(event)) {
            return;
        }

        BigDecimal codParc = parceiroVO.asBigDecimal("CODPARC");
        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: CODPARC nao informado.");
        }

        iniciarProcessoParceiros(codParc);
    }

    private boolean alterouParaPendente(PersistenceEvent event) {
        if (!(event.getOldVO() instanceof DynamicVO)) {
            return true;
        }

        DynamicVO oldVO = (DynamicVO) event.getOldVO();
        return !STATUS_PENDENTE.equals(getString(oldVO.getProperty("AD_REVISADO")));
    }

    private void iniciarProcessoParceiros(BigDecimal codParc) throws Exception {
        ListaTarefaSP listaTarefaSP = (ListaTarefaSP) getStatelessFacade(ListaTarefaSPHome.JNDI_NAME,
                ListaTarefaSPHome.class);

        AuthenticationInfo auth = new AuthenticationInfo("SUP", BigDecimal.ZERO, BigDecimal.ZERO, 0);
        auth.makeCurrent();

        ServiceContext sctx = new ServiceContext(null);
        sctx.setAutentication(auth);
        sctx.makeCurrent();

        JapeSessionContext.putProperty("usuario_logado", auth.getUserID());
        JapeSession.putProperty("usuario_logado", auth.getUserID());

        String params = String.format(
                "{\n" +
                        "    \"serviceName\": \"ListaTarefaSP.startProcess\",\n" +
                        "    \"status\": \"4\",\n" +
                        "    \"pendingPrinting\": \"false\",\n" +
                        "    \"transactionId\": \"499B5C93C3EF90E40EBD23BD7B6B2DA9\",\n" +
                        "    \"param\": {\n" +
                        "        \"codPrn\": %s,\n" +
                        "        \"formulario\": {\n" +
                        "            \"personalizado\": [\n" +
                        "                {\n" +
                        "                    \"entityName\": \"AD_PARCPEND\",\n" +
                        "                    \"records\": [\n" +
                        "                        {\n" +
                        "                            \"CODPARC\": %s\n" +
                        "                        }\n" +
                        "                    ],\n" +
                        "                    \"configFields\": [\n" +
                        "                        {\n" +
                        "                            \"nome\": \"CODPARC\",\n" +
                        "                            \"nomeAba\": \"Geral\",\n" +
                        "                            \"visivel\": \"S\",\n" +
                        "                            \"somenteLeitura\": \"N\",\n" +
                        "                            \"obrigatorio\": \"N\",\n" +
                        "                            \"ordem\": \"1\",\n" +
                        "                            \"descricao\": \"Cód. Parceiro\"\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        },\n" +
                        "        \"telas\": null\n" +
                        "    },\n" +
                        "    \"clientEvents\": [\n" +
                        "        {\n" +
                        "            \"id\": \"br.com.sankhya.workflow.listatarefa.necessita.variavel.inicializacao\",\n" +
                        "            \"start\": {\n" +
                        "                \"temVariavelInicializacao\": \"S\",\n" +
                        "                \"forms\": {\n" +
                        "                    \"AD_PARCPEND\": {\n" +
                        "                        \"codRegistro\": \"%s\",\n" +
                        "                        \"entityName\": \"AD_PARCPEND\",\n" +
                        "                        \"description\": \"Parceiros Pendentes\",\n" +
                        "                        \"cardinality\": \"1\",\n" +
                        "                        \"scope\": \"P\",\n" +
                        "                        \"politicaReetrancia\": \"R\",\n" +
                        "                        \"tipo\": \"F\",\n" +
                        "                        \"ordemAbas\": \"Geral\",\n" +
                        "                        \"configuracaoCampos\": {\n" +
                        "                            \"members\": {\n" +
                        "                                \"properties\": {\n" +
                        "                                    \"nome\": \"CODPARC\",\n" +
                        "                                    \"nomeAba\": \"Geral\",\n" +
                        "                                    \"visivel\": \"S\",\n" +
                        "                                    \"somenteLeitura\": \"N\",\n" +
                        "                                    \"obrigatorio\": \"N\",\n" +
                        "                                    \"ordem\": \"1\",\n" +
                        "                                    \"descricao\": \"Cód. Parceiro\"\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}\n",
                COD_PRN_PARCEIROS.toPlainString(), codParc.toPlainString(), codParc.toPlainString());

        try {
            JsonObject param = JsonUtils.convertStringToJsonObject(params);
            sctx.setJsonRequestBody(param);
            sctx.setRequestBody(Json2XMLParser.jsonToElement("param", sctx.getJsonRequestBody()));
            listaTarefaSP.startProcess(sctx);
        } catch (Exception e) {
            throw new Exception("Erro ao iniciar o processo de parceiros: " + e.getMessage(), e);
        }
    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    private String getString(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString().trim().toUpperCase();
    }
}
