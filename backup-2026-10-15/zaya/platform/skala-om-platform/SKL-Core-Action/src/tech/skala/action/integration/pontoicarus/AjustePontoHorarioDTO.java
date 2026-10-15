package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class AjustePontoHorarioDTO implements DTO {
	private String								horarioDateTime;

	public String getHorarioDateTime() {
		return horarioDateTime;
	}
	public void setHorarioDateTime(String horarioDateTime) {
		this.horarioDateTime = horarioDateTime;
	}
	
	@Override
	public String toString() {
		return String.format("AjustePontoHorarioDTO [horarioDateTime=%s]", horarioDateTime);
	}

}
