package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class DismissDTO {
	private transient BigDecimal				codEmp;
	private transient BigDecimal				codFunc;
	private String								externalId;
	private BigDecimal							resignationDate;
	private BigDecimal							tangerinoId;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getCodFunc() {
		return codFunc;
	}
	public void setCodFunc(BigDecimal codFunc) {
		this.codFunc = codFunc;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public BigDecimal getResignationDate() {
		return resignationDate;
	}
	public void setResignationDate(BigDecimal resignationDate) {
		this.resignationDate = resignationDate;
	}
	public BigDecimal getTangerinoId() {
		return tangerinoId;
	}
	public void setTangerinoId(BigDecimal tangerinoId) {
		this.tangerinoId = tangerinoId;
	}
	
	@Override
	public String toString() {
		return String.format("DismissDTO [codEmp=%s, codFunc=%s, externalId=%s, resignationDate=%s, tangerinoId=%s]",
				codEmp, codFunc, externalId, resignationDate, tangerinoId);
	}
	
}
