package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.workflow.model.helper.ListaTarefaHelper;
import br.com.sankhya.workflow.model.services.ListaTarefaSP;
import br.com.sankhya.workflow.model.services.ListaTarefaSPHome;
import br.com.sankhya.workflow.utils.WorkflowUtils;
import br.com.sankhya.ws.ServiceContext;
import br.com.sankhya.ws.transformer.json.Json2XMLParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import static br.com.sankhya.dwf.services.ServiceUtils.getStatelessFacade;

final class StartProcessHelperSupport {

    private static final BigDecimal COD_PRN_AD_REVISADO = BigDecimal.valueOf(13L);
    private static final BigDecimal COD_PRN_FORNECEDOR_XML = BigDecimal.valueOf(15L);
    private static final String CLIENT_EVENT_INICIALIZACAO =
            "br.com.sankhya.workflow.listatarefa.necessita.variavel.inicializacao";

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

            String processKey = WorkflowUtils.getProcessKeyByProcessId(COD_PRN_AD_REVISADO);
            JsonArray formFormatadoArray = buildFormFormatado(codParc);

            System.out.println("[Teleword][" + origemLog + "] Iniciando Flow via ListaTarefaHelper. codPrn="
                    + COD_PRN_AD_REVISADO + ", processKey=" + processKey + ", codParc=" + codParc);
            System.out.println("[Teleword][" + origemLog + "] formFormatado=" + formFormatadoArray);

