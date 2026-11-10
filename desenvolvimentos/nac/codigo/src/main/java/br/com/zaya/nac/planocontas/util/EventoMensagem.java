package br.com.zaya.nac.planocontas.util;

public final class EventoMensagem {

    private EventoMensagem() {
    }

    public static String erro(Throwable erro, String idExecucao) {
        return "<html>"
                + "<div style='font-family:Arial;padding:12px;border-left:5px solid #c62828;"
                + "background:#ffebee;color:#7f0000'>"
                + "<h3 style='margin:0 0 8px'>Parceiro e conta contábil não gravados</h3>"
                + "<b>Motivo:</b> " + escapar(mensagemRaiz(erro)) + "<br>"
                + "<p style='margin:8px 0'>O salvamento automático foi interrompido e "
                + "solicitou rollback. O parceiro, a conta contábil, o vínculo referencial "
                + "e a atualização da TGFPAR desta execução não devem permanecer gravados.</p>"
                + "<small>Execução: " + escapar(idExecucao)
                + " — informe este código ao suporte.</small>"
                + "</div></html>";
    }

    private static String mensagemRaiz(Throwable erro) {
        Throwable atual = erro;
        while (atual.getCause() != null) {
            atual = atual.getCause();
        }
        return atual.getMessage() == null
                ? atual.getClass().getSimpleName()
                : atual.getMessage();
    }

    private static String escapar(Object valor) {
        String texto = valor == null ? "" : valor.toString();
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
