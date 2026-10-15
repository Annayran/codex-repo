package tech.skala.modules.logistica.hub;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class HubTransportStatus implements EntityBean, Persistable, LoadableByValueObject {
	
	public static final String 						ENTITY_NAME = "AD_PLTHUBTRASTA";
	private BigDecimal 								codEmp;
	private BigDecimal 								ordemCarga;
	private BigDecimal 								nuNota;
	private String 									chaveNfe;
	private BigDecimal 								sequencia;
	private Timestamp 								dtStatus;
	private String 									descricao;
	
	public HubTransportStatus() {

	}

	public HubTransportStatus(HubTransport hubTransport) {
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

	public Timestamp getDtStatus() {
		return dtStatus;
	}

	public void setDtStatus(Timestamp dtStatus) {
		this.dtStatus = dtStatus;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal("CODEMP");
		this.ordemCarga = vo.asBigDecimal("ORDEMCARGA");
		this.nuNota = vo.asBigDecimal("NUNOTA");
		this.chaveNfe = vo.asString("CHAVENFE");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");
		this.dtStatus = vo.asTimestamp("DTSTATUS");
		this.descricao = vo.asString("DESCRICAO");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);

		entityNew.setField("CODEMP", codEmp);
		entityNew.setField("ORDEMCARGA", ordemCarga);
		entityNew.setField("NUNOTA", nuNota);
		entityNew.setField("CHAVENFE", chaveNfe);
		entityNew.setField("SEQUENCIA", sequencia);
		entityNew.setField("DTSTATUS", dtStatus);
		entityNew.setField("DESCRICAO", descricao);

		DynamicVO newVO = entityNew.fluidInsert();
		this.sequencia = newVO.asBigDecimal("SEQUENCIA");

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"HubTransportStatus [codEmp=%s, ordemCarga=%s, nuNota=%s, chaveNfe=%s, sequencia=%s, dtStatus=%s, descricao=%s]",
				codEmp, ordemCarga, nuNota, chaveNfe, sequencia, dtStatus, descricao);
	}

}
