//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.zaya.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.zaya.controller.NotasController;

public class ActionNotas implements AcaoRotinaJava {
    private static final Logger LOG = Logger.getLogger(ActionNotas.class.getName());
    private final NotasController controller = new NotasController();

    @Override
    public void doAction(ContextoAcao ctx) {
        try {
            controller.executar(ctx);
        } catch (Exception e) {
            String msg = "Falha na integracao Koncili: " + e.getMessage();
            LOG.log(Level.SEVERE, "[KONCILI_IMPORT_ERRO_GERAL] " + msg, e);
            ctx.setMensagemRetorno(msg);
        }
    }
}
