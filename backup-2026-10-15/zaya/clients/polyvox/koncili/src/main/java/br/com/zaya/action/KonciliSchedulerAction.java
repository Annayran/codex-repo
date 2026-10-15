package br.com.zaya.action;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ação Agendada para sincronização de dados da Koncili.
 * Implementa ScheduledAction para execução automática no Sankhya.
 *
 * Executa a importação de registros da API Koncili:
 * - Busca dados do primeiro dia do mês até hoje
 * - Compara com IDTRA e IDEX para evitar duplicatas
 * - Insere apenas registros novos
 * - Executa vinculação de notas automaticamente
 *
 * Configuração no Sankhya:
 * - Classe: org.Koncili.action.KonciliSchedulerAction
 * - Tipo: ScheduledAction
 * - Frequência: Diária em horário específico (ex: 02:00)
 */
public class KonciliSchedulerAction implements ScheduledAction {
    private static final Logger LOG = Logger.getLogger(KonciliSchedulerAction.class.getName());

    /**
     * Executado automaticamente no horário agendado
     */
    @Override
    public void onTime(ScheduledActionContext scheduledActionContext) {
        long inicioMs = System.currentTimeMillis();

        try {
            LOG.info("[SCHEDULER_KONCILI] Iniciando execução agendada da integração Koncili...");

            // Executar lógica principal de importação
            ScheduledKonciliAction action = new ScheduledKonciliAction();
            action.executarImportacaoAgendada();

            long duracaoMs = System.currentTimeMillis() - inicioMs;
            LOG.info("[SCHEDULER_KONCILI_SUCESSO] Execução agendada concluída em " + duracaoMs + "ms");

        } catch (Exception e) {
            long duracaoMs = System.currentTimeMillis() - inicioMs;
            LOG.log(Level.SEVERE,
                    "[SCHEDULER_KONCILI_ERRO] Falha na execução agendada após " + duracaoMs + "ms: " + e.getMessage(),
                    e);
        }
    }
}

