package tech.skala.modules.logistica.hub;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class HubTransportOccurrence implements EntityBean, Persistable, LoadableByValueObject {
	
	public static final String						ENTITY_NAME = "AD_PLTHUBTRAOCO";
	private BigDecimal 								codEmp;
	private BigDecimal 								ordemCarga;
	private BigDecimal 								nuNota;
	private String 									chaveNfe;
	private BigDecimal 								sequencia;
	private BigDecimal 								nuOccurrenceType;
	private String 									status;
	private String 									observacao;
	private Timestamp 								dhInc;
	private Timestamp 								dhOcorrencia;
	private Timestamp 								dhAlter;

	public HubTransportOccurrence() {

	}
	
	public HubTransportOccurrence(HubTransport hubTransport) {
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

	public BigDecimal getNuOccurrenceType() {
		return nuOccurrenceType;
	}

	public void setNuOccurrenceType(BigDecimal nuOccurrenceType) {
		this.nuOccurrenceType = nuOccurrenceType;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Timestamp getDhOcorrencia() {
		return dhOcorrencia;
	}

	public void setDhOcorrencia(Timestamp dhOcorrencia) {
		this.dhOcorrencia = dhOcorrencia;
	}

	public Timestamp getDhInc() {
		return dhInc;
	}

	public void setDhInc(Timestamp dhInc) {
		this.dhInc = dhInc;
	}

	public Timestamp getDhAlter() {
		return dhAlter;
	}

	public void setDhAlter(Timestamp dhAlter) {
		this.dhAlter = dhAlter;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dhOcorrencia, nuNota, nuOccurrenceType, observacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HubTransportOccurrence))
			return false;
		HubTransportOccurrence other = (HubTransportOccurrence) obj;
		return Objects.equals(dhOcorrencia, other.dhOcorrencia) && Objects.equals(nuNota, other.nuNota)
				&& Objects.equals(nuOccurrenceType, other.nuOccurrenceType)
				&& Objects.equals(observacao, other.observacao);
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal("CODEMP");
		this.ordemCarga = vo.asBigDecimal("ORDEMCARGA");
		this.nuNota = vo.asBigDecimal("NUNOTA");
		this.chaveNfe = vo.asString("CHAVENFE");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");
		this.nuOccurrenceType = vo.asBigDecimal("NUOCCURRENCETYPE");
		this.status = vo.asString("STATUS");
		this.observacao = vo.asString("OBSERVACAO");
		this.dhInc = vo.asTimestamp("DHINC");
		this.dhOcorrencia = vo.asTimestamp("DHOCORRENCIA");
		this.dhAlter = vo.asTimestamp("DHALTER");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);

		entityNew.setField("CODEMP", codEmp);
		entityNew.setField("ORDEMCARGA", ordemCarga);
		entityNew.setField("NUNOTA", nuNota);
		entityNew.setField("CHAVENFE", chaveNfe);
		entityNew.setField("SEQUENCIA", sequencia);
		entityNew.setField("NUOCCURRENCETYPE", nuOccurrenceType);
		entityNew.setField("STATUS", status);
		entityNew.setField("OBSERVACAO", observacao);
		entityNew.setField("DHINC", dhInc);
		entityNew.setField("DHALTER", dhAlter);
		entityNew.setField("DHOCORRENCIA", dhOcorrencia);

		DynamicVO newVO = entityNew.fluidInsert();
		this.sequencia = newVO.asBigDecimal("SEQUENCIA");
		
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"HubTransportOccurrence [codEmp=%s, ordemCarga=%s, nuNota=%s, chaveNfe=%s, sequencia=%s, nuOccurrenceType=%s, status=%s, observacao=%s, dhInc=%s, dhAlter=%s]",
				codEmp, ordemCarga, nuNota, chaveNfe, sequencia, nuOccurrenceType, status, observacao, dhInc, dhAlter);
	}
	
}