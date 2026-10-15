package tech.skala.action.integration.transport.hub;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class HubTransportDAO {

	private HubTransportDAO() {
		throw new IllegalStateException("Utility class");
	}

	private static final JapeWrapper HUB_TRANSPORT__DAO = JapeFactory.dao(HubTransport.ENTITY_NAME);
	private static final JapeWrapper HUB_TRANSPORT_EVENT_DAO = JapeFactory.dao(HubTransportEvent.ENTITY_NAME);
	private static final JapeWrapper HUB_TRANSPORT_OCCURRENCE_DAO = JapeFactory.dao(HubTransportOccurrence.ENTITY_NAME);
	private static final JapeWrapper HUB_TRANSPORT_REQUESTRESPONSE_DAO = JapeFactory
			.dao(HubTransportRequestResponse.ENTITY_NAME);
	private static final JapeWrapper HUB_TRANSPORT_STATUS_DAO = JapeFactory.dao(HubTransportStatus.ENTITY_NAME);

	public static void excluiRegistroHubTransport(BigDecimal ordemCarga) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					excluirRegistroHubTransport(ordemCarga);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir Ordem de Carga do Hub Transportadora.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private static void excluirRegistroHubTransport(BigDecimal ordemCarga) throws SkalaModelException {

		try {
			HUB_TRANSPORT_STATUS_DAO.deleteByCriteria("this.ORDEMCARGA = ?", ordemCarga);

			HUB_TRANSPORT_REQUESTRESPONSE_DAO.deleteByCriteria("this.ORDEMCARGA = ?", ordemCarga);

			HUB_TRANSPORT_OCCURRENCE_DAO.deleteByCriteria("this.ORDEMCARGA = ?", ordemCarga);

			HUB_TRANSPORT_EVENT_DAO.deleteByCriteria("this.ORDEMCARGA = ?", ordemCarga);

			HUB_TRANSPORT__DAO.deleteByCriteria("this.ORDEMCARGA = ?", ordemCarga);
		} catch (Exception e) {
			String msg = String.format("[ORDEMCARGA %s] Não foi possível excluir Ordem de Carga do Hub Transportadora.",
					ordemCarga);
			SkalaModelException.throwMe(msg, e);
		}

	}

	public static HubTransport findHubTransportByPk(BigDecimal codEmp, BigDecimal ocPai, BigDecimal nuNota,
			String chaveNFe) throws SkalaModelException {

		HubTransport hubTransport = null;
		try {
			DynamicVO vo = HUB_TRANSPORT__DAO.findByPK(codEmp, ocPai, nuNota, chaveNFe);

			if (vo != null) {
				hubTransport = new HubTransport();
				hubTransport.loadByVO(vo);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar HubTransportes pela PK", e);
		}

		return hubTransport;
	}

	public static HubTransport findHubTransportPeloNuNota(BigDecimal nuNota) throws SkalaModelException {

		HubTransport hubTransport = null;
		try {
			DynamicVO vo = HUB_TRANSPORT__DAO.findOne("this.NUNOTA = ?", nuNota);

			if (vo != null) {
				hubTransport = new HubTransport();
				hubTransport.loadByVO(vo);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar HubTransportes pelo NuNota.", e);
		}

		return hubTransport;
	}

	public static void excluirEventosHubTransport(HubTransport hubTransport) throws SkalaModelException {
		try {
			HUB_TRANSPORT_EVENT_DAO.deleteByCriteria("CODEMP = ? AND ORDEMCARGA = ? AND NUNOTA = ? AND CHAVENFE = ?",
					hubTransport.getCodEmp(), hubTransport.getOrdemCarga(), hubTransport.getNuNota(),
					hubTransport.getChaveNfe());
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir Eventos do HubTransport.", e);
		}
	}

	public static void excluirEventosHubTransport(BigDecimal codEmp, BigDecimal ocPai, BigDecimal nuNota,
			String chaveNFe) throws SkalaModelException {
		try {
			HUB_TRANSPORT_EVENT_DAO.deleteByCriteria("CODEMP = ? AND ORDEMCARGA = ? AND NUNOTA = ? AND CHAVENFE = ?",
					codEmp, ocPai, nuNota, chaveNFe);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir Eventos do HubTransport.", e);
		}
	}
}
