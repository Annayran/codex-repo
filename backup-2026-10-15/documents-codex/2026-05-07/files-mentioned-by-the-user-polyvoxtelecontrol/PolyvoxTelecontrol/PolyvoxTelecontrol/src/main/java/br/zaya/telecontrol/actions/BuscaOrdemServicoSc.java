package br.zaya.telecontrol.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.services.BuscaOrdemServico;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class BuscaOrdemServicoSc implements ScheduledAction {

    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    BuscaOrdemServico.getGeralNaoIntegradas();
                }
            });
            ctx.info("Sincronização de OS Concluída: Verificar Logs ");
        } catch (Exception var9) {
            ctx.info("erro: " + var9.toString() + " : " + var9.getLocalizedMessage());
        } finally {
            JapeSession.close(hnd);
        }
    }
}
