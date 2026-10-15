package br.zaya.telecontrol.familia.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.familia.application.BuscaFamiliaService;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class BuscaFamiliaSc implements ScheduledAction {
    public void onTime(final ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    BuscaFamiliaService.getFamilias();
                }
            });
            ctx.info("Sincronizacao de familias concluida: verificar logs");
        } catch (Exception e) {
            ctx.info("erro: " + e.toString() + " : " + e.getLocalizedMessage());
        } finally {
            JapeSession.close(hnd);
        }
    }
}
