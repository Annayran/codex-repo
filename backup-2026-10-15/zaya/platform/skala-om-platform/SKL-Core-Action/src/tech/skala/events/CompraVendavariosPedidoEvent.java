package tech.skala.events;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CompraVendavariosPedidoEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
	}

	@Override
	public void beforeCommit(TransactionContext tranCtx) throws Exception {
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		JapeWrapper topCartDAO = JapeFactory.dao(SklEntityName.TOPCARTEIRA);

		BigDecimal targetInvoiceId = vo.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal originInvoiceId = vo.asBigDecimal("NUNOTAORIG");

		BigDecimal productCounter = BigDecimal.ZERO;
		try {
			DynamicVO cabVO = cabDAO.findByPK(targetInvoiceId);
			BigDecimal codTipOper = cabVO.asBigDecimal(SklFieldName.CODTIPOPER);

			DynamicVO topCartVO = topCartDAO
					.findOne("this.CODTIPOPER = ? AND this.EXPORTACAO = 'N' AND this.TIPCART = 'V'", codTipOper);

			if (topCartVO != null) {
				productCounter = NativeSql.getBigDecimal("COUNT(1)", "TGFITE ITE",
						"NUNOTA = ? AND EXISTS (SELECT 1 FROM TGFPRO_EXP T WHERE T.CODPROD = ITE.CODPROD)",
						new Object[] { originInvoiceId });
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível buscar produtos em processo de exportação.",
					originInvoiceId);
			SkalaModelException.throwMe(msg, e);
		}

		if (productCounter.longValue() > 0) {
			String msg = String.format(
					"Não é possível faturar o Número Único [%s], existem Produtos em processo de Exportação.",
					originInvoiceId);
			throw new SkalaModelException(msg);
		}

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
	}

}
