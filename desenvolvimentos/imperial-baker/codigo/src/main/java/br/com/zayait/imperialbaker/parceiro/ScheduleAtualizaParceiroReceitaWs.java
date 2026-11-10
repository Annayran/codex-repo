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
        try {
            PeriodoDia periodo = periodoDiaAtual();
            ResultadoSchedule resultado = executarPorPeriodo(periodo.inicio, periodo.fim);

            log(contexto, "Schedule ReceitaWS concluido. Periodo DTNEG: " + periodo.inicio + " ate " + periodo.fim +
                    ". Parceiros encontrados: " + resultado.encontrados +
                    ". Atualizados: " + resultado.atualizados +
                    ". Sem atualizacao: " + resultado.ignorados +
                    ". Falhas: " + resultado.falhas +
                    (resultado.detalhesFalhas.length() > 0 ? ". " + resultado.detalhesFalhas.toString() : "."));
        } catch (Exception e) {
            log(contexto, "Erro geral no Schedule ReceitaWS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResultadoSchedule executarPorPeriodo(Timestamp dtIni, Timestamp dtFin) throws Exception {
        BigDecimal[] parceiros = service.listarParceirosPorDtneg(dtIni, dtFin);

        ResultadoSchedule resultado = new ResultadoSchedule();
        resultado.encontrados = parceiros.length;

        for (int i = 0; i < parceiros.length; i++) {
            BigDecimal codParc = parceiros[i];
            try {
                ReceitaWsParceiroService.ResultadoAtualizacao atualizacao = service.atualizarParceiro(codParc);
                if (atualizacao.atualizado) {
                    resultado.atualizados++;
                } else {
                    resultado.ignorados++;
                }
            } catch (Exception e) {
                resultado.falhas++;
                resultado.detalhesFalhas
                        .append("CODPARC ")
                        .append(codParc)
                        .append(": ")
                        .append(e.getMessage())
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
