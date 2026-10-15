package br.zaya.telecontrol.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.services.EnvioPosto;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class EnvioPostoSc implements ScheduledAction {
    public void onTime(final ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    EnvioPosto.postPosto();
                }
            });
            ctx.info("Envio de postos concluido: verificar logs");
        } catch (Exception e) {
            ctx.info("erro: " + e.toString() + " : " + e.getLocalizedMessage());
        } finally {
            JapeSession.close(hnd);
        }
    }
}
