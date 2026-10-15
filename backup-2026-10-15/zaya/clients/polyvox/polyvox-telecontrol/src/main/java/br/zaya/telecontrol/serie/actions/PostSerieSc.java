package br.zaya.telecontrol.serie.actions;

import br.zaya.telecontrol.serie.application.EnviaSerieService;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class PostSerieSc implements ScheduledAction {
    public void onTime(ScheduledActionContext ctx) {
        try {
            EnviaSerieService.postSerie();
            ctx.info("Dados Enviados: Verificar Logs");
        } catch (Exception e) {
            ctx.info("erro: " + e.toString() + " : " + e.getLocalizedMessage());
        }
    }
}
