package br.com.zaya.action;

import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.zaya.controller.SchedulerController;

/**
 * Ação agendada para importar dados da Koncili diariamente.
 * Busca registros do mês atual até o dia de hoje, compara com IDTRA e IDEX,
 * insere apenas registros novos e vincula notas automaticamente.
 */
public class ScheduledKonciliAction {
    private static final Logger LOG = Logger.getLogger(ScheduledKonciliAction.class.getName());
    private final SchedulerController controller = new SchedulerController();

    /**
     * Executa a importação agendada da Koncili
     */
    public void executarImportacaoAgendada() {
        try {
            controller.executarImportacaoAgendada();

        } catch (Exception e) {
            LOG.log(Level.SEVERE,
                    "[AGENDADA_KONCILI_ERRO_GERAL] Falha na importacao agendada: " + e.getMessage(),
                    e);
        }
    }
}
