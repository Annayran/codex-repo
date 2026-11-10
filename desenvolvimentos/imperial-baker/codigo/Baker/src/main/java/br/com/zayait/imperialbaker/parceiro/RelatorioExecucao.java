package br.com.zayait.imperialbaker.parceiro;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

final class RelatorioExecucao {
    private static final Logger LOG = Logger.getLogger("br.com.zayait.imperialbaker.receitaws");
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss XXX");
    private static final int LIMITE_DETALHES = 30000;
    private final String id = UUID.randomUUID().toString();
    private final String inicio = agora();
    private final long inicioNanos = System.nanoTime();
    private final StringBuilder detalhes = new StringBuilder();
    private final StringBuilder detalhesHtml = new StringBuilder();
    private boolean truncado;
    private boolean htmlTruncado;
    private String fim;
    private long duracaoMs;

    RelatorioExecucao(String origem) {
        registrar("INICIO " + origem + "; horario=" + inicio);
    }

    void registrar(String mensagem) {
        LOG.info("[ReceitaWS][" + id + "][" + agora() + "] " + mensagem);
    }

    void resultado(ReceitaWsParceiroService.ResultadoAtualizacao resultado) {
        detalheTexto("CODPARC " + resultado.codParc + ": " + resultado.mensagem);
        adicionarHtml(parceiroHtml(resultado));
    }

    void detalhe(String mensagem) {
        detalheTexto(mensagem);
        adicionarHtml("<p>" + html(mensagem) + "</p>");
    }

    private void detalheTexto(String mensagem) {
        registrar(mensagem);
        if (detalhes.length() + mensagem.length() + 1 <= LIMITE_DETALHES) {
            detalhes.append(mensagem).append('\n');
        } else {
            truncado = true;
        }
    }

    void falha(String referencia, Exception erro) {
        detalhe(referencia + ": nao foi possivel concluir a atualizacao. Consulte o suporte com o ID da execucao.");
        erroTecnico(referencia, erro, Level.SEVERE);
    }

    void aviso(String referencia, Exception erro) {
        erroTecnico(referencia, erro, Level.WARNING);
    }

    private void erroTecnico(String referencia, Exception erro, Level nivelLog) {
        LOG.log(nivelLog, "[ReceitaWS][" + id + "] " + referencia);
        // Mensagens de bibliotecas podem conter payloads e dados pessoais.
        Throwable causa = erro;
        for (int nivel = 0; causa != null && nivel < 10; nivel++) {
            LOG.log(nivelLog, "[ReceitaWS][" + id + "] causa=" + causa.getClass().getName());
            if (causa instanceof SQLException) {
                SQLException sql = (SQLException) causa;
                LOG.log(nivelLog, "[ReceitaWS][" + id + "] SQLState=" + sql.getSQLState()
                        + "; codigoBanco=" + sql.getErrorCode());
            }
            for (StackTraceElement frame : causa.getStackTrace()) {
                LOG.log(nivelLog, "[ReceitaWS][" + id + "] at " + frame);
            }
            causa = causa.getCause();
        }
    }

    String finalizar(String resumo) {
        fim = agora();
        duracaoMs = (System.nanoTime() - inicioNanos) / 1000000L;
        String cabecalho = "Execucao " + id + "\nInicio: " + inicio + "\nFim: " + fim
                + "\nDuracao: " + duracaoMs + " ms\n" + resumo;
        registrar("FIM " + cabecalho.replace('\n', ' '));
        return cabecalho + "\n\n" + detalhes
                + (truncado ? "\nExibicao limitada. Consulte os demais detalhes no log pelo ID da execucao." : "");
    }

    String finalizarHtml(int atualizados, int mantidos, int falhas) {
        finalizar("Atualizados: " + atualizados + "; sem alteracao: " + mantidos + "; falhas: " + falhas);
        String titulo = falhas > 0 ? "Consulta finalizada com pendencias" : "Consulta ReceitaWS finalizada";
        return "<div style=\"font-family:Arial,sans-serif;font-size:13px;line-height:1.5;color:#24332e;max-width:760px;word-wrap:break-word;\">"
                + "<h3 style=\"font-size:18px;margin:0 0 10px;\">" + titulo + "</h3>"
                + "<p style=\"margin:0 0 14px;\"><strong>Atualizados:</strong> " + atualizados
                + " &nbsp; | &nbsp; <strong>Sem altera&ccedil;&atilde;o:</strong> " + mantidos
                + " &nbsp; | &nbsp; <strong>Pend&ecirc;ncias:</strong> " + falhas + "</p>"
                + detalhesHtml
                + (htmlTruncado ? "<p>Alguns parceiros n&atilde;o foram exibidos devido ao tamanho do retorno. Consulte o suporte com a refer&ecirc;ncia abaixo.</p>" : "")
                + "<div style=\"border-top:1px solid #dce3df;margin-top:16px;padding-top:10px;font-size:11px;color:#52625a;\">"
                + "In&iacute;cio: " + html(inicio) + "<br>Fim: " + html(fim) + "<br>Dura&ccedil;&atilde;o: " + duracaoMs + " ms"
                + "<br>Refer&ecirc;ncia: " + html(id) + "</div></div>";
    }

    private void adicionarHtml(String bloco) {
        // Nao corta uma tabela ou entidade HTML no meio quando o lote e grande.
        if (detalhesHtml.length() + bloco.length() <= LIMITE_DETALHES) {
            detalhesHtml.append(bloco);
        } else {
            htmlTruncado = true;
        }
    }

