package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.like.Actions.dto.ResultadoSuprimentoDTO;
import br.com.zaya.like.Actions.services.MomentoNegociacaoService;

public class CancelarItemPedidoVendaNegociacaoAction implements AcaoRotinaJava {

    @Override
    public void doAction(final ContextoAcao contexto) throws Exception {
        try {
            Registro[] linhas = contexto.getLinhas();

            if (linhas == null || linhas.length == 0) {
                contexto.mostraErro("Selecione uma negociacao para cancelar o item do Pedido de Venda.");
                return;
            }

            if (linhas.length > 1) {
                contexto.mostraErro("Selecione apenas uma negociacao para cancelar um item especifico do Pedido de Venda.");
                return;
            }

            final BigDecimal nunegociacao = (BigDecimal) linhas[0].getCampo("NUNEGOCIACAO");

            if (nunegociacao == null) {
                contexto.mostraErro("A linha selecionada nao possui o campo NUNEGOCIACAO.");
                return;
            }

            final BigDecimal nunotaPedidoVenda = obterParametroBigDecimal(contexto, "P_NUNOTA_PEDVENDA", "P_NUNOTA");
            final BigDecimal sequencia = obterParametroBigDecimal(contexto, "P_SEQUENCIA", "P_SEQ");
            final String motivo = obterMotivo(contexto);
            final BigDecimal codUsuario = contexto.getUsuarioLogado();

            ResultadoSuprimentoDTO resultado = MomentoNegociacaoService.cancelarItemPedidoVenda(nunegociacao,
                    nunotaPedidoVenda, sequencia, motivo, codUsuario);
            contexto.setMensagemRetorno(MomentoNegociacaoService.montarMensagemResultado(resultado, true));
        } catch (Exception e) {
            contexto.mostraErro("<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"></font>"
                    + "<b>Atencao: </b>Erro ao cancelar item do Pedido de Venda.<br><br>"
                    + "<b>Motivo: </b>" + escapeHtml(e.getMessage()) + "</p>");
        }
    }

    private static BigDecimal obterParametroBigDecimal(ContextoAcao contexto, String nomePrincipal, String nomeAlternativo)
            throws Exception {
        Object valor = contexto.getParam(nomePrincipal);

        if (valor == null && nomeAlternativo != null) {
            valor = contexto.getParam(nomeAlternativo);
        }

        if (valor == null || String.valueOf(valor).trim().isEmpty()) {
            throw new Exception("Informe o parametro " + nomePrincipal + ".");
        }

        if (valor instanceof BigDecimal) {
            return (BigDecimal) valor;
        }

        return new BigDecimal(String.valueOf(valor).trim());
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
