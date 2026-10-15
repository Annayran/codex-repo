package br.com.crmbonus.clientes.action;

import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO.ItemDTO;
import br.com.crmbonus.clientes.dto.request.PinSendRequestDTO.CustomerDTO;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;
import br.com.crmbonus.clientes.integration.client.CrmHttpClient;
import br.com.crmbonus.clientes.query.PinSendQueryHelper;
import br.com.crmbonus.common.integration.EmpresaCredentialsValidationService;
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
import java.util.Optional;

public class PinSendAction implements AcaoRotinaJava {

    private final EmpresaCredentialsValidationService credentialsValidationService = new EmpresaCredentialsValidationService();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        System.out.println("\n[PIN-SEND-ACTION] ===== INICIANDO AÇÃO PIN-SEND =====");

        for (Registro linha : contexto.getLinhas()) {
            // validacao basica de campos de entrada
            Object codParcObj = linha.getCampo("CODPARC");
            Object nunotaObj = linha.getCampo("NUNOTA");

            if (codParcObj == null || nunotaObj == null) {
                contexto.setMensagemRetorno("<b>Registro precisa ter CODPARC e NUNOTA.</b>");
                return;
            }

            BigDecimal codParc = new BigDecimal(codParcObj.toString());
            BigDecimal nunota = new BigDecimal(nunotaObj.toString());

            // montar payload a ser enviado
            List<ItemDTO> items = new ArrayList<>();
            String nomeParc = null;
            String telefone = null;
            String email = null;
            String dtnasc = null;
            String sexo = null;
            String cpf = null;
            BigDecimal grossValue = BigDecimal.ZERO;
            BigDecimal cabVlrNota = null;

            JdbcWrapper jdbc = null;
            NativeSql sql = null;

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

                        if (cabVlrNota == null) cabVlrNota = rs.getBigDecimal("VLRNOTA");

                        String sku = rs.getString("SKU");
                        BigDecimal quantity = rs.getBigDecimal("QUANTITY");
                        BigDecimal valuePer = rs.getBigDecimal("VALUEPER");
                        BigDecimal valueOf = rs.getBigDecimal("VALUEOF");

                        ItemDTO item = new ItemDTO();
                        item.setSku(sku);
                        item.setQuantity(quantity == null ? BigDecimal.ZERO : quantity);
                        item.setValue_per(valuePer == null ? BigDecimal.ZERO : valuePer);
                        item.setValue_of(valueOf == null ? BigDecimal.ZERO : valueOf);

                        items.add(item);
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

            CustomerDTO customer = new CustomerDTO();
            customer.setCountry_code(new BigDecimal(55));
            customer.setName(nomeParc);
            customer.setPhone(telefone != null ? telefone.replaceAll("[^0-9]", "") : "");
            customer.setEmail(email != null ? email : "");

            String documentNumberClean = cpf != null ? cpf.replaceAll("[^0-9]", "") : null;
            if (documentNumberClean == null || documentNumberClean.isEmpty()) {
                contexto.setMensagemRetorno("<b>Parceiro não possui documento (CPF/CNPJ) válido cadastrado.</b>");
                return;
            }

            if (documentNumberClean.length() == 11) {
                if (!br.com.crmbonus.clientes.util.ValidatorUtils.isValidCPF(documentNumberClean)) {
                    contexto.setMensagemRetorno("<b>CPF inválido: " + documentNumberClean + "</b>");
                    return;
                }
                customer.setDocument_type("CPF");
            } else if (documentNumberClean.length() == 14) {
                if (!br.com.crmbonus.clientes.util.ValidatorUtils.isValidCNPJ(documentNumberClean)) {
                    contexto.setMensagemRetorno("<b>CNPJ inválido: " + documentNumberClean + "</b>");
                    return;
                }
                customer.setDocument_type("CNPJ");
            } else {
                contexto.setMensagemRetorno("<b>Documento inválido: " + documentNumberClean + "</b>");
                return;
            }

            customer.setDocument_number(documentNumberClean);

            PinSendRequestDTO request = new PinSendRequestDTO();
            request.setCustomer(customer);
            request.setItems(items.toArray(new ItemDTO[0]));
            request.setGross_value(cabVlrNota != null ? cabVlrNota : grossValue);

            String requestHash = null;
            try {
                requestHash = PinSendQueryHelper.getRequestHashFromNota(nunota);
            } catch (Exception ignored) {}

            System.out.println("[PIN-SEND-ACTION] JSON ENVIADO AO CRM: " + new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request));

            // chama cliente CRM passando codEmp extraido de linha.getCampo("CODEMP")
            BigDecimal codEmp = null;
            try {
                Object codEmpObj = linha.getCampo("CODEMP");
                if (codEmpObj != null) {
                    if (codEmpObj instanceof BigDecimal) codEmp = (BigDecimal) codEmpObj;
                    else codEmp = new BigDecimal(codEmpObj.toString());
                }
            } catch (Exception ex) {
                System.out.println("[CRM-BONUS] Não foi possível ler CODEMP da linha: " + ex.getMessage());
            }

            Optional<String> credValidationError = credentialsValidationService.validate(codEmp);
            if (credValidationError.isPresent()) {
                System.out.println("[CRM-BONUS] " + credValidationError.get());
                contexto.setMensagemRetorno("<b>" + credValidationError.get() + "</b>");
                return;
            }

            CrmHttpClient crm = new CrmHttpClient();
            PinSendResponseDTO response = null;
            try {
                response = crm.pinSend(request, requestHash, codEmp);
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
            if (response.getData() != null) {
                userId = response.getData().getUser_id();
                textPin = response.getData().getText_pin();
                requestPinFlag = response.getData().isRequest_pin();
            }
            if ((userId == null || userId.trim().isEmpty()) && response.getUser_id() != null) {
                userId = response.getUser_id();
            }
            if ((textPin == null || textPin.trim().isEmpty()) && response.getText_pin() != null) {
                textPin = response.getText_pin();
            }
            if (!requestPinFlag) requestPinFlag = response.isRequest_pin();

            if (userId == null || userId.trim().isEmpty()) {
                contexto.setMensagemRetorno("<b>PIN enviado, porém user_id não retornado.</b>");
                return;
            }

            // persistir user_id no parceiro
            try {
                JapeWrapper parcDAO = JapeFactory.dao("Parceiro");
                DynamicVO parcVO = parcDAO.findByPK(codParc);
                String existingUserId = parcVO.asString("AD_USER_ID");
                if (existingUserId == null || existingUserId.trim().isEmpty()) {
                    FluidUpdateVO fluid = parcDAO.prepareToUpdate(parcVO);
                    fluid.set("AD_USER_ID", userId);
                    fluid.update();
                }
            } catch (Exception e) {
                System.out.println("[PIN-SEND-ACTION] ERRO ao persistir user_id: " + e.getMessage());
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao persistir user_id no Parceiro: " + e.getMessage() + "</b>");
                return;
            }

            // montar retorno para o usuário
            String pinDisplay = null;
            if (textPin != null) {
                Pattern p = Pattern.compile("PIN\\s*\\d{3,6}");
                Matcher m = p.matcher(textPin);
                if (m.find()) pinDisplay = m.group(0);
            }

            String html;
            if (pinDisplay != null) html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN enviado</h2><hr/><p>" + pinDisplay + "</p></div></html>";
            else html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN enviado</h2><hr/><p>user_id: <b>" + userId + "</b></p></div></html>";

            contexto.setMensagemRetorno(html);
        }

        System.out.println("[PIN-SEND-ACTION] ===== FIM AÇÃO PIN-SEND =====\n");
    }
}
