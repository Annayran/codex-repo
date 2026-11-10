package br.com.zaya.teleworld.fornecedorxml.sankhya.services;

import static br.com.sankhya.dwf.services.ServiceUtils.getStatelessFacade;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.workflow.model.services.ListaTarefaSP;
import br.com.sankhya.workflow.model.services.ListaTarefaSPHome;
import br.com.sankhya.ws.ServiceContext;
import br.com.sankhya.ws.transformer.json.Json2XMLParser;
import br.com.zaya.teleworld.fornecedorxml.shared.config.FornecedorXmlConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class SankhyaFlowGateway {
    public BigDecimal iniciar(DynamicVO fila, FornecedorXmlConfig config) throws Exception {
        AuthenticationInfo authAnterior = AuthenticationInfo.getCurrentOrNull();
        ServiceContext contextoAnterior = ServiceContext.getCurrent();
        ServiceContext contexto = null;

        try {
            DynamicVO usuario = buscarUsuario(config.getCodUsu());
            String authId = System.currentTimeMillis() + ":" + config.getCodUsu() + ":zaya-flow-fornecedor";
            AuthenticationInfo auth = new AuthenticationInfo(
                    usuario.asString("NOMEUSU"),
                    config.getCodUsu(),
                    usuario.asBigDecimalOrZero("CODGRUPO"),
                    Integer.valueOf(authId.hashCode()));
            auth.makeCurrent();

            JapeSessionContext.putProperty("usuario_logado", auth.getUserID());
            JapeSessionContext.putProperty("grupo_usu_logado", auth.getGroupID());
            JapeSessionContext.putProperty("emp_usu_logado", fila.asBigDecimal("CODEMP"));
            JapeSessionContext.putProperty("dh_atual", new Timestamp(System.currentTimeMillis()));
            JapeSessionContext.putProperty("d_atual", new Timestamp(System.currentTimeMillis()));
            JapeSessionContext.putProperty("usuarioVO", usuario);
            JapeSessionContext.putProperty("authInfo", auth);

            JsonObject request = montarPayload(fila, config);
            ListaTarefaSP service = (ListaTarefaSP) getStatelessFacade(
                    ListaTarefaSPHome.JNDI_NAME, ListaTarefaSPHome.class);
            contexto = new ServiceContext(null);
            contexto.setAutentication(auth);
            contexto.makeCurrent();
            contexto.setJsonRequestBody(request);
            contexto.setRequestBody(Json2XMLParser.jsonToElement("param", request));
            service.startProcess(contexto);

            JsonObject response = contexto.getJsonResponse();
            if (response == null || !response.has("IDINSTPRN")
                    || response.get("IDINSTPRN").isJsonNull()) {
                throw new Exception("ListaTarefaSP.startProcess nao retornou IDINSTPRN.");
            }
            return response.get("IDINSTPRN").getAsBigDecimal();
        } finally {
            if (contexto != null) contexto.unregistry();
            AuthenticationInfo.unregistry();
            if (authAnterior != null) authAnterior.makeCurrent();
            if (contextoAnterior != null) contextoAnterior.makeCurrent();
        }
    }

    JsonObject montarPayload(DynamicVO fila, FornecedorXmlConfig config) throws Exception {
        String documento = fila.asString("CNPJCPF");
        if (documento == null || documento.trim().isEmpty()) {
            throw new Exception("A fila nao possui CNPJCPF para iniciar o Flow.");
        }

        JsonArray record = new JsonArray();
        record.add(campo("CGC_CPF", documento));
        record.add(campo("FORNECEDOR", "S"));
        record.add(campo("AD_SITCAD", "A"));

        JsonObject registro = new JsonObject();
        registro.add("record", record);
        registro.add("detalhes", new JsonArray());
        JsonArray records = new JsonArray();
        records.add(registro);

        JsonObject formFormatado = new JsonObject();
        formFormatado.addProperty("entityName", "AD_PARCPEND");
        formFormatado.add("records", records);
        formFormatado.add("configFields", configFields());
        JsonArray formatado = new JsonArray();
        formatado.add(formFormatado);

        JsonObject formulario = new JsonObject();
        formulario.add("formatado", formatado);
        JsonObject param = new JsonObject();
        param.addProperty("codPrn", config.getCodPrn());
        param.add("formulario", formulario);
        param.add("telas", JsonNull.INSTANCE);

        JsonObject evento = new JsonObject();
        evento.addProperty("$", "br.com.sankhya.workflow.listatarefa.necessita.variavel.inicializacao");
        JsonArray eventos = new JsonArray();
        eventos.add(evento);
        JsonObject listaEventos = new JsonObject();
        listaEventos.add("clientEvent", eventos);

        JsonObject body = new JsonObject();
        body.add("param", param);
        body.add("clientEventList", listaEventos);
        return body;
    }

    private JsonArray configFields() {
        JsonArray fields = new JsonArray();
        fields.add(configField("CODPARC", "N", "1", "Cod. Parceiro", null));
        fields.add(configField("CGC_CPF", "S", "3", "CPF / CNPJ", null));
        fields.add(configField("FORNECEDOR", "S", "4", "Fornecedor", "S"));
        fields.add(configField("AD_SITCAD", "S", "5", "Sit. Cadastro", "A"));
        return fields;
    }

    private JsonObject configField(String nome, String visivel, String ordem,
            String descricao, String valorPadrao) {
        JsonObject field = new JsonObject();
        field.addProperty("nome", nome);
        field.addProperty("nomeAba", "Geral");
        field.addProperty("visivel", visivel);
        field.addProperty("somenteLeitura", "N");
        field.addProperty("obrigatorio", "N");
        field.addProperty("ordem", ordem);
        field.addProperty("descricao", descricao);
        if (valorPadrao != null) field.addProperty("valorPadrao", valorPadrao);
        return field;
    }

    private JsonObject campo(String nome, String valor) {
        JsonObject campo = new JsonObject();
        campo.addProperty("name", nome);
        campo.addProperty("value", valor);
        return campo;
    }

    private DynamicVO buscarUsuario(BigDecimal codUsu) throws Exception {
        EntityFacade facade = EntityFacadeFactory.getDWFFacade();
        DynamicVO usuario = (DynamicVO) facade.findEntityByPrimaryKeyAsVO(
                "Usuario", new Object[] { codUsu });
        if (usuario == null) throw new Exception("Usuario tecnico nao encontrado: " + codUsu + '.');
        return usuario;
    }

}
