package tech.skala.action.integration.transport.hub;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.integration.transport.azship.EventDTO;
import tech.skala.action.integration.transport.azship.OccurrenceDTO;

public class HubTransportHelper {

	private HubTransport hubTransport;
	private HubTransportRequestResponse hubTransportRequestResponse;
	private HubTransportStatus hubTransportStatus;
	private HubTransportEvent hubTransportEvent;
	private HubTransportOccurrence hubTransportOccurrence;
	private BigDecimal codEmp;
	private BigDecimal ordemCarga;
	private BigDecimal nuNota;
	private BigDecimal numNota;
	private String chaveNfe;

	public HubTransportHelper(BigDecimal codEmp, BigDecimal ordemCarga, BigDecimal nuNota) throws Exception {
		this.codEmp = codEmp;
		this.ordemCarga = ordemCarga;
		this.nuNota = nuNota;

		resolveAdditionalData();
	}

	public HubTransport create() throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				JapeWrapper azsOcpDao = JapeFactory.dao("AD_PLVAZSOCP");
				DynamicVO azsOcpVO = azsOcpDao.findByPK(codEmp, ordemCarga);

				String ordemFilha = azsOcpVO.asString("ORDEMFILHA");

				JapeWrapper hubDao = JapeFactory.dao("AD_PLTHUBTRA");
				DynamicVO hubVO = hubDao.findByPK(codEmp, ordemCarga, nuNota, chaveNfe);

				HubTransport hubTransport = null;

				@Override
				public void doWithTx() throws Exception {
					if (hubVO == null) {
						hubTransport = new HubTransport();

						hubTransport.setCodEmp(codEmp);
						hubTransport.setOrdemCarga(ordemCarga);
						hubTransport.setNuNota(nuNota);
						hubTransport.setChaveNfe(chaveNfe);
						hubTransport.setNumNota(numNota);
						hubTransport.setOrdemCargaFilha(ordemFilha);

						hubTransport.persists();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

		return hubTransport;
	}

	public HubTransportRequestResponse createRequestResponse(String nomeProcesso, String request, String response)
			throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					hubTransportRequestResponse = new HubTransportRequestResponse();

					hubTransportRequestResponse.setCodEmp(codEmp);
					hubTransportRequestResponse.setOrdemCarga(ordemCarga);
					hubTransportRequestResponse.setNuNota(nuNota);
					hubTransportRequestResponse.setChaveNfe(chaveNfe);
					hubTransportRequestResponse.setNomeProcesso(nomeProcesso);
					hubTransportRequestResponse.setRequest(request);
					hubTransportRequestResponse.setResponse(response);
					hubTransportRequestResponse.setDtReq(TimeUtils.getNow());

					hubTransportRequestResponse.persists();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

		return hubTransportRequestResponse;
	}

	public HubTransportStatus createStatus(String descricao) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					hubTransportStatus = new HubTransportStatus();

					hubTransportStatus.setCodEmp(codEmp);
					hubTransportStatus.setOrdemCarga(ordemCarga);
					hubTransportStatus.setNuNota(nuNota);
					hubTransportStatus.setChaveNfe(chaveNfe);
					hubTransportStatus.setDtStatus(TimeUtils.getNow());
					hubTransportStatus.setDescricao(descricao);

					hubTransportStatus.persists();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

		return hubTransportStatus;
	}

	public HubTransportEvent createEvent(EventDTO event) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					hubTransportEvent = new HubTransportEvent();

					hubTransportEvent.setCodEmp(codEmp);
					hubTransportEvent.setOrdemCarga(ordemCarga);
					hubTransportEvent.setNuNota(nuNota);
					hubTransportEvent.setChaveNfe(chaveNfe);
					hubTransportEvent.setDtEvento(event.getDate());
					hubTransportEvent.setNomeEvento(event.getName());
					hubTransportEvent.setObservacao(event.getObservation());
					hubTransportEvent.setUsuario(event.getOwner());

					hubTransportEvent.persists();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

		return hubTransportEvent;
	}

	public void clearEvents() throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					hubTransportEvent = new HubTransportEvent();

					hubTransportEvent.setCodEmp(codEmp);
					hubTransportEvent.setOrdemCarga(ordemCarga);
					hubTransportEvent.setNuNota(nuNota);
					hubTransportEvent.setChaveNfe(chaveNfe);

					hubTransportEvent.clear();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}
	}

	public HubTransportOccurrence createOccurrence(OccurrenceDTO occurrence) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {

				JapeWrapper hubOccurrenceDao = JapeFactory.dao("AD_PLTHUBTRAOCO");
				DynamicVO hubOccurrenceVO = hubOccurrenceDao.findByPK(codEmp, ordemCarga, nuNota, chaveNfe,
						occurrence.getId());

				HubTransportOccurrence hubTransportOccurrence = null;

				@Override
				public void doWithTx() throws Exception {
					if (hubOccurrenceVO == null) {
						hubTransportOccurrence = new HubTransportOccurrence();

						hubTransportOccurrence.setCodEmp(codEmp);
						hubTransportOccurrence.setOrdemCarga(ordemCarga);
						hubTransportOccurrence.setNuNota(nuNota);
						hubTransportOccurrence.setChaveNfe(chaveNfe);
						hubTransportOccurrence.setSequencia(occurrence.getId());
						hubTransportOccurrence.setNuOccurrenceType(occurrence.getOccurrenceType());
						hubTransportOccurrence.setStatus(occurrence.getStatus());
						hubTransportOccurrence.setDhInc(occurrence.getCreateDate());
						hubTransportOccurrence.setDhAlter(occurrence.getModifiedDAte());

						hubTransportOccurrence.persists();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hnd = null;
		}

		return hubTransportOccurrence;
	}

	private void resolveAdditionalData() throws Exception {
		try {
			JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			DynamicVO cabVO = cabDAO.findOne("this.NUNOTA = ?", nuNota);

			numNota = cabVO.asBigDecimal("NUMNOTA");
			chaveNfe = cabVO.asString("CHAVENFE") == null ? "0" : cabVO.asString("CHAVENFE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
