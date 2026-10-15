package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.workflow.model.services.ListaTarefaSP;
import br.com.sankhya.workflow.model.services.ListaTarefaSPHome;
import br.com.sankhya.ws.ServiceContext;
import br.com.sankhya.ws.transformer.json.Json2XMLParser;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;

import static br.com.sankhya.dwf.services.ServiceUtils.getStatelessFacade;

public class StartProcessButton implements AcaoRotinaJava {

    private static final String STATUS_PROCESSO = "4";

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        Registro[] linhas = contexto.getLinhas();
        System.out.println("[Teleword][StartProcessButton] Botao acionado. Linhas selecionadas=" + linhas.length);

        for (int i = 0; i < linhas.length; i++) {
            Registro registro = linhas[i];
            BigDecimal codParc = getBigDecimal(registro.getCampo("CODPARC"));

            System.out.println("[Teleword][StartProcessButton] Linha " + (i + 1) + " CODPARC=" + codParc);

            if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
                throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: CODPARC nao informado.");
            }

            iniciarProcessoParceiros(codParc);
        }
    }

    private void iniciarProcessoParceiros(BigDecimal codParc) throws Exception {
        System.out.println("[Teleword][StartProcessButton] Preparando startProcess. status=" + STATUS_PROCESSO
                + ", codParc=" + codParc);

        ListaTarefaSP listaTarefaSP = (ListaTarefaSP) getStatelessFacade(ListaTarefaSPHome.JNDI_NAME,
                ListaTarefaSPHome.class);
        System.out.println("[Teleword][StartProcessButton] ListaTarefaSP recuperado com sucesso.");

        ServiceContext sctx = new ServiceContext(null);
        sctx.makeCurrent();
        BigDecimal usuarioLogado = getUsuarioLogado();

        if (usuarioLogado != null) {
            JapeSessionContext.putProperty("usuario_logado", usuarioLogado);
            JapeSession.putProperty("usuario_logado", usuarioLogado);
        }

        System.out.println("[Teleword][StartProcessButton] ServiceContext configurado.");
        System.out.println("[Teleword][StartProcessButton] Usuario logado no contexto=" + usuarioLogado);

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
                        "            \"descricao\": \"C\\u00f3d. Parceiro\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"telas\": null\n" +
                        "}\n",
                STATUS_PROCESSO, codParc.toPlainString());

        try {
            System.out.println("[Teleword][StartProcessButton] JSON startProcess montado. Tamanho=" + params.length());
            System.out.println("[Teleword][StartProcessButton] JSON startProcess=" + params);

            JsonObject param = JsonUtils.convertStringToJsonObject(params);
            System.out.println("[Teleword][StartProcessButton] JSON convertido para JsonObject.");

            sctx.setJsonRequestBody(param);
            sctx.setRequestBody(Json2XMLParser.jsonToElement("param", sctx.getJsonRequestBody()));
            System.out.println("[Teleword][StartProcessButton] RequestBody XML configurado. Chamando ListaTarefaSP.startProcess...");

            listaTarefaSP.startProcess(sctx);


            System.out.println("[Teleword][StartProcessButton] Processo iniciado com sucesso. status=" + STATUS_PROCESSO
                    + ", codParc=" + codParc);
        } catch (Exception e) {
            System.out.println("[Teleword][StartProcessButton] Erro ao iniciar processo de parceiros: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erro ao iniciar o processo de parceiros: " + e.getMessage(), e);
        }
    }

    private BigDecimal getBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        return new BigDecimal(value.toString());
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
}
