package org.ImportadorPedido.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.service.NotaGeracaoService;

import java.util.logging.Logger;

public class GerarNotaController {
    private static final Logger LOGGER = Logger.getLogger(GerarNotaController.class.getName());
    private final NotaGeracaoService notaGeracaoService = new NotaGeracaoService();

    public void executar(ContextoAcao contexto) throws Exception {
        long inicio = System.currentTimeMillis();
        try {
            ImportadorLogger.info(LOGGER, "GERAR_NOTA_START", "Inicio da rotina de geracao de nota", ImportadorLogger.ctx());
            notaGeracaoService.gerar(contexto);
            ImportadorLogger.info(
                    LOGGER,
                    "GERAR_NOTA_SUCCESS",
                    "Geracao de nota concluida com sucesso",
                    ImportadorLogger.ctx("duracao_ms", System.currentTimeMillis() - inicio)
            );
        } catch (Exception e) {
            ImportadorLogger.error(
                    LOGGER,
                    "GERAR_NOTA_ERROR",
                    "Falha durante geracao de nota",
                    e,
                    ImportadorLogger.ctx("duracao_ms", System.currentTimeMillis() - inicio)
            );
            throw e;
        }
    }
}

