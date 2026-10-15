package tech.skala.modules.logistica.ocorrenciaentrega;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

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

/**
 * A classe {@code OcorrenciaDeEntrega} representa a tabela {@code AD_PLTGFENTCTEOCO}.
 * 
 * @author hiagoalexandre
 * @since 2023-03-10
 */
public class OcorrenciaDeEntrega implements EntityBean, Persistable, LoadableByValueObject {

	public static final String						ENTITY_NAME = "AD_PLTGFENTOCO";
	
	private	BigDecimal								nuNota;
	private	BigDecimal								sequencia;
	private	BigDecimal								codOco;
	private	Timestamp								dhOcorrencia;
	private	BigDecimal								vlrOcorrencia;
	private String									origem;
	private	String									observacao;
	private BigDecimal  							codUsuInc;
	private Timestamp  								dhInc;
	private BigDecimal  							codUsu;
	private Timestamp  								dhAlter;

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getCodOco() {
		return codOco;
	}

	public void setCodOco(BigDecimal codOco) {
		this.codOco = codOco;
	}

	public Timestamp getDhOcorrencia() {
		return dhOcorrencia;
	}

	public void setDhOcorrencia(Timestamp dhOcorrencia) {
		this.dhOcorrencia = dhOcorrencia;
	}

	public BigDecimal getVlrOcorrencia() {
		return vlrOcorrencia;
	}

	public void setVlrOcorrencia(BigDecimal vlrOcorrencia) {
		this.vlrOcorrencia = vlrOcorrencia;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getCodUsuInc() {
		return codUsuInc;
	}

	public void setCodUsuInc(BigDecimal codUsuInc) {
		this.codUsuInc = codUsuInc;
	}

	public Timestamp getDhInc() {
		return dhInc;
	}

	public void setDhInc(Timestamp dhInc) {
		this.dhInc = dhInc;
	}

	public BigDecimal getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(BigDecimal codUsu) {
		this.codUsu = codUsu;
	}

	public Timestamp getDhAlter() {
		return dhAlter;
	}

	public void setDhAlter(Timestamp dhAlter) {
		this.dhAlter = dhAlter;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nuNota, codOco, dhOcorrencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OcorrenciaDeEntrega))
			return false;
		OcorrenciaDeEntrega other = (OcorrenciaDeEntrega) obj;
		return Objects.equals(nuNota, other.nuNota) && Objects.equals(codOco, other.codOco)
				&& Objects.equals(dhOcorrencia, other.dhOcorrencia);
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = null;

			if (nuNota != null && sequencia != null)
				vo = dao.findByPK(nuNota, sequencia);

			if (vo == null) {
				EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
				entityNew.setField(SklFieldName.NUNOTA, nuNota);
				entityNew.setField(SklFieldName.CODOCO, codOco);
				entityNew.setField(SklFieldName.DHOCORRENCIA, dhOcorrencia);
				entityNew.setField("VLROCORRENCIA", vlrOcorrencia);
				entityNew.setField(SklFieldName.OBSERVACAO, observacao);
				entityNew.setField("ORIGEM", origem);
				
				if(codUsuInc != null && dhInc != null && codUsu != null && dhAlter != null) {
					entityNew.setField(SklFieldName.CODUSU, codUsu);
					entityNew.setField(SklFieldName.DHALTER, dhAlter);
					entityNew.setField("DHINC", dhInc);
					entityNew.setField("CODUSUINC", codUsuInc);
				}

				DynamicVO newVO = entityNew.fluidInsert();
				this.sequencia = newVO.asBigDecimal(SklFieldName.SEQUENCIA);
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.codOco = vo.asBigDecimal(SklFieldName.CODOCO);
		this.dhOcorrencia = vo.asTimestamp(SklFieldName.DHOCORRENCIA);
		this.vlrOcorrencia = vo.asBigDecimal("VLROCORRENCIA");
		this.observacao = vo.asString(SklFieldName.OBSERVACAO);
		this.origem = vo.asString("ORIGEM");
	}

	@Override
	public String toString() {
		return String.format(
				"OcorrenciaDeEntrega [nuNota=%s, sequencia=%s, codOco=%s, dhOcorrencia=%s, vlrOcorrencia=%s, observacao=%s, origem=%s]",
				nuNota, sequencia, codOco, dhOcorrencia, vlrOcorrencia, observacao, origem);
	}

}
