package tech.skala.action.integration.transport.callcenter;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CallCenterDAO {

	private CallCenterDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static CallCenter findCallCenterByNuNotaETransportador(BigDecimal nuNota, BigDecimal codParcTransp)
			throws SkalaModelException {
		CallCenter callCenter = null;

		SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();

			Collection<DynamicVO> callNotasVO = NotasCallCenterDAO.buscarNotasCallCenterPorNuNotaParcTransp(nuNota,
					codParcTransp);

			if (!callNotasVO.isEmpty()) {
				BigDecimal codCal = callNotasVO.iterator().next().asBigDecimal(SklFieldName.CODCAL);

				callCenter = new CallCenter();
				callCenter.loadByPk(codCal);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar CallCenter.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return callCenter;
	}

}
