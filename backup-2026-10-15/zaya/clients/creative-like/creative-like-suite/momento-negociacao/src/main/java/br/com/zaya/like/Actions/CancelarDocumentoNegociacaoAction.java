package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.like.Actions.dto.ResultadoSuprimentoDTO;
import br.com.zaya.like.Actions.services.MomentoNegociacaoService;

public class CancelarDocumentoNegociacaoAction implements AcaoRotinaJava {

    @Override
    public void doAction(final ContextoAcao contexto) throws Exception {
        JapeSession.SessionHandle hnd = null;

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

            final BigDecimal nunotaDocumento = obterParametroBigDecimal(contexto, "P_NUNOTA_DOCUMENTO", "P_NUNOTA");
            final String tipoDocumento = obterParametroTexto(contexto, "P_TIPO_DOCUMENTO");
            final String motivo = obterParametroTexto(contexto, "P_MOTIVO");
            final BigDecimal sequencia = obterParametroBigDecimalOpcional(contexto, "P_SEQUENCIA", "P_SEQ");
            final BigDecimal codUsuario = contexto.getUsuarioLogado();
            final String titulo = sequencia == null ? "Cancelar itens do documento" : "Cancelar item do documento";
            final String alvo = sequencia == null
                    ? "todos os itens com saldo do documento " + nunotaDocumento
                    : "o item " + sequencia + " do documento " + nunotaDocumento;

            boolean executar = contexto.confirmarSimNao(titulo,
                    "Deseja cancelar " + alvo + " vinculado a negociacao " + nunegociacao + "?", 1);

            if (!executar) {
                contexto.setMensagemRetorno("Operacao cancelada pelo usuario.");
                return;
            }

            hnd = JapeSession.open();
            hnd.setCanTimeout(false);
            final StringBuilder retorno = new StringBuilder();

            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    ResultadoSuprimentoDTO resultado;

                    if (sequencia == null) {
                        resultado = MomentoNegociacaoService.cancelarItensDocumento(nunegociacao, nunotaDocumento,
                                tipoDocumento, motivo, codUsuario);
                    } else {
                        resultado = MomentoNegociacaoService.cancelarItemDocumento(nunegociacao, nunotaDocumento,
                                tipoDocumento, sequencia, motivo, codUsuario);
                    }

                    retorno.append(MomentoNegociacaoService.montarMensagemResultado(resultado, true));
                }
            });

            contexto.setMensagemRetorno(retorno.toString());
        } catch (Exception e) {
            contexto.mostraErro("<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"></font>"
                    + "<b>Atencao: </b>Erro ao cancelar documento da negociacao.<br><br>"
                    + "<b>Motivo: </b>" + escapeHtml(e.getMessage()) + "</p>");
        } finally {
            JapeSession.close(hnd);
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
