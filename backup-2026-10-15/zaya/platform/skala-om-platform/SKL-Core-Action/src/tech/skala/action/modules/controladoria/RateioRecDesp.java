package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;


import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class RateioRecDesp implements EntityBean, Persistable {

	public static final String ENTITY_NAME = DynamicEntityNames.RATEIO_REC_DESP;

	private String							origem;
	private BigDecimal						nuFin;
	private BigDecimal						codNat;
	private BigDecimal						codCenCus;
	private BigDecimal						codProj;
	private BigDecimal						codSite;
	private BigDecimal						codParc;
	private BigDecimal						vlrTotal;
	private BigDecimal						vlrRateio;
	private BigDecimal						percRateio;

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

	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(BigDecimal vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	public BigDecimal getVlrRateio() {
		return vlrRateio;
	}

	public void setVlrRateio(BigDecimal vlrRateio) {
		this.vlrRateio = vlrRateio;
	}

	public BigDecimal getPercRateio() {
		return percRateio;
	}

	public void setPercRateio(BigDecimal percRateio) {
		this.percRateio = percRateio;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		result = prime * result + ((nuFin == null) ? 0 : nuFin.hashCode());
		result = prime * result + ((codNat == null) ? 0 : codNat.hashCode());
		result = prime * result + ((codCenCus == null) ? 0 : codCenCus.hashCode());
		result = prime * result + ((codProj == null) ? 0 : codProj.hashCode());
		result = prime * result + ((codSite == null) ? 0 : codSite.hashCode());
		result = prime * result + ((codParc == null) ? 0 : codParc.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		if (obj instanceof RateioRecDesp) {
			RateioRecDesp toCompare = (RateioRecDesp) obj;
			if (!this.origem.equals(toCompare.origem))
				return false;
			if (!this.nuFin.equals(toCompare.nuFin))
				return false;
			if (!this.codNat.equals(toCompare.codNat))
				return false;
			if (!this.codCenCus.equals(toCompare.codCenCus))
				return false;
			if (!this.codProj.equals(toCompare.codProj))
				return false;
			if (!this.codSite.equals(toCompare.codSite))
				return false;
			if (!this.codParc.equals(toCompare.codParc))
				return false;
			
			return true;
		}
		
		return false;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
				
		this.percRateio = vlrRateio.divide(vlrTotal, BigDecimalUtil.MATH_CTX).multiply(BigDecimalUtil.CEM_VALUE, BigDecimalUtil.MATH_CTX);
		this.percRateio = BigDecimalUtil.getRounded(percRateio, 6);
		
		EntityInsert insert = new EntityInsert(ENTITY_NAME);
		insert.setField("ORIGEM", origem);
		insert.setField("NUFIN", nuFin);
		insert.setField("CODNAT", codNat);
		insert.setField("CODCENCUS", codCenCus);
		insert.setField("CODPROJ", codProj);
		insert.setField("CODSITE", codSite);
		insert.setField("CODPARC", codParc);
		insert.setField("PERCRATEIO", percRateio);
		insert.setField("CODUSU", AuthenticationInfo.getCurrent().getUserID());
		insert.fluidInsert();
		
		return null;
	}

	
}
