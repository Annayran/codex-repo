package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;
import java.util.List;

public class WorkScheduleDTO {
	private BigDecimal							alterationDate;
	private EmployerDTO							employerDTO;
	private String 								externalId;
	private BigDecimal							id;
	private String 								message;
	private String								name;
	private boolean								standard;
	private List<WorkScheduleTimetableDTO>		workScheduleTimetableList;
	
	public BigDecimal getAlterationDate() {
		return alterationDate;
	}
	public void setAlterationDate(BigDecimal alterationDate) {
		this.alterationDate = alterationDate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStandard() {
		return standard;
	}
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	public List<WorkScheduleTimetableDTO> getWorkScheduleTimetableList() {
		return workScheduleTimetableList;
	}
	public void setWorkScheduleTimetableList(List<WorkScheduleTimetableDTO> workScheduleTimetableList) {
		this.workScheduleTimetableList = workScheduleTimetableList;
	}
	
	@Override
	public String toString() {
		return String.format(
				"WorkScheduleDTO [alterationDate=%s, employerDTO=%s, externalId=%s, id=%s, message=%s, name=%s, standard=%s, workScheduleTimetableList=%s]",
				alterationDate, employerDTO, externalId, id, message, name, standard, workScheduleTimetableList);
	}
	
}
