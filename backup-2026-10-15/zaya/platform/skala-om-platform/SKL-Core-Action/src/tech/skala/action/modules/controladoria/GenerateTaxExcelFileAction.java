package tech.skala.action.modules.controladoria;

import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class GenerateTaxExcelFileAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			GenerateTaxExcelFileParams params = new GenerateTaxExcelFileParams(
					(String) context.getParam(SklFieldName.TIPO),
					TimeUtils.clearTime((Timestamp) context.getParam("PERIODO_INI")),
					TimeUtils.clearTime((Timestamp) context.getParam("PERIODO_FIN")),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam("CODCFO"))),
					(String) context.getParam(SklFieldName.CODEMP),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam("CODUF"))),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam(SklFieldName.CODTIPOPER))),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam("NCM"))),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam(SklFieldName.NUNOTA))),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam(SklFieldName.CODPARC))),
					(String) context.getParam(SklFieldName.TIPMOV),
					BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(context.getParam("CODCTACTB"))));

			GenerateTaxExcelFileHelper helper = new GenerateTaxExcelFileHelper(action, params);
			helper.execute();
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();
			context.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}
