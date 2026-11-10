package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.like.Actions.dto.ResultadoSuprimentoDTO;
import br.com.zaya.like.Actions.services.MomentoNegociacaoService;

public class CancelarSuprimentoNegociacaoAction implements AcaoRotinaJava {

    @Override
    public void doAction(final ContextoAcao contexto) throws Exception {
        try {
            final Registro[] linhas = contexto.getLinhas();

            if (linhas == null || linhas.length == 0) {
                contexto.mostraErro("Selecione uma negociacao para cancelar os itens de suprimentos.");
                return;
            }

            final String motivo = obterMotivo(contexto);
            final BigDecimal codUsuario = contexto.getUsuarioLogado();
            final StringBuilder retorno = new StringBuilder();

            for (Registro linha : linhas) {
                BigDecimal nunegociacao = (BigDecimal) linha.getCampo("NUNEGOCIACAO");

                if (nunegociacao == null) {
                    throw new Exception("A linha selecionada nao possui o campo NUNEGOCIACAO.");
                }

                ResultadoSuprimentoDTO resultado = MomentoNegociacaoService.cancelarSuprimentos(nunegociacao,
                        motivo, codUsuario);
                retorno.append(MomentoNegociacaoService.montarMensagemResultado(resultado, true))
                        .append("<br><br>");
            }

            contexto.setMensagemRetorno(retorno.toString());
        } catch (Exception e) {
            contexto.mostraErro("<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"></font>"
                    + "<b>Atencao: </b>Erro ao cancelar suprimentos da negociacao.<br><br>"
                    + "<b>Motivo: </b>" + escapeHtml(e.getMessage()) + "</p>");
        }
    }

    private static String obterMotivo(ContextoAcao contexto) throws Exception {
        Object param = contexto.getParam("P_MOTIVO");
        String motivo = param == null ? "" : String.valueOf(param).trim();

        if (motivo.isEmpty()) {
            throw new Exception("Informe o parametro P_MOTIVO com o motivo do cancelamento.");
        }

        return motivo;
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
