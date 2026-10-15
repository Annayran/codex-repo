package tech.skala.modules.logistica.hub;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class HubTransportRequestResponse implements EntityBean, Persistable, LoadableByValueObject {

	public static final String 						ENTITY_NAME = "AD_PLTHUBTRAREQRES";
	private BigDecimal 								codEmp;
	private BigDecimal 								ordemCarga;
	private BigDecimal 								nuNota;
	private String 									chaveNfe;
	private BigDecimal 								sequencia;
	private String 									nomeProcesso;
	private String 									request;
	private String 									response;
	private Timestamp 								dtReq;

	public HubTransportRequestResponse() {

	}

	public HubTransportRequestResponse(HubTransport hubTransport) {
		this.codEmp = hubTransport.getCodEmp();
		this.ordemCarga = hubTransport.getOrdemCarga();
		this.nuNota = hubTransport.getNuNota();
		this.chaveNfe = hubTransport.getChaveNfe();
	}

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

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public String getChaveNfe() {
		return chaveNfe;
	}

	public void setChaveNfe(String chaveNfe) {
		this.chaveNfe = chaveNfe;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public String getNomeProcesso() {
		return nomeProcesso;
	}

	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Timestamp getDtReq() {
		return dtReq;
	}

	public void setDtReq(Timestamp dtReq) {
		this.dtReq = dtReq;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal("CODEMP");
		this.ordemCarga = vo.asBigDecimal("ORDEMCARGA");
		this.nuNota = vo.asBigDecimal("NUNOTA");
		this.chaveNfe = vo.asString("CHAVENFE");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");
		this.nomeProcesso = vo.asString("NOMEPROCESSO");
		this.request = vo.asString("REQUEST");
		this.response = vo.asString("RESPONSE");
		this.dtReq = vo.asTimestamp("DTREQ");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entity = new EntityInsert(ENTITY_NAME);

		entity.setField("CODEMP", codEmp);
		entity.setField("ORDEMCARGA", ordemCarga);
		entity.setField("NUNOTA", nuNota);
		entity.setField("CHAVENFE", chaveNfe);
		entity.setField("SEQUENCIA", sequencia);
		entity.setField("NOMEPROCESSO", nomeProcesso);
		entity.setField("REQUEST", request.toCharArray());
		entity.setField("RESPONSE", response.toCharArray());
		entity.setField("DTREQ", dtReq);

		DynamicVO vo = entity.fluidInsert();
		this.sequencia = vo.asBigDecimal("SEQUENCIA");

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"HubTransportRequestResponse [codEmp=%s, ordemCarga=%s, nuNota=%s, chaveNfe=%s, sequencia=%s, nomeProcesso=%s, request=%s, response=%s, dtReq=%s]",
				codEmp, ordemCarga, nuNota, chaveNfe, sequencia, nomeProcesso, request, response, dtReq);
	}

}