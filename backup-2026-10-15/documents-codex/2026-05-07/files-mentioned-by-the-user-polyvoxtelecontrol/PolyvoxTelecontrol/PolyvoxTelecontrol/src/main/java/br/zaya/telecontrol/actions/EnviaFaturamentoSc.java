package br.zaya.telecontrol.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.services.EnvioFaturamento;
import br.zaya.telecontrol.utils.LogUtil;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class EnviaFaturamentoSc implements ScheduledAction {
    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            EnvioFaturamento.setFaturamento();
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
