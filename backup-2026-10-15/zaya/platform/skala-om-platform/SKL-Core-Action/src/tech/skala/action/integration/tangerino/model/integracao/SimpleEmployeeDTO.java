package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class SimpleEmployeeDTO {
	private transient BigDecimal				codEmp;
	private transient BigDecimal				codFunc;
	private BigDecimal							id;
	private String								name;
	private String								externalId;
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
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public BigDecimal getTangerinoId() {
		return tangerinoId;
	}
	public void setTangerinoId(BigDecimal tangerinoId) {
		this.tangerinoId = tangerinoId;
	}
	
	@Override
	public String toString() {
		return String.format("SimpleEmployeeDTO [codEmp=%s, codFunc=%s, id=%s, name=%s, externalId=%s, tangerinoId=%s]",
				codEmp, codFunc, id, name, externalId, tangerinoId);
	}
	
}
