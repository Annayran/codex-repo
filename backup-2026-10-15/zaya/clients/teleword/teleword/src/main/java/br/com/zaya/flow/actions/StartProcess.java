package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.workflow.model.services.ListaTarefaSP;
import br.com.sankhya.workflow.model.services.ListaTarefaSPHome;
import br.com.sankhya.ws.ServiceContext;
import br.com.sankhya.ws.transformer.json.Json2XMLParser;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import static br.com.sankhya.dwf.services.ServiceUtils.getStatelessFacade;

public class StartProcess implements EventoProgramavelJava {

    private static final String STATUS_PROCESSO = "4";
    private static final String STATUS_PENDENTE = "P";
    private static final String TRANSACTION_ID = "17A45093C93DC17F00C67E973BC104F8";
    private static final String TX_PROPERTY_PREFIX = "teleword.startProcess.parceiro.";

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        System.out.println("[Teleword][StartProcess] afterUpdate TGFPAR iniciado.");

        DynamicVO parceiroVO = (DynamicVO) event.getVo();
        String revisadoAtual = getString(parceiroVO.getProperty("AD_REVISADO"));
        System.out.println("[Teleword][StartProcess] AD_REVISADO atual=" + revisadoAtual);

        if (!STATUS_PENDENTE.equals(revisadoAtual)) {
            System.out.println("[Teleword][StartProcess] Fluxo nao iniciado: AD_REVISADO atual nao esta como P.");
            return;
        }

