/*
 * Decompiled with CFR 0.152.
 */
package org.ImportadorPedido.repository;

import org.ImportadorPedido.service.PlanilhaImportService;

public class MapPlanilha {
    private static final PlanilhaImportService SERVICE = new PlanilhaImportService();

    public static void mapearPlanilha() throws Exception {
        SERVICE.processarPlanilhasPendentes();
    }
}

