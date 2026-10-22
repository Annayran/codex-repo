package br.com.zaya.mahospitalar.genesis.schedule;

import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.mahospitalar.genesis.service.IntegracaoGenesisRunner;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public final class IntegracaoGenesisScheduleAction implements ScheduledAction {
    private static final String PREFIXO_LOG = "[MAH-GENESIS] ";

    public IntegracaoGenesisScheduleAction() {
        logSistema("Classe da acao agendada instanciada pelo Sankhya.");
    }

    @Override
    public void onTime(final ScheduledActionContext context) {
        JapeSession.SessionHandle session = null;
        try {
            session = JapeSession.open();
            executar(context);
        } catch (Exception e) {
            throw new IllegalStateException("Integracao Genesis interrompida.",e);
        } finally { JapeSession.close(session); }
    }

    private void executar(final ScheduledActionContext context) {
        logSistema("onTime acionado. Inicio da execucao.");
        try {
            logSistema("Iniciando Genesis; cada fase usa transacao autonoma Jape.");
            new IntegracaoGenesisRunner().executar(null, new IntegracaoGenesisRunner.Log() {
                @Override public void info(String mensagem) {
                    context.info(mensagem);
                }
            });
            logSistema("Fluxo Genesis encerrado sem excecao geral.");
        } catch (Exception e) {
            String mensagem = "Integracao Genesis interrompida: " + mensagemSegura(e);
            System.err.println(PREFIXO_LOG + mensagem);
            java.io.StringWriter trace = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(trace));
            System.err.println(br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer.sanitizeForLog(trace.toString()));
            context.info(mensagem);
            throw new IllegalStateException(mensagem, e);
        } finally {
            logSistema("Fim da acao agendada.");
        }
    }

    private static void logSistema(String mensagem) {
        System.out.println(PREFIXO_LOG + mensagem);
    }

    private static String mensagemSegura(Exception e) {
        String value = br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer.sanitize(e.getMessage());
        if (value == null || value.trim().isEmpty()) return e.getClass().getSimpleName();
        return value.length() > 500 ? value.substring(0, 500) : value;
    }
}
