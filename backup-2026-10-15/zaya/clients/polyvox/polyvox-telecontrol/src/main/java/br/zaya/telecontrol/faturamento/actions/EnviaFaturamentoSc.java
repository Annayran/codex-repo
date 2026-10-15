package br.zaya.telecontrol.faturamento.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.faturamento.application.EnvioFaturamentoService;
import br.zaya.telecontrol.shared.utils.LogUtil;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class EnviaFaturamentoSc implements ScheduledAction {
    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            EnvioFaturamentoService.setFaturamento();
            ctx.info("Processo de Envio de Faturamento finalizado, examinar os logs");
        } catch (Exception var4) {
            try {
                LogUtil.saveLog(var4);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ctx.info("Erro no Envio de Faturamento: " + var4.getMessage());
        } finally {
            JapeSession.close(hnd);
        }
    }
}

