package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class AdjustmentReasonRecordDTO {
	private AdjustmentReasonDTO					adjustmentReasonDTO;
	private SimpleEmployeeDTO					employeeDTO;
	private BigDecimal							endDate;
	private boolean								fullDay;
	private String								observation;
	private String								origem;
	private BigDecimal							startDate;
	private String								status;
	
	public AdjustmentReasonDTO getAdjustmentReasonDTO() {
		return adjustmentReasonDTO;
	}
	public void setAdjustmentReasonDTO(AdjustmentReasonDTO adjustmentReasonDTO) {
		this.adjustmentReasonDTO = adjustmentReasonDTO;
	}
	public SimpleEmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}
	public void setEmployeeDTO(SimpleEmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}
	public BigDecimal getEndDate() {
		return endDate;
	}
	public void setEndDate(BigDecimal endDate) {
		this.endDate = endDate;
	}
	public boolean isFullDay() {
		return fullDay;
	}
	public void setFullDay(boolean fullDay) {
		this.fullDay = fullDay;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public BigDecimal getStartDate() {
		return startDate;
	}
	public void setStartDate(BigDecimal startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String.format(
				"AdjustmentReasonRecordDTO [adjustmentReasonDTO=%s, employeeDTO=%s, endDate=%s, fullDay=%s, observation=%s, origem=%s, startDate=%s, status=%s]",
				adjustmentReasonDTO, employeeDTO, endDate, fullDay, observation, origem, startDate, status);
	}
}
