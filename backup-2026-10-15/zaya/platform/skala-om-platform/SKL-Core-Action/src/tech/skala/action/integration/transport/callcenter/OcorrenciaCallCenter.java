package tech.skala.action.integration.transport.callcenter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * A classe {@code OcorrenciaCallCenter} representa a tabela {@code AD_TMSCALOCO}.
 * 
 * @author hiagoalexandre
 * @since 2022-11-24
 */
public class OcorrenciaCallCenter implements EntityBean, Persistable, LoadableByValueObject {
	public static final String						ENTITY_NAME = "AD_TMSCALOCO";
	
	private	BigDecimal  							codCal;
	private	BigDecimal  							sequencia;
	private	Timestamp  								dhOcorrencia;
	private	BigDecimal  							codOco;
	private	Timestamp  								dhEnvTransp;
	private	String  								usuarioExt;
	private	BigDecimal  							codParcCausador;
	private	String  								observacao;
	private	BigDecimal  							setoRresp;
	private	BigDecimal  							vlrOcorrencia;
	private	Timestamp  								dhAgend;
	private	BigDecimal  							nuNota;

	public BigDecimal getCodCal() {
		return codCal;
	}

	public void setCodCal(BigDecimal codCal) {
		this.codCal = codCal;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public Timestamp getDhOcorrencia() {
		return dhOcorrencia;
	}

	public void setDhOcorrencia(Timestamp dhOcorrencia) {
		this.dhOcorrencia = dhOcorrencia;
	}

	public BigDecimal getCodOco() {
		return codOco;
	}

	public void setCodOco(BigDecimal codOco) {
		this.codOco = codOco;
	}

	public Timestamp getDhEnvTransp() {
		return dhEnvTransp;
	}

	public void setDhEnvTransp(Timestamp dhEnvTransp) {
		this.dhEnvTransp = dhEnvTransp;
	}

	public String getUsuarioExt() {
		return usuarioExt;
	}

	public void setUsuarioExt(String usuarioExt) {
		this.usuarioExt = usuarioExt;
	}

	public BigDecimal getCodParcCausador() {
		return codParcCausador;
	}

	public void setCodParcCausador(BigDecimal codParcCausador) {
		this.codParcCausador = codParcCausador;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getSetoRresp() {
		return setoRresp;
	}

	public void setSetoRresp(BigDecimal setoRresp) {
		this.setoRresp = setoRresp;
	}

	public BigDecimal getVlrOcorrencia() {
		return vlrOcorrencia;
	}

	public void setVlrOcorrencia(BigDecimal vlrOcorrencia) {
		this.vlrOcorrencia = vlrOcorrencia;
	}

	public Timestamp getDhAgend() {
		return dhAgend;
	}

	public void setDhAgend(Timestamp dhAgend) {
		this.dhAgend = dhAgend;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codCal, codOco, dhOcorrencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OcorrenciaCallCenter))
			return false;
		OcorrenciaCallCenter other = (OcorrenciaCallCenter) obj;
		return Objects.equals(codCal, other.codCal) && Objects.equals(codOco, other.codOco)
				&& Objects.equals(dhOcorrencia, other.dhOcorrencia);
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		
		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
		entityNew.setField(SklFieldName.CODCAL, codCal);
		entityNew.setField(SklFieldName.DHOCORRENCIA, dhOcorrencia);
		entityNew.setField(SklFieldName.CODOCO, codOco);
		entityNew.setField(SklFieldName.DHENVTRANSP, dhEnvTransp);
		entityNew.setField("USUARIOEXT", usuarioExt);
		entityNew.setField(SklFieldName.CODPARCCAUSADOR, codParcCausador);
		entityNew.setField(SklFieldName.OBSERVACAO, observacao);
		entityNew.setField("SETORRESP", setoRresp);
		entityNew.setField("VLROCORRENCIA", vlrOcorrencia);
		entityNew.setField("DHAGEND", dhAgend);
		entityNew.setField(SklFieldName.NUNOTA, nuNota);
		DynamicVO vo = entityNew.fluidInsert();

		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);

		return null;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codCal = vo.asBigDecimal("CODCAL");
		this.sequencia = vo.asBigDecimal("SEQUENCIA");
		this.dhOcorrencia = vo.asTimestamp("DHOCORRENCIA");
		this.codOco = vo.asBigDecimal("CODOCO");
		this.dhEnvTransp = vo.asTimestamp("DHENVTRANSP");
		this.usuarioExt = vo.asString("USUARIOEXT");
		this.codParcCausador = vo.asBigDecimal("CODPARCCAUSADOR");
		this.observacao = vo.asString("OBSERVACAO");
		this.setoRresp = vo.asBigDecimal("SETORRESP");
		this.vlrOcorrencia = vo.asBigDecimal("VLROCORRENCIA");
		this.dhAgend = vo.asTimestamp("DHAGEND");
		this.nuNota = vo.asBigDecimal("NUNOTA");
	}

	@Override
	public String toString() {
		return String.format(
				"OcorrenciaCallCenter [codCal=%s, sequencia=%s, dhOcorrencia=%s, codOco=%s, dhEnvTransp=%s, usuarioExt=%s, codParcCausador=%s, observacao=%s, setoRresp=%s, vlrOcorrencia=%s, dhAgend=%s, nuNota=%s]",
				codCal, sequencia, dhOcorrencia, codOco, dhEnvTransp, usuarioExt, codParcCausador, observacao,
				setoRresp, vlrOcorrencia, dhAgend, nuNota);
	}
}
