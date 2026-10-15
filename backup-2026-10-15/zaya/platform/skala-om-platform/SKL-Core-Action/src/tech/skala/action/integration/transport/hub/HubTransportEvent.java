package tech.skala.action.integration.transport.hub;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class HubTransportEvent implements EntityBean, Persistable, LoadableByValueObject {
	public static final String 						ENTITY_NAME = "AD_PLTHUBTRAEVE";

	private BigDecimal 								codEmp;
	private BigDecimal 								ordemCarga;
	private BigDecimal 								nuNota;
	private String 									chaveNfe;
	private BigDecimal 								sequencia;
	private Timestamp 								dtEvento;
	private String 									nomeEvento;
	private String 									observacao;
	private String 									usuario;

	public HubTransportEvent() {

	}

	public HubTransportEvent(HubTransport hubTransport) {
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

	public Timestamp getDtEvento() {
		return dtEvento;
	}

	public void setDtEvento(Timestamp dtEvento) {
		this.dtEvento = dtEvento;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal("CODEMP");
		this.ordemCarga = vo.asBigDecimal("ORDEMCARGA");
		this.nuNota = vo.asBigDecimal("NUNOTA");
		this.chaveNfe = vo.asString("CHAVENFE");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");
		this.dtEvento = vo.asTimestamp("DTEVENTO");
		this.nomeEvento = vo.asString("NOME");
		this.observacao = vo.asString("OBSERVACAO");
		this.usuario = vo.asString("USUARIO");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entity = new EntityInsert(ENTITY_NAME);

		entity.setField("CODEMP", codEmp);
		entity.setField("ORDEMCARGA", ordemCarga);
		entity.setField("NUNOTA", nuNota);
		entity.setField("CHAVENFE", chaveNfe);
		entity.setField("DTEVENTO", dtEvento);
		entity.setField("NOME", nomeEvento);
		entity.setField("OBSERVACAO", observacao);
		entity.setField("USUARIO", usuario);

		DynamicVO vo = entity.fluidInsert();
		this.sequencia = vo.asBigDecimal("SEQUENCIA");

		return null;
	}
	
	public void clear() throws Exception {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		dao.deleteByCriteria("CODEMP = ? AND ORDEMCARGA = ? AND NUNOTA = ? AND CHAVENFE = ?", codEmp, ordemCarga, nuNota, chaveNfe);
	}

	@Override
	public int hashCode() {
		return Objects.hash(chaveNfe, codEmp, dtEvento, nomeEvento, nuNota, ordemCarga);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HubTransportEvent))
			return false;
		HubTransportEvent other = (HubTransportEvent) obj;
		return Objects.equals(chaveNfe, other.chaveNfe) && Objects.equals(codEmp, other.codEmp)
				&& Objects.equals(dtEvento, other.dtEvento) && Objects.equals(nomeEvento, other.nomeEvento)
				&& Objects.equals(nuNota, other.nuNota) && Objects.equals(ordemCarga, other.ordemCarga);
	}

	@Override
	public String toString() {
		return String.format(
				"HubTransportEvent [codEmp=%s, ordemCarga=%s, nuNota=%s, chaveNfe=%s, sequencia=%s, dtEvento=%s, nomeEvento=%s, observacao=%s, usuario=%s]",
				codEmp, ordemCarga, nuNota, chaveNfe, sequencia, dtEvento, nomeEvento, observacao, usuario);
	}

}