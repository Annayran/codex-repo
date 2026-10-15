package br.zaya.telecontrol.pedidos.application;

/**
 * Facade de aplicacao para pedidos.
 * Delegado para a implementacao legada para manter compatibilidade.
 */
public class BuscaPedidosService {
    public static void getNaoIntegrados() throws Exception {
        br.zaya.telecontrol.services.BuscaPedidos.getNaoIntegrados();
    }

    public static void gravarPedido(br.zaya.telecontrol.models.PedidoDistribuidor pedido) throws Exception {
        br.zaya.telecontrol.services.BuscaPedidos.gravarPedido(pedido);
    }
}

