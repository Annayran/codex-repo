package br.zaya.telecontrol.services;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.parsers.ParsePedido;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.util.Collection;

public class EnvioPedidoExportado {
	public static void postPedidoExportado() throws Exception {
		LogUtil.SYNC = "PedidoExportado";
		try {
			JapeWrapper pedidoDAO = JapeFactory.dao("AD_TCLPED");
			Collection<DynamicVO> collPedVO = pedidoDAO.find("this.NUNOTA IS NOT NULL AND (this.EXPORTADO IS NULL OR this.EXPORTADO = 'N')");
			ConfigTelecontrol config = new ConfigTelecontrol();
			String url = String.valueOf(config.url) + config.epPedidos;
			for (DynamicVO pedidoVO : collPedVO) {
				try {
					LogUtil.REGISTRO = pedidoVO.asBigDecimal("PEDIDO").toString();
					String content = ParsePedido.toPedidoExportadoJson(pedidoVO.asBigDecimal("PEDIDO"));
					LogUtil.REQUEST = content;
					Api.put(url, content);
					((FluidUpdateVO) pedidoDAO.prepareToUpdate(pedidoVO).set("EXPORTADO", "S")).update();
				} catch (Exception var9) {
					LogUtil.saveLog(var9);
					var9.printStackTrace();
				}
			}
		} catch (Exception var10) {
			LogUtil.saveLog(var10);
			var10.printStackTrace();
		}
	}
}
