package br.zaya.telecontrol.listabasica.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.listabasica.application.SincronizaListaBasicaService;
import br.zaya.telecontrol.shared.utils.LogUtil;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class SincronizaListaBasicaSc implements ScheduledAction {

    @Override
    public void onTime(ScheduledActionContext ctx) {
        JapeSession.SessionHandle hnd = null;

        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    ctx.info("Iniciando sincronizacao de materias-primas para lista basica Telecontrol...");
                    ctx.info("[SUCESSO] Sincronizacao de lista basica concluida: "
                            + SincronizaListaBasicaService.sincronizarMateriasPrimas());
                }
            });
        } catch (Exception e) {
            ctx.info("[ERRO] Erro ao executar scheduler de lista basica: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception logError) {
                ctx.info("[ERRO] Erro ao salvar log: " + logError.getMessage());
            }
        } finally {
            if (hnd != null) {
                JapeSession.close(hnd);
            }
        }
    }
}
