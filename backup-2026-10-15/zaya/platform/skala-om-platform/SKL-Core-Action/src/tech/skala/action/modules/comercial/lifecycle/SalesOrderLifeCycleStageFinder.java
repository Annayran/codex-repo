package tech.skala.action.modules.comercial.lifecycle;

import java.math.BigDecimal;
import br.com.sankhya.jape.sql.NativeSql;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SalesOrderLifeCycleStageFinder {

	private SalesOrderLifeCycleStageFinder() {
	}

	public static String getOrderLifeCycleStage(BigDecimal codTipOper) throws SkalaModelException {
		String stage = null;

		try {
			stage = NativeSql.getString("ETAPA", SklEntityName.TOPCARTEIRA, "CODTIPOPER = ?",
					new Object[] { codTipOper });
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar etapa do Ciclo do Pedido.", e);
		}

		return stage;
	}
}