package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;

public class DeleteListaBasicaProdutoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        new br.zaya.telecontrol.listabasica.actions.DeleteListaBasicaProdutoBt().doAction(ctx);
    }
}
