package br.com.crmbonus.clientes.action;

import br.com.crmbonus.clientes.integration.client.CrmHttpClient;
import br.com.crmbonus.clientes.dto.response.UserVerifyResponse;
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
import java.sql.ResultSet;

public class UserVerifyAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {

        System.out.println("\n[USER-VERIFY-ACTION] ========== INICIANDO AÇÃO USER-VERIFY ==========");

        for (Registro linha : contexto.getLinhas()) {

            System.out.println("\n[USER-VERIFY-ACTION] Processando registro...");

            Object codParcObj = linha.getCampo("CODPARC");
            System.out.println("[USER-VERIFY-ACTION] CODPARC recebido: " + codParcObj);

            BigDecimal codParc = null;
            if (codParcObj != null) {
                try {
                    if (codParcObj instanceof BigDecimal) {
                        codParc = (BigDecimal) codParcObj;
                    } else {
                        codParc = new BigDecimal(codParcObj.toString());
                    }
                    System.out.println("[USER-VERIFY-ACTION] CODPARC convertido para BigDecimal: " + codParc);
                } catch (Exception e) {
                    System.out.println("[USER-VERIFY-ACTION] ERRO ao converter CODPARC: " + e.getMessage());
                    contexto.setMensagemRetorno("<b>Erro ao ler CODPARC do registro.</b>");
                    return;
                }
            }

            // Busca parceiro: por código se disponível, caso contrário tentamos buscar pelo CPF
            DynamicVO parcVO = null;
            JapeWrapper parcDAO = JapeFactory.dao("Parceiro");

            if (codParc != null) {
                System.out.println("[USER-VERIFY-ACTION] Tentando buscar Parceiro por CODPARC: " + codParc);
                parcVO = PinSendQueryHelper.findParceiroByCode(codParc);
                if (parcVO != null) {
                    System.out.println("[USER-VERIFY-ACTION] ✓ Parceiro encontrado por CODPARC");
                } else {
                    System.out.println("[USER-VERIFY-ACTION] ✗ Parceiro NÃO encontrado por CODPARC");
                }
            }

            // Se não encontrou por CODPARC, tentamos localizar por CPF (será preenchido adiante)

            // Se não temos parceiro ainda, precisamos do CPF; primeiro tentamos extrair CPF do registro atual (campo CGC_CPF pode não estar na linha)
            String cpf = null;
            String telefoneRaw = null;
            String telefoneMask = "";
            if (parcVO != null) {
                cpf = parcVO.asString("CGC_CPF");
                telefoneRaw = parcVO.asString("TELEFONE");
                System.out.println("[USER-VERIFY-ACTION] CPF lido do Parceiro (com mascara): " + cpf);
            }

            // Se ainda não tem cpf, tentar ler campo CGC_CPF da linha (quando a grid traz o campo direto)
            if ((cpf == null || cpf.trim().isEmpty()) && linha.getCampo("CGC_CPF") != null) {
                Object cpfObj = linha.getCampo("CGC_CPF");
                if (cpfObj != null) {
                    cpf = cpfObj.toString();
                    System.out.println("[USER-VERIFY-ACTION] CPF lido da linha (com mascara): " + cpf);
                }
            }

            cpf = PinSendQueryHelper.extractCleanCPF(cpf);
            System.out.println("[USER-VERIFY-ACTION] CPF limpo (apenas números): " + cpf);

            if ((parcVO == null) && (cpf != null && !cpf.isEmpty())) {
                // tentar buscar parceiro pelo CPF (CGC_CPF) de forma robusta usando NativeSql para recuperar CODPARC
                System.out.println("[USER-VERIFY-ACTION] Parceiro ainda não encontrado. Tentando busca por CPF: " + cpf);
                JdbcWrapper jdbc = null;
                try {
                    EntityFacade facade = EntityFacadeFactory.getDWFFacade();
                    jdbc = facade.getJdbcWrapper();
                    jdbc.openSession();

                    NativeSql sql = new NativeSql(jdbc);
                    sql.appendSql("SELECT CODPARC, TELEFONE FROM TGFPAR WHERE REGEXP_REPLACE(CGC_CPF, '[^0-9]', '') = :CPF");
                    sql.setNamedParameter("CPF", cpf);

                    System.out.println("[USER-VERIFY-ACTION] Executando SQL: SELECT CODPARC FROM TGFPAR WHERE REGEXP_REPLACE(CGC_CPF, '[^0-9]', '') = '" + cpf + "'");

                    try (ResultSet rs = sql.executeQuery()) {
                        if (rs.next()) {
                            BigDecimal foundCodParc = new BigDecimal(rs.getString("CODPARC"));
                            telefoneRaw = rs.getString("TELEFONE");
                            System.out.println("[USER-VERIFY-ACTION] ✓ Parceiro encontrado por CPF. CODPARC: " + foundCodParc);
                            parcVO = JapeFactory.dao("Parceiro").findByPK(foundCodParc);
                            System.out.println("[USER-VERIFY-ACTION] ✓ DynamicVO do Parceiro carregado");
                        } else {
                            System.out.println("[USER-VERIFY-ACTION] ✗ Nenhum parceiro encontrado com CPF: " + cpf);
                        }
                    }

                } catch (Throwable t) {
                    System.out.println("[USER-VERIFY-ACTION] ✗ ERRO ao consultar Parceiro por CPF: " + t.getMessage());
                    t.printStackTrace();
                    contexto.setMensagemRetorno("<b>Não foi possível consultar Parceiro por CPF (verifique integração JAPE/DB): " + t.getMessage() + "</b>");
                    return;
                } finally {
                    if (jdbc != null) {
                        try { jdbc.closeSession(); } catch (Exception ignored) {}
                    }
                }
            }

            if (parcVO == null) {
                System.out.println("[USER-VERIFY-ACTION] ✗ Parceiro não encontrado (nem por CODPARC nem por CPF)");
                contexto.setMensagemRetorno("<b>Parceiro não encontrado.</b>");
                return;
            }

            if (cpf == null || cpf.isEmpty()) {
                // Se mesmo assim não conseguimos recuperar CPF, abortamos
                System.out.println("[USER-VERIFY-ACTION] ✗ CPF inválido ou vazio");
                contexto.setMensagemRetorno("<b>Parceiro não possui CPF válido cadastrado.</b>");
                return;
            }

            // format phone mask
            if (telefoneRaw != null) {
                String telefoneClean = telefoneRaw.replaceAll("[^0-9]", "");
                telefoneMask = maskPhone(telefoneClean);
                System.out.println("[USER-VERIFY-ACTION] Telefone (limpo): " + telefoneClean + " -> mascarado: " + telefoneMask);
            }

            System.out.println("[USER-VERIFY-ACTION] Preparando chamada ao CRM Bonus - user-verify");
            System.out.println("[USER-VERIFY-ACTION] ========== ENVIANDO PARA API ==========");
            System.out.println("[USER-VERIFY-ACTION] Endpoint: https://hub.zaya.it/webhook-test/crm-bonus");
            System.out.println("[USER-VERIFY-ACTION] Entidade: inicializacao");
            System.out.println("[USER-VERIFY-ACTION] Sub-Entidade: user-verify");
            System.out.println("[USER-VERIFY-ACTION] CPF: " + cpf);

            CrmHttpClient crm = new CrmHttpClient();

            UserVerifyResponse response;
            try {
                response = crm.userVerify(cpf);
                System.out.println("[USER-VERIFY-ACTION] ========== RESPOSTA RECEBIDA DA API ==========");
            } catch (Exception e) {
                System.out.println("[USER-VERIFY-ACTION] ✗ ERRO ao chamar user-verify: " + e.getMessage());
                e.printStackTrace();
                String html =
                        "<html>" +
                                "<div style='font-family:Arial; padding:10px;'>" +
                                "<h2 style='color:#E74C3C;'>Erro na consulta ao CRM Bonus</h2>" +
                                "<hr/>" +
                                "<p>Detalhes: <b>" + e.getMessage() + "</b></p>" +
                                "</div>" +
                                "</html>";

                contexto.setMensagemRetorno(html);
                return;
            }

            if (response == null) {
                System.out.println("[USER-VERIFY-ACTION] ✗ Response é null");
                contexto.setMensagemRetorno("<b>Erro na consulta ao CRM.</b>");
                return;
            }

            System.out.println("[USER-VERIFY-ACTION] Response recebido:");
            System.out.println("[USER-VERIFY-ACTION]   - resource_id: " + response.isResource_id());
            System.out.println("[USER-VERIFY-ACTION]   - request_pin: " + response.isRequest_pin());
            System.out.println("[USER-VERIFY-ACTION]   - user_id: " + response.getUser_id());

            // Se o CRM indicar que precisa de PIN, informar ao usuário para enviar cadastro (pin-send)
            if (response.isRequest_pin()) {
                System.out.println("[USER-VERIFY-ACTION] → request_pin = TRUE. Usuário precisa ser cadastrado no CRM Bonus");
                String html =
                        "<html>" +
                                "<div style='font-family:Arial; padding:10px;'>" +
                                "<h2 style='color:#E67E22;'>Parceiro precisa ser cadastrado no CRM Bonus</h2>" +
                                "<hr/>" +
                                "<p>CPF: <b>" + cpf + "</b></p>" +
                                (telefoneMask != null && !telefoneMask.isEmpty() ? "<p>Telefone: <b>" + telefoneMask + "</b></p>" : "") +
                                "<p>Para prosseguir, selecione o botão <b>Enviar PIN</b> para cadastrar o cliente no CRM Bonus.</p>" +
                                "</div>" +
                                "</html>";

                // Persistir AD_PINVAL conforme mapping: true -> 'S', false -> 'N'
                try {
                    String pinVal = response.isRequest_pin() ? "S" : "N";
                    FluidUpdateVO f = parcDAO.prepareToUpdate(parcVO);
                    f.set("AD_PINVAL", pinVal);
                    f.update();
                    System.out.println("[USER-VERIFY-ACTION] TGFPAR.AD_PINVAL definido para: '" + pinVal + "' no CODPARC=" + codParc);
                } catch (Exception e) {
                    System.out.println("[USER-VERIFY-ACTION] Erro ao persistir AD_PINVAL (request_pin branch): " + e.getMessage());
                    e.printStackTrace();
                }

                contexto.setMensagemRetorno(html);
                System.out.println("[USER-VERIFY-ACTION] Mensagem apresentada ao usuário (request_pin=true)");
                return;
            }

            // Caso não precise de PIN, gravar user_id retornado (se existir) no Parceiro (campo TGFPAR.AD_USER_ID)
            String userId = response.getUser_id();

            if (userId == null || userId.trim().isEmpty()) {
                System.out.println("[USER-VERIFY-ACTION] ✗ user_id não foi retornado pelo CRM");
                contexto.setMensagemRetorno("<b>Consulta realizada, porém não foi retornado user_id pelo CRM.</b>");
                return;
            }

            System.out.println("[USER-VERIFY-ACTION] → user_id retornado: " + userId);
            System.out.println("[USER-VERIFY-ACTION] Persistindo user_id no campo TGFPAR.AD_USER_ID...");

            // Persistir AD_USER_ID na tabela Parceiro
            try {
                FluidUpdateVO fluid = parcDAO.prepareToUpdate(parcVO);
                fluid.set("AD_USER_ID", userId);
                // Set AD_PINVAL strictly based on API: true -> 'S', false -> 'N'
                String pinValOnSuccess = response.isRequest_pin() ? "S" : "N";
                fluid.set("AD_PINVAL", pinValOnSuccess);
                fluid.update();
                System.out.println("[USER-VERIFY-ACTION] ✓ user_id persistido com sucesso em TGFPAR.AD_USER_ID");
                System.out.println("[USER-VERIFY-ACTION] TGFPAR.AD_PINVAL definido para: '" + pinValOnSuccess + "' no CODPARC=" + codParc);
            } catch (Exception e) {
                System.out.println("[USER-VERIFY-ACTION] ✗ ERRO ao persistir user_id: " + e.getMessage());
                e.printStackTrace();
                contexto.setMensagemRetorno("<b>Erro ao persistir user_id no Parceiro: " + e.getMessage() + "</b>");
                return;
            }

            String html =
                    "<html>" +
                            "<div style='font-family:Arial; padding:10px;'>" +
                            "<h2 style='color:#27AE60;'>PIN já validado — Não é necessário cadastrar</h2>" +
                            "<hr/>" +
                            "<p>CPF: <b>" + cpf + "</b></p>" +
                            (telefoneMask != null && !telefoneMask.isEmpty() ? "<p>Telefone: <b>" + telefoneMask + "</b></p>" : "") +
                            "<p>user_id salvo: <b>" + userId + "</b></p>" +
                            "</div>" +
                            "</html>";

            contexto.setMensagemRetorno(html);
            System.out.println("[USER-VERIFY-ACTION] ✓ Ação finalizada com sucesso");
            System.out.println("[USER-VERIFY-ACTION] ========== FIM DA AÇÃO USER-VERIFY ==========" + "\n");
        }
    }

    private static String maskPhone(String digits) {
        if (digits == null) return null;

        // Exemplo de máscara: (99) 99999-9999
        String masked = digits.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        return masked;
    }
}
