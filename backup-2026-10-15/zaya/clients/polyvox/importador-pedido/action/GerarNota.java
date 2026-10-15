package org.ImportadorPedido.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import org.ImportadorPedido.controller.GerarNotaController;

public class GerarNota implements AcaoRotinaJava {

    private final GerarNotaController controller = new GerarNotaController();

    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        controller.executar(ctx);
    }
}
