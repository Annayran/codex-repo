package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class EmployeeWorkScheduleAssignDTO {
	private String 								employeeExternalId;
	private BigDecimal							employeeId;
	private BigDecimal							startDate;
	private BigDecimal							workScheduleId;
	
	public String getEmployeeExternalId() {
		return employeeExternalId;
	}
	public void setEmployeeExternalId(String employeeExternalId) {
		this.employeeExternalId = employeeExternalId;
	}
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}
	public BigDecimal getStartDate() {
		return startDate;
	}
	public void setStartDate(BigDecimal startDate) {
		this.startDate = startDate;
	}
	public BigDecimal getWorkScheduleId() {
		return workScheduleId;
	}
	public void setWorkScheduleId(BigDecimal workScheduleId) {
		this.workScheduleId = workScheduleId;
	}
	
	@Override
	public String toString() {
		return String.format(
				"EmployeeWorkScheduleAssignDTO [employeeExternalId=%s, employeeId=%s, startDate=%s, workScheduleId=%s]",
				employeeExternalId, employeeId, startDate, workScheduleId);
	}

}
