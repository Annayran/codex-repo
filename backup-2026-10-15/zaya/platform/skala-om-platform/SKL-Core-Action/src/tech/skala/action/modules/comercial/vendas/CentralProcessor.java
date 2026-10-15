package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CentralProcessor {
	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private final JapeWrapper pltCabDAO = JapeFactory.dao(SklEntityName.CABECALHO_NOTA);
	private final JapeWrapper topCarteiraDAO = JapeFactory.dao(SklEntityName.TOPCARTEIRA);
	private final JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

	public void alteraPedidoFinal(DynamicVO vo, Timestamp dtComercial) throws SkalaModelException {

		BigDecimal codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);
		String tipMov = vo.asString("TIPMOV");

		if ("P-V-D".indexOf(tipMov) > -1) {

			String tipoCarteira;
			try {
				JapeWrapper topCarteiraDAO = JapeFactory.dao(SklEntityName.TOPCARTEIRA);
				DynamicVO topCarteiraVO = topCarteiraDAO.findByPK(codTipOper);
				if (topCarteiraVO == null)
					return;

				tipoCarteira = topCarteiraVO.asString("TIPO");

			} catch (Exception e) {
				String msg = String.format("Não há informações de carteira para TOP %s.", codTipOper);
				msg = SkalaModelException.buildErrorMessage(msg, e);
				throw new SkalaModelException(msg, e);
			}

			try {
				String msg = String.format("tipoCarteira = %s", tipoCarteira);
				logger.info(msg);

				JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
				FluidUpdateVO updCabVO = cabDAO.prepareToUpdate(vo);
				updCabVO.set("AD_DTCOMERCIAL", TimeUtils.clearTime(dtComercial));
				updCabVO.update();

				Collection<DynamicVO> pedidosVO = buscaProximoPedido(vo);
				if (pedidosVO == null)
					return;

				for (DynamicVO pedidoVO : pedidosVO) {
					BigDecimal nuNota = pedidoVO.asBigDecimal(SklFieldName.NUNOTA);

					alteraPedidoFinal(cabDAO.findByPK(nuNota), dtComercial);
				}

			} catch (Exception e) {
				String msg = String.format("Não foi possível obter os dados de destino para o lançamento %s.",
						vo.asBigDecimal(SklFieldName.NUNOTA));
				msg = SkalaModelException.buildErrorMessage(msg, e);
				throw new SkalaModelException(msg, e);
			}

		}
	}

	private Collection<DynamicVO> buscaProximoPedido(DynamicVO vo) throws SkalaModelException {
		BigDecimal nuNotaOrig = vo.asBigDecimal(SklFieldName.NUNOTA);

		StringBuilder sbMsg = new StringBuilder();
		String msg = String.format("nuNotaOrig %s ", nuNotaOrig);
		sbMsg.append(msg);

		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("this.NUNOTAORIG = ? ");
		sbWhere.append("AND EXISTS(SELECT 1 FROM TGFCAB T WHERE T.NUNOTA = this.NUNOTA AND T.DTNEG >= '17/01/2022')");

		JapeWrapper compraVendaVariosPedidoDAO = JapeFactory.dao(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO);
		try {
			return compraVendaVariosPedidoDAO.find(sbWhere.toString(), nuNotaOrig);
		} catch (Exception e) {
			String errMsg = String.format("Não foi possível localizar Pedidos gerados pelo lançamento %s.", nuNotaOrig);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			throw new SkalaModelException(errMsg, e);
		} finally {
			msg = sbMsg.toString();
			logger.info(msg);
		}

	}

	public void marcarItemComSaldoComoPendente(DynamicVO cabVO, Collection<DynamicVO> itensVO)
			throws SkalaModelException {
		JapeWrapper itemNotaDAO = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
		FluidUpdateVO updItemVO;

		BigDecimal qtdNeg;
		BigDecimal qtdEntregue;

		if (ItemNotaEnum.PENDENTE.getValue().equals(cabVO.asString(SklFieldName.PENDENTE))) {

			for (DynamicVO itemVO : itensVO) {
				if (ItemNotaEnum.NAO_PENDENTE.getValue().equals(itemVO.asString(SklFieldName.PENDENTE))) {
					qtdNeg = itemVO.asBigDecimal(SklFieldName.QTDNEG);
					qtdEntregue = itemVO.asBigDecimal("QTDENTREGUE");

					if (qtdNeg.longValue() > qtdEntregue.longValue()) {

						try {
							updItemVO = itemNotaDAO.prepareToUpdate(itemVO);
							updItemVO.set(SklFieldName.PENDENTE, ItemNotaEnum.PENDENTE.getValue());
							updItemVO.update();

						} catch (Exception e) {
							String msg = String.format(
									"[NUNOTA %s] Não foi possivel definir o produto %s como Pendente.",
									itemVO.asBigDecimal(SklFieldName.NUNOTA),
									itemVO.asBigDecimal(SklFieldName.CODPROD));
							msg = SkalaModelException.buildErrorMessage(msg, e);
							throw new SkalaModelException(msg, e);
						}
					}
				}
			}
		}

	}

	public void definirCampoReajuste(final BigDecimal nuNota, final String reajuste) throws Exception {

		DynamicVO pltCabVO = pltCabDAO.findByPK(nuNota);
		DynamicVO cabVO = cabDAO.findByPK(nuNota);

		BigDecimal codTipOper = cabVO.asBigDecimal(SklFieldName.CODTIPOPER);
		DynamicVO topCarteiraVO = topCarteiraDAO.findByPK(codTipOper);

		if (("P".indexOf(topCarteiraVO.asString("TIPO")) > -1)) {

			if (pltCabVO != null) {
				FluidUpdateVO pltCabUpdVO = pltCabDAO.prepareToUpdate(pltCabVO);
				pltCabUpdVO.set(SklFieldName.TEMREAJUSTE, reajuste);
				pltCabUpdVO.set("DTREAJUSTE", TimeUtils.getNow());
				pltCabUpdVO.update();
			} else {
				FluidCreateVO pltCabNewVO = pltCabDAO.create();
				pltCabNewVO.set(SklFieldName.NUNOTA, nuNota);
				pltCabNewVO.set(SklFieldName.TEMREAJUSTE, reajuste);
				pltCabNewVO.set("DTREAJUSTE", TimeUtils.getNow());
				pltCabNewVO.save();
			}
		}
	}

	public void definirCampoProgramado(final BigDecimal nuNota, final String programado) throws Exception {

		DynamicVO pltCabVO = pltCabDAO.findByPK(nuNota);

		if (pltCabVO != null) { // se houver registro, entao atualiza
			FluidUpdateVO pltCabUpdVO = pltCabDAO.prepareToUpdate(pltCabVO);
			pltCabUpdVO.set(SklFieldName.PROGRAMADO, programado);
			pltCabUpdVO.update();
		} else {// se no houver registro, entao cria
			FluidCreateVO pltCabNewVO = pltCabDAO.create();
			pltCabNewVO.set(SklFieldName.NUNOTA, nuNota);
			pltCabNewVO.set(SklFieldName.PROGRAMADO, programado);
			pltCabNewVO.save();

		}
	}

	public void preencheCausaDevolucao(BigDecimal nuNota, BigDecimal codOco) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					preencherCausaDevolucao(nuNota, codOco);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível definir o motivo da devolução.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void preencherCausaDevolucao(BigDecimal nuNota, BigDecimal codOco) throws SkalaModelException {
		JapeWrapper itemNotaDAO = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);

		Collection<DynamicVO> itemNotaVOs = null;
		try {
			itemNotaVOs = itemNotaDAO.find("this.NUNOTA = ?", nuNota);

			EntityUpdate entityUpdate;
			for (DynamicVO itemVO : itemNotaVOs) {
				entityUpdate = new EntityUpdate(DynamicEntityNames.ITEM_NOTA, itemVO);

				entityUpdate.setField("AD_CODOCO", codOco);
				entityUpdate.fluidUpdate();
			}
		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s][CODOCO %s] Não foi possível definir o motivo da devolução para os itens do Lançamento.", nuNota, codOco);
			SkalaModelException.throwMe(msg, e);
		}

	}

	public void alterarDataNegociacaoComercial(BigDecimal nuNota, Timestamp data) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
					DynamicVO vo = dao.findByPK(nuNota);

					EntityUpdate update = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA, vo);
					update.setField("AD_DTCOMERCIAL", data);
					update.setField(SklFieldName.DTNEG, data);
					update.fluidUpdate();

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possível alterar a data comercial e de negociação.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	
}
