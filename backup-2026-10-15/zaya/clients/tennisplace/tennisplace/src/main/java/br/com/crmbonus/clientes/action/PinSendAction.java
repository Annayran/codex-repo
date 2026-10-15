package br.com.crmbonus.clientes.action;

import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO.ItemDTO;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO.CustomerDTO;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;
import br.com.crmbonus.clientes.integration.client.CrmHttpClient;
import br.com.crmbonus.clientes.query.PinSendQueryHelper;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.ResultSet;

public class PinSendAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {

        System.out.println("\n[PIN-SEND-ACTION] ===== INICIANDO AÇÃO PIN-SEND =====");

        for (Registro linha : contexto.getLinhas()) {


            Object codParcObj = linha.getCampo("CODPARC");
            Object nunotaObj = linha.getCampo("NUNOTA");

            if (codParcObj == null || nunotaObj == null) {
                contexto.setMensagemRetorno("<b>Registro precisa ter CODPARC e NUNOTA.</b>");
                return;
            }

            BigDecimal codParc = new BigDecimal(codParcObj.toString());
            BigDecimal nunota = new BigDecimal(nunotaObj.toString());

            System.out.println("[PIN-SEND-ACTION] CODPARC=" + codParc + " NUNOTA=" + nunota);

            JdbcWrapper jdbc = null;
            NativeSql sql = null;

            List<ItemDTO> items = new ArrayList<>();
            String nomeParc = null;
            String telefone = null;
            String email = null;
            String dtnasc = null;
            String sexo = null;
            String cpf = null;
            BigDecimal grossValue = BigDecimal.ZERO;
            BigDecimal cabVlrNota = null;

            try {
                EntityFacade facade = EntityFacadeFactory.getDWFFacade();
                jdbc = facade.getJdbcWrapper();
                jdbc.openSession();

                sql = new NativeSql(jdbc);

                sql.appendSql(
                        "SELECT PAR.NOMEPARC, PAR.TELEFONE, PAR.EMAIL, PAR.DTNASC, PAR.SEXO, PAR.CGC_CPF, " +
                                " ITE.CODPROD AS SKU, ITE.QTDNEG AS QUANTITY, ITE.VLRUNIT AS VALUEPER, ITE.VLRTOT AS VALUEOF, " +
                                " CAB.NUNOTA, CAB.VLRNOTA " +
                                " FROM TGFCAB CAB " +
                                " JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC " +
                                " JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA " +
                                " WHERE CAB.CODPARC = :CODPARC AND CAB.NUNOTA = :NUNOTA"
                );

                sql.setNamedParameter("CODPARC", codParc);
                sql.setNamedParameter("NUNOTA", nunota);

                System.out.println("[PIN-SEND-ACTION] Executando query para montar o JSON de envio ao CRM...");

                try (ResultSet rs = sql.executeQuery()) {
                    while (rs.next()) {
                        if (nomeParc == null) nomeParc = rs.getString("NOMEPARC");
                        if (telefone == null) telefone = rs.getString("TELEFONE");
                        if (email == null) email = rs.getString("EMAIL");
                        if (dtnasc == null) {
                            java.sql.Date date = rs.getDate("DTNASC");
                            if (date != null) dtnasc = date.toString();
                        }
                        if (sexo == null) sexo = rs.getString("SEXO");
                        if (cpf == null) cpf = rs.getString("CGC_CPF");

                        // capture CAB.VLRNOTA once
                        if (cabVlrNota == null) {
                            cabVlrNota = rs.getBigDecimal("VLRNOTA");
                        }

                        String sku = rs.getString("SKU");
                        BigDecimal quantity = rs.getBigDecimal("QUANTITY");
                        BigDecimal valuePer = rs.getBigDecimal("VALUEPER");
                        BigDecimal valueOf = rs.getBigDecimal("VALUEOF");

                        PinSendRequestDTO.ItemDTO item = new PinSendRequestDTO.ItemDTO();
                        item.setSku(sku);
                        item.setQuantity(quantity == null ? BigDecimal.ZERO : quantity);
                        item.setValue_per(valuePer == null ? BigDecimal.ZERO : valuePer);
                        item.setValue_of(valueOf == null ? BigDecimal.ZERO : valueOf);

                        items.add(item);

                        // keep summing in case needed, but we'll use CAB.VLRNOTA as gross_value per request
                        grossValue = grossValue.add(valueOf == null ? BigDecimal.ZERO : valueOf);

                    }
                }

            } catch (Exception e) {
                System.out.println("[PIN-SEND-ACTION] ERRO ao montar dados: " + e.getMessage());
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao montar dados para o envio: " + e.getMessage() + "</b>");
                return;
            } finally {
                if (sql != null) NativeSql.releaseResources(sql);
                if (jdbc != null) JdbcWrapper.closeSession(jdbc);
            }

            if (nomeParc == null) {
                contexto.setMensagemRetorno("<b>Não foi possível localizar dados do parceiro/nota para envio.</b>");
                return;
            }

            // montar customer
            CustomerDTO customer = new CustomerDTO();
            customer.setCountry_code(new BigDecimal(55));
            customer.setName(nomeParc);
            customer.setPhone(telefone != null ? telefone.replaceAll("[^0-9]", "") : "");
            customer.setEmail(email != null ? email : "");

            // validate and set document type (CPF/CNPJ)
            String documentNumberClean = cpf != null ? cpf.replaceAll("[^0-9]", "") : null;
            if (documentNumberClean == null || documentNumberClean.isEmpty()) {
                contexto.setMensagemRetorno("<b>Parceiro não possui documento (CPF/CNPJ) válido cadastrado.</b>");
                return;
            }

            if (documentNumberClean.length() == 11) {
                // CPF
                if (!isValidCPF(documentNumberClean)) {
                    contexto.setMensagemRetorno("<b>CPF inválido: " + documentNumberClean + "</b>");
                    return;
                }
                customer.setDocument_type("CPF");
            } else if (documentNumberClean.length() == 14) {
                // CNPJ
                if (!isValidCNPJ(documentNumberClean)) {
                    contexto.setMensagemRetorno("<b>CNPJ inválido: " + documentNumberClean + "</b>");
                    return;
                }
                customer.setDocument_type("CNPJ");
            } else {
                contexto.setMensagemRetorno("<b>Documento inválido: " + documentNumberClean + "</b>");
                return;
            }

            customer.setDocument_number(documentNumberClean);

            // set birth and gender if available
            if (dtnasc != null) {
                customer.setBirth(dtnasc);
            }
            if (sexo != null) {
                String g = sexo.trim().toUpperCase();
                if (g.equals("M") || g.equals("F")) {
                    customer.setGender(g);
                }
            }

            PinSendRequestDTO request = new PinSendRequestDTO();
            request.setCustomer(customer);
            request.setItems(items.toArray(new PinSendRequestDTO.ItemDTO[0]));
            // use CAB.VLRNOTA if present, otherwise fallback to sum of items
            if (cabVlrNota != null) {
                request.setGross_value(cabVlrNota);
            } else {
                request.setGross_value(grossValue);
            }

            // Read AD_REQHASH from the note and send it in header when present
            String requestHash = null;
            try {
                requestHash = PinSendQueryHelper.getRequestHashFromNota(nunota);
                System.out.println("[PIN-SEND-ACTION] AD_REQHASH from nota: " + requestHash);
            } catch (Exception e) {
                System.out.println("[PIN-SEND-ACTION] Erro ao ler AD_REQHASH: " + e.getMessage());
            }

            // Log do JSON enviado
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String jsonToSend = mapper.writeValueAsString(request);
            System.out.println("[PIN-SEND-ACTION] JSON ENVIADO AO CRM: " + jsonToSend);

            // Chamar cliente HTTP
            CrmHttpClient crm = new CrmHttpClient();
            PinSendResponseDTO response;
            try {
                response = crm.pinSend(request, requestHash);
                System.out.println("[PIN-SEND-ACTION] RESPONSE (raw parsed): status=" + response.isStatus() + " message=" + response.getMessage());
                if (response.getData() != null) {
                    System.out.println("[PIN-SEND-ACTION] response.data.user_id=" + response.getData().getUser_id());
                    System.out.println("[PIN-SEND-ACTION] response.data.request_pin=" + response.getData().isRequest_pin());
                    System.out.println("[PIN-SEND-ACTION] response.data.text_pin=" + response.getData().getText_pin());
                }
            } catch (Exception e) {
                System.out.println("[PIN-SEND-ACTION] ERRO ao chamar PIN-SEND: " + e.getMessage());
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao chamar PIN-SEND: " + e.getMessage() + "</b>");
                return;
            }

            if (response == null) {
                contexto.setMensagemRetorno("<b>Resposta vazia do CRM.</b>");
                return;
            }

            String userId = null;
            String textPin = null;
            boolean requestPinFlag = false;
            if (response != null) {
                if (response.getData() != null) {
                    userId = response.getData().getUser_id();
                    textPin = response.getData().getText_pin();
                    requestPinFlag = response.getData().isRequest_pin();
                }
                // fallback to top-level fields if nested data is not present
                if ((userId == null || userId.trim().isEmpty()) && response.getUser_id() != null) {
                    userId = response.getUser_id();
                }
                if ((textPin == null || textPin.trim().isEmpty()) && response.getText_pin() != null) {
                    textPin = response.getText_pin();
                }
                if (!requestPinFlag) {
                    requestPinFlag = response.isRequest_pin();
                }
            }

            if (userId == null || userId.trim().isEmpty()) {
                contexto.setMensagemRetorno("<b>PIN enviado, porém user_id não retornado.</b>");
                return;
            }

            // Persistir TGFPAR.AD_USER_ID com userId
            try {
                JapeWrapper parcDAO = JapeFactory.dao("Parceiro");
                DynamicVO parcVO = parcDAO.findByPK(codParc);
                String existingUserId = parcVO.asString("AD_USER_ID");
                if (existingUserId == null || existingUserId.trim().isEmpty()) {
                    FluidUpdateVO fluid = parcDAO.prepareToUpdate(parcVO);
                    fluid.set("AD_USER_ID", userId);
                    fluid.update();
                    System.out.println("[PIN-SEND-ACTION] ✓ AD_USER_ID atualizado com: " + userId);
                } else {
                    System.out.println("[PIN-SEND-ACTION] AD_USER_ID já existente (não será sobrescrito): " + existingUserId);
                }
            } catch (Exception e) {
                System.out.println("[PIN-SEND-ACTION] ERRO ao persistir user_id: " + e.getMessage());
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao persistir user_id no Parceiro: " + e.getMessage() + "</b>");
                return;
            }

            // extrair "PIN 3885" do textPin
            String pinDisplay = null;
            if (textPin != null) {
                Pattern p = Pattern.compile("PIN\\s*\\d{3,6}");
                Matcher m = p.matcher(textPin);
                if (m.find()) {
                    pinDisplay = m.group(0);
                }
            }

            String html;
            if (pinDisplay != null) {
                html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN enviado</h2><hr/><p>" + pinDisplay + "</p></div></html>";
            } else {
                html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN enviado</h2><hr/><p>user_id: <b>" + userId + "</b></p></div></html>";
            }

            contexto.setMensagemRetorno(html);

        }

        System.out.println("[PIN-SEND-ACTION] ===== FIM AÇÃO PIN-SEND =====\n");
    }

    // CPF validation
    private static boolean isValidCPF(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) return false;
        // reject same digits
        if (cpf.matches("^(\\d)\\1{10}$")) return false;

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstCheck = 11 - (sum % 11);
            if (firstCheck >= 10) firstCheck = 0;

            if (firstCheck != Character.getNumericValue(cpf.charAt(9))) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondCheck = 11 - (sum % 11);
            if (secondCheck >= 10) secondCheck = 0;

            return secondCheck == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    // CNPJ validation
    private static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null) return false;
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() != 14) return false;
        if (cnpj.matches("^(\\d)\\1{13}$")) return false;

        try {
            int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weight1[i];
            }
            int firstCheck = sum % 11;
            firstCheck = firstCheck < 2 ? 0 : 11 - firstCheck;
            if (firstCheck != Character.getNumericValue(cnpj.charAt(12))) return false;

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weight2[i];
            }
            int secondCheck = sum % 11;
            secondCheck = secondCheck < 2 ? 0 : 11 - secondCheck;

            return secondCheck == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }
}