            BigDecimal idInstPrn = new ListaTarefaHelper().startProcess(
                    jdbc,
                    COD_PRN_AD_REVISADO,
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

    static BigDecimal iniciarProcessoFornecedorXml(JdbcWrapper jdbc, String cnpjParc,
            String origemLog) throws Exception {
        return iniciarProcessoFornecedorXml(jdbc, cnpjParc, origemLog, null);
    }

    static BigDecimal iniciarProcessoFornecedorXml(JdbcWrapper jdbc, String cnpjParc,
            String origemLog, StringBuilder diagnostico) throws Exception {
        String etapa = "XML-START-01 validacao da entrada";
        registrarDiagnostico(origemLog, diagnostico,
                etapa + ": jdbcInformado=" + (jdbc != null) + ", CNPJPARC=" + maskCnpj(cnpjParc));

        if (jdbc == null) {
            throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: conexao do evento nao informada.");
        }

        String cnpjNormalizado = normalizeCnpj(cnpjParc);
        if (cnpjNormalizado == null) {
            throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: CNPJPARC nao informado.");
        }

        try {
            etapa = "XML-START-02 identificacao do processo";
            registrarDiagnostico(origemLog, diagnostico,
                    etapa + ": codPrn=" + COD_PRN_FORNECEDOR_XML);

            etapa = "XML-START-03 construcao do payload ListaTarefaSP.startProcess";
            JsonObject requestBody = buildRequestBodyFornecedorXml(cnpjNormalizado);
            registrarDiagnostico(origemLog, diagnostico,
                    etapa + ": codPrn=15, entityName=AD_PARCPEND, registros=1, campos="
                            + "[CGC_CPF=" + maskCnpj(cnpjNormalizado)
                            + ", FORNECEDOR=S, AD_SITCAD=A], detalhes=[], configFields=4, "
                            + "clientEventList=preenchido");

            etapa = "XML-START-04 recuperacao do servico ListaTarefaSP";
            ListaTarefaSP listaTarefaSP = (ListaTarefaSP) getStatelessFacade(
                    ListaTarefaSPHome.JNDI_NAME,
                    ListaTarefaSPHome.class);
            registrarDiagnostico(origemLog, diagnostico,
                    etapa + ": concluida");

            ServiceContext contextoAnterior = ServiceContext.getCurrent();
            ServiceContext contextoServico = new ServiceContext(null);

            try {
                etapa = "XML-START-05 configuracao do ServiceContext";
                contextoServico.makeCurrent();
                contextoServico.setJsonRequestBody(requestBody);
                contextoServico.setRequestBody(Json2XMLParser.jsonToElement("param", requestBody));
                registrarDiagnostico(origemLog, diagnostico, etapa + ": concluida");

                etapa = "XML-START-06 chamada ListaTarefaSP.startProcess";
                registrarDiagnostico(origemLog, diagnostico, etapa + ": iniciada");
                listaTarefaSP.startProcess(contextoServico);

                etapa = "XML-START-07 leitura do retorno ListaTarefaSP.startProcess";
                JsonObject response = contextoServico.getJsonResponse();
                if (response == null || !response.has("IDINSTPRN")
                        || response.get("IDINSTPRN").isJsonNull()) {
                    throw new Exception("ListaTarefaSP.startProcess nao retornou IDINSTPRN.");
                }

                BigDecimal idInstPrn = response.get("IDINSTPRN").getAsBigDecimal();
                registrarDiagnostico(origemLog, diagnostico,
                        etapa + ": IDINSTPRN=" + idInstPrn);
                return idInstPrn;
            } finally {
                contextoServico.unregistry();
                if (contextoAnterior != null) {
                    contextoAnterior.makeCurrent();
                }
            }
        } catch (Exception erro) {
            registrarDiagnostico(origemLog, diagnostico,
                    "XML-START-ERRO na etapa [" + etapa + "]: " + getMensagemErro(erro));
            throw erro;
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

    private static JsonObject buildRequestBodyFornecedorXml(String cnpjParc) {
        JsonArray records = new JsonArray();

        records.add(buildRecord("CGC_CPF", cnpjParc));
        records.add(buildRecord("FORNECEDOR", "S"));
        records.add(buildRecord("AD_SITCAD", "A"));

        JsonObject recordsJSON = new JsonObject();
        recordsJSON.add("record", records);
        recordsJSON.add("detalhes", new JsonArray());

        JsonArray recordsArray = new JsonArray();
        recordsArray.add(recordsJSON);

        JsonObject formFormatado = new JsonObject();
        formFormatado.addProperty("entityName", "AD_PARCPEND");
        formFormatado.add("records", recordsArray);
        formFormatado.add("configFields", buildConfigFieldsFornecedorXml());

        JsonArray formFormatadoArray = new JsonArray();
        formFormatadoArray.add(formFormatado);

        JsonObject formulario = new JsonObject();
        formulario.add("formatado", formFormatadoArray);

        JsonObject param = new JsonObject();
        param.addProperty("codPrn", COD_PRN_FORNECEDOR_XML);
        param.add("formulario", formulario);
        param.add("telas", JsonNull.INSTANCE);

        JsonObject clientEvent = new JsonObject();
        clientEvent.addProperty("$", CLIENT_EVENT_INICIALIZACAO);

        JsonArray clientEvents = new JsonArray();
        clientEvents.add(clientEvent);

        JsonObject clientEventList = new JsonObject();
        clientEventList.add("clientEvent", clientEvents);

        JsonObject requestBody = new JsonObject();
        requestBody.add("param", param);
        requestBody.add("clientEventList", clientEventList);
        return requestBody;
    }

    private static JsonArray buildConfigFieldsFornecedorXml() {
        JsonArray configFields = new JsonArray();
        configFields.add(buildConfigField("CODPARC", "N", "1", "Cód. Parceiro", null));
        configFields.add(buildConfigField("CGC_CPF", "S", "3", "CPF / CNPJ", null));
        configFields.add(buildConfigField("FORNECEDOR", "S", "4", "Fornecedor", "S"));
        configFields.add(buildConfigField("AD_SITCAD", "S", "5", "Sit. Cadastro", "A"));
        return configFields;
    }

    private static JsonObject buildConfigField(String nome, String visivel, String ordem,
            String descricao, String valorPadrao) {
        JsonObject configField = new JsonObject();
        configField.addProperty("nome", nome);
        configField.addProperty("nomeAba", "Geral");
        configField.addProperty("visivel", visivel);
        configField.addProperty("somenteLeitura", "N");
        configField.addProperty("obrigatorio", "N");
        configField.addProperty("ordem", ordem);
        configField.addProperty("descricao", descricao);

        if (valorPadrao != null) {
            configField.addProperty("valorPadrao", valorPadrao);
        }

        return configField;
    }

    private static JsonObject buildRecord(String name, String value) {
        JsonObject record = new JsonObject();
        record.addProperty("name", name);
        record.addProperty("value", value);
        return record;
    }

    private static String normalizeCnpj(String cnpjParc) {
        if (cnpjParc == null) {
            return null;
        }

        String valor = cnpjParc.trim();
        return valor.isEmpty() ? null : valor;
    }

    private static void registrarDiagnostico(String origemLog, StringBuilder diagnostico, String mensagem) {
        String linha = "[Teleword][" + origemLog + "] " + mensagem;
        System.out.println(linha);

        if (diagnostico != null) {
            diagnostico.append(mensagem).append('\n');
        }
    }

    private static String maskCnpj(String cnpjParc) {
        String normalizado = normalizeCnpj(cnpjParc);
        if (normalizado == null) {
            return "NULL";
        }

        String somenteDigitos = normalizado.replaceAll("[^0-9]", "");
        if (somenteDigitos.length() <= 4) {
            return "****";
        }

        return "**********" + somenteDigitos.substring(somenteDigitos.length() - 4);
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private static String getMensagemErro(Throwable erro) {
        StringBuilder mensagem = new StringBuilder();
        Throwable causa = erro;
        int nivel = 0;

        while (causa != null && nivel < 12) {
            if (mensagem.length() > 0) {
                mensagem.append(" -> ");
            }

            mensagem.append(causa.getClass().getSimpleName());
            if (!isBlank(causa.getMessage())) {
                mensagem.append(": ").append(causa.getMessage().trim());
            }

            if (causa.getCause() == causa) {
                break;
            }

            causa = causa.getCause();
            nivel++;
        }

        return mensagem.toString();
    }
}
