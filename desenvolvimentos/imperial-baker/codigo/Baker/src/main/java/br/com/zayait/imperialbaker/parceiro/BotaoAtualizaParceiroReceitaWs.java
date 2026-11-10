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
        RelatorioExecucao relatorio = new RelatorioExecucao("Botao TGFCAB");
        int atualizados = 0;
        int ignorados = 0;
        int falhas = 0;
        Set<BigDecimal> parceirosProcessados = new HashSet<BigDecimal>();
        try {
            Registro[] linhas = contexto.getLinhas();
            if (linhas == null || linhas.length == 0) {
                relatorio.detalhe("Nenhuma nota selecionada na TGFCAB.");
                return;
            }
            relatorio.registrar("Notas selecionadas: " + linhas.length);
            for (int i = 0; i < linhas.length; i++) {
                BigDecimal codParc = null;
                try {
                    codParc = asBigDecimal(linhas[i].getCampo("CODPARC"));
                    if (codParc == null) {
                        falhas++;
                        relatorio.detalhe("Linha " + (i + 1) + ": CODPARC nao encontrado.");
                        continue;
                    }
                    if (!parceirosProcessados.add(codParc.stripTrailingZeros())) {
                        relatorio.registrar("CODPARC " + codParc + " repetido nas notas; consulta nao repetida.");
                        continue;
                    }
                    ReceitaWsParceiroService.ResultadoAtualizacao resultado = service.atualizarParceiro(codParc, relatorio);
                    relatorio.resultado(resultado);
                    if (resultado.atualizado) {
                        atualizados++;
                    } else {
                        ignorados++;
                    }
                } catch (Exception e) {
                    falhas++;
                    relatorio.falha("Linha " + (i + 1) + ", CODPARC " + codParc, e);
                }
            }
        } catch (Exception e) {
            falhas++;
            relatorio.falha("Falha geral no botao", e);
            throw new Exception("Nao foi possivel concluir a atualizacao. Consulte o suporte com o ID da execucao.", e);
        } finally {
            contexto.setMensagemRetorno(relatorio.finalizarHtml(atualizados, ignorados, falhas));
        }
    }

    private BigDecimal asBigDecimal(Object valor) {
        if (valor == null) return null;
        if (valor instanceof BigDecimal) return (BigDecimal) valor;
        return new BigDecimal(String.valueOf(valor));
    }
}
