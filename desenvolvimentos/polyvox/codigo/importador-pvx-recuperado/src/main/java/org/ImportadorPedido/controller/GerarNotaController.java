/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.extensions.actionbutton.ContextoAcao
 */
package org.ImportadorPedido.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import java.util.logging.Logger;
import br.com.sankhya.jape.core.JapeSession;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.service.NotaGeracaoService;

public class GerarNotaController {
    private static final Logger LOGGER = Logger.getLogger(GerarNotaController.class.getName());
    private final NotaGeracaoService notaGeracaoService = new NotaGeracaoService();

    public void executar(ContextoAcao contexto) throws Exception {
        long inicio = System.currentTimeMillis();
        try {
            ImportadorLogger.info(LOGGER, "GERAR_NOTA_START", "Inicio da rotina de geracao de nota", ImportadorLogger.ctx(new Object[0]));
            JapeSession.SessionHandle sessao = JapeSession.open();
            try {
                sessao.execWithTX(new JapeSession.TXBlock() {
                    public void doWithTx() throws Exception {
                        notaGeracaoService.gerar(contexto);
                    }
                });
            } finally {
                JapeSession.close(sessao);
            }
            ImportadorLogger.info(LOGGER, "GERAR_NOTA_SUCCESS", "Geracao de nota concluida com sucesso", ImportadorLogger.ctx("duracao_ms", System.currentTimeMillis() - inicio));
        }
        catch (Exception e) {
            ImportadorLogger.error(LOGGER, "GERAR_NOTA_ERROR", "Falha durante geracao de nota", e, ImportadorLogger.ctx("duracao_ms", System.currentTimeMillis() - inicio));
            throw e;
        }
    }
}

