package br.com.crmbonus.bonus.action;

import br.com.crmbonus.clientes.integration.client.CrmHttpClient;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.vo.DynamicVO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

public class BonusAvailableAction implements AcaoRotinaJava {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {

        System.out.println("[BONUS-AVAILABLE] Iniciando ação BonusAvailable");

        for (Registro linha : contexto.getLinhas()) {
            Object codParcObj = linha.getCampo("CODPARC");
            Object nunotaObj = linha.getCampo("NUNOTA");

            if (codParcObj == null || nunotaObj == null) {
                contexto.setMensagemRetorno("<b>Registro precisa ter CODPARC e NUNOTA.</b>");
                return;
            }

            BigDecimal codParc = new BigDecimal(codParcObj.toString());
            BigDecimal nunota = new BigDecimal(nunotaObj.toString());

            System.out.println("[BONUS-AVAILABLE] CODPARC=" + codParc + " NUNOTA=" + nunota);

            // load partner user_id and phone and items + gross_value + request_hash
            String userId = null;
            String phone = null;
            BigDecimal grossValue = BigDecimal.ZERO;
            String requestHash = null;
            List<Map<String,Object>> items = new ArrayList<>();

            JdbcWrapper jdbc = null;
            NativeSql sql = null;

            try {
                EntityFacade facade = EntityFacadeFactory.getDWFFacade();
                jdbc = facade.getJdbcWrapper();
                jdbc.openSession();

                // partner
                DynamicVO parcVO = JapeFactory.dao("Parceiro").findByPK(codParc);
                if (parcVO != null) {
                    userId = parcVO.asString("AD_USER_ID");
                    phone = parcVO.asString("TELEFONE");
                }

                // items and header
                sql = new NativeSql(jdbc);
                sql.appendSql("SELECT CAB.VLRNOTA, CAB.AD_REQUEST_HASH, ITE.CODPROD AS SKU, ITE.QTDNEG AS QUANTITY, ITE.VLRUNIT AS VALUEPER, ITE.VLRTOT AS VALUEOF FROM TGFCAB CAB JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA WHERE CAB.CODPARC = :CODPARC AND CAB.NUNOTA = :NUNOTA");
                sql.setNamedParameter("CODPARC", codParc);
                sql.setNamedParameter("NUNOTA", nunota);

                try (ResultSet rs = sql.executeQuery()) {
                    while (rs.next()) {
                        if (grossValue == null || grossValue.compareTo(BigDecimal.ZERO) == 0) {
                            grossValue = rs.getBigDecimal("VLRNOTA");
                        }
                        if (requestHash == null) requestHash = rs.getString("AD_REQUEST_HASH");

                        Map<String,Object> it = new HashMap<>();
                        it.put("sku", rs.getString("SKU"));
                        // quantity and values as BigDecimal/double
                        it.put("quantity", rs.getBigDecimal("QUANTITY"));
                        it.put("value_per", rs.getBigDecimal("VALUEPER"));
                        it.put("value_of", rs.getBigDecimal("VALUEOF"));
                        items.add(it);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao consultar dados locais: " + e.getMessage() + "</b>");
                return;
            } finally {
                if (sql != null) NativeSql.releaseResources(sql);
                if (jdbc != null) JdbcWrapper.closeSession(jdbc);
            }

            if (userId == null || userId.trim().isEmpty()) {
                contexto.setMensagemRetorno("<b>Parceiro sem AD_USER_ID (user_id). Utilize Enviar PIN para vincular o parceiro.</b>");
                return;
            }

            // build request payload
            Map<String,Object> payload = new HashMap<>();
            payload.put("user_id", userId);
            payload.put("value", grossValue);
            payload.put("items", items);

            CrmHttpClient crm = new CrmHttpClient();
            String responseRaw;
            try {
                responseRaw = crm.bonusAvailable(payload, requestHash);
                System.out.println("[BONUS-AVAILABLE] Response raw: " + responseRaw);
            } catch (Exception e) {
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao consultar CRM Bonus: " + e.getMessage() + "</b>");
                return;
            }

            if (responseRaw == null || responseRaw.trim().isEmpty()) {
                contexto.setMensagemRetorno("<b>Resposta vazia do CRM Bonus.</b>");
                return;
            }

            try {
                JsonNode root = mapper.readTree(responseRaw);
                JsonNode data = root.path("data");
                boolean bonusHas = false;
                if (!data.isMissingNode() && data.has("bonus_has")) {
                    bonusHas = data.path("bonus_has").asBoolean(false);
                } else if (root.has("bonus_has")) {
                    bonusHas = root.path("bonus_has").asBoolean(false);
                }

                // persist the response into AD_TGIBAV and AD_TGIBAI if bonus available
                if (bonusHas) {
                    // extract fields
                    double bonusBalance = data.path("bonus_balance").asDouble(0.0);
                    double bonusAvailable = data.path("bonus_available").asDouble(0.0);
                    String bonusQuestion = data.path("bonus_accumulate_question").asText(null);

                    // persist header
                    JdbcWrapper jdbc2 = null;
                    NativeSql sql2 = null;
                    try {
                        EntityFacade facade = EntityFacadeFactory.getDWFFacade();
                        jdbc2 = facade.getJdbcWrapper();
                        jdbc2.openSession();

                        sql2 = new NativeSql(jdbc2);
                        String insertHeader = "INSERT INTO AD_TGIBAV (CODPARC, NUNOTA, AD_USER_ID, BAV_BONUS_BALANCE, BAV_BONUS_AVAILABLE, BAV_MESSAGE, BAV_REQUEST_HASH, BAV_RESPONSE_JSON, BAV_CREATED_AT) VALUES (:CODPARC, :NUNOTA, :AD_USER_ID, :BAV_BONUS_BALANCE, :BAV_BONUS_AVAILABLE, :BAV_MESSAGE, :BAV_REQUEST_HASH, :BAV_RESPONSE_JSON, SYSTIMESTAMP)";
                        sql2.appendSql(insertHeader);
                        sql2.setNamedParameter("CODPARC", codParc);
                        sql2.setNamedParameter("NUNOTA", nunota);
                        sql2.setNamedParameter("AD_USER_ID", userId);
                        sql2.setNamedParameter("BAV_BONUS_BALANCE", BigDecimal.valueOf(bonusBalance));
                        sql2.setNamedParameter("BAV_BONUS_AVAILABLE", BigDecimal.valueOf(bonusAvailable));
                        sql2.setNamedParameter("BAV_MESSAGE", bonusQuestion);
                        sql2.setNamedParameter("BAV_REQUEST_HASH", requestHash);
                        sql2.setNamedParameter("BAV_RESPONSE_JSON", responseRaw);
                        sql2.executeUpdate();

                        // retrieve the generated BAV_ID - try selecting last inserted for this sale
                        String sel = "SELECT BAV_ID FROM AD_TGIBAV WHERE CODPARC = :CODPARC AND NUNOTA = :NUNOTA ORDER BY BAV_CREATED_AT DESC";
                        NativeSql selSql = new NativeSql(jdbc2);
                        selSql.appendSql(sel);
                        selSql.setNamedParameter("CODPARC", codParc);
                        selSql.setNamedParameter("NUNOTA", nunota);

                        Long bavId = null;
                        try (ResultSet rs = selSql.executeQuery()) {
                            if (rs.next()) {
                                bavId = rs.getLong("BAV_ID");
                            }
                        } finally {
                            NativeSql.releaseResources(selSql);
                        }

                        if (bavId == null) {
                            contexto.setMensagemRetorno("<b>Erro ao persistir BAV (não foi possível recuperar BAV_ID).</b>");
                            return;
                        }

                        // persist items from response data.items OR root.items
                        JsonNode itemsNode = data.path("items");
                        if (itemsNode == null || itemsNode.isMissingNode() || !itemsNode.isArray()) {
                            itemsNode = root.path("items");
                        }

                        if (itemsNode != null && itemsNode.isArray()) {
                            for (JsonNode itNode : itemsNode) {
                                String sku = itNode.path("sku").asText(null);
                                double valueOf = itNode.path("value_of").asDouble(0.0);
                                double valuePer = itNode.path("value_per").asDouble(0.0);
                                int quantity = itNode.path("quantity").asInt(1);

                                NativeSql insItem = new NativeSql(jdbc2);
                                insItem.appendSql("INSERT INTO AD_TGIBAI (BAI_BAV_ID, SKU, QUANTITY, VALUE_PER, VALUE_OF) VALUES (:BAI_BAV_ID, :SKU, :QUANTITY, :VALUE_PER, :VALUE_OF)");
                                insItem.setNamedParameter("BAI_BAV_ID", bavId);
                                insItem.setNamedParameter("SKU", sku);
                                insItem.setNamedParameter("QUANTITY", BigDecimal.valueOf(quantity));
                                insItem.setNamedParameter("VALUE_PER", BigDecimal.valueOf(valuePer));
                                insItem.setNamedParameter("VALUE_OF", BigDecimal.valueOf(valueOf));
                                insItem.executeUpdate();
                                NativeSql.releaseResources(insItem);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        contexto.setMensagemRetorno("<b>Erro ao persistir dados de bônus: " + e.getMessage() + "</b>");
                        return;
                    } finally {
                        if (sql2 != null) NativeSql.releaseResources(sql2);
                        if (jdbc2 != null) JdbcWrapper.closeSession(jdbc2);
                    }

                    // show message/question to user
                    String userMessage = null;
                    if (data != null && data.has("bonus_accumulate_question")) userMessage = data.path("bonus_accumulate_question").asText(null);
                    if (userMessage == null && root.has("message")) userMessage = root.path("message").asText(null);
                    if (userMessage == null) userMessage = "Bônus disponível: R$" + bonusAvailable;

                    String html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>Bônus disponível</h2><hr/><p>" + userMessage + "</p></div></html>";
                    contexto.setMensagemRetorno(html);

                } else {
                    // no bonus
                    String msg = null;
                    if (data != null && data.has("message")) msg = data.path("message").asText(null);
                    if (msg == null && root.has("message")) msg = root.path("message").asText(null);
                    if (msg == null) msg = "Nenhum bônus disponível.";
                    contexto.setMensagemRetorno("<b>" + msg + "</b>");
                }

            } catch (Exception e) {
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao parsear resposta do CRM: " + e.getMessage() + "</b>");
                return;
            }

        }

        System.out.println("[BONUS-AVAILABLE] Fim da ação");
    }
}
