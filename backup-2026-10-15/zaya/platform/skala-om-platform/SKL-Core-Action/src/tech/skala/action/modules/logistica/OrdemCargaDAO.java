package tech.skala.action.modules.logistica;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class OrdemCargaDAO {

	private OrdemCargaDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static BigDecimal findCodParcTranspOrdemCarga(BigDecimal codEmp, BigDecimal ordemCarga)
			throws SkalaModelException {

		SessionHandle hnd = null;

		BigDecimal codParcTransp = null;
		try {
			hnd = JapeSession.open();

			OrdemCarga oc = new OrdemCarga();
			oc.loadByPk(codEmp, ordemCarga);

			codParcTransp = oc.getCodParcTransp();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível obter Parceiro Transportador.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return codParcTransp;
	}
}
