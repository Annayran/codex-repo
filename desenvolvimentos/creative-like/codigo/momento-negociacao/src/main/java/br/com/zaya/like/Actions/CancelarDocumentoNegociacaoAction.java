package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.like.Actions.dto.ResultadoSuprimentoDTO;
import br.com.zaya.like.Actions.services.MomentoNegociacaoService;

public class CancelarDocumentoNegociacaoAction implements AcaoRotinaJava {

    @Override
    public void doAction(final ContextoAcao contexto) throws Exception {
        try {
            Registro[] linhas = contexto.getLinhas();

            if (linhas == null || linhas.length == 0) {
                contexto.mostraErro("Selecione uma negociacao para cancelar itens do documento.");
                return;
            }

            if (linhas.length > 1) {
                contexto.mostraErro("Selecione apenas uma negociacao para executar o cancelamento pelo modal.");
                return;
            }

            final BigDecimal nunegociacao = (BigDecimal) linhas[0].getCampo("NUNEGOCIACAO");

            if (nunegociacao == null) {
                contexto.mostraErro("A linha selecionada nao possui o campo NUNEGOCIACAO.");
                return;
            }

            final String tipoDocumento = obterParametroTexto(contexto, "P_TIPO_DOCUMENTO");
            final String motivo = obterParametroTexto(contexto, "P_MOTIVO");
            final BigDecimal codUsuario = contexto.getUsuarioLogado();
            final String tipoNormalizado = normalizarTipo(tipoDocumento);
            ResultadoSuprimentoDTO resultado;

            if ("TUDO".equals(tipoNormalizado)) {
                resultado = MomentoNegociacaoService.cancelarTudoNegociacao(nunegociacao, motivo, codUsuario);
            } else if ("PROD".equals(tipoNormalizado)) {
                resultado = MomentoNegociacaoService.cancelarProducaoNegociacao(nunegociacao, motivo, codUsuario);
            } else {
                final BigDecimal nunotaDocumento = obterParametroBigDecimal(contexto, "P_NUNOTA_DOCUMENTO", "P_NUNOTA");
                final BigDecimal sequencia = obterParametroBigDecimalOpcional(contexto, "P_SEQUENCIA", "P_SEQ");

                if (sequencia == null) {
                    resultado = MomentoNegociacaoService.cancelarItensDocumento(nunegociacao, nunotaDocumento,
                            tipoNormalizado, motivo, codUsuario);
                } else {
                    resultado = MomentoNegociacaoService.cancelarItemDocumento(nunegociacao, nunotaDocumento,
                            tipoNormalizado, sequencia, motivo, codUsuario);
                }
            }

            contexto.setMensagemRetorno(MomentoNegociacaoService.montarMensagemResultado(resultado, true));
        } catch (Exception e) {
            contexto.mostraErro("<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"></font>"
                    + "<b>Atencao: </b>Erro ao cancelar documento da negociacao.<br><br>"
                    + "<b>Motivo: </b>" + escapeHtml(e.getMessage()) + "</p>");
        }
    }

    private static BigDecimal obterParametroBigDecimal(ContextoAcao contexto, String nomePrincipal, String nomeAlternativo)
            throws Exception {
        BigDecimal valor = obterParametroBigDecimalOpcional(contexto, nomePrincipal, nomeAlternativo);

        if (valor == null) {
            throw new Exception("Informe o parametro " + nomePrincipal + ".");
        }

        return valor;
    }

    private static BigDecimal obterParametroBigDecimalOpcional(ContextoAcao contexto, String nomePrincipal,
            String nomeAlternativo) throws Exception {
        Object valor = contexto.getParam(nomePrincipal);

        if (valor == null && nomeAlternativo != null) {
            valor = contexto.getParam(nomeAlternativo);
        }

        if (valor == null || String.valueOf(valor).trim().isEmpty()) {
            return null;
        }

        if (valor instanceof BigDecimal) {
            return (BigDecimal) valor;
        }

        return new BigDecimal(String.valueOf(valor).trim().replaceAll("[^0-9]", ""));
    }

    private static String obterParametroTexto(ContextoAcao contexto, String nome) throws Exception {
        Object param = contexto.getParam(nome);
        String valor = param == null ? "" : String.valueOf(param).trim();

        if (valor.isEmpty()) {
            throw new Exception("Informe o parametro " + nome + ".");
        }

        return valor;
    }

    private static String normalizarTipo(String tipoDocumento) throws Exception {
        String tipo = tipoDocumento == null ? "" : tipoDocumento.trim().toUpperCase();

        if ("ALL".equals(tipo) || "TODOS".equals(tipo) || "COMPLETO".equals(tipo) || "CANCELAR_TUDO".equals(tipo)) {
            return "TUDO";
        }

        if ("PRODUCAO".equals(tipo) || "PRODUÇÃO".equals(tipo) || "PRD".equals(tipo)) {
            return "PROD";
        }

        if ("PV".equals(tipo) || "MI".equals(tipo) || "TUDO".equals(tipo) || "PROD".equals(tipo)) {
            return tipo;
        }

        throw new Exception("Tipo de cancelamento invalido: " + tipoDocumento
                + ". Use PV, MI, PROD ou TUDO.");
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
