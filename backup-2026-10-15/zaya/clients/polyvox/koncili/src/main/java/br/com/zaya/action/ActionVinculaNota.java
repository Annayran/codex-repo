package br.com.zaya.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.controller.VinculacaoNotaController;

public class ActionVinculaNota implements AcaoRotinaJava {
    private final VinculacaoNotaController controller = new VinculacaoNotaController();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        controller.executar(contexto);
    }
}
