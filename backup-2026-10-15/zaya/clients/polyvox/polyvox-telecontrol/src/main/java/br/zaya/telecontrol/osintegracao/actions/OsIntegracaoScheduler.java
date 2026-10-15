package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.osintegracao.application.LegacyOsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.SincronizacaoResultado;
import br.zaya.telecontrol.shared.utils.DateUtil;
import br.zaya.telecontrol.shared.utils.LogUtil;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import java.util.Calendar;
import java.util.Date;

/**
 * Scheduler para sincronizacao automatica de OS
 * Executa diariamente as 01:00 da manha
 * Sincroniza OS do dia 01 do mes atual ate hoje
 */
public class OsIntegracaoScheduler implements ScheduledAction {
    private final OsIntegracaoUseCase osUseCase;

    public OsIntegracaoScheduler() {
        this(new LegacyOsIntegracaoUseCase());
    }

    public OsIntegracaoScheduler(OsIntegracaoUseCase osUseCase) {
        this.osUseCase = osUseCase;
    }

    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;

        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(() -> executarSincronizacao(ctx));
        } catch (Exception e) {
            ctx.info("[ERRO] Erro ao executar scheduler de OS: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception logError) {
                ctx.info("[ERRO] Erro ao salvar log: " + logError.getMessage());
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    private void executarSincronizacao(ScheduledActionContext ctx) throws Exception {
        try {
            ctx.info("Iniciando sincronizacao automatica de OS do mes atual...");

            Date dataInicio = primeiroDiaMesAtual();
            Date dataFim = new Date();

            ctx.info(String.format("Periodo de sincronizacao: %s a %s",
                    DateUtil.formatBrDate(dataInicio),
                    DateUtil.formatBrDate(dataFim)));

            SincronizacaoResultado resultado = osUseCase.sincronizarPorPeriodo(dataInicio, dataFim);

            if (resultado.getErro() != null) {
                ctx.info("[ERRO] Erro na sincronizacao: " + resultado.getErro());
            } else if (resultado.getAviso() != null) {
                ctx.info("[AVISO] " + resultado.getAviso());
            } else {
                ctx.info(String.format(
                        "[SUCESSO] Sincronizacao concluida: %d OS processadas, %d com erro",
                        resultado.getOsProcessadas(), resultado.getOsComErro()
                ));
            }

        } catch (Exception e) {
            ctx.info("[ERRO] Erro ao executar sincronizacao do mes atual: " + e.getMessage());
            LogUtil.saveLog(e);
        }
    }

    private Date primeiroDiaMesAtual() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}

