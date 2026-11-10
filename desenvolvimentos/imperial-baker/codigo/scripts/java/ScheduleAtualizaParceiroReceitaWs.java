package br.com.zayait.imperialbaker.parceiro;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Schedule Action para atualizar parceiros a partir das notas da TGFCAB.
 *
 * Criterio inicial:
 * - Buscar parceiros distintos em TGFCAB por periodo de DTNEG.
 *
 * Parametros esperados no agendamento:
 * - DTINI
 * - DTFIN
 *
 * Observacao:
 * - A interface usada aqui e AcaoRotinaJava para facilitar reaproveitamento
 *   em ambientes Sankhya que executam a action agendada com ContextoAcao.
 * - Se o ambiente usar outra assinatura para Schedule Action, reaproveite
 *   o metodo executarPorPeriodo(Timestamp dtIni, Timestamp dtFin).
 */
public class ScheduleAtualizaParceiroReceitaWs implements AcaoRotinaJava {

    private final ReceitaWsParceiroService service = new ReceitaWsParceiroService();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        Timestamp dtIni = asTimestamp(getParamFlex(contexto, "DTINI", "P_DTINI", "DTNEG_INI", "DATA_INICIAL"), false);
        Timestamp dtFin = asTimestamp(getParamFlex(contexto, "DTFIN", "P_DTFIN", "DTNEG_FIN", "DATA_FINAL"), true);

        if (dtIni == null || dtFin == null) {
            contexto.setMensagemRetorno("Parametros DTINI/DTFIN nao informados ou invalidos para filtro de TGFCAB.DTNEG.");
            return;
        }

        ResultadoSchedule resultado = executarPorPeriodo(dtIni, dtFin);

        contexto.setMensagemRetorno(
                "Schedule ReceitaWS concluido. Parceiros encontrados: " + resultado.encontrados +
                        ". Atualizados: " + resultado.atualizados +
                        ". Sem atualizacao: " + resultado.ignorados +
                        ". Falhas: " + resultado.falhas +
                        (resultado.detalhesFalhas.length() > 0 ? ". " + resultado.detalhesFalhas.toString() : ".")
        );
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

    private Object getParamFlex(ContextoAcao contexto, String... nomes) {
        for (int i = 0; i < nomes.length; i++) {
            Object valor = getParam(contexto, nomes[i]);
            if (valor != null && !String.valueOf(valor).trim().isEmpty()) {
                return valor;
            }
        }
        return null;
    }

    private Object getParam(ContextoAcao contexto, String nome) {
        try {
            Method method = contexto.getClass().getMethod("getParam", String.class);
            return method.invoke(contexto, nome);
        } catch (Exception ignored) {
        }
        return null;
    }

    private Timestamp asTimestamp(Object valor, boolean fimDoDia) {
        if (valor == null) return null;
        if (valor instanceof Timestamp) return (Timestamp) valor;
        if (valor instanceof java.util.Date) return new Timestamp(((java.util.Date) valor).getTime());

        String texto = String.valueOf(valor).trim();
        if (texto.isEmpty()) return null;

        String[] formatos = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "dd/MM/yyyy"};
        for (int i = 0; i < formatos.length; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(formatos[i]);
                java.util.Date data = sdf.parse(texto);
                if (formatos[i].indexOf("HH") < 0) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(data);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, fimDoDia ? 23 : 0);
                    cal.set(java.util.Calendar.MINUTE, fimDoDia ? 59 : 0);
                    cal.set(java.util.Calendar.SECOND, fimDoDia ? 59 : 0);
                    cal.set(java.util.Calendar.MILLISECOND, fimDoDia ? 999 : 0);
                    data = cal.getTime();
                }
                return new Timestamp(data.getTime());
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    public static class ResultadoSchedule {
        public int encontrados;
        public int atualizados;
        public int ignorados;
        public int falhas;
        public StringBuilder detalhesFalhas = new StringBuilder();
    }
}
