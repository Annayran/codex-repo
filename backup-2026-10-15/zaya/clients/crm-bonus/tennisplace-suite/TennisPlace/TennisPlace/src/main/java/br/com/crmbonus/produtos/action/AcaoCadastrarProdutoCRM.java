package br.com.crmbonus.produtos.action;

import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;
import br.com.crmbonus.produtos.service.ProdutoCRMService;
import br.com.crmbonus.produtos.service.impl.ProdutoCRMServiceImpl;
import br.com.crmbonus.produtos.query.ProdutoCRMQueryHelper;
import br.com.crmbonus.common.integration.EmpresaCredentialsValidationService;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

public class AcaoCadastrarProdutoCRM implements AcaoRotinaJava {

    private ProdutoCRMService service = new ProdutoCRMServiceImpl();
    private final EmpresaCredentialsValidationService credentialsValidationService = new EmpresaCredentialsValidationService();

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {

        System.out.println("\n[CRM-BONUS] ===== INÍCIO AÇÃO CADASTRO LOTE =====");

        // Obter CODEMP a partir dos parâmetros da ação e validar credenciais da empresa
        Object codEmpParam = ctx.getParam("CODEMP");
        BigDecimal codEmp = null;
        if (codEmpParam != null) {
            try {
                if (codEmpParam instanceof BigDecimal) codEmp = (BigDecimal) codEmpParam;
                else codEmp = new BigDecimal(codEmpParam.toString());
            } catch (Exception ex) {
                System.out.println("[CRM-BONUS] Não foi possível converter CODEMP do parâmetro: " + ex.getMessage());
            }
        }

        Optional<String> credValidationError = credentialsValidationService.validate(codEmp);
        if (credValidationError.isPresent()) {
            System.out.println("[CRM-BONUS] " + credValidationError.get());
            ctx.setMensagemRetorno("<b>" + credValidationError.get() + "</b>");
            return;
        }

        EntityFacade facade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = facade.getJdbcWrapper();

        jdbc.openSession();

        try {

            List<ProdutoCRMRequestDTO> produtos = new ArrayList<>();

            for (Registro linha : ctx.getLinhas()) {

                String codProd = linha.getCampo("CODPROD").toString();

                System.out.println("[CRM-BONUS] Preparando SKU para cadastro: " + codProd);

                ProdutoCRMRequestDTO dto =
                        ProdutoCRMQueryHelper.buscarProduto(jdbc, codProd);

                produtos.add(dto);
            }

            if (produtos.isEmpty()) {
                throw new RuntimeException("Nenhum produto selecionado.");
            }

            String retorno = service.cadastrar(produtos, codEmp);

            System.out.println("[CRM-BONUS] Retorno cadastro lote:");
            System.out.println(retorno);

            System.out.println("[CRM-BONUS] Retorno cadastro lote:");
            System.out.println(retorno);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(retorno);

            StringBuilder html = new StringBuilder();

            html.append("<html>");
            html.append("<div style='font-family:Arial;'>");
            html.append("<h2 style='color:#2E86C1;'>Resultado Cadastro CRM</h2>");
            html.append("<hr/>");

            int sucesso = 0;
            int erro = 0;

            for (JsonNode item : root) {

                boolean status = item.path("status").asBoolean();
                JsonNode data = item.path("data");

                if (status) {

                    sucesso++;

                    html.append("<div style='color:green;'>");
                    html.append("✔ <b>SKU:</b> ")
                            .append(data.path("sku").asText())
                            .append(" | <b>CRM ID:</b> ")
                            .append(data.path("crm_id").asText());
                    html.append("</div><br/>");

                } else {

                    erro++;

                    html.append("<div style='color:red;'>");
                    html.append("✖ <b>SKU:</b> ")
                            .append(data.path("sku").asText())
                            .append(" | <b>Erro:</b> ")
                            .append(data.path("erro").asText());
                    html.append("</div><br/>");
                }
            }

            html.append("<hr/>");
            html.append("<b>Total Sucesso:</b> ").append(sucesso).append("<br/>");
            html.append("<b>Total Erros:</b> ").append(erro).append("<br/>");

            html.append("</div>");
            html.append("</html>");

            ctx.setMensagemRetorno(html.toString());

        } catch (Exception e) {

            System.out.println("[CRM-BONUS] ERRO NA ACTION CADASTRO:");
            e.printStackTrace();
            throw e;

        } finally {
            jdbc.closeSession();
            System.out.println("[CRM-BONUS] ===== FIM AÇÃO CADASTRO =====\n");
        }
    }
}