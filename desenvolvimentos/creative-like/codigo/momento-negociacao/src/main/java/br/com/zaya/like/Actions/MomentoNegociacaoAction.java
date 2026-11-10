package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.like.Actions.services.MomentoNegociacaoService;

public class MomentoNegociacaoAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        try {
            Registro[] linhas = contexto.getLinhas();

            if (linhas == null || linhas.length == 0) {
                contexto.mostraErro("Selecione uma negociacao para consultar o momento atual.");
                return;
            }

            StringBuilder retorno = new StringBuilder();

            for (Registro linha : linhas) {
                BigDecimal nunegociacao = (BigDecimal) linha.getCampo("NUNEGOCIACAO");

                if (nunegociacao == null) {
                    contexto.mostraErro("A linha selecionada nao possui o campo NUNEGOCIACAO.");
                    return;
                }

                retorno.append(MomentoNegociacaoService.gerarHtmlMomento(nunegociacao));
            }

            contexto.setMensagemRetorno(retorno.toString());
        } catch (Exception e) {
            contexto.mostraErro("<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"></font>"
                    + "<b>Atencao: </b>Erro ao consultar o momento da negociacao.<br><br>"
                    + "<b>Motivo: </b>" + escapeHtml(e.getMessage()) + "</p>");
        }
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
