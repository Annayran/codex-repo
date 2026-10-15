package tech.skala.modules.logistica.ocorrenciaentrega;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EntregasDAO {

	private EntregasDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static Entregas buscaCabecalhoEntregaPorNuNota(BigDecimal nuNota) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFENT");

		Entregas entrega = null;
		SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();

			DynamicVO vo = dao.findByPK(nuNota);

			if (vo != null) {
				entrega = new Entregas();
				entrega.loadByVO(vo);
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Cabecalho Entrega", e);
		} finally {
			JapeSession.close(hnd);
		}
		
		return entrega;
	}
}
