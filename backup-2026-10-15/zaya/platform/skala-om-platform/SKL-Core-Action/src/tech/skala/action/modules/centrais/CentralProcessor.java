package tech.skala.action.modules.centrais;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.CbmModelException;
import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CentralProcessor {
	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final JapeWrapper pltCabDAO = JapeFactory.dao(SklEntityName.CABECALHO_NOTA);
	private final JapeWrapper topCarteiraDAO = JapeFactory.dao(SklEntityName.TOPCARTEIRA);
	private final JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

	/**
	 * Retorna a classe {@code CabeclhoNotaDadosPedidoOrigem} que tem dados sobre um
	 * pedido da venda ou bonificacao.
	 * <p>
	 * A classe {@code CabecalhoNotaDadosPedidoOrigem} contem dados sobre um pedido
	 * de venda ou bonificacao, mais especificamente a <b>Data Comercial</b>,
	 * informacao utilizada principalmente para o calculo de comissoes. <br>
	 * Somente serao considerados lancamentos cujas <b>TOP</> estiverem cadastradas
	 * como <b> TOP carteira</b>. <br>
	 * Recebe um <i>vo</i> da tabela {@code TGFCAB}.
	 * <p>
	 * 
	 * @param vo {@code TGFCAB}
	 * @return CabecalhoNotaDadosPedidoOrigem Class com dados referentes a um pedido
	 *         de venda ou bonificacao
	 * @throws SkalaModelException 
	 * @Since 2021-12-14
	 */

	public CabecalhoNotaDadosPedidoOrigem getDadosPedidoOrigem(DynamicVO vo) throws SkalaModelException {
		final List<String> tiposOrigem = Arrays.asList("B", "P", "PE");
		final List<String> tiposCarteiraVenda = Arrays.asList("D", "DB", "DE", "E", "EB", "R", "RB", "RE", "V", "VB",
				"VE");

		BigDecimal codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);
		String tipMov = vo.asString(SklFieldName.TIPMOV);
		CabecalhoNotaDadosPedidoOrigem origem = null;

		if ("P-V-D".indexOf(tipMov) > -1) {
			String tipoCarteira = null;

			try {
				JapeWrapper topCarteiraDAO = JapeFactory.dao(SklEntityName.TOPCARTEIRA);
				DynamicVO topCarteiraVO = topCarteiraDAO.findByPK(codTipOper);
				if (topCarteiraVO == null)
					return null;

				tipoCarteira = topCarteiraVO.asString(SklFieldName.TIPO);

			} catch (Exception e) {
				String msg = String.format("Não há informações de carteira para TOP %s.", codTipOper);
				SkalaModelException.throwMe(msg, e);
			}

			try {
				String msg = String.format("tipoCarteira = %s", tipoCarteira);
				logger.info(msg);

				origem = new CabecalhoNotaDadosPedidoOrigem();
				if (tiposOrigem.contains(tipoCarteira)) {
					origem.loadByVO(vo);
				} else {
					
					if (tiposCarteiraVenda.contains(tipoCarteira)) {
						DynamicVO pedidoOrigemVO = buscaPedidoOrigem(vo);
						if (pedidoOrigemVO == null)
							return null;

						origem = getDadosPedidoOrigem(pedidoOrigemVO);
					} else {
						return null;
					}
				}
			} catch (Exception e) {
				String msg = String.format("Não foi possível obter os daods de origem para o lançamento %s.",
						vo.asBigDecimal(SklFieldName.NUNOTA));
				SkalaModelException.throwMe(msg, e);
			}

		}
		return origem;
	}

	private DynamicVO buscaPedidoOrigem(DynamicVO vo) throws SkalaModelException {
		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);

		StringBuilder sbMsg = new StringBuilder();
		String msg = String.format("nuNota %s", nuNota);
		sbMsg.append(msg);

		DynamicVO cabecalhoNotaVO = null;
		JapeWrapper compraVendaVariosPedidoDAO = JapeFactory.dao(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO);
		try {
			DynamicVO varVO = compraVendaVariosPedidoDAO.findOne("this.NUNOTA = ?", nuNota);
			if (varVO == null)
				return null;

			BigDecimal nuNotaOrig = varVO.asBigDecimal(SklFieldName.NUNOTAORIG);
			msg = String.format("nuNotaOrig %s", nuNotaOrig);
			sbMsg.append(msg);

			JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			cabecalhoNotaVO = cabecalhoNotaDAO.findByPK(nuNotaOrig);

		} catch (Exception e) {
			String errMsg = String.format("Não foi possível localizar Pedido de Origem para lançamento %s.", nuNota);
			SkalaModelException.throwMe(errMsg, e);
		} finally {
			msg = sbMsg.toString();
			logger.info(msg);
		}
		
		return cabecalhoNotaVO;
	}
}
