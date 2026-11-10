package br.com.zaya.nac.planocontas.util;

import br.com.zaya.nac.planocontas.model.ResultadoGeracao;
import br.com.zaya.nac.planocontas.model.FalhaProcessamento;
import br.com.zaya.nac.planocontas.model.ResultadoProcessamentoLote;
import java.util.List;

public final class AcaoMensagem {

    private AcaoMensagem() {
    }

    public static String sucesso(List<ResultadoGeracao> resultados, String idExecucao) {
        ResultadoGeracao primeiro = resultados.get(0);
        StringBuilder contas = new StringBuilder();
        for (ResultadoGeracao resultado : resultados) {
            contas.append("<b>")
                    .append(escapar(descricaoTipo(resultado.getCampoVinculado())))
                    .append(":</b> ")
                    .append(escapar(resultado.getConta()))
                    .append(" — campo ")
                    .append(escapar(resultado.getCampoVinculado()))
                    .append("<br>");
        }
        String titulo = resultados.size() == 1
                ? "Conta contábil criada com sucesso"
                : "Contas contábeis criadas com sucesso";
        return "<html>"
                + "<div style='font-family:Arial;padding:12px;border-left:5px solid #2e7d32;"
                + "background:#e8f5e9;color:#1b5e20'>"
                + "<h3 style='margin:0 0 8px'>" + titulo + "</h3>"
                + "<b>Parceiro:</b> " + escapar(primeiro.getCodigoParceiro() + " - "
                + primeiro.getNomeParceiro()) + "<br>"
                + contas
                + "<p style='margin:8px 0 0'>A criação da(s) conta(s), o(s) vínculo(s) "
                + "referencial(is) e a atualização do parceiro foram confirmados na mesma "
                + "transação.</p>"
                + "<small>Execução: " + escapar(idExecucao) + "</small>"
                + "</div></html>";
    }

    private static String descricaoTipo(String campo) {
        return "CODCTACTB2".equals(campo) ? "Conta de cliente" : "Conta de fornecedor";
    }

    public static String jaProcessado(
            Object codigoParceiro,
            String idExecucao) {
        return "<html>"
                + "<div style='font-family:Arial;padding:12px;border-left:5px solid #1565c0;"
                + "background:#e3f2fd;color:#0d47a1'>"
                + "<h3 style='margin:0 0 8px'>Parceiro já processado</h3>"
                + "<b>Parceiro:</b> " + escapar(codigoParceiro) + "<br>"
                + "<p style='margin:8px 0'>As contas aplicáveis já foram geradas pelo "
                + "desenvolvimento. A proteção impediu uma nova criação e nenhum vínculo "
                + "foi alterado.</p>"
                + "<small>Execução: " + escapar(idExecucao) + "</small>"
                + "</div></html>";
    }

    public static String erro(Throwable erro, String idExecucao) {
        String motivo = mensagemRaiz(erro);
        return "<html>"
                + "<div style='font-family:Arial;padding:12px;border-left:5px solid #c62828;"
                + "background:#ffebee;color:#7f0000'>"
                + "<h3 style='margin:0 0 8px'>Conta contábil não criada</h3>"
                + "<b>Motivo:</b> " + escapar(motivo) + "<br>"
                + "<p style='margin:8px 0'>A ação foi interrompida e solicitou rollback. "
                + "Nenhuma criação ou vínculo desta execução deve permanecer gravado.</p>"
                + "<small>Execução: " + escapar(idExecucao)
                + " — informe este código ao suporte.</small>"
                + "</div></html>";
    }

    public static String resultadoLote(
            ResultadoProcessamentoLote resultado,
            String idExecucao) {
        boolean possuiFalhas = resultado.possuiFalhas();
        String cor = possuiFalhas ? "#ef6c00" : "#2e7d32";
        String fundo = possuiFalhas ? "#fff3e0" : "#e8f5e9";
        String titulo = possuiFalhas
                ? "Processamento concluído com ocorrências"
                : "Processamento concluído";
        StringBuilder detalhesFalhas = new StringBuilder();
        int exibidas = 0;
        for (FalhaProcessamento falha : resultado.getFalhas()) {
            if (exibidas++ >= 10) {
                detalhesFalhas.append("<li>Consulte o log para as demais ocorrências.</li>");
                break;
            }
            detalhesFalhas.append("<li>Parceiro ")
                    .append(escapar(falha.getCodigoParceiro()))
                    .append(": ")
                    .append(escapar(falha.getMotivo()))
                    .append("</li>");
        }

        return "<html>"
                + "<div style='font-family:Arial;padding:12px;border-left:5px solid " + cor
                + ";background:" + fundo + ";color:#263238'>"
                + "<h3 style='margin:0 0 8px'>" + titulo + "</h3>"
                + "<b>Parceiros avaliados:</b> " + resultado.getTotal() + "<br>"
                + "<b>Parceiros atualizados:</b> " + resultado.getParceirosProcessados() + "<br>"
                + "<b>Contas criadas:</b> " + resultado.getContasCriadas() + "<br>"
                + "<b>Já protegidos:</b> " + resultado.getParceirosIgnorados() + "<br>"
                + "<b>Falhas:</b> " + resultado.getQuantidadeFalhas()
                + (possuiFalhas
                        ? "<p style='margin:8px 0 4px'>Cada falha reverteu somente o parceiro "
                                + "afetado; os demais resultados confirmados foram preservados.</p>"
                                + "<ul style='margin:4px 0 8px'>" + detalhesFalhas + "</ul>"
                        : "<p style='margin:8px 0'>Todos os parceiros foram tratados sem falhas.</p>")
                + "<small>Execução: " + escapar(idExecucao) + "</small>"
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