    private static String parceiroHtml(ReceitaWsParceiroService.ResultadoAtualizacao parceiro) {
        String nome = valor(parceiro.nomeParceiro);
        StringBuilder bloco = new StringBuilder("<div style=\"border-top:1px solid #dce3df;padding:12px 0;\">");
        bloco.append("<strong style=\"font-size:14px;\">").append(html(nome.isEmpty() ? "Parceiro" : nome)).append("</strong>")
                .append("<div style=\"color:#52625a;margin:3px 0 8px;\">C&oacute;digo Sankhya: ")
                .append(html(valor(parceiro.codParc))).append(" &nbsp; | &nbsp; CNPJ: ")
                .append(html(formatarCnpj(parceiro.cnpj))).append("</div>");
        if (parceiro.atualizado) {
            bloco.append("<p style=\"color:#08724d;margin:0 0 8px;\"><strong>Atualiza&ccedil;&atilde;o realizada</strong></p>")
                    .append("<table style=\"width:100%;border-collapse:collapse;table-layout:fixed;font-size:12px;\">")
                    .append("<thead><tr style=\"background:#edf3f0;text-align:left;\">")
                    .append(celula("Campo", true)).append(celula("Antes", true)).append(celula("Depois", true))
                    .append("</tr></thead><tbody>");
            for (Map.Entry<String, Object> campo : parceiro.novos.entrySet()) {
                Object anterior = parceiro.anteriores.get(campo.getKey());
                if (!valor(anterior).equals(valor(campo.getValue()))) {
                    bloco.append("<tr>").append(celula(nomeCampo(campo.getKey()), false))
                            .append(celula(valorCampo(campo.getKey(), anterior), false))
                            .append(celula(valorCampo(campo.getKey(), campo.getValue()), false)).append("</tr>");
                }
            }
            bloco.append("</tbody></table>");
        } else {
            bloco.append("<p style=\"margin:0;\"><strong>Sem altera&ccedil;&atilde;o.</strong> ")
                    .append(html(parceiro.mensagem)).append("</p>");
        }
        return bloco.append("</div>").toString();
    }

    private static String celula(String texto, boolean titulo) {
        String tag = titulo ? "th" : "td";
        return "<" + tag + " style=\"padding:8px;border-bottom:1px solid #dce3df;vertical-align:top;word-wrap:break-word;overflow-wrap:anywhere;\">"
                + html(texto).replace("\n", "<br>") + "</" + tag + ">";
    }

    private static String nomeCampo(String campo) {
        switch (campo) {
            case "CGC_CPF": return "CNPJ";
            case "TIPPESSOA": return "Tipo de pessoa";
            case "RAZAOSOCIAL": return "Raz\u00e3o social";
            case "NOMEPARC": return "Nome do parceiro";
            case "ATIVO": return "Cadastro ativo";
            case "EMAIL": return "E-mail";
            case "TELEFONE": return "Telefone";
            case "AD_EMAILRECEITA": return "E-mail da ReceitaWS";
            case "AD_TELRECEITA": return "Telefone da ReceitaWS";
            case "SIMPLES": return "Simples Nacional";
            default: return "Dado cadastral";
        }
    }

    private static String valorCampo(String campo, Object conteudo) {
        String texto = valor(conteudo);
        if (texto.isEmpty()) return "N\u00e3o informado";
        if ("CGC_CPF".equals(campo)) return formatarCnpj(texto);
        if ("SIMPLES".equals(campo)) {
            if ("S".equals(texto)) return "Optante";
            if ("N".equals(texto)) return "N\u00e3o optante";
        }
        if ("ATIVO".equals(campo)) {
            if ("S".equals(texto)) return "Sim";
            if ("N".equals(texto)) return "N\u00e3o";
        }
        if ("TIPPESSOA".equals(campo)) {
            if ("J".equals(texto)) return "Pessoa jur\u00eddica";
            if ("F".equals(texto)) return "Pessoa f\u00edsica";
        }
        return texto;
    }

    private static String formatarCnpj(String texto) {
        String numero = valor(texto).replaceAll("\\D", "");
        if (numero.length() != 14) return numero.isEmpty() ? "N\u00e3o informado" : numero;
        return numero.substring(0, 2) + "." + numero.substring(2, 5) + "." + numero.substring(5, 8)
                + "/" + numero.substring(8, 12) + "-" + numero.substring(12);
    }

    private static String html(String texto) {
        return valor(texto).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    static String diferencas(Map<String, Object> anteriores, Map<String, Object> novos) {
        StringBuilder resultado = new StringBuilder();
        for (Map.Entry<String, Object> campo : novos.entrySet()) {
            Object anterior = anteriores.get(campo.getKey());
            if (!valor(anterior).equals(valor(campo.getValue()))) {
                if (resultado.length() > 0) resultado.append("; ");
                resultado.append(campo.getKey()).append(": ")
                        .append(exibir(campo.getKey(), anterior)).append(" -> ")
                        .append(exibir(campo.getKey(), campo.getValue()));
            }
        }
        return resultado.toString();
    }

    private static String exibir(String campo, Object conteudo) {
        String texto = valor(conteudo);
        if (texto.isEmpty()) return "[vazio]";
        if (("SIMPLES".equals(campo) || "ATIVO".equals(campo)) && ("S".equals(texto) || "N".equals(texto))) {
            return texto + ("S".equals(texto) ? " (Sim)" : " (Nao)");
        }
        if ("TIPPESSOA".equals(campo) && ("J".equals(texto) || "F".equals(texto))) return texto;
        return "[valor protegido]";
    }

    private static String valor(Object conteudo) {
        return conteudo == null ? "" : conteudo.toString();
    }

    private static String agora() {
        return ZonedDateTime.now().format(FORMATO);
    }
}
