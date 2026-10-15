package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class EmployeeFunctionSimpleDTO implements DTO {
	private String 								description;
	private BigDecimal							externalId;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getExternalId() {
		return externalId;
	}
	public void setExternalId(BigDecimal externalId) {
		this.externalId = externalId;
	}
	
	@Override
	public String toString() {
		return String.format("EmployeeFunctionSimpleDTO [description=%s, externalId=%s]", description, externalId);
	}
}
