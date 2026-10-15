package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class TurnoIdDTO implements DTO {
	private BigDecimal							idTurno;

	public BigDecimal getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(BigDecimal idTurno) {
		this.idTurno = idTurno;
	}
	
	@Override
	public String toString() {
		return String.format("TurnoIdDTO [idTurno=%s]", idTurno);
	}
	
}