        BigDecimal codParc = parceiroVO.asBigDecimal("CODPARC");
        System.out.println("[Teleword][StartProcess] CODPARC identificado=" + codParc);

        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            System.out.println("[Teleword][StartProcess] Erro: CODPARC nao informado.");
            throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: CODPARC nao informado.");
        }

        if (!Boolean.TRUE.equals(JapeSession.getCurrentSession().getTxProperty(getTxPropertyName(codParc)))) {
            System.out.println("[Teleword][StartProcess] Fluxo nao iniciado: beforeUpdate nao marcou alteracao para P.");
            return;
        }

        iniciarProcessoParceiros(codParc);
    }

    private void iniciarProcessoParceiros(BigDecimal codParc) throws Exception {
        System.out.println("[Teleword][StartProcess] Preparando startProcess. status=" + STATUS_PROCESSO
                + ", codParc=" + codParc);

        ListaTarefaSP listaTarefaSP = (ListaTarefaSP) getStatelessFacade(ListaTarefaSPHome.JNDI_NAME,
                ListaTarefaSPHome.class);
        System.out.println("[Teleword][StartProcess] ListaTarefaSP recuperado com sucesso.");

        ServiceContext sctx = new ServiceContext(null);
        sctx.makeCurrent();
        BigDecimal usuarioLogado = getUsuarioLogado();

        if (usuarioLogado != null) {
            JapeSessionContext.putProperty("usuario_logado", usuarioLogado);
            JapeSession.putProperty("usuario_logado", usuarioLogado);
        }

        System.out.println("[Teleword][StartProcess] ServiceContext configurado.");
        System.out.println("[Teleword][StartProcess] Usuario logado no contexto=" + usuarioLogado);

        String params = String.format(
                "{\n" +
                        "  \"codPrn\": %s,\n" +
                        "  \"formulario\": {\n" +
                        "    \"embarcado\": [\n" +
                        "      {\n" +
                        "        \"entityName\": \"PROCESS_4_VERSION_18\",\n" +
                        "        \"records\": [\n" +
                        "          {\n" +
                        "            \"record\": [\n" +
                        "              {\n" +
                        "                \"name\": \"DADOS_MINIMOS_OK\",\n" +
                        "                \"value\": \"N\"\n" +
                        "              },\n" +
                        "              {\n" +
                        "                \"name\": \"TOKEN_SINTEGRAWS\"\n" +
                        "              }\n" +
                        "            ],\n" +
                        "            \"detalhes\": []\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"configFields\": []\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"formatado\": [\n" +
                        "      {\n" +
                        "        \"entityName\": \"AD_PARCPEND\",\n" +
                        "        \"records\": [\n" +
                        "          {\n" +
                        "            \"record\": [\n" +
                        "              {\n" +
                        "                \"name\": \"CODPARC\",\n" +
                        "                \"value\": %s\n" +
                        "              }\n" +
                        "            ],\n" +
                        "            \"detalhes\": []\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"configFields\": [\n" +
                        "          {\n" +
                        "            \"nome\": \"CODPARC\",\n" +
                        "            \"nomeAba\": \"Geral\",\n" +
                        "            \"visivel\": \"S\",\n" +
                        "            \"somenteLeitura\": \"N\",\n" +
                        "            \"obrigatorio\": \"N\",\n" +
                        "            \"ordem\": \"1\",\n" +
                        "            \"descricao\": \"Cód. Parceiro\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"telas\": null\n" +
                        "}\n",
                STATUS_PROCESSO,
                codParc.toPlainString());

        try {
            System.out.println("[Teleword][StartProcess] JSON startProcess montado. Tamanho=" + params.length());
            System.out.println("[Teleword][StartProcess] JSON startProcess=" + params);

            JsonObject param = JsonUtils.convertStringToJsonObject(params);
            System.out.println("[Teleword][StartProcess] JSON convertido para JsonObject.");

            sctx.setJsonRequestBody(param);
            sctx.setRequestBody(Json2XMLParser.jsonToElement("param", sctx.getJsonRequestBody()));
            System.out.println("[Teleword][StartProcess] RequestBody XML configurado. Chamando ListaTarefaSP.startProcess...");

            listaTarefaSP.getTaskReports(sctx);
            listaTarefaSP.startProcess(sctx);

            System.out.println("[Teleword][StartProcess] Processo iniciado com sucesso. status=" + STATUS_PROCESSO
                    + ", codParc=" + codParc);
        } catch (Exception e) {
            System.out.println("[Teleword][StartProcess] Erro ao iniciar processo de parceiros: " + e.getMessage());
            e.printStackTrace();

            if (isErroPermissaoInicializacao(e)) {
                System.out.println("[Teleword][StartProcess] Parceiro salvo, mas o Flow nao foi iniciado por falta "
                        + "de permissao de inicializacao. Libere o usuario/grupo no processo do Flow.");
                return;
            }

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
        DynamicVO parceiroVO = (DynamicVO) event.getVo();
        BigDecimal codParc = parceiroVO.asBigDecimal("CODPARC");

        String revisadoAnterior = getString(event.getModifingFields().getOldValue("AD_REVISADO"));
        String revisadoNovo = getString(event.getModifingFields().getNewValue("AD_REVISADO"));

        System.out.println("[Teleword][StartProcess] beforeUpdate TGFPAR. CODPARC=" + codParc
                + ", AD_REVISADO anterior=" + revisadoAnterior + ", novo=" + revisadoNovo);

        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            System.out.println("[Teleword][StartProcess] beforeUpdate sem CODPARC valido. Ignorando marcacao.");
            return;
        }

        if (STATUS_PENDENTE.equals(revisadoNovo) && !STATUS_PENDENTE.equals(revisadoAnterior)) {
            JapeSession.getCurrentSession().addTxProperty(getTxPropertyName(codParc), Boolean.TRUE);
            System.out.println("[Teleword][StartProcess] beforeUpdate marcou inicio do fluxo para CODPARC=" + codParc);
        } else {
            System.out.println("[Teleword][StartProcess] beforeUpdate nao marcou fluxo: campo nao mudou para P.");
        }
    }

    private String getString(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString().trim().toUpperCase();
    }

    private String getTxPropertyName(BigDecimal codParc) {
        return TX_PROPERTY_PREFIX + codParc.toPlainString();
    }

    private BigDecimal getUsuarioLogado() {
        Object usuarioLogado = JapeSessionContext.getProperty("usuario_logado");

        if (usuarioLogado == null) {
            usuarioLogado = JapeSession.getProperty("usuario_logado");
        }

        if (usuarioLogado == null) {
            return null;
        }

        if (usuarioLogado instanceof BigDecimal) {
            return (BigDecimal) usuarioLogado;
        }

        return new BigDecimal(usuarioLogado.toString());
    }

    private boolean isErroPermissaoInicializacao(Exception e) {
        Throwable erro = e;

        while (erro != null) {
            String mensagem = erro.getMessage();

            if (mensagem != null) {
                String mensagemNormalizada = mensagem.toLowerCase();

                if (mensagemNormalizada.contains("nao possui permiss")
                        || mensagemNormalizada.contains("não possui permiss")) {
                    return true;
                }
            }

            erro = erro.getCause();
        }

        return false;
    }
}
