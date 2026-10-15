package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class Rateio implements EntityBean, Persistable, LoadableByResultSet {
	
	private String  						origem;
	private BigDecimal  					nuFin;
	private BigDecimal  					codNat;
	private BigDecimal  					codCenCus;
	private BigDecimal  					codProj;
	private BigDecimal  					percRateio;
	private BigDecimal  					codCtaCtb;
	private BigDecimal  					numContrato;
	private String  						digitado;
	private BigDecimal  					codSite;
	private BigDecimal  					codParc;
	private BigDecimal  					codUsu;
	
	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public BigDecimal getNuFin() {
		return nuFin;
	}

	public void setNuFin(BigDecimal nuFin) {
		this.nuFin = nuFin;
	}

	public BigDecimal getCodNat() {
		return codNat;
	}

	public void setCodNat(BigDecimal codNat) {
		this.codNat = codNat;
	}

	public BigDecimal getCodCenCus() {
		return codCenCus;
	}

	public void setCodCenCus(BigDecimal codCenCus) {
		this.codCenCus = codCenCus;
	}

	public BigDecimal getCodProj() {
		return codProj;
	}

	public void setCodProj(BigDecimal codProj) {
		this.codProj = codProj;
	}

	public BigDecimal getPercRateio() {
		return percRateio;
	}

	public void setPercRateio(BigDecimal percRateio) {
		this.percRateio = percRateio;
	}

	public BigDecimal getCodCtaCtb() {
		return codCtaCtb;
	}

	public void setCodCtaCtb(BigDecimal codCtaCtb) {
		this.codCtaCtb = codCtaCtb;
	}

	public BigDecimal getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(BigDecimal numContrato) {
		this.numContrato = numContrato;
	}

	public String getDigitado() {
		return digitado;
	}

	public void setDigitado(String digitado) {
		this.digitado = digitado;
	}

	public BigDecimal getCodSite() {
		return codSite;
	}

	public void setCodSite(BigDecimal codSite) {
		this.codSite = codSite;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(BigDecimal codUsu) {
		this.codUsu = codUsu;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		try {
			JapeWrapper dao = JapeFactory.dao("RateioRecDesp");

			DynamicVO vo = dao.findByPK(origem, nuFin, codNat, codCenCus, codProj, codSite, codParc);

			if (vo == null) {

				EntityInsert insert = new EntityInsert("RateioRecDesp");

				FluidCreateVO newVO = dao.create();
				insert.setField("ORIGEM", origem);
				insert.setField("NUFIN", nuFin);
				insert.setField("CODNAT", codNat);
				insert.setField("CODCENCUS", codCenCus);
				insert.setField("CODPROJ", codProj);
				insert.setField("PERCRATEIO", percRateio);
				insert.setField("CODCTACTB", codCtaCtb);
				insert.setField("NUMCONTRATO", numContrato);
				insert.setField("DIGITADO", digitado);
				insert.setField("CODSITE", codSite);
				insert.setField("CODPARC", codParc);
				insert.setField("CODUSU", codUsu);

				insert.fluidInsert();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.origem = rs.getString("ORIGEM");
			this.codCenCus = rs.getBigDecimal("CODCENCUS");
			this.codProj = rs.getBigDecimal("CODPROJ");
			this.percRateio = rs.getBigDecimal("PERCRATEIO");
			this.codCtaCtb = rs.getBigDecimal("CODCTACTB");
			this.numContrato = rs.getBigDecimal("NUMCONTRATO");
			this.digitado = rs.getString("DIGITADO");
			this.codSite = rs.getBigDecimal("CODSITE");

		} catch (SQLException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

	}

}
