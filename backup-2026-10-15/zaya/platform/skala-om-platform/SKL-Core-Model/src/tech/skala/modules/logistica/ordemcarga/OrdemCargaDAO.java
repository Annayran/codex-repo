package tech.skala.modules.logistica.ordemcarga;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class OrdemCargaDAO {

	private OrdemCargaDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static OrdemCargaPedidoDTO getOrdemCargaByNuNota(BigDecimal nuNota) throws SkalaModelException {

		SessionHandle hnd = null;
		OrdemCargaPedidoDTO dto = null;
		try {
			hnd = JapeSession.open();

			JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			DynamicVO cabVO = cabDAO.findByPK(nuNota);

			dto = new OrdemCargaPedidoDTO();
			dto.loadByVO(cabVO);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Ordens de Carga para o Pedido", e);
		} finally {
			JapeSession.close(hnd);
		}

		return dto;
	}
	
}
