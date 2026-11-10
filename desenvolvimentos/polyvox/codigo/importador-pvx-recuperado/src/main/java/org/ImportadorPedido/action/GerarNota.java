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
import org.ImportadorPedido.controller.GerarNotaController;

public class GerarNota
implements AcaoRotinaJava {
    private final GerarNotaController controller = new GerarNotaController();

    public void doAction(ContextoAcao ctx) throws Exception {
        this.controller.executar(ctx);
    }
}

