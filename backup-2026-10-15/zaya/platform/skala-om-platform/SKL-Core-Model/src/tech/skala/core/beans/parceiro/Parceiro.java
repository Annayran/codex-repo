package tech.skala.core.beans.parceiro;

import java.math.BigDecimal;

import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;

public abstract class Parceiro implements EntityBean {
	protected static final String				ENTITY_NAME = DynamicEntityNames.PARCEIRO;

	protected BigDecimal						codParc;
	protected String							nomeParc;
	protected String							razaoSocial;
	protected String							cgcCnpj;
	
	protected Parceiro() {
		
	}
	
	protected Parceiro(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}
	
	public void setCodParc(BigDecimal codParc) {
		if (codParc == null)
			this.codParc = codParc;
	}

	public String getNomeParc() {
		return nomeParc;
	}

	public void setNomeParc(String nomeParc) {
		this.nomeParc = nomeParc;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCgcCnpj() {
		return cgcCnpj;
	}

	public void setCgcCnpj(String cgcCnpj) {
		this.cgcCnpj = cgcCnpj;
	}

	@Override
	public String toString() {
		return String.format("Parceiro [codParc=%s, nomeParc=%s, razaoSocial=%s, cgcCnpj=%s]", codParc, nomeParc,
				razaoSocial, cgcCnpj);
	}

}
