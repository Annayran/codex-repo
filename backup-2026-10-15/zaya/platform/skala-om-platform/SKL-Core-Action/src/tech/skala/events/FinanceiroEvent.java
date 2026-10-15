package tech.skala.events;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.ListenerParameters;
import tech.skala.core.commons.constants.SklFieldName;

public class FinanceiroEvent implements EventoProgramavelJava {

	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		
		final boolean isBaixa = JapeSession.getPropertyAsBoolean("mov.financeiro.baixando", false).booleanValue();
		final boolean isCompensacao = JapeSession.getPropertyAsBoolean(ListenerParameters.COMPENSACAO_FINANCEIRA, false)
				.booleanValue();
		final boolean hasPendencia = JapeSession.getPropertyAsBoolean("BaixaHelper.baixa.parcial.com.pendencia", false)
				.booleanValue();

		String msg = String.format("beforeInsert Eh baixa? %s Tem pendencia? %s", isBaixa, hasPendencia);
		logger.info(msg);

		// SE INCLUSAO VIA BAIXA (POR BAIXA PARCIAL) NAO DISPARAR REGRAS
		if (isBaixa || hasPendencia || isCompensacao)
			return;

		DynamicVO vo = (DynamicVO) event.getVo();
		msg = String.format("Novo Titulo NUFIN %s CODPARC %s", vo.asBigDecimal("NUNOTA"), vo.asBigDecimal("CODPARC"));
		logger.info(msg);

		BigDecimal nuFin = vo.asBigDecimal("NUFIN");
		BigDecimal nuNota = vo.asBigDecimal("NUNOTA");
		BigDecimal codTipOper = vo.asBigDecimal("CODTIPOPER");
		String origem = vo.asString("ORIGEM").trim().toUpperCase();

		msg = String.format("codTipOper eh nulo? %s", codTipOper == null);
		logger.info(msg);

		if (codTipOper == null)
			return;

		boolean isBaixaExp = (codTipOper.longValue() == 3333) && "E".equalsIgnoreCase(origem);
		if (isBaixaExp) {
			BarramentoRegra.alteraMoedaParaZero(vo);
		}

		msg = String.format("[NUFIN %s][NUNOTA %s] Preparando para calcular desconto por acordo comercial.",
				vo.asBigDecimal("NUFIN"), vo.asBigDecimal(SklFieldName.NUNOTA));
		logger.info(msg);

		BarramentoRegra.calculaDescontoFinanceiroPeloAcordoComercial(vo);

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		
		final boolean isCompensacao = JapeSession.getPropertyAsBoolean(ListenerParameters.COMPENSACAO_FINANCEIRA, false)
				.booleanValue();
		final boolean hasPendencia = JapeSession.getPropertyAsBoolean("BaixaHelper.baixa.parcial.com.pendencia", false)
				.booleanValue();

		DynamicVO vo = (DynamicVO) event.getVo();
		DynamicVO oldVO = (DynamicVO) event.getOldVO();

		ModifingFields modifingFields = event.getModifingFields();
		String fieldList = null;
		String fieldListBaixa = "CODEMPBAIXA,CODEMPBAIXA,CODUSUBAIXA,DHBAIXA,VLRBAIXA";
		boolean isBaixa = modifingFields.isModifingAny(fieldListBaixa);

		String msg = String.format("beforeUpdate Eh baixa? %s Eh compensacao? %s", isBaixa, isCompensacao);
		logger.info(msg);

		// SE BAIXA NAO DISPARAR
		if (isBaixa)
			return;

		if (isCompensacao) {
			if (modifingFields.isModifing("VLRDESC")) {
				String origem = vo.asString("ORIGEM").trim().toUpperCase();
				int recDesp = vo.asInt("RECDESP");

				if ("E".equalsIgnoreCase(origem) && recDesp == 1) {
					BigDecimal vlrDesc = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("VLRDESC"));
					BigDecimal vlrDescOld = BigDecimalUtil.getValueOrZero(oldVO.asBigDecimal("VLRDESC"));

					if (vlrDesc.doubleValue() != vlrDescOld.doubleValue()) {
						vo.setProperty("VLRDESC", vlrDescOld);
					}
				}

			}
		} else {
			fieldList = "VLRDESDOB";
			if (modifingFields.isModifingAny(fieldList)) {
				BarramentoRegra.calculaDescontoFinanceiroPeloAcordoComercial(vo);
			}
		}

	}

	@Override
	public void beforeDelete(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDelete(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext var1) throws Exception {
		// TODO Auto-generated method stub

	}

}
