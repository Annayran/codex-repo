package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EventAZShipDAO {

	private EventAZShipDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static BigDecimal buscaCodOcoPeloNomeEvento(String nameEvent) throws SkalaModelException {
		SessionHandle hnd = null;

		BigDecimal codOco = null;
		try {
			hnd = JapeSession.open();

			DynamicVO vo = JapeFactory.dao("AD_PLTAZSCFG").findOne("UPPER(this.DESCROCO) = ?", nameEvent.toUpperCase());
			if (vo != null)
				codOco = vo.asBigDecimal("CODOCOAZS");

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar evento.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return codOco;
	}

}
