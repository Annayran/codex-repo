package br.zaya.telecontrol.pedidos.infrastructure.mapper;

import br.zaya.telecontrol.models.Pedido;
import br.zaya.telecontrol.models.PedidoDistribuidor;
import br.zaya.telecontrol.models.PedidoExportado;

import java.math.BigDecimal;

/** Bridge de compatibilidade para o parser legadao de pedidos. */
public class ParsePedido {
    public static PedidoDistribuidor[] getPedidosNaoIntegradosResultado(String result) {
        return br.zaya.telecontrol.parsers.ParsePedido.getPedidosNaoIntegradosResultado(result);
    }

    public static Pedido getPedido(String result) {
        return br.zaya.telecontrol.parsers.ParsePedido.getPedido(result);
    }

    public static String toPrettyJson(PedidoDistribuidor result) {
        return br.zaya.telecontrol.parsers.ParsePedido.toPrettyJson(result);
    }

    public static String toPedidoExportadoJson(BigDecimal pedidoID) {
        return br.zaya.telecontrol.parsers.ParsePedido.toPedidoExportadoJson(pedidoID);
    }
}

