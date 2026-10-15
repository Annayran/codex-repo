package tech.skala.action.integration.tangerino.rotina;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import tech.skala.action.integration.tangerino.controller.TangerinoController;

public class AcaoAgendadaIntegracao implements ScheduledAction {
    public void onTime(ScheduledActionContext schedule) {

        schedule.info("INICINANDO EXECUÇÃO DA INTEGRAÇÃO");
        try {
            TangerinoController tangerinoController = new TangerinoController();
            tangerinoController.gerenciar();
        } catch (Exception e) {
            schedule.info("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
        schedule.info("FINALIZANDO EXECUÇÃO DA INTEGRAÇÃO");
    }
}
