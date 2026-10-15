package org.ImportadorPedido.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.core.JapeSession;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.service.PlanilhaImportService;

import java.util.logging.Logger;

public class ImportadorController {
    private static final Logger LOGGER = Logger.getLogger(ImportadorController.class.getName());
    private final PlanilhaImportService planilhaImportService = new PlanilhaImportService();

    public void executar(ContextoAcao contexto) throws Exception {
        JapeSession.SessionHandle hnd = null;
        long inicio = System.currentTimeMillis();

        try {
            ImportadorLogger.info(LOGGER, "IMPORTADOR_START", "Inicio da rotina de importacao", ImportadorLogger.ctx());
            hnd = JapeSession.open();
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    planilhaImportService.processarPlanilhasPendentes();
                }
            });

            long duracaoMs = System.currentTimeMillis() - inicio;
            ImportadorLogger.info(LOGGER, "IMPORTADOR_SUCCESS", "Importacao concluida com sucesso", ImportadorLogger.ctx("duracao_ms", duracaoMs));
            contexto.setMensagemRetorno("Importacao realizada com sucesso.");
        } catch (Exception e) {
            long duracaoMs = System.currentTimeMillis() - inicio;
            ImportadorLogger.error(LOGGER, "IMPORTADOR_ERROR", "Falha durante importacao", e, ImportadorLogger.ctx("duracao_ms", duracaoMs));
            contexto.setMensagemRetorno("Erro na importacao: " + e.getMessage());
            throw e;
        } finally {
            JapeSession.close(hnd);
        }
    }
}

