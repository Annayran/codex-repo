package br.com.zayait.imperialbaker.parceiro;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Botao de Acao para tela/grade de notas TGFCAB.
 *
 * Fluxo:
 * - Usuario seleciona uma ou mais notas.
 * - O botao le TGFCAB.CODPARC de cada nota selecionada.
 * - Para cada parceiro distinto, consulta ReceitaWS e atualiza TGFPAR.
 */
public class BotaoAtualizaParceiroReceitaWs implements AcaoRotinaJava {

    private final ReceitaWsParceiroService service = new ReceitaWsParceiroService();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        Registro[] linhas = contexto.getLinhas();
        if (linhas == null || linhas.length == 0) {
            contexto.setMensagemRetorno("Nenhuma nota selecionada na TGFCAB.");
            return;
        }

        int atualizados = 0;
        int ignorados = 0;
        int falhas = 0;
        StringBuilder detalhesFalhas = new StringBuilder();
        Set<BigDecimal> parceirosProcessados = new HashSet<BigDecimal>();

        for (int i = 0; i < linhas.length; i++) {
            BigDecimal codParc = asBigDecimal(linhas[i].getCampo("CODPARC"));
            if (codParc == null) {
                falhas++;
                detalhesFalhas.append("Linha ").append(i + 1).append(": CODPARC nao encontrado. ");
                continue;
            }

            if (!parceirosProcessados.add(codParc)) {
                continue;
            }

            try {
                ReceitaWsParceiroService.ResultadoAtualizacao resultado = service.atualizarParceiro(codParc);
                if (resultado.atualizado) {
                    atualizados++;
                } else {
                    ignorados++;
                }
            } catch (Exception e) {
                falhas++;
                detalhesFalhas
                        .append("CODPARC ")
                        .append(codParc)
                        .append(": ")
                        .append(e.getMessage())
                        .append(". ");
            }
        }

        contexto.setMensagemRetorno(
                "Atualizacao ReceitaWS concluida. Parceiros atualizados: " + atualizados +
                        ". Parceiros sem atualizacao: " + ignorados +
                        ". Falhas: " + falhas +
                        (detalhesFalhas.length() > 0 ? ". " + detalhesFalhas.toString() : ".")
        );
    }

    private BigDecimal asBigDecimal(Object valor) {
        if (valor == null) return null;
        if (valor instanceof BigDecimal) return (BigDecimal) valor;
        return new BigDecimal(String.valueOf(valor));
    }
}
