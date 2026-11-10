package br.com.zayait.imperialbaker.parceiro;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Schedule Action para atualizar parceiros a partir das notas da TGFCAB.
 *
 * Criterio:
 * - Usa automaticamente o dia da execucao.
 * - Busca parceiros distintos em TGFCAB onde DTNEG esteja entre o inicio
 *   e o fim do dia corrente.
 */
public class ScheduleAtualizaParceiroReceitaWs implements ScheduledAction {

    private final ReceitaWsParceiroService service = new ReceitaWsParceiroService();

    @Override
    public void onTime(ScheduledActionContext contexto) {
        RelatorioExecucao relatorio = new RelatorioExecucao("Schedule TGFCAB");
        String resumo = "Schedule encerrado com falha geral.";
        try {
            PeriodoDia periodo = periodoDiaAtual();
            relatorio.registrar("Periodo DTNEG: " + periodo.inicio + " ate " + periodo.fim);
            ResultadoSchedule resultado = executarPorPeriodo(periodo.inicio, periodo.fim, relatorio, contexto);

            resumo = "Schedule ReceitaWS concluido. Periodo DTNEG: " + periodo.inicio + " ate " + periodo.fim +
                    ". Parceiros encontrados: " + resultado.encontrados +
                    ". Atualizados: " + resultado.atualizados +
                    ". Sem atualizacao: " + resultado.ignorados +
                    ". Falhas: " + resultado.falhas +
                    (resultado.detalhesFalhas.length() > 0 ? ". " + resultado.detalhesFalhas.toString() : ".");
        } catch (Exception e) {
            relatorio.falha("Erro geral no Schedule", e);
        } finally {
            log(contexto, relatorio.finalizar(resumo + " Gravacoes sujeitas a confirmacao da transacao pelo Sankhya."));
        }
    }

    public ResultadoSchedule executarPorPeriodo(Timestamp dtIni, Timestamp dtFin) throws Exception {
        RelatorioExecucao relatorio = new RelatorioExecucao("Schedule por periodo");
        try {
            return executarPorPeriodo(dtIni, dtFin, relatorio, null);
        } catch (Exception e) {
            relatorio.falha("Falha geral por periodo", e);
            throw e;
        } finally {
            relatorio.finalizar("Processamento por periodo encerrado.");
        }
    }

    private ResultadoSchedule executarPorPeriodo(Timestamp dtIni, Timestamp dtFin,
            RelatorioExecucao relatorio, ScheduledActionContext contexto) throws Exception {
        relatorio.registrar("Selecao de parceiros iniciada.");
        BigDecimal[] parceiros = service.listarParceirosPorDtneg(dtIni, dtFin);
        relatorio.registrar("Selecao concluida; parceiros distintos=" + parceiros.length);

        ResultadoSchedule resultado = new ResultadoSchedule();
        resultado.encontrados = parceiros.length;

        for (int i = 0; i < parceiros.length; i++) {
            BigDecimal codParc = parceiros[i];
            try {
                ReceitaWsParceiroService.ResultadoAtualizacao atualizacao = service.atualizarParceiro(codParc, relatorio);
                relatorio.resultado(atualizacao);
                if (contexto != null) contexto.log("CODPARC " + codParc + ": " + atualizacao.mensagem);
                if (atualizacao.atualizado) {
                    resultado.atualizados++;
                } else {
                    resultado.ignorados++;
                }
            } catch (Exception e) {
                resultado.falhas++;
                relatorio.falha("CODPARC " + codParc, e);
                resultado.detalhesFalhas
                        .append("CODPARC ")
                        .append(codParc)
                        .append(": ")
                        .append(e.getClass().getSimpleName())
                        .append(". ");
            }
        }

        return resultado;
    }

    private PeriodoDia periodoDiaAtual() {
        Calendar inicio = Calendar.getInstance();
        inicio.set(Calendar.HOUR_OF_DAY, 0);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.SECOND, 0);
        inicio.set(Calendar.MILLISECOND, 0);

        Calendar fim = Calendar.getInstance();
        fim.set(Calendar.HOUR_OF_DAY, 23);
        fim.set(Calendar.MINUTE, 59);
        fim.set(Calendar.SECOND, 59);
        fim.set(Calendar.MILLISECOND, 999);

        return new PeriodoDia(new Timestamp(inicio.getTimeInMillis()), new Timestamp(fim.getTimeInMillis()));
    }

    private void log(ScheduledActionContext contexto, String mensagem) {
        String texto = "[ImperialBaker][ScheduleAtualizaParceiroReceitaWs] " + mensagem;
        if (contexto != null) {
            contexto.log(texto);
        }
        System.out.println(texto);
    }

    public static class ResultadoSchedule {
        public int encontrados;
        public int atualizados;
        public int ignorados;
        public int falhas;
        public StringBuilder detalhesFalhas = new StringBuilder();
    }

    private static class PeriodoDia {
        private final Timestamp inicio;
        private final Timestamp fim;

        private PeriodoDia(Timestamp inicio, Timestamp fim) {
            this.inicio = inicio;
            this.fim = fim;
        }
    }
}
