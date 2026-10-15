package br.zaya.telecontrol.parsers;

import br.zaya.telecontrol.models.Pedido;
import br.zaya.telecontrol.models.PedidoDistribuidor;
import br.zaya.telecontrol.models.PedidoExportado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sankhya.util.TimeUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class ParsePedido {

	public static PedidoDistribuidor[] getPedidosNaoIntegradosResultado(String result) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(result, PedidoDistribuidor[].class);
		} catch (JsonSyntaxException e) {
			return new PedidoDistribuidor[0];
		}
	}

	public static Pedido getPedido(String result) {
		return (new Gson()).fromJson(result, Pedido.class);
	}

	public static String toPrettyJson(PedidoDistribuidor result) {
		Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
		return gson.toJson(result, PedidoDistribuidor.class);
	}

	public static String toPedidoExportadoJson(BigDecimal pedidoID) {
		PedidoExportado pedido = new PedidoExportado();
		pedido.pedido = pedidoID.toString();
		pedido.exportado = new PedidoExportado.Exportado();
		pedido.exportado.date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")).format(TimeUtils.getNow());
		pedido.exportado.timezone_type = 3;
		pedido.exportado.timezone = "America/Sao_Paulo";
		return (new Gson()).toJson(pedido, PedidoExportado.class);
	}
}
