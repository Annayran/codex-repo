package tech.skala.action.integration.transport.hub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class HubTransport implements EntityBean, Persistable, LoadableByValueObject {

	public static final String 						ENTITY_NAME = "AD_PLTHUBTRA";
	private BigDecimal 								codEmp;
	private BigDecimal 								ordemCarga;
	private String 									ordemCargaFilha;
	private BigDecimal 								nuNota;
	private BigDecimal 								numNota;
	private String 									chaveNfe;
	private List<HubTransportEvent> 				events = new ArrayList<>();
	private List<HubTransportOccurrence> 			occurrences = new ArrayList<>();
	private List<HubTransportRequestResponse> 		requestResponses = new ArrayList<>();
	private List<HubTransportStatus> 				status = new ArrayList<>();
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public String getOrdemCargaFilha() {
		return ordemCargaFilha;
	}

	public void setOrdemCargaFilha(String ordemCargaFilha) {
		this.ordemCargaFilha = ordemCargaFilha;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getNumNota() {
		return numNota;
	}

	public void setNumNota(BigDecimal numNota) {
		this.numNota = numNota;
	}

	public String getChaveNfe() {
		return chaveNfe;
	}

	public void setChaveNfe(String chaveNfe) {
		this.chaveNfe = chaveNfe;
	}

	public List<HubTransportEvent> getEvents() {
		return events;
	}

	public void setEvents(List<HubTransportEvent> events) {
		this.events = events;
	}

	public List<HubTransportOccurrence> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(List<HubTransportOccurrence> occurences) {
		this.occurrences = occurences;
	}

	public List<HubTransportRequestResponse> getRequestResponses() {
		return requestResponses;
	}

	public void setRequestResponses(List<HubTransportRequestResponse> requestResponses) {
		this.requestResponses = requestResponses;
	}

	public List<HubTransportStatus> getStatus() {
		return status;
	}

	public void setStatus(List<HubTransportStatus> status) {
		this.status = status;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = dao.findByPK(codEmp, ordemCarga, nuNota, chaveNfe);

			if (vo == null) {
				EntityInsert entity = new EntityInsert(ENTITY_NAME);

				entity.setField("CODEMP", codEmp);
				entity.setField("ORDEMCARGA", ordemCarga);
				entity.setField("NUNOTA", nuNota);
				entity.setField("CHAVENFE", chaveNfe);
				entity.setField("NUMNOTA", numNota);
				entity.setField("ORDEMFILHA", ordemCargaFilha);

				entity.fluidInsert();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return null;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);
		this.ordemCargaFilha = vo.asString("ORDEMFILHA");
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.numNota = vo.asBigDecimal(SklFieldName.NUMNOTA);
		this.chaveNfe = vo.asString("CHAVENFE");

		try {
			loadEvents();
			loadOccurences();
			loadRequests();
			loadStatus();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException();
		}

	}

	private void loadStatus() throws Exception {
		JapeWrapper dao = JapeFactory.dao(HubTransportStatus.ENTITY_NAME);
		Collection<DynamicVO> vos = dao.find("this.CODEMP = ? AND this.ORDEMCARGA = ? AND this.NUNOTA = ? AND this.CHAVENFE = ?", codEmp, ordemCarga, nuNota, chaveNfe);
		
		HubTransportStatus stats;
		for(DynamicVO vo : vos) {
			stats = new HubTransportStatus();
			stats.loadByVO(vo);
			
			this.status.add(stats); 
		}
	}

	private void loadRequests() throws Exception {
		JapeWrapper dao = JapeFactory.dao(HubTransportRequestResponse.ENTITY_NAME);
		Collection<DynamicVO> vos = dao.find("this.CODEMP = ? AND this.ORDEMCARGA = ? AND this.NUNOTA = ? AND this.CHAVENFE = ?", codEmp, ordemCarga, nuNota, chaveNfe);
		
		HubTransportRequestResponse requestResponse;
		for(DynamicVO vo : vos) {
			requestResponse = new HubTransportRequestResponse();
			requestResponse.loadByVO(vo);
			
			this.requestResponses.add(requestResponse); 
		}
	}

	private void loadOccurences() throws Exception {
		JapeWrapper dao = JapeFactory.dao(HubTransportOccurrence.ENTITY_NAME);
		Collection<DynamicVO> vos = dao.find("this.CODEMP = ? AND this.ORDEMCARGA = ? AND this.NUNOTA = ? AND this.CHAVENFE = ?", codEmp, ordemCarga, nuNota, chaveNfe);
		
		HubTransportOccurrence occurrence;
		for(DynamicVO vo : vos) {
			occurrence = new HubTransportOccurrence();
			occurrence.loadByVO(vo);
			
			this.occurrences.add(occurrence); 
		}
	}
	private void loadEvents() throws Exception {
		JapeWrapper dao = JapeFactory.dao(HubTransportEvent.ENTITY_NAME);
		Collection<DynamicVO> vos = dao.find("this.CODEMP = ? AND this.ORDEMCARGA = ? AND this.NUNOTA = ? AND this.CHAVENFE = ?", codEmp, ordemCarga, nuNota, chaveNfe);
		
		HubTransportEvent event;
		for(DynamicVO vo : vos) {
			event = new HubTransportEvent();
			event.loadByVO(vo);
			
			this.events.add(event); 
		}
	}

	public void clearOccurrences() throws Exception {
		JapeWrapper dao = JapeFactory.dao(HubTransportOccurrence.ENTITY_NAME);
		dao.deleteByCriteria("CODEMP = ? AND ORDEMCARGA = ? AND NUNOTA = ? AND CHAVENFE = ?", codEmp, ordemCarga, nuNota, chaveNfe);
	}

	@Override
	public String toString() {
		return String.format(
				"HubTransport [codEmp=%s, ordemCarga=%s, ordemCargaFilha=%s, nuNota=%s, numNota=%s, chaveNfe=%s, events=%s, occurences=%s, requestResponses=%s, status=%s]",
				codEmp, ordemCarga, ordemCargaFilha, nuNota, numNota, chaveNfe, events, occurrences, requestResponses,
				status);
	}

}
