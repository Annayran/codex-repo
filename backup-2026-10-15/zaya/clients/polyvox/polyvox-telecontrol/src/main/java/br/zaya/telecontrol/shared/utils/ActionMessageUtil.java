package br.zaya.telecontrol.shared.utils;

public class ActionMessageUtil {
    public static String erroAmigavel(String titulo, Exception e) {
        String mensagem = e == null ? "" : String.valueOf(e.getMessage());
        String detalhes = primeiroValor(mensagem, e == null ? null : e.toString(), "Erro nao informado");
        Diagnostico diagnostico = diagnosticar(detalhes);

        return "<div style='font-family:Arial,sans-serif;line-height:1.35;color:#263238;'>"
                + "<h3 style='margin:0 0 8px 0;color:" + diagnostico.cor + ";'>" + escape(titulo) + "</h3>"
                + "<p style='margin:0 0 8px 0;'><b>Situacao:</b> " + diagnostico.situacao + "</p>"
                + "<p style='margin:0 0 8px 0;'><b>Onde atuar:</b> " + diagnostico.acao + "</p>"
                + "<p style='margin:0 0 8px 0;'><b>Registro:</b> " + escape(valorOuPadrao(LogUtil.REGISTRO, "Nao identificado")) + "</p>"
                + "<p style='margin:0 0 8px 0;'><b>Metodo/URL:</b> "
                + escape(valorOuPadrao(LogUtil.METHOD, "Nao identificado")) + " "
                + escape(valorOuPadrao(LogUtil.URL, "URL nao identificada")) + "</p>"
                + "<details style='margin-top:10px;'>"
                + "<summary>Detalhes tecnicos</summary>"
                + "<pre style='white-space:pre-wrap;background:#f5f5f5;border:1px solid #ddd;padding:8px;border-radius:4px;'>"
                + escape(resumir(detalhes, 1800))
                + "</pre>"
                + "</details>"
                + "<p style='margin:10px 0 0 0;color:#607d8b;'>O detalhe completo foi registrado em AD_TCLLGT.</p>"
                + "</div>";
    }

    public static String sucesso(String mensagem) {
        return "<div style='font-family:Arial,sans-serif;line-height:1.35;color:#1b5e20;'>"
                + "<h3 style='margin:0 0 8px 0;'>Processo finalizado</h3>"
                + "<p style='margin:0;'>" + escape(valorOuPadrao(mensagem, "Operacao concluida.")) + "</p>"
                + "</div>";
    }

    private static Diagnostico diagnosticar(String detalhes) {
        String texto = detalhes == null ? "" : detalhes.toLowerCase();
        int status = extrairStatus(texto);

        if (texto.contains("produto nao encontrado") || texto.contains("produto n\\u00e3o encontrado")) {
            return new Diagnostico("Erro de cadastro: produto nao encontrado na Telecontrol.",
                    "Validar a referencia do produto/peca enviada e o cadastro correspondente no Telecontrol.",
                    "#b71c1c");
        }
        if (texto.contains("duplicate key") || texto.contains("uniqueconstraintviolationexception")
                || texto.contains("duplicidade")) {
            return new Diagnostico("Registro ja existe na Telecontrol.",
                    "Conferir se a fila local deve ser marcada como processada ou se ha duplicidade em AD_TCLLOGSER/entidade de origem.",
                    "#ef6c00");
        }
        if (status == 400 || status == 422) {
            return new Diagnostico("Erro de validacao dos dados enviados.",
                    "Revisar os campos obrigatorios, formatos de data, CNPJ, referencia e payload gravado em AD_TCLLGT.REQUEST.",
                    "#ef6c00");
        }
        if (status == 401 || status == 403) {
            return new Diagnostico("Erro de autenticacao ou permissao na API Telecontrol.",
                    "Validar token, ambiente e credenciais em AD_TCLCFG antes de reenviar.",
                    "#b71c1c");
        }
        if (status == 404) {
            return new Diagnostico("Recurso nao encontrado na API Telecontrol.",
                    "Confirmar endpoint configurado em AD_TCLCFG e confirmar se o cadastro relacionado existe na Telecontrol.",
                    "#b71c1c");
        }
        if (status == 409) {
            return new Diagnostico("Conflito de dados na API Telecontrol.",
                    "Verificar duplicidade ou estado ja processado no Telecontrol e na fila local.",
                    "#ef6c00");
        }
        if (status >= 500) {
            return new Diagnostico("Erro interno retornado pela API Telecontrol.",
                    "Consultar AD_TCLLGT.RESPONSE e acionar suporte Telecontrol se o payload estiver correto.",
                    "#b71c1c");
        }
        if (texto.contains("connect") || texto.contains("timeout") || texto.contains("timed out")
                || texto.contains("connection")) {
            return new Diagnostico("Falha de comunicacao com a API Telecontrol.",
                    "Validar internet, DNS, liberacao de firewall/proxy e disponibilidade da API.",
                    "#b71c1c");
        }
        if (texto.contains("configuracao telecontrol nao encontrada") || texto.contains("ad_tclcfg")) {
            return new Diagnostico("Configuracao Telecontrol nao encontrada ou incompleta.",
                    "Validar parametros e cadastro AD_TCLCFG no Sankhya.",
                    "#b71c1c");
        }
        return new Diagnostico("Erro nao classificado automaticamente.",
                "Consultar AD_TCLLGT para ver request, response e stack trace; se necessario, acionar suporte tecnico.",
                "#b71c1c");
    }

    private static int extrairStatus(String texto) {
        String marcador = "response code :";
        int index = texto.indexOf(marcador);
        if (index < 0) {
            return -1;
        }
        int start = index + marcador.length();
        while (start < texto.length() && !Character.isDigit(texto.charAt(start))) {
            start++;
        }
        int end = start;
        while (end < texto.length() && Character.isDigit(texto.charAt(end))) {
            end++;
        }
        if (start == end) {
            return -1;
        }
        try {
            return Integer.parseInt(texto.substring(start, end));
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    private static String primeiroValor(String primeiro, String segundo, String padrao) {
        if (primeiro != null && primeiro.trim().length() > 0) {
            return primeiro;
        }
        if (segundo != null && segundo.trim().length() > 0) {
            return segundo;
        }
        return padrao;
    }

    private static String valorOuPadrao(String value, String padrao) {
        return value == null || value.trim().length() == 0 ? padrao : value;
    }

    private static String resumir(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength) + "\n... conteudo reduzido. Consulte AD_TCLLGT para o detalhe completo.";
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static class Diagnostico {
        private final String situacao;
        private final String acao;
        private final String cor;

        private Diagnostico(String situacao, String acao, String cor) {
            this.situacao = situacao;
            this.acao = acao;
            this.cor = cor;
        }
    }
}
