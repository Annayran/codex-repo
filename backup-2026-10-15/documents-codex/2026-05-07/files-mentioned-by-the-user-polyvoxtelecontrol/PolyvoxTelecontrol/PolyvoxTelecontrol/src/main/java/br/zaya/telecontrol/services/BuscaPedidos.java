package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.zaya.telecontrol.models.PedidoDistribuidor;
import br.zaya.telecontrol.parsers.ParsePedido;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import com.sankhya.util.TimeUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class BuscaPedidos {
    public static void getNaoIntegrados() throws Exception {
        LogUtil.SYNC = "PedidosNaoExportados";
        ConfigTelecontrol serv = new ConfigTelecontrol();
        String dtInicial = MGECoreParameter.getParameterAsString("DTINIPEDTLC");
        if (dtInicial == null)
            dtInicial = (new SimpleDateFormat("yyyy-MM-dd")).format(TimeUtils.getNow());
        
        String dtFinal = (new SimpleDateFormat("yyyy-MM-dd")).format(TimeUtils.getNow());
        String url = String.valueOf(serv.url) + serv.epPedidosNaoIntegrados
                .replaceFirst("\\{dataInicial\\}", dtInicial).replaceFirst("\\{dataFinal\\}", dtFinal);
        PedidoDistribuidor[] listaPedidos = null;
        int page = 0;
        do {
            String urlPage = url.replaceFirst("\\{page\\}", String.valueOf(page++));
            LogUtil.URL = urlPage;
            String result = Api.get(urlPage);

            listaPedidos = ParsePedido.getPedidosNaoIntegradosResultado(result);
            if (listaPedidos != null) {
                for (PedidoDistribuidor item : listaPedidos) {
                    try {
                        gravarPedido(item);
                    } catch (Exception var11) {
                        LogUtil.saveLog(var11);
                    }
                }
            }
        } while (listaPedidos != null && listaPedidos.length > 0);
    }

    public static void gravarPedido(PedidoDistribuidor pedido) throws Exception {
        BigDecimal pedidoId = new BigDecimal(pedido.pedido);
        JapeWrapper pedidoDAO = JapeFactory.dao("AD_TCLPED");
        DynamicVO pedidoVO = pedidoDAO.findByPK(new Object[] { pedidoId });
        
        if (pedidoVO != null) {
            FluidUpdateVO itemVO = pedidoDAO.prepareToUpdate(pedidoVO);
            itemVO.set("TIPOPEDIDO", "GAR");
            itemVO.set("JSON", ParsePedido.toPrettyJson(pedido).toCharArray());
            itemVO.update();
        } else {
            FluidCreateVO itemVO = pedidoDAO.create();
            itemVO.set("PEDIDO", pedidoId);
            itemVO.set("TIPOPEDIDO", "GAR");
            itemVO.set("JSON", ParsePedido.toPrettyJson(pedido).toCharArray());
            itemVO.save();
        }

        // Melhoria: Busca ordem de serviço conforme OS vindo de itens (conforme logicamente em Teleworld/Pedidos)
        if (pedido.itens != null) {
            for (PedidoDistribuidor.Item item : pedido.itens) {
                if (item.os != null && !item.os.isEmpty()) {
                    BuscaOrdemServico.getOrdemServico(item.os);
                }
            }
        }
    }
}
