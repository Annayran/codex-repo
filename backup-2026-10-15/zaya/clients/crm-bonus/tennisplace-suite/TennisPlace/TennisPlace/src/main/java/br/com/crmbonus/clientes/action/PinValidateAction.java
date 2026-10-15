package br.com.crmbonus.clientes.action;

import br.com.crmbonus.clientes.dto.request.PinValidateRequestDTO;
import br.com.crmbonus.clientes.integration.client.CrmHttpClient;
import br.com.crmbonus.clientes.query.PinSendQueryHelper;
import br.com.crmbonus.common.integration.EmpresaCredentialsValidationService;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Optional;

public class PinValidateAction implements AcaoRotinaJava {

    private final EmpresaCredentialsValidationService credentialsValidationService = new EmpresaCredentialsValidationService();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {

        System.out.println("\n[PIN-VALIDATE-ACTION] ===== INICIANDO AÇÃO PIN-VALIDATE =====");

        Object pinObj = contexto.getParam("PIN");
        if (pinObj == null) {
            contexto.setMensagemRetorno("<b>É necessário informar o PIN (campo PIN).</b>");
            return;
        }

        String pin = pinObj.toString();
        System.out.println("[PIN-VALIDATE-ACTION] PIN informado: " + pin);

        for (Registro linha : contexto.getLinhas()) {

            Object codParcObj = linha.getCampo("CODPARC");
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

            // Tentativa de obter NUNOTA (nota) para atualizar AD_PINMASTER
            Object nunotaObj = linha.getCampo("NUNOTA");
            if (nunotaObj == null) {
                // tenta obter do contexto se foi passado como parametro
                Object nunotaCtx = contexto.getParam("NUNOTA");
                nunotaObj = nunotaCtx;
            }

            BigDecimal nunota = null;
            if (nunotaObj != null) {
                try {
                    nunota = new BigDecimal(nunotaObj.toString());
                } catch (Exception e) {
                    // não fatal, apenas logamos
                    System.out.println("[PIN-VALIDATE-ACTION] Não foi possível converter NUNOTA: " + e.getMessage());
                }
            }

            // Montar DTO e chamar CRM
            PinValidateRequestDTO req = new PinValidateRequestDTO();
            req.setPin(pin);
            req.setUser_id(userId);

            System.out.println("[PIN-VALIDATE-ACTION] JSON ENVIADO: pin=" + pin + ", user_id=" + userId);

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

                String retorno = crm.pinValidate(req, null, codEmp);
                System.out.println("[PIN-VALIDATE-ACTION] RESPONSE: " + retorno);

                // Parse JSON response and check data.valid_pin
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(retorno);

                    // Accept both shapes: { data: { valid_pin: true } } or { valid_pin: true }
                    JsonNode dataNode = root.path("data");
                    boolean valid;
                    if (dataNode != null && !dataNode.isMissingNode() && dataNode.has("valid_pin")) {
                        valid = dataNode.path("valid_pin").asBoolean(false);
                    } else if (root.has("valid_pin")) {
                        valid = root.path("valid_pin").asBoolean(false);
                    } else {
                        // if neither present, assume false
                        valid = false;
                    }

                    // Check request_pin if present (used to mark not-validated)
                    boolean requestPin = false;
                    if (dataNode != null && dataNode.has("request_pin")) {
                        requestPin = dataNode.path("request_pin").asBoolean(false);
                    } else if (root.has("request_pin")) {
                        requestPin = root.path("request_pin").asBoolean(false);
                    }

                    // Check pin_master and update TGFCAB.AD_PINMASTER if possible
                    boolean pinMaster = false;
                    if (dataNode != null && dataNode.has("pin_master")) {
                        pinMaster = dataNode.path("pin_master").asBoolean(false);
                    } else if (root.has("pin_master")) {
                        pinMaster = root.path("pin_master").asBoolean(false);
                    }

                    System.out.println("[PIN-VALIDATE-ACTION] pin_master recebido: " + pinMaster);
                    System.out.println("[PIN-VALIDATE-ACTION] valid_pin recebido: " + valid + " | request_pin recebido: " + requestPin);

                    // Update TGFCAB.AD_PINMASTER when NUNOTA is available
                    if (nunota != null) {
                        try {
                            // atualiza TGFCAB.AD_PINMASTER com 'S' ou 'N'
                            br.com.sankhya.jape.wrapper.JapeWrapper cabDAO = br.com.sankhya.jape.wrapper.JapeFactory.dao("CabecalhoNota");
                            br.com.sankhya.jape.vo.DynamicVO cabVO = cabDAO.findByPK(nunota);
                            if (cabVO != null) {
                                br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO fluid = cabDAO.prepareToUpdate(cabVO);
                                fluid.set("AD_PINMASTER", pinMaster ? "S" : "N");
                                fluid.update();
                                System.out.println("[PIN-VALIDATE-ACTION] TGFCAB.AD_PINMASTER atualizado para: " + (pinMaster ? "S" : "N") + " na NUNOTA=" + nunota);
                            } else {
                                System.out.println("[PIN-VALIDATE-ACTION] Não foi possível localizar TGFCAB com NUNOTA=" + nunota);
                            }
                        } catch (Exception e) {
                            System.out.println("[PIN-VALIDATE-ACTION] Erro ao persistir AD_PINMASTER: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("[PIN-VALIDATE-ACTION] NUNOTA não fornecida; pulando atualização de AD_PINMASTER.");
                    }

                    // Atualizar TGFPAR.AD_PINVAL (parceiro) conforme resultado do PIN
                    try {
                        JapeWrapper parcDAO = JapeFactory.dao("Parceiro");
                        DynamicVO parcUpdate = parcDAO.findByPK(codParc);
                        if (parcUpdate != null) {
                            FluidUpdateVO f = parcDAO.prepareToUpdate(parcUpdate);
                            // lógica: somente 'S' quando valid==true e request_pin==false; caso contrário 'N'
                            String pinVal = (valid && !requestPin) ? "S" : "N";
                            f.set("AD_PINVAL", pinVal);
                            f.update();
                            System.out.println("[PIN-VALIDATE-ACTION] TGFPAR.AD_PINVAL atualizado para: " + pinVal + " no CODPARC=" + codParc);
                        } else {
                            System.out.println("[PIN-VALIDATE-ACTION] Não foi possível localizar TGFPAR com CODPARC=" + codParc);
                        }
                    } catch (Exception e) {
                        System.out.println("[PIN-VALIDATE-ACTION] Erro ao persistir AD_PINVAL no Parceiro: " + e.getMessage());
                        e.printStackTrace();
                    }

                    String html;
                    if (valid) {
                        html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#27AE60;'>PIN VALIDO</h2></div></html>";
                    } else {
                        html = "<html><div style='font-family:Arial; padding:10px;'><h2 style='color:#E74C3C;'>PIN INVALIDO</h2></div></html>";
                    }

                    contexto.setMensagemRetorno(html);

                } catch (Exception e) {
                    // fallback: show raw response
                    System.out.println("[PIN-VALIDATE-ACTION] Não foi possível parsear JSON da resposta: " + e.getMessage());
                    contexto.setMensagemRetorno("<b>Validação PIN:</b> " + retorno);
                }

            } catch (Exception e) {
                System.out.println("[PIN-VALIDATE-ACTION] ERRO ao chamar pin-validate: " + e.getMessage());
                contexto.setMensagemRetorno("<b>Erro ao validar PIN: " + e.getMessage() + "</b>");
            }
        }

        System.out.println("[PIN-VALIDATE-ACTION] ===== FIM AÇÃO PIN-VALIDATE =====\n");
    }
}
