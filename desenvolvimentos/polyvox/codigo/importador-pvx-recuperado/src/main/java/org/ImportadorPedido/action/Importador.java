/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.extensions.actionbutton.AcaoRotinaJava
 *  br.com.sankhya.extensions.actionbutton.ContextoAcao
 */
package org.ImportadorPedido.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import org.ImportadorPedido.controller.ImportadorController;

public class Importador
implements AcaoRotinaJava {
    private final ImportadorController controller = new ImportadorController();

    public void doAction(ContextoAcao contexto) throws Exception {
        this.controller.executar(contexto);
    }
}

