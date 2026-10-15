package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.ComercialUtils.MontantesVolumeAlternativo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ListenerParameters;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RecalcularImpostoProcessor {

	public void recalculaImposto(BigDecimal nuNota, BigDecimal fator, BigDecimal codGrupoProd) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					Collection<DynamicVO> itensVO = obtemItensNota(nuNota, codGrupoProd);
					recalcularImpostos(itensVO, fator, nuNota);
				}

			});

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível atualizar os impostos dos itens do lançamento.",
					nuNota);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private Collection<DynamicVO> obtemItensNota(BigDecimal nuNota, BigDecimal codGrupoProd) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
		String msg;

		Collection<DynamicVO> itensVO = new LinkedList<>();
		try {
			Collection<DynamicVO> vos = dao.find("this.NUNOTA = ? AND this.PENDENTE = 'S'", nuNota);
			
			for (DynamicVO vo : vos) {
				if(vo.asBigDecimal("Produto.CODGRUPOPROD").equals(codGrupoProd))
					itensVO.add(vo);
			}
			
		} catch (Exception e) {
			msg = String.format("[NUNOTA %s] Não foi possível buscar os itens do lançamento.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		return itensVO;
	}

	private void recalcularImpostos(Collection<DynamicVO> itensVO, BigDecimal fator, BigDecimal nuNota)
			throws SkalaModelException {
		JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);
		JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.TRUE);

		try {

			CACHelper cacHelper = new CACHelper();
			Collection<PrePersistEntityState> itens = new ArrayList<>();

			BigDecimal qtdNeg;
			BigDecimal vlrUnitOld;
			BigDecimal vlrUnit;
			BigDecimal vlrTot;
			BigDecimal vlrDesc;
			BigDecimal percDesc;

			PersistentLocalEntity entity = null;
			EntityDAO dao = EntityFacadeFactory.getDWFFacade().getDAOInstance(DynamicEntityNames.ITEM_NOTA);
			DynamicVO newItemVO;

			EntityPrimaryKey key;
			for (DynamicVO oldItemVO : itensVO) {
				key = new EntityPrimaryKey(new Object[] { nuNota, oldItemVO.asBigDecimal(SklFieldName.SEQUENCIA) });
				entity = EntityFacadeFactory.getDWFFacade().findEntityByPrimaryKey(dao.getEntityName(), key);

				newItemVO = oldItemVO.buildClone();

				MontantesVolumeAlternativo volumeAternativo;
				try {
					volumeAternativo = ComercialUtils.calcularVolumeAlternativo(
							oldItemVO.asBigDecimal(SklFieldName.CODPROD), oldItemVO.asString(SklFieldName.CODVOL),
							oldItemVO.asString(SklFieldName.CONTROLE), oldItemVO.asBigDecimal(SklFieldName.QTDNEG),
							oldItemVO.asBigDecimal(SklFieldName.VLRUNIT));
					qtdNeg = volumeAternativo.getQtdVolAlternativo();

					vlrUnitOld = volumeAternativo.getVlrVolAlternativo();
				} catch (Exception e) {
					qtdNeg = BigDecimalUtil.getValueOrZero(oldItemVO.asBigDecimal(SklFieldName.QTDNEG));
					vlrUnitOld = BigDecimalUtil.getValueOrZero(oldItemVO.asBigDecimal(SklFieldName.VLRUNIT));
				}

				percDesc = BigDecimalUtil.getValueOrZero(oldItemVO.asBigDecimal("PERCDESC"))
						.divide(BigDecimalUtil.CEM_VALUE, BigDecimalUtil.MATH_CTX);

				vlrUnit = vlrUnitOld.multiply(fator, BigDecimalUtil.MATH_CTX);
				vlrUnit = BigDecimalUtil.getRounded(vlrUnit, 2);

				vlrTot = qtdNeg.multiply(vlrUnit, BigDecimalUtil.MATH_CTX);
				vlrTot = BigDecimalUtil.getRounded(vlrTot, 2);

				if (!BigDecimal.ZERO.equals(percDesc)) {
					vlrDesc = vlrTot.multiply(percDesc, BigDecimalUtil.MATH_CTX);
					vlrDesc = BigDecimalUtil.getRounded(vlrDesc, 2);
					
					newItemVO.setProperty("VLRDESC", vlrDesc);
				}

				newItemVO.setProperty(SklFieldName.QTDNEG, qtdNeg);
				newItemVO.setProperty(SklFieldName.VLRUNIT, vlrUnit);
				newItemVO.setProperty("VLRTOT", vlrTot);

				itens.add(PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(), DynamicEntityNames.ITEM_NOTA,
						newItemVO, oldItemVO, entity));

			}

			cacHelper.incluirAlterarItem(nuNota, ServiceContext.getCurrent(), itens, false);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível recalcular impostos.", e);
		}
	}

}
