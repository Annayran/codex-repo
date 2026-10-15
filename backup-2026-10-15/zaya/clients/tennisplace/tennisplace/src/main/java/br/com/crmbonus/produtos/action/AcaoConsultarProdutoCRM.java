package br.com.crmbonus.produtos.action;

import br.com.crmbonus.produtos.service.ProdutoCRMService;
import br.com.crmbonus.produtos.service.impl.ProdutoCRMServiceImpl;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AcaoConsultarProdutoCRM implements AcaoRotinaJava {

    private ProdutoCRMService service = new ProdutoCRMServiceImpl();

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {

        for (Registro linha : ctx.getLinhas()) {

            String codProd = linha.getCampo("CODPROD").toString();

            String retorno = service.consultar(codProd);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(retorno);

            int total = root.path("total_products").asInt();

            if (total == 0) {
                ctx.setMensagemRetorno("<b>Nenhum produto encontrado no CRM.</b>");
                return;
            }

            JsonNode produto = root.path("products").get(0);

// 🔥 Tratamento de Status
            boolean status = produto.path("status").asBoolean();
            String statusFormatado = status
                    ? "<span style='color:green'><b>ITEM ATIVO</b></span>"
                    : "<span style='color:red'><b>ITEM INATIVO</b></span>";

// 🔥 Tratamento de Datas
            DateTimeFormatter entrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter saida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String criadoEm = produto.path("created").asText();
            String modificadoEm = produto.path("modified").asText();

            String criadoFormatado = "";
            String modificadoFormatado = "";

            if (!criadoEm.isEmpty()) {
                criadoFormatado = LocalDateTime.parse(criadoEm, entrada).format(saida);
            }

            if (!modificadoEm.isEmpty()) {
                modificadoFormatado = LocalDateTime.parse(modificadoEm, entrada).format(saida);
            }

// 🔥 Montagem HTML
            StringBuilder html = new StringBuilder();

            html.append("<html>");
            html.append("<div style='font-family:Arial;'>");

            html.append("<h2 style='color:#2E86C1;'>Consulta CRM Bonus</h2>");
            html.append("<hr/>");

            html.append("<b>ID CRM:</b> ").append(produto.path("id").asText()).append("<br/>");
            html.append("<b>SKU:</b> ").append(produto.path("sku").asText()).append("<br/>");
            html.append("<b>Nome:</b> ").append(produto.path("name").asText()).append("<br/>");
            html.append("<b>Unidade:</b> ").append(produto.path("unit_measurement").asText()).append("<br/>");

            html.append("<b>Preço:</b> ")
                    .append(produto.path("value_of").asText())
                    .append(" ")
                    .append(produto.path("currency").asText())
                    .append("<br/>");

            html.append("<b>Status:</b> ").append(statusFormatado).append("<br/><br/>");

            html.append("<b>Criado em:</b> ").append(criadoFormatado).append("<br/>");
            html.append("<b>Última Modificação:</b> ").append(modificadoFormatado).append("<br/>");

            html.append("</div>");
            html.append("</html>");

            ctx.setMensagemRetorno(html.toString());
        }
    }
}
