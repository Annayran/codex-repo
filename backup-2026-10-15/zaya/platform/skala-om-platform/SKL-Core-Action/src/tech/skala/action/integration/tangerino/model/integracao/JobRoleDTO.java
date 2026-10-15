package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class JobRoleDTO {
	private BigDecimal							alterationDate;
	private String								description;
	private EmployerDTO							employerDTO;
	private String								externalId;
	private BigDecimal							id;
	private String 								message;
	
	public BigDecimal getAlterationDate() {
		return alterationDate;
	}
	public void setAlterationDate(BigDecimal alterationDate) {
		this.alterationDate = alterationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EmployerDTO getEmployerDTO() {
		return employerDTO;
	}
	public void setEmployerDTO(EmployerDTO employerDTO) {
		this.employerDTO = employerDTO;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
		return String.format(
				"JobRoleDTO [alterationDate=%s, description=%s, employerDTO=%s, externalId=%s, id=%s, message=%s]",
				alterationDate, description, employerDTO, externalId, id, message);
	}
	
}
