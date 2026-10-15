package br.zaya.telecontrol.pedidos.application;

/** Facade de aplicacao para exportacao de pedidos. */
public class EnvioPedidoExportadoService {
    public static void postPedidoExportado() throws Exception {
        br.zaya.telecontrol.services.EnvioPedidoExportado.postPedidoExportado();
    }
}

