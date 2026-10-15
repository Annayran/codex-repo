package br.com.crmbonus.clientes.action;

import br.com.crmbonus.clientes.dto.request.PinResendRequestDTO;
import br.com.crmbonus.clientes.dto.response.PinSendResponseDTO;
import br.com.crmbonus.clientes.integration.client.CrmHttpClient;
import br.com.crmbonus.clientes.query.PinSendQueryHelper;
import br.com.crmbonus.common.integration.EmpresaCredentialsValidationService;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinResendAction implements AcaoRotinaJava {

    private final EmpresaCredentialsValidationService credentialsValidationService = new EmpresaCredentialsValidationService();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {

        System.out.println("\n[PIN-RESEND-ACTION] ===== INICIANDO AÇÃO PIN-RESEND =====");

        for (Registro linha : contexto.getLinhas()) {

            Object codParcObj = linha.getCampo("CODPARC");
            Object nunotaObj = linha.getCampo("NUNOTA");

            if (codParcObj == null) {
                contexto.setMensagemRetorno("<b>Registro sem CODPARC.</b>");
                return;
            }

            BigDecimal codParc;
            try {
                codParc = new BigDecimal(codParcObj.toString());
            } catch (Exception e) {
                contexto.setMensagemRetorno("<b>Erro ao ler CODPARC do registro.</b>");
                return;
            }

            // Buscar parceiro
            DynamicVO parcVO = PinSendQueryHelper.findParceiroByCode(codParc);
            if (parcVO == null) {
                contexto.setMensagemRetorno("<b>Parceiro não encontrado.</b>");
                return;
            }

            String userId = parcVO.asString("AD_USER_ID");
            if (userId == null || userId.trim().isEmpty()) {
                contexto.setMensagemRetorno("<b>Parceiro não possui AD_USER_ID. Use Enviar PIN primeiro.</b>");
                return;
            }

            PinResendRequestDTO req = new PinResendRequestDTO();
            req.setUser_id(userId);

            System.out.println("[PIN-RESEND-ACTION] JSON ENVIADO: user_id=" + userId);

            // try to obtain request hash from the note if provided or latest note for the partner
            String requestHash = null;
            try {
                if (nunotaObj != null) {
                    BigDecimal nunota = new BigDecimal(nunotaObj.toString());
                    requestHash = PinSendQueryHelper.getRequestHashFromNota(nunota);
                }
                if (requestHash == null) {
                    DynamicVO latestNota = PinSendQueryHelper.findLatestNotaByCodParc(codParc);
                    if (latestNota != null) {
                        requestHash = latestNota.asString("AD_REQHASH");
                    }
                }
                System.out.println("[PIN-RESEND-ACTION] AD_REQHASH from nota: " + requestHash);
            } catch (Exception e) {
                System.out.println("[PIN-RESEND-ACTION] Erro ao ler AD_REQHASH: " + e.getMessage());
            }

            CrmHttpClient crm = new CrmHttpClient();

            try {
                BigDecimal codEmp = null;
                try {
                    Object codEmpObj = linha.getCampo("CODEMP");
                    if (codEmpObj != null) {
                        if (codEmpObj instanceof BigDecimal) {
                            codEmp = (BigDecimal) codEmpObj;
                        } else {
                            codEmp = new BigDecimal(codEmpObj.toString());
                        }
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

                PinSendResponseDTO resp = crm.pinResend(req, requestHash, codEmp);
                System.out.println("[PIN-RESEND-ACTION] RESPONSE (parsed): status=" + resp.isStatus() + " message=" + resp.getMessage());

                String textPin = null;
                String respUserId = null;
                if (resp.getData() != null) {
                    textPin = resp.getData().getText_pin();
                    respUserId = resp.getData().getUser_id();
                }
                if ((textPin == null || textPin.isEmpty()) && resp.getText_pin() != null) {
                    textPin = resp.getText_pin();
                }
                if ((respUserId == null || respUserId.isEmpty()) && resp.getUser_id() != null) {
                    respUserId = resp.getUser_id();
                }

                // extract PIN digits
                String pinDisplay = null;
                if (textPin != null) {
                    Pattern p = Pattern.compile("PIN\\s*(\\d{3,6})", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(textPin);
                    if (m.find()) {
                        pinDisplay = m.group(1); // only digits
                    }
                }

                // get partner phone number if available
                String phone = parcVO.asString("TELEFONE");
                if (phone != null) phone = phone.replaceAll("[^0-9]", "");
                String phoneMask = maskPhone(phone);

                String html;
                if (pinDisplay != null) {
                    String numberToShow = (phoneMask != null && !phoneMask.isEmpty()) ? phoneMask : (respUserId != null ? respUserId : "");
                    html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN ENVIADO</h2><hr/><p><b>PIN:</b> " + pinDisplay + "</p><p><b>Numero:</b> " + numberToShow + "</p></div></html>";
                } else {
                    String numberToShow = (phoneMask != null && !phoneMask.isEmpty()) ? phoneMask : (respUserId != null ? respUserId : "");
                    html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN ENVIADO</h2><hr/><p><b>Numero:</b> " + numberToShow + "</p></div></html>";
                }

                contexto.setMensagemRetorno(html);

            } catch (Exception e) {
                System.out.println("[PIN-RESEND-ACTION] ERRO ao chamar pin-resend: " + e.getMessage());
                contexto.setMensagemRetorno("<b>Erro ao reenviar PIN: " + e.getMessage() + "</b>");
            }
        }

        System.out.println("[PIN-RESEND-ACTION] ===== FIM AÇÃO PIN-RESEND =====\n");
    }

    private static String maskPhone(String digits) {
        if (digits == null) return null;

        // Try to apply common Brazilian masks
        if (digits.length() == 11) {
            // (99) 99999-9999
            return digits.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (digits.length() == 10) {
            // (99) 9999-9999
            return digits.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        } else if (digits.length() == 9) {
            return digits.replaceAll("(\\d{5})(\\d{4})", "$1-$2");
        }
        return digits; // fallback: raw digits
    }
}
