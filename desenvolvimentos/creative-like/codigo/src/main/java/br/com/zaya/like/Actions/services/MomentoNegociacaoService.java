package br.com.zaya.like.Actions.services;

import java.math.BigDecimal;
import java.util.List;

import br.com.zaya.like.Actions.dao.MomentoNegociacaoDAO;
import br.com.zaya.like.Actions.dto.ItemMomentoNegociacaoDTO;
import br.com.zaya.like.Actions.dto.MomentoNegociacaoDTO;

public class MomentoNegociacaoService {

    public static String gerarHtmlMomento(BigDecimal nunegociacao) throws Exception {
        MomentoNegociacaoDTO momento = MomentoNegociacaoDAO.buscarMomento(nunegociacao);
        List<ItemMomentoNegociacaoDTO> itens = MomentoNegociacaoDAO.buscarItensPrincipais(nunegociacao);

        StringBuilder html = new StringBuilder();

        html.append("<div style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#222;\">");
        html.append("<h3 style=\"margin:0 0 12px 0;\">Momento da Negociacao ");
        html.append(escapeHtml(toStr(momento.getNumeroNegociacao(), nunegociacao)));
        html.append("</h3>");

        html.append("<div style=\"display:flex;gap:14px;flex-wrap:wrap;margin-bottom:16px;\">");
        html.append(cardStatus("Suprimento", momento.getStatusSuprimento()));
        html.append(cardStatus("Layout", momento.getStatusLayout()));
        html.append(cardStatus("Producao", momento.getStatusProducaoFinal()));
        html.append(cardStatus("Faturamento", momento.getStatusFaturamento()));
        html.append(cardStatus("Entrega", momento.getStatusEntrega()));
        html.append("</div>");

        html.append("<div style=\"margin:8px 0 10px 0;\">");
        html.append("<b>Cliente:</b> ").append(escapeHtml(toStr(momento.getCliente(), "-")));
        html.append(" &nbsp; <b>Vendedor:</b> ").append(escapeHtml(toStr(momento.getVendedor(), "-")));
        html.append(" &nbsp; <b>Status proposta:</b> ").append(escapeHtml(toStr(momento.getStatusNegociacao(), "-")));
        html.append("</div>");

        html.append(montarResumoItens(itens));
        html.append("</div>");

        return html.toString();
    }

    private static String cardStatus(String titulo, String status) {
        String statusFormatado = toStr(status, "Nao informado");
        String cor = definirCorStatus(statusFormatado);

        return "<div style=\"background:#f4f6f8;min-width:210px;padding:18px 20px;border-left:10px solid "
                + cor + ";box-sizing:border-box;\">"
                + "<b>" + escapeHtml(titulo) + ":</b> " + escapeHtml(statusFormatado)
                + "</div>";
    }

    private static String montarResumoItens(List<ItemMomentoNegociacaoDTO> itens) {
        StringBuilder html = new StringBuilder();

        html.append("<div style=\"margin-top:12px;\">");
        html.append("<b>Itens principais da negociacao:</b>");

        if (itens == null || itens.isEmpty()) {
            html.append("<div style=\"border:1px solid #ddd;padding:8px;margin-top:8px;\">Nenhum item principal encontrado.</div>");
        } else {
            for (ItemMomentoNegociacaoDTO item : itens) {
                html.append("<div style=\"border:1px solid #d9dde2;background:#fff;margin-top:8px;padding:10px;\">");

                html.append("<div style=\"font-size:13px;margin-bottom:8px;\">");
                html.append("<b>Seq. ").append(escapeHtml(toStr(item.getSequencia(), "-"))).append("</b>");
                html.append(" &nbsp; <b>Referencia:</b> ").append(escapeHtml(toStr(item.getReferencia(), "-")));
                html.append("</div>");

                html.append("<div style=\"margin-bottom:8px;line-height:1.35;\">");
                html.append("<b>Produto:</b> ").append(escapeHtml(toStr(item.getDescricaoProduto(), "-")));
                html.append("</div>");

                html.append("<table style=\"width:100%;border-collapse:collapse;font-size:12px;table-layout:fixed;\">");
                html.append("<tr>");
                html.append(celulaInfo("Arte", toStr(item.getTituloArte(), "-")));
                html.append(celulaInfo("Status arte", toStr(item.getStatusArte(), "-")));
                html.append(celulaInfo("Suprimento", toStr(item.getStatusSuprimento(), "-")));
                html.append(celulaInfo("Producao", toStr(item.getStatusProducao(), "-")));
                html.append("</tr>");
                html.append("<tr>");
                html.append(celulaInfo("Qtd. neg.", toStr(item.getQtdNegociada(), "0")));
                html.append(celulaInfo("Disp. estoque", toStr(item.getQtdDisponivelEstoque(), "0")));
                html.append(celulaInfo("Pend. compra", toStr(item.getQtdPendenteCompra(), "0")));
                html.append(celulaInfo("Disp. faturamento", toStr(item.getQtdDisponivelFaturamento(), "0")));
                html.append("</tr>");
                html.append("<tr>");
                html.append(celulaInfo("Qtd. faturada", toStr(item.getQtdFaturada(), "0")));
                html.append(celulaInfo("Lib. CQ", toStr(item.getQtdLiberadaCQ(), "0")));
                html.append(celulaInfo("Lib. estoque", toStr(item.getQtdLiberadaEstoque(), "0")));
                html.append(celulaInfo("Tipo", toStr(item.getTipoProduto(), "-")));
                html.append("</tr>");
                html.append("</table>");

                html.append("</div>");
            }
        }

        html.append("</div>");

        return html.toString();
    }

    private static String definirCorStatus(String status) {
        String s = status == null ? "" : status.toUpperCase();

        if (s.contains("COMPLETO") || s.contains("CONCL") || s.contains("FINALIZ") || s.contains("ENTREG")) {
            return "#00a65a";
        }

        if (s.contains("PEND") || s.contains("ANDAMENTO") || s.contains("PARCIAL") || s.contains("AGUARD")) {
            return "#f0ad4e";
        }

        if (s.contains("BLOQUE") || s.contains("CANCEL") || s.contains("ATRAS") || s.contains("ERRO")) {
            return "#dd4b39";
        }

        return "#333333";
    }

    private static String celulaInfo(String titulo, String valor) {
        return "<td style=\"border:1px solid #e1e4e8;padding:7px;vertical-align:top;word-break:normal;\">"
                + "<span style=\"display:block;color:#555;font-size:11px;\"><b>" + escapeHtml(titulo) + "</b></span>"
                + "<span>" + escapeHtml(toStr(valor, "-")) + "</span>"
                + "</td>";
    }

    private static String toStr(Object value, Object defaultValue) {
        if (value == null) {
            return String.valueOf(defaultValue);
        }

        String s = String.valueOf(value);
        return s.trim().isEmpty() ? String.valueOf(defaultValue) : s;
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
