package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;
import java.util.List;

public class WorkScheduleReturnDTO {
	private String								externalId;
	private BigDecimal							id;
	private String								name;
	private boolean								standard;
	private BigDecimal							startDate;
	private List<WorkScheduleTimetableDTO>		workScheduleTimetableList;
	
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
	public BigDecimal getStartDate() {
		return startDate;
	}
	public void setStartDate(BigDecimal startDate) {
		this.startDate = startDate;
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
				"WorkScheduleReturnDTO [externalId=%s, id=%s, name=%s, standard=%s, startDate=%s, workScheduleTimetableList=%s]",
				externalId, id, name, standard, startDate, workScheduleTimetableList);
	}
	
}
