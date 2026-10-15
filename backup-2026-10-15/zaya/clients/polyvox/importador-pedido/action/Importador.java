package org.ImportadorPedido.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import org.ImportadorPedido.controller.ImportadorController;

public class Importador implements AcaoRotinaJava {

    private final ImportadorController controller = new ImportadorController();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        controller.executar(contexto);
    }
}
