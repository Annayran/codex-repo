package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class EmployeeFunctionDTO {
	private BigDecimal							externalId;
	private String 								description;
	private BigDecimal							id;
	private String								message;
	
	public BigDecimal getExternalId() {
		return externalId;
	}
	public void setExternalId(BigDecimal externalId) {
		this.externalId = externalId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return String.format("EmployeeFunctionDTO [externalId=%s, description=%s, id=%s, message=%s]", externalId,
				description, id, message);
	}

}
