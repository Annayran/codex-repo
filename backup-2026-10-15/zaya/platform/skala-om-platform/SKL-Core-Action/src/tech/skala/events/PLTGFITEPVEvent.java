package tech.skala.events;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.ComercialUtils.MontantesVolumeAlternativo;
import tech.skala.action.modules.comercial.vendas.ComercialHelper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Gestão de reservas
 */
@Deprecated
public class PLTGFITEPVEvent implements EventoProgramavelJava {
	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private String msg;

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		ModifingFields modifingFields = event.getModifingFields();
		String fieldList = null;

		fieldList = "QTDAENTPALT,QTDAENTCX";

		if (modifingFields.isModifingAny(fieldList))
			realizaConversao(event);

	}

	private void realizaConversao(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		DynamicVO oldVo = (DynamicVO) event.getOldVO();
		verificarQuantidade(vo);
		realizarConversao(vo, oldVo);
	}

	private void verificarQuantidade(DynamicVO vo) throws SkalaModelException {
		Double qtdAEntPalt = vo.asDouble("QTDAENTPALT");
		Double qtdPendPalt = vo.asDouble("QTDPENDPALT");
		Double qtdAEntCx = vo.asDouble("QTDAENTCX");
		Double qtdPendCX = vo.asDouble("QTDPENDENTECX");

		if (qtdAEntPalt > qtdPendPalt || qtdAEntCx > qtdPendCX) {
			msg = "<b>Quantidade a Entregar é maior que a Quantidade Pendente.</b>";
			SkalaModelException.throwMe(msg, null);
		}
	}

	private void realizarConversao(DynamicVO vo, DynamicVO oldVo) throws Exception {

		Long oldQtdAEntrCx = oldVo.asBigDecimal("QTDAENTCX").longValue();
		Double oldQtdAEntrPalt = oldVo.asBigDecimal("QTDAENTPALT").doubleValue();

		Long newQtdAEntrCx = vo.asBigDecimal("QTDAENTCX").longValue();
		Double newQtdAEntrPalt = vo.asBigDecimal("QTDAENTPALT").doubleValue();

		BigDecimal codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		String codVol = vo.asString(SklFieldName.CODVOL);
		String controle = vo.asString(SklFieldName.CONTROLE);

		BigDecimal qtdAEntCx = vo.asBigDecimal("QTDAENTCX");
		BigDecimal qtdAEntPalt = vo.asBigDecimal("QTDAENTPALT");
		BigDecimal unidadesPorPalete;
		BigDecimal quantidadeAEntregar;
		MontantesVolumeAlternativo volumeAternativo;

		if (newQtdAEntrPalt.doubleValue() != 0D && newQtdAEntrPalt.doubleValue() != oldQtdAEntrPalt.doubleValue()) {
			unidadesPorPalete = ComercialHelper.calcularUnidadesPorPalete(vo.asBigDecimal("Produto.CODGPROD"));

			quantidadeAEntregar = BigDecimalUtil
					.getRounded(qtdAEntPalt.multiply(unidadesPorPalete, BigDecimalUtil.MATH_CTX), 0);

			volumeAternativo = ComercialUtils.calcularVolumeAlternativo(codProd, codVol, controle, quantidadeAEntregar,
					BigDecimal.ONE);

			vo.setProperty("QTDAENTCX", volumeAternativo.getQtdVolAlternativo());
		}

		if (newQtdAEntrCx.longValue() != 0L && newQtdAEntrCx.longValue() != oldQtdAEntrCx.longValue()) {
			unidadesPorPalete = ComercialHelper.calcularUnidadesPorPalete(vo.asBigDecimal("Produto.CODGPROD"));
			volumeAternativo = ComercialUtils.calcularVolumeAlternativo(codProd, codVol, controle, BigDecimal.ONE,
					BigDecimal.ONE);

			BigDecimal fator = volumeAternativo.getQtdVolAlternativo();
			BigDecimal qtdAEntregar = qtdAEntCx.divide(fator, BigDecimalUtil.MATH_CTX);

			BigDecimal qtdAEntregarPalt = qtdAEntregar.divide(unidadesPorPalete, BigDecimalUtil.MATH_CTX);

			vo.setProperty("QTDAENTPALT", qtdAEntregarPalt);
		}

		if ((newQtdAEntrCx.longValue() == 0L && oldQtdAEntrCx.longValue() != 0L)
				|| (newQtdAEntrPalt.doubleValue() == 0D && oldQtdAEntrPalt.doubleValue() != 0D)) {
			vo.setProperty("QTDAENTCX", BigDecimal.ZERO);
			vo.setProperty("QTDAENTPALT", BigDecimal.ZERO);
		}

	}

}
