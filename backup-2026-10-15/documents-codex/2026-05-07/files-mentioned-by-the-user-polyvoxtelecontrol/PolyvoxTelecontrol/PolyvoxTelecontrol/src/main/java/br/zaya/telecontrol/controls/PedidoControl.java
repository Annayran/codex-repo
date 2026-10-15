package br.zaya.telecontrol.controls;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.comercial.util.TipoOperacaoUtils;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ParceiroHellper;
import br.zaya.telecontrol.models.Pedido;
import br.zaya.telecontrol.models.PedidoItem;
import br.zaya.telecontrol.parsers.ParsePedido;
import br.zaya.telecontrol.services.BuscaOrdemServico;
import br.zaya.telecontrol.utils.ConfigTelecontrol;
import br.zaya.telecontrol.utils.LogUtil;
import br.zaya.telecontrol.utils.PedidoNotaHelper;
import com.sankhya.util.TimeUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class PedidoControl {
	public static void gravarPedidosNaoGerados() throws Exception {
		LogUtil.METHOD = "GerarTodosPedidos";
		LogUtil.SYNC = "SankhyaGerarPedido";
		LogUtil.REGISTRO = "0";
		try {
			JapeWrapper pedidoDAO = JapeFactory.dao("AD_TCLPED");
			Collection<DynamicVO> collPedVO = pedidoDAO
					.find("this.NUNOTA IS NULL AND (this.EXPORTADO IS NULL OR this.EXPORTADO = 'N')");
			for (DynamicVO pedidoVO : collPedVO)
				gravaPedido(pedidoVO.asBigDecimal("PEDIDO"));
		} catch (Exception var9) {
			LogUtil.saveLog(var9);
			var9.printStackTrace();
		}
	}

	public static void gravaPedido(BigDecimal pedidoId) throws Exception {
		LogUtil.METHOD = "GerarPedido";
		LogUtil.SYNC = "SankhyaGerarPedido";
		LogUtil.REGISTRO = pedidoId.toString();
		LogUtil.URL = "";
		ConfigTelecontrol config = new ConfigTelecontrol();
		try {
			JapeWrapper pedidoDAO = JapeFactory.dao("AD_TCLPED");
			DynamicVO pedidoVO = pedidoDAO.findByPK(new Object[] { pedidoId });
			if (pedidoVO.asBigDecimal("NUNOTA") != null)
				throw new Exception("Pedido já gerado");
			EntityFacade entityFacade = EntityFacadeFactory.getDWFFacade();
			LogUtil.REQUEST = pedidoVO.asString("JSON");
			Pedido pedido = ParsePedido.getPedido(pedidoVO.asString("JSON"));
			
            // Melhoria: Lógica de modelo de nota baseada na raiz do CNPJ (Polyvox)
            String cnpj = pedido.cnpj;
            String raizCnpj = (cnpj != null && cnpj.length() >= 8) ? cnpj.substring(0, 8) : "";
            String raizComparacao = "04933508"; // Raiz CNPJ Polyvox

            BigDecimal modeloNota;
            if (raizCnpj.equals(raizComparacao)) {
                modeloNota = config.modPedGarPlx;
            } else {
                modeloNota = config.modPedido;
            }

			DynamicVO cabecalhoVO = getCabecalhoVO(pedido, modeloNota);
			Collection<DynamicVO> itemColl = getItensVO(pedido, cabecalhoVO.asBigDecimal("CODTIPOPER"), entityFacade);
			
            // Melhoria: Validar se há itens antes de incluir nota
            if (itemColl.isEmpty()) {
                throw new Exception("Não foram encontrados produtos correspondentes no Sankhya para as referências do pedido " + pedidoId);
            }

            PedidoNotaHelper pedidoHelper = new PedidoNotaHelper();
			BigDecimal nuNota = pedidoHelper.incluirNota(cabecalhoVO, itemColl);
			((FluidUpdateVO) pedidoDAO.prepareToUpdate(pedidoVO).set("NUNOTA", nuNota)).update();
			
            // Melhoria: Buscar OS e atualizar dados na nota
            String ultimaOS = "";
            if (pedido.itens != null) {
                for (PedidoItem item : pedido.itens) {
                    if (item.os != null && !item.os.equals(ultimaOS)) {
                        BuscaOrdemServico.getOrdemServico(item.os, nuNota);
                        ultimaOS = item.os;
                    }
                }
            }

			if (config.confirmaPedido)
				confirmaPedido(nuNota);
		} catch (Exception var15) {
			LogUtil.saveLog(var15);
			var15.printStackTrace();
		}
	}

	private static DynamicVO getCabecalhoVO(Pedido pedido, BigDecimal modelo) throws Exception {
		DynamicVO notaModeloVO = JapeFactory.dao("CabecalhoNota").findByPK(new Object[] { modelo });
        if (notaModeloVO == null) {
            throw new Exception("Modelo de nota " + modelo + " não encontrado.");
        }
		DynamicVO TopVo = TipoOperacaoUtils.getTopVO(notaModeloVO.asBigDecimal("CODTIPOPER"));
		BigDecimal codparc = (new ParceiroHellper()).getParceiroFromCGC(pedido.cnpj);
		if (codparc == null)
			throw new Exception("Parceiro não cadastrado CNPJ: " + pedido.cnpj);
		
		StringBuilder obs = new StringBuilder();
		obs.append("Os:");
		for (PedidoItem item : pedido.itens) {
			obs.append(String.valueOf(item.os) + " ");
            // Vincula a última OS ao cabeçalho conforme código de referência
            if (item.os != null) {
                notaModeloVO.setProperty("AD_NUMOS", item.os);
            }
        }

		notaModeloVO.setProperty("CODPARC", codparc);
		notaModeloVO.setProperty("TIPMOV", TopVo.asString("TIPMOV"));
		notaModeloVO.setProperty("CODTIPOPER", TopVo.asBigDecimal("CODTIPOPER"));
		notaModeloVO.setProperty("DHTIPOPER", TopVo.asTimestamp("DHALTER"));
		notaModeloVO.setProperty("VLRNOTA", BigDecimal.ZERO);
		notaModeloVO.setProperty("VLRFRETE", BigDecimal.ZERO);
		notaModeloVO.setProperty("NUMPEDIDO2", String.valueOf(pedido.pedido));
		notaModeloVO.setProperty("DTNEG",
				(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(pedido.data.substring(0, 19)));
		notaModeloVO.setProperty("DTALTER", TimeUtils.getNow());
		notaModeloVO.setProperty("DTENTSAI", null);
		notaModeloVO.setProperty("HRENTSAI", null);
		notaModeloVO.setProperty("DTMOV", null);
		notaModeloVO.setProperty("HRMOV", null);
		notaModeloVO.setProperty("CODUSU", BigDecimal.ZERO);
		notaModeloVO.setProperty("NUNOTA", null);
		notaModeloVO.setProperty("OBSERVACAO", obs.toString());
		return notaModeloVO;
	}

	private static Collection<DynamicVO> getItensVO(Pedido pedido, BigDecimal codTipOper, EntityFacade entityFacade)
			throws Exception {
		Collection<DynamicVO> itemColl = new ArrayList<>();
		BigDecimal codLocal = null;
		try {
			ConfigTelecontrol config = new ConfigTelecontrol();
            codLocal = config.codLocal;
		} catch (Exception exception) {
		}
		for (br.zaya.telecontrol.models.PedidoItem item : pedido.itens) {
			JapeWrapper pecaDAO = JapeFactory.dao("AD_TCLPCA"); // Conforme Teleworld/Pedidos
			Collection<DynamicVO> produtoVO = pecaDAO.find("this.REFERENCIA = ?", new Object[] { item.referencia });
			
            if (produtoVO.isEmpty()) {
                // Tenta no cadastro de produtos padrão se não achar na customizada? 
                // No original de Teleworld era AD_TCLPCA. No original de br.com era PRODUTO.
                // Vou manter o comportamento de buscar na customizada e se não achar logar.
                System.out.println("Referência não encontrada: " + item.referencia);
            }

			for(DynamicVO prodVO : produtoVO) {
                DynamicVO itemNotaVO = (DynamicVO) entityFacade.getDefaultValueObjectInstance("ItemNota");
				BigDecimal codProd = prodVO.asBigDecimal("CODPROD");
				BigDecimal qtdNeg = (item.qtde != null) ? new BigDecimal(item.qtde) : BigDecimal.ZERO;
				BigDecimal vlrUnit = (item.preco != null) ? new BigDecimal(item.preco) : BigDecimal.ZERO;
				
                itemNotaVO.setProperty("AD_TCLPEDITE", BigDecimal.valueOf(item.pedido_item));
				itemNotaVO.setProperty("NUNOTA", null);
				itemNotaVO.setProperty("CODPROD", codProd);
				itemNotaVO.setProperty("CODVOL", getCodVol(codProd));
				itemNotaVO.setProperty("QTDNEG", qtdNeg);
				itemNotaVO.setProperty("PERCDESC", BigDecimal.ZERO);
				itemNotaVO.setProperty("VLRDESC", BigDecimal.ZERO);
				itemNotaVO.setProperty("VLRUNIT", vlrUnit);
				itemNotaVO.setProperty("VLRTOT", vlrUnit.multiply(qtdNeg));
				
                if (item.os != null) {
                    itemNotaVO.setProperty("AD_NUMOS", item.os);
                }

				itemNotaVO.setProperty("CODLOCALORIG",
						(codLocal != null) ? codLocal : prodVO.asBigDecimal("CODLOCALPADRAO"));
				itemColl.add(itemNotaVO);
			}
		}
		return itemColl;
	}

	public static String getCodVol(BigDecimal codprod) throws Exception {
		JapeWrapper produtoDAO = JapeFactory.dao("Produto");
		DynamicVO produtoVO = produtoDAO.findByPK(new Object[] { codprod });
		return produtoVO.asString("CODVOL");
	}

	public static void confirmaPedido(BigDecimal nuNota) throws Exception {
		CACHelper helper = new CACHelper();
		BarramentoRegra barramento = BarramentoRegra.build(helper.getClass(), "regrasConfirmacaoCAC.xml",
				AuthenticationInfo.getCurrent());
		ConfirmacaoNotaHelper.confirmarNota(nuNota, barramento);
	}
}
